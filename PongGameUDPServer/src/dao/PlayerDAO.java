/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Clan;
import model.Player;

/**
 *
 * @author nguye
 */
public class PlayerDAO extends DAO{
    public PlayerDAO(){
        super();
    }
    public boolean checkName(String name){
        boolean result=false;
        String sql="SELECT nickname FROM tblplayer WHERE nickname =?";
        PreparedStatement ps;

        try {
            ps = con.prepareStatement(sql);
              ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                result=true;
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        return result;
    }
    public boolean RegisterPlayer(int id){
          boolean result = false;
          String nickname = "player_"+id;
          String sql = "INSERT INTO tblplayer(id, nickname,status) VALUES(?,?,?)";
              try{
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1,id);
                ps.setString(2, nickname);
                ps.setString(3, "offline");
                ps.executeUpdate();
                     result = true;
              }catch(Exception e){
            e.printStackTrace();
            }   
              return result;
    }
    public ArrayList<Player> getPlayerList(){
        ArrayList<Player> result = new ArrayList<Player>();
         String sql = "SELECT * FROM tblPlayer LEFT OUTER JOIN tblClan ON tblplayer.inclan = tblclan.id";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
 
            while(rs.next()){
                Player rm = new Player();
                rm.setId(rs.getInt("tblplayer.id"));
                rm.setNickName(rs.getString("nickName"));
                rm.setEmail(rs.getString("email"));
                rm.setMatchWon(rs.getInt("matchwon"));
                rm.setMatchPlayed(rs.getInt("matchplayed"));
                rm.setStatus(rs.getString("status"));
                if(rs.getInt("inclan")!=0){
                Clan clan = new Clan();
                clan.setId(rs.getInt("tblclan.id"));
                clan.setName(rs.getString("tblclan.name"));
                clan.setMaxMember(rs.getInt("tblclan.maxmember"));
                clan.setType(rs.getInt("type"));
                clan.setDes(rs.getString("des"));
                Player leader = new Player();
                leader.setId(rs.getInt("leaderid"));
                clan.setLeaderId(leader);
                rm.setInClan(clan);
                }
                else
                    rm.setInClan(null);
                result.add(rm);   
            }
        }catch(Exception e){
            e.printStackTrace();
        }   
        return result;
    }
    public boolean editPlayer(Player player){
       boolean result=false;
       String sql = "UPDATE tblplayer SET nickname=?, email=?, matchwon=?, matchplayed=?, inclan=? WHERE id = ?";
       try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, player.getNickName());
            ps.setString(2, player.getEmail());
            ps.setInt(3, player.getMatchWon());
            ps.setInt(4, player.getMatchPlayed());
            ps.setInt(5, player.getInClan().getId());
            ps.setInt(6, player.getId());         
            ps.executeUpdate();
            result=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public boolean setInClan(Player player){
        boolean result=false;
        if(player.getInClan()==null){
            Clan clan = new Clan();
            clan.setId(0);
            player.setInClan(clan);
        }
        String sql="UPDATE tblplayer SET inclan=? WHERE id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, player.getInClan().getId());
            ps.setInt(2, player.getId());
            ps.executeUpdate();
            result=true;
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
            result=false;
        }
        
        return result;
    }
    
    public ArrayList<Player> getRank(String key){
        ArrayList<Player> list = new ArrayList<Player>();
        String sql = "SELECT * FROM tblPlayer LEFT OUTER JOIN tblClan ON tblplayer.inclan = tblclan.id WHERE nickname LIKE ? ";
         try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%"+key+"%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Player player = new Player();
                player.setId(rs.getInt("tblplayer.id"));
                player.setNickName(rs.getString("nickname"));
                player.setMatchWon(rs.getInt("matchwon"));
                player.setMatchPlayed(rs.getInt("matchplayed"));
                player.setStatus(rs.getString("status"));
                
                if(rs.getInt("inclan")!=0){
                Clan clan = new Clan();
                clan.setId(rs.getInt("tblclan.id"));
                clan.setName(rs.getString("tblclan.name"));
                clan.setMaxMember(rs.getInt("tblclan.maxmember"));
                clan.setType(rs.getInt("type"));
                clan.setDes(rs.getString("des"));
                Player leader = new Player();
                leader.setId(rs.getInt("leaderid"));
                clan.setLeaderId(leader);
                 player.setInClan(clan);
                }
                else player.setInClan(null);
               
                list.add(player);
            }
        }catch(Exception e){
            e.printStackTrace();
           
        }
        return list;
    }
    public Player getPlayer(int id){
        Player pl= new Player();
         String sql = "SELECT * FROM tblPlayer WHERE id =?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
           if(rs.next()){
                pl = new Player();
                pl.setId(rs.getInt("id"));
                pl.setNickName(rs.getString("nickName"));
                pl.setEmail(rs.getString("email"));
                pl.setMatchWon(rs.getInt("matchwon"));
                pl.setMatchPlayed(rs.getInt("matchplayed"));
                pl.setStatus(rs.getString("status"));
                pl.setInClan(null);
                
                return pl;
            }
        }catch(Exception e){
            e.printStackTrace();
        }   
        return null;
    }
    public boolean updateResultPlayer(Player player){
        boolean result=false;
        int isWin = player.getMatchWon();
        player= getPlayer(player.getId());
        int matchWon = player.getMatchWon() + isWin;
        int matchPlayed = player.getMatchPlayed()+1;
        player.setMatchWon(matchWon);
        player.setMatchPlayed(matchPlayed);
        String sql="UPDATE tblplayer SET matchWon=?, matchPlayed = ? WHERE id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, player.getMatchWon());
            ps.setInt(2, player.getMatchPlayed());
            ps.setInt(3, player.getId());
            ps.executeUpdate();
            result=true;
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
            result=false;
        }
        
        return result;
    }
}
