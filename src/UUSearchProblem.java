/**
 * Author: Edrei Chua
 * Created on: 01/08/16
 * CS 76 Winter 2016
 *
 * Assignment 1: Missionaries and Cannibals
 *
 * Acknowledgement: Prof. Devin Balkcom for providing the scaffold for this assignment
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public abstract class UUSearchProblem {


	
	// used to store performance information about search runs.
	//  these should be updated during the process of searches

	// see methods later in this class to update these values
	protected int nodesExplored;
	protected int maxMemory;

	protected UUSearchNode startNode;
	
	protected interface UUSearchNode {
		ArrayList<UUSearchNode> getSuccessors();
		boolean goalTest();
		int getDepth();
	}

	// breadthFirstSearch:  return a list of connecting Nodes, or null
	// no parameters, since start and goal descriptions are problem-dependent.
	//  therefore, constructor of specific problems should set up start
	//  and goal conditions, etc.
	
	public List<UUSearchNode> breadthFirstSearch(){
		resetStats();
		// You will write this method

		// the frontier of breadth first search; linked list acts as a queue
		LinkedList<UUSearchNode> frontier = new LinkedList<>();

		// the visited states
		HashSet<UUSearchNode> visited = new HashSet<>();

		// back chaining map
		HashMap<UUSearchNode,UUSearchNode> backchainMap = new HashMap<>();

		// add the starting node
		frontier.addLast(startNode);
		visited.add(startNode);
		incrementNodeCount();

		// classic BFS
		while(!frontier.isEmpty()) {

			// Update memory
			updateMemory(visited.size());

			// visit node from frontier
			UUSearchNode tovisit = frontier.removeFirst();

			// termination condition
			if (tovisit.goalTest())
				return backchain(tovisit, backchainMap);

			for (UUSearchNode child : tovisit.getSuccessors()) {
				if(!visited.contains(child)) {
					backchainMap.put(child, tovisit);

					// add to linked list, which acts as a queue
					frontier.addLast(child);
					visited.add(child);

					// increment count
					incrementNodeCount();
				}
			}
		}
		return null;
	}


	// backchain should only be used by bfs, not the recursive dfs
	private List<UUSearchNode> backchain(UUSearchNode node,
			HashMap<UUSearchNode, UUSearchNode> visited) {
		// you will write this method

		LinkedList<UUSearchNode> result = new LinkedList<>();
		result.addFirst(node);

		while(visited.containsKey(node)){
			UUSearchNode previous = visited.get(node);

			// do not add the startNode (optional, for aesthetic reason)
			if(!previous.equals(startNode))
				result.addFirst(previous); // back tracking
			node = previous;
		}

		return result;
	}


	public List<UUSearchNode> depthFirstMemoizingSearch(int maxDepth) {
		resetStats(); 
		
		// You will write this method

		HashMap<UUSearchNode,Integer> visited = new HashMap<>();
		visited.put(startNode,0);
		return dfsrm(startNode, visited, 0, maxDepth);

	}

	// recursive memoizing dfs. Private, because it has the extra
	// parameters needed for recursion.
	private List<UUSearchNode> dfsrm(UUSearchNode currentNode, HashMap<UUSearchNode,Integer> visited,
			int depth, int maxDepth) {
		
		// keep track of stats; these calls charge for the current node
		updateMemory(visited.size());
		incrementNodeCount();

		// you write this method.  Comments *must* clearly show the 
		//  "base case" and "recursive case" that any recursive function has.

		// Base case
		if(currentNode.goalTest()){	// success
			LinkedList<UUSearchNode> head = new LinkedList<>();
			return head;
		}else if(depth>=maxDepth){ // failure
			return null;
		}

		// recursive case
		for(UUSearchNode child: currentNode.getSuccessors()){
			if(!visited.containsKey(child) || visited.get(child) >= depth){
				visited.put(child,depth+1);
				LinkedList<UUSearchNode> array = (LinkedList<UUSearchNode>) dfsrm(child, visited,depth+1,maxDepth); // recursion

				if(array!=null){
					array.addFirst(child);
					return array;
				}
			}
		}
		return null;
	}
	

	// set up the iterative deepening search, and make use of dfspc
	public List<UUSearchNode> IDSearch(int maxDepth) {
		resetStats();
		// you write this method

		for(int i = 0; i < maxDepth; i++){
			HashSet<UUSearchNode> currentPath = new HashSet<>();
			currentPath.add(startNode);
			List<UUSearchNode> attempt = dfsrpc(startNode, currentPath, 0, i);

			if(attempt != null)
				return attempt;
		}
		return null;
	}



	// set up the depth-first-search (path-checking version), 
	//  but call dfspc to do the real work
	public List<UUSearchNode> depthFirstPathCheckingSearch(int maxDepth) {
		resetStats();
		
		// I wrote this method for you.  Nothing to do.

		HashSet<UUSearchNode> currentPath = new HashSet<UUSearchNode>();
		currentPath.add(startNode);
		return dfsrpc(startNode, currentPath, 0, maxDepth);

	}

	// recursive path-checking dfs. Private, because it has the extra
	// parameters needed for recursion.
	private List<UUSearchNode> dfsrpc(UUSearchNode currentNode, HashSet<UUSearchNode> currentPath,
			int depth, int maxDepth) {

		// you write this method
		// keep track of stats; these calls charge for the current node
		updateMemory(currentPath.size());
		incrementNodeCount();

		// you write this method.  Comments *must* clearly show the
		//  "base case" and "recursive case" that any recursive function has.

		// Base case
		if(currentNode.goalTest()){
			LinkedList<UUSearchNode> head = new LinkedList<>();
			return head;
		}else if(depth>=maxDepth){
			return null;
		}

		// recursive case
		for(UUSearchNode child: currentNode.getSuccessors()){
			if(!currentPath.contains(child)){
				currentPath.add(child);
				LinkedList<UUSearchNode> array = (LinkedList<UUSearchNode>) dfsrpc(child, currentPath, depth + 1, maxDepth); // recursion

				if(array!=null){
					array.addFirst(child);
					return array;
				}else{
					currentPath.remove(child); // back tracking to remove node that is off the path
				}
			}
		}
		return null;
	}

	protected void resetStats() {
		nodesExplored = 0;
		maxMemory = 0;
	}
	
	protected void printStats() {
		System.out.println("Nodes explored during last search:  " + nodesExplored);
		System.out.println("Maximum memory usage during last search " + maxMemory);
	}
	
	protected void updateMemory(int currentMemory) {
		maxMemory = Math.max(currentMemory, maxMemory);
	}
	
	protected void incrementNodeCount() {
		nodesExplored++;
	}

}
