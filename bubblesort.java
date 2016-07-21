/* package whatever; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
class Ideone
{
	public static void bubbleSort(int[] a, int from, int to)
	{
		//boolean done = false;
		boolean swapped = true;
		//while (! done) {
		while (swapped) {
			// ! done == true
			// done == false
		  //done = true;
		  swapped = false;
		  for (int i = 0; i < a.length - 1; i++) {
		    if (a[i] > a[i+1]) { // strcmp
		      //System.out.println("a["+i+"] = "+a[i]+" <-> a["+(i+1)+"] = "+a[i+1]);
		      int t = a[i];
		      a[i] = a[i+1];
		      a[i+1] = t;
		      swapped = true;
		      // done = false;
		    }
		  }
		  
		}
	}
	
	public static void print_array(int[] a) 
	{
	  for (int i = 0; i < a.length; i++) {
	    System.out.println("a["+i+"] = "+a[i]);
	  }
	  System.out.println();
	}
	
		
	public static void main (String[] args) throws java.lang.Exception
	{
		// your code goes here
		/*
		The algorithm begins at the start of the data set and comparing the first two elements.
		  If the first is greater than the second, they switch places.
		  This is done with all pairs of consecutive elements throughout the dataset.
		*/
		
		int n_elems = 7;
		int n_sections = 3;
		
		int[] a = new int[n_elems];
		a[0] = 3;
		a[1] = 1;
		a[2] = 2;
		a[3] = 4;
		a[4] = 6;
		a[5] = 5;
		a[6] = 7;
		
		int n_elems_per_section = n_elems / n_sections; // = 2
		// 0, 1
		// 2, 3
		// 4, 6
		
		int[] froms = new int[n_elems];
		int[] tos = new int[n_elems];
		
		int from = 0;
		for (int i = 0; i < n_sections; i++) {
		  // i=0: from=0, to=1
		  // i=1: from=2, to=3
		  // i=2: from=4, to=5->6
		  //      from=7
		  int to;
		  if (i == n_sections - 1) {
		    to = n_elems - 1;
		  } else {
		    to = from + n_elems_per_section - 1;		  
		  }
		  froms[i] = from;
		  tos[i] = to;
		  from = to + 1;
		}
		
		
		boolean[] more = new boolean[n_elems];
		for (int i = 0; i < n_sections; i++) {
		  more[i] = true;
		}		
		  
		//boolean done = false;
		boolean some_more = true;
		// while (more[0] == false || ... || more[n_sections-1] == false) {
		//while (! done) {
		while (some_more) {
		  //done = true;
		  
		  for (int i = 0; i < n_sections; i++) {
		    if (more[i]) {
		      bubbleSort(a, froms[i], tos[i]);
		      more[i] = false;
		    }
		  }
		  
		  print_array(a);
		  
		  some_more = false;		  
		  
		  for (int i = 0; i < n_sections - 1; i++) {
		    int best_of_worst_index = tos[i];
		    int worst_of_best_index = froms[i+1]; // best_of_worst_index + 1
		    if (a[best_of_worst_index] > a[worst_of_best_index]) {
		      int t = a[best_of_worst_index];
		      a[best_of_worst_index] = a[worst_of_best_index];
		      a[worst_of_best_index] = t;
		      
		      more[i] = true;
		      more[i+1] = true;
		      //done = false;
		      some_more = true;
		    }
		  }
		  
		  print_array(a);
		  
		}
			
		
	}
}