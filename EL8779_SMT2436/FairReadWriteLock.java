//Erik Lopez(el8779) and Sean Tubbs(smt2436)
package EL8779_SMT2436;

public class FairReadWriteLock {
	
	/*
	private int readers;
	private int writers;
	private int futureWriters;
	private int futureReaders;
	*/
	
	private int numProcesses;
	//private int latestRead;
	private int latestWrite;
	private int turn;
	private int nextTurnAssign;
	
	private ReadWriteLockLogger log = new ReadWriteLockLogger();
	
	public FairReadWriteLock() {
		numProcesses = 0;
		initialize();
		
		/*
		readers = 0;
		writers = 0;
		futureWriters = 0;
		futureReaders = 0;
		*/
	}
	
	//call whenever 
	//I don't think synchronize is strictly needed but doesn't hurt
	private synchronized void initialize() {
		turn = 0;
		nextTurnAssign = 0;
		//latestRead = -1;
		latestWrite = -1;
	}
	
	public synchronized void beginRead() throws InterruptedException {
		log.logTryToRead();
		
		numProcesses++;
		int prevWrite = latestWrite;
		//int myTurn = nextTurnAssign;
		nextTurnAssign++;
		//latestRead = myTurn;
		
		while(turn <= prevWrite ) {
			wait();
		}
		
		/*
		futureReaders++;
		while(writers > 0 || futureWriters > 0){
			wait();
		}
		readers++;
		futureReaders--;
		*/
		
		log.logBeginRead();
	}
	
	
	public synchronized void beginWrite() throws InterruptedException{
		log.logTryToWrite();
		
		numProcesses++;
		int myTurn = nextTurnAssign;
		nextTurnAssign++;
		latestWrite = myTurn;
		
		while(myTurn != turn) {
			wait();
		}
		
		/*
		int readersAhead = futureReaders;
		
		if(readersAhead == 0) futureWriters++;//TODO stop adding future Writers when a reader is on front
		while(writers > 0 || readers > 0){
			wait();
		}
		
		writers++;
		if (readersAhead == 0)futureWriters--;
		*/
		
		log.logBeginWrite();
	}
	
	
	public synchronized void endRead() throws InterruptedException {
		
		turn++;
		numProcesses--;
		
		notifyAll();
		
		
		if(numProcesses == 0) {
			initialize();
		}
		
		/*
		readers--;
		notifyAll();
		*/
		
		log.logEndRead();
	}
	
	
	public synchronized void endWrite() throws InterruptedException{
		
		turn++;
		numProcesses--;
		
		notifyAll();
		
		
		if(numProcesses == 0) {
			initialize();
		}
		
		/*
		writers--;
		notifyAll();
		*/
		
		log.logEndWrite();
	}
	
	
	

}
