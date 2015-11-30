package db;

import entity.User;
import entity.Character;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author strudel
 */
public class CharacterDAO {
    private final Connection connection;
    
    public CharacterDAO(){
        this.connection = ConnectionFactory.getConnection();
    }
    
    public void add(Character c,User u){
        String sql = "insert into ksen_characters " + "(class, level, score, points,attack,defense,dexterity,critical,speed,user_id)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, c.getClasse());
                stmt.setInt(2, c.getLevel());
                stmt.setInt(3, c.getScore());
                stmt.setInt(4, c.getPoints());
                stmt.setInt(5, c.getAtaque());
                stmt.setInt(6, c.getDefesa());
                stmt.setInt(7, c.getPrecisao());
                stmt.setInt(8, c.getCritico());
                stmt.setFloat(9, c.getVelocidade());
                stmt.setInt(10, u.getId());        
                stmt.execute();
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(CharacterDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
 
    }
    
    public void update(Character c){
        String sql = "update ksen_characters set class = ?, level = ?, score = ?, points = ?, attack = ?, defense = ?, dexterity = ?, critical = ?, speed = ? where id = ?";
        try{
            System.out.println("Updating id "+c.getId());
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, c.getClasse());
            stmt.setInt(2, c.getLevel());
            stmt.setInt(3, c.getScore());
            stmt.setInt(4, c.getPoints());
            stmt.setInt(5, c.getAtaque());
            stmt.setInt(6, c.getDefesa());
            stmt.setInt(7, c.getPrecisao());
            stmt.setInt(8, c.getCritico());
            stmt.setFloat(9, c.getVelocidade());
            stmt.setInt(10, c.getId());
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro: "+ex.getMessage());
        }        
    }
    
    public Character userClass(User u, int classe){
        Character character = null;
        try {
            PreparedStatement stmt = this.connection.prepareStatement("select * from ksen_characters WHERE user_id=? AND class=?");
            stmt.setInt(1, u.getId());
            stmt.setInt(2, classe);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                character = new Character();
                character.setClasse(classe);
                character.setId(rs.getInt("id"));
                character.setLevel(rs.getInt("level"));
                character.setScore(rs.getInt("score"));
                character.setPoints(rs.getInt("points"));
                character.setAtaque(rs.getInt("attack"));
                character.setDefesa(rs.getInt("defense"));
                character.setPrecisao(rs.getInt("dexterity"));
                character.setCritico(rs.getInt("critical"));
                character.setVelocidade(rs.getFloat("speed"));     
                character.setNome(u.getLogin());
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("Erro: "+ex.getMessage());
        }
       return character;
    }
    
}
