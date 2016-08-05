    // TODO:
    // - implement SortThread, MergeThread
    // - compile and run the program for 39 words (change the constants)
    // - compile and run the program for 78 words (change the constants)
    // - compile and run the program for 39000 words (change the constants)
    // - try to implement the code for levels 1-6 usign a for/while loop
    //   (mergeThreadsPrev[32] -(data)-> mergeThreads[32] -(copy)-> mergeThreadsPrev[32])
    
    // go through other tasks in Exam 2015 and prepare questions


// MAIN VERSION
class Exam-2016-sorting {

    public static void main(String[] args) {
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
      
      LinkedList<String>[] partLists = new LinkedList<String>[64]; // 64 because it's the nearest power of 2 so that each time there will be 2 lists to merge
      
      for (int i = 0; i < n_sort_threads; i++) {
	sortThreads[i].join();
	partLists[i] = sortThreads[i].getPartList();
      }
      
      for (int i = n_sort_threads; i < 64; i++) {
	partLists[i] = new LinkedList<String>();
      }
      
      
      // MERGING IN A LOOP
      
	 int powerOfTwo = 32;
	 MergeThread[] prevMergeThread = new MergeThread[powerOfTwo];
	 
	  while(powerOfTwo/2 > 0){
	    MergeThread[] mergeThreads = new MergeThread[powerOfTwo];
	    
	    for(int i = 0; i<mergeThreads.length; i++){
	      int index1 = i * 2;
	      int index2 = index1 + 1;
	      
	      if(powerOfTwo == 32){
	      
		mergeThreads[i] = new MergeThread(partLists[index1], partLists[index2]);
		mergeThreads[i].start();
		
		
	      }else{
	      
		for(int i = 0; i < mergeThreads.length; i++){
		  mergeThreads[i] = new MergeThread(prevMergeThread[index1].getResList(), prevMergeThread[index2].getResList());
		  mergeThreads[i].start();
		}
	      }      
	    }
	    
	    for (int i = 0; i < n_sort_threads; i++) {
	      mergeThreads[i].join();
	    }
		
	    for(int j = 0; j < prevMergeThread.length; j++){
	      prevMergeThread[j] = mergeThreads[j];
	    }
	    
	    powerOfTwo /= 2;
	    
	  }
	  
      
      
      
      
      
      
      //REGULAR MERGING
      // - Level 1: 64 -> 32
      MergeThread[] mergeThreads1 = new MergeThread[32];
      
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
      }
      
      mergeThreads6[0].getResList().print();
      
    }
}

class MergeThread extends Thread {
	private LinkedList<String> list1 = new LinkedList<String>();
	private LinkedList<String> list2 = new LinkedList<String>();


	public MergeThread(LinkedList<String> l1, LinkedList<String> l2) {
	  this.list1 = l1;
	  this.list2 = l2;
	}

	public void run() {
	  merge(list1, list2);
	}
}

class SortThread extends Thread {
	private String[] values;
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
	
	public LinkedList<String> getPartList() {
	
	}
}