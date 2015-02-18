//Erik Lopez(el8779) and Sean Tubbs(smt2436)
package EL8779_SMT2436;



public class Main {
	

	public static void main(String[] args) {
		
		//CyclicBarrierTester.test(3, 5);
		//GardenTester.test(50);
		FairReadWriteLockTester.test(3, 3, 2);
		
		/*TODO: I redid CyclicBarrier, check it out
		 * 
		 * Garden looks good, though I'm slightly unsure if we should be modifying the number of
		 * emptyHoles, etc. inside the start and done methods. In the pseudocode provided, 
		 * it looks like the Newton thread calls a method dig() in between startDigging and doneDigging?
		 * I'm a little confused on how they are going to test this the way that we have it ...
		 * 
		 * 
		 */

	}

}
