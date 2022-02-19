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
 * @author Menna
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

