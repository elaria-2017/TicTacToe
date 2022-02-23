/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author sara
 */
class ListPlayers {
  private String name,status; 
  
  private Integer score;

    public ListPlayers(String name, String status, Integer score) {
        this.name = name;
        this.status = status;
        
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    

    public Integer getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

    public void setScore(Integer score) {
        this.score = score;
    }
    
}
