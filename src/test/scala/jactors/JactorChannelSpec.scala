package jactors

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec
import org.jetlang.channels.MemoryChannel
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit._
import org.jetlang.fibers.{FiberStub, ThreadFiber}

class JactorChannelSpec extends Spec with ShouldMatchers {
	
  describe("A Channel") {
    it("can be subscribed to with +=") {
      val channel = new Channel[Int]
      val fiber = new FiberStub
      fiber.start
      var result = 0
      channel(fiber) += { i => 
         result = i
      }
      channel publish 1
      fiber.executeAllPending
      result should be (1)
    }
    
    it("can publish a message with !") {
      val channel = new Channel[Int]
      val fiber = new FiberStub
      fiber.start
      var result = 0
      channel(fiber) += { i => 
         result = i
      }
      channel ! 1
      fiber.executeAllPending
      result should be (1)
    }
    
    it("should execute on a different thread") {
      val channel = new Channel[Int]
      val fiber = new ThreadFiber
      fiber.start
      var executionThread:Thread = null 
      val cd = new CountDownLatch(1)
      channel(fiber) += { i => 
         executionThread = currentThread
         cd.countDown
      }
      channel publish 1      
      cd.await(100, MILLISECONDS)
      executionThread should not equal (null)
      executionThread should not equal (currentThread)
    }
  }
  
}
