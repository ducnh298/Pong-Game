/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import model.Result;
/**
 *
 * @author nguye
 */
public class ResultDAO extends DAO{
    public boolean addResult(Result result){
        String sql = "INSERT INTO tblResult(matchId, tournamentId, playerId, iswin) VALUES(?,?,?,?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, result.getMatch().getId());
             if(result.getTournament()!=null)
                ps.setInt(2, result.getTournament().getId());
            else
                ps.setInt(2, 0);
           ps.setInt(3, result.getPlayer().getId());
           ps.setInt(4, result.getIsWin());
            ps.executeUpdate();
           
        }catch(Exception e){
            e.printStackTrace();
            return false;   
        }
        
        return true;
    }
    
}
