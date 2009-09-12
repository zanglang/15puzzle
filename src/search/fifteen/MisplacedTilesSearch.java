package search.fifteen;

import java.util.ArrayList;
import java.util.Collections;

import search.ActionStatePair;
import search.Node;
import search.State;

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
		ArrayList<String> history = new ArrayList<String>();
		fringe.add(new MisplacedNode(initialState));
		
		System.out.println("Start traverse. Misplaced nodes count: " + fringe.get(0).value);

		// start traversing from starting node
		while (!fringe.isEmpty()) {
			// check if currently first node in fringe is goal state, if so,
			// return because we've found a solution
			MisplacedNode head = fringe.remove(0);
			PuzzleState state = head.getState();
			// also check if state is already in closed list
			String stateCode = encode(state);
			if (history.contains(stateCode))
				continue;
			else
				history.add(stateCode);
			if (state.goal())
				return head;
			
			System.out.println("Chose node with value " + head.value +
					". Adding fringe: ");
			// add successor nodes to the fringe
			for (ActionStatePair successor : state.successor()) {
				MisplacedNode node = new MisplacedNode(head,
						successor.getAction(),
						successor.getState());
				// check if successor state is already in open or closed list
				if (fringe.contains(node) || history.contains(encode((PuzzleState)successor.getState())))
					continue;
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
	
	public static String encode(PuzzleState state) {
		StringBuffer code = new StringBuffer();
		for (int[] i : state.tiles) {
			for (int tile : i) {
				code.append('a' + tile);
			}
		}
		
		return code.toString();
	}
}
