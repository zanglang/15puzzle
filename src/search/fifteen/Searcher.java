package search.fifteen;

import java.util.ArrayList;
import java.util.Collections;

import search.ActionStatePair;
import search.Node;
import search.State;

/**
 * The core class that traverses a search tree given an algorithm 
 */
public class Searcher {

	// records the nodes traversed (A for A*, G for Greedy)
	public int nodesTraversedA = 0;
	public int nodesTraversedG = 0;
	// algorithm used
	private Algorithms algorithm = null;
	
	/**
	 * Constructor
	 * @param algorithm Search algorithm used
	 */
	public Searcher(Algorithms algorithm) {
		this.algorithm = algorithm; 
	}

	/**
	 * Start traversing the tree until the goal state is reached
	 * @param initialState The original puzzle state to start searching from
	 * @return The node representing the goal state of the puzzle, or null if the puzzle was unsolvable
	 */
	public Node traverse(PuzzleState initialState) {

		// setup fringe list
		ArrayList<SearchNode> fringe = new ArrayList<SearchNode>();
		ArrayList<State> history = new ArrayList<State>();
		fringe.add(AlgorithmFactory.createNode(algorithm, initialState));

		// start traversing from starting node
		while (!fringe.isEmpty()) {
			// inspect the first node in fringe
			// (usually with lowest heuristic value/cheapest)
			SearchNode head = fringe.remove(0);
			State state = head.getState();
			// if node is already in closed fringe skip it
			if (history.contains(state))
				continue;
			else
				history.add(state);
			
			//System.out.println(state.toString());			
			// check if currently first node in fringe is goal state, if so,
			// return because we've found a solution			
			if (state.goal()) {
				if (SearchNode.greedy)
					nodesTraversedG = fringe.size();
				else
					nodesTraversedA = fringe.size();
				return head;
			}

			// add successor nodes to the fringe
			for (ActionStatePair successor : state.successor()) {
				SearchNode node = AlgorithmFactory.createNode(
						algorithm,
						head,
						successor.getAction(),
						(PuzzleState)successor.getState());
				fringe.add(node);
			}

			// sort our fringe so lowest cost nodes get pushed to front
			Collections.sort(fringe);
		}
		
		// nothing was found! Puzzle was unsolvable
		return null;
	}
}
