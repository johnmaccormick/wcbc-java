package wcbc;

import java.util.List;

public class WaitAllTerminated implements Runnable {

	private List<Thread> threads;
	private NonDetSolution nds;

	public WaitAllTerminated(List<Thread> threads, NonDetSolution nds) {
		this.threads = threads;
		this.nds = nds;
	}

	@Override
	public void run() {
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) { }
		}			
		nds.setDone();
	}
}

