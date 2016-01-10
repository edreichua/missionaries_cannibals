import java.util.List;

public class CannibalDriver {
	public static void main(String args[]) {

		final int MAXDEPTH = 5000;

		// interesting starting state:  
		//  8, 5, 1  (IDS slow, but uses least memory.)

		// set up the "standard" 331 problem:
		CannibalProblem mcProblem = new CannibalProblem(8, 5, 1, 0, 0, 0);
	
		List<UUSearchProblem.UUSearchNode> path;
		
		
		path = mcProblem.breadthFirstSearch();

		if(path == null){
			System.out.println("This problem doesn't have a solution");
			return;
		}

		System.out.println("bfs path length:  " + path.size() + " " + path);
		mcProblem.printStats();
		System.out.println("--------");

	
		path = mcProblem.depthFirstMemoizingSearch(MAXDEPTH);	
		System.out.println("dfs memoizing path length: " + path.size() + " " + path);
		mcProblem.printStats();
		System.out.println("--------");

		path = mcProblem.depthFirstPathCheckingSearch(MAXDEPTH);
		System.out.println("dfs path checking path length: " + path.size() + " " + path);
		mcProblem.printStats();
		

		System.out.println("--------");
		path = mcProblem.IDSearch(MAXDEPTH);
		System.out.println("Iterative deepening (path checking) path length: " + path.size() + " " + path);
		mcProblem.printStats();


		// Test for Lossy using E = 0 (should produce same results as above) and E = 1
		System.out.println("====================");
		System.out.println("Lossy Implementation");

		LossyCannibalProblem lossymcProblem = new LossyCannibalProblem(8, 5, 1, 0, 0, 0, 0);

		List<LossyUUSearchProblem.LossyUUSearchNode> lossypath;


		lossypath = lossymcProblem.breadthFirstSearch();

		if(lossypath == null){
			System.out.println("This problem doesn't have a solution");
			return;
		}


		System.out.println("Lossy bfs path length:  " + lossypath.size() + " " + lossypath);
		lossymcProblem.printStats();
		System.out.println("--------");


		lossypath = lossymcProblem.depthFirstMemoizingSearch(MAXDEPTH);
		System.out.println("Lossy dfs memoizing path length: " + lossypath.size()+" "+lossypath);
		lossymcProblem.printStats();
		System.out.println("--------");

		lossypath = lossymcProblem.depthFirstPathCheckingSearch(MAXDEPTH);
		System.out.println("Lossy dfs path checking path length: " + lossypath.size()+" "+lossypath);
		lossymcProblem.printStats();


		System.out.println("--------");
		lossypath = lossymcProblem.IDSearch(MAXDEPTH);
		System.out.println("Lossy Iterative deepening (path checking) path length: " + lossypath.size()+" "+lossypath);
		lossymcProblem.printStats();

	}

}
