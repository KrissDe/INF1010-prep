import java.util.*;
import java.lang.*;
import java.io.*;

class SectionBubbleSorter implements Runnable //class implements interface Runnable because threads will be used
{
	public int[] a; //array of integers that holds data to be sorted
	public int from, to; //2 indexes that show the starting point and the end point of the array section that is to be sorted
	
	SectionBubbleSorter(int[] a, int from, int to) { //constructor of the class SectionBubbleSorter that takes array of integers, start and end point of the section as arguments
	  this.a = a; //initializes variables for current section
	  this.from = from;
	  this.to = to;
	}
	
	public void run() { //method to initialize threads
		bubbleSort(a, from, to); //method for bubbleSort is executed with threads that's why it must be launched via run()
	}
		
	public static void bubbleSort(int[] a, int from, int to) //method for bubble sorting that takes array of integers, start and end point of the section as arguments
	{
		//boolean done = false;
		boolean swapped = true; //boolean variable that tells us if all the values have been swapped. We assume that it is true at the start
		//while (! done) {
		while (swapped) { //while values are swapped/ while true
			// ! done == true
			// done == false
		  //done = true;
		  swapped = false; //mark as not swapped in order to perform checking
		  for (int i = from; i < to; i++) { //while iterating the section
		    if (a[i] > a[i+1]) { // strcmp, check if this value is bigger than next value. If yes, then perform following:
		      System.out.println("a["+i+"] = "+a[i]+" <-> a["+(i+1)+"] = "+a[i+1]); //print the values to be swapped
		      int t = a[i]; //temporary variable that will hold the current value
		      a[i] = a[i+1]; //value at current place will be changed to the value which was at the place after current
		      a[i+1] = t; //value at the place after current will now hold value that was hold in temporary variable
		      swapped = true; //let the program know that the values have been swapped
		      // done = false;
		    } //end if-condition. Do nothing in else
		  }//end of section iteration
		  
		}//end of while
	}
	
}

public class bubblesort_threaded
{
	public static void print_array(int[] a) //method that will print the array of data - takes array of integers as an argument
	{
	  for (int i = 0; i < a.length; i++) { //iterating the array of data
	    System.out.println("a["+i+"] = "+a[i]); //print the value at the current place
	  }
	  System.out.println(); //newline
	}
	
		
	public static void main (String[] args) throws java.lang.Exception //main method with exception handling
	{
		// your code goes here
		/*
		The algorithm begins at the start of the data set and comparing the first two elements.
		  If the first is greater than the second, they switch places.
		  This is done with all pairs of consecutive elements throughout the dataset.
		*/
		
		int n_elems = 7; //number of elements in the array
		int n_sections = 3; //number of sections that the array will be splitted into for sorting
		
		int[] a = new int[n_elems]; //allocate an array with place to desired amount of element - n_elems
		a[0] = 30;//fill array with data
		a[1] = 1;
		a[2] = 2;
		a[3] = 4;
		a[4] = 6;
		a[5] = 5;
		a[6] = 7;
		
		int n_elems_per_section = n_elems / n_sections; // = 2 divide total amount of elements to amount of sections to find out how many elements each section will hold
		// 0, 1
		// 2, 3
		// 4, 6
		
		SectionBubbleSorter[] sorters = new SectionBubbleSorter[n_sections]; //create an array that will hold objects of type SectionBubbleSorter
		Thread[] threads = new Thread[n_sections];//create an arrray that will hold objects of type Thread
		
		int from = 0; //start index
		
		for (int i = 0; i < n_sections; i++) { //iterating all the sections
		  // i=0: from=0, to=1
		  // i=1: from=2, to=3
		  // i=2: from=4, to=5->6
		  //      from=7
		  
		  int to; //end index
		  
		  if (i == n_sections - 1) { //if current section is the last section
		    to = n_elems - 1; //-1 because starting from 0. let the end point include all the elements that are left (in case of odd amount of elements in the array)
		  
		  } else { //if not the last section
		    to = from + n_elems_per_section - 1; //end index is the amount of elements per section (-1 because starting from 0) 
		  }
		  
		  sorters[i] = new SectionBubbleSorter(a, from, to); //initialize new object of type SectionBubbleSorter  
		  
		  from = to + 1;  //the next index after end point for this section is the start point for the new section
		}
		
		
		boolean[] more = new boolean[n_elems]; //array signifying us if there is more sorting needed
		
		for (int i = 0; i < n_sections; i++) { //iterating sections
		  more[i] = true; //we assume that all sections need to be sorted in the beginning
		}
		  
		boolean some_more = true; //flag to check if there is some more iteration through sections needed for checking - assume it is needed in the beginning
		
		while (some_more) { //while more iterations needed/is true
		  System.out.println("another iteration"); //print notification
		  
		  for (int i = 0; i < n_sections; i++) { //iterating sections
		  
		    if (more[i]) { //if current section needs to be sorted
		      // bubbleSort(a, froms[i], tos[i]);
		      threads[i] = new Thread(sorters[i]); //we allocate a new thread for this section
		      threads[i].start(); //and start the thread for execution
		      
		      more[i] = false; //now this section was sorted and no more sorting needed -> therefore mark it false in our boolean array
		    }
		  }
		  
		  /*
		  threads[0].join();
		  // t0 is fin.
		  threads[1].join();
		  // t1 is fin.
		  threads[2].join();
		  // t2 is fin.
		  
		  // t0, t1, t2 are fin.
		  */
		  
		  for (int i = 0; i < n_sections; i++) { //iterate sections
		    threads[i].join(); //and wait for every thread-section to finish execution
		  }
		  		  
		  print_array(a); //print the array at this moment of time
		  
		  some_more = false; //no more needs to be done at this moment of time   
		  
		  for (int i = 0; i < n_sections - 1; i++) { //iterating sections
		  
		    int best_of_worst_index = sorters[i].to; //last element in current section
		    int worst_of_best_index = sorters[i+1].from; // best_of_worst_index + 1 , first element in next section
		    
		    System.out.println(best_of_worst_index + ", " + worst_of_best_index); //print these 2 elements to screen
		    
		    if (a[best_of_worst_index] > a[worst_of_best_index]) { //if the current element is bigger than the first element of the next section -> must be swapped
		      int t = a[best_of_worst_index]; //create temporary variable to hold values of the last element of the current section
		      a[best_of_worst_index] = a[worst_of_best_index]; //make the last place of the current section to have the value of the first element of the next section
		      a[worst_of_best_index] = t; //and put value from the temporary variable into first place of the next section
		      
		      more[i] = true; //section must again be sorted
		      more[i+1] = true; //and next section, too
		      //done = false;
		      some_more = true; //some more iterations need to be done
		    }
		  }
		  
		  print_array(a); //print the array at this moment of time
		  
		}
			
		
	}
}