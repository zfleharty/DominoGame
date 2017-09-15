# DominoGame
Application runs the Domino game with three buttons. one for selecting from the boneyard, another two for placing the currently
selected piece on the left/right. When the game is over a new button to restart the game is presented. On click a a new game starts
over. The current .jar file uploaded upon download does not immediately work becuase it can not find the intended images. The images must
also be dowloaded and placed in a file titled Repository/DominoImages/ in order for the jar file to work upon execution. The only known bugs 
are sometimes when the left/right buttons are pressed they will not place the piece as if it's an invalid move. Upon second click they place
the piece in the specified position. Also once in a while the application has ended up not responding and needed to be closed out. I am yet
to identify whether this crash happens upon a specific instance such that the bug may be traced down. It appears to happen at random times 
and doesn't display a clear pattern. These are the only two bugs I know of. One thing to mention is a part of my implementation and not a 
bug but the player has an option to pull from boneyard at all times rather than when the player needs to. There is no rule against it
but it's technically a cheat since all pieces can be grabbed from the boneyard at which point, the user knows which Dominos the computer
has and can simply attempt to make a move such the computer can not play a piece with the boneyard empty. User played the last piece and 
wins. The getImage() method within Domino object contains a comment with a cited source for the code in the function.
