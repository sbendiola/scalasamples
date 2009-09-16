package jactors 

import org.jetlang.core.Callback
import org.jetlang.core.Disposable
import org.jetlang.channels.MemoryChannel
import org.jetlang.fibers.Fiber

object JetlangExplicits {  
  implicit def toCallback[T](f : T => Any) = {
    new Callback[T] {
      def onMessage(message: T) {
        f(message)
      }      
    }
  }
}
private[jactors] class ChannelSubscriber[T](fiber: Fiber, channel: Channel[T]) {
  def += (f: T => Any) : Disposable = {
    val cb:Callback[T] = JetlangExplicits.toCallback(f)
    this.+=(cb)
  }
  
  def += (callback: Callback[T]) : Disposable = {
    channel.subscribe(fiber, callback)  
  }
}
class Channel[T] extends MemoryChannel[T] {
  
  def apply(fiber: Fiber) = {    
    new ChannelSubscriber[T](fiber, this)
  }  
      
  def ! (msg: T) {
    publish(msg)
  }
}

trait JetReactor[T] extends Callback[T] {
  private var target:PartialFunction[T, Any] = null
  
  def react(pf: PartialFunction[T, Any]) {
    target = pf
  }
  
  override def onMessage(message: T) {
    if (target.isDefinedAt(message)) {
      target(message)
    }
  } 
  
}