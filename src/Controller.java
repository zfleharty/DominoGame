import javafx.scene.canvas.GraphicsContext;

import java.util.Iterator;
/**@author Zachary Fleharty
 * Controls the game and manages calls to the current DominoSet and GameBoard within the game.
 * Class features methods to place any move on the GameBoard, checks the state of the game, computer player and
 * updating graphical components.*/
public class Controller
{
    //Constants to represent player
    private final int PLAYER = 0;
    private final int COMP = 1;

    private int winner = 30; //Integer to represent winner, 30 initially represents no winner,
    private DominoSet DomSet;
    private GameBoard board;


    /**Allocates memory for a new Controller instance. Also allocates memory for a new DominoSet and new GameBoard.*/
    public Controller()
    {
        DomSet = new DominoSet();
        board = new GameBoard();

    }

    /**returns the winner of the game assuming the game is over.
     * NOTE: the winner variable switches between computer and user after each makes a move to represent who last made
     * a move. IsGameOver should be called first and if true then winner variables current value will be the winner
     * of the game.
     * @return winner: Integer value 0 = user 1 = Computer*/
    public int getWinner()
    {
        return winner;
    }

    /**Tests the current state of the game to see if the game is over. Game is over when a player hand is empty or the
     * boneyard is empty and one player has no moves left.
     * @return Boolean: true = GameOver, false = Game not over.*/
    public boolean isGameOver()
    {
        if (getPlayerHandSize() == 0)
        {
            winner = PLAYER;
            return true;
        } else if (DomSet.getCompHand().size() == 0)
        {
            winner = COMP;
            return true;
        }

        Iterator<Domino> playerHand = DomSet.getPlayerHand().iterator();
        Iterator<Domino> compHand = DomSet.getCompHand().iterator();

        if (DomSet.getBoneyardSize() == 0)
        {
            if (!board.matchInSet(playerHand))
            {
                return true;
            }
            if (!board.matchInSet(compHand))
            {
                return true;
            }
        }
        return false;
    }

    /**Given a player represented by an integer, an index and a string, the function makes a call to gameboard
     * to play the piece at the specified index from the player hand at the specified position from the string.
     * Current two valid positions are "left" or "right". Function returns false if the move is invalid or the
     * position is invalid.
     * @param  index index of Domino to play from specified player's hand
     * @param player integer representing player, two current possible players, 0 = user, 1 = Computer
     * @param position String representing specified position to play Domino. Two possible values "left" or "right".
     * @throws NullPointerException If index is greater than the player's hand ArrayList representation
     * @return Boolean representing whether the Domino move was valid returned by GameBoard or the position is valid*/
    public boolean makeMove(int index, int player, String position)
    {
        Domino newPiece = DomSet.getPiece(index, player);
        boolean validMove;

        if (position.equals("left"))
        {
            validMove = board.addLeft(newPiece);
        } else if (position.equals("right"))
        {
            validMove = board.addRight(newPiece);
        } else
        {
            return false;
        }

        if (!validMove)
        {
            DomSet.addFailedPiece(index, newPiece, player);
            return false;
        }

        winner = PLAYER;
        return true;
    }

    /**Plays a valid Domino from the Computer hand on the GameBoard. If the computer has no valid moves, it picks from
     * the boneyard in DominoSet.
     * @return Boolean: true = Computer made a move, false = Boneyard is empty and no valid move available*/
    public boolean computerTurn()
    {
        int hand_Size = DomSet.getCompHand().size();
        Domino current;
        Boolean possibleMove = true;
        Boolean SuccessfulMove = false;

        while (possibleMove)
        {
            for (int i = 0; i < hand_Size; i++)
            {
                current = DomSet.getPiece(i, COMP);
                if (current.matchSide(board.getLeft()))
                {
                    if (SuccessfulMove = board.addLeft(current)) break;
                } else if (current.matchSide(board.getRight()))
                {
                    if (SuccessfulMove = board.addRight(current)) break;
                }
                DomSet.addFailedPiece(i, current, COMP);
            }

            if (SuccessfulMove)
            {
                winner = COMP;
                return true;
            }
            possibleMove = DomSet.getFromBoneyard(COMP);
            hand_Size++;
        }
        winner = PLAYER;
        return false;
    }

    /**Sets a dropShadow effect on the Domino in the specified index of the user hand.
     * @param index index of Domino in player hand*/
    public void highlightPiece(int index)
    {
        DomSet.setHighlight(index);
    }

    /**Calls the current GameBoard method drawBoard with passed parameters. Updates the
     * board canvas to current state.
     * @param height height of canvas
     * @param width width of canvas
     * @param boardDisp GraphicsContext Representing the canvas*/
    public void updateBoardCanvas(GraphicsContext boardDisp, double width, double height)
    {
        boardDisp.clearRect(0, 0, width, height);
        board.drawBoard(boardDisp, width, height);
    }

    /**Calls the current DominoSet to draw the player hands current state.
     * @param height height of canvas
     * @param width width of canvas
     * @param playerDisp GraphicsContext representing the canvas*/
    public void updatePlayerHandCanvas(GraphicsContext playerDisp, double width, double height)
    {
        playerDisp.clearRect(0, 0, width, height);
        DomSet.getPlayerHandVisual(playerDisp, width, height);
    }

    /**Calls the current DominoSet to draw the Computer hands current state.
     * @param height height of canvas
     * @param width width of canvas
     * @param compDisp GraphicsContext representing the canvas*/
    public void updateCompHandCanvas(GraphicsContext compDisp, double width, double height)
    {
        compDisp.clearRect(0, 0, width, height);
        DomSet.getCompHandVisual(compDisp, width, height);
    }

    /**Calls on the current DominoSet with parameter player to move a piece from the boneyard
     * to the specified players hand. Returns false if the boneyard is empty when called upon.
     * @param player Integer representation of which player hand to move Domino to. 0=user, 1=computer
     * @return Boolean value of whether the grab from boneyard was successful or not*/
    public boolean getBoneyard(int player)
    {
        if (DomSet.getBoneyardSize() == 0)
        {
            return false;
        }
        DomSet.getFromBoneyard(player);
        return true;
    }

    /**Gets the player hand size.
     * @return Integer of the player hand's current size*/
    public int getPlayerHandSize()
    {
        return DomSet.getPlayerHand().size();
    }

    /**gets the bone yard size.
     * @return Integer of the bone yard's current size*/
    public String getBoneYardSize()
    {
        return "size" + DomSet.getBoneyardSize();
    }


}

