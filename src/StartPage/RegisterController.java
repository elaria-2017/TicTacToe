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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class RegisterController implements Initializable {
    
    Thread th;

    @FXML
    public TextField userName;
    @FXML
    public TextField firstName;

    @FXML
    public TextField lastName;

    @FXML
    public TextField Password;

    @FXML
    public TextField confirmationPassword;

    @FXML
    public Button signup;

    public void REGE(ActionEvent event) {
        signup.setDisable(true);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("action", "signup");
        map.put("userID", userName.getText());
        map.put("name", firstName.getText() +" "+ lastName.getText());
        map.put("password", Password.getText());

        //send stream to server containing hashmap
        try {
            Stream.sender.writeObject(map);
        } catch (IOException ex) {
            //
        }
        System.out.println("before while");
        
        
        th = new Thread(() -> {
            while (true) {
                System.out.println("inside while");
                if(Stream.signup_status == 1) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                    Dialog<String> dialog = new Dialog<String>();
                                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                                    dialog.setContentText("Successfully signed up!");
                                    dialog.getDialogPane().getButtonTypes().add(type);
                                    dialog.showAndWait();
                            }
                        });
                        signup.setDisable(false);
                        Stream.signup_status = -1;
                        break;
                }
                if (Stream.signup_status == 0) {
                    System.out.println("Error: Try again");
                    signup.setDisable(false);
                    Stream.signup_status = -1;
                    break;
                }
            }
        });
        th.start();
        
        System.out.println("after while");
    }

    public void Switch2Login(ActionEvent event) throws IOException {
        Parent signNView = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene signNViewScene = new Scene(signNView);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(signNViewScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
