package search.fifteen;

import search.Action;
import search.Node;
import search.State;

/**
 * Abstract class representing the nodes on a search path. Implements Comparable
 * so it may be sorted in a Java Collections structure
 * @see search.Node
 */
public abstract class SearchNode extends Node implements Comparable<SearchNode> {
	// heuristic value of the node
	public int value;
	// whether greedy algorithm is used for this search
	// (for calculating the heuristic value)
	public static boolean greedy = true;

	/**
	 * Constructor
	 * @param state Initial puzzle state
	 */
	public SearchNode(PuzzleState state) {
		super(state);
		calculateValue();
	}

	/**
	 * Constructor
	 * @param parent Parent of node
	 * @param action Action to move to
	 * @param state Resulting state of the action
	 */
	public SearchNode(SearchNode parent, Action action, State state) {
		super(state, parent, action, parent.getCost() + state.pathcost(action));
		calculateValue();
	}

	/**
	 * Abstract function for calculating the heuristic value of the node
	 */
	protected abstract void calculateValue();

	/**
	 * Returns the puzzle state stored represented by this node
	 */
	public PuzzleState getState() {
		return (PuzzleState) super.getState();
	}

	/**
	 * Comparing between two nodes for closest search
	 */
	public int compareTo(SearchNode o) {
		return o.value == value ? 0 : o.value < value ? 1 : -1;
	}
}
