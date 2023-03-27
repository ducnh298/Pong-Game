/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author nguye
 */
public class Player  implements Serializable{
    private int id;
    private String nickName;
    private String email;
    private String status;
    private int matchWon;
    private int matchPlayed;
    private Clan inClan;
    public Player() {
        super();
    }

    public Player(int id, String nickName, String status, int matchWon, int matchPlayed, Clan inclan) {
        this.id = id;
        this.nickName = nickName;
        this.status = status;
        this.matchWon = matchWon;
        this.matchPlayed = matchPlayed;
        this.inClan = inclan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Clan getInClan() {
        return inClan;
    }

    public void setInClan(Clan inClan) {
        this.inClan = inClan;
    }

    public  int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public int getMatchWon() {
        return matchWon;
    }

    public void setMatchWon(int matchWon) {
        this.matchWon = matchWon;
    }

    public int getMatchPlayed() {
        return matchPlayed;
    }

    public void setMatchPlayed(int matchPlayed) {
        this.matchPlayed = matchPlayed;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
