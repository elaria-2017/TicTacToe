/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StartPage;

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
 * @author Lenovo
 */
public class loginController implements Initializable {
    
    @FXML
    private Label label;
    
    public void LOGIN(ActionEvent event) throws IOException{
     Parent signNView = FXMLLoader.load(getClass().getClassLoader().getResource("SelectionOptions/SelectionOptions.fxml"));
                
        Scene signNViewScene = new Scene(signNView);
        
       
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(signNViewScene);
        window.show();
    }
    
      public void SwitchtoSignUp(ActionEvent event) throws IOException
    {
        Parent signUpView =  FXMLLoader.load(getClass().getClassLoader().getResource("StartPage/Register.fxml"));
        Scene signUpViewScene = new Scene(signUpView);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(signUpViewScene);
        window.show();
    }
      
     public void Switch2Mode(ActionEvent event) throws IOException
    {
       
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
