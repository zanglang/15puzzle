package search.fifteen;

import java.util.ArrayList;
import java.util.Collections;

import search.ActionStatePair;
import search.Node;

/**
 * Misplaced Tiles Search algorithm implementation
 * - generates a tree based on the number of misplaced numbers in the 15 puzzle
 */
public class MisplacedTilesSearch {

	/**
	 * Traverses the tree from an initial state to search the least moves 
	 * @param initialState Starting state of 15 puzzle board
	 * @return The node which represents the goal state of the puzzle
	 */
	public static Node traverse(PuzzleState initialState) {
		// setup fringe list
		ArrayList<MisplacedNode> fringe = new ArrayList<MisplacedNode>();
		ArrayList<PuzzleState> history = new ArrayList<PuzzleState>();
		fringe.add(new MisplacedNode(initialState));
		
		System.out.println("Start traverse. Misplaced nodes count: " + fringe.get(0).value);

		// start traversing from starting node
		while (!fringe.isEmpty()) {
			// check if currently first node in fringe is goal state, if so,
			// return because we've found a solution
			MisplacedNode head = fringe.remove(0);
			PuzzleState state = head.getState();
			if (history.contains(state))
				continue;
			else
				history.add(state);
			if (state.goal())
				return head;
			
			System.out.println("Chose node with value " + head.value +
					". Adding fringe: ");
			// add successor nodes to the fringe
			for (ActionStatePair successor : state.successor()) {
				MisplacedNode node = new MisplacedNode(head,
						successor.getAction(),
						successor.getState());
				System.out.print(node.value + " ");
				fringe.add(node);
			}
			System.out.println();
			
			System.out.print("Fringe: ");
			for (MisplacedNode node : fringe)
				System.out.print(node.value + " ");
			System.out.print(" --> ");
			
			// sort our fringe so lowest cost nodes get pushed to front
			Collections.sort(fringe);
			
			for (MisplacedNode node : fringe)
				System.out.print(node.value + " ");
			System.out.println();
		}

		// no nodes were found?
		return null;
	}
}
