//Erik Lopez(el8779) and Sean Tubbs(smt2436)
package EL8779_SMT2436;

public class FairReadWriteLock {

	private int readers;
	private int writers;
	private int futureWriters;
	
	public FairReadWriteLock() {
		readers = 0;
		writers = 0;
		futureWriters = 0;
	}
	public synchronized void beginRead() throws InterruptedException {
		while(writers > 0 || futureWriters > 0){
			wait();
		}
		readers++;
	}
	public synchronized void beginWrite() throws InterruptedException{
		futureWriters++;
		while(writers > 0 || readers > 0){
			wait();
		}
		
		//TODO: I reversed  the order of the two statements. In the original order,
		//if interrupted between the two, futureWriters and writers may both be temporarily 0,
		//which would allow a read to begin
		
		writers++;
		futureWriters--;
	}
	public synchronized void endRead() throws InterruptedException {
		readers--;
		notifyAll();
	}
	public synchronized void endWrite() throws InterruptedException{
		writers--;
		notifyAll();
	}

}
