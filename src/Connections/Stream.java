/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connections;

import StartPage.Login;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 *
 * @author mostafa
 */
public class Stream {

    public static String identity;

    public static Thread th;
    public static List<HashMap<String, Object>> onlinePlayers;

    public static Socket mySocket;
    public static ObjectInputStream display;
    public static ObjectOutputStream sender;

    public static int login_status = -1;
    public static int signup_status = -1;
    public static int refresh_status = -1;
    public static int server_status = 1;
    public static int invite_status = -1;
    public static int inviteAccept_status = -1;
    public static int move_status = -1;

    public static void start() {

        try {
            System.out.println("function start");

            mySocket = new Socket("127.0.0.1", 5005);
            System.out.println("created socket");

            sender = new ObjectOutputStream(mySocket.getOutputStream());
            System.out.println("created sender");

            //display = new ObjectInputStream(mySocket.getInputStream());
            //System.out.println("created display");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR: in start function");
        }

        th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    display = new ObjectInputStream(mySocket.getInputStream());
                    System.out.println("created Thread");
                    while (true) {
                        //System.out.println("started while in thread");
                        try {
                            //System.out.println("listening...");
                            HashMap<String, Object> res = (HashMap<String, Object>) display.readObject();
                            System.out.println("i got message...");
                            System.out.println((String) res.get("action"));

                            switch ((String) res.get("action")) {
                                case "login":
                                    identity = (String) res.get("userID");
                                    System.out.println("switch case login...");
                                    loginRes(res);
                                    break;
                                case "getOnline":
                                    System.out.println("switch case getOnline...");
                                    getOnlineRes(res);
                                    break;
                                case "signup":
                                    System.out.println("switch case signup...");
                                    signUp(res);
                                    break;
                                case "invite":
                                    System.out.println("switch case invite...");
                                    invite(res);
                                    break;
                                case "inviteRes":
                                    System.out.println("switch case inviteRes...");
                                    inviteRes(res);
                                    break;
                                case "moveRes":
                                    moveRes(res);
                                    break;
                                case "close":
                                    System.out.println("switch case close...");
                                    server_status = 0;
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            Login.s.close();

                                        }
                                    });
                                    return;
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(Stream.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("catch 1");
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(Stream.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("catch 2");
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Stream.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void loginRes(HashMap<String, Object> res) {
                if ((boolean) res.get("success")) {
                    //TODO remove this line

                    login_status = 1;

                    System.out.println("login accepted from server");

//                    HashMap<String, Object> map = new HashMap<String, Object>();
//                    map.put("success", true);
//                    map.put("action", "getOnline");
//                    try {
//                        System.out.println("switching to getOnline...");
//                        sender.writeObject(map);
//                    } catch (IOException ex) {
//                        //
//                        System.out.println("catch3");
//                    }
                } else {
                    login_status = 0;
                    System.out.println("login refused from server");
                    //TODO show error msg
                }
            }

            private void getOnlineRes(HashMap<String, Object> res) {

                if ((boolean) res.get("success")) {

                    System.out.println("getting online players...");

                    //onlinePlayers.clear();
                    onlinePlayers = (List<HashMap<String, Object>>) res.get("onlinePlayers");
                    refresh_status = 1;
                    //System.out.println(onlinePlayers);

                    /*
                    for (int i = 0; i < onlinePlayers.size(); i++) {
                        Button b = new Button(onlinePlayers.get(i).get("name").toString());
                        final String invitedId = (String) onlinePlayers.get(i).get("socketID").toString();
                       // listView.getItems().add(b);
                          b.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                HashMap<String, Object> map = new HashMap<String, Object>();
                                map.put("action", "invite");
                                map.put("invited", invitedId);
                                map.put("invite", name);

                                try {
                                    sender.writeObject(map);
                                } catch (IOException ex) {
                                    Logger.getLogger(Stream.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        }); 
                    }*/
                    //System.out.println(onlinePlayers);
                } else {
                    System.out.println("catch4");
                }
            }

            private void invite(HashMap<String, Object> res) {
                if ((boolean) res.get("success")) {

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("New Invite");
                            alert.setHeaderText((String) res.get("msg"));
                            alert.setContentText("Do you want to join this game?");

                            ButtonType acceptBtn = new ButtonType("Accept");
                            ButtonType rejectBtn = new ButtonType("Reject");

                            alert.getButtonTypes().setAll(acceptBtn, rejectBtn);

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == acceptBtn) {
                                System.out.println("Accepted");
                                HashMap<String, Object> map = new HashMap<String, Object>();
                                map.put("action", "inviteRes");
                                map.put("success", true);
                                map.put("inviter", res.get("inviter"));
                                map.put("invited", res.get("invited"));
                                try {
                                    sender.writeObject(map);
                                    //move to multiplayer scene
                                    inviteAccept_status = 1;
                                    playerwithplayer.PlayWithPlayerController.map.put("userID", identity);
                                    playerwithplayer.PlayWithPlayerController.map.put("opponentID", res.get("inviter"));
                                    playerwithplayer.PlayWithPlayerController.map.put("player", "O");
                                    playerwithplayer.PlayWithPlayerController.map.put("opponent", "X");
                                    playerwithplayer.PlayWithPlayerController.map.put("playerTurn", false);
                                } catch (IOException ex) {
                                    //
                                }
                            }
                            if (result.get() == rejectBtn) {
                                System.out.println("Rejected");
                                HashMap<String, Object> map = new HashMap<String, Object>();
                                map.put("action", "inviteRes");
                                map.put("success", false);
                                map.put("inviter", res.get("inviter"));
                                map.put("invited", res.get("invited"));
                                try {
                                    sender.writeObject(map);
                                    inviteAccept_status = 0;
                                } catch (IOException ex) {
                                    //
                                }
                            }
                        }
                    });

                } else {
                    Alert rejection = new Alert(Alert.AlertType.CONFIRMATION);
                    rejection.setTitle("Response");
                    rejection.setHeaderText("invitation expired");
                    rejection.setContentText((String) res.get("msg"));
                    ButtonType Btn = new ButtonType("Ok");
                    rejection.getButtonTypes().setAll(Btn);
                    rejection.showAndWait();
                }
            }

            private void inviteRes(HashMap<String, Object> res) {
                System.out.println("I'm " + identity + " " + res);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if ((boolean) res.get("success")) {

                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Response");
                            alert.setHeaderText((String) res.get("msg"));
                            alert.setContentText("get ready !!");

                            ButtonType okBtn = new ButtonType("Ok");

                            alert.getButtonTypes().setAll(okBtn);
                            alert.showAndWait();
                            //move to multiplayer scene
                            invite_status = 1;
                            playerwithplayer.PlayWithPlayerController.map.put("userID", identity);
                            playerwithplayer.PlayWithPlayerController.map.put("opponentID", res.get("invited"));
                            playerwithplayer.PlayWithPlayerController.map.put("player", "X");
                            playerwithplayer.PlayWithPlayerController.map.put("opponent", "O");
                            playerwithplayer.PlayWithPlayerController.map.put("playerTurn", true);

                        } else {

                            Alert rejection = new Alert(Alert.AlertType.CONFIRMATION);
                            rejection.setTitle("Response");
                            rejection.setHeaderText("invitation rejected");
                            rejection.setContentText((String) res.get("invited") + " has rejected your invitation");
                            ButtonType Btn = new ButtonType("Ok");
                            rejection.getButtonTypes().setAll(Btn);
                            rejection.showAndWait();
                            invite_status = 0;
                        }
                    }
                });

            }

            private void signUp(HashMap<String, Object> res) {
                if ((boolean) res.get("success")) {
                    //TODO remove this line

                    signup_status = 1;

                    System.out.println("signup accepted from server");

                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("success", "true");
                    try {
                        sender.writeObject(map);
                    } catch (IOException ex) {
                        //
                    }

                } else {
                    signup_status = 0;
                    //TODO show error msg
                    System.out.println("signup refused from server");
                }
            }
            
             private void moveRes(HashMap<String, Object> res) {
                //System.out.println(res.get("userID"));
                //System.out.println(res.get("pos"));
                playerwithplayer.PlayWithPlayerController.moveResMap.put("userID", res.get("userID"));
                playerwithplayer.PlayWithPlayerController.moveResMap.put("pos", res.get("pos"));
                move_status = 1;
            }

            private void close_client(HashMap<String, Object> res) {
                /*   Stream.display.close();
                Stream.sender.close();
                Stream.mySocket.close();*/

            }
        });
        th.start();
    }
}


/*
*****CLIENT*****
first:
    1- opens login page
    2- Class Stream [sender, receiver, send(#map), receive(inside Thread while to remain listening all time)]: will hold client's global unique io streams across the game in the form of static members


 */
