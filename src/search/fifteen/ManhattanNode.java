package search.fifteen;

import search.Action;
import search.State;
import search.util.Tiles;

public class ManhattanNode extends SearchNode {
	
	/**
	 * Constructor
	 * @param state Initial puzzle state
	 */
	public ManhattanNode(PuzzleState state) {
		super(state);
		calculateValue();
	}
	
	/**
	 * Constructor
	 * @param parent Parent of node
	 * @param action Action to move to
	 * @param state Resulting state of the action
	 */
	public ManhattanNode(ManhattanNode parent, Action action, State state) {
		super(parent, action, state);
		calculateValue();
    }
	
	/**
	 * Calculates the total Manhattan distance of the puzzle
	 */
	protected void calculateValue() {
		PuzzleState state = this.getState();
		PuzzleState goalState = new PuzzleState();
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				// finds the actual position of the number on the puzzle
				int[] tile = Tiles.findTile(goalState.tiles[i][j],
						state);
				// calculate the Manhattan distance between actual position
				// and goal position
				value += (Math.abs((tile[0] - i)) + Math.abs((tile[1] - j)));
			}
		}
	}
}
