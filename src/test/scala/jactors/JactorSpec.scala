package jactors

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec
import org.jetlang.channels.MemoryChannel
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit._
import jactors.JetlangExplicits._
import org.jetlang.fibers.{FiberStub, ThreadFiber}

class JactorSpec extends Spec with ShouldMatchers {
	
	sealed abstract class Msg
  case class Add(i:Int) extends Msg
  case class Subtract(i:Int) extends Msg
        
  describe("A JetReactor") {
    it("can initialize it's callback") {
      val fiber = new FiberStub
      fiber.start
      val channel = new Channel[Msg]
      
      var value = 0
      val actor = new JetReactor[Msg] {
        react {
          case Add(i) => value += i
          case Subtract(i) => value -= i 
        }
      }
      
      channel(fiber) += actor
      channel ! Add(1)
      channel ! Subtract(2)
      fiber.executeAllPending
      value should be (-1)
      ()
    }
    
    it("can modify it's callback") {
      case class Update(i:PartialFunction[Msg, Any]) extends Msg
      val fiber = new FiberStub
      fiber.start
      val channel = new Channel[Msg]      
      var value = 0
      
      val actor = new JetReactor[Msg] {
        react {
          case Update(f) => react(f)
          case _ => value = 100
        }
      }
      
      channel(fiber) += actor      
      channel ! Update {
        case _ => value = 1000
      }
      
      channel ! Add(1)          
      fiber.executeAllPending
      value should be (1000)
      ()
    }
    
    it("can simulate state") {
      case class First(i:Int) extends Msg
      case class Second(i:Int) extends Msg
      case object Totals extends Msg
      
      val fiber = new FiberStub
      fiber.start
      val channel = new Channel[Msg]
      
      var totals = (0, 0)
      val actor = new JetReactor[Msg] {        
        def loop(first: Int, second: Int) : Unit = {
          react {
            case First(i) => loop(first + i, second)
            case Second(i) => loop(first, second + i) 
            case Totals => totals = (first, second)            
          }
        }        
        loop(0, 0)
      }
      
      channel(fiber) += actor  
      channel ! First(1)          
      channel ! Second(43)          
      channel ! Totals
      fiber.executeAllPending
      totals should be (1, 43)
      ()
    }
  }
}
