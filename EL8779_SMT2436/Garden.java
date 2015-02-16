//Erik Lopez(el8779) and Sean Tubbs(smt2436)
package EL8779_SMT2436;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Garden {

	private final int MAX_UNFILLEDHOLES;
	private volatile int emptyHoles;
	private volatile int seededHoles;
	private volatile int doneHoles;
	//private volatile boolean shovel = true;
	
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
	
	public void startDigging() throws InterruptedException{	
		workLock.lock();
		while(emptyHoles+seededHoles >= MAX_UNFILLEDHOLES){
			lessUnfilledHoles.await();  //await until there is lessEmptyHoles
		}
		shovelLock.lock();
		emptyHoles++;
		
	}
	public void doneDigging(){
		moreEmptyHoles.signalAll();	//there are moreEmptyHoles
		workLock.unlock();
		shovelLock.unlock();	
	}
	public void startSeeding() throws InterruptedException{
		workLock.lock();
		while(emptyHoles <= 0){
			moreEmptyHoles.await();
		}
		seededHoles++;
		emptyHoles--;
	}
	public  void doneSeeding(){
		moreSeededHoles.signalAll();	//there are moreSeededHoles
		workLock.unlock();
		//System.out.println("End Seeding");
		
	}
	public void startFilling() throws InterruptedException{
		workLock.lock();		
		while(seededHoles <= 0){
			moreSeededHoles.await(); //wait until there are moreSeededHoles	
		}	
		shovelLock.lock();
		seededHoles--;
		doneHoles++;		
	}
	
	public void doneFilling(){
		shovelLock.unlock();
		lessUnfilledHoles.signalAll();
		workLock.unlock();
	}
	
	public int count(){
		return doneHoles;
	}


	
}
