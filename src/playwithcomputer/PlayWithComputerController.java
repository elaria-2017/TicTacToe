/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playwithcomputer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
public class PlayWithComputerController implements Initializable {
    
    @FXML
    private Label label;
    @FXML Button btn1;
    @FXML Button btn2;
    @FXML Button btn3;
    @FXML Button btn4;
    @FXML Button btn5;
    @FXML Button btn6;
    @FXML Button btn7;
    @FXML Button btn8;
    @FXML Button btn9;
    ArrayList<Button> boardButtons = new ArrayList<Button>();
    boolean playerTurn = true;
    boolean isEndOfGame = false;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
       
    }
    
    public void resetGrid(ActionEvent event)throws IOException{
        Parent signNView = FXMLLoader.load(getClass().getClassLoader().getResource("playwithcomputer/PlayWtihComputer.fxml"));
        Scene signNViewScene = new Scene(signNView);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(signNViewScene);
        window.show();
        
    }
    
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
        playerTurn = true;
        isEndOfGame = false;
        
    }
    
    private void changeTurn(){
        if(playerTurn){
            playerTurn = false;
        }
        else{
            playerTurn = true;
        }
    }
    private void drawX(Button b){
        b.setText("X");
    }
    
    private void drawO(Button b){
        b.setText("O");
    }
    
    public int randomMove (int max)
    {   
    int random_int = (int)Math.floor(Math.random()*(max));     
 
    return random_int;       
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
    
    public void PlayerMove(ActionEvent event) throws InterruptedException 
    {
        Button btn = (Button) event.getSource(); 
        if(isEmpty(btn) && playerTurn && !isEndOfGame){
            drawX(btn);
            changeTurn();
            checkForWinner();
            machineMove();
            changeTurn();
            checkForWinner();
        }  
     }
    
    public void machineMove() throws InterruptedException
    {
        if(isBoardFull() || playerTurn || isEndOfGame){
            return;
        }
        int pos;
        while(true){
            pos=randomMove(boardButtons.size());
            if(isEmpty(boardButtons.get(pos))){
                drawO(boardButtons.get(pos));
                break;
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
        if(winner.equals("X")){
            msg = "You won!";
        }
        else{
            msg = "You lost!";
        }
        Dialog<String> dialog = new Dialog<String>();
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(msg);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.getDialogPane().setStyle("-fx-background-color: #B19CD8;");
        dialog.showAndWait();
    }
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        startGame();
    } 
    
    public void back2selectionmode(ActionEvent event) throws IOException{
        
       Parent signNView = FXMLLoader.load(getClass().getClassLoader().getResource("selectionmode/SelectionMode.fxml"));
        Scene signNViewScene = new Scene(signNView);
        
       
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(signNViewScene);
        window.show();
    }

}

