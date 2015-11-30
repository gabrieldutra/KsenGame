/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author strudel
 */
public class UserDAO {
    private final Connection connection;
    
    public UserDAO(){
        this.connection = ConnectionFactory.getConnection();
    }
    
    public void add(User u){
        String sql = "insert into ksen_users " + "(login, password, admin)" + " values (?, ?, ?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, u.getLogin());
            stmt.setString(2, u.getPassword());
            stmt.setInt(3,u.getAdmin());            
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro: "+ex.getMessage());
        }        
    }
    
    public void update(User u){
        String sql = "update ksen_users set login = ?, password = ?, admin = ? where id = ?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, u.getLogin());
            stmt.setString(2, u.getPassword());
            stmt.setInt(3,u.getAdmin());  
            stmt.setInt(4, u.getId());
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro: "+ex.getMessage());
        }        
    }
    
    public User checkLogin(String login, String password){
        User user = null;
        try {
        PreparedStatement stmt = this.connection.prepareStatement("select * from ksen_users WHERE login=? AND password=?");
        stmt.setString(1, login);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setId(rs.getInt("id"));
            user.setAdmin(rs.getInt("admin"));
        }
        stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro: "+ex.getMessage());
        }
       return user;
    }
    
    public User getUser(String login){
        User user = null;
        try {
        PreparedStatement stmt = this.connection.prepareStatement("select * from ksen_users WHERE login=?");
        stmt.setString(1, login);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            user = new User();
            user.setLogin(login);
            user.setPassword(rs.getString("password"));
            user.setId(rs.getInt("id"));
            user.setAdmin(rs.getInt("admin"));
        }
        stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro: "+ex.getMessage());
        }
       return user;
    }
    
    public boolean userExist(String login){
        try {
        PreparedStatement stmt = this.connection.prepareStatement("select * from ksen_users WHERE login=?");
        stmt.setString(1, login);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            return true;
        }
        stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro: "+ex.getMessage());
        }
       return false;
    }
}
