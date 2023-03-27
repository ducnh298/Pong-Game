/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.DAO.con;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Match;

/**
 *
 * @author nguye
 */
public class MatchDAO extends DAO{
    public Match createMatch(Match match){
        String sql ="INSERT INTO tblmatch(date,tournamentid) VALUES(?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, (Date) match.getDate());
            if(match.getTournament()!=null)
                ps.setInt(2, match.getTournament().getId());
            else
                 ps.setInt(2, 0);
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
           if (generatedKeys.next()) {
                match.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MatchDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return match;
    }
    public boolean doneMatch(ArrayList<Integer> list){
        
        String sql ="DELETE FROM tblmatch WHERE (player1 = ? AND player2 = ?) OR (player2 = ? AND player1 = ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, list.get(0));
            ps.setInt(2, list.get(1));
            ps.setInt(3, list.get(0));
            ps.setInt(4, list.get(1));
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(MatchDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
//    public int createTournamentMatch(int tournamentId) throws ParseException{
//        Match match = new Match();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
//        match.setDate(sdf.parse(java.time.LocalDateTime.now().toString()));
//        match.setId(0);
//        match.setTournamentId(tournamentId);
//        String sql = "INSERT INTO tblMatch(date, tournamentid) VALUES(?,?)";
//        try{
//            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//           // ps.setInt(1, match.getId());
//            ps.setDate(1, (Date) match.getDate());
//            ps.setFloat(2, match.getTournamentId());
//
//            ps.executeUpdate();
//             
//            //get id of the new inserted client
//            ResultSet generatedKeys = ps.getGeneratedKeys();
//           if (generatedKeys.next()) {
//                match.setId(generatedKeys.getInt("id"));
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//           
//        }
//        return match.getId();
//    }
//    public int createNormalMatch() throws ParseException{
//        Match match = new Match();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
//        match.setDate(sdf.parse(java.time.LocalDateTime.now().toString()));
//        match.setTournamentId(0);
//        String sql = "INSERT INTO tblMatch( date, tournamentid) VALUES(?,?)";
//        try{
//            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            //ps.setInt(1, match.getId());
//            ps.setDate(1, (Date) match.getDate());
//            ps.setFloat(2, match.getTournamentId());
//          
//        
//            ps.executeUpdate();
//             
//            //get id of the new inserted client
//            ResultSet generatedKeys = ps.getGeneratedKeys();
//           if (generatedKeys.next()) {
//                match.setId(generatedKeys.getInt("id"));
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//            
//        }
//        return match.getId();
//    }
}
