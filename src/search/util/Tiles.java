package search.util;

import java.util.ArrayList;
import java.util.List;

import search.fifteen.PuzzleState;

/**
 * Utility class for manipulating tiles on the puzzle
 */
public class Tiles {

	/**
	 * Find a tile given the number
	 * @param number Number to find
	 * @param state An instance of PuzzleState
	 * @return A size-two array containing the coordinates of the number in the puzzle 
	 */
	public static int[] findTile(int number, PuzzleState state) {
		int[] location = new int[2];
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++)
				if (state.tiles[i][j] == number) {
					location[0] = i;
					location[1] = j;
					break;
				}
		}
		return location;
	}
	
	/**
	 * Finds a list of adjacent tiles on the puzzle given a coordinate 
	 * @param state The puzzle to search on
	 * @param row Row of the coordinate
	 * @param column Column of the coordinate
	 * @return A list of adjacent tiles on north, south, east and west of the
	 * 		given tile. A tile is represented by a 2-element integer array. If
	 * 		an adjacent tile falls outside the puzzle (e.g. [0,-1]) it is not
	 * 		included.
	 */
	public static List<int[]> findAdjacent(PuzzleState state, int row, int column) {
		ArrayList<int[]> adjacentTiles = new ArrayList<int[]>();
		int[] whiteTile = Tiles.findTile(0, state);
		int row1, row2, column1, column2;
		
		row1 = whiteTile[1] - 1;
		row2 = whiteTile[1] + 1;
		column1 = whiteTile[0] - 1;
		column2 = whiteTile[1] + 1;
		
		// West
		if (column1 >= 0) {
			int[] tile = new int[2];
			tile[0] = column1;
			tile[1] = row;
			adjacentTiles.add(tile);
		}
		// South
		if (row1 >= 0) {
			int[] tile = new int[2];
			tile[0] = row1;
			tile[1] = column;
			adjacentTiles.add(tile);
		}
		// East
		if (column2 <= 3) {
			int[] tile = new int[2];
			tile[0] = column2;
			tile[1] = row;
			adjacentTiles.add(tile);
		}
		// North
		if (row2 <= 3) {
			int[] tile = new int[2];
			tile[0] = column;
			tile[1] = row2;
			adjacentTiles.add(tile);
		}
		
		return adjacentTiles;
	}
}
