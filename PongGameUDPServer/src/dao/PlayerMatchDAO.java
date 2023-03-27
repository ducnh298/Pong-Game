/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import static dao.DAO.con;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import model.PlayerMatch;

/**
 *
 * @author nguye
 */
public class PlayerMatchDAO extends DAO{
    public PlayerMatch addPlayerMatch(PlayerMatch pm){
        
        String sql = "INSERT INTO tblplayermatch(matchid, playerid, tournamentid) VALUES(?,?,?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, pm.getMatch().getId());
            ps.setInt(2, pm.getPlayer().getId());
            if(pm.getTournament()!=null)
                ps.setInt(3, pm.getTournament().getId());
            else
                ps.setInt(3, 0);
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
           if (generatedKeys.next()) {
                //pm.setId(generatedKeys.getInt("id"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return pm;
    }

    
}
