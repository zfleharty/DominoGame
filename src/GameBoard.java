import java.util.ArrayList;
import java.util.Iterator;

public class GameBoard
{
    private final int PLAYER = 0;
    private final int COMP = 1;
    private final int TOP = 1; //constant to indicate where the next Domino to be placed belongs on left/right row
    private final int BOT = 2; //constant to indicate where the next Domino to be placed belongs on left/right row

    private ArrayList<Domino> topRow = new ArrayList<>(28);
    private ArrayList<Domino> botRow = new ArrayList<>(28);
    DominoSet Set;

    private int middle;
    private int left;
    private int right;
    private int shift = TOP;
    private int leftValue;
    private int rightValue;

    public GameBoard()
    {
    }

    public GameBoard(DominoSet set)
    {
        Set = set;
    }

    private void addInitial(int index)
    {
        Domino newPiece = Set.getPiece(index, PLAYER);
        botRow.add(0, newPiece);
        middle = 0;
        left = TOP;
        right = TOP;
        leftValue = newPiece.getLeftSide();
        rightValue = newPiece.getRightSide();
    }

    public int getLeft()
    {
/*        if(left == TOP){
            return botRow.get(0);
        }else{
            return topRow.get(0);
        }*/
        return leftValue;
    }

    public int getRight()
    {
        /*if(right == TOP){
            return botRow.get(topRow.size() - 1);
        }else{
            return topRow.get(botRow.size() - 1);
        }*/
        return rightValue;
    }

    public boolean addLeft(int index, int player)
    {
        boolean result;
        if (left == TOP)
        { //play piece on top left
            left = BOT;
            shift = BOT;
            result = addTopLeft(index, player);
        } else if (left == BOT)
        { //play piece on bottom left
            left = TOP;
            shift = TOP;
            result = addBotLeft(index, player);
        } else
        { //left is 0, board empty
            addInitial(index);
            return true;
        }
        return result;
    }

    public boolean addRight(int index, int player)
    {
        boolean result;
        if (right == TOP)
        { //play piece top right
            right = BOT;
            result = addTopRight(index, player);
        } else if (right == BOT)
        { //play piece bottom right
            right = TOP;
            result = addBotRight(index, player);
        } else
        { //right is 0, empty board
            addInitial(index);
            return true;
        }
        return result;
    }

    private boolean addTopLeft(int index, int player)
    {
        Domino matchingPiece = botRow.get(0);
        Domino newPiece = Set.getPiece(index, player);
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
            }
            topRow.add(0, newPiece);
            leftValue = newPiece.getLeftSide();
            return true;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            left = TOP;
            shift = TOP;
            Set.addFailedPiece(index, newPiece, player);
            return false;
        }
    }

    private boolean addTopRight(int index, int player)
    {
        Domino matchingPiece = botRow.get(botRow.size() - 1);
        Domino newPiece = Set.getPiece(index, player);

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
            }
            topRow.add(newPiece);
            rightValue = newPiece.getRightSide();
            return true;
        } catch (Exception e)
        { // incorrect move
            System.out.println(e.getMessage());
            right = TOP;
            Set.addFailedPiece(index, newPiece, player);
            return false;
        }
    }

    private boolean addBotRight(int index, int player)
    {
        Domino matchingPiece = topRow.get(topRow.size() - 1);
        Domino newPiece = Set.getPiece(index, player);

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
            }
            botRow.add(newPiece);
            rightValue = newPiece.getRightSide();
            return true;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            right = BOT;
            Set.addFailedPiece(index, newPiece, player);
            return false;
        }
    }

    private boolean addBotLeft(int index, int player)
    {
        Domino matchingPiece = topRow.get(0);
        Domino newPiece = Set.getPiece(index, player);


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
            Set.addFailedPiece(index, newPiece, player);
            return false;
        }
    }

    public boolean matchSides(int left, int right)
    {
        if (left == 0 || right == 0 || left == right)
        {
            return true;
        }
        return false;
    }

    public void printBoard()
    {

        Iterator<Domino> TopIt = topRow.iterator();
        Iterator<Domino> BotIt = botRow.iterator();

        if (shift == TOP)
        {
            System.out.print("   ");
        }
        while (TopIt.hasNext())
        {
            System.out.print(TopIt.next().getDomino());
        }
        System.out.print("\n");

        if (shift == BOT)
        {
            System.out.print("   ");
        }
        while (BotIt.hasNext())
        {
            System.out.print(BotIt.next().getDomino());
        }
        System.out.println("\n");
    }
}

