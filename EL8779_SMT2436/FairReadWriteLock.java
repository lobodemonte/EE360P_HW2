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
	}
	
	//call whenever 
	//I don't think synchronize is strictly needed but doesn't hurt
	private synchronized void initialize() {
		turn = 0;
		nextTurnAssign = 0;
		latestWrite = -1;
	}
	
	public synchronized void beginRead() throws InterruptedException {
		log.logTryToRead();
		numProcesses++;
		int prevWrite = latestWrite;
		nextTurnAssign++;
		while(turn <= prevWrite ) {
			wait();
		}
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
		log.logBeginWrite();
	}
	
	
	public synchronized void endRead() throws InterruptedException {
		turn++;
		numProcesses--;
		notifyAll();
		if(numProcesses == 0) {
			initialize();
		}	
		log.logEndRead();
	}
	
	
	public synchronized void endWrite() throws InterruptedException{
		turn++;
		numProcesses--;
		notifyAll();	
		if(numProcesses == 0) {
			initialize();
		}
		log.logEndWrite();
	}
	
	
	

}
