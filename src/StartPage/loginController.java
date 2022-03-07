/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StartPage;

import Connections.*;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Lenovo
 */
public class loginController implements Initializable {
    
    Thread th;
    
    @FXML
    private Label label;
    
    @FXML
    public TextField loginUserName;
    
    @FXML
    public TextField loginPassword;
    
    @FXML
    public Button loginButton;
    
    
    public void LOGIN(ActionEvent event) throws IOException{
        
        loginButton.setDisable(true);
        //create hashmap with user input and login action
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("action", "login");
        map.put("userID", loginUserName.getText());
        map.put("password", loginPassword.getText());

        //open stream with server
        //Stream.start();
        
        //send stream to server containing hashmap
        try {
            Stream.sender.writeObject(map);
        } catch (IOException ex) {
            //
        }        
        System.out.println("before while");
        
        
        th = new Thread(() -> {
            while (true) {
                System.out.print("");
                if(Stream.login_status == 1) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Parent signNView = FXMLLoader.load(getClass().getClassLoader().getResource("SelectionOptions/SelectionOptions.fxml"));
                                    Scene signNViewScene = new Scene(signNView);
                                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                                    window.setScene(signNViewScene);
                                    window.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        loginButton.setDisable(false);
                        Stream.login_status = -1;
                        break;
                }
                if (Stream.login_status == 0) {
                    System.out.println("Invalid Credentials: ");
                    System.out.print(loginUserName.getText() + "," + loginPassword.getText());
                    loginButton.setDisable(false);
                    Stream.login_status = -1;
                    break;
                }
            }
        });
        th.start();
        
        System.out.println("after while");
        //listen to response..
        //if true then continue to the next scene
        //if false show error message
        /*
        if(myStream.status == 1) {
            Parent signNView = FXMLLoader.load(getClass().getClassLoader().getResource("SelectionOptions/SelectionOptions.fxml"));
            Scene signNViewScene = new Scene(signNView);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(signNViewScene);
            window.show();
            System.out.println(Stream.onlinePlayers);
        }
        else {
            System.out.println("Invalid Credentials: ");
            System.out.print(loginUserName.getText() + "," + loginPassword.getText());
        }
        */
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
        //open stream with server
        Stream.start();
        System.out.println("stream opened");
    }    
    
}
