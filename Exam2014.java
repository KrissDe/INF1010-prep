// Task 3. Remove zeros

class Exam2014 {
  public static void main(String[] args) {
    int[] currentTable = new int[] {0,1,7,43,0,8,0,0,0};
    int[] newTable = removeZeros(currentTable);
    
    for(int a: newTable){
      System.out.print(a + " ");
    }
    
    System.out.println();
    
  }
  
  public static int[] removeZeros (int[] theTable) {
    
    int[] removeFrontZeros = new int[theTable.length];
    
    int i = 0;
    int j = 0;
    
    while(theTable[i] == 0){
	i++;
    }

    for(int k=i; k<theTable.length; k++) {
      removeFrontZeros[j] = theTable[k];
      j++;
    }

   int a = removeFrontZeros.length-1;

    while(removeFrontZeros[a] == 0) {
      a--;
    }
    
    int[] removeBackZeros = new int[a+1];
  
    for(int b=0; b<=a; b++) {
      removeBackZeros[b] = removeFrontZeros[b];
    }
    
    return removeBackZeros;    
  }
}


// Task 2A. Merge with a Node-class

class MergeInt {
  public static IntNode mergeLists(IntNode n1, IntNode n2) {
    if(n1 == null && n2 == null) return null;
    if(n1 == null && n2 != null) return n2;
    if(n1 != null && n2 == null) return n1;
    
    if(n1.data <= n2.data) {
      n1.next = mergeLists(n1.next, n2);
      
    }else{
      IntNode helpNode = n2;
      n2 = n2.next;
      helpNode.next = n1;
      n1 = helpNode;
      n1.next = mergeLists(n1.next, n2);
    }
    
    return n1;
  }
}

class IntNode {
  IntNode next;
  int data;
}


// Task 2B. Merge with interface

class Merging {
  public static grNode mergeLists(GrNode gr1, GrNode gr2) {
    if(gr1 == null && gr2 == null) return null;
    if(gr1 == null && gr2 != null) return gr2;
    if(gr1 != null && gr2 == null) return gr1;
    
    if(gr1.compareTo(gr2) <= 0) {
      gr1.setNext(mergeLists(gr1.getNext(), gr2));
      
    }else{
      GrNode helpNode = gr2;
      gr2 = gr2.getNext();
      helpNode.setNext(gr1);
      gr1 = helpNode;
      gr1.setNext(mergeLists(gr1.getNext(), gr2));
    }
    
    return gr1;
  }
}

interface GrNode extends Comparable<GrNode> {
  GrNode getNext();
  void setNext(GrNode gn);
}


// Task 3. Sorting workers
// See Samleband.java