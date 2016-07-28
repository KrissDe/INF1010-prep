class LList<T> {
    public Node first;
    public Node last;
    public int size;
    
    public LList(){
//         first = new Node();
//         last = new Node();
//         size = 0;
//         first.next = last;
//         last.prev = first;
    }
      
    private class Node {
      private T data;
      private Node next;
      private Node prev;
      
//       public Node(){
//       
//       }
      
      public Node(T d){
          this.data = d;          
      }
      
    }
    
    public boolean empty(){
        return size == 0;
    }
    
    public int size(){
        return size;
    }
    
    public Node front(){
        return first;        
    }
    
    public Node back(){
        return last;
    }
    
    public void push_front(T data){ 
       Node helpNode = new Node(data);
       
       if(empty()){
	  first = helpNode;
	  last = helpNode;
       }else{  
	  first.prev = helpNode;
	  helpNode.next = first;
	  first = helpNode;
	  
       }
       size++;
    }
    
    public void pop_front(){        
        //if(empty()) return;
        //if(first.next == null){	    
        //    size--;
        //}
        assert first != null;
        first = first.next;
        size--;              
    }
    
    public void push_back(T data){
	Node newbie = new Node(data);
	
	if (empty()) {
	  //assert first == null;
	  first = newbie;
	  last = newbie;
	  
	} else {
	// F <-> ... <-> L
	// F <-> ... <-> L <-> N
	  last.next = newbie; //this line was missing
	  newbie.prev = last;
	  last = newbie;
	}
	size++;       
        
    }
    
    public void pop_back(){
      /*
        if(empty()) return;
        
        if(first.next == null){
	  first = null;
	  size--;
	}else{
	  assert last != null;
	  last = last.prev;
	  last.next = null;
	  size--;
	}
	*/
        assert last != null;
        last  = last.prev;
        size--;   
	
	
	
        
    }
    
    public Node get(int pos){
      Node cur = first;
      for (int i = 0; i < pos; i++)
	cur = cur.next;
      return cur;
    }
      
    
    public void insert_old(int pos, T data){ //insert before the element at the specified position
        Node newbie = new Node(data);
        //if(empty() && pos != 0) return;
        
        if(pos == 0){
	    if(empty()){
	      newbie.next = first;
	      first = newbie;
	      
	      size++;
	    }else{
	      newbie.next = first;
	      first.prev = newbie;
	      first = newbie;  
	      size++;
            }
        }

        Node cur = first;
	for(int i=0; i<pos-1; i++){ //here comes the problem when pos=1
	    cur = cur.next;
	}
	System.out.println("Element " + cur.data + " " + cur.next.data);
	cur.prev.next = newbie;
	newbie.prev = cur.prev;
	cur.prev = newbie;
	newbie.next = cur;        
        size++;
          
    }
    
    public void insert(int pos, T data){ //insert before the element at the specified position
        Node newbie = new Node(data);
        //if(empty() && pos != 0) return;
        
        if (pos == 0) {
	   newbie.next = first;
	   first = newbie;
	} else {	  
	  Node a = get(pos-1);
	  Node b = a.next;
	  
	  n.prev = a;
	  n.next = b;
	  
	  a.next = n;
	  if (b != null)
	    b.prev = n;
	  else
	    last = n;
	}	  
	
	size++;
    }
    
    public void erase(){
        //if(empty()) return;
        first = null;
        last = null;
        size = 0;
    }
    
    public void print() {
	Node cur = first;
	System.out.print("[ ");
	while (cur != null) {
	System.out.print(cur.data + " ");
	cur = cur.next;
	}
	System.out.println("] ");

    }   
    
    public void print_in_reverse_order(){
	System.out.println("Reversed print: ");
	Node cur = last;
        while(cur != null){
            System.out.print(cur.data + " "); 
            cur = cur.prev;
        }
        System.out.println();
        
    }
    

  }

  class DoublyMain {
    public static void main(String[] args) {
      LList<Integer> list = new LList<Integer>();
      assert list.empty(); // read about assert keyword and enable it
      
      list.push_front(2);
      list.print(); //[2]
      list.push_front(4);
      list.push_front(6);
      list.print(); //[6 4 2]
      
      list.pop_front();
      list.print(); //[4 2]
      
      list.erase();
      list.print(); //[]

      list.push_back(1);
      list.push_back(3);
      list.push_back(2);
      list.push_back(4);
      list.print(); //[1 3 2 4]
      list.print_in_reverse_order();
      
      list.pop_back();
      list.print(); //[1 3 2]
      list.pop_back();
      list.pop_back();
      list.pop_back();
      list.print(); //[]
      
      list.push_back(1);
      list.push_back(3);
      list.push_back(2);
      list.push_back(4);

      list.insert(0, 8);
     // list.insert(1, 10);
      list.insert(4, 26);
      list.insert(6, 99);
      list.print(); //[8 10 1 3 26 2 99 4]
      
      
    }
  }
  