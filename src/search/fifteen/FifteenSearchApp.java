package search.fifteen;

import java.util.ArrayList;
import java.lang.Math;

import search.Action;
import search.Node;

public class FifteenSearchApp {
	
	// search algorithms classes
	public static Searcher searchMhG = new Searcher(Algorithms.TEST_MH);
	public static Searcher searchMhA = new Searcher(Algorithms.TEST_MH);
	public static Searcher searchH2G = new Searcher(Algorithms.HEURISTIC_2);
	public static Searcher searchH2A = new Searcher(Algorithms.HEURISTIC_2);
	public static Searcher searchManhattanG = new Searcher(Algorithms.MANHATTAN_DISTANCE);
	public static Searcher searchManhattanA = new Searcher(Algorithms.MANHATTAN_DISTANCE);
	public static Searcher searchMisplacedG = new Searcher(Algorithms.MISPLACED_TILES);
	public static Searcher searchMisplacedA = new Searcher(Algorithms.MISPLACED_TILES);
	public static Searcher searchTree = new Searcher(Algorithms.TREE);
	
	/**
	 * Test program for search procedures
	 * 
	 * @param args none interpreted as yet
	 */
	public static void main(String[] args) {
		// create an initial fifteen puzzle state by first generating the goal
		// configuration
		// PuzzleState myState=new PuzzleState();

		// Create a random puzzle and memorise the puzzle state.
		// PuzzleState InState = new PuzzleState();
		// System.out.println(InState.toString());

		PuzzleState myState = randomPuzzle(30);
		PuzzleState myState2 = new PuzzleState(myState);

		// or "shuffle" the tiles around manually a little bit...
		// myState=new PuzzleState(myState, PuzzleState.MOVE_LEFT);
		// myState=new PuzzleState(myState, PuzzleState.MOVE_UP);
		// myState=new PuzzleState(myState, PuzzleState.MOVE_LEFT);
		// myState=new PuzzleState(myState, PuzzleState.MOVE_UP);
		// myState=new PuzzleState(myState, PuzzleState.MOVE_UP);

		// List the initial state and results of actions performed.

		System.out.println("Initial state:");
		System.out.println(myState2.toString());
		
		Action[] actionsMhG = solveH1G(new PuzzleState(myState));
		Action[] actionsMhA = solveH1A(new PuzzleState(myState));
		//Action[] actionsH2G = solveH2G(new PuzzleState(myState));
		//Action[] actionsH2A = solveH2A(new PuzzleState(myState));
		Action[] actionsManhattanG = solveManhattanG(new PuzzleState(myState));
		Action[] actionsManhattanA = solveManhattanA(new PuzzleState(myState));
		//Action[] actionsMisplacedG = solveMisplacedG(new PuzzleState(myState));
		//Action[] actionsMisplacedA = solveMisplacedA(new PuzzleState(myState));
		// Action[] actionsTree = solveTree(new PuzzleState(myState));
		
		printResults("TestMh with Greedy*", myState, actionsMhG);
		printResults("TestMh with A*", myState, actionsMhA);
		//printResults("Heuristic 2 with Greedy*", myState, actionsH2G);
		//printResults("Heuristic 2 with A*", myState, actionsH2A);
		printResults("Manhattan Distance search with Greedy", myState, actionsManhattanG);
		printResults("Manhattan Distance search with A*", myState, actionsManhattanA);
		//printResults("Misplaced Tiles Search with Greedy", myState, actionsMisplacedG);
		//printResults("Misplaced Tiles Search with A*", myState, actionsMisplacedA);
		// printResults("Breadth First Search", myState, actionsTree);
		
		System.out.println("TestMh with Greedy depth:             "
				+ actionsMhG.length
				+ " nodes: "
				+ searchMhG.nodesTraversedG
				+ " EBF: "
				+ Node.effectiveBranchingFactor(searchMhG.nodesTraversedG,
						(actionsMhG.length - 1))); 
		System.out.println("TestMh with A* depth:                 "
				+ actionsMhA.length
				+ " nodes: "
				+ searchMhA.nodesTraversedA
				+ " EBF: "
				+ Node.effectiveBranchingFactor(searchMhA.nodesTraversedA,
						(actionsMhA.length - 1)));
		/*System.out.println("Heuristic 2 with Greedy depth:        "
				+ actionsH2G.length
				+ " nodes: "
				+ searchH2G.nodesTraversedG
				+ " EBF: "
				+ Node.effectiveBranchingFactor(searchH2G.nodesTraversedG,
						(actionsH2G.length - 1)));
		System.out.println("Heuristic 2 with A* depth:            "
				+ actionsH2A.length
				+ " nodes: "
				+ searchH2A.nodesTraversedA
				+ " EBF: "
				+ Node.effectiveBranchingFactor(searchH2A.nodesTraversedA,
						(actionsH2A.length - 1)));*/
		/*System.out.println("Misplaced Tiles depth with Greedy:    "
				+ actionsMisplacedG.length
				+ " nodes: "
				+ searchMisplacedG.nodesTraversedG
				+ " EBF: "
				+ Node.effectiveBranchingFactor(
						searchMisplacedG.nodesTraversedG,
						(actionsMisplacedG.length - 1)));
		System.out.println("Misplaced Tiles depth with A*      :  "
				+ actionsMisplacedA.length
				+ " nodes: "
				+ searchMisplacedA.nodesTraversedA
				+ " EBF: "
				+ Node.effectiveBranchingFactor(
						searchMisplacedA.nodesTraversedA,
						(actionsMisplacedA.length - 1)));*/
		System.out.println("Manhattan Distance depth with Greedy: "
				+ actionsManhattanG.length
				+ " nodes: "
				+ searchManhattanG.nodesTraversedG
				+ " EBF: "
				+ Node.effectiveBranchingFactor(
						searchManhattanG.nodesTraversedG,
						(actionsManhattanG.length - 1)));
		System.out.println("Manhattan Distance depth with A*    : "
				+ actionsManhattanA.length
				+ " nodes: "
				+ searchManhattanA.nodesTraversedA
				+ " EBF: "
				+ Node.effectiveBranchingFactor(
						searchManhattanA.nodesTraversedA,
						(actionsManhattanA.length - 1)));
		
	}

	/**
	 * Breadth-first search
	 * @param state initial puzzle state
	 */
	public static Action[] solveTree(PuzzleState state) {
		// now perform the search from the "shuffled" initial state (fringe is
		// empty)
		Node goal = Node.breadthFirstSearch(state, new ArrayList<Node>());
		Action[] actions = goal.getActions();

		return actions;
	}

	// Heuristic
	public static Action[] solveH2A(PuzzleState state) {
		TestMhNode.greedy = false;
		Node goal = searchH2A.traverse(state);
		Action[] actions = goal.getActions();
		System.out.println("Heuristic2 A* solved");

		return actions;
	}
	
	// Heuristic
	public static Action[] solveH2G(PuzzleState state) {
		TestMhNode.greedy = true;
		Node goal = searchH2G.traverse(state);
		Action[] actions = goal.getActions();
		System.out.println("Heuristic2 Greedy solved");

		return actions;
	}

	// Improved Manhattan distance
	public static Action[] solveH1A(PuzzleState state) {
		TestMhNode.greedy = false;
		Node goal = searchMhA.traverse(state);
		Action[] actions = goal.getActions();
		System.out.println("MH A* solved");

		return actions;
	}

	// Improved Manhattan distance
	public static Action[] solveH1G(PuzzleState state) {
		TestMhNode.greedy = true;
		Node goal = searchMhG.traverse(state);
		Action[] actions = goal.getActions();
		System.out.println("MH Greedy solved");

		return actions;
	}
	
	// Misplaced Tiles search
	public static Action[] solveMisplacedG(PuzzleState state) {
		TestMhNode.greedy = true;
		Node goal = searchMisplacedG.traverse(state);
		Action[] actions = goal.getActions();
		System.out.println("Misplaced Search solved");

		return actions;
	}
	
	public static Action[] solveMisplacedA(PuzzleState state) {
		TestMhNode.greedy = false;
		Node goal = searchMisplacedA.traverse(state);
		Action[] actions = goal.getActions();
		System.out.println("Misplaced Search solved");

		return actions;
	}
	
	// Manhattan distance
	public static Action[] solveManhattanG(PuzzleState state) {
		TestMhNode.greedy = true;
		Node goal = searchManhattanG.traverse(state);
		Action[] actions = goal.getActions();
		System.out.println("Manhattan Distance solved");

		return actions;
	}
	
	public static Action[] solveManhattanA(PuzzleState state) {
		TestMhNode.greedy = false;
		Node goal = searchManhattanA.traverse(state);
		Action[] actions = goal.getActions();
		System.out.println("Manhattan Distance solved");
		return actions;
	}
	/**
	 * Generate a solvable random puzzle.
	 * 
	 * @param maxShuffles the number of shuffles to be performed
	 */
	public static PuzzleState randomPuzzle(int maxShuffles) {
		PuzzleState myState = new PuzzleState();
		Action lastAction = null;
		int totalMoves = 0;
		while (totalMoves < maxShuffles) {
			double r = Math.random();
			try {
				if (r < 0.25) {
					// check if this move will cancel out the previous move, if
					// so, pick a different move
					if (lastAction == PuzzleState.MOVE_RIGHT)
						continue;
					// save the move to be checked again afterwards
					lastAction = PuzzleState.MOVE_LEFT;
					PuzzleState.performAction(myState, PuzzleState.MOVE_LEFT);
					// System.out.println("left");
				} else if (r < 0.5) {
					if (lastAction == PuzzleState.MOVE_LEFT)
						continue;
					lastAction = PuzzleState.MOVE_RIGHT;
					PuzzleState.performAction(myState, PuzzleState.MOVE_RIGHT);
					// System.out.println("right");
				} else if (r < 0.75) {
					if (lastAction == PuzzleState.MOVE_DOWN)
						continue;
					lastAction = PuzzleState.MOVE_UP;
					PuzzleState.performAction(myState, PuzzleState.MOVE_UP);
					// System.out.println("up");
				} else {
					if (lastAction == PuzzleState.MOVE_UP)
						continue;
					lastAction = PuzzleState.MOVE_DOWN;
					PuzzleState.performAction(myState, PuzzleState.MOVE_DOWN);
					// System.out.println("down");
				}
				totalMoves++;
			} catch (RuntimeException e) {
				; // illegal move
			}
		}
		return myState;
	}

	/**
	 * Check if the actions solve the given puzzle.
	 * 
	 * @param myState a problem
	 * @param actions an array of Action
	 */
	public static boolean checkActions(PuzzleState myState, Action[] actions) {
		// create an initial fiteen puzzle state by first generating the goal
		// config
		for (int i = 0; i < actions.length; i++) {
			try {
				PuzzleState.performAction(myState, actions[i]);
			} catch (RuntimeException e) {
				return false; // illegal move
			}
		}

		// check whether myState is the goal state.
		return myState.goal();
	}

	/**
	 * Prints out the results of a search
	 * @param algorithmName Descriptive name of algorithm used
	 * @param state Current state of puzzle
	 * @param actions An array of actions used to generate the state
	 */
	public static void printResults(String algorithmName, PuzzleState state,
			Action[] actions) {
		System.out.println("Solution " + algorithmName + " :-------------");
		PuzzleState myState = new PuzzleState(state);
		for (int i = 0; i < actions.length; i++) {
			System.out.println((i + 1) + ": " + actions[actions.length - 1 - i]);
			PuzzleState.performAction(myState, actions[actions.length - 1 - i]);
			System.out.println(myState.toString());
		}
	}
}
