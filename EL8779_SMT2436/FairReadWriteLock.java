//Erik Lopez(el8779) and Sean Tubbs(smt2436)
package EL8779_SMT2436;

public class FairReadWriteLock {
	
	private int numProcesses;
	private int latestWrite;
	private int turn;
	private int nextTurnAssign;
	
	private ReadWriteLockLogger log = new ReadWriteLockLogger();
	
	public FairReadWriteLock() {
		numProcesses = 0;
		initialize();
	}

	private synchronized void initialize() {
		turn = 0;
		nextTurnAssign = 0;
		latestWrite = -1;
	}
	
	public synchronized void beginRead() {
		log.logTryToRead();
		numProcesses++;
		int prevWrite = latestWrite;
		nextTurnAssign++;
		while(turn <= prevWrite ) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println("Begin Read Wait Fail");
				e.printStackTrace();
			}
		}
		log.logBeginRead();
	}
	
	public synchronized void beginWrite() {
		log.logTryToWrite();
		numProcesses++;
		int myTurn = nextTurnAssign;
		nextTurnAssign++;
		latestWrite = myTurn;
		while(myTurn != turn) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println("Begin Write Wait Fail");
				e.printStackTrace();
			}
		}	
		log.logBeginWrite();
	}
	
	public synchronized void endRead() {
		turn++;
		numProcesses--;
		notifyAll();
		if(numProcesses == 0) {
			initialize();
		}	
		log.logEndRead();
	}
	
	public synchronized void endWrite() {
		turn++;
		numProcesses--;
		notifyAll();	
		if(numProcesses == 0) {
			initialize();
		}
		log.logEndWrite();
	}
}
