package search.fifteen;

import java.util.List;

import search.Action;
import search.State;
import search.util.Tiles;

public class H2Node extends SearchNode {
	
	/**
	 * Constructor
	 * @param state Initial puzzle state
	 */
	public H2Node(PuzzleState state) {
		super(state);
		calculateValue();
	}

	/**
	 * Constructor
	 * @param parent Parent of node
	 * @param action Action to move to
	 * @param state Resulting state of the action
	 */
	public H2Node(H2Node parent, Action action, State state) {
		super(parent, action, state);
		calculateValue();
	}

	/**
	 * Calculates the total heuristic value of the puzzle
	 */
	protected void calculateValue() {
		
		PuzzleState state = this.getState();
		PuzzleState goalState = new PuzzleState();
		int[] tile, goalTile;
		
		// first component: find Manhattan distance for first wrong number
		// and apply a high weight to it
		for (int i = 1; i < 16; i++) {
			tile = Tiles.findTile(i, state);
			goalTile = Tiles.findTile(i, goalState);
			if (tile[0] == goalTile[0] && tile[1] == goalTile[1])
				continue;
			value += Math.abs(tile[0] - goalTile[0]) + Math.abs(tile[1] - goalTile[1]);
			break;
		}
		
		// second component: distances of adjacent tiles
		int[] whiteTile = Tiles.findTile(0, state);
		List<int[]> adjacentTiles = Tiles.findAdjacent(state,
				whiteTile[0], whiteTile[1]);
		
		for (int[] adjacentTile : adjacentTiles) {
			tile = Tiles.findTile(
					state.tiles[adjacentTile[0]][adjacentTile[1]], state);
			goalTile = Tiles.findTile(
					state.tiles[adjacentTile[0]][adjacentTile[1]], goalState);
			
			value += (Math.abs(tile[0] - goalTile[0]) + Math.abs(tile[1] - goalTile[1]));
		}
		
		if (!greedy)
			value += (int) this.getCost();
		//System.out.println("Value = " + value);
	}
}
