package EL8779_SMT2436;

import java.util.concurrent.Semaphore;

//Erik Lopez(el8779) and Sean Tubbs(smt2436)
public class CyclicBarrier {

	Semaphore semaphore;
	private  int parties;
	private  int index; 
	
	public CyclicBarrier(int parties) {
		this.parties = parties;
		this.index = parties;
		this.semaphore = new Semaphore(parties); //TODO idk about semaphores
	}
	
	/**
	 * @return index of arrival, where (parties -1) indicates the first one and zero the last to arrive.
	 * 
	 */
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
