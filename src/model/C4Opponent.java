package model;

import java.util.Random;
import java.util.PriorityQueue;


public class C4Opponent {
	/*
	 * This class will make a move based off the given board
	 * So far it makes the optimal move if first (middle),
	 * and blocks a win/makes a win on a column if possible
	 * otherwise it returns a random move
	 */
	public static int chooseColumn(char[][] board, char cpu){
		int ret;
		
		if(empty(board)) return 3;
		ret = blockColumn(board, cpu);
		
		if(ret != -1) return ret;
		else return new Random().nextInt(7);
	}
	
	
	//pick the column if there are three in a row with a spot to place it.
	//This blocks column wins and makes the computer win if its own piece is there (always a good move)
	private static int blockColumn(char[][] board, char cpu){
		String col;
		PriorityQueue<Integer> priorityMove = new PriorityQueue<Integer>();
		
		int one = -1;
		int two = -1;
		
		//check for three in a row
		for(int i = 0; i < board.length; i++){
			col = new String(board[i]);
			if(col.contains("rrr\u0000") || col.contains("bbb\u0000")) {
				
				priorityMove.add(1);
				one = i;
			}
		}
		//check for two in a row
		for(int i = 0; i < board.length; i++){
			col = new String(board[i]);
			if(col.contains("rr\u0000") || col.contains("bb\u0000")){
				
				priorityMove.add(2);
				two = i;
			}
			
		}
		//check for priority one and two and return the corresponding move
		
		if(priorityMove.contains(2)==true){ 
			if(priorityMove.contains(1)==true){return one;}
			return two;
			}
		

		return -1;
		
	}
	
	//Checks if the board is empty (ie first move)
	private static boolean empty(char[][] board){
		String check;
		for(char[] col : board){
			check = new String(col);
			if(!check.equals("\u0000\u0000\u0000\u0000\u0000\u0000")) return false;
		}return true;
	}
	
}
