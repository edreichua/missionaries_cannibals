import java.util.ArrayList;
import java.util.Arrays;


// for the first part of the assignment, you might not extend UUSearchProblem,
//  since UUSearchProblem is incomplete until you finish it.

public class LossyCannibalProblem extends LossyUUSearchProblem {


    // the following are the only instance variables you should need.
    //  (some others might be inherited from UUSearchProblem, but worry
    //  about that later.)

    private static int goalm, goalc, goalb, E;
    private static int totalMissionaries, totalCannibals;

    public LossyCannibalProblem(int sm, int sc, int sb, int E, int gm, int gc, int gb) {
        // I (djb) wrote the constructor; nothing for you to do here.

        goalm = gm;
        goalc = gc;
        goalb = gb;
        totalMissionaries = sm;
        totalCannibals = sc;
        this.E = E;
        startNode = new LossyCannibalNode(sm, sc, totalMissionaries -  sm, totalCannibals - sc, sb, 0);
    }

    // node class used by searches.  Searches themselves are implemented
    //  in UUSearchProblem.

    //private static class CannibalNode{
    private class LossyCannibalNode implements LossyUUSearchNode {

        // do not change BOAT_SIZE without considering how it affect
        // getSuccessors.

        private final static int BOAT_SIZE = 2;

        // how many missionaries, cannibals, and boats
        // are on the starting shore
        private int[] state;

        // how far the current node is from the start.  Not strictly required
        //  for search, but useful information for debugging, and for comparing paths
        private int depth, E;

        public LossyCannibalNode(int m1, int c1, int m2, int c2, int b, int d) {
            state = new int[5];
            this.state[0] = m1;
            this.state[1] = c1;
            this.state[2] = m2;
            this.state[3] = c2;
            this.state[4] = b;

            depth = d;

        }

        //public ArrayList<CannibalNode> getSuccessors() {
        public ArrayList<LossyUUSearchNode> getSuccessors() {

            // add actions (denoted by how many missionaries and cannibals to put
            // in the boat) to current state.

            // You write this method.  Factoring is usually worthwhile.  In my
            //  implementation, I wrote an additional private method 'isSafeState',
            //  that I made use of in getSuccessors.  You may write any method
            //  you like in support of getSuccessors.

            ArrayList<LossyUUSearchNode> array = new ArrayList<>();

            int numMissionaries1, numCannibals1, numMissionaries2, numCannibals2, nextb;

            // loop through all possible combination of movement of missionaries and cannibals
            for(int moveMissionaries = 0; moveMissionaries <= BOAT_SIZE; moveMissionaries++){
                for(int moveCannibals = 0; moveCannibals <= BOAT_SIZE - moveMissionaries; moveCannibals++) {

                    // if boat is at the start bank, move away from start bank
                    if(state[4] == 1) {
                        numMissionaries1 = state[0] - moveMissionaries;
                        numCannibals1 = state[1] - moveCannibals;
                        numMissionaries2 = state[2] + moveMissionaries;
                        numCannibals2 = state[3] + moveCannibals;
                        nextb = 0;
                        // if boat is at finish bank, move towards the finish bank
                    }else{
                        numMissionaries1 = state[0] + moveMissionaries;
                        numCannibals1 = state[1] + moveCannibals;
                        numMissionaries2 = state[2] - moveMissionaries;
                        numCannibals2 = state[3] - moveCannibals;
                        nextb = 1;
                    }
                    if(numCannibals1 > numMissionaries1) numMissionaries1 = 0;
                    if(numCannibals2 > numMissionaries2) numMissionaries2 = 0;

                    LossyCannibalNode potentialSuccessor = new LossyCannibalNode(numMissionaries1,numCannibals1,numMissionaries2,numCannibals2,nextb,depth);

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

            return state[0]+state[2] >= totalMissionaries - E;
        }

        private boolean isLegalState(){

            // check for negative states
            if( state[0] < 0 || state[1] < 0 || state[2] < 0 || state[3] < 0) {
                return false;
                // check for states with number that exceeds the original
            }else if(state[0] + state[2] > totalMissionaries || state[1] + state[3] > totalCannibals ) {
                return false;

            }else {
                return true;
            }
        }



        //@Override
        public boolean goalTest() {
            // you write this method.  (It should be only one line long.)
            return state[0] == goalm && state[1] == goalc && state[4] == goalb;

        }



        // an equality test is required so that visited lists in searches
        // can check for containment of states
        @Override
        public boolean equals(Object other) {
            return Arrays.equals(state, ((LossyCannibalNode) other).state);
        }

        @Override
        public int hashCode() {
            return state[0] * 10000 + state[1] * 1000 + state[2] * 100 + state[3] * 10 + state[4];
        }

        @Override
        public String toString() {
            // you write this method
            return "("+state[0]+", "+state[1]+", "+state[2]+", "+state[3]+", "+state[4]+")";
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

        LossyCannibalNode startnode = new LossyCannibalNode(3,3,0,0,1,0);
        ArrayList<LossyCannibalNode> list = startnode.getSuccessors();

        for(LossyCannibalNode i : list) {
            System.out.println(i.toString());
        }
     }
     */


}
