/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import static dao.DAO.con;
import java.sql.Date;
import java.sql.PreparedStatement;

import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import model.Tournament;
/**
 *
 * @author nguye
 */
public class TournamentDAO extends DAO{
    
      public boolean EditTournament(Tournament t) throws ParseException{
          String sql = "UPDATE tbltournament SET name=?, managerId=?, date=?, maxplayer=?, numberPlayerCurrent =?,  des=? WHERE id=?";
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        t.setDate(sdf.parse(java.time.LocalDateTime.now().toString()));
          try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t.getName());
            ps.setInt(2, t.getManager().getId());
            ps.setDate(3, (Date) t.getDate());
            ps.setInt(4, t.getMaxPlayer());
            ps.setInt(5, t.getNumberPlayerCurrent());
            ps.setString(6, t.getDes());
            ps.setInt(7, t.getId());
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }       
        return true;
      }
    public boolean DeleteTournament(Tournament t){
        String sql = "DELETE FROM tbltournament WHERE id = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, t.getId());
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }       
        return true;
      }
}
