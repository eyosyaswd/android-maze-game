package edu.wm.cs.cs301.amazebyeyosyas.generation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * This class has the responsibility to create a maze of given dimensions (width, height) 
 * together with a solution based on a distance matrix.
 * The MazeBuilder implements Runnable such that it can be run a separate thread.
 * The MazeFactory has a MazeBuilder and handles the thread management.   
 * Eller's algorithm is used to create the maze.
 * 
 * @author Eyosyas
 */
public class MazeBuilderEller extends MazeBuilder implements Runnable{
	
	/**
	 * MazeBuilderEller constructor for a randomized maze generation
	 */
	public MazeBuilderEller() {
		super();
		System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
	}
	
	/**
	 * MazeBuilerEller constructor with parameter that allows choice for a deterministic or random maze
	 * @param det
	 */
	public MazeBuilderEller(boolean det) {
		super(det);
		System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
	}
	
	// a 2D array is used to keep track of the sets in the maze; i.e. if two cells have the same number, then they belong to the same set
	protected int[][] sets;
	
	/**
	 * Generates pathways into the maze using Eller's algorithm. I used http://weblog.jamisbuck.org/2010/12/29/maze-generation-eller-s-algorithm to help me code the algorithm.
	 * My code is basically set up the same way that Jamis Buck explains the algorithm. I used four smaller methods (joinRandomSets(), verticalConnections(), fleshOut(), and joinSetsOfLastRow()) to separate the work the algorithm does. 
	 * There are six main steps to the algorithm as listed by Buck, which is how this code is also organized.
	 */
	@Override
	protected void generatePathways() {
		sets = new int[width][height]; 		// instantiates 2D array based on dimension of maze
		
		// Eller's algorithm begins here
		assignFirstRow(width); 	// step 1: assign each cell of the first row a number (starting from 1 and incrementing by 1) indicating the set it belongs to
		
		for (int i = 0; i < height - 1; i++) { // step 5: repeat steps 2-4 for all rows except last row
			joinRandomSets(i); 		// step 2: randomly join adjacent cells that belong to different sets
			verticalConnections(i);	// step 3: for each set in a row, create at least one random vertical connection with the row below 
			fleshOutRow(i+1); 		// step 4: fill in new numbers to the rest of the cells that aren't in sets already
		}		
		
		joinSetsOfLastRow(height - 1); 	// step 6: for the last row, join all adjacent but disjoint cells
	}
	
	/**
	 * Iterates through the first row of the maze and assign each cell a new set number starting from 1 and iterating by 1.
	 * @param width of the maze
	 */
	private void assignFirstRow(int width) {
		for (int i = 0; i < width; i++) {
			sets[i][0] = i + 1;
		}
	}

	/**
	 * Iterates through a row and randomly joins adjacent cells that belong to different sets.
	 * If a wall is deleted, the cell of the right joins the same set that the cell on the left is in.
	 * @param row index of a maze
	 */
	private void joinRandomSets(int row) {
		Random random = new Random();
		for (int i = 0; i < width - 1; i++) { 	// go through each cell
			Wall rightWall = new Wall(i, row, CardinalDirection.East); 	
			if (sets[i][row] != sets[i+1][row] && cells.canGo(rightWall)) {	// if cell on right is not part of cell on left
				if (random.nextBoolean() == true) { 	// pick a random boolean and if true
					cells.deleteWall(rightWall);	// delete wall between them
					rename(row,sets[i+1][row],sets[i][row]);
					
					//sets[i+1][row] = sets[i][row]; 	// merge the set of the left cell with the set of the right cell
				}
			}
		}
	}
	
	/**
	 * Iterates through a row of an array and replaces all values of before with the value after
	 * @param row index of a maze
	 * @param before the set number that is being replaced
	 * @param after the set number that will replace before
	 */
	private void rename(int row, int before, int after) {
		for (int i = 0; i < width; i++) {
			if (sets[i][row] == before) {
				sets[i][row] = after;
			}
		}
	}

	// verticalConnections() {}: a method that iterates through a row, it randomly chooses at least one cell from one set and makes the cell below it also part of that set (it deletes the wall between the two cells)
	/**
	 * Iterates through a row and randomly chooses at least one cell from all cells in the same set on a row. 
	 * It deletes the walls between that chosen cell and the cell below it and makes the cell below it part of it's set.
	 * @param row index of a maze
	 */
	private void verticalConnections(int row) {
		// create a hash that maps the column index of cells to their set (we don't need the row index because we only look at one row and we are given that row)
		HashMap<Integer, ArrayList<Integer>> setsMap = new HashMap<Integer, ArrayList<Integer>>();
		for (int i = 0; i < width; i++) {
			if (setsMap.get(sets[i][row]) == null) {
				setsMap.put(sets[i][row], new ArrayList<Integer>());
			}
			setsMap.get(sets[i][row]).add(i);
		}
		
		// iterate through each column indexes and randomly pick some to have vertical connections
		// have a boolean to check if at least one vertical connection was achieved from a set
		for (ArrayList<Integer> singleSetList: setsMap.values()) {
			boolean verticalConnectionAchieved = false;
			while (verticalConnectionAchieved == false) {
				verticalConnectionAchieved = randomConnection(singleSetList, row, verticalConnectionAchieved);
			}
		}
	}
	
	/**
	 * Randomly decides if a cell should have a vertical connection with the cell below it and return a boolean to tell if there was a connection or not.
	 * @param singleSetList ArrayList that contains column indices of cells in a single row that are in the same set
	 * @param row index of a maze
	 * @param verticalConnectionAchieved determines whether a vertical connection was actually achieved
	 * @return verticalConnectionAchieved return true if a vertical connection occurred and false if it didn't
	 */
	private boolean randomConnection(ArrayList<Integer> singleSetList, int row, boolean verticalConnectionAchieved) {
		Random random = new Random();
		int col;
		for (int i = 0; i < singleSetList.size(); i++) {
			col = singleSetList.get(i);
			Wall bottomWall = new Wall(col, row, CardinalDirection.South);
			if (random.nextBoolean() == true /**&& cells.canGo(bottomWall)*/) {
				cells.deleteWall(bottomWall);
				sets[col][row+1] = sets[col][row];
				verticalConnectionAchieved = true;
			}
		}
		return verticalConnectionAchieved;
	}

	/**
	 * Iterates through a row and if there is a cell that is not part of a set, it assigns it a new number (and therefore puts it in its own set)
	 * @param row index of a maze
	 */
	private void fleshOutRow(int row) {
		int newSetValue = max(row) + 1; 	// figure out what value to give a new set (one more than the largest value of a set in the row)
		
		for (int i = 0; i < width; i++) { 	// iterate through each cell of a row
			if (sets[i][row] == 0) { 	// if the value of a cell is 0 (it doesn't have a set yet)
				sets[i][row] = newSetValue; 	// give the cell in a new set
				newSetValue++; 	// iterate newSetValue so the next cell not in a set can have a different value
			}
		}	
	}
	
	/**
	 * Finds the largest value in a row. I use this method in the fleshOutRow() method to help me find a new set to give to a cell.
	 * Code to find largest value taken from: http://www.quickprogrammingtips.com/java/find-largest-number-in-an-array-using-java.html
	 * @param row is an the index of a row
	 * @return largest value in the array
	 */
	protected int max(int row) {
		int largest = sets[0][row];
		for(int i = 1; i < width; i++) {
			if(sets[i][row] > largest) {
				largest = sets[i][row];
			}
		}
		return largest;
	}
	
	/**
	 * Connects all adjacent but disjoint cells of the last row.
	 * In simpler words, it compares two adjacent cells and if they are not in the same set, it deletes the wall between them;
	 * however, if they are in the same set it does nothing to the wall between them
	 * @param lastRow index of a maze
	 */
	private void joinSetsOfLastRow(int lastRow) {
		for (int i = 0; i < width - 1; i++) {
			if (sets[i][lastRow] != sets[i+1][lastRow]) {
				Wall rightWall = new Wall(i, lastRow, CardinalDirection.East);
				cells.deleteWall(rightWall);
				rename(lastRow, sets[i+1][lastRow], sets[i][lastRow]);
			}
		}
	}
	
}
