import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec
import reflection._

class ReflectionSpec extends Spec with ShouldMatchers {

  describe("Reflection") {
    it("should return all methods") {
       val ms = "".methods.toList
       val manual = classOf[String].getMethods.toList
       ms should be (manual)
    }
    
    it("should only return declared") {
       val ms = "".methods.declared.toList
       val manual = classOf[String].getDeclaredMethods
       ms should be (manual)
    }

  }
}