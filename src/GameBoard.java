import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Zachary Fleharty
 * Class simulates a game board of the game Dominos. Two arrayLists represent the top and bottom rows of the board.
 * Class features methods to add to either the left or rightSide and Graphical components for displaying the current
 * board state. Graphical components resize automatically for any size Canvas.
 */
public class GameBoard
{
    private final int TOP = 1; //constant to indicate where the next Domino to be placed belongs on left/right row
    private final int BOT = 2; //constant to indicate where the next Domino to be placed belongs on left/right row

    private ArrayList<Domino> topRow = new ArrayList<>(28);
    private ArrayList<Domino> botRow = new ArrayList<>(28);

    private int middle = 0;
    private int left;
    private int right;
    private int shift = TOP;
    private int leftValue;
    private int rightValue;
    private int rightCount = 0;
    private double dominoWidth = 100;
    private double dominoHeight = 50;

    /**
     * Allocates memory for a new gameBoard initialization
     */
    public GameBoard()
    {
    }

    /**Get's the value of the open side on the left side of the Board
     * @return LeftValue: Integer of the left value to match*/
    public int getLeft()
    {
        return leftValue;
    }

    /**Gets the value of the open side on the right side of the Board
     * @return rightValue: Integer of the right value to match*/
    public int getRight()
    {
        return rightValue;
    }

    /**
     * Add's the passed Domino object to the left Side of the board. Whether the
     * next move on the left side is to played top or bottom, the function figures
     * this out itself. Also the function figures out whether the move is valid or not
     * if invalid the piece will not be added and the function returns false.
     *
     * @param newPiece Domino object to be added on the left side of the board.
     * @return Boolean representing whether the Domino was valid to be placed on the left side
     */
    public boolean addLeft(Domino newPiece)
    {
        boolean result;
        if (left == TOP)
        { //play piece on top left
            left = BOT;
            shift = BOT;
            result = addTopLeft(newPiece);
        } else if (left == BOT)
        { //play piece on bottom left
            left = TOP;
            shift = TOP;
            result = addBotLeft(newPiece);
        } else
        { //left is 0, board empty
            addInitial(newPiece);
            return true;
        }
        return result;
    }

    /**
     * Add's the passed Domino object to the right Side of the board. Whether the
     * next move on the right side is to played top or bottom, the function figures
     * this out itself. Also the function figures out whether the move is valid or not
     * if invalid the piece will not be added and the function returns false.
     *
     * @param newPiece Domino object to be added on the right side of the board.
     * @return Boolean representing whether the Domino was valid to be placed on the right side
     */
    public boolean addRight(Domino newPiece)
    {
        boolean result;
        if (right == TOP)
        { //play piece top right
            right = BOT;
            result = addTopRight(newPiece);
        } else if (right == BOT)
        { //play piece bottom right
            right = TOP;
            result = addBotRight(newPiece);
        } else
        { //right is 0, empty board
            addInitial(newPiece);
            return true;
        }
        if (result) rightCount++;
        return result;
    }

    /**
     * Called upon assuming the GameBoard is empty with no allocated Dominos in either two row representations.
     * By default the first Domino is played on the bottom row and then internal variables are updated according
     * to that first Domino's values.
     *
     * @param newPiece Domino Object to be added in the ArrayList bottomRow.
     */
    private void addInitial(Domino newPiece)
    {
        botRow.add(0, newPiece);
        middle = 0;
        left = TOP;
        right = TOP;
        leftValue = newPiece.getLeftSide();
        rightValue = newPiece.getRightSide();
    }

    /**
     * Adds the passed Domino to the top right of the board(end of ArrayList topRow). Whether the Domino is a valid
     * move is irrelevant. The method checks for valid move and returns false if not. The Domino's orientation also
     * does not matter. If a side matches the open side on the right side the method will handle aligning it and placing
     * it on the right side.
     *
     * @param newPiece Domino object to be added at the end of ArrayList topRow
     * @return Boolean representing whether the move was valid or not
     */
    private boolean addTopRight(Domino newPiece)
    {
        Domino matchingPiece = botRow.get(botRow.size() - 1);

        try
        {
            //check if valid move, if not throw exception
            if (!matchingPiece.match(newPiece) && matchingPiece.getRightSide() != 0)
            {
                throw new Exception("Invalid Move");
            }
            //check if newPiece is aligned right
            if (!matchSides(matchingPiece.getRightSide(), newPiece.getLeftSide()))
            {
                newPiece.flip();

                //newPiece does not have a side which matches the open side
                if (rightValue != newPiece.getLeftSide())
                {
                    throw new Exception("Invalid Move");
                }
            }
            topRow.add(newPiece);
            rightValue = newPiece.getRightSide();
            return true;
        } catch (Exception e)
        { // incorrect move
            System.out.println(e.getMessage());
            right = TOP;
            return false;
        }
    }

    /**
     * Adds the passed Domino to the bottom right of the board(end of ArrayList botRow). Whether the Domino is a valid
     * move is irrelevant. The method checks for valid move and returns false if not. The Domino's orientation also
     * does not matter. If a side matches the open side on the right side the method will handle aligning it and placing
     * it on the right side.
     *
     * @param newPiece Domino object to be added at the end of ArrayList botRow
     * @return Boolean representing whether the move was valid or not
     */
    private boolean addBotRight(Domino newPiece)
    {
        Domino matchingPiece = topRow.get(topRow.size() - 1);

        try
        {
            //check if valid move, if not throw exception
            if (!matchingPiece.match(newPiece) && matchingPiece.getRightSide() != 0)
            {
                throw new Exception("Invalid Move");
            }
            //check if newPiece is aligned right
            if (!matchSides(matchingPiece.getRightSide(), newPiece.getLeftSide()))
            {
                newPiece.flip();
                //newPiece does not have a side which matches the open side
                if (rightValue != newPiece.getLeftSide())
                {
                    throw new Exception("Invalid Move");
                }
            }
            botRow.add(newPiece);
            rightValue = newPiece.getRightSide();
            return true;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            right = BOT;
            return false;
        }
    }

    /**
     * Adds the passed Domino to the top right of the board(beginning of ArrayList topRow). Whether the Domino is a
     * valid move is irrelevant. The method checks for valid move and returns false if not. The Domino's orientation
     * also does not matter. If a side matches the open side on the left side the method will handle aligning it and
     * placing it on the left side.
     *
     * @param newPiece Domino object to be added at the beginning of ArrayList topRow
     * @return Boolean representing whether the move was valid or not
     */
    private boolean addTopLeft(Domino newPiece)
    {
        Domino matchingPiece = botRow.get(0);
        try
        {
            //check if valid move, if not throw exception
            if (!matchingPiece.match(newPiece) && matchingPiece.getLeftSide() != 0)
            {
                throw new Exception("Invalid Move");
            }
            //check if newPiece is aligned right
            if (!matchSides(newPiece.getRightSide(), matchingPiece.getLeftSide()))
            {
                newPiece.flip();
                //newPiece does not have a side which matches the open side
                if (leftValue != newPiece.getLeftSide())
                {
                    throw new Exception("Invalid Move");
                }
            }
            topRow.add(0, newPiece);
            leftValue = newPiece.getLeftSide();
            return true;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            left = TOP;
            shift = TOP;
            return false;
        }
    }

    /**Adds the passed Domino to the bottom right of the board(beginning of ArrayList botRow). Whether the Domino is a
     * valid move is irrelevant. The method checks for valid move and returns false if not. The Domino's orientation
     * also does not matter. If a side matches the open side on the left side the method will handle aligning it and
     * placing it on the left side.
     * @param newPiece Domino object to be added at the beginning of ArrayList botRow
     * @return Boolean representing whether the move was valid or not*/
    private boolean addBotLeft(Domino newPiece)
    {
        Domino matchingPiece = topRow.get(0);

        try
        {
            //check if valid move, if not throw exception
            if (!matchingPiece.match(newPiece) && matchingPiece.getLeftSide() != 0)
            {
                throw new Exception("Invalid Move");
            }
            //check if newPiece is aligned right
            if (!matchSides(newPiece.getRightSide(), matchingPiece.getLeftSide()))
            {
                newPiece.flip();
                //newPiece does not have a side which matches the open side
                if (leftValue != newPiece.getLeftSide())
                {
                    throw new Exception("Invalid Move");
                }
            }
            botRow.add(0, newPiece);
            leftValue = newPiece.getLeftSide();
            middle++;
            return true;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            left = BOT;
            shift = BOT;
            return false;
        }
    }

    /**
     * Checks whether the set of Dominos in the iterator match any of the open sides on the gameBoard.
     *
     * @return Boolean true if match exists, false if no match
     */
    public boolean matchInSet(Iterator<Domino> hand)
    {
        Domino current;
        while (hand.hasNext())
        {
            current = hand.next();
            if (isMatch(rightValue, current) || isMatch(leftValue, current))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the firstParameter matches either of the second parameter Domino's two values.
     *
     * @param openSide Integer of the side to match with second argument(Domino object)
     * @param piece    Domion to match with first argument(integer)
     * @return Boolean: true if match, false otherwise
     */
    private boolean isMatch(int openSide, Domino piece)
    {
        if (piece.isBlank() || openSide == 0 || piece.getRightSide() == openSide || piece.getLeftSide() == 0)
        {
            return true;
        }
        return false;
    }

    /**checks whether the two parameters are a match.
     * @param left First argument to be compared
     * @param right Second argument to be compared
     * @return Boolean true if two values are equal or at least one is 0. False otherwise*/
    public boolean matchSides(int left, int right)
    {
        if (left == 0 || right == 0 || left == right)
        {
            return true;
        }
        return false;
    }

    /**
     * Draws the Domino images of the board scaled to size depending upon the width and height.
     *
     * @param gc     GraphicContext of Canvas to be drawn upon
     * @param width  Double of the canvas width
     * @param height Double of the canvas height
     */
    public void drawBoard(GraphicsContext gc, double width, double height)
    {
        //Domino Coordinates
        double lengthOfBoard = (dominoWidth * ((topRow.size() + botRow.size()) / 2)) + dominoWidth;
        double start = (width / 2) - ((dominoWidth * middle) / 2);
        double x;
        double topY = height / 2 - (dominoWidth / 2);
        double botY = topY + dominoHeight;


        Iterator<Domino> topIt = topRow.iterator();
        Iterator<Domino> botIt = botRow.iterator();
        Image domino;
        while ((start + lengthOfBoard) > width)
        {
            start -= (((dominoWidth * rightCount) + (width / 2)) - width);
        }
        if (start < 0)
        {
            start = dominoWidth / 2;
            while ((start + lengthOfBoard) > width)
            {
                dominoWidth -= 10;
                dominoHeight = (dominoWidth / 2);
                lengthOfBoard = (dominoWidth * ((topRow.size() + botRow.size()) / 2)) + dominoWidth;
            }

        }
        if (shift == TOP)
        {
            x = start + (dominoWidth / 2);
        } else
        {
            x = start - (dominoWidth / 2);
        }
        while (topIt.hasNext())
        {
            domino = topIt.next().getRotatedDomino();
            gc.drawImage(domino, x, topY, dominoWidth, dominoHeight);
            x += dominoWidth;
        }

        x = start;
        while (botIt.hasNext())
        {
            domino = botIt.next().getRotatedDomino();
            gc.drawImage(domino, x, botY, dominoWidth, dominoHeight);
            x += dominoWidth;
        }
    }
}

