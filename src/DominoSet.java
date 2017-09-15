import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * @author Zachary Fleharty
 * Object represents a full Domino set. Class includes three sub sets of the set to represent the bone yard, player
 * hand and computer hand. On allocation the set is already shuffled and dealed out to the player hand boneyard and
 * computer hand. Available methods in class allow to transfer from boneyard to one of the player hands, get a Domino
 * object from either hand, add a failed peice to either hand and Graphical components to draw the representation
 * of the three subsets of the domino set
 */
public class DominoSet
{
    private ArrayList<Domino> BoneYard = new ArrayList(14);
    private ArrayList<Domino> playerHand = new ArrayList(7);
    private ArrayList<Domino> compHand = new ArrayList(7);
    private final int PLAYER = 0; //Constant to represent user player
    private int SelectedPiece = 30; //integer representing current highlighted piece,30 represents no piece selected

    /**
     * Allocates a new Domino Set with the set shuffled and dealt out to the players and the bone yard
     */
    public DominoSet()
    {
        ArrayList<Domino> Set = createSet();
        dealSet(Set);
    }

    /**Method shuffles the passed set and then deals out the appropriate amount to the player, computer hand bone yard
     * sub sets of this class
     * @param set ArrayList of Domino objects containing one of each type, total size of 28*/
    private void dealSet(ArrayList<Domino> set)
    {
        Collections.shuffle(set);
        Iterator<Domino> DomIt = set.iterator();
        int i = 0;
        Domino current;
        while (i < 14)
        {
            BoneYard.add(DomIt.next());
            i++;
        }
        i = 0;
        while (i < 7)
        {
            current = DomIt.next();
            current.setVisibility(true);
            playerHand.add(current);
            i++;
        }
        i = 0;
        while (i < 7)
        {
            current = DomIt.next();
            current.setVisibility(false);
            compHand.add(current);
            i++;
        }
    }

    /**Method Allocates memory for a full set of Domino's including one of each peice from (0,0) up to (6,6) with
     * a total of 28 peices.
     * @return ArrayList of 28 distinct Domino Objects*/
    private ArrayList<Domino> createSet()
    {
        ArrayList<Domino> set = new ArrayList<>(28);
        int counter = 0;
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j <= i; j++)
            {
                set.add(new Domino(j, i, counter));
                counter++;
            }
        }
        return set;
    }

    /**Method grabs the Domino object at the specified index from the specified player hand. Removes the object
     * from the hand and shrinks the size of the players hand.
     * @param index Integer of index to grab from ArrayList.
     * @param player Integer representing which player hand to pick Domino from User = 0, any other integer to pick from
     *               the computer's hand.
     * @return Domino object at index provided of player hand
     * @throws NullPointerException If called at index greater than the specified players hand size*/
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
        SelectedPiece = 30;
        return piece;
    }

    /**
     * Method removes the first element from the boneyard assuming it has been shuffled beforehand and adds it to
     * the specified player hand. It shrinks the arrayList boneyard to it's new size.
     *
     * @param player Integer representing which player hand to add the Domino to User = 0, any other integer to add to
     *               the computer's hand.
     * @return boolean false if the boneyard was empty before method call and true if the boneyard had a Domino before
     * the method was called.
     */
    boolean getFromBoneyard(int player)
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
            piece.setVisibility(true);
            playerHand.add(piece);
        } else
        {
            piece.setVisibility(false);
            compHand.add(piece);
        }
        return true;
    }

    /**Adds the Domino piece to the specified players hand at the specified index. Method is used for adding a Domino,
     * which was an invalid move, back in it's original position in the hand.
     * @param index Integer of where to add Domino.
     * @param piece Domino to add to player hand
     * @param player Integer representing which player to add Domino to. 0 = User, any other integer for the Computer
     * @throws NullPointerException If called at index greater than the player hand size*/
    void addFailedPiece(int index, Domino piece, int player)
    {
        if (player == PLAYER)
        {
            playerHand.add(index, piece);
        } else
        {
            compHand.add(index, piece);
        }
        SelectedPiece = index;
    }

    /**
     * returns the player hand
     *
     * @return ArrayList of the player hand
     */
    public ArrayList<Domino> getPlayerHand()
    {
        return playerHand;
    }

    /**
     * returns the computer hand
     *
     * @return ArrayList of the computer hand
     */
    public ArrayList<Domino> getCompHand()
    {
        return compHand;
    }

    /**
     * Returns the size of the bone yard
     *
     * @return integer of bone yards total size
     */
    public int getBoneyardSize()
    {
        return BoneYard.size();
    }

    /**
     * Updates the domino image in player hand at the specified image such that it uses a Drop shadow effect to
     * Display the current selected piece. Only one Domino in the player hand can be selected at any given time.
     *
     * @param index Integer of index to switch the effect to on.
     * @throws NullPointerException if called at index greater than the player hand size
     */
    public void setHighlight(int index)
    {

        if (index == SelectedPiece)
        {
            playerHand.get(SelectedPiece).switchEffect();
            SelectedPiece = 30;
            return;
        }

        if (SelectedPiece != 30)
        {
            playerHand.get(SelectedPiece).switchEffect();
        }

        playerHand.get(index).switchEffect();
        SelectedPiece = index;

    }

    /**
     * Draws the Domino images of the player hand scaled to size depending upon the width and height.
     *
     * @param gc     GraphicContext of Canvas to be drawn upon
     * @param width  Double of the canvas width
     * @param height Double of the canvas height
     */
    public void getPlayerHandVisual(GraphicsContext gc, double width, double height)
    {
        Iterator<Domino> playerDominos = playerHand.iterator();
        int dominoWidth;
        if (playerHand.size() > 6)
        {
            dominoWidth = (int) (width / playerHand.size());
        } else
        {
            dominoWidth = (int) (width / 6);
        }

        int dominoHeight = (int) height;
        int x = 0, y = 0;

        while (playerDominos.hasNext())
        {
            gc.drawImage(playerDominos.next().getDominoImage(), x, y, dominoWidth, dominoHeight);
            x += dominoWidth;
        }
    }

    /**
     * Draws the Domino images of the Computer hand scaled to size depending upon the width and height.
     *
     * @param compDisp GraphicContext of Canvas to be drawn upon
     * @param width    Double of the canvas width
     * @param height   Double of the canvas height
     */
    public void getCompHandVisual(GraphicsContext compDisp, double width, double height)
    {
        Iterator<Domino> compDominos = compHand.iterator();
        int dominoWidth;

        if (compHand.size() > 6)
        {
            dominoWidth = (int) (width / compHand.size());
        } else
        {
            dominoWidth = (int) (width / 6);
        }

        int dominoHeight = (int) height;
        int x = 0, y = 0;

        while (compDominos.hasNext())
        {
            compDisp.drawImage(compDominos.next().getDominoImage(), x, y, dominoWidth, dominoHeight);
            x += dominoWidth;
        }
    }


}