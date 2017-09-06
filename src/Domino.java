import javax.print.attribute.standard.MediaSize;

public class Domino
{
    private int leftSide;
    private int rightSide;

    public Domino(int s1, int s2)
    {
        this.leftSide = s1;
        this.rightSide = s2;
    }

    private boolean isBlank()
    {
        if (this.leftSide == 0 || this.rightSide == 0)
        {
            return true;
        }
        return false;
    }

    public int getLeftSide()
    {
        return leftSide;
    }

    public int getRightSide()
    {
        return rightSide;
    }

    public void flip()
    {
        int temp = this.leftSide;
        this.leftSide = this.rightSide;
        this.rightSide = temp;
    }

    public boolean matchSide(int otherSide)
    {
        if (this.rightSide == otherSide || this.leftSide == otherSide
                || otherSide == 0 || this.isBlank())
        {
            return true;
        }
        return false;
    }

    /*returns a match if the domino (OtherPiece) has sides that match this Dominoes
    * two sides and or the OtherPiece has a blank side.
    * NOTE: This function does not return true if this Dominoe object is placed on the board and
    * it's open side is a blank. */
    public boolean match(Domino OtherPiece)
    {
        if (this.leftSide == OtherPiece.getRightSide() || this.rightSide == OtherPiece.getLeftSide()
                || this.leftSide == OtherPiece.getLeftSide() || this.rightSide == OtherPiece.getRightSide())
        {
            return true;
        } else if (OtherPiece.isBlank())
        {
            return true;
        }
        return false;
    }

    public String getDomino()
    {
        return "(" + leftSide + "," + rightSide + ")";
    }
}
