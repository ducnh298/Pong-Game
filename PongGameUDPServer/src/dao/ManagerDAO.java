/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.DAO.con;
import java.sql.PreparedStatement;
import model.Manager;
/**
 *
 * @author nguye
 */
public class ManagerDAO extends DAO{
    public boolean SetName(int id, String name){
        String sql = "UPDATE SET name = ? WHERE id = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%"+name+"%");
            ps.setInt(2, id);
            
            ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace(); 
            return false;
        }
        return true;        
    }
}
