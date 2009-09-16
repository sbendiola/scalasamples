
object Streams {
  
  def main(args: Array[String]) = {
    val maxValue = Stream.range(500001, 999999, 2).map(_.toString).reduceLeft((left, right) => {
                   if (left.length > right.length) 
                        left 
                    else 
                        right
               }).first;
    println(maxValue)  
    
  }
}
