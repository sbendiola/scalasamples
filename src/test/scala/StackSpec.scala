import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec
class StackSpec extends Spec with ShouldMatchers {

  	class Stack[T] {
		var items = List[T]()

	    def push(item: T) {
		  items = item :: items  
	    }

	    def pop() : T = {
		  items match {
		    case h :: t =>	{
			  items = t
			  h
		    }
		    case _ => throw new Exception("no elements")
		  } 
	    }	

	    def empty = {
		  items == Nil
		}
	  }
	
  describe("A Stack") {

    it("should pop values in last-in-first-out order") {
      val stack = new Stack[Int]
      stack.push(1)
      stack.push(2)
      stack.pop() should equal (2)
      stack.pop() should equal (1)
    }

    it("should be empty when created") {
      val stack = new Stack[Int]
      stack should be ('empty)
    }
  }

  
}
