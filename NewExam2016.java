/* DOUBLY LINKED LIST

Node next, Node previous, Node a;

1a) How many objects are there in the picture?
1b) How many different types are there?
1c) How many variables are there in the picture?
             fields
             
- a, neste, forrige
- n1.neste, n1.

2. Implement method void swap(Node n) such that being called swap(a)
swaps node a points at with node a.next. In the picture there are 2 middle 
node objects that are coloured more grey. You can assizee that there is a 
node before and a node after these 2 nodes that need to be swapped.

LINKED LIST SCOPE

Program sketch for the scope that uses ll as a data structure
*/


/*
public class LinkedLists<...> {
  LinkedLists() { }
  
  class Elem() {
    ...
  }
}
*/

public class LinkedLists<T extends Comparable<T>>{
  private HeadNode lhead; //list head 
  private TailNode ltail; //list tail 
  private int size;  // size of objects in the list
  
  void swap(Node a) {  }

  LinkedLists(){ 
    lhead = new HeadNode();
    ltail = new TailNode();
    size = 0;
    lhead.next = ltail;
    ltail.prev = lhead;
  }

  private interface Elem {
    int compareTo(Elem k);
    void insertOrdered(Elem k);  
  }
  
  
  private class HeadNode implements Elem  { // done
    Elem next; // Node, TailNode
    
    HeadNode() { }
    
    int compareTo(Elem other) {
      if (next != null) { // next is the first element of the list
	return next.compareTo(other.obj);
      } else { // next == null: the first element is treated as +inf (any obj < +inf) {
	return 1; // +inf is always greater
      }
    }  
    
    public void insertOrdered(Node newNode){ // head, data node
/*
7. Pay attention to how the insertOrdered is defined in the scope. It calls the recursive
insert-method in the list head. Complete the classes HeadNode, TailNode and Node with the 
recursive method insertOrdered where it's required. Make it clear in the answer what method 
belongs to what class.
*/
      assert next != null;
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
  
  private class TailNode implements Elem {
    Elem prev;
    
    TailNode() { }    
    
    int compareTo(Elem other) {
      return 1; // tail = +inf
    }
    
    public void insertOrdered(Node newNode){ // insertAfterThisNodeIfNeccesary
/*
7. Pay attention to how the insertOrdered is defined in the scope. It calls the recursive
insert-method in the list head. Complete the classes HeadNode, TailNode and Node with the 
recursive method insertOrdered where it's required. Make it clear in the answer what method 
belongs to what class.
*/
      /*
      Node b = tail;
      Node a = b.prev;
      a.next = newNode;
      newNode.next = b;
      b.prev = newNode;  
      size++;
      */
      assert false;
    }
  }
  
  private class Node implements Elem {
    Elem next;    
    T obj;
    
    Node(T object){
      this.obj = object;
    }
    
    int compareTo(Elem k){
      return obj.compareTo(k.obj);
    }
  }
  
  public int size() {return size;}
  
  //this method must not be changed
  public void insertOrdered(T newComparable){
/*
7. Pay attention to how the insertOrdered is defined in the scope. It calls the recursive
insert-method in the list head. Complete the classes HeadNode, TailNode and Node with the 
recursive method insertOrdered where it's required. Make it clear in the answer what method 
belongs to what class.
*/
    Node newNode = new Node(newComparable);
    lhead.insertOrdered(newNode);
  }
  
  public void insertTail(T newComparable) {
  /*
6. Variable previous in the list tail must always point to the 
node at the back. It must point to list head when scope is empty.
Implement method insertTail in the scope such that the new object will be inserted
at the back. This method must not be recursive.
*/    
    Node n = new Node(newComparable);
    TailNode b = last;
    Elem a = b.prev;
    n.next = b;
    a.next = n;
    b.prev = n;   
  
  }
  
  /* 8. Implement method getFromFront in the class LinkedLists such that the first node 
(not the list head) is removed and the object obj points to is returned to where it's been
called. If the list is empty the method must throw exception. */
  public T getFromFront() throws OutOfBoundsException { 
    if (empty()) {
      throw new OutOfBoundsException("The list empty");
    }
    // Ada: VHDL
    
    
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
    LinkedLists<String> llStr = new LinkedLists<String>(); // 0
    //data structure here is as in the picture
    llStr.insertTail(new String("sss")); // I
    //
    llStr.insertOrdered(new String("yyy")); // II
    llStr.insertOrdered(new String("ttt"));
    llStr.insertTail(new String("aaa"));
    llStr.insertOrdered(new String("bbb"));
    
  }
}



/* Linked list has a list head and list tail. Data structure when we create 
a new scope is in the picture.

3. Implement classes LinkedLists, HeadNode, TailNode and Node with class
variables and constructors such that scope state will be the same as in the 
picture when we have just created an empty scope, jf.main-method in task 9.
+

4. Pay attention to how the method compareTo() is defined in Node and that 
we have defined this method in this scope in order to compare nodes.
Implement method compareTo() in HeadNode.
+

5. Implement method compareTo() in TailNode.
+

6. Variable previous in the list tail must always point to the 
node at the back. It must point to list head when scope is empty.
Implement method insertTail in the scope such that the new object will be inserted
at the back. This method must not be recursive.
++

7. Pay attention to how the insertOrdered is defined in the scope. It calls the recursive
insert-method in the list head. Complete the classes HeadNode, TailNode and Node with the 
recursive method insertOrdered where it's required. Make it clear in the answer what method 
belongs to what class.
++

8. Implement method getFromFront in the class LinkedLists such that the first node 
(not the list head) is removed and the object obj points to is returned to where it's been
called. If the list is empty the method must throw exception.
++

9. Draw data structure that will be created by main-methof in the program sketch. In addition
to the objects inserted you must draw the list head and list tail.
(HOMEWORK)

10. Since list head and list tail don't need obj-variable and list tail doesn't need
next-pointer, we want to remove these variables from Elem. After that we will have a class
that can be defined as interface. Let us call it as Elem since it's an interface for all the list 
elements (nodes and list ends). Implement interface and classes HeadNode, TailNode 
and Node once more. You don't need to write methods that are not being changed.



HOMEWORK:
9.
1-8: check correctness
*/







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


  inteface Figure { void draw(); }
  ...
  Figure[] figs = ...
  figs[0] = new Square(new Point(1,1), new Point(3,3));
  figs[1] = new Cube(...);
  ...
  for (Figure fig: figs) {
    fig.draw();
  }

      public class Mainclass{
	public static void main(String[] args){
	    C c = new E(); ok
	    IC ic = c; error? cast is needed
	    IBD ibd = c; error? no connection with C
	    Object o = new B(); ok // o1, ..., o11 (hidden: i1,i2,ia1,ia2,ibd1,ibd2)
	    IA ia = (IA) o; ok -> error // ia1,ia2 not in o1, ..., o11
	    ic = (IC) o; ok? // ic: ic1,ic2 not in 
	    A a = (C) ic; error? not allowed cast for abstract object type
	    E e = new A(); ok
	    IBD ibd = (IBD) a; initialization of a is wrong so this line can't be initialized
	    a = (B) o; ok?
	    
	    c = new C(); error, not allowed for abstract classes
	}
      }
      
      
      public class Mainclass{
	public static void main(String[] args){
	    C c = new E(); ok
	    //    100
	    
	    IC ic = (IC) c; // ic=100
	    //IBD ibd = c; error? no connection with C
	    
	    //Object o = new B(); ok // o1, ..., o11 (hidden: i1,i2,ia1,ia2,ibd1,ibd2)
	    //IA ia = (IA) o; ok -> error // ia1,ia2 not in o1, ..., o11
	    
	    A o = new B(); ok // o1, ..., o11,i1,i2,ia1,ia2 (hidden: ibd1,ibd2)
	    // o=200
	    IA ia = (IA) o; ok // ia1,ia2 in o1, ..., o11,i1,i2,ia1,ia2
	    // ia=200
	    
	    IA ic = (IA) o; ok? 
	    // ic=200
	    
	    A a = (A) o; // a: o1,...,o11, // ERROR, TODO
	    // a=200
	    	    
	    a = new E(); ok // a: o1,...,o11,i1,i2,ia1,ia2
	    
	    //IBD ibd = (IBD) a; initialization of a is wrong so this line can't be initialized
	    a = (A) o; ok?
	    
	    c = new E(); error, not allowed for abstract classes
	    
	}
      }

      

      
15. Remove sentences that give errors either during compilation or execution. Write the main-method once more 
without error sentences.

Common parents:
1. E, IBD: no common
2. B, IA: A
3. C, I: A
4. B, G: A, (IBD?)
5. IC, IBD: no common

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
	// A: a, a, a, ...
	// B: b, b, b, ...
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
	// A: []
	// B: b, b, b, ...
	
	
	// what do we know about the lists a and b here?
	// some code must be put in here as exercise 11b 
      }

      
      
11a. What do we know about the lists a and b after the while-loop in the method merge? +

	- while-loop here is used for sorting/merging the most part of the elements from the both linked lists until one of the linked lists
	(or eventually both)
	become empty. After the loop the most elements from the linked lists are sorted and put into alphabetical order into the resulting scope c. 
	Now the situation when there are still _elements_ in one of the scopes needs to be handled.
	
11b. Complete method merge. +

    while (fromA != null || fromB != null) {
      if(fromA != null){
	c.insertTail(fromA);
	fromA = a.getFromFront();
      }else{
        c.insertTail(fromB);
	fromB = b.getFromFront();
      }
    }
    
    
    while (! a.empty()) c.insertTail(a.getFromFront());
    while (! b.empty()) c.insertTail(b.getFromFront());
    
    
        
      
      return c;

12. Implement main-method that uses threads to parallellize insertion sort and merging. You can but not obliged to use wait()/notify
in this task. But both insertion in the linked lists and merging must be executed in parallell.
*/
    void sortPart(LinkedList<String> partList, String[] allStrings, int indexFrom, int indexTo) {
      for (int i = indexFrom; i <= indexTo; i++) {
	parlist.insertOrdered(allStrings[i]);
      }
    }
    
    public static main(String[] args) {
      // - read lines from the file -> allStrings[]
      //   aaa ccc
      //   zzz
      final int n_strings = 390000;
      String[] strings = new String[n_strings];
      Scanner scFile;
      try {
	scFile = new Scanner(new File("file.txt"));
      } catch (Exception e) {
	e.printStackTrace();
	return;
	 }
	int i_string = 0;
      while (scFile.hasNextLine()) {
	Scanner scLine = new Scanner(scFile.nextLine());
	while (scLine.hasNext()) {
	  assert i_string < n_strings;
	  strings[i_string] = scLine.next();
	  i_string++;
	}
      }
      
      assert i_string == n_strings;
      
      
      // SORTING
      
      final int n_sort_threads = 10; // n_sort_threads
      final int n_strings_per_thread = n_strings / n_sort_threads;
      SortThread[] sortThreads = new SortThread[n_sort_threads];
      for (int i = 0; i < n_sort_threads; i++) {
      // i=0: indexFrom = 0, indexTo = 0 + 10000 - 1 = 9999
      // i=1: indexFrom = 10000 * 1 = 10000, indexTo = 10000 + 10000 - 1 = 19999
      // ...
	int indexFrom = n_strings_per_thread * i;
	int indexTo = indexFrom + n_strings_per_thread - 1;
	sortThreads[i] = new SortThread(strings, indexFrom, indexTo);
	sortThreads[i].start()
      }
      
      LinkedList<String>[] partLists = new LinkedList<String>[n_sort_threads]; // 
      for (int i = 0; i < n_sort_threads; i++) {
	sortThreads[i].join();
	partLists[i] = sortThreads[i].getPartList();
      }
      // i=9: threads 0, ..., 9 are finished
      
      // MERGING
      
      // - Level 1
      
      final int n_merge1_threads = 5;
      MergeThread[] mergeThreads1 = new MergeThreads[n_merge1_threads];
      for (int i = 0; i < n_merge1_threads; i++) {
      // i=0: (0, 1)
      // i=1: (2, 3)
	int index1 = i * 2;
	int index2 = index1 + 1;
	mergeThreads1[i] = new MergeThread(partLists[index1], partLists[index2]);
	mergeThreads1[i].start();
	}
	
      LinkedList<String>[] resLists = new LinkedList<String>[9]; // see the drawing
      for (int i = 0; i < n_sort_threads; i++) {
	mergeThreads1[i].join();
	resLists[i] = mergeThreads1[i].getResList();
      }
      
      // - Level 2
      MergeThread mergeThread2a = new MergeThread(resLists[0], resLists[1]);
      MergeThread mergeThread2b = new MergeThread(resLists[2], resLists[3]);
      
      mergeThread2a.start();
      mergeThread2b.start();
      
      mergeThread2a.join();
      mergeThread2b.join();
      
      resLists[5] = mergeThread2a.getResList();
      resLists[6] = mergeThread2b.getResList();
      
      // - Level 3
      MergeThread mergeThread3 = new MergeThread(resLists[5], resLists[6]);
      mergeThread3.start();
      mergeThread3.join();
      resLists[7] = mergeThread3.getResList();

      // - Level 4: TODO

      
      // - sort each section of allStrings[] in parallel -> partLists[0], ..., partLists[9]
      // - 
      
    }


    public static main(String[] args) {
      // - read lines from the file -> allStrings[]
      //   aaa ccc
      //   zzz
      final int n_strings = 390000;
      String[] strings = new String[n_strings];
      Scanner scFile;
      try {
	scFile = new Scanner(new File("file.txt"));
      } catch (Exception e) {
	e.printStackTrace();
	return;
	 }
	int i_string = 0;
      boolean done = false;	
      while (!done && scFile.hasNextLine()) {
	Scanner scLine = new Scanner(scFile.nextLine());
	while (!done && scLine.hasNext()) {
	  assert i_string < n_strings;
	  strings[i_string] = scLine.next();
	  i_string++;
	  if (i_string == n_strings) {
	    done = true;
	  }
	}
      }
      
      assert i_string == n_strings;
      
      
      // SORTING
      
      final int n_sort_threads = 39;
      final int n_strings_per_thread = n_strings / n_sort_threads;
      SortThread[] sortThreads = new SortThread[n_sort_threads];
      for (int i = 0; i < n_sort_threads; i++) {
      // i=0: indexFrom = 0, indexTo = 0 + 10000 - 1 = 9999
      // i=1: indexFrom = 10000 * 1 = 10000, indexTo = 10000 + 10000 - 1 = 19999
      // ...
	int indexFrom = n_strings_per_thread * i;
	int indexTo = indexFrom + n_strings_per_thread - 1;
	sortThreads[i] = new SortThread(strings, indexFrom, indexTo);
	sortThreads[i].start()
      }
      
      LinkedList<String>[] partLists = new LinkedList<String>[64]; // 
      for (int i = 0; i < n_sort_threads; i++) {
	sortThreads[i].join();
	partLists[i] = sortThreads[i].getPartList();
      }
      for (int i = n_sort_thread; i < 64; i++) {
	partLists[i] = new LinkedList<String>();
	}
      
      
      // MERGING
      
      // - Level 1: 64 -> 32
      MergeThread[] mergeThreads1 = new MergeThreads[32];
      for (int i = 0; i < mergeThreads1.length; i++) {
	int index1 = i * 2;
	int index2 = index1 + 1;
	mergeThreads1[i] = new MergeThread(partLists[index1], partLists[index2]);
	mergeThreads1[i].start();
      }
      
      for (int i = 0; i < n_sort_threads; i++) {
	mergeThreads1[i].join();
      }
      
      // - Level 2: 32 -> 16
      MergeThread[] mergeThreads2 = new MergeThreads[16];
      for (int i = 0; i < mergeThreads2.length; i++) {
	int index1 = i * 2;
	int index2 = index1 + 1;
	mergeThreads2[i] = new MergeThread(mergeThreads1[index1].getResList(), 
	                                   mergeThreads1[index2].getResList());
	mergeThreads2[i].start();
      }
      
      for (int i = 0; i < n_sort_threads; i++) {
	mergeThreads2[i].join();
      }
      
      // - Level 3: 16 -> 8
      MergeThread[] mergeThreads3 = new MergeThreads[16];
      for (int i = 0; i < mergeThreads3.length; i++) {
	int index1 = i * 2;
	int index2 = index1 + 1;
	mergeThreads3[i] = new MergeThread(mergeThreads2[index1].getResList(), 
	                                   mergeThreads2[index2].getResList());
	mergeThreads3[i].start();
      }
      
      for (int i = 0; i < n_sort_threads; i++) {
	mergeThreads3[i].join();
      }
      
      // - Level 4: 8 -> 4
      MergeThread[] mergeThreads4 = new MergeThreads[4];
      for (int i = 0; i < mergeThreads4.length; i++) {
	int index1 = i * 2;
	int index2 = index1 + 1;
	mergeThreads4[i] = new MergeThread(mergeThreads3[index1].getResList(), 
	                                   mergeThreads3[index2].getResList());
	mergeThreads4[i].start();
      }
      
      for (int i = 0; i < n_sort_threads; i++) {
	mergeThreads4[i].join();
      }
      
      // - Level 5: 4 -> 2
      // ...
      
      // - Level 6: 2 -> 1
      // ...
      
      mergeThreads6[0].getResList().print();
      
    }
    
    // TODO:
    // - implement SortThread, MergeThread
    // - compile and run the program for 39 words (change the constants)
    // - compile and run the program for 78 words (change the constants)
    // - compile and run the program for 39000 words (change the constants)
    // - try to implement the code for levels 1-6 usign a for/while loop
    //   (mergeThreadsPrev[32] -(data)-> mergeThreads[32] -(copy)-> mergeThreadsPrev[32])
    
    // go through other tasks in Exam 2015 and prepare questions


    public static void main(Sring[] args){
	String fileName = "manywords.txt";
	String[] words = new String[390000];
	words.readWordsFromFile(fileName);
	
	private final int numThreads = 39;
	final int numElements = 10000; //per insert
	
	private CountDownLatch cdlatch = new CountDownLatch(numThreads);
	LinkedLists<String> wordsList = new LinkedLists<String>();
	LinkedLists<String> resultingList = new LinkedLists<String>();
	
	
	for(int i=0; i<numThreads; i++){ //how to merge simultaneously?
	  new InsertThread(wordsList, numElements, words, i).start();
	  
	}
	//this must happen inside InsertThread..
	/* for(int i=0; i<numElements-1; i++){ 
	    wordsList.insertOrdered(words[i]);
	  }
	*/
	
	
	
	
	
	
	
    }