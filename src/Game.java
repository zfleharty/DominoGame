import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Game
{

    private final static int PLAYER = 0;
    private final static int COMP = 1;

    public static void main(String[] args)
    {
        DominoSet DomSet = new DominoSet();
        GameBoard board = new GameBoard(DomSet);

        Scanner input = new Scanner(System.in);

        Boolean winner = true;
        Boolean validMove;
        while (winner)
        {
            validMove = false;
            //user input variables
            while (!validMove)
            {
                validMove = playerTurn(board, input);
            }
            validMove = false;
            while (!validMove)
            {
                validMove = ComputerTurn(board);
            }
            //  winner = playerTurn(board, input);
            //        flag = computerTurn(board);
        }

    }

    public static boolean ComputerTurn(GameBoard board)
    {
        ArrayList<Domino> ComputerHand = board.Set.compHand;
        Domino current;
        Boolean possibleMove = true;
        Boolean SuccessfulMove = false;

        while (possibleMove)
        {
            for (int i = 0; i < ComputerHand.size(); i++)
            {
                current = ComputerHand.get(i);
                if (current.matchSide(board.getLeft()))
                {
                    if (SuccessfulMove = board.addLeft(i, COMP)) break;
                } else if (current.matchSide(board.getRight()))
                {
                    if (SuccessfulMove = board.addRight(i, COMP)) break;
                }
            }

            if (SuccessfulMove)
            {
                return true;
            }
            possibleMove = board.Set.getBoneyard(COMP);
        }
        return false;
    }

    /*playerTurn prints the board and the players current hand and prompts
    * the user to pick a domino to play and where to play it. Input is processed
    * and if valid move the piece is added to the board and removed from the player
    * hand*/
    public static boolean playerTurn(GameBoard board, Scanner input)
    {
        int playPiece; //index of piece to play
        String position; //players choice of where to play domino

        //print the board and hands
        System.out.println("Player hand");
        board.Set.printSet(board.Set.playerHand);
        System.out.println("Board");
        board.printBoard();

        //user picks domino
        System.out.print("pick Domino or boneyard (int/boneyard): ");

        playPiece = input.nextInt();


        //user picks where to place domino
        System.out.println("Selected Piece " + board.Set.playerHand.get(playPiece).getDomino());
        System.out.println("pick position (left/right):");
        input.nextLine(); //clear any /n characters
        position = input.nextLine().toLowerCase();

        switch (position)
        {
            case "left":
                return board.addLeft(playPiece, PLAYER);
            case "right":
                return board.addRight(playPiece, PLAYER);
            default:
                System.out.println("Invalid Move");
                return false;
        }
    }
}
