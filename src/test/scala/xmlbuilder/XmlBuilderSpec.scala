import org.scalatest.Spec

class XmlBuilderSpec extends Spec with org.scalatest.matchers.ShouldMatchers {
  
  trait Foo {
    implicit def symbolToString(symbol: Symbol) = "foo" 
  }
  
  trait Bar {
    implicit def anotherSymbolToString(symbol: Symbol) = "bar" 
  }
  
  class Test extends Foo {
    import super[Foo].{symbolToString => _, _}
    new Bar {      
      val shouldeBeBar = 'symbol.length
    }
  }
//  import .{convertSymbolToHavePropertyMatcherGenerator => _, _}
  describe("An XmlBuilder") {
    it("should include attributes") {
      new xmlbuilder.XMLBuilder {
        
        //implicit def convertSymbolToHavePropertyMatcherGenerator(symbol : Symbol) : Element = this.element(symbol.name)
        {
          
         'products {
            'widget22 ("name" -> "flux capacitor", "weight" -> "22")
            'widget44 ("name" -> "easy bake oven", "weight" -> "22")
          }
          ()          
        }
      }
    }
  }
}
