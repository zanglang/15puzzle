package search.fifteen;

import search.Action;
import search.State;
import search.util.Tiles;

class TestMhNode extends SearchNode {

	/**
	 * Constructor
	 * @param state Initial puzzle state
	 */
	public TestMhNode(PuzzleState state) {
		super(state);
		calculateValue();
	}

	/**
	 * Constructor
	 * @param parent Parent of node
	 * @param action Action to move to
	 * @param state Resulting state of the action
	 */
	public TestMhNode(TestMhNode parent, Action action, State state) {
		super(parent, action, state);
		calculateValue();
	}

	/**
	 * Calculates the total heuristic value of the puzzle
	 */
	protected void calculateValue() {
		
		int DDistance;	// distance from node to blank square
		int MDistance;	// Manhattan distance
		PuzzleState goalState = new PuzzleState();
		
		PuzzleState state = this.getState();
		int[] firstWrongNumber = new int[2];	// position of first wrong number
		boolean foundWrongNumber = false;
		
		// first component: count misplaced tiles
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++) {
				// if tile is incorrect
				if (goalState.tiles[i][j] == state.tiles[i][j])
					continue;
				// otherwise increment heuristic value
				value++;
				
				// save position
				if (foundWrongNumber == false) {
					foundWrongNumber = true;
					firstWrongNumber[0] = i;
					firstWrongNumber[1] = j;
				}
			}
		
		// second component: count Manhattan distance of first wrong number
		// and actual position in puzzle
		int[] tile = Tiles.findTile(
				goalState.tiles[firstWrongNumber[0]][firstWrongNumber[1]],
				state);
		MDistance = Math.abs((tile[0] - firstWrongNumber[0])) +
				Math.abs((tile[1] - firstWrongNumber[1]));
		
		// third component: count Manhattan distance of wrong number from blank tile
		int[] whiteTile = Tiles.findTile(0, state);
		DDistance = Math.abs((whiteTile[0] - firstWrongNumber[0])) +
				Math.abs((whiteTile[1] - firstWrongNumber[1]));
		
		// add together everything
		value += DDistance + MDistance;
		if (!greedy)
			value+= (int) this.getCost();
	}
}
