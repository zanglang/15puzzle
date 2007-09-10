/**
 * Created on 06/09/2007 9:53:40 PM
 * @author s4089901
 */
package search.fifteen;

import search.Action;
import search.State;

/**
 * Node wrapper for Misplaced Tiles search
 */
class MisplacedNode extends SearchNode {
	/**
	 * Constructor
	 * @param state Initial puzzle state
	 */
	public MisplacedNode(PuzzleState state) {
		super(state);
		calculateValue();
	}
	
	/**
	 * Constructor
	 * @param parent Parent of node
	 * @param action Action to move to
	 * @param state Resulting state of the action
	 */
	public MisplacedNode(MisplacedNode parent, Action action, State state) {
		super(parent, action, state);
		calculateValue();
    }
	
	/**
	 * Calculates the number of misplaced tiles in state
	 */
	protected void calculateValue() {
		PuzzleState state = this.getState();
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				if (state.tiles[i][j] != i * 4 + j + 1 &&
						!(i == 3 && j == 3))
					value++;
		// increment path cost to value
		//value += this.getCost();
			
		if (!greedy)
			value += (int) this.getCost();
	}
}