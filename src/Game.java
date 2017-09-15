import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Scanner;

/**
 * @author Zachary Fleharty
 * Runs a simple version of Dominos game with one player vs a computer player
 */
public class Game extends Application
{

    private final static int PLAYER = 0;
    public static Controller gameController;

    public static void main(String[] args)
    {
        gameController = new Controller();
        /*boolean flag = true;
        while(flag){
            flag = playerTurn(new Scanner(System.in));
        }*/


        launch(args);

    }

    /**
     * Loads in all the graphical components from methods in GameController and adds them to the scene of the board.
     * Start's a new Window with the DominoGame playing and implements actionListeners to play the Game. Uses
     * GameController to Check if the game is over and calls GameOver().
     *
     * @param primaryStage Stage used to launch JavaFx application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Domino!");

        //playable buttons and Labels
        Button getBoneyard = new Button("Boneyard");
        Button playLeft = new Button("Play Left");
        Button playRight = new Button("Play Right");
        Label bone_Yard_Count = new Label(gameController.getBoneYardSize());

        //gameboard canvas components
        Canvas gameBoard = new Canvas(1100, 100);
        GraphicsContext boardDisp = gameBoard.getGraphicsContext2D();
        boardDisp.setFill(Color.BLACK);
        boardDisp.fillRect(0, 0, gameBoard.getWidth(), gameBoard.getHeight());
        gameController.updateBoardCanvas(boardDisp, gameBoard.getWidth(), gameBoard.getHeight());

        //player canvas components
        Canvas playerCanvas = new Canvas(500, 100);
        GraphicsContext playerDisp = playerCanvas.getGraphicsContext2D();
        gameController.updatePlayerHandCanvas(playerDisp, playerCanvas.getWidth(), playerCanvas.getHeight());
        //computer Canvas
        Canvas computerCanvas = new Canvas(500, 100);
        GraphicsContext compDisp = computerCanvas.getGraphicsContext2D();
        gameController.updateCompHandCanvas(compDisp, computerCanvas.getWidth(), computerCanvas.getHeight());


        //playable buttons pane

        HBox bone_yard_disp = new HBox();
        bone_yard_disp.setSpacing(10);
        bone_yard_disp.getChildren().addAll(getBoneyard, new Label(gameController.getBoneYardSize()));
        VBox playableButtons = new VBox();
        playableButtons.getChildren().addAll(bone_yard_disp, playLeft, playRight);

        StackPane boardPane = new StackPane();
        StackPane playerPane = new StackPane();
        StackPane ComputerPane = new StackPane();
        boardPane.getChildren().addAll(gameBoard, playableButtons);
        playerPane.getChildren().add(playerCanvas);
        ComputerPane.getChildren().add(computerCanvas);

        GridPane rootPane = new GridPane();
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setHgap(40);
        rootPane.setVgap(80);
        rootPane.setPadding(new Insets(25, 25, 25, 25));

        rootPane.add(ComputerPane, 0, 0);
        rootPane.add(boardPane, 0, 1);
        rootPane.add(playableButtons, 1, 1);
        rootPane.add(playerPane, 0, 2);




        /*Action Events*/
        //GetBoneyard
        getBoneyard.setOnAction(event ->
        {
            boolean is_Boneyard_empty = gameController.getBoneyard(PLAYER);
            if (is_Boneyard_empty)
            {
                gameController.updatePlayerHandCanvas(playerDisp, playerCanvas.getWidth(), playerCanvas.getHeight());
                bone_Yard_Count.setText(gameController.getBoneYardSize());
                bone_yard_disp.getChildren().clear();
                bone_yard_disp.getChildren().addAll(getBoneyard, bone_Yard_Count);
            }
        });
        //selectPiece
        playerCanvas.setOnMouseClicked(event ->
        {
            int selected_piece;
            if (gameController.getPlayerHandSize() > 6)
            {
                selected_piece = (int) (event.getX() / (playerCanvas.getWidth() / gameController.getPlayerHandSize()));
            } else
            {
                selected_piece = (int) (event.getX() / (playerCanvas.getWidth() / 6));
            }

            if (selected_piece < gameController.getPlayerHandSize())
            {
                playLeft.setDisable(false);
                playRight.setDisable(false);

                gameController.highlightPiece(selected_piece);
                gameController.updatePlayerHandCanvas(playerDisp, playerCanvas.getWidth(), playerCanvas.getHeight());

                playLeft.setOnAction(event1 ->
                {
                    boolean validMove = gameController.makeMove(selected_piece, PLAYER, "left");
                    gameController.updatePlayerHandCanvas(playerDisp, playerCanvas.getWidth(), playerCanvas.getHeight());
                    if (gameController.isGameOver())
                    {
                        GameOver(gameController.getWinner(), primaryStage, rootPane);
                    }

                    if (validMove)
                    {
                        gameController.computerTurn();
                        gameController.updateCompHandCanvas(compDisp, computerCanvas.getWidth(), computerCanvas.getHeight());
                        if (gameController.isGameOver())
                        {
                            GameOver(gameController.getWinner(), primaryStage, rootPane);
                        }
                    }

                    gameController.updateBoardCanvas(boardDisp, gameBoard.getWidth(), gameBoard.getHeight());
                    bone_Yard_Count.setText(gameController.getBoneYardSize());
                    bone_yard_disp.getChildren().clear();
                    bone_yard_disp.getChildren().addAll(getBoneyard, bone_Yard_Count);
                });

                playRight.setOnAction(event1 ->
                {
                    boolean validMove = gameController.makeMove(selected_piece, PLAYER, "right");
                    gameController.updatePlayerHandCanvas(playerDisp, playerCanvas.getWidth(), playerCanvas.getHeight());
                    if (gameController.isGameOver())
                    {
                        GameOver(gameController.getWinner(), primaryStage, rootPane);
                    }

                    if (validMove)
                    {
                        gameController.computerTurn();
                        gameController.updateCompHandCanvas(compDisp, computerCanvas.getWidth(), computerCanvas.getHeight());
                        if (gameController.isGameOver())
                        {
                            GameOver(gameController.getWinner(), primaryStage, rootPane);
                        }
                    }
                    gameController.updateBoardCanvas(boardDisp, gameBoard.getWidth(), gameBoard.getHeight());
                    bone_Yard_Count.setText(gameController.getBoneYardSize());
                    bone_yard_disp.getChildren().clear();
                    bone_yard_disp.getChildren().addAll(getBoneyard, bone_Yard_Count);
                });
            } else
            {
                playLeft.setDisable(true);
                playRight.setDisable(true);
            }
        });

        Scene scene = new Scene(rootPane, 1400, 700);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Removes playable buttons from the rootpane and adds a new button to
     * start a new Game. When selected method calls start and launches a new
     * window with a new game.
     *
     * @param primaryStage stage used in current Window. Updates the rootPane.
     * @param rootPane     GridPane which holds all other panes, most specifically the node with playable buttons
     * @param winner       Integer value representing who won. 0= User, otherwise Computer
     */
    public void GameOver(int winner, Stage primaryStage, GridPane rootPane)
    {
        Label who_Won = new Label();
        Button restart = new Button("New Game");
        restart.setOnMouseClicked(event ->
        {
            primaryStage.close();
            try
            {
                gameController = new Controller();
                start(primaryStage);
            } catch (Exception e)
            {
                System.err.print(e.getStackTrace());
            }
        });

        if (winner == PLAYER)
        {
            who_Won.setText("You Win!");
        } else
        {
            who_Won.setText("Computer wins.");
        }
        VBox restartOption = new VBox();
        restartOption.getChildren().addAll(who_Won, restart);

        rootPane.getChildren().remove(2);
        rootPane.add(restartOption, 1, 1);

    }
}
