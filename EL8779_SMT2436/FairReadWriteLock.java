//Erik Lopez(el8779) and Sean Tubbs(smt2436)
package EL8779_SMT2436;

public class FairReadWriteLock {
	
	private int readers;
	private int writers;
	private int futureWriters;
	private int futureReaders;
	
	private ReadWriteLockLogger log = new ReadWriteLockLogger();
	
	public FairReadWriteLock() {
		readers = 0;
		writers = 0;
		futureWriters = 0;
		futureReaders = 0;
	}
	public synchronized void beginRead() throws InterruptedException {
		log.logTryToRead();
		
		futureReaders++;
		while(writers > 0 || futureWriters > 0){
			wait();
		}
		readers++;
		futureReaders--;
		
		log.logBeginRead();
	}
	public synchronized void beginWrite() throws InterruptedException{
		log.logTryToWrite();
		
		int readersAhead = futureReaders;
		
		if(readersAhead == 0) futureWriters++;//TODO stop adding future Writers when a reader is on front
		while(writers > 0 || readers > 0){
			wait();
		}
		
		//TODO: I reversed  the order of the two statements. In the original order,
		//if interrupted between the two, futureWriters and writers may both be temporarily 0,
		//which would allow a read to begin
		
		writers++;
		if (readersAhead == 0)futureWriters--;
		log.logBeginWrite();
	}
	public synchronized void endRead() throws InterruptedException {
		readers--;
		notifyAll();
		log.logEndRead();
	}
	public synchronized void endWrite() throws InterruptedException{
		writers--;
		notifyAll();
		log.logEndWrite();
	}
	
	
	

}
