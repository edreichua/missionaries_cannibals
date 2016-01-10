import java.util.ArrayList;
import java.util.Arrays;


// for the first part of the assignment, you might not extend UUSearchProblem,
//  since UUSearchProblem is incomplete until you finish it.

public class CannibalProblem extends UUSearchProblem {


	// the following are the only instance variables you should need.
	//  (some others might be inherited from UUSearchProblem, but worry
	//  about that later.)

	private static int goalm, goalc, goalb;
	private static int totalMissionaries, totalCannibals;

	public CannibalProblem(int sm, int sc, int sb, int gm, int gc, int gb) {
		// I (djb) wrote the constructor; nothing for you to do here.

		startNode = new CannibalNode(sm, sc, sb, 0);
		goalm = gm;
		goalc = gc;
		goalb = gb;
		totalMissionaries = sm;
		totalCannibals = sc;
		
	}
	
	// node class used by searches.  Searches themselves are implemented
	//  in UUSearchProblem.

	//private static class CannibalNode{
	private class CannibalNode implements UUSearchNode {

		// do not change BOAT_SIZE without considering how it affect
		// getSuccessors. 
		
		private final static int BOAT_SIZE = 2;
	
		// how many missionaries, cannibals, and boats
		// are on the starting shore
		private int[] state; 
		
		// how far the current node is from the start.  Not strictly required
		//  for search, but useful information for debugging, and for comparing paths
		private int depth;  

		public CannibalNode(int m, int c, int b, int d) {
			state = new int[3];
			this.state[0] = m;
			this.state[1] = c;
			this.state[2] = b;
			
			depth = d;

		}

		//public ArrayList<CannibalNode> getSuccessors() {
		public ArrayList<UUSearchNode> getSuccessors() {

			// add actions (denoted by how many missionaries and cannibals to put
			// in the boat) to current state. 

			// You write this method.  Factoring is usually worthwhile.  In my
			//  implementation, I wrote an additional private method 'isSafeState',
			//  that I made use of in getSuccessors.  You may write any method
			//  you like in support of getSuccessors.

			ArrayList<UUSearchNode> array = new ArrayList<>();

			int b = state[2] == 0 ? 1 : 0; // change the boat state

			int numMissionaries, numCannibals;

			// loop through all possible combination of movement of missionaries and cannibals
			for(int moveMissionaries = 0; moveMissionaries <= BOAT_SIZE; moveMissionaries++){
				for(int moveCannibals = 0; moveCannibals <= BOAT_SIZE - moveMissionaries; moveCannibals++) {

					// if boat is at the start bank, move away from start bank
					if(state[2] == 1) {
						numMissionaries = state[0] - moveMissionaries;
						numCannibals = state[1] - moveCannibals;

					// if boat is at finish bank, move towards the finish bank
					}else{
						numMissionaries = state[0] + moveMissionaries;
						numCannibals = state[1] + moveCannibals;
					}

					CannibalNode potentialSuccessor = new CannibalNode(numMissionaries,numCannibals,b,depth);

					// check if there is at least one person on the boat and potential successor is a legal and safe state
					if(moveCannibals+moveMissionaries != 0 && potentialSuccessor.isLegalState()
							&& potentialSuccessor.isSafeState()){
						array.add(potentialSuccessor);
					}
				}
			}

			return array;
		}

		private boolean isSafeState(){

			// check the starting bank
			if( state[0] > 0 && state[0] < state[1]) {
				return false;

			// check the finish bank
			}else if(totalMissionaries - state[0] > 0 && totalMissionaries - state[0] < totalCannibals - state[1]) {
				return false;

			}else {
				return true;
			}
		}

		private boolean isLegalState(){

			// check for negative states
			if( state[0] < 0 || state[1] < 0) {
				return false;

			// check for states with number that exceeds the original
			}else if(state[0] > totalMissionaries || state[1] > totalCannibals) {
				return false;

			}else {
				return true;
			}
		}


		
		//@Override
		public boolean goalTest() {
			// you write this method.  (It should be only one line long.)
			return state[0] == goalm && state[1] == goalc && state[2] == goalb;

		}

		

		// an equality test is required so that visited lists in searches
		// can check for containment of states
		@Override
		public boolean equals(Object other) {
			return Arrays.equals(state, ((CannibalNode) other).state);
		}

		@Override
		public int hashCode() {
			return state[0] * 100 + state[1] * 10 + state[2];
		}

		@Override
		public String toString() {
			// you write this method
			return "("+state[0]+", "+state[1]+", "+state[2]+")";
		}

		/*
        You might need this method when you start writing 
        (and debugging) UUSearchProblem.
        */
		@Override
		public int getDepth() {
			return depth;
		}


	}


	/**
	// Test
	public static void main(String args[]){

		CannibalNode startnode = new CannibalNode(3,3,1,0);
		ArrayList<CannibalNode> list = startnode.getSuccessors();

		for(CannibalNode i : list) {
			System.out.println(i.toString());
		}

	}*/
	

}
