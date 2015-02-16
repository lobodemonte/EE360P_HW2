//Erik Lopez(el8779) and Sean Tubbs(smt2436)
package EL8779_SMT2436;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Garden {

	private final int MAX_UNFILLEDHOLES;
	private volatile int emptyHoles;
	private volatile int seededHoles;
	private volatile int doneHoles;
	private volatile boolean shovel = true;
	
	ReentrantLock shovelLock = new ReentrantLock();
	ReentrantLock workLock = new ReentrantLock();
	
	Condition shovelAvail = shovelLock.newCondition();
	Condition shovelTaken = shovelLock.newCondition();
	
	Condition moreEmptyHoles = workLock.newCondition();
	//Condition lessEmptyHoles = workLock.newCondition();
	Condition lessUnfilledHoles = workLock.newCondition();
	Condition moreSeededHoles = workLock.newCondition(); 
	
	public Garden(int MAX) {
		this.MAX_UNFILLEDHOLES = MAX;
		emptyHoles = 0;
		seededHoles = 0;
		doneHoles = 0;
	}
	
	public synchronized void startDigging() throws InterruptedException{
		shovelLock.lock();
		
		
		
		workLock.lock();
		//System.out.println("startDigging");
		while(emptyHoles+seededHoles >= MAX_UNFILLEDHOLES){
			shovelLock.unlock();
			lessUnfilledHoles.await();  //await until there is lessEmptyHoles
		}
		//System.out.println("get dig shovel");
		emptyHoles++;
		
	}
	public synchronized void doneDigging(){
		shovelLock.unlock();
		moreEmptyHoles.signalAll();	//there are moreEmptyHoles
		workLock.unlock();
		
		//System.out.println("End Digging "+doneHoles);
		
	}
	public synchronized void startSeeding() throws InterruptedException{
		workLock.lock();
		//System.out.println("Start Seeding");
		while(emptyHoles <= 0){
			moreEmptyHoles.await();
		}
		seededHoles++;
		emptyHoles--;
	}
	public synchronized void doneSeeding(){
		moreSeededHoles.signalAll();	//there are moreSeededHoles
		workLock.unlock();
		//System.out.println("End Seeding");
		
	}
	public synchronized void startFilling() throws InterruptedException{
		shovelLock.lock();
		workLock.lock();
		//System.out.println("Start Filling");
		while(seededHoles <= 0){
			//System.out.println("still waiting to fill");
			shovelLock.unlock();
			moreSeededHoles.await(); //wait until there are moreSeededHoles
			
		}
		//System.out.println("get fill shovel");
		
			
		seededHoles--;
		doneHoles++;		

	}
	
	public synchronized void doneFilling(){
		shovelLock.unlock();
		lessUnfilledHoles.signalAll();
		workLock.unlock();	
		
		//System.out.println("End Filling");
	}
	
	public synchronized int count(){
		return doneHoles;
	}


	
}
