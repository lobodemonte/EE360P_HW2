//Erik Lopez(el8779) and Sean Tubbs(smt2436)
package EL8779_SMT2436;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Garden {

	private final int MAX_UNFILLEDHOLES;
	private int emptyHoles;
	private int seededHoles;
	private int doneHoles;
	//int holesDone;
	
	ReentrantLock shovelLock = new ReentrantLock();
	ReentrantLock something = new ReentrantLock();
	
	
	
	Condition shovelAvailable = shovelLock.newCondition();
	Condition shovelTaken = shovelLock.newCondition();
	
	public Garden(int MAX) {
		this.MAX_UNFILLEDHOLES = MAX;
		emptyHoles = 0;
		seededHoles = 0;
		doneHoles = 0;
	}
	
	public void startDigging() throws InterruptedException{

		while(emptyHoles >= MAX_UNFILLEDHOLES){
			wait();
		}
		
		shovelLock.lock();
		try{
			
			
		}finally{
			shovelLock.unlock();
		}
		
		
		emptyHoles++;
		
	}
	public void doneDigging(){
		
	}
	public void startSeeding() throws InterruptedException{
		while(emptyHoles <= 0){
			wait();
		}
		seededHoles++;
		emptyHoles--;
	
	}
	public void doneSeeding(){
		
	}
	public void startFilling() throws InterruptedException{
		
		while(seededHoles <= 0){
			wait();
		}
		
		shovelLock.lock();
		try{
			seededHoles--;
		}finally{
			shovelLock.unlock();
		}
		
		
	}
	public void doneFilling(){
		doneHoles++;
	}
	
	public int count(){
		return doneHoles;
	}


	
}
