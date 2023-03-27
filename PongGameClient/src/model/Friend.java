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
public class Friend implements Serializable{
    private static int id;
    private Player player;
    private ArrayList<Player> friend;

    public Friend() {
    }

    public Friend(int id, Player player, ArrayList<Player> friend) {
        this.id = id;
        this.player = player;
        this.friend = friend;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Player> getFriend() {
        return friend;
    }

    public void setFriend(ArrayList<Player> friend) {
        this.friend = friend;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
}
