/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author abtpst
 */
public class Game 
{
    	
        public static void main(String[] args)
        {
                C4 frame = new C4();
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
        }
    
}
class C4 extends JFrame implements ActionListener
{
        public static Board board = new Board();
	ComputerPlayer computer;
	char currentPlayer='B';
        public static int limit;
	private Button	btn1, btn2, btn3, btn4,b1,b2,b3,b4 ;
	private Label lblSpacer;
	MenuItem newMI, exitMI, START, redMI, blackMI;
	char[][] theArray;
	boolean	end=false;
	boolean	gameStart;
	public static final char BLANK = ' ';
	public static final char RED = 'R';
	public static final char BLACK = 'B';

	public static final int MAXROW = 5;	
	public static final int MAXCOL = 4;	

	public static final String SPACE = "                  "; // 18 spaces

	char activeColour = BLACK;
	
	public C4() 
        {
		setTitle("Connect4 By Abhijit Pratap Singh Tomar");
		MenuBar mbar = new MenuBar();
		
                Menu fileMenu = new Menu("File");
		
                START = new MenuItem("Start Game");
		START.addActionListener(this);
		fileMenu.add(START);
		
                newMI = new MenuItem("New");
		newMI.addActionListener(this);
		fileMenu.add(newMI);
		
                exitMI = new MenuItem("Exit");
		exitMI.addActionListener(this);
		fileMenu.add(exitMI);
		
                mbar.add(fileMenu);
		
                Menu optMenu = new Menu("Options");
		redMI = new MenuItem("Red starts");
		redMI.addActionListener(this);
		
                optMenu.add(redMI);
		blackMI = new MenuItem("Black starts");
		blackMI.addActionListener(this);
		optMenu.add(blackMI);
		
                mbar.add(optMenu);
		
                setMenuBar(mbar);

		// Build control panel.
		Panel panel = new Panel();

                btn1 = new Button("1");
		btn1.addActionListener(this);
		panel.add(btn1);
		lblSpacer = new Label(SPACE);
		panel.add(lblSpacer);

		btn2 = new Button("2");
		btn2.addActionListener(this);
		panel.add(btn2);
		lblSpacer = new Label(SPACE);
		panel.add(lblSpacer);

		btn3 = new Button("3");
		btn3.addActionListener(this);
		panel.add(btn3);
		lblSpacer = new Label(SPACE);
		panel.add(lblSpacer);

		btn4 = new Button("4");
		btn4.addActionListener(this);
		panel.add(btn4);
		lblSpacer = new Label(SPACE);
		panel.add(lblSpacer);

                add(panel, BorderLayout.NORTH);
		
                Panel panel2 = new Panel();

		b1 = new Button("1");
		b1.addActionListener(this);
		panel2.add(b1);
		lblSpacer = new Label(SPACE);
		panel2.add(lblSpacer);

		b2 = new Button("2");
		b2.addActionListener(this);
		panel2.add(b2);
		lblSpacer = new Label(SPACE);
		panel2.add(lblSpacer);

		b3 = new Button("3");
		b3.addActionListener(this);
		panel2.add(b3);
		lblSpacer = new Label(SPACE);
		panel2.add(lblSpacer);

		b4 = new Button("4");
		b4.addActionListener(this);
		panel2.add(b4);
		lblSpacer = new Label(SPACE);
		panel2.add(lblSpacer);

		
		add(panel2, BorderLayout.SOUTH);
		
                initialize();
		// Set to a reasonable size.
		setSize(1024, 768);
	} // Connect4

	public void initialize() {
		theArray=new char[MAXROW][MAXCOL];
		for (int row=0; row<MAXROW; row++)
			for (int col=0; col<MAXCOL; col++)
				theArray[row][col]=BLANK;
		gameStart=false;
	} // initialize
	public void paint(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(420, 50, 100+100*MAXCOL, 100+100*MAXROW);
		for (int row=0; row<MAXROW; row++)
			for (int col=0; col<MAXCOL; col++) {
				if (theArray[row][col]==BLANK) g.setColor(Color.WHITE);
				if (theArray[row][col]==RED) g.setColor(Color.RED);
				if (theArray[row][col]==BLACK) g.setColor(Color.BLACK);
				g.fillOval(470+100*col, 100+100*row, 100, 100);
			}
		check4(g);
	} // paint

	public void putDisk(int n) 
        {
	// put a disk on top of column n
		// if game is won, do nothing
		if (end) return;
		gameStart=true;
		int row;
		n--;
		for (row=0; row<MAXROW; row++)
			if (theArray[row][n]!=' ') break;
		if (row>0) {
			theArray[--row][n]=activeColour;
			if (activeColour==RED)
				activeColour=BLACK;
			else
				activeColour=RED;
			repaint();
		}
	}
       
        //POP function 
      public void Pop(int c)
      {
          if (end) return;
		gameStart=true;
		      
          if(theArray[4][c]==activeColour)
                  {
                      int i=4;
                      while(i>0)
                      {
                          theArray[i][c]=theArray[i-1][c];
                          i--;
                          if(theArray[i][c]==' ')
                              break;
                      }
                      if(i==0)
                          theArray[0][c]=' ';
                      if (activeColour==RED)
				activeColour=BLACK;
			else
				activeColour=RED;
                      repaint();
                  }
      }
	public void displayWinner(char n) {
		if (n==RED)
			JOptionPane.showMessageDialog(null,"YOU WIN !");
		else
			JOptionPane.showMessageDialog(null,"YOU LOSE !");
		end=true;
                //display stats
                computer.stats();
	}

	public void check4(Graphics g) {
	// see if there are 4 disks in a row: horizontal, vertical or diagonal
		// horizontal rows
            computer.stats();
			for (int row=0; row<5; row++) 
		    for (int col=0; col<4-3; col++)
		    	if (theArray[row][col] != ' ' &&
		    		theArray[row][col] == theArray[row][col+1] &&  
					theArray[row][col] == theArray[row][col+2] && 
					theArray[row][col] == theArray[row][col+3]) 
                    		displayWinner(theArray[row][col]);
				
			
		
		// vertical columns
		for (int row = 0; row < 5-3; row++)
		    for (int col = 0; col < 4; col++)
				if (theArray[row][col] != ' ' &&
					theArray[row][col] == theArray[row+1][col] &&
					theArray[row][col] == theArray[row+2][col] &&
					theArray[row][col] == theArray[row+3][col])
				displayWinner(theArray[row][col]);
			
		
		//check for win diagonally (upper left to lower right)
		for (int row = 0; row < 5-3; row++) 
		    for (int col = 0; col < 4-3; col++) 
				if (theArray[row][col] != ' ' &&
					theArray[row][col] == theArray[row+1][col+1] &&
					theArray[row][col] == theArray[row+2][col+2] &&
					theArray[row][col] == theArray[row+3][col+3]) 
				displayWinner(theArray[row][col]);
			
		
		//check for win diagonally (lower left to upper right)
		for (int row = 3; row < 5; row++) 
		    for (int col = 0; col < 4-3; col++) 
				if (theArray[row][col] != ' ' &&
					theArray[row][col] == theArray[row-1][col+1] &&
					theArray[row][col] == theArray[row-2][col+2] &&
					theArray[row][col] == theArray[row-3][col+3])
					displayWinner(theArray[row][col]);
			
		
	} // end check4

	public void actionPerformed(ActionEvent e) 
        {
            
            if (e.getSource() == START)
            {
                String s=JOptionPane.showInputDialog(null,"Choose a difficulty level. Easy, Medium or Hard");
                
                    if(s.equalsIgnoreCase("easy"))
                        limit=3;
                    if(s.equalsIgnoreCase("medium"))
                        limit=6;
                    if(s.equalsIgnoreCase("hard"))
                        limit=9;
            }
            if(activeColour==RED){
		if (e.getSource() == btn1)
			putDisk(1);
		else if (e.getSource() == btn2)
			putDisk(2);
		else if (e.getSource() == btn3)
			putDisk(3);
		else if (e.getSource() == btn4)
			putDisk(4);
                else if (e.getSource() == b1)
			Pop(0);
		else if (e.getSource() == b2)
			Pop(1);
		else if (e.getSource() == b3)
			Pop(2);
		else if (e.getSource() == b4)
			Pop(3);
            }
            
            if(activeColour==BLACK)
            {
                board.Copy(theArray);
                int column;
                computer=new ComputerPlayer(limit*3);
                column = computer.alphaBetaSearch(board);
                
                if(computer.PopOk())
                {
                    board.Pop(column, 'B');
                    Pop(column+1);
                }
                                            
                    board.insert(column, 'B');
                    putDisk((column+1));
            }
                 if (e.getSource() == newMI) {
			end=false;
			initialize();
			repaint();
		} else if (e.getSource() == exitMI) {
			System.exit(0);
		} else if (e.getSource() == redMI) {
			// don't change colour to play in middle of game
			if (!gameStart) activeColour=RED;
		} else if (e.getSource() == blackMI) {
			if (!gameStart) activeColour=BLACK;
		}
	} // end ActionPerformed

        
}