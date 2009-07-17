import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec
class StackSpec extends Spec with ShouldMatchers {
	
  describe("A Stack") {
    it("should pop values in last-in-first-out order") {
      val stack = new Stack[Int]
      stack.push(1)
      stack.push(2)
      stack.pop() should equal (2)
      stack.pop() should equal (1)
    }

	it("foo bar") {
      val stack = new Stack[Int]
      stack should be ('empty)
    }

    it("should be empty when created") {
      val stack = new Stack[Int]
      stack should be ('empty)
    }    
  }
}
