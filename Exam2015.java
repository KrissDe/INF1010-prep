//Task 3

class LList<T extends Comparable<T>> {
  private Node head;
  
  /* 3c - write the class ListEnd. Objects of this class will be used for two special nodes, the list head and the
      list tail. The list head is always the first node and the tail always the last
      node in the link list. Use polymorphism to ensure that the list head node is
      «less than» all other nodes, and to ensure that the list tail node is «greater
      than» all other nodes.
  */
  
  private class ListEnd extends Node { 
    // Needs to be discussed. 
    
    protected ListEnd listHead, listTail; 
    private Node helpNode, firstNode, lastNode;
    
    ListEnd() {
      firstNode = head;
      lastNode = head;
      
      while(head.next != null) {
	assert firstNode.compare(head) == 0;
	assert lastNode.next.compare(head) == 1;
	head = head.next;
      }    
      
      listHead = (ListEnd) head;
      listTail = (ListEnd) helpNode;         
      
    }
    
    int compare(Node k) { 
      return 1;
    }
  }
  
  private class ListEnd extends Node { 
    private Node prev;
    
    ListEnd() {
      prev = null;
      next = null;
      // either prev = ... or next = ... should be called after the object is created!
    }
    
    int compare(Node k) {
      assert prev == null && next != null  ||  prev != null && next == null;
      if (prev == null) { // head
	// assert false;
	return -1;
      } else { // tail
	return 1;
      }
    }	
    
    
  }
  
  
  private class Node {
    protected T t ;
    // pointer to the object to be put into the list
    
    protected Node next;
    
    Node(T newObject) {
      t = newObject;
    }
    
    /* 3a - write the method compare(Node k). The method should work as compareTo, ie return an integer greater than zero
       if the object k.t points out is less than the object (this node’s) t is pointing at.
    */
    
    int compare(Node k) { 
      return t.compareTo(k.t);      
      //if(this.t >= k.t) return 1;
      //else return 0;
    }

    
    /* 3b - write the recursive method insert(Node newNode) adding a new node into the linked list pointed to by
       foran in the Liste class. You do not get full score for this task if the method is not recursive.
    */
    
    void insert(Node newNode) {
      assert next != null;
      
      if(next.compare(newNode) > 0){ //next >= newNode
      // strcmp: abc3 '3'=0x33
      //         abc1 '1'=0x31
	newNode.next = next;
	next = newNode;
      
      }else{
	next.insert(newNode);
      }
    }
    
    

    void print() {
      System.out.println (t);
      next.print();
    }
  }
  
  // 3d - Write the constructor Liste() {...}. The list is initialized with its head and tail here.
  //ListEnd newListEnds;
  
  LList() {
    /*
    head = new Node(); // it is actually done automatically, right? I think there is no need for this line but the task confuses me
    ///newListEnds = new ListEnd(); //since we need to initialize tail somehow - variable for the tail of the list is not in the precode...
    head.next = new ListEnd();
    */
    
    head = new ListEnd(); // head.prev = null, head.next = null
    ListEnd tail = new ListEnd(); // tail.prev = null, tail.next = null
    
    head.next = tail;
    tail.prev = head;
  }

  
  public void insert(T t) {

    // you must not change this
    Node newNode = new Node(t);
    head.insert(newNode);
  
  }
  
  public void printAll() {
    System.out.println("All in the list :\ n" + "-----");
    head.print();
    System.out.println(" SLUTT ");
  }
}

class OrderedLinkedList {
  public static void main(String[] args) {
    LList<String> wordList = new LList<String >();
    String[] name = new String[] {" I " , "dag", " er " , " det " , "eksamen" , " i " , "INF1010 .\ n" , "Jeg" , "håper " , "du" , " liker " , "denne" ,  "oppgaven .\ n" ,
"Lykke" , " til! " , " hilsen " , " oppgaveforfatteren \n" };
    
    System.out.print( "Inserting:  ");
    
    for(String n: name) {
      System.out.print(n + " ");
      wordList.insert(n);
    }
    
    System.out.println();
    wordList.printAll();
    System.out.println();
  }
}


