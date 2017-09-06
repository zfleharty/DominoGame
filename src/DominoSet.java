import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class DominoSet
{
    public ArrayList<Domino> BoneYard = new ArrayList(14);
    public ArrayList<Domino> playerHand = new ArrayList(7);
    public ArrayList<Domino> compHand = new ArrayList(7);
    private final int PLAYER = 0;
    private final int COMP = 1;

    public DominoSet()
    {
        ArrayList<Domino> Set = createSet();
        dealSet(Set);
    }

    private void dealSet(ArrayList<Domino> set)
    {
        Collections.shuffle(set);
        Iterator<Domino> DomIt = set.iterator();
        int i = 0;
        while (i < 14)
        {
            BoneYard.add(DomIt.next());
            i++;
        }
        i = 0;
        while (i < 7)
        {
            playerHand.add(DomIt.next());
            i++;
        }
        i = 0;
        while (i < 7)
        {
            compHand.add(DomIt.next());
            i++;
        }
    }

    private ArrayList<Domino> createSet()
    {
        ArrayList<Domino> set = new ArrayList<>(28);
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j <= i; j++)
            {
                set.add(new Domino(j, i));
            }
        }
        return set;
    }

    void printSet(ArrayList<Domino> set)
    {
        Domino current = null;
        Iterator<Domino> DominoIt = set.iterator();
        int i = 0;

        while (DominoIt.hasNext())
        {
            System.out.print(i + ": ");
            current = DominoIt.next();
            System.out.println(current.getDomino());
            i++;
        }
    }

    Domino getPiece(int index, int player)
    {
        Domino piece;
        if (player == PLAYER)
        {
            piece = playerHand.get(index);
            playerHand.remove(index);
            playerHand.trimToSize();
        } else
        {
            piece = compHand.get(index);
            compHand.remove(index);
            compHand.trimToSize();
        }
        return piece;
    }

    boolean getBoneyard(int player)
    {
        if (BoneYard.size() == 0)
        {
            return false;
        }

        Domino piece = BoneYard.get(0);
        BoneYard.remove(0);
        BoneYard.trimToSize();

        if (player == PLAYER)
        {
            playerHand.add(piece);
        } else
        {
            compHand.add(piece);
        }
        return true;
    }

    void addFailedPiece(int index, Domino piece, int player)
    {
        if (player == PLAYER)
        {
            playerHand.add(index, piece);
        } else
        {
            compHand.add(index, piece);
        }
    }


}
