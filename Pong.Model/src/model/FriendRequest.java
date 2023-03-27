/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author nguye
 */
public class FriendRequest implements Serializable{
    private static int id;
    private ArrayList<Player> playerSend;
    private Player playerReceive;
    public FriendRequest() {
    }

    public FriendRequest(ArrayList<Player> playerSend, Player playerReceive) {
        this.playerSend = playerSend;
        this.playerReceive = playerReceive;
    }

    public ArrayList<Player> getPlayerSend() {
        return playerSend;
    }

    public void setPlayerSend(ArrayList<Player> playerSend) {
        this.playerSend = playerSend;
    }

    public Player getPlayerReceive() {
        return playerReceive;
    }

    public void setPlayerReceive(Player playerReceive) {
        this.playerReceive = playerReceive;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
