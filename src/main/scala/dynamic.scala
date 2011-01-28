import scala.util.parsing.json.JSON

class JSONList(items: Iterable[Any]) extends Dynamic {
  def invokeDynamic(name: String)(args: Any*) = {
    val value = items.collect { case l:Iterable[_] =>  l.collect { case (n, v) if (n == name) => v } }.flatten.headOption
    println("name: ", name, items, "result: ", value)
    new JSONList(value)
  }
  override def typed[T] = items.headOption.map(_.asInstanceOf[T]).get
  override def toString = "JSONList(" + items.toString + ")"
}

object JSONList {
  def apply(text: String): JSONList = 
    new JSONList(JSON.parse(text))     
}

val json = JSONList("""{ "foo" : { "bar" : { "baz" : 23  }   }, "cuz" : "abc" }""")
val number = json.foo.bar.baz.typed[Double]
val text = json.cuz.typed[String]
require(number == 23d)
println(text, text.getClass)
require(text == "abc")
