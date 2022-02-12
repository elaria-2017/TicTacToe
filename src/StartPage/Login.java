///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package StartPage;
//
//import Connections.Stream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import playerlist.PlayerList;
//import playerlist.PlayerListController;
//
///**
// *
// * @author sara
// */
//public class Login extends Application {
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
//
//        Scene scene = new Scene(root);
//
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    @Override
//    public void stop() {
//        System.out.println("Stage of " + Stream.identity + " is closing...........");
//        try {
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("action", "offline");
//            map.put("userID", Stream.identity);
//            Stream.sender.writeObject(map);
//            Stream.display.close();
//            Stream.sender.close();
//            Stream.mySocket.close();
//            Stream.th.stop();
//            try {
//                super.stop();
//            } catch (Exception ex) {
//                Logger.getLogger(PlayerList.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(PlayerListController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//}
