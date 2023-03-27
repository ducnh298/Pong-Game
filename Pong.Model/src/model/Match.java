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
public class Match implements Serializable{
    
    private static int id;
    private Date date;
    private Tournament tournament;

    public Match() {
    }

    public Match(Date date, Tournament tournament) {
        this.date = date;
        this.tournament = tournament;
    }

    public int getId() {
        return id;
    }

    public static void setId(int id) {
        Match.id = id;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

   
    
        
}
