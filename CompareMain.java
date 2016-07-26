import java.util.*;
import java.io.*;

class CompareMain{
     public static void main(String[] args){
         LinkedLists l1 = new LinkedLists();
         LinkedLists l2 = new LinkedLists();
         LinkedLists l3 = new LinkedLists();
         LinkedLists l4 = new LinkedLists();
         
         System.out.println("List #1, inserting at the tail position..");
         l1.insertTail(2);
         l1.insertTail(3);
         
         System.out.println("List #2, inserting at the head position..");
         l2.insertHead(1);
         l2.insertHead(2);
         
         System.out.println("Inserting at specific position in the list 1 and 2..");
         l1.insertPos(4,1);
         l2.insertPos(8,2);
         
         System.out.println("Printing resulted list 1 and 2..");
         l1.printList();
         l2.printList();
         
         System.out.println("Deleting 1st node in list #2 and 2d node in list #1, and printing the resulted lists..");
         l2.deleteNode(0);
         l1.deleteNode(1);
         l1.printList();
         l2.printList();
         
         System.out.println("Printing reversed list #1: ");
         l1.reversePrint();
         System.out.println();
         
         System.out.println("Reversing the list #2 and printing the resulted list: ");
         l2.reverseList();
         l2.printList();
         
         System.out.println("Comparing lists 1 and 2 - all elements must be equal to be true..");
         l1.compareListsTotal(l2);
         
         System.out.println("Creating list #3 that is identical to list #1..");
         l3.insertTail(2);
         l3.insertTail(3);
         
         System.out.println("Comparing lists 3 and 1..");
         l1.compareListsTotal(l3);
         
         System.out.println("Creating list #4 that has one element that is the same in list #2..");
         l4.insertHead(8);
         l4.insertTail(10);
         
         System.out.println("Comparing lists 4 and 2, 2 and 3 - evaluates to true iff elements at some position are equal");
         l4.compareListsElem(l2, 0);
         l2.compareListsElem(l3, 1);
         
         Scanner scan = new Scanner(System.in);
         LinkedLists userLL = new LinkedLists();
         System.out.println("Type elements for the new linked list separated with whitespace (f.ex. 1 2 3 4) > ");
         String line = scan.nextLine();
         String[] nrs = line.split(" ");
         
         for(int i=0; i<nrs.length; i++){
             userLL.insertTail(Integer.parseInt(nrs[i]));
         }

         
         System.out.println("Printing resulted user's linked list..");
         userLL.printList();
         System.out.println("Comparing with list #2..");
         userLL.compareListsTotal(l2);
     }
     

}


class LinkedLists{
    Node head;
    
    public LinkedLists(){
        
    }
    
    
    
    public void insertTail(int data){          
          
	  if(head == null){
	    head = new Node(data);
	  }else{
	  
	    Node n = new Node(data);
	    Node current = head;  
	    
	    while(current.next != null){
	      current = current.next;
	    }
	    
	    current.next = n;
	  }
    }
    
    
    
    
    
     public void insertHead(int x) {
 	Node n = new Node(x);
 	
 	if(head == null){
 	    head = n;
 	}else{
 	
	  Node tmp = head;
	  head = n;
	  head.next = tmp;
 	}
     }

     
     
     
     
     
     public void insertPos(int data, int position) {
 
 	Node n = new Node(data);
  	if(position == 0){    //smth.wrong here
	    n.next = head;   
	}
 	  
 	Node cur = head;  
 	for(int i = 0; i<position-1; i++){
 	    cur = cur.next;                
 	} 
 	// cur is (position-1)-th node
 	
 	n.next = cur.next;
 	cur.next = n;
	
	System.out.println("Insert at position " + position + " ok");
 
     }
     
     
     
     
     
     public void deleteNode(int position) {
 	Node tmp = head;
 	
 	if(head == null) System.out.println("List is empty");
 	
 	if(position == 0) head = tmp.next;
 	
 	for(int i=0; tmp!= null && i<position-1; i++){
 	    tmp = tmp.next;
 	}
 	
 	Node current = tmp.next.next;
 	tmp.next = current;    
 	System.out.println("Deletion at " + position + " ok");
     }
     
     
     
     
     
     public void reversePrint(){
         reversePrint(head);
     }
     
     public void reversePrint(Node head){
 	if(head ==  null) System.out.println("List is empty");
 	
 	if(head.next!=null){
 	    reversePrint(head.next);
 	}
 	System.out.print(head.data + " ");
     }
     
 
 
 
 
     public void reverseList() {
 	Node current = head;
 	Node previous = null;
 	Node afterwards = null;
 	
 	while(current != null){
 	    afterwards = current.next;
 	    current.next = previous;
 	    previous = current;
 	    current = afterwards;
 	}
 	head = previous;
 	System.out.println("Reverse ok");
     }

     
     
     
    
    public void printList() {
      Node currentNode = head;
      System.out.print("List: ");
      
      while (currentNode != null){
	  System.out.print(currentNode.data + " ");
	  currentNode = currentNode.next;
      }
      
      System.out.println();
    }

    
    
    
     public void compareListsTotal(LinkedLists B){
         if(compareListsTotal(this.head, B.head) == 1) System.out.println("Equal lists");
         else if(compareListsTotal(this.head, B.head) == 0) System.out.println("Not equal lists");
         else System.out.println("Error");
     }
     
     
     
     public int compareListsTotal(Node headA, Node headB) {
 	if (headA == null || headB == null)
 	    return (headA == null && headB == null) ? 1 : 0;
 	
 	if (headA.data != headB.data)
 	    return 0;
 	
 	return compareListsTotal(headA.next, headB.next);
     }
     
     
     
     
     
     public void compareListsElem(LinkedLists B, int position){
         if(compareListsElem(this.head, B.head, position) == 1){ 
	    System.out.println("Equal lists");
         }else if(compareListsElem(this.head, B.head, position) == 0){ 
	    System.out.println("Not equal lists");
         }else{ 
	    System.out.println("Error");
	 }
     }
     
     
     
     public int compareListsElem(Node headA, Node headB, int pos){
         if(headA == null || headB == null){
 	    return (headA == null && headB == null && pos == 0) ? 1 : 0;
 	 }
 		
 	if(headA.data == headB.data && pos == 0){
 	    return 1;
 	}
 	
 	if(headA.data != headB.data && pos == 0){
	    return 0;
	}
 	
	return compareListsElem(headA.next, headB.next, pos--);
     }

     
     
     
     
    private class Node{
        Node next;
        int data;
        
        public Node(int data){
            next = null;
            this.data = data;
        }
    }
}