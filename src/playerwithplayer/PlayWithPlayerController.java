/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playerwithplayer;

import Connections.Stream;
import static Connections.Stream.sender;
import StartPage.loginController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author sara
 */
public class PlayWithPlayerController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    Button btn1;
    @FXML
    Button btn2;
    @FXML
    Button btn3;
    @FXML
    Button btn4;
    @FXML
    Button btn5;
    @FXML
    Button btn6;
    @FXML
    Button btn7;
    @FXML
    Button btn8;
    @FXML
    Button btn9;
    @FXML
    Label playerturn;
    ArrayList<Button> boardButtons = new ArrayList<Button>();
    boolean playerTurn;
    boolean isEndOfGame = false;
    String player,opponent;
    public static HashMap<String, Object> map = new HashMap<String, Object>();
    public static HashMap<String, Object> moveResMap = new HashMap<String, Object>();
    String userID, opponentID;
    
    public void startGame(){
        boardButtons.add(btn1);
        boardButtons.add(btn2);
        boardButtons.add(btn3);
        boardButtons.add(btn4);
        boardButtons.add(btn5);
        boardButtons.add(btn6);
        boardButtons.add(btn7);
        boardButtons.add(btn8);
        boardButtons.add(btn9);
        for(Button b: boardButtons)
        {
            b.setText("");
           
        }
        isEndOfGame = false;
        userID = (String) map.get("userID");
        System.out.println("user " + userID);
        opponentID = (String) map.get("opponentID");
        System.out.println("opponent " + opponentID);
        player = (String) map.get("player");
        opponent = (String) map.get("opponent");
        playerTurn = (boolean) map.get("playerTurn");
        if(playerTurn){
            playerturn.setText("Your Turn");
        }
        else{
            playerturn.setText("Waiting");
        }
    }
    
    private void changeTurn(){
        if(playerTurn){
            playerTurn = false;
            playerturn.setText("Waiting");
        }
        else{
            playerTurn = true;
            playerturn.setText("Your Turn");
        }
    }
    
    private void drawPlayer(Button b){
        b.setText(player);
    }
    
    private void drawOpponent(Button b){
        b.setText(opponent);
    }
    
    private boolean isEmpty(Button pos)
    {
        return pos.getText().isEmpty();
    
    }
   
   public boolean isBoardFull(){
        for(Button boardButton : boardButtons) {
            if(isEmpty(boardButton)){
                return false;
            }
        }
        return true;
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    @FXML
    private void playerMove(ActionEvent event) throws IOException {
        // this function assigned to all x o buttons
        Button btn = (Button) event.getSource();
        int pos = boardButtons.indexOf(btn);
        if(isEmpty(btn) && playerTurn && !isEndOfGame){
            // if playerTurnn true, send to server button number
            // receive from server opponent button number
            // check for winner
            drawPlayer(btn);
            HashMap<String, Object> move = new HashMap<String, Object>();
            move.put("action", "move");
            move.put("userID", opponentID);
            move.put("pos", pos);
            Stream.sender.writeObject(move);
            changeTurn();
            checkForWinner();
            Thread thread = new Thread(() -> {
                while(true){
                    System.out.println("inside receive move thread");
                    if(Stream.move_status == 1){
                        Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    int move = (int) moveResMap.get("pos");
                                    drawOpponent(boardButtons.get(move));
                                    changeTurn();
                                    checkForWinner();
                                    
                                }
                            });
                        Stream.move_status = -1;
                        break;
                    }
                    else if(isEndOfGame){
                        break;
                    }
                }
            });
        
            if(!playerTurn){
                thread.start();
            }
        } 
    }
    
    
     public void checkForWinner(){
        checkRows();
        checkCols();
        checkDiagonals();
        checkForDraw();
    }
    
    private void checkRows(){
        if(!isEndOfGame){
            for(int i=0;i<boardButtons.size();i+=3){
                if(boardButtons.get(i).getText().equals(boardButtons.get(i+1).getText())
                        && boardButtons.get(i).getText().equals(boardButtons.get(i+2).getText())
                        && !boardButtons.get(i).getText().isEmpty()){
                     String winner = boardButtons.get(i).getText();
                     endGame(winner);
                     return;
                }
            }
        }
    }
    
    private void checkCols(){
        if(!isEndOfGame){
            for(int i=0;i<3;i++){
                if(boardButtons.get(i).getText().equals(boardButtons.get(i+3).getText())
                        && boardButtons.get(i).getText().equals(boardButtons.get(i+6).getText())
                        && !boardButtons.get(i).getText().isEmpty()){
                     String winner = boardButtons.get(i).getText();
                     endGame(winner);
                     return;
                }
            }
        }
    }
    
    private void checkDiagonals(){
        if(!isEndOfGame){
            if(boardButtons.get(0).getText().equals(boardButtons.get(4).getText())
                        && boardButtons.get(0).getText().equals(boardButtons.get(8).getText())
                        && !boardButtons.get(0).getText().isEmpty()){
                     String winner = boardButtons.get(0).getText();
                     endGame(winner);
                }
            else if(boardButtons.get(2).getText().equals(boardButtons.get(4).getText())
                        && boardButtons.get(2).getText().equals(boardButtons.get(6).getText())
                        && !boardButtons.get(2).getText().isEmpty()){
                     String winner = boardButtons.get(2).getText();
                     endGame(winner);
                }
        }
    }
    
    private void checkForDraw(){
        if(!isEndOfGame){
            if(isBoardFull()){
                isEndOfGame = true;
                playerturn.setText("It's a draw!");
                Dialog<String> dialog = new Dialog<String>();
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                dialog.setContentText("It's a draw!");
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.getDialogPane().setStyle("-fx-background-color: #B19CD8;");
                dialog.showAndWait();
            }
        }
    }
    
    private void endGame(String winner){
        isEndOfGame = true;
        String msg;
        if(winner.equals(player)){
            msg = "You won!";
            playerturn.setText(msg);
        }
        else {
            msg = "You lost!";
            playerturn.setText(msg);
        }
        Dialog<String> dialog = new Dialog<String>();
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(msg);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.getDialogPane().setStyle("-fx-background-color: #B19CD8;");
        dialog.showAndWait();
    }
    
    @FXML
    private void pause(ActionEvent event) {
        // this function assigned to pause button
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startGame();
        Thread thread = new Thread(() -> {
            while(true){
                System.out.println("inside receive move thread");
                if(Stream.move_status == 1){
                    Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                int move = (int) moveResMap.get("pos");
                                drawOpponent(boardButtons.get(move));
                                changeTurn();
                                checkForWinner();
                                
                            }
                        });
                    Stream.move_status = -1;
                    break;
                }
                else if(isEndOfGame){
                    break;
                }
            }
        });
        
        if(!playerTurn){
            thread.start();
        }
    }    
    
}

