//Task 3
class LList<T extends Comparable<T>> {
  private Node head;
  
  private class ListEnd extends Node { 
    //Needs to be discussed. Do we need to kind of iterate the list and check if the largest element is in the last position in the list
    //and therefore create a pointer of class ListEnd to it?
  }
  
  // c
  private class Node {
    protected T t ;
    // pointer to the object to be put into the list
    
    protected Node next;
    
    Node(T newObject) {
      t = newObject;
    }
    
    // a
    int compare(Node k) { 
      if(this.t > k.t) return 1;
      else return 0;
    }

    
    // b
    void insert(Node newNode) {
      assert next != null;
      if(next.compare(newNode) == 1){ //next>newNode
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
  //d
  LList() {
    head = null;
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


