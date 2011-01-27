package xmlbuilder 

trait XMLBuilder {
  // namespaces, etc. ignored for now
 
  implicit def symbolToElement(symbol : Symbol) = element(symbol.name)
 
  def element(name : String) : Element = element(name, Nil)
  def element(name : String, attributes : List[(String, String)]) : Element = new Element(name, attributes)
     
  class Element(name : String, attributes : List[(String, String)]) {
    def apply(body : => Unit) {
      startElement(name, attributes)
      body
      endElement(name)
    }
   
    def apply(attributes : (String, String)*) = element(name,attributes.toList);
    def apply(content : String) { apply { text(content) } }
  }
 
  def startElement(name : String, attributes : List[(String, String)]) {
    val attr = attributes.foldLeft(new StringBuilder) { (buffer, a) =>
      buffer.append(<x>'{a._1}'={a._2}</x>.text)
    }
    println(new StringBuilder().append("<")
                     .append(name)
                     .append(if (attr.isEmpty) attr.toString else attr.insert(0, " ").toString)
                     .append(">"))
  }
  
  def text(content : String) {
    println(content)
  }
  
  def endElement(name : String) {
    println(new StringBuilder().append("</").append(name).append(">"))
  }
}