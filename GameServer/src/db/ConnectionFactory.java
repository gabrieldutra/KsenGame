package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author strudel
 */
public class ConnectionFactory {
    //private static final String address = "localhost";
    private static final String address = "alunos.coltec.ufmg.br";
    private static final int port = 3306;
    //private static final String user = "root";
    //private static final String password = "allods6655";
    private static final String user = "daw-aluno3";
    private static final String password = "gabriel";
    private static final String db = "daw-aluno3";
    
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            return DriverManager.getConnection("jdbc:mysql://"+address+":"+port+"/"+db,user,password);
        } catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
