//Connor Sheehan 1330964, Daniel Mandel 1303865, Group 30
package model;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/*
 * This class is an ADT for a Connect Four Board
 * It represents the game in a specific state, and has methods to gather
 * information about the state of the game
 */

public class C4Board {
	
	/*
	 * board is an array of C4Col objects, representing the game state. used to make sure moves are always legal
	 * currentPlayer is a char, representing the player currently in game
	 * charBoard represents the setup board used to draw in the circles
	 * preGame returns a value for the state of the game (is a game initialized or not)
	 * state holds a string describing the current game state (in progress, setup, winner etc)
	 */
	private C4Col[] 	board;
	private char[][] 	charBoard;
	private char 		currentPlayer;
	private String 		message;
	private int 		state;
	private int[][] 	errors;
	private char		cpuColour;
	
	/*
	 * Accessors for fields
	 */
	public char 	whoseTurn(){ return this.currentPlayer; }
	public int 		getState(){	return this.state;}
	public char[][] getBoard() { return this.charBoard; }
	public String 	getMessage(){return this.message;}
	public void 	setMessage(String message){this.message = message;}
	public int[][]  getErrors(){return this.errors;}
	public char		getCPU(){ return this.cpuColour;}
	
	
	
	/*
	 * This constructor will be used to create a new setup state.
	 */
	public C4Board(){
		this.currentPlayer = randomizePlayer();
		this.state = 0;
		this.charBoard 	= new char[7][6];
		this.message 	= "GAME SETUP - LEGAL GAME";
		this.board 		= null;
		this.errors 	= null;
		this.cpuColour  = '\u0000';
	}
	
	/*
	 * This constructor will start a new blank game
	 */
	public C4Board(char cpu){
		this.currentPlayer 	= randomizePlayer();
		this.charBoard 		= new char[7][6];
		this.message 		= "GAME IN PROGRESS!";
		this.board 			= new C4Col[7];
		for(int i = 0; i < this.board.length; i ++) 
			  this.board[i] = new C4Col();
		this.state 			= 1;
		this.errors 		= null;
		this.cpuColour		= cpu;
	}
	
 	
	/*
	 * This constructor sets up a pre-made game. will not be called unless the game is valid!
	 */
	public C4Board(char[][] s, char player, char cpu){
		this.currentPlayer 	= player;
		this.state 			= 1;
		this.message 		= "GAME IN PROGRESS!";
		
		this.board 			= new C4Col[7];
		for(int i = 0; i < 7; i++) 
			this.board[i] 	= new C4Col(s[i]);
		this.errors 		= null;
		this.charBoard 		= this.getRepresentation();
		this.cpuColour 		= cpu;
	}
	
	
	/*
	 * This method places the char currentPlayer on the stack specified by c
	 */
	public void makeMove(int c){
		try{
			this.board[c].push(this.currentPlayer);
	
			
			this.charBoard = this.getRepresentation();
			this.errors = C4Checker.isWon(this.charBoard);
			if(this.errors == null){
				this.message = "GAME IN PROGRESS!";
				this.switchTurn();
			}else{
				String winner;
				if(this.currentPlayer == 'r') winner = "RED";
				else winner = "BLUE";
				this.message = winner + " IS THE WINNER!";
				this.state = 2;
			}
		}catch(Exception e){
			if(this.cpuColour == this.currentPlayer) this.computerMove();
			else this.message = e.getMessage();
		}
	}
	
	
	//This method places the disk in a given position on the start board
	public void placeDisk(int x, int y){
		if(this.charBoard[x][y] == this.currentPlayer){
			this.charBoard[x][y] = '\u0000';
		}else{
			this.charBoard[x][y] = this.currentPlayer;
		}
		
		//We call find errors to check to board representation for errors and store them for drawing
		this.checkAndUpdate();
	}
	
	/*
	 * This method runs the checker and updates to determine a legal game
	 */
	private void checkAndUpdate(){
		this.errors = C4Checker.checkBoard(this.charBoard);
		if(this.errors == null && C4Checker.isThisTurn(this.getRepresentation(), this.whoseTurn())) this.message = "GAME SETUP - LEGAL GAME";
		else this.message = "GAME SETUP - FIX ERRORS AND RETRY";
	}
	
	
	/*
	 * This method switches the turn
	 */
	public void switchTurn(){
		if(this.currentPlayer == 'r') this.currentPlayer = 'b';
		else if(this.currentPlayer == 'b') this.currentPlayer = 'r';
		if(this.state == 0)this.checkAndUpdate();
	}
	

	//Returns the board as a char[][] array
	public char[][] getRepresentation(){
		if(this.state == 0) return this.charBoard;
		else{
			char[][] ret = new char[7][];
			for(int i = 0; i < 7; i++) ret[i] = this.board[i].getRepresentation();
			return ret;
		}
	}
	
	//Saves games
	public void saveGame(File saveFile){
		try {
			PrintWriter writer = new PrintWriter(saveFile);
			char[][] representation = this.getRepresentation();
			writer.println(this.whoseTurn());
			for(char[] col : representation){
				writer.println(col);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		}
	}
	
	//loads a player from a game
	public char loadPlayer(File loadFile){
		try{
			Scanner firstLetter = new Scanner(loadFile);
			char player = firstLetter.nextLine().charAt(0);
			firstLetter.close();
			return player;
		}catch(FileNotFoundException f){ 
			//f.printStackTrace(); 
			return '\u0000';
		}
	}
	
	//Loads a game
	public char[][] loadGame(File loadFile){
		try {
			Scanner reader = new Scanner(loadFile);
			char[][] newBoard = new char[7][6];
			int count = 0;
			reader.nextLine();
			while(reader.hasNextLine() && count <= 7){
				newBoard[count++] = reader.nextLine().toCharArray();
			}reader.close();
			return newBoard;
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	
	//Randomizes the player
	private char randomizePlayer(){
		if(Math.round(new Random().nextDouble()) == 0) return 'b';
		else return 'r';
	}
	
	//allows the computer to make a move
	public void computerMove(){
		this.makeMove(C4Opponent.chooseColumn(this.getRepresentation(), this.getCPU()));
		
	}
	
	//returns true if it is the computer's move
	public boolean isCPUTurn(){
		return this.cpuColour == this.currentPlayer;
	}
}
