package EL8779_SMT2436;

public class GardenTester {

	static int goal = 100;
	static Garden garden;
	
	public static void test(){
		
		
		
		garden = new Garden(10);
		
		Thread Newton = new Thread() {
			public void run() {
				while (garden.count() < goal){
					try {
						//System.out.println("Go Newt");
						try{
						garden.startDigging();
						}
						//System.out.println("Stop Newt");
						finally{garden.doneDigging();}
						//System.out.println("Yay");
						
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
						//System.out.println("Go Ben");
						garden.startSeeding();}
						//System.out.println("Stop Ben");
						finally{garden.doneSeeding();}
						//System.out.println("Yay Ben");
						  
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
						//System.out.println("Go Mary");
						try{garden.startFilling();}
						//System.out.println("Stop Mary");
						finally{garden.doneFilling();}
						//System.out.println("Yay Mary");
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
						System.out.println("Mary the Filler Failed");
					}

				}
			}
		};
		
		System.out.println("Starting...");
		Mary.start();
		Newton.start();
		Benjamin.start();
		//All.run();
		
	    System.out.println("GARDEN COUNT: "+garden.count());
	}

}
