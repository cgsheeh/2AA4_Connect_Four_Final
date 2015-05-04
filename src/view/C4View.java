//Connor Sheehan 1330964, Daniel Mandel 1303865, Group 30
package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.C4Board;

public class C4View extends JFrame{

	private static final long serialVersionUID = 260315L;
	
	/*
	 * This is the view class for the Connect Four Game
	 * window is the overall window holding the content
	 * boardDraw is the board/chip area
	 * left and right are the side panels
	 * left/rightCircle are the circle buttons
	 * left/rightArrow are the turn indicator arrows
	 * c4 is the text "Connect Four"
	 * bottom is the buttons and messages
	 * loadOrSave are the load/save buttons
	 * 
	 * view is the drawing components, represented as a JFrame
	 * model is the backend/game components, which updates the view
	 * the "Controllers" are listener objects attached to each specific panel.
	 */
	
	private JPanel window;
	private JLabel c4;
	private JLabel boardDraw;
	private JPanel left;
		private JLabel leftCircle;
		private JLabel leftArrow;
		
	private JPanel right;
		private JLabel rightCircle;
		private JLabel rightArrow;
		
	private JPanel bottom;
		private JPanel loadOrSave;
		private JPanel gameStarts;
		private ButtonGroup playerSelectorGroup;
		private JPanel playerSelector;
		private JRadioButton onePlayer;
		private JRadioButton twoPlayer;
		private JButton load;
		private JButton save;
		private JButton newGame;
		private JButton newPre;
		private JLabel gameState;
	
	private static C4Board model;
	private static C4View view;
	

	//This class draws the initial frame on the screen
	private C4View(){
		
		/*
		 * THINGS TO DO
		 * 1. Set up the main frame (ie in Windows) 						- DONE
		 * 2. Set up the main content window (ie the panel in BorderLayout)	- DONE
		 * 3. Set up the 5 components of the BorderLayout					- DONE
		 * 		A.Top Pane													- DONE
		 * 		B.Board pane												- DONE
		 * 		C.Button Panes												- DONE
		 * 			s.circles
		 * 			t.arrows
		 * 		D.Bottom Pane												- TBC
		 * 			X.Message
		 * 			Y.New Game button
		 * 			Z.predetermined game button
		 * 4.Add all the components to the main content window				- DONE
		 * 5.Add the content window to the frame							- DONE
		 * 6.Wait for action, based on the action change the model			- TBC SEE ABOVE
		 * 7.Redraw!
		 */
		
		//1. Set the frame window behaviour and size
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//2. Setup the main content window
		this.window = new JPanel();
		this.window.setLayout(new BorderLayout());
		this.window.setBorder(new EmptyBorder(5,5,5,5));
		
		//3.
		//A. is already set up above, as it never changes
		this.c4 = new JLabel("2AA4 Connect Four!!!", SwingConstants.CENTER);
		this.c4.setFont(new java.awt.Font("monospaced", Font.BOLD, 32));
		
		
		//B. Setup the board Panel
		this.boardDraw = new JLabel();
		this.boardDraw.setIcon(new ImageIcon(getClass().getResource("/c4template.png")));
		this.boardDraw.setAlignmentX(CENTER_ALIGNMENT);
		this.boardDraw.setAlignmentY(CENTER_ALIGNMENT);
		
		
		//C. Set up the two button panes. They are grids
		this.left = new JPanel(new GridLayout(2,0));
		this.right = new JPanel(new GridLayout(2,0));
		
		//s. Set up the two circles and the two arrows
		this.leftCircle = new JLabel();
		this.rightCircle = new JLabel();
		this.leftArrow = new JLabel();
		this.rightArrow = new JLabel();
		
		//load the images onto the labels
		this.leftCircle.setIcon(new ImageIcon(getClass().getResource("/red.jpg")));
		this.rightCircle.setIcon(new ImageIcon(getClass().getResource("/blue.jpg")));
		this.leftArrow.setIcon(new ImageIcon(getClass().getResource("/arrow.jpg")));
		this.rightArrow.setIcon(new ImageIcon(getClass().getResource("/arrow.jpg")));
		
		//put the labels on the grid
		this.left.add(this.leftCircle, "Left Circle");
		this.left.add(this.leftArrow, "Left Arrow");
		this.right.add(this.rightCircle, "Right Circle");
		this.right.add(this.rightArrow, "Right Arrow");
		
		//get the turn, set the marker based off the turn
		if(model.whoseTurn() == 'r'){
			this.leftArrow.setEnabled(true);
			this.rightArrow.setEnabled(false);
		}else if(model.whoseTurn() == 'b'){
			this.leftArrow.setEnabled(false);
			this.rightArrow.setEnabled(true);
		}
		
		
		//D. Set the bottom pane. It is a FlowLayout object
		this.bottom = new JPanel(new FlowLayout());
		
		this.playerSelectorGroup = new ButtonGroup();
		this.playerSelector = new JPanel(new FlowLayout());
		this.onePlayer = new JRadioButton("CPU Game");
		this.twoPlayer = new JRadioButton("2P Game", true);
		this.playerSelector.add(this.onePlayer);
		this.playerSelectorGroup.add(this.onePlayer);
		this.playerSelector.add(this.twoPlayer);
		this.playerSelectorGroup.add(this.twoPlayer);
		
		this.loadOrSave = new JPanel(new FlowLayout());
		this.load = new JButton("Load Game");
		this.save = new JButton("Save Game");
		this.loadOrSave.add(this.load);
		this.loadOrSave.add(this.save);
		
		this.gameState = new JLabel(model.getMessage());
		this.gameState.setFont(new java.awt.Font("monospaced", Font.BOLD, 16));
		
		this.gameStarts = new JPanel(new FlowLayout());
		this.newGame = new JButton("New Game");
		this.newPre = new JButton("Resume Game");
		this.gameStarts.add(this.newGame, "New Game");
		this.gameStarts.add(this.newPre, "Resume Game");
		
		
		this.bottom.add(this.playerSelector);
		this.bottom.add(this.gameStarts);
		this.bottom.add(this.loadOrSave);
		this.bottom.add(gameState);
		
		//Set
		//this.bottom.setLayout(new FlowLayout());
		this.boardDraw.setVisible(true);
		this.c4.setVisible(true);
		
		
		//This code adds all components to the main window
		this.window.add(c4, BorderLayout.NORTH);
		this.window.add(right, BorderLayout.EAST);
		this.window.add(left, BorderLayout.WEST);
		this.window.add(bottom, BorderLayout.SOUTH);
		this.window.add(boardDraw, BorderLayout.CENTER);
		
		//This code adds the main window to the computer screen
		this.add(window);
		this.setTitle("2AA4 Connect Four!");
		this.window.setVisible(true);
		this.pack();
		this.setResizable(false);
		
		
		/*
********************************************************************************************************
*				CONTROLLERS HERE! CLICKS ON DIFFERENT LABELS CHANGE THE MODEL, MODEL UPDATES VIEW!
*
*				Each object has a MouseListener object attached to it, that waits for a mouse click to register.
*				On click, the mouseClicked method will perform the appropriate updates to the model.
*				view.updateView() is then called to draw the game updates to the screen
		 */
	
		//red circle controller code
		this.leftCircle.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent evt){
				int state = model.getState();
				if(state == 0 && model.whoseTurn() == 'b') model.switchTurn();
				else return;
				view.updateView();
			}
		});
		
		
		//blue circle click controller code
		this.rightCircle.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent evt){
				int state = model.getState();
				if(state == 0 && model.whoseTurn() == 'r') model.switchTurn();
				else return;
				view.updateView();
			}
		});
		
		//board click controller code
		this.boardDraw.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent evt){
				int x = evt.getX();
				int y = evt.getY();
				int state = model.getState();
				int[] location = getBoardCoord(x, y);
				
				
				if(location[0] == -1 || location[1] == -1 || model.isCPUTurn()) return;
				if(state == 0){
					model.placeDisk(location[0], location[1]);
				}else if(state == 1){
					model.makeMove(location[0]);
				}else if(state == 2){
					model = new C4Board();
				}
				view.updateView();
				
				
				//After updating the view we can check if the computer should make a move
				
				if(model.isCPUTurn()) view.waitForComputer();
			}
		});
		
		//New Game Button Controller Code
		this.newGame.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent evt){
				char cpu = '\u0000';
				if(view.is1PSelected()){
					cpu = view.selectCPU();
					if(cpu == 'c') return;
				}
				model = new C4Board(cpu);
				view.updateView();
				if(model.isCPUTurn()) view.waitForComputer();
			}
		});
		
		//Pre Game Controller Code
		this.newPre.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent evt){
				int state = model.getState();
				char cpu = '\u0000';
				if(state == 0 && model.getErrors() == null) {
					if(view.is1PSelected()){
						cpu = view.selectCPU();
						if(cpu == 'c') return;
					}
					model = new C4Board(model.getRepresentation(), model.whoseTurn(), cpu);
				}else if (state == 1 || state == 2){
					model = new C4Board();
				}
				view.updateView();
				if(model.isCPUTurn()) view.waitForComputer();
			}
		});
		
		//save game controller code
		this.save.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent evt){
				//Open's a JFileChooser to select the save game code
				JFileChooser saver = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Connect Four Game Files (*.txt, *.c4g)", "txt", "c4g");
				saver.setFileFilter(filter);
				int returnVal = saver.showOpenDialog(getParent());
				if(returnVal == JFileChooser.APPROVE_OPTION){
					model.saveGame(saver.getSelectedFile());
				}
			}
		});
		
		//load game controller code
		this.load.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent evt){
				//this code will load a file to a game state
				JFileChooser loader = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Connect Four Game Files (*.txt, *.c4g)", "txt", "c4g");
				loader.setFileFilter(filter);
				int returnVal = loader.showOpenDialog(getParent());
				char cpu = '\u0000';
				if(returnVal == JFileChooser.APPROVE_OPTION){
					try{
						char player = model.loadPlayer(loader.getSelectedFile());
						char[][] game = model.loadGame(loader.getSelectedFile());
						if(view.is1PSelected())
							cpu = view.selectCPU();
						model = new C4Board(game, player, cpu);
					}catch(NullPointerException e){}
				}view.updateView();
				if(model.isCPUTurn()) view.waitForComputer();
			}
		});
		
		
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	//this method draws a circle on the boardDraw panel at the given xy-coordinate, based on the model
	private void drawCircle(int x, int y, char c){
		Graphics g = this.boardDraw.getGraphics();
		if(c == 'r'){
			g.setColor(Color.RED);			
		}else if(c == 'b'){
			g.setColor(Color.BLUE);
		}else{
			g.setColor(Color.WHITE);
		}
		
		g.fillOval(x * 90 + 19, (5 - y) * 80 + 15,  60, 60);
	}
	
	//this method draws the error square to the board in the specified location
	private void drawError(int x, int y){
		Graphics g = this.boardDraw.getGraphics();
		g.setColor(Color.BLACK);
		g.fillOval(x * 90 + 37, (5 - y) * 80 + 37,  20, 20);
	}

	
	
	//updates the view on click action.
	private void updateView(){
		//get all required information for drawing
		char turn = model.whoseTurn();
		char[][] rep = model.getRepresentation();
		int[][] errs = model.getErrors();
		
		
		//Switch the arrow to match whose turn it is
		if(turn == 'r'){
			this.leftArrow.setEnabled(true);
			this.rightArrow.setEnabled(false);
		}else if(turn == 'b'){
			this.leftArrow.setEnabled(false);
			this.rightArrow.setEnabled(true);
		}
		
		
		/*
		 * draw the circles here!
		 */
		for(int i = 0; i < 7; i++)
			for(int j = 0; j < 6; j++)
				drawCircle(i,j, rep[i][j]);
		
		//draw errors to the screen, if any, on top of the squares that have been drawn
		if(errs != null){
			for(int i = 0; i < errs.length; i++)
				drawError(errs[i][0], errs[i][1]);
			
		}

		
		//Update the message box
		this.gameState.setText(model.getMessage());
		int state = model.getState();
		if(state == 0) this.newPre.setText("Resume Game");
		if(state == 1) this.newPre.setText("Return to Setup");
	}
	
	
	//Analyzes clicks on the board and determines grid coordinates. returns -1 if error/not found
	private int[] getBoardCoord(int x, int y){
		int[] ret = {-1, -1};
		int count = 0;
		for(int i = 95; i < 700 && ret[0] == -1; i += 90){
			if(i - 95 < x && x < i)
				ret[0] = count;
			else
				count++;
			
		}
		
		count = 0;
		for(int i = 420; i > -20 && ret[1] == -1; i -= 80){
			if(i < y && y < i + 80)
				ret[1] = count;
			else
				count++;
			
		}
		return ret;
	}
	
	//opens a dialog and gets a user selection for CPU colour
	private char selectCPU(){
		String[] options = {"Red", "Blue"};
		int selection = JOptionPane.showOptionDialog(view, "Select a colour for the CPU:", "Colour Select", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
		if(selection == JOptionPane.NO_OPTION) return 'b';
		else if(selection == JOptionPane.YES_OPTION) return 'r';
		else if(selection == JOptionPane.CLOSED_OPTION) return 'c';
		else return '\u0000';
	}
	
	//returns whether or not cpu mode is selected
	public boolean is1PSelected(){
		return this.onePlayer.isSelected();
	}
	
	
	//Creates the initial view
	private static void createView(){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try{
					view = new C4View();
					view.setVisible(true);
				}catch(Exception e){
					e.printStackTrace();
				}
					
			}
		});
	}
	
	
	/*
	 * Some code to get around the redraw
	 * Essential allows a separate thread to sleep
	 * then make a computer move, replicating a small pause in the game
	 * to think about a move.
	 * If this is not implemented, the game draws the computers turn too
	 * fast and it looks odd.
	 */
	private void waitForComputer(){
		new Thread(new Runnable(){public void run(){
			try {
				view.updateView();
				Thread.sleep(2000);
				model.computerMove();
				view.updateView();
				Thread.currentThread().join();
			} catch (InterruptedException e) {
				System.out.println("PROBLEM WITH WAITING");
				
			}
		}}).start();		
	}
	
	/*
	 * The main method. initializes model to the pre-game setup state
	 * draws the pre-game state to the screen
	 */
	public static void main(String[] args) {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e) {
			// handle exception
		}
		catch (ClassNotFoundException e) {
			// handle exception
		}
		catch (InstantiationException e) {
			// handle exception
		}
		catch (IllegalAccessException e) {
			// handle exception
		}
		model = new C4Board();
		createView();
	}
}