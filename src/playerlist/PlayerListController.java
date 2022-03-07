/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playerlist;

import Connections.Stream;
import static Connections.Stream.sender;
import StartPage.loginController;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Lenovo
 */
public class PlayerListController implements Initializable {

    Thread th;

    @FXML
    private Button invite;

    @FXML
    private Button refresh;

    @FXML
    private Button back;
    
    @FXML Button join;

    @FXML
    private TableView playersListTable;

    @FXML
    private TableColumn userNameColumn;

    @FXML
    private TableColumn scoreColoumn;

    @FXML
    private TableColumn statusColoumn;
    @FXML
    private TableColumn isPlayingColoumn;

    @FXML
    public void sendInvite(ActionEvent event) throws IOException {
        Player invited = (Player) playersListTable.getSelectionModel().getSelectedItem();
        String inviter = Stream.identity;
        if (!invited.getUserName().equals(inviter) && !invited.getStatus().equals("offline")) {
            HashMap<String, Object> inviteMap = new HashMap<String, Object>();
            inviteMap.put("action", "invite");
            inviteMap.put("inviter", inviter);
            inviteMap.put("invited", invited.getUserName());
            Stream.sender.writeObject(inviteMap);
        }
        if (invited.getUserName().equals(inviter)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setHeaderText("invitation was not sent");
            alert.setContentText("You can't invite yourself !");
            ButtonType cancelBtn = new ButtonType("Ok");
            alert.getButtonTypes().setAll(cancelBtn);
            alert.showAndWait();
        }
        if (invited.getStatus().equals("offline")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setHeaderText("invitation was not sent");
            alert.setContentText(invited.getUserName() + " is offline now !");
            ButtonType cancelBtn = new ButtonType("Ok");
            alert.getButtonTypes().setAll(cancelBtn);
            alert.showAndWait();
        }
        
        Thread thread = new Thread(() -> {
            while(true){
                System.out.println("inside invite thread");
                if(Stream.invite_status == 1){
                    Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Parent signNView = FXMLLoader.load(getClass().getClassLoader().getResource("playerwithplayer/PlayerWithPlayer.fxml"));
                                    Scene signNViewScene = new Scene(signNView);
                                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                                    window.setScene(signNViewScene);
                                    window.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                    Stream.invite_status = -1;
                    break;
                }
            }
        });
        thread.start();
    }

    public void Back2SelectionMode(ActionEvent event) throws IOException {
        Parent signNView = FXMLLoader.load(getClass().getClassLoader().getResource("selectionmode/SelectionMode.fxml"));
        Scene signNViewScene = new Scene(signNView);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(signNViewScene);
        window.show();

    }
    
    public void joinFunction(ActionEvent event)throws IOException{
        if(Stream.inviteAccept_status==1){
           Stream.inviteAccept_status = -1;
           Parent signNView = FXMLLoader.load(getClass().getClassLoader().getResource("playerwithplayer/PlayerWithPlayer.fxml"));
           Scene signNViewScene = new Scene(signNView);
           Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
           window.setScene(signNViewScene);
           window.show(); 
        }
    };

    public void refresh(ActionEvent event) throws IOException {
        refresh();
    }

    public void refresh() {
        refresh.setDisable(true);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("action", "getOnline");

        try {
            Stream.sender.writeObject(map);
        } catch (IOException ex) {

        }

        th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (Stream.refresh_status == 1) {
                        Stream.refresh_status = -1;
                        userNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("userName"));
                        scoreColoumn.setCellValueFactory(new PropertyValueFactory<Player, String>("score"));
                        statusColoumn.setCellValueFactory(new PropertyValueFactory<Player, String>("status"));
                        isPlayingColoumn.setCellValueFactory(new PropertyValueFactory<Player, String>("isPlaying"));

                        playersListTable.setItems(getPlayers());
                        refresh.setDisable(false);
                        break;
                    }
                }
            }
        });
        th.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        refresh();

//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("action", "getOnline");
//        map.put("success", true);
//        try {
//            Stream.sender.writeObject(map);
//        } catch (IOException ex) {
//            Logger.getLogger(PlayerListController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("action", "getOnline");
//        map.put("success", true);
//        Stream myStream = new Stream();
//        myStream.myFun();
//        //send stream to server containing hashmap
//        try {
//            myStream.sender.writeObject(map);
//            System.out.println("sent");
//        } catch (IOException ex) {
//            //
//                System.out.print("ERROR");
//        }
//        
//        th = new Thread(() -> {
//            while (true) {
//                System.out.print("");
//                if(myStream.status == 1) {
//                        
//                        break;
//                }
//                if (myStream.status == 0) {
//
//                    break;
//                }
//            }
//        });
//        th.start();
        // TODO
    }

    public ObservableList<Player> getPlayers() {

        System.out.println(Connections.Stream.onlinePlayers);

        ObservableList<Player> playersList = FXCollections.observableArrayList();

        List<HashMap<String, Object>> temp = Connections.Stream.onlinePlayers;

        for (int i = 0; i < temp.size(); i++) {
            String arg1 = temp.get(i).get("userID").toString();
            String arg2 = temp.get(i).get("points").toString();
            String arg3 = temp.get(i).get("status").toString();
            String arg4 = "unknown";
            Player tmp = new Player(arg1, arg2, arg3, arg4);
            playersList.add(tmp);
        }

        return playersList;
    }

}
