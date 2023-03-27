/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.DAO.con;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Player;

/**
 *
 * @author nguye
 */
public class ClanRequestDAO extends DAO{

    public ClanRequestDAO() {
        super();
    }
    public boolean checkJoinRequest(Player joinPlayer){
        boolean result=false;
        String sql = "SELECT * FROM tblclanrequest WHERE playerid = ? AND clanid = ?";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, joinPlayer.getId());
            ps.setInt(2, joinPlayer.getInClan().getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                result = true;
        } catch (SQLException ex) {
            Logger.getLogger(FriendRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            return result;
    }
    public int addJoinRequest(Player joinPlayer){
        int result=0;
     if(!checkJoinRequest(joinPlayer)){
        String sql = "INSERT INTO tblclanrequest (playerid,clanid) VALUES(?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, joinPlayer.getId());
            ps.setInt(2, joinPlayer.getInClan().getId());
            ps.executeUpdate();
            result=2;
        } catch (SQLException ex) {
            Logger.getLogger(FriendRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            result=0;
        }
        }
        else
            result = 1;
        return result;
    }
    public ArrayList<Integer> getJoinRequest(int id){
        ArrayList<Integer> list = new ArrayList<>();
        String sql = "SELECT playerid FROM tblclanrequest WHERE clanid = ? ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                list.add(rs.getInt("playerid"));
        } catch (SQLException ex) {
            Logger.getLogger(FriendRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public boolean deleteJoinRequest(ArrayList<Integer> response){
        
        String sql = "DELETE FROM tblclanrequest WHERE playerid=? AND clanid=?";
        try {
            PreparedStatement ps=  con.prepareStatement(sql);
            ps.setInt(1, response.get(0));
            ps.setInt(2, response.get(1));
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(FriendRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }
}
