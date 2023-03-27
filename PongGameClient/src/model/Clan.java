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
public class Clan implements Serializable{
    private int id;
    private String name;
    private Player leaderId;
    private int maxMember;
    private int type;
    private String des;

    public Clan() {
    }

    public Clan(int id, String name, Player leaderId, int maxMember,int type, String des) {
        this.id = id;
        this.name = name;
        this.leaderId = leaderId;
        this.maxMember = maxMember;
        this.type=type;
        this.des = des;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public Player getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Player leaderId) {
        this.leaderId = leaderId;
    }

    

    public int getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(int maxMember) {
        this.maxMember = maxMember;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
    
}
