import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Oppgave5 {
	public static void main(String[] args) {
		new Oppgave();
	}
}

class Oppgave {

	private final int numThreads = 32;
	private final int numElements = 1024;

	private Monitor monitor;

	// Oppgave d
	private CountDownLatch workingLatch;

	Oppgave() {
		// Testdata
		Random r = new Random();
		char[] letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		String[] values = new String[numElements];
		for (int i = 0; i < values.length; i++) {
			String word = "";
			for (int j = 0; j < 4; j++) {
				word += letters[r.nextInt(letters.length)];
			}
			values[i] = word;
		}

		workingLatch = new CountDownLatch(1);
		monitor = new Monitor(workingLatch, numThreads);

		// Oppgave b
		int start = 0;
		int per = values.length / numThreads;
		int stop = per;
		for (int i = 0; i < numThreads; i++) {
			new SortThread(values, start, stop, i).start();
			start = stop;
			stop = stop + per;
			if (i == numThreads - 2) { // prep for the last i = numThreads - 1
				stop = values.length - 1;
			}
		}

		try {
			workingLatch.await(); // wait for workingLatch.getCount() to become 0
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Print and check results
		String word_prev = null;
		for (String word : values) {
			System.out.println(word);
			if (word_prev != null && word_prev.compareTo(word) > 0) {
				System.out.println("error: " + word_prev + " > " + word);
			}
		}
	}

	// Oppgave b
	class SortThread extends Thread {
		private String[] values;
		private int start;
		private int stop;
		private int threadID;

		public SortThread(String[] values, int start, int stop, int threadID) {
			this.values = values;
			this.start = start;
			this.stop = stop;
			this.threadID = threadID;
		}

		public void run() {
			sort(values, start, stop, threadID);
		}
	}

	// Oppgave a
	public void sort(String[] a, int from, int to, int threadID) {
		while (true) {
			System.out.println("thread " + threadID + ": new interation");
			boolean swapped = false;
			for (int i = from; i < to; i++) {
				if (a[i].compareTo(a[i + 1]) > 0) {
					if (i == from || i == to - 1) {
						// protected (synchronized) swap
						monitor.swap(a, i, i + 1);
					} else {
						// unprotected swap
						String tmp = a[i];
						a[i] = a[i + 1];
						a[i + 1] = tmp;
					}
					swapped = true;
				}
			}

			if (! swapped) {
				System.out.println("thread " + threadID + ": preliminary finished");
				if (monitor.reportPreFinishedThread()) {
					break;
				}
			}
		}
		System.out.println("thread " + threadID + ": done");
	}
}

class Monitor {
	private int numThreads;
	private int numPreFinishedThreads = 0;
	private CountDownLatch workingLatch;
	
	Monitor(CountDownLatch workingLatch, int numThreads) {
		this.workingLatch = workingLatch;
		this.numThreads = numThreads;
	}

	// returns true iff all threads are done
	private synchronized boolean changePreFinishedThreads(boolean isIncrement) {
		if (isIncrement) {
			numPreFinishedThreads++;
		} else {
			numPreFinishedThreads = 0;
		}
		return numPreFinishedThreads == numThreads;
	}

	// returns true iff all threads are done
	public synchronized boolean reportPreFinishedThread() {
		// should be synchronized, because can be called from different threads
		// (numPreFinishedThreads is being protected)
		try {
			if (! changePreFinishedThreads(true)) {
				// wait() for someone to notifyAll():
				// - in swap() (to continue work),
				// - or in reportPreFinishedThread() (all is done).
				wait();
				// After that the method may return either true (if workingLatch
				// is countDown()'d by someone else) or false (if there is a swap()).
			} else { // numPreFinishedThreads == numThreads
				workingLatch.countDown();
				notifyAll();
				// After that the method will always return true
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return workingLatch.getCount() == 0;
	}

	// Oppgave c
	public synchronized void swap(String[] a, int i, int j) {
		// should be synchronized, because can be called from different threads
		// (a[i], a[j] are being protected)
		String tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;

		changePreFinishedThreads(false); // state that no one is done actually
		notifyAll(); // make every preliminary finished thread awake
	}
}
