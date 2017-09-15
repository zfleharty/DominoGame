import javafx.scene.SnapshotParameters;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * @author Zachary Fleharty
 * Domino object with two integers ranging from 0 to 6 to represent the value of the Domino. Class also includes methods
 * which operate on the Domino's value, image and ways of comparing other Domino objects.
 */
public class Domino
{
    //Constants to help with which direction the Domino Image should be rotated
    private final int RIGHT = 0;
    private final int LEFT = 1;
    private int rotate = LEFT;

    //values of the Domino
    private int leftSide;
    private int rightSide;

    //Domino Graphical components
    private ImageView dominoImage;
    private ImageView blankImage = new ImageView("File:Resources/DominoImages/domino(0).png");
    private Boolean faceUp;


    /**
     * Constructor initializes a new Domino Object with assigned values for both sides as well as an initiated image.
     *
     * @param s1    int value of leftSide
     * @param s2    int value of rightSide
     * @param image int value to represent which domino image belongs to this object instance from
     *              F:Resources/DominoImages
     */
    public Domino(int s1, int s2, int image)
    {
        this.leftSide = s1;
        this.rightSide = s2;
        dominoImage = new ImageView("File:Resources/DominoImages/domino(" + image + ").png");
    }

    /**
     * function set's the visibility of the Domino, either face up or down
     *
     * @param visibility Boolean value tells whether the Domino Graphics should be visible to the board or not
     */
    void setVisibility(boolean visibility)
    {
        faceUp = visibility;
    }

    /**
     * returns the leftSide value of Domino
     *
     * @return an integer representing the left side of the Domino
     */
    public int getLeftSide()
    {
        return leftSide;
    }

    /**returns the rightSide value of Domino
     * @return an integer representing the right side of the Domino */
    public int getRightSide()
    {
        return rightSide;
    }

    /**
     * @return an image of the Domino on it's horizontal side
     */
    Image getRotatedDomino()
    {
        SnapshotParameters params = new SnapshotParameters();
        ImageView DomImage = dominoImage;
        if (rotate == RIGHT)
        {
            DomImage.setRotate(90);
        } else
        {
            DomImage.setRotate(270);
        }
        Image returnImage = DomImage.snapshot(params, null);
        return returnImage;
    }

    /**
     * @return an Image of the Domino on it's vertical side
     */
    Image getDominoImage()
    { //method of converting imageView to image found at https://stackoverflow.com/questions/33613664/javafx-drawimage-rotated
        SnapshotParameters params = new SnapshotParameters();
        Image returnImage;
        if (faceUp)
        {
            returnImage = dominoImage.snapshot(params, null);
        } else
        {
            returnImage = blankImage.snapshot(params, null);
        }
        return returnImage;
    }

    /**
     * Switches a DropShadow effect on the Domino Image either off or on
     */
    void switchEffect()
    {
        if (dominoImage.getEffect() != null)
        {
            dominoImage.setEffect(null);
            return;
        }
        DropShadow borderShadow = new DropShadow();
        borderShadow.setColor(Color.YELLOW);
        dominoImage.setEffect(borderShadow);
    }

    /**
     * Tells whether this Domino object has a blank wild card side
     *
     * @return boolean true for domino has a blank side and false otherwise
     */
    public boolean isBlank()
    {
        if (this.leftSide == 0 || this.rightSide == 0)
        {
            return true;
        }
        return false;
    }

    /**Flips the left and right side values of the Domino. This function also updates the rotation of the Domino object
     * such that the horizontal Image returned by getRotatedDomino returns the correct image corresponding to the values
     * of the left and right side.*/
    void flip()
    {
        int temp = this.leftSide;
        this.leftSide = this.rightSide;
        this.rightSide = temp;

        if (rotate == RIGHT)
        {
            rotate = LEFT;
        } else
        {
            rotate = RIGHT;
        }
    }

    /**
     * Tells whether the param 'side' matches any of this objects values left and right side.
     *
     * @param side integer of side to test for match with this Domino object
     */
    boolean matchSide(int side)
    {
        if (this.rightSide == side || this.leftSide == side
                || side == 0 || this.isBlank())
        {
            return true;
        }
        return false;
    }

    /**
     * @param matching_Domino a domino object two test if sides match with this Domino object
     * @return boolean true if a side matches, false if no sides match
     * <p>
     * returns a match if the param domino has at least a side that match's with a side
     * of this Domino object. NOTE: This function does not return true if this Dominoe
     * object is placed on the board and it's open side is a blank.
     */
    boolean match(Domino matching_Domino)
    {
        if (this.leftSide == matching_Domino.getRightSide() || this.rightSide == matching_Domino.getLeftSide()
                || this.leftSide == matching_Domino.getLeftSide() || this.rightSide == matching_Domino.getRightSide())
        {
            return true;
        } else if (matching_Domino.isBlank())
        {
            return true;
        }
        return false;
    }

}
