package EL8779_SMT2436;

import java.util.concurrent.TimeUnit;

public class CyclicBarrierTester {

	
	private static int parties;
	private static CyclicBarrier barrier;
	
	private static class NumberedThread extends Thread{
		private int n;
		
		public NumberedThread(int num) {
			super();
			n = num;
		}
		
		public void run() {
			try {
				System.out.println("Thread t" + n + " arrived at: " + barrier.await());
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void test(int numParties, int numTests){
		
		parties = numParties;
		barrier = new CyclicBarrier(parties);
		NumberedThread nt;
		int j = 1;
		
		for(int i = 0; i < numTests; i++) { //number of tests to run
			for(int k = 0; k < numParties; k++) {				
				nt = new NumberedThread(j);
				j++;
				nt.start();
				
				if(i % 3 == 0) { //alternate doing this every other test
					try {
						TimeUnit.MILLISECONDS.sleep(500);
						//sleep in an attempt to make threads receive indices in CyclicBarrier in a nice order
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			}
		}
		
		/*
		Thread t1 = new Thread() {
			public void run() {
				//while (true){
					try {
						System.out.println("Thread t1 arrived at: "+barrier.await());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				//}
			}
		};
		Thread t2 = new Thread() {
			public void run() {
				//while (true){
					try {
						System.out.println("Thread t2 arrived at: "+barrier.await());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				//}
			}
		};
		Thread t3 = new Thread() {
			public void run() {
				//while (true){
					try {
						System.out.println("Thread t3 arrived at: "+barrier.await());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				//}
			}
		};
		Thread t4 = new Thread() {
			public void run() {
				//while (true){
					try {
						System.out.println("Thread t4 arrived at: "+barrier.await());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				//}
			}
		};
		
		t1.start();
		t2.start();
		t3.start();
		t4.start(); */
		
	}

}
