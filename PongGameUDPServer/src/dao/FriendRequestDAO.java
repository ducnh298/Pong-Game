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
import model.Clan;
import model.FriendRequest;
import model.Player;
/**
 *
 * @author nguye
 */
public class FriendRequestDAO extends DAO{
    public FriendRequestDAO(){
        super();
    }
    public boolean checkFriendRequest(ArrayList<Player> request){
        boolean result=false;
        String sql = "SELECT * FROM tblfriendrequest WHERE playersend = ? AND playerreceive = ?";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, request.get(0).getId());
            ps.setInt(2, request.get(1).getId());
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                result = true;
        } catch (SQLException ex) {
            Logger.getLogger(FriendRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            return result;
    }
    public int addFriendRequest(ArrayList<Player> request){
        int result=0;
        if(!checkFriendRequest(request)){
        String sql = "INSERT INTO tblfriendrequest (playersend,playerreceive) VALUES(?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql,java.sql.Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, request.get(0).getId());
            ps.setInt(2, request.get(1).getId());
            
            ps.executeUpdate();
             ResultSet generatedKeys = ps.getGeneratedKeys();
           if (generatedKeys.next()) {
               }       
           result=2;
        } catch (SQLException ex) {
            Logger.getLogger(FriendRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else
            result = 1;
        return result;
    }
    public FriendRequest getFriendRequest(int id){
        FriendRequest friendRQ = new FriendRequest();
        ArrayList<Player> list = new ArrayList<>();
        String sql = "SELECT * FROM tblfriendrequest LEFT OUTER JOIN tblplayer ON tblfriendrequest.playersend=tblplayer.id WHERE playerreceive = ? ";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
               Player pl= new Player();
                pl.setId(rs.getInt("tblplayer.id"));
                pl.setNickName(rs.getString("nickname"));
                pl.setEmail(rs.getString("email"));
                pl.setMatchWon(rs.getInt("matchwon"));
                pl.setMatchPlayed(rs.getInt("matchplayed"));
                Clan clan = new Clan();
                clan.setId(rs.getInt("inclan"));
                pl.setInClan(clan);
              list.add(pl); 
        }
            Player playerReceive = new Player();
            playerReceive.setId(id);
            friendRQ.setPlayerReceive(playerReceive);
            friendRQ.setPlayerSend(list);
        } catch (SQLException ex) {
            Logger.getLogger(FriendRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return friendRQ;
    }
    public boolean deleteFriendRequest(ArrayList<Integer> response){
        
        String sql = "DELETE FROM tblfriendrequest WHERE (playersend=? AND playerreceive=?) OR (playersend=? AND playerreceive=?) ";
        try {
            PreparedStatement ps=  con.prepareStatement(sql);
            ps.setInt(1, response.get(0));
            ps.setInt(2, response.get(1));
            ps.setInt(3, response.get(1));
            ps.setInt(4, response.get(0));
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(FriendRequestDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }
}
