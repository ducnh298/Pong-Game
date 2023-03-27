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
import model.Friend;
import model.Player;
/**
 *
 * @author nguye
 */
public class FriendDAO extends DAO{

    public FriendDAO() {
        super();
    }
    public Friend getFriendList(int id){
         Friend listfriend = new Friend();
        ArrayList<Player> list = new ArrayList<>();
        String sql = "SELECT * FROM tblfriend LEFT OUTER JOIN tblplayer ON tblfriend.friend=tblplayer.id WHERE player = ?";
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
           ArrayList<Player> list2= getFriendList2(id);
            for(int i=0;i<list2.size();i++){
                list.add(list2.get(i));
            }
            Player player = new Player();
            player.setId(id);
           
            listfriend.setPlayer(player);
            listfriend.setFriend(list);
        } catch (SQLException ex) {
            Logger.getLogger(FriendDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listfriend;
    }
    public ArrayList<Player> getFriendList2(int id){ 
        ArrayList<Player> list2 = new ArrayList<>();
        String sql = "SELECT * FROM tblfriend LEFT OUTER JOIN tblplayer ON tblfriend.player=tblplayer.id WHERE friend = ?";
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
              list2.add(pl);  
            }
        } catch (SQLException ex) {
            Logger.getLogger(FriendDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list2;
    }
    public boolean addFriend(int player1,int player2){
        
        String sql = "INSERT INTO tblfriend(player,friend) VALUES(?,?)";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, player1);
            ps.setInt(2, player2);
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(FriendDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
     public boolean unFriend(int player1,int player2){
        
        String sql = "DELETE FROM tblfriend WHERE (player=? AND friend=?) OR (player=? AND friend=?)";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, player1);
            ps.setInt(2, player2);
            ps.setInt(3, player2);
            ps.setInt(4, player1);
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FriendDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
