/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playerlist;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Menna
 */
public class Player {
    private SimpleStringProperty userName, score, status, isPlaying;

    public Player(String userName, String score, String status, String isPlaying) {
        this.userName = new SimpleStringProperty(userName);
        this.score = new SimpleStringProperty(score);
        this.status = new SimpleStringProperty(status);
        this.isPlaying = new SimpleStringProperty(isPlaying);
    }

    public String getUserName() {
        return userName.get();
    }

    public void setUserName(SimpleStringProperty userName) {
        this.userName = userName;
    }

    public String getScore() {
        return score.get();
    }

    public void setScore(SimpleStringProperty score) {
        this.score = score;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(SimpleStringProperty status) {
        this.status = status;
    }

    public String getIsPlaying() {
        return isPlaying.get();
    }

    public void setIsPlaying(SimpleStringProperty isPlaying) {
        this.isPlaying = isPlaying;
    }
    
}
