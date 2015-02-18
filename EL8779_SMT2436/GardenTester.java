package EL8779_SMT2436;

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
						}finally{garden.doneDigging();}
						
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
							garden.doneSeeding();
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
							garden.doneFilling();
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
					try {
						garden.startDigging();
						garden.doneDigging();
						garden.startSeeding();
						garden.doneSeeding();
						garden.startFilling();
						garden.doneFilling();
					} catch (InterruptedException e) {
						System.out.println("Omnipotent Worker Failed");
					}

				}
			}
		};
		
		System.out.println("Starting...");
		
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
