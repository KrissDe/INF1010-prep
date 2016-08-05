/* DOUBLY LINKED LIST

Node next, Node previous, Node a;

1a) How many objects are there in the picture? 4 (boxes illustrate objects)
1b) How many different types are there? 1 (type Node)
1c) How many variables are there in the picture? 3 (Node a, Node neste, Node forrige)

2. Implement method void swap(Node n) such that being called swap(a)
swaps node a points at with node a.next. In the picture there are 2 middle 
node objects that are coloured more grey. You can assume that there is a 
node before and a node after these 2 nodes that need to be swapped.

	void bytt(Node n) {
	  Node n1 = n.forrige;
	  Node n2 = n;
	  Node n3 = n.neste;
	  Node n4 = n.neste.neste;
	  
	  n1.neste = n3;
	  n2.neste = n4;
	  n2.forrige = n3;
	  n3.neste = n2;
	  n3.forrige = n1;
	  n4.forrige = n2;
	}

LINKED LIST SCOPE

Program sketch for the scope that uses ll as a data structure */

public class LinkedLists<T extends Comparable<T>> {
  private ListHead lhead; //list head 
  private ListTail ltail; //list tail 
  private int size;  // sum of objects in the list


  LinkedLists(){ 
    lhead = new ListHead(); //is initialized automatically
    ltail = new ListTail(); //is initialized automatically
    size = 0; //is initialized automatically
    lhead.next = ltail;
    ltail.prev = lhead; // why is it possible to assign lhead of type ListHead to ltail.prev of type AbstrNode? 
			// is it because of casting downwards (AbstrNode -> ListHead) ?
  }

  private abstract class AbstrNode {
    T obj;
    AbstrNode next;

    AbstrNode(T t) {
      obj = t;
      next = null;
    }

    abstract int compareTo(AbstrNode k);
    abstract void insertOrdered(AbstrNode k);
  }
  
  private class ListHead extends AbstrNode {
    
    ListHead() {
	/* if we don't use obj in ListHead and obj+next in ListTail
	   do we need to operate with them in the constructor?
	*/
      
    }
    
    int compareTo(AbstrNode k){
      if(next != null){ // next is the first element in the list (like Node head in a traditional LL implementation)
	return next.compareTo(k.obj); // shouldn't it be next.obj ?
      }else{
	return 1;
      }
    }
    
    void insertOrdered(AbstrNode k){
      assert next != null; //but what if there is an empty list and we want to insert the first element?
     
     if (next.compareTo(newNode) >= 0) {
	// next >= newNode
	// 1) next = tail = +inf
	newNode.next = next;
	next = newNode;
      } else {
	next.insertOrdered(newNode);
      }
      size++;  
    }
    
    
  }
  
  private class ListTail extends AbstrNode {
    AbstrNode prev;
    
    ListTail() {
    
    }
    
    //why do we return 1 in any case? what if we want to compare the tail with an object that is larger? 
    int compareTo(AbstrNode k){
      return 1;
    }
    
    void insertOrdered(AbstrNode k){
      assert false; //no need to implement this method for tail
    }
  }
  
  private class Node extends AbstrNode {
    
    Node() {

    }
    
    int compareTo(AbstrNode k){
      return obj.compareTo(k.obj);
    }
    
  }
  
  public int sum() {
    return sum;
  }
  
  //this method must not be changed
  public void insertOrdered(T newComparable){
    Node newNode = new Node(newComparable);
    lhead.insertOrdered(newNode);
  }
  
  public void insertTail(T newComparable) { 
    Node newNode = new Node(newComparable);
    ListTail b = ltail;
    AbstrNode a = b.prev; //why can't it be of type Node since it's the real Node prev points at?
    
    newNode.next = b;
    a.next = newNode;
    b.prev = newNode; 
    //we need to increase the size of the scope here, right? since there is a new object we are inserting 
  }
  
  public T getFromFront() throws OutOfBoundsException { 
    if (empty()) {
      throw new OutOfBoundsException("The list is empty");
    }
    
    
    Node a = lhead;
    Node b = a.next;
    Node c = b.next;
    
    a.next = c;
    
    return b.data; 
    
  }
  
  public boolean empty() { 
    return size == 0;
  }

}

class Main{
  public static void main (Strin[] a){
    LinkedLists<String> llStr = new LinkedLists<String>();
    //data structure here is as in the picture
    llStr.insertTail(new String("sss"));
    llStr.insertOrdered(new String("yyy"));
    llStr.insertOrdered(new String("ttt"));
    llStr.insertTail(new String("aaa"));
    llStr.insertOrdered(new String("bbb"));
  }
}



/* Linked list has a list head and list tail. Data structure when we create 
a new scope is in the picture.

3. Implement classes LinkedLists, ListHead, ListTail and Node with class
variables and constructors such that scope state will be the same as in the 
picture when we have just created an empty scope, jf.main-method in task 9.

4. Pay attention to how the method compareTo() is defined in Node and that 
we have defined this method in this scope in order to compare nodes.
Implement method compareTo() in ListHead.

5. Implement method compareTo() in ListTail.

6. Variable previous in the list tail must always point to the 
node at the back. It must point to list head when scope is empty.
Implement method insertTail in the scope such that the new object will be inserted
at the back. This method must not be recursive.

7. Pay attention to how the insertOrdered is defined in the scope. It calls the recursive
insert-method in the list head. Complete the classes ListHead, ListTail and Node with the 
recursive method insertOrdered where it's required. Make it clear in the answer what method 
belongs to what class.

8. Implement method getFromFront in the class LinkedLists such that the first node 
(not the list head) is removed and the object obj points to is returned to where it's been
called. If the list is empty the method must throw exception. +ok

9. Draw data structure that will be created by main-methof in the program sketch. In addition
to the objects inserted you must draw the list head and list tail. +ok (see 2016-9.jpg)

10. Since list head and list tail don't need obj-variable and list tail doesn't need
next-pointer, we want to remove these variables from AbstrNode. After that we will have a class
that can be defined as interface. Let us call it as Elem since it's an interface for all the list 
elements (nodes and list ends). Implement interface and classes ListHead, ListTail 
and Node once more. You don't need to write methods that are not being changed. +ok (see code downwards)

*/

// HERE IS IMPLEMENTATION WITH INTERFACE ELEM

public class LinkedLists<T extends Comparable<T>> {
  private ListHead lhead; //list head 
  private ListTail ltail; //list tail 
  private int size;  // sum of objects in the list


  LinkedLists(){ 
    lhead = new ListHead(); //is initialized automatically
    ltail = new ListTail(); //is initialized automatically
    size = 0; //is initialized automatically
    lhead.next = ltail;
    ltail.prev = lhead; // why is it possible to assign lhead of type ListHead to ltail.prev of type AbstrNode? 
			// is it because of casting downwards (AbstrNode -> ListHead) ?
  }
  
  private interface Elem {
    int compareTo(Elem k);
    void insertOrdered(Elem k);
  }

  private class ListHead implements Elem {
    
    ListHead() {
	/* if we don't use obj in ListHead and obj+next in ListTail
	   do we need to operate with them in the constructor?
	*/
      
    }
    
    int compareTo(Elem k){
      if(next != null){ // next is the first element in the list (like Node head in a traditional LL implementation)
	return next.compareTo(k.obj); // shouldn't it be next.obj ?
      }else{
	return 1;
      }
    }
    
    void insertOrdered(Elem k){
      assert next != null; //but what if there is an empty list and we want to insert the first element?
     
     if (next.compareTo(newNode) >= 0) {
	// next >= newNode
	// 1) next = tail = +inf
	newNode.next = next;
	next = newNode;
	
      } else {
	next.insertOrdered(newNode);
      }
      size++;  
    }
    
    
  }
  
  private class ListTail implements Elem {
    Elem prev;
    
    ListTail() {
    
    }
    
    //why do we return 1 in any case? what if we want to compare the tail with an object that is larger? 
    int compareTo(Elem k){
      return 1;
    }
    
    void insertOrdered(Elem k){
      assert false; //no need to implement this method for tail
    }
  }
  
  private class Node implements Elem {
    
    Node() {

    }
    
    int compareTo(Elem k){
      return obj.compareTo(k.obj);
    }
    
  }
  
  public int sum() {
    return sum;
  }
  
  //this method must not be changed
  public void insertOrdered(T newComparable){
    Node newNode = new Node(newComparable);
    lhead.insertOrdered(newNode);
  }
  
  public void insertTail(T newComparable) { 
    Node newNode = new Node(newComparable);
    ListTail b = ltail;
    Elem a = b.prev; //why can't it be of type Node since it's the real Node prev points at?
    
    newNode.next = b;
    a.next = newNode;
    b.prev = newNode; 
    //we need to increase the size of the scope here, right? since there is a new object we are inserting 
  }
  
  public T getFromFront() throws OutOfBoundsException { 
    if (empty()) {
      throw new OutOfBoundsException("The list is empty");
    }
    
    
    Node a = lhead;
    Node b = a.next;
    Node c = b.next;
    
    a.next = c;
    
    return b.data; 
    
  }
  
  public boolean empty() { 
    return size == 0;
  }

}


class Main{
  public static void main (Strin[] a){
    LinkedLists<String> llStr = new LinkedLists<String>();
    //data structure here is as in the picture
    llStr.insertTail(new String("sss"));
    llStr.insertOrdered(new String("yyy"));
    llStr.insertOrdered(new String("ttt"));
    llStr.insertTail(new String("aaa"));
    llStr.insertOrdered(new String("bbb"));
  }
}


/* KLASSES, INTERFACE AND INHERITANCE

13. Define classes and interfaces that represent the hierarchy in the picture. Definitions
must be empty, ie.there must be empty between { }. There must be only classes and interfaces that 
are in the picture. Write all interfaces first, followed by abstract classes. In the end write all the
non-abstract classes in alphabetical order. 1 definition per line.

14a. How many different objects can be created?
14b. How many methods is it possible to call in the object of class B when we know that 
Object has 11 methods and classes don't have their own methods and that all interfaces in the hierarchy/program
have 2 methods each, all with different signatures?
14c. How many methods are available in the object of class G?
14d. How many methods is it possible to call from variable ia after assignment in the line 8 of the program?

      public class Mainclass{
	public static void main(String[] args){
	    C c = new E();
	    IC ic = c;
	    IBD ibd = c;
	    Object o = new B();
	    IA ia = (IA) o;
	    ic = (IC) o;
	    A a = (C) ic;
	    E e = new A();
	    IBD ibd = (IBD) a;
	    a = (B) o;
	    c = new C();
	}
      }

      
15. Remove sentences that give errors either during compilation or execution. Write the main-method once more 
without error sentences.

16. Draw data structure the way it is right before the main-method finishes execution. You don't need to draw 
variables that have value null or objects that are not being pointed to/referred by variables.
*/



/* SORT TEXT STRINGS

We have a file with 390 000 words(text strings) that we will sort, ie. order in the alphabetical(ascending) order.
To check if the word is alphabetically larger or smaller than the other word we will use method compareTo()
from the class String.
A sketch of the algorithm of the approach where you end with the linked list where all the words are ordered(you will not 
program this non-parallell algorithm):
-first we create a String[] that has place for 390 000 words. Array will be filled from the file with the name 
manywords with help of completed method String[] readWordsFromFile(String fileName)
-then we create a scope of class LinkedLists from the previous task and put 10 000 words from the array into it such that they will be 
ordered. We use insertOrdered for that.
-We do it 39 times and each time we have filled the new scope with 10 000 words from the array, we merge them together with the resulting 
scope to a new resulting scope.
In the end we have a resulting scope with 390 000 words.

Pay attention to that we only use an array with words as temporary container. We use scopes both for sorting and merging. For merging of 2 scopes 
we use method merge that is partially completed:

      LinkedLists<String> merge(LinkedLists<String> a, LinkedLists<String> b){
	//merge ordered lists a and b together to the new ordered list c
	
	LinkedLists<String> c = new LinkedLists<String>();
	String fromA = a.getFromFront();
	String fromB = b.getFromFront();
	while(fromA != null && fromB != null){
	  if(fromA.compareTo(fromB) <= 0){
	    c.insertTail(fromA);
	    fromA = a.getFromFront();
	  }else{
	    c.insertTail(fromB);
	    fromB = b.getFromFront();
	  }
	}
	
	// what do we know about the lists a and b here?
	// some code must be put in here as exercise 11b
      }

      
      
11a. What do we know about the lists a and b after the while-loop in the method merge?

      - while-loop here is used for sorting the most part of the elements from the both linked lists until one of the linked lists (or eventually both)
	become empty. After the loop the most elements from the linked lists are sorted and put into alphabetical order into the resulting scope c. 
	Now the situation when there are still elements in one of the scopes needs to be handled.
	
11b. Complete method merge.

      if(fromA != null){
	c.insertTail(fromA);
      }else{
        c.insertTail(fromB);
      }
      
      return c;

12. Implement main-method that uses threads to parallellize insertion sort and merging. You can but not obliged to use wait()/notify
in this task. But both insertion in the linked lists and merging must be executed in parallell.
*/