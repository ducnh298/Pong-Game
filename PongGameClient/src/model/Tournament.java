/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author nguye
 */
public class Tournament implements Serializable{
    private int id;
    private String name;
    private Player manager;
    private Date date;
    private int maxPlayer;
    private int numberPlayerCurrent;
    private String des;

    public Tournament() {
    }

    public Tournament(int id, String name, Player managerID, Date date, int maxPlayer, int numberPlayerCurrent, String des) {
        this.id = id;
        this.name = name;
        this.manager = managerID;
        this.date = date;
        this.maxPlayer = maxPlayer;
        this.numberPlayerCurrent = numberPlayerCurrent;
        this.des = des;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getManager() {
        return manager;
    }

    public void setManager(Player manager) {
        this.manager = manager;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public int getNumberPlayerCurrent() {
        return numberPlayerCurrent;
    }

    public void setNumberPlayerCurrent(int numberPlayerCurrent) {
        this.numberPlayerCurrent = numberPlayerCurrent;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
    
}
