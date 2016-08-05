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
      if(this.t >= k.t) return 1;
      else return 0;
    }

    
    /* 3b - write the recursive method insert(Node newNode) adding a new node into the linked list pointed to by
       foran in the Liste class. You do not get full score for this task if the method is not recursive.
    */
    
    void insert(Node newNode) {
      assert next != null;
      
      if(next.compare(newNode) == 1){ //next >= newNode
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
  ListEnd newListEnds;
  
  LList() {
    head = null; // it is actually done automatically, right? I think there is no need for this line but the task confuses me
    newListEnds = new ListEnd(); //since we need to initialize tail somehow - variable for the tail of the list is not in the precode...
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


