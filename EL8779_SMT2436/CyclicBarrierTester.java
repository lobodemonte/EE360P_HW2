package EL8779_SMT2436;

public class CyclicBarrierTester {

	
	private static final int parties = 3;
	private static CyclicBarrier barrier = new CyclicBarrier(parties);
	
	public static void test(){
		
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
		t4.start();
		
	}

}
