import scala.util.parsing.json.JSON

class ListAccessor(items: Iterable[Any]) extends Dynamic {
  def invokeDynamic(name: String)(args: Any*) = {
    val value = items.collect { case l:Iterable[_] =>  l.collect { case (n, v) if (n == name) => v } }.flatten.headOption
    println(Map("name" -> name, "args" -> args, "items" -> items, "result" -> value))
    new ListAccessor(value)
  }
  override def typed[T] = items.headOption.map(_.asInstanceOf[T]).get
  override def toString = "ListAccessor(" + items.toString + ")"
}

object ListAccessor {
  def fromJson(text: String): ListAccessor = 
    new ListAccessor(JSON.parse(text))     
}

val json = ListAccessor.fromJson("""{ "foo" : { "bar" : { "baz" : 23  }   }, "cuz" : "abc" }""")
val number = json.foo.bar.baz.typed[Double]
val text = json.cuz.typed[String]
require(number == 23d)
println(text, text.getClass)
require(text == "abc")
val items = List(List(("foo",List(("bar",List(("baz",23.0))))), ("cuz","abc")))
items.foreach(println)
val listsAndMaps = new ListAccessor(items)
require(listsAndMaps.foo.bar.baz.typed[Double] == 23)
require(listsAndMaps.foo.bar.typed[List[Any]] == List("baz" -> 23))
require(listsAndMaps.cuz.typed[String] == "abc")

