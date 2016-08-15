import java.io.*;
import java.util.*;

    // TODO:
    // - implement SortThread, MergeThread
    // - compile and run the program for 39 words (change the constants)
    // - compile and run the program for 78 words (change the constants)
    // - compile and run the program for 39000 words (change the constants)
    // - try to implement the code for levels 1-6 usign a for/while loop
    //   (mergeThreadsPrev[32] -(data)-> mergeThreads[32] -(copy)-> mergeThreadsPrev[32])
    
    // go through other tasks in Exam 2015 and prepare questions


// MAIN VERSION
class Exam_2016_sorting {

    public static void main(String[] args) {
      // - read lines from the file -> allStrings[]
      //   aaa ccc
      //   zzz
      
      final int n_strings = 39000; 
      String[] strings = new String[n_strings];
      Scanner scFile;
      try {
	scFile = new Scanner(new File("file.txt"));
      
      } catch (Exception e) {
	e.printStackTrace();
	return;
      }
      
      int i_string = 0; //i-th word 
      boolean done = false;
      
      while (!done && scFile.hasNextLine()) { //until eof70
	Scanner scLine = new Scanner(scFile.nextLine());
	
	while (!done && scLine.hasNext()) {
	  assert i_string < n_strings; //accept only 39 000 words
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
      SortThread[] sortThreads = new SortThread[n_sort_threads]; //array to keep all threads 
      
      for (int i = 0; i < n_sort_threads; i++) {
      // i=0: indexFrom = 0, indexTo = 0 + 10000 - 1 = 9999
      // i=1: indexFrom = 10000 * 1 = 10000, indexTo = 10000 + 10000 - 1 = 19999
      // ...
	int indexFrom = n_strings_per_thread * i;
	int indexTo = indexFrom + n_strings_per_thread - 1;
	sortThreads[i] = new SortThread(strings, indexFrom, indexTo);
	sortThreads[i].start();
      }
      
      //LinkedList<String>[] partLists = (LinkedList[]) new Object[64];
      Object[] partLists = new Object[64];
      //LinkedList<String>[] partLists = new LinkedList<String>[64]; 
      // 64 because it's the nearest power of 2 so that each time there will be 2 lists to merge
      
      for (int i = 0; i < n_sort_threads; i++) {
	try {
	  sortThreads[i].join(); //wait for all the threads to finish sorting
	} catch (InterruptedException e) {
	  e.printStackTrace();
	}
	partLists[i] = (Object) sortThreads[i].getPartList(); //fill the list with the sorted sublists to merge them afterwards
      }
      
      for (int i = n_sort_threads; i < 64; i++) {
	partLists[i] = (Object) new LinkedList<String>();
      }
      
      
      // PRINTING
             
      for (int i = 0; i < n_sort_threads; i++) {
	((LinkedList<String>) partLists[i]).print();
	//partLists[i] = (Object) sortThreads[i].getPartList(); //fill the list with the sorted sublists to merge them afterwards
      }
      
      
      // MERGING IN A LOOP
      
	 final int powerOfTwoInitial = 32; // # of mergers
	 int powerOfTwo = powerOfTwoInitial; // # of mergers
	 MergeThread[] prevMergeThread = new MergeThread[powerOfTwo];
	 
	  while(powerOfTwo > 0) { // 16>0, 8>0, 4>0, 2>0, 1>0, 0>0  // mergers > 0
	    MergeThread[] mergeThreads = new MergeThread[powerOfTwo];
	    
	    for(int i = 0; i<mergeThreads.length; i++){
	      int index1 = i * 2;
	      int index2 = index1 + 1;      
	  
	      LinkedList<String> source1, source2;
	      if(powerOfTwo == powerOfTwoInitial){      
		source1 = (LinkedList<String>) partLists[index1];
		source2 = (LinkedList<String>) partLists[index2];
	      }else{
		//for(int i = 0; i < mergeThreads.length; i++){
		source1 = prevMergeThread[index1].getResList();
		source2 = prevMergeThread[index2].getResList();
		 
		//}
	      }   
	      
	      
	      System.out.println("Merging (" + i + "):");
	      //source1.print();
	      //source2.print();
	      
	      mergeThreads[i] = new MergeThread(source1, source2);
	      mergeThreads[i].start();
	      
	    }
	    
	    for (int i = 0; i < mergeThreads.length; i++) {
	      try {
		mergeThreads[i].join();
		
	      System.out.println("Result (" + i + "):");
		//mergeThreads[i].getResList().print();
		
	      } catch (InterruptedException e) {
		e.printStackTrace();
	      }
	    
	    }
	    
	    /*
	    for (MergeThread m : mergeThreads) {
	      m.join();
	    }
	    */
		
	    for(int j = 0; j < mergeThreads.length; j++){
	      prevMergeThread[j] = mergeThreads[j];
	    }
	    
	    powerOfTwo /= 2;
	    
	  }
	  
	  prevMergeThread[0].getResList().print();
      
      
      
      
      
      
      //REGULAR MERGING
      // - Level 1: 64 -> 32
     /* MergeThread[] mergeThreads1 = new MergeThread[32];
      
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
      MergeThread[] mergeThreads2 = new MergeThread[16];
      
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
      MergeThread[] mergeThreads3 = new MergeThread[8];
      
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
      MergeThread[] mergeThreads4 = new MergeThread[4];
      
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
      MergeThread[] mergeThreads5 = new MergeThread[2];
      
      for (int i = 0; i < mergeThreads5.length; i++) {
	int index1 = i * 2;
	int index2 = index1 + 1;
	mergeThreads5[i] = new MergeThread(mergeThreads4[index1].getResList(), 
	                                   mergeThreads4[index2].getResList());
	mergeThreads5[i].start();
      }
      
      for (int i = 0; i < n_sort_threads; i++) {
	mergeThreads5[i].join();
      }
      
      
      
      // - Level 6: 2 -> 1
      // ...
      MergeThread[] mergeThreads6 = new MergeThread[1];
      
      for (int i = 0; i < mergeThreads6.length; i++) {
	int index1 = i * 2;
	int index2 = index1 + 1;
	mergeThreads6[i] = new MergeThread(mergeThreads5[index1].getResList(), 
	                                   mergeThreads5[index2].getResList());
	mergeThreads6[i].start();
      }
      
      for (int i = 0; i < n_sort_threads; i++) {
	mergeThreads6[i].join();
//       }
      
      mergeThreads6[0].getResList().print();*/
      
    } // end main method
} //end main class



class MergeThread extends Thread {
	private LinkedList<String> list1, list2, resList;


	public MergeThread(LinkedList<String> list1, LinkedList<String> list2) {
	  this.list1 = list1;
	  this.list2 = list2;
	}
 //MergeThread t = this;
	public void run() {
	  resList = merge(list1, list2);
	}
	
    LinkedList<String> merge(LinkedList<String> a, LinkedList<String> b){
	//merge ordered lists a and b together to the new ordered list c
	
	LinkedList<String> c = new LinkedList<String>();
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
	  //a.print();
	  //b.print();
	}
	
	//*
	while (fromA != null || fromB != null) {
      
	  if(fromA != null){
	    c.insertTail(fromA);
	    fromA = a.getFromFront();
	  
	  }else{
	    c.insertTail(fromB);
	    fromB = b.getFromFront();
	  }
	} 
	//*/
	
	//a.print();
	//b.print();
	//while (! a.empty()) c.insertTail(a.getFromFront());
	//while (! b.empty()) c.insertTail(b.getFromFront());
	
	return c;
	
      }
	
	public LinkedList<String> getResList() {
	  return resList;
	}
	
}

class SortThread extends Thread {
	private String[] values;
	private LinkedList<String> partList;
	private int start;
	private int stop;

	public SortThread(String[] values, int start, int stop) {
	  this.values = values;
	  this.start = start;
	  this.stop = stop;
	}

	public void run() {
	  sort(values, start, stop);
	}
	
	void sort(String[] values, int start, int stop) {
	  partList = new LinkedList<String>();
	  for (int i = start; i <= stop; i++) {
	    partList.insertOrdered(values[i]);
	  }
	}
	
	public LinkedList<String> getPartList() {
	  return partList;
	}
}




class LinkedList<T extends Comparable<T>> {
  private ListHead lhead; //list head 
  private ListTail ltail; //list tail 
  private int size;  // sum of objects in the list


  LinkedList(){ 
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
	void insertOrdered(AbstrNode k) {
	  assert next != null; //but what if there is an empty list and we want to insert the first element?
	  
	  if (next.compareTo(k) >= 0) {
	      // next >= newNode
	      // 1) next = tail = +inf
	      k.next = next;
	      if (next.next == null) { // instanceof LinkedList<T>.ListTail) {
		((ListTail) next).prev = k;
	      }
	      next = k;	      
	      size++; 
	  } else {
	    next.insertOrdered(k);
	  } 
	}
      }
    
    
    
  
      private class ListHead extends AbstrNode {
	
	ListHead() {
	  super(null);      
	}
	
	
	int compareTo(AbstrNode k){
	  
	  assert false;
	  return 1;
	}	
      }

      
      
      
	private class ListTail extends AbstrNode {
	  AbstrNode prev;
	  
	  ListTail() {
	    super(null);
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
	  
	// Node() {

	  //}
	  
	  Node(T t){
	    super(t);
	  }
	  
	  
	  int compareTo(AbstrNode k){
	    return obj.compareTo(k.obj); // >0: obj > k.obj, =0: =, <0: <
	  }     
	}



  public int sum() {
    return size;
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
    // a b
    // a new b
    
    newNode.next = b;
    a.next = newNode;
    b.prev = newNode; 
    //we need to increase the size of the scope here, right? since there is a new object we are inserting 
    size++;
    
    System.out.println("After inserting " + newComparable + ": ");
    //print();
  }
  
  public T getFromFront() throws IndexOutOfBoundsException { 
    if (empty()) {
      //throw new IndexOutOfBoundsException("The list is empty");
      return null;
    }
    
   // print();
    AbstrNode a = lhead;
    AbstrNode b = a.next;
    AbstrNode c = b.next;
    
    a.next = c;
    size--;
    
    return b.obj; 
  }
  
  public boolean empty() { 
    return size == 0;
  }
  
  public void print() {
    AbstrNode runner = lhead;
    
    System.out.print(size + ": ");
    while(runner.next != null){
      System.out.print(runner.obj + " ");
      runner = runner.next;
    }
    System.out.println();
  }

}

// - LinkedList from Exam 2014
// - LinkedList from Exam 2013

// ...

