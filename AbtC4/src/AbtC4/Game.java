/**
 *
 * @author abtpst
 */
package AbtC4;

import java.util.Scanner;

public class Game {
	public static Board board = new Board();
	public static ComputerPlayer computer;
	public static char currentPlayer;
	public static char winner;

	public static void changePlayer() 
        {
            	if (currentPlayer == 'R') 
				currentPlayer = 'B';
		else currentPlayer = 'R';
	}
	
	private static void showWinner() {
		if (winner == ' ')
			System.out.println("Draw !");
		else 
			System.out.println("Player " + winner + " wins!");
	}
	
	public static void main(String[] args) {
		
                Scanner in = new Scanner(System.in);
                System.out.println("Choose a difficulty level 1.Easy 2.Meduim 3.Hard");
                int limit = in.nextInt();
		computer = new ComputerPlayer((limit*3));
                System.out.println(("Do you want to start ? 1. Yes 2. No"));
		int choice = in.nextInt();
		if (choice == 1)
			currentPlayer = 'R';
		else currentPlayer = 'B';
		do {
			int column;
			if (currentPlayer == 'B') {
                            
                            System.out.println("AI's turn ");				
					column = computer.alphaBetaSearch(board);
                                        board.insert(column, currentPlayer);
                                        board.showContent();
				
			} else
                        { 
                            System.out.println("Your turn ");
                            System.out.println("Would you like to 1.Drop 2.Pop");
                            int c=in.nextInt();
                            
                            if(c==1)
                            { 
                                System.out.println("Choose a column");
				board.showContent();
                                column = in.nextInt();
                                
                                while(!board.insert(column, currentPlayer))
                                {
                                    System.out.println("Choose a column");
                                    column = in.nextInt();
                                }
                                
                            }
                            
                            else
                            {
                                System.out.println("Choose a column");
				board.showContent();
                                column = in.nextInt();
                                
                                if(!board.Pop(column, currentPlayer))
                                    changePlayer();
                                
                            }
                        }
			
				
			board.showContent();
			changePlayer();
			winner = board.isFinished();
		}
		while (winner == ' ' && !board.isTie());
		showWinner();
	}
}
