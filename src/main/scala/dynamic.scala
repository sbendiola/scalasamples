import scala.util.parsing.json.JSON
object Foo {
object JSONList {
  def apply(text: String): JSONList = 
    new JSONList(JSON.parse(text))     
}

class JSONList(items: Option[Any]) extends Dynamic {
  def invokeDynamic(name: String)(args: Any*) = {
    val value = items.flatMap {
      case l:List[_] => 
        l.find { 
          case (n, _) => n == name
          case _ => false
        }
      case _ => None
    }
    new JSONList(value)
  }
  override def typed[T] = items.map(_.asInstanceOf[T]).get
  override def toString = "JSONList(" + items.toString + ")"
}

val json = JSONList("""{ "foo" : { "bar" : { "baz" : 23  }   }, "cuz" : "abc" }""")


val number = json.foo.bar.baz.typed[Double]
val text = json.cuz.typed[String]
require(number == 23d)
require(text == "abc")
val x = new JSONList(Some(123))
x.bar
}