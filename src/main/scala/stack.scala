class Stack[T] {
	var items = List[T]()
	
    def push(item: T) {
	  items = item :: items  
    }

    def pop() : T = {
	  items match {
	    case h :: t =>	
	     items = t
		 h
	    case _ => throw new Exception("no elements")
	  } 
    }	

    def empty = {
	  items == Nil
	}
  }