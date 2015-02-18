//Erik Lopez(el8779) and Sean Tubbs(smt2436)
package EL8779_SMT2436;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Garden implements GardenCounts {

	private final int MAX_UNFILLEDHOLES;
	private AtomicInteger emptyHoles;
	private AtomicInteger seededHoles;
	private AtomicInteger doneHoles;
	
	private AtomicInteger totalDug = new AtomicInteger(0);
	private AtomicInteger totalSeeded = new AtomicInteger(0);
	private AtomicInteger totalFilled = new AtomicInteger(0);
	
	ReentrantLock shovelLock = new ReentrantLock();
	ReentrantLock workLock = new ReentrantLock();
	
	Condition moreEmptyHoles = workLock.newCondition();
	Condition lessUnfilledHoles = workLock.newCondition();
	Condition moreSeededHoles = workLock.newCondition(); 
	
	public Garden(int MAX) {
		this.MAX_UNFILLEDHOLES = MAX;
		emptyHoles.set(0);
		seededHoles.set(0);
		doneHoles.set(0);
	}	
	public void startDigging() throws InterruptedException{	
		workLock.lock();
		while(emptyHoles.get()+seededHoles.get() >= MAX_UNFILLEDHOLES){
			lessUnfilledHoles.await();  //await until there is lessEmptyHoles
		}
		shovelLock.lock();
		emptyHoles.incrementAndGet();	
		totalDug.incrementAndGet();
	}
	public void doneDigging(){
		shovelLock.unlock(); 
		moreEmptyHoles.signalAll();
		workLock.unlock();
			
	}
	public void startSeeding() throws InterruptedException{
		workLock.lock();
		while(emptyHoles.get() <= 0){
			moreEmptyHoles.await();
		}
		seededHoles.incrementAndGet();
		emptyHoles.decrementAndGet();
		totalSeeded.incrementAndGet();
	}
	public  void doneSeeding(){
		moreSeededHoles.signalAll();	//there are moreSeededHoles
		workLock.unlock();
	}
	public void startFilling() throws InterruptedException{
		workLock.lock();		
		while(seededHoles.get() <= 0){
			moreSeededHoles.await(); //wait until there are moreSeededHoles	
		}	
		shovelLock.lock();
		seededHoles.decrementAndGet();
		doneHoles.incrementAndGet();
		totalFilled.incrementAndGet();
	}
	public void doneFilling(){
		shovelLock.unlock();
		lessUnfilledHoles.signalAll();
		workLock.unlock();
	}
	public int count(){
		return doneHoles.get();
	}
	@Override
	public int totalHolesDugByNewton() {
		return totalDug.get();
	}
	@Override
	public int totalHolesSeededByBenjamin() {
		return totalDug.get();
	}
	@Override
	public int totalHolesFilledByMary() {
		return totalFilled.get();
	}
}
