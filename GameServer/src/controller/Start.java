package controller;


import connection.Connection;
import connection.Server;
import entity.MapElement;
import entity.Update;
import java.io.IOException;
import java.net.*;

/**
 *
 * @author gabriel
 */
public class Start {
    public static void main(String args[]) throws IOException {
        // Life recovery
        // Área toda
        /*
        for(int i=17;i<=19;i++) Server.getLiferecovery().add(new MapElement(i,8));
        for(int i=16;i<=20;i++) Server.getLiferecovery().add(new MapElement(i,9));
        for(int i=15;i<=21;i++) Server.getLiferecovery().add(new MapElement(i,10));
        for(int i=14;i<=22;i++) Server.getLiferecovery().add(new MapElement(i,11));
        for(int i=13;i<=23;i++) Server.getLiferecovery().add(new MapElement(i,12));
        for(int i=13;i<=23;i++) Server.getLiferecovery().add(new MapElement(i,13));
        for(int i=12;i<=24;i++) Server.getLiferecovery().add(new MapElement(i,14));
        for(int i=12;i<=24;i++) Server.getLiferecovery().add(new MapElement(i,15));
        for(int i=12;i<=24;i++) Server.getLiferecovery().add(new MapElement(i,16));
        for(int i=13;i<=23;i++) Server.getLiferecovery().add(new MapElement(i,17));
        for(int i=14;i<=22;i++) Server.getLiferecovery().add(new MapElement(i,18));
        for(int i=15;i<=21;i++) Server.getLiferecovery().add(new MapElement(i,19));
        for(int i=16;i<=20;i++) Server.getLiferecovery().add(new MapElement(i,20));
        */
        // Cinza claro
        for(int i=18;i<=18;i++) Server.getLiferecovery().add(new MapElement(i,12));
        for(int i=17;i<=19;i++) Server.getLiferecovery().add(new MapElement(i,13));
        for(int i=16;i<=20;i++) Server.getLiferecovery().add(new MapElement(i,14));
        for(int i=16;i<=20;i++) Server.getLiferecovery().add(new MapElement(i,15));
        for(int i=17;i<=19;i++) Server.getLiferecovery().add(new MapElement(i,16));
        for(int i=18;i<=18;i++) Server.getLiferecovery().add(new MapElement(i,17));
        
        // Objetos
        Server.getElements().add(new MapElement(3,0));
        Server.getElements().add(new MapElement(4,0));
        Server.getElements().add(new MapElement(3,39));
        Server.getElements().add(new MapElement(4,39));
        Server.getElements().add(new MapElement(26,39));
        Server.getElements().add(new MapElement(27,39));          
        Server.getElements().add(new MapElement(3,39));
        Server.getElements().add(new MapElement(4,39));
        Server.getElements().add(new MapElement(39,5));
        Server.getElements().add(new MapElement(39,6));
        Server.getElements().add(new MapElement(39,21));
        Server.getElements().add(new MapElement(39,22));
        Server.getElements().add(new MapElement(5,3));
        Server.getElements().add(new MapElement(6,3));
        Server.getElements().add(new MapElement(26,3));
        Server.getElements().add(new MapElement(5,6));
        Server.getElements().add(new MapElement(9,6));
        Server.getElements().add(new MapElement(10,6));
        Server.getElements().add(new MapElement(14,6));
        Server.getElements().add(new MapElement(22,6));
        Server.getElements().add(new MapElement(26,6));
        Server.getElements().add(new MapElement(27,6));
        Server.getElements().add(new MapElement(30,6));
        Server.getElements().add(new MapElement(36,6));
        Server.getElements().add(new MapElement(5,12));
        Server.getElements().add(new MapElement(30,13));
        Server.getElements().add(new MapElement(5,20));
        Server.getElements().add(new MapElement(30,20));
        Server.getElements().add(new MapElement(33,11));
        Server.getElements().add(new MapElement(34,11));
        Server.getElements().add(new MapElement(33,17));
        Server.getElements().add(new MapElement(34,17));
        Server.getElements().add(new MapElement(34,20));
        Server.getElements().add(new MapElement(35,20));
        Server.getElements().add(new MapElement(36,20));
        Server.getElements().add(new MapElement(28,23));
        Server.getElements().add(new MapElement(28,30));
        Server.getElements().add(new MapElement(28,39));
        Server.getElements().add(new MapElement(31,23));
        Server.getElements().add(new MapElement(32,23));
        Server.getElements().add(new MapElement(28,26));
        Server.getElements().add(new MapElement(29,26));
        Server.getElements().add(new MapElement(28,34));
        Server.getElements().add(new MapElement(29,34));
        
        // Área central        
        Server.getElements().add(new MapElement(18,8));
        Server.getElements().add(new MapElement(15,10));
        Server.getElements().add(new MapElement(21,10));
        Server.getElements().add(new MapElement(13,13));
        Server.getElements().add(new MapElement(23,13));
        Server.getElements().add(new MapElement(12,16));
        Server.getElements().add(new MapElement(24,16));
        Server.getElements().add(new MapElement(14,18));
        Server.getElements().add(new MapElement(22,18));
        Server.getElements().add(new MapElement(16,20));
        Server.getElements().add(new MapElement(20,20));
        Server.getElements().add(new MapElement(18,14));
        Server.getElements().add(new MapElement(18,16));
        
        // Área da casa
        for(int i=23;i<=39;i++) Server.getElements().add(new MapElement(21,i));
        for(int i=24;i<=39;i++) Server.getElements().add(new MapElement(6,i));
        Server.getElements().add(new MapElement(22,23));
        Server.getElements().add(new MapElement(23,23));
        Server.getElements().add(new MapElement(18,25));
        Server.getElements().add(new MapElement(19,25));
        Server.getElements().add(new MapElement(18,28));
        Server.getElements().add(new MapElement(19,28));
        Server.getElements().add(new MapElement(18,31));
        Server.getElements().add(new MapElement(19,31));
        Server.getElements().add(new MapElement(8,26));
        Server.getElements().add(new MapElement(11,25));
        Server.getElements().add(new MapElement(12,35));
        Server.getElements().add(new MapElement(13,35));
        Server.getElements().add(new MapElement(12,36));
        Server.getElements().add(new MapElement(13,36));
        Server.getElements().add(new MapElement(11,36));
        Server.getElements().add(new MapElement(14,36));
        Server.getElements().add(new MapElement(7,23));
        Server.getElements().add(new MapElement(8,23));
        Server.getElements().add(new MapElement(9,23));
        Server.getElements().add(new MapElement(10,23));
        Server.getElements().add(new MapElement(11,23));
        Server.getElements().add(new MapElement(5,23));
        Server.getElements().add(new MapElement(5,26));
        Server.getElements().add(new MapElement(5,30));
        Server.getElements().add(new MapElement(5,34));
        Server.getElements().add(new MapElement(5,39));
        //paredes
        for(int i=23;i<=39;i++) Server.getElements().add(new MapElement(20,i));
        for(int i=24;i<=39;i++) Server.getElements().add(new MapElement(7,i));
        for(int i=8;i<=19;i++) Server.getElements().add(new MapElement(i,39));
        Server.getElements().add(new MapElement(17,23));
        Server.getElements().add(new MapElement(18,23));
        Server.getElements().add(new MapElement(19,23));
        Server.getElements().add(new MapElement(12,23));
        Server.getElements().add(new MapElement(13,23));
        Server.getElements().add(new MapElement(14,23));
        Server.getElements().add(new MapElement(12,24));
        Server.getElements().add(new MapElement(11,24));
        Server.getElements().add(new MapElement(10,24));
        Server.getElements().add(new MapElement(9,24));
        Server.getElements().add(new MapElement(8,24));
        
        
        /*Server.getElements().add(new MapElement(2,6));
        Server.getElements().add(new MapElement(7,11));
        Server.getElements().add(new MapElement(8,11));
        Server.getElements().add(new MapElement(21,11));
        Server.getElements().add(new MapElement(22,11));
        Server.getElements().add(new MapElement(36,2));
        Server.getElements().add(new MapElement(37,2));
        Server.getElements().add(new MapElement(34,13));
        Server.getElements().add(new MapElement(35,13));
        Server.getElements().add(new MapElement(12,20));
        Server.getElements().add(new MapElement(13,20));
        Server.getElements().add(new MapElement(23,19));
        Server.getElements().add(new MapElement(24,19));
        Server.getElements().add(new MapElement(10,76));
        Server.getElements().add(new MapElement(11,76));
        Server.getElements().add(new MapElement(20,78));
        Server.getElements().add(new MapElement(21,78));
        Server.getElements().add(new MapElement(3,81));
        Server.getElements().add(new MapElement(4,81));
        Server.getElements().add(new MapElement(13,83));
        Server.getElements().add(new MapElement(14,83));
        Server.getElements().add(new MapElement(24,83));
        Server.getElements().add(new MapElement(25,83));
        Server.getElements().add(new MapElement(4,86));
        Server.getElements().add(new MapElement(5,86));
        Server.getElements().add(new MapElement(9,90));
        Server.getElements().add(new MapElement(10,90));
        Server.getElements().add(new MapElement(21,90));
        Server.getElements().add(new MapElement(22,90));
        Server.getElements().add(new MapElement(33,91));
        Server.getElements().add(new MapElement(34,91));
        Server.getElements().add(new MapElement(12,93));
        Server.getElements().add(new MapElement(13,93));
        Server.getElements().add(new MapElement(40,93));
        Server.getElements().add(new MapElement(41,93));
        Server.getElements().add(new MapElement(35,96));
        Server.getElements().add(new MapElement(36,96));
        Server.getElements().add(new MapElement(25,96));
        Server.getElements().add(new MapElement(26,96));
        Server.getElements().add(new MapElement(16,95));
        Server.getElements().add(new MapElement(17,95));
        Server.getElements().add(new MapElement(43,98));
        Server.getElements().add(new MapElement(44,98));*/
        try (ServerSocket servidor = new ServerSocket(6040)) {
            System.out.println("Servidor iniciado pela porta 6040.");
            
            Thread up = new Thread(new Update());
            up.start();
            while (!servidor.isClosed()) {
                
                Socket cliente = servidor.accept();
                
                // cria um objeto que vai tratar a conexão
                Connection con = new Connection(cliente);
                
                // cria a thread em cima deste objeto
                Thread t = new Thread(con);
                
                // inicia a thread
                t.start();
                
            }   
        } catch(Exception e){
            System.out.println("Erro: "+e.getMessage());
        }
 
   }
}
