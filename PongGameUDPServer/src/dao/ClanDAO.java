/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import model.Clan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Player;
/**
 *
 * @author nguye
 */
public class ClanDAO extends DAO{

    public ClanDAO() {
        super();
    }
    public boolean checkName(String name){
        boolean result=false;
        String sql="SELECT * FROM tblclan WHERE name =?";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                result=true;
        } catch (SQLException ex) {
            Logger.getLogger(ClanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            return result;
    }
    public ArrayList<Clan> getClanList(String key){
        ArrayList<Clan> list =new ArrayList<>();
        String sql ="SELECT * FROM tblclan INNER JOIN tblPlayer ON tblclan.leaderid= tblplayer.id WHERE name LIKE ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%"+key+"%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Clan clan = new Clan();
                clan.setId(rs.getInt("id"));
                clan.setName(rs.getString("name"));
                clan.setMaxMember(rs.getInt("maxmember"));
                clan.setType(rs.getInt("type"));
                clan.setDes(rs.getString("des"));
                Player leader = new Player();
                leader.setId(rs.getInt("tblplayer.id"));
                leader.setNickName(rs.getString("tblplayer.nickName"));
                leader.setEmail(rs.getString("tblplayer.email"));
                leader.setMatchWon(rs.getInt("tblplayer.matchwon"));
                leader.setMatchPlayed(rs.getInt("tblplayer.matchplayed"));
                leader.setStatus(rs.getString("tblplayer.status"));
                leader.setInClan(clan);
                clan.setLeaderId(leader);
               
                list.add(clan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClanDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return list;
    }
    public Clan createClan(Clan clan){
        if(checkName(clan.getName()))
                return null;
        String sql = "INSERT INTO tblclan(name,leaderid,maxmember,type,des) VALUES(?,?,?,?,?)";
        PreparedStatement ps;
        try {
            ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, clan.getName());
            ps.setInt(2, clan.getLeaderId().getId());
            ps.setInt(3, clan.getMaxMember());
            ps.setInt(4, clan.getType());
            ps.setString(5, clan.getDes());
             ps.executeUpdate();
             ResultSet generatedKeys = ps.getGeneratedKeys();
           if (generatedKeys.next()) {
                clan.setId(generatedKeys.getInt(1));}
                
        } catch (SQLException ex) {
            Logger.getLogger(ClanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clan;
    }
    public boolean editClan(Clan clan){
        boolean result=false;
        String sql = "UPDATE tblclan SET name=?, maxmember=?,type=?, des=? WHERE id = ?";
       try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, clan.getName());
            ps.setInt(2, clan.getMaxMember());
            ps.setInt(3, clan.getType());
            ps.setString(4, clan.getDes());
            ps.setInt(5,clan.getId());
           
            ps.executeUpdate();
            result=true;
        }catch(Exception e){
            e.printStackTrace();
            result=false;
        }
        return result;
    }
}
