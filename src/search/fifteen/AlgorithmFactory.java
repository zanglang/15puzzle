package search.fifteen;

import search.Action;

/**
 * A factory class for generating search nodes
 * @see SearchNode
 */
public class AlgorithmFactory {

	/**
	 * Generates a search node given a search algorithm
	 * @param algorithm The algorithm used for the heuristic
	 * @param parent The parent of the node
	 * @param action The action taken for the node
	 * @param state The puzzle state represented by the node
	 * @return A new instance of the search node
	 */
	public static SearchNode createNode(Algorithms algorithm,
			SearchNode parent, Action action, PuzzleState state) {
		// check algorithm used
		if (algorithm.equals(Algorithms.HEURISTIC_2))
			return new H2Node((H2Node)parent, action, state);
		else if (algorithm.equals(Algorithms.MANHATTAN_DISTANCE))
			return new ManhattanNode((ManhattanNode)parent, action, state);
		else if (algorithm.equals(Algorithms.MISPLACED_TILES))
			return new MisplacedNode((MisplacedNode)parent, action, state);
		else if (algorithm.equals(Algorithms.TEST_MH))
			return new TestMhNode((TestMhNode)parent, action, state);
		// nothing was found!
		return null;
	}

	/**
	 * Generates a search node given a search algorithm
	 * @param algorithm The algorithm used for the heuristic
	 * @param initialState The puzzle state represented by the node
	 * @return A new instance of the search node
	 */
	public static SearchNode createNode(Algorithms algorithm,
			PuzzleState initialState) {
		// check algorithm used
		if (algorithm.equals(Algorithms.HEURISTIC_2))
			return new H2Node(initialState);
		else if (algorithm.equals(Algorithms.MANHATTAN_DISTANCE))
			return new ManhattanNode(initialState);
		else if (algorithm.equals(Algorithms.MISPLACED_TILES))
			return new MisplacedNode(initialState);
		else if (algorithm.equals(Algorithms.TEST_MH))
			return new TestMhNode(initialState);
		// nothing was found!
		return null;
	}
}
