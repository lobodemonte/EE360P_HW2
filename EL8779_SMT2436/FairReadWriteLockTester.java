package EL8779_SMT2436;

public class FairReadWriteLockTester {

	public static FairReadWriteLock lock = new FairReadWriteLock();
	
	public static void test(){
		Thread r1 = new Thread() {
			public void run() {
				while (true){
					try {
						lock.beginRead();
						System.out.println("Reading");
						lock.endRead();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						wait(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		Thread r2 = new Thread() {
			public void run() {
				while (true){
					try {
						lock.beginRead();
						System.out.println("Reading");
						lock.endRead();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						wait(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		Thread r3 = new Thread() {
			public void run() {
				while (true){
					try {
						lock.beginRead();
						System.out.println("Reading");
						lock.endRead();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
	
		Thread w1 = new Thread() {
			public void run() {
				while (true){
					try {
						lock.beginWrite();
						System.out.println("Writing");
						lock.endWrite();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						wait(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		Thread w2 = new Thread() {
			public void run() {
				while (true){
					try {
						lock.beginWrite();
						System.out.println("Writing");
						lock.endWrite();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						wait(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
	
		r1.start();
		w1.start();
		r2.start();
		w2.start();
	}

}
