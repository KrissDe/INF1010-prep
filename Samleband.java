class Samleband {

  public static void main(String[] args) throws Exception{
    Scanner scan = new Scanner(System.in);
    int wordCount;
    String inputFileName = "";
    String outputFileName = "";
    
    try{
      wordCount = scan.nextInt();
      inputFileName = scan.next();
      outputFileName = scan.next();
    }catch(Exception e){
      System.out.println("Usage: java Samleband wordsNr inputFileName outputFileName");
    }
    scan.close();
    
    private final int numThreads = wordCount;
    private CountDownLatch workingLatch;
    
    FirstWorkerThread fw = new FirstWorkerThread(1);
    workingLatch = new CountDownLatch(numThreads);
  }
}

abstract class WorkerThread extends Thread {
  protected int workerNr;
  
  public WorkerThread(int nr) {
    workerNr = nr;
  }
  
  
}

class FirstWorkerThread extends Worker {

}

class InnerWorkerThread extends Worker {

}

class LastWorkerThread extends Worker {

}

class WordsBuffer {
  protected ArrayList<String> words = new ArrayList<String>();
  
  
  
}