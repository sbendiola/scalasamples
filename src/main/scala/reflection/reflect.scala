object reflection {

  import java.lang.reflect.Modifier
  import java.lang.reflect.Modifier._
  import java.lang.reflect.Method

  implicit def toReflectionHelper(ref: AnyRef) = new ReflectionHelper(ref)  
      
  class ReflectionHelper(ref: AnyRef) {        
    val methodHelper = new Methods(ref.getClass, ref.getClass.getMethods.toList)
    def methods = methodHelper
  }
  
  class Methods(clazz: Class[_], val methods: List[Method]) {    
    val declaredFilter: Method => Boolean = m => m.getDeclaringClass == clazz
    
    def public = {
      methods.map(m => Modifier.isPublic(m.getModifiers))
    }
    
    def declared = {
      methods.filter(declaredFilter).toList
    }
    
    def inherited = {
      methods.filter(m => declaredFilter(m) && false)
    }        
  }
}