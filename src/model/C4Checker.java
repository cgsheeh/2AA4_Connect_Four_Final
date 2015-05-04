package model;

import java.util.LinkedList;

/*
 * This module is a game board checker which looks for game setup errors, as well as game victories and returns {x, y} coordinates of
 * board errors / connect-four's.
 * 
 * Only two methods are public, the method to check for errors and the method to check for game wins.
 * 
 * Privately, there are methods to check for row wins, column wins, diagonal wins, floating pieces, extra pieces, etc.
 * 
 * To return all errors to the screen, 2 linked lists were used to quickly dump elements in order for later retrieval. 
 */

public class C4Checker {
	//this function checks the board for errors
	public static int[][] checkBoard(char[][] board){
		LinkedList<Integer> finalX = new LinkedList<Integer>();
		LinkedList<Integer> finalY = new LinkedList<Integer>();
		
		addResultToLL(checkCol(board), finalX, finalY);
		addResultToLL(checkRow(board), finalX, finalY);
		addResultToLL(checkDiag(board), finalX, finalY);
		addResultToLL(checkFloating(board), finalX, finalY);
		addResultToLL(extraPieces(board), finalX, finalY);
		
		
		return linkedListToIntArray(finalX, finalY);
	}
	
	//this function checks the board for a winner/connect four
	public static int[][] isWon(char[][] board){
		LinkedList<Integer> finalX = new LinkedList<Integer>();
		LinkedList<Integer> finalY = new LinkedList<Integer>();
		
		addResultToLL(checkCol(board), finalX, finalY);
		addResultToLL(checkRow(board), finalX, finalY);
		addResultToLL(checkDiag(board), finalX, finalY);
		
		return linkedListToIntArray(finalX, finalY);
	}
	
	//Tells you if it it t's turn
	public static boolean isThisTurn(char[][] board, char t){
		int redCount = 0, blueCount = 0;
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 6; j++){
				if(board[i][j] == 'r') redCount++;
				else if(board[i][j] == 'b') blueCount++;
			}
		}if(blueCount > redCount && t == 'r') return true;
		else if(redCount > blueCount && t == 'b') return true;
		else if(redCount == blueCount) return true;
		else return false;
	}
	
	//Tells you if the game has been drawn before it is over
	public static boolean isGameDrawn(char[][] board){
		return false;
	}
	
	//this method checks the columns for winners
	private static int[][] checkCol(char[][] board){
		int count;
		char current;
		
		LinkedList<Integer> xCoords = new LinkedList<Integer>();
		LinkedList<Integer> yCoords = new LinkedList<Integer>();

		

		//cycles through the columns
		for(int i = 0; i < 7; i++){
			//only check if the bottom is a disk, otherwise try the next column
			if(board[i][0] != '\u0000'){
				count = 1;
				//if the bottom is a disk the current starts there
				current = board[i][0];
				
				//this tells us if we are still looking for the pattern in the column
				boolean stillLooking = true;
				//start checking all disks above our current
				for(int j = 1; j < 6 && stillLooking; j++){
					//if the disks are touching the count goes up
					if(current == board[i][j]) count++;
					//if there is a blank space before count is 4 there cannot be a connect four
					else if(current == '\u0000')
						stillLooking = false;
					//if the disk is the opposite colour we change and see if this colour has a connect four
					else{
						if(current == 'b') current = 'r';
						else current = 'b';
						count = 1;
					}
					//if count reaches four add the indexes of the array to the arraylist
					if(count >= 4){
						for(int k = 0; k < 4; k++){
							xCoords.add(new Integer(i));
							yCoords.add(new Integer(j-k));
						}
						stillLooking = false;
					}
				}
			}
		}
		return linkedListToIntArray(xCoords, yCoords);
		
		//always returns this, if no errors this is NULL

	}
	/*
	 * This method checks the board for row-wise wins. will implement with strings
	 */
	private static int[][] checkRow(char[][] board){
		/*
		 * dump holds the values in each row, dumped
		 */
		LinkedList<Integer> xCoords = new LinkedList<Integer>(), yCoords = new LinkedList<Integer>();
		char[] charArray;
		String row;
		int location;
		
		
		for(int i = 0; i < 6; i++){
			charArray = new char[7];
			for(int j = 0; j < 7; j++){
				if(board[j][i] != '\u0000')
					charArray[j] = board[j][i];
				else charArray[j] = '-';
			}
			row = new String(charArray);
			if(row.indexOf("rrrr") != -1) location = row.lastIndexOf("rrrr");
			else if (row.indexOf("bbbb") != -1) location = row.lastIndexOf("bbbb");
			else location = -1;
			
			if(location != -1){
				for(int j = 0; j < 4; j++){
					xCoords.add(new Integer(location + j));
					yCoords.add(new Integer(i));
				}
			}
		}
		return linkedListToIntArray(xCoords, yCoords);
	}
	
	/*
	 * This method checks for a diagonal win
	 */
	private static int[][] checkDiag(char[][] board){
		/*
		 * xCoords and yCoords hold the (x, y) pairs in linked lists
		 * err holds the potential victory piece
		 * count holds how many piece we have
		 */
		//linked list holds x and y coords of four in a row
		LinkedList<Integer> xCoords = new LinkedList<Integer>(), yCoords = new LinkedList<Integer>();
		//keep track of the current piece searching for
		char cur;
		//count the number of char that are the same
		int count = 0;
		//start from top left
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 3; j++){


				cur = board[i][j];
				

				if(cur != '\u0000'){
					count = 1;
					//check if next value in the diagonal is the same
					if(cur == board[i+1][j+1]){
						count++;
						if(cur == board[i+2][j+2]){
							count++;
							if(cur == board[i+3][j+3]){
								count++;

								if(count >= 4){
									for(int k = 0; k < 4; k++){
										xCoords.add(new Integer(i+k));
										yCoords.add(new Integer(j+k));
									}
								}
							}else if(board[i+3][j+3] == '\u0000'){
								count = 0;
							}
						}else if(board[i+2][j+2] == '\u0000'){
							count = 0;
						}
					}

				}else if(board[i+1][j+1] == '\u0000'){
					count = 0;
				}else{
					if(cur == 'b') cur = 'r';
					else cur = 'b';
					count = 1;
				}
			}
		}
		//start from top right and to the left
		//keep track of the current piece searching for
		cur = '\u0000';
		//count the number of char that are the same
		count = 0;
		//start from top right
		for(int i = 6; i > 2; i--){
			for(int j = 0; j < 3; j++){


				cur = board[i][j];
				

				if(cur != '\u0000'){
					count = 1;
					//check if next value in the diagonal is the same
					if(cur == board[i-1][j+1]){
						count++;
						if(cur == board[i-2][j+2]){
							count++;
							if(cur == board[i-3][j+3]){
								count++;

								if(count >= 4){
									for(int k = 0; k < 4; k++){
										xCoords.add(new Integer(i-k));
										yCoords.add(new Integer(j+k));
									}
								}
							}else if(board[i-3][j+3] == '\u0000'){
								count = 0;
							}
						}else if(board[i-2][j+2] == '\u0000'){
							count = 0;
						}
					}

				}else if(board[i-1][j+1] == '\u0000'){
					count = 0;
				}else{
					if(cur == 'b') cur = 'r';
					else cur = 'b';
					count = 1;
				}
			}
		}
		
		
		
		
		return linkedListToIntArray(xCoords, yCoords);
	}
	
	
	/*
	 * This method detects floating pieces
	 */
	private static int[][] checkFloating(char[][] board){
		LinkedList<Integer> xCoords = new LinkedList<Integer>(), yCoords = new LinkedList<Integer>();
		String column;
		int firstFloater;
		for(int i = 0; i < 7; i++){
			column = new String(board[i]);
			column = blankToDash(column);
			if(column.indexOf("-r") != -1){
				firstFloater = column.indexOf("-r");
				xCoords.add(new Integer(i));
				yCoords.add(new Integer(firstFloater + 1));
			}if(column.indexOf("-b") != -1){
				firstFloater = column.indexOf("-b");
				xCoords.add(new Integer(i));
				yCoords.add(new Integer(firstFloater + 1));
			}
		}
		
		return linkedListToIntArray(xCoords, yCoords);
	}
	
	/*
	 * This method returns positions of extra pieces
	 */
	private static int[][] 	extraPieces(char[][] board){
		int redCount = 0, blueCount = 0, difference;
		char extra;
		LinkedList<Integer> xCoords = new LinkedList<Integer>(), yCoords = new LinkedList<Integer>();
		
		//count the pieces
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 6; j++){
				if(board[i][j] == 'r') redCount++;
				else if(board[i][j] == 'b') blueCount++;
			}
		}
		
		//determine the difference
		difference = redCount - blueCount;
		if(difference > 1){
			extra = 'r';
			
		}else if(difference < -1){
			extra = 'b';
			difference = Math.abs(difference);
		}
		else return null;
		
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 6; j++){
				if(difference > 0 && board[i][j] == extra){
					difference--;
					xCoords.add(i);
					yCoords.add(j);
				}
			}
		}
		
		return linkedListToIntArray(xCoords, yCoords);
	}
	
	/*
	 * This method converts from two linked lists into integer array (2d)
	 */
	private static int[][] linkedListToIntArray(LinkedList<Integer> xCoords, LinkedList<Integer> yCoords){
		if(xCoords.size() == 0 || yCoords.size() == 0) return null;
		Integer[] xDump = xCoords.toArray(new Integer[xCoords.size()]);
		Integer[] yDump = yCoords.toArray(new Integer[yCoords.size()]);
		int[][] returnArray = new int[xCoords.size()][2];
		for(int i = 0; i < xCoords.size(); i++){
			returnArray[i][0] = xDump[i].intValue();
			returnArray[i][1] = yDump[i].intValue();
		}return returnArray;
	}
	
	
	
	/*
	 * This method adds result to two LinkedLists of coordinates
	 */
	private static void addResultToLL(int[][] result, LinkedList<Integer> x, LinkedList<Integer> y){
		if(result != null){
			for(int i = 0; i < result.length; i++){
				x.add(new Integer(result[i][0]));
				y.add(new Integer(result[i][1]));
			}
		}
	}
	
	/*
	 * Converts from blank null characters to dashes
	 */
	private static String blankToDash(String blank){
		
		char[] blankChars = blank.toCharArray();
		for(int i = 0; i < blankChars.length; i++){
			if(blankChars[i] != 'r' && blankChars[i] != 'b') blankChars[i] = '-';
		}
		
		return new String(blankChars);
	}
	
}
