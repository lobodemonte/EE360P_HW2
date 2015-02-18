package EL8779_SMT2436;

import java.util.concurrent.Semaphore;

//Erik Lopez(el8779) and Sean Tubbs(smt2436)

public class CyclicBarrier {
	private Semaphore mutex;
	private Semaphore everyone;
	private Semaphore resetting;
	private int parties;
	private int index;
	
	public CyclicBarrier(int parties) {
		this.parties = parties;
		this.index = parties;
		this.mutex = new Semaphore(1); //serves as mutex tool
		this.everyone = new Semaphore(1); //waits for everyone to be done
		this.resetting = new Semaphore(1); //blocks if in the process of resetting
		
	}
	
	public int await() throws InterruptedException {
		
		mutex.acquire();
		
		resetting.acquire(); //just to handle edge case that new set of threads enter while
		resetting.release(); //still releasing previous set of threads
		
		if(index == parties) { //first to arrive
			everyone.acquire(); //note, first one will try to acquire everyone TWICE, this is intended
		}
		
		int myIndex = --index;
		
		if(myIndex == 0) { //last one has arrived
			resetting.acquire(); //we're resetting everything now, release when all threads are done
			index++; //index will now serve as a count for the number of threads released
			everyone.release(); //release "everyone" that first visitor claimed
			mutex.release(); //release mutex b/c returning early
			return 0;
		}
		
		mutex.release();
		
		everyone.acquire(); //wait for everyone before exiting
		index++; //increment
		if(index == parties) { //that is, we're the last one to exit
			resetting.release();
		}
		everyone.release();
		
		return myIndex;
	}
}


/*
public class CyclicBarrier {

	Semaphore semaphore;
	private  int parties;
	private  int index; 
	
	public CyclicBarrier(int parties) {
		this.parties = parties;
		this.index = parties;
		this.semaphore = new Semaphore(parties); //TODO idk about semaphores
	}
	
	
	 // @return index of arrival, where (parties -1) indicates the first one and zero the last to arrive.
	  
	 
	public synchronized int await() throws InterruptedException {
	
		int myIndex = --index;
		
		if (myIndex == 0) {
			index = parties;
			notifyAll();
		}
		else{
			wait();
		}
		
		semaphore.acquire();
	
		return myIndex;
	}

}
*/
