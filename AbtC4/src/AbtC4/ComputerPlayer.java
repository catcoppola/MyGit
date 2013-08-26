
/**
 *
 * @author abtpst
 */
package AbtC4;

/*This is the class which represents the computer player*/
public class ComputerPlayer 
{
	private char mySymbol = 'B';    // The computer has a symbol B for Black
	private char opponentSymbol = 'R';  // The human player has a symbol R for Red
	private int searchLimit;    // searchlimit is the maximum depth of the algorithm
	private int maxColumn;      // maxcolumn represents the optimal column number 
                                    //if the computer chooses a PUSH operation 
        private int popColumn;      // popcolumn represents the optimal column number 
                                    //if the computer chooses a POP operation
	private int [] poparray={4,4,4,4};  //this array stores the column numbers where  a POP is possible
        private boolean popflag=false;      // popflag will be set if POP is chosen as the optimal move
                                            // by the computer
        private static int Nodegen=0;      // Nodegen keeps count of the number of nodes generated thus far
	private static int Maxprunings=0;   // Maxprunings keeps count of the number of prunings at MAX nodes 
                                            // thus far
        private static int Minprunings=0;   // Minprunings keeps count of the number of prunings at MAX nodes 
                                            // thus far

        // Constructor for this class
	public ComputerPlayer(int limit) 
        {
		searchLimit = limit;
	}
        
        //This function returns true if popflag is set
        public boolean PopOk()
        {
            if(popflag)
                return true;
            return false;
        }
        
        //This function initializes the poparray by looking at the bottom row of the board
        public void SetPop(Board b)
        {
            for (int i=0;i<4;i++)
            {
                if(b.board[4][i]=='B')
                    poparray[i]=2;
                if(b.board[4][i]=='R')
                    poparray[i]=1;
            }
        }
	
        // This function calls the Alpha Beta search algorithm
        public int alphaBetaSearch(Board board)
        {
            Nodegen++;
            maxValue(board, 0, -1000, 1000);
            
            
            if(PopOk())
            {
                System.out.println("We are poppin' !");
                return popColumn;
            }
           
		return maxColumn;
	}
        
        // This function displays the statistics for this game
	public void stats()
        {
            System.out.println("Nodes generated = "+Nodegen+
                    "\n Prunings at Max Nodes = "+Maxprunings+
                    "\n Prunings at Min Nodes = "+Minprunings);
        }
	/**
	 * This method returns the highest utility value that is possible
	 * for the max player by starting from the passed board state. It
	 * also modifies the global variables maxColumn and popcolumn (if applicable)
         * that are associated with the column with the highest maxValue value.
	 */
	private int maxValue(Board board, int depth, int alpha, int beta) {
	
            // check for victory
            if (board.isFinished() != ' ')
			if (board.isFinished() == mySymbol)
				return 100;
			else return -100;
		//check if the depth has reached its limit or if the
		//board is in a terminal (tie) state and return its utility value.
		if (depth == searchLimit || board.isTie())
			return board.evaluateContent();
		depth = depth + 1;
		int column = 0;
            
                // first we consider all the PUSH moves
		for (int i = 0; i < board.columns; i++) {
			if (board.isLegalMove(i))
                        {
                            Nodegen++;
                            
                            board.insert(i, mySymbol);
				int value = minValue(board, depth, alpha, beta);
				if (value > alpha) {
					alpha = value;
					column = i;
				}
				board.remove(i);
		
                                if (alpha >= beta)
                                {Maxprunings++;
                                return alpha;}
			}
                }
                maxColumn = column;
                
                // now consider all the POP moves
                int vr=0;
                int pcolumn=0;
                for(int j=0;j<4;j++)
                {
                    if(poparray[j]==2)
                        if(board.Pop(j, mySymbol))
                        {
                            Nodegen++;
                            vr=minValue(board, depth, alpha, beta);
                            if(vr>alpha)
                            {
                                alpha=vr;
                                popflag=true;
                                pcolumn=j;
                            
                            }
                
                            board.PopRestore(j, mySymbol);
                            if (alpha >= beta)
                                {Maxprunings++;
                                return alpha;}
                        }
                }
                
                popColumn=pcolumn;
		return alpha;
	}

	/**
	 * This method returns the lowest utility value that is possible
	 * for the min player by starting from the passed board state.
	 */
	private int minValue(Board board, int depth, int alpha, int beta) {
	
            // check for victory	
            if (board.isFinished() != ' ')
			if (board.isFinished() == mySymbol)
				return 100;
			else return -100;
		//check if the depth has reached its limit or if the
		//board is in a terminal (tie) state and return its utility value.
		if (depth == searchLimit || board.isTie())
			return board.evaluateContent();
		depth = depth + 1;
	    
                // first we consider all the PUSH moves
		for (int i = 0; i < board.columns; i++) {
			if (board.isLegalMove(i))
                        {
                            Nodegen++;

				board.insert(i, opponentSymbol);
				int value = maxValue(board, depth, alpha, beta);
				if (value < beta)
					beta = value;
				board.remove(i);
				if (beta <= alpha)
                                {Minprunings++;
                                return beta;}
			}
		}
                
                // now we consider all the POP moves
	
                int vr=0;
                for(int j=0;j<4;j++)
                {
                    if(poparray[j]==2)
                        if(board.Pop(j, opponentSymbol))
                        {
                            Nodegen++;
                            vr=maxValue(board, depth, alpha, beta);
                            if(vr<beta)
                                beta=vr;
        
                            board.PopRestore(j, opponentSymbol);
                            if (beta <= alpha)
                                {Minprunings++;
                                return beta;}
                        }
                }
                
                
		return beta;
	}
}
