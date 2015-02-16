package EL8779_SMT2436;

import java.util.concurrent.Semaphore;

//Erik Lopez(el8779) and Sean Tubbs(smt2436)
public class CyclicBarrier {

	Semaphore semaphore;
	private volatile int parties;
	private volatile int waiting; 
	
	public CyclicBarrier(int parties) {
		this.parties = parties;
		this.waiting = 0;
		this.semaphore = new Semaphore(parties); //TODO idk about semaphores
	}
	
	/**
	 * @return index of arrival, where (parties -1) indicates the first one and zero the last to arrive.
	 * 
	 */
	int await() throws InterruptedException {
		int result = 0;
		semaphore.acquire();
		try{
			waiting++;
			result = parties - waiting;
			if (parties - waiting > 0){
				wait();
			}
			else {
				waiting = 0;
				notifyAll();
			}
		}
		catch(Exception e){ 
			System.out.println("Something went wrong, oops");
		}
		finally{
			semaphore.release();
		}
		
		return result;
		
		//if current thread is not the last 
		//then disable it
		
		//if current thread is the last 
		//wake all up
		
		
	}

}
