abstract class AbstractWorker extends Thread {
  int num;
  String smallest;
  
  AbstractWorker(int n) {
    num = n;
    shortest = null;
  }
  
  public abstract void run();
  
  protected void report() {
    System.out.println(num + ". " + smallest + "\n");
  }
  
  protected abstract void outputWord();
  
  protected void decideOnWord(String word) {
    if (smallest == null || smallest.compareTo(word) > 0) {
	  String exSmallest = smallest;
	  smallest = word;
	  
	  if (exSmallest != null) {
	    outputWord(exSmallest);
	  }
    } else {
	  outputWord(word);
    }
  }
}

class FirstWorker extends AbstractWorker {
  FileInputStream inputFile;
  WordBuffer outputBuffer;
  
  FirstWorker(int n, String inputFileName, WordBuffer ob) {
    super(n);
    
    try {
      inputFile = new FileInputStream(inputFileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    outputBuffer = ob;
  }
  
  public void run() {
    try {
	InputStreamReader isr = new InputStreamReader(inputFile);
	BufferedReader br = new BufferedReader(isr);
	String word;
	
	while ((word = br.readLine()) != null) {
	  decideOnWord(word);
	}    
	report();
    }
  
    protected void outputWord(String word) {
      ob.push(word);
    }
  }

class InnerWorker extends AbstractWorker {
  WordBuf inputBuffer;
  WordBuf outputBuffer;
  
  InnerWorker(int n, WordBuffer ib, WordBuffer ob) {
    super(n);
    inputBuffer = ib;
    outputBuffer = ob;
  }
  
  public void run() {
    String word;
    
    while ((word = ib.pop()) != null) {
      decideOnWord(word);
    }    
    report();
  }  
  
  protected void outputWord(String word) {
    ob.push(word);
  }
}



class LastWorker extends AbstractWorker {
  WordBuf inputBuffer;
  FileOutputStream outputFile;
  
  LastWorker(int n, WordBuffer ib, String outputFileName) {
    super(n);
    
    try {
      outputFile = new FileOutputStream(outputFileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
    outputBuffer = ob;
  }
  
  public void run() { // TODO: change
    try {
	InputStreamReader isr = new InputStreamReader(inputFile);
	BufferedReader br = new BufferedReader(isr);
	String word;
	
	while ((word = br.readLine()) != null) {
	  decideOnWord(word);
	}    
	report();
    // close the file
    }  
  
    protected void outputWord(String word) { // TODO: write to outputFile
      ob.push(word);
    }
  }

class WordBuffer {
  ArrayList<String> list;
  int capacity;
  
  WordBuffer(int c) {
    capacity = c;
    list = new ArrayList<String>(capacity);
  }
   
  public String pop() {
    while (isEmpty) {
	synchronized(list) {
	  isEmpty = (list.size() == 0);
	}
	
	if (isEmpty) {
	  wait();
	}
    }
	
    String s;
    
    synchronized(list) {
      s = list[0];
      list.remove(0);
    }
    
    notifyAll(); // push will be awaken if necessary
    return s;
  }
  
  public void push(String word) {
      // TODO   
    if (list.size() < capacity) {
	list.add(word);
	notifyAll(); // pop  will be awaken if necessary	
    } else {
      
    }
  }
}

main(args) {
  int k = 3;
  
  WordBuffer[] bufs = new WordBuffer[k-1];
  
  for (int i = 0; i < bufs.length; i++) {
  }
  

  AbstractWorker[] workers = new AbstractWorker[k];
  
  for (int i = 0; i < workers.length; i++) {
    int num = i + 1;
    AbstractWorker w;
    if (i == 0) {    
      w =  new FirstWorker(num, ifn, bufs[0]
    } else if (i == workers.length - 1) {
      w= new Lst...
    } else {
      w = new InnerWorker(num, bufs[i-1], bufs[i]);
    }
    w.start();
    workers[i] = w;
  }
  
  for (AbstractWorker w: workers) { 
    w.join();
  }      
}