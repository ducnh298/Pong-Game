/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

//import com.mysql.cj.xdevapi.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;
import model.Player;

/**
 *
 * @author nguye
 */
public class UserDAO extends DAO{
    public UserDAO(){
        super();
    }
     
    public int CheckLogin(User user) {
        int result=0;
        String sql = "SELECT * FROM tblUser WHERE username = ? AND password = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
            result = rs.getInt("id");
            }   
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
      public int Register(User user){
          //boolean result=false;
          int result=0;
        String sql = "INSERT INTO tbluser(username, password, position) VALUES(?,?,?)";
              try{               
                PreparedStatement ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getPosition());
                ps.executeUpdate();
                ResultSet generatedKeys = ps.getGeneratedKeys();
           if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));}
                result= user.getId();
                }catch(Exception e){
            e.printStackTrace();
            }     
              return result;
            //  return result;  
 }
      
    public User getUser(int id){
        String sql = "SELECT * FROM tbluser WHERE id = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                User user = new User();
                user.setId(id);
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setPosition(rs.getString("position"));
                return user;
            }   
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean ChangePassword(User user){

        String sql = "UPDATE tbluser SET password = ? WHERE id = ? ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getPassword());
            ps.setInt(2, user.getId());
           
            ps.executeUpdate();
            
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
   
}
