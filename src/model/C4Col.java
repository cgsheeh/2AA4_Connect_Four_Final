//Connor Sheehan 1330964, Daniel Mandel 1303865, Group 30
package model;

/*
 * This class is an ADT for a column in Connect Four
 */

public class C4Col{
	//Game data is stored as an array of characters. 0 is the bottom.
	private char[] c;
	
	//Creates a new empty column
	public C4Col(){
		this.c = new char[6];
	}
	
	//Creates a new column from a pre-given column
	public C4Col(char[] p){
		this.c = p;
	}
	
	//Pushes a disc down a slot
	public void push(char v) throws Exception{
		if(this.c[5] != '\u0000') throw new Exception("Column is full.");
		for(int i = 0; i < 6; i++){
			if(this.c[i] == '\u0000'){
				this.c[i] = v;
				return;
			}
		}
	}
	
	//returns the internal column representation
	public char[] getRepresentation(){
		return this.c;
	}

} 
