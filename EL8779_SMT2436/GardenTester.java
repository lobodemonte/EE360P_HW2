package EL8779_SMT2436;

import java.util.concurrent.TimeUnit;

public class GardenTester {

	static int goal;
	static Garden garden;
	
	public static void test(int goalNum){
		
		goal = goalNum;
		garden = new Garden(10);
		
		Thread Newton = new Thread() {
			public void run() {
				while (garden.count() < goal){
					try {
						try{
							garden.startDigging();
						}
						finally{
							System.out.println("Dug up: "+garden.totalHolesDugByNewton());
							garden.doneDigging();
							TimeUnit.MILLISECONDS.sleep(500);
						}
						
					} catch (InterruptedException e) {
						System.out.println("Newton the Digger Failed");
					}
				}
			}
		};
		Thread Benjamin = new Thread() {
			public void run() {
				while (garden.count() < goal){
					try {
						try{
							garden.startSeeding();
						}
						finally{
							System.out.println("Seeded up: "+garden.totalHolesSeededByBenjamin());
							garden.doneSeeding();
							TimeUnit.MILLISECONDS.sleep(500);
						}	  
					} catch (InterruptedException e) {
						System.out.println("Benjamin the Seeder Failed");
					}
				}
				  
			}
		};
		Thread Mary = new Thread() {
			public void run() {
				while (garden.count() < goal){
					try {
						try{
							garden.startFilling();
						}finally{
							System.out.println("Filled up: "+garden.totalHolesFilledByMary());
							garden.doneFilling();
							TimeUnit.MILLISECONDS.sleep(500);
						}
					} catch (InterruptedException e) {
						System.out.println("Mary the Filler Failed");
					}

				}
			}
		};
		
		Thread All = new Thread() {
			public void run() {
				while (garden.count() < goal){
					garden.startDigging();
					garden.doneDigging();
					garden.startSeeding();
					garden.doneSeeding();
					garden.startFilling();
					garden.doneFilling();
				}
			}
		};
		
		System.out.println("Starting...");
		
		//ExecutorService executor = Executors.newFixedThreadPool(5);
		
		Mary.start();		
		Newton.start();		
		Benjamin.start();
		
		try{
			Newton.join();
			Benjamin.join();
			Mary.join();
		}catch (Exception e){}
		
		//All.run();
	    System.out.println("GARDEN COUNT: "+garden.count());
	}

}
