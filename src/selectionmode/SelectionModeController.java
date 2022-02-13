/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selectionmode;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author sara
 */
public class SelectionModeController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
     public void Switch2Options(ActionEvent event) throws IOException
    {
         Parent signINView =  FXMLLoader.load(getClass().getClassLoader().getResource("SelectionOptions/SelectionOptions.fxml"));
        Scene signNViewScene = new Scene(signINView);
        
       
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(signNViewScene);
        window.show();
    }
     public void Switch2MultiPlayers(ActionEvent event) throws IOException
    {
        Parent signNView = FXMLLoader.load(getClass().getClassLoader().getResource("playerlist/PlayerList.fxml"));
        Scene signNViewScene = new Scene(signNView);
        
       
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(signNViewScene);
        window.show();
    }
     
    public void singleMode(ActionEvent event) throws IOException{
      
       Parent signNView = FXMLLoader.load(getClass().getClassLoader().getResource("playwithcomputer/PlayWtihComputer.fxml"));
        Scene signNViewScene = new Scene(signNView);
        
       
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(signNViewScene);
        window.show();
    }
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
