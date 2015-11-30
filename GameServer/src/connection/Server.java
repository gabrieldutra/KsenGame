package connection;


import db.CharacterDAO;
import db.UserDAO;
import entity.Character;
import entity.Element;
import entity.Fireball;
import entity.MapElement;
import entity.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author gabriel
 */
public class Server {
    private static Character[] personagens = new Character[0];
    private static final ArrayList<MapElement> elements = new ArrayList<>();
    private static ArrayList<MapElement> liferecovery = new ArrayList<>();
    private static final ArrayList<Fireball> fireballs = new ArrayList<>();
    public static final UserDAO udao = new UserDAO();
    public static final CharacterDAO cdao = new CharacterDAO();

    /**
     * @return the personagens
     */
    public static Character[] getPersonagens() {
        return personagens;
    }

    /**
     * @param aPersonagens the personagens to set
     */
    public static void setPersonagens(Character[] aPersonagens) {
        personagens = aPersonagens;
    }
    
    public static void addPersonagem(Character p){
        Character[] novo = new Character[Server.getPersonagens().length+1];
        for(int i=0;i<Server.getPersonagens().length;i++){
            novo[i] = Server.getPersonagens()[i];
        }
        novo[Server.getPersonagens().length] = p;
        Server.setPersonagens(novo);
    }
    
     public static void remPersonagem(Character p){
        if(Server.getPersonagens().length > 0){
            Character[] novo = new Character[Server.getPersonagens().length-1];
            int count=0;
            for(int i=0;i<Server.getPersonagens().length;i++){
                if(Server.getPersonagens()[i] == p) continue;
                if(count == Server.getPersonagens().length-1) break;
                novo[count] = Server.getPersonagens()[i];
                count++;
            }
            if(count < Server.getPersonagens().length) Server.setPersonagens(novo);
        }
    }
     
     public static void sendMessageToAll(String message){
         for(Character p: personagens){
             p.getConexao().sendMessage(message);
         }
     }
     
     public static void soundToWhoCanSee(Element e,String soundDir){
         for(Character c : Server.getPersonagens()){
             if(c.canSee(e)) c.getConexao().sendMessage("USP:"+soundDir+"\\");
         }
     }
     
     public static Character getPersonagem(String nick){
         for(Character p: personagens){
             if(p.getNome().equals(nick)) return p;
         }
         return null;
     }
    
    public static String checkName(String name, String password, Connection c){
        for(int i=0;i<Server.getPersonagens().length;i++){
            if(Server.getPersonagens()[i].getNome().equals(name)) return "Nick já conectado!";
        }
        if(name.equalsIgnoreCase("Server")) return "Nick inválido!";
        if(name.length()< 3) return "Min. 3 caracteres (Nick)!";
        if(name.length()> 7) return "Máx. 7 caracteres (Nick)!";
        if(password.length()< 4) return "Min. 4 caracteres (Senha)!";
        if(password.length()> 10) return "Máx. 10 caracteres (Senha)!";
        return Server.checkLogin(name, password,c);
    }
    
    public static String checkLogin(String name, String password, Connection c){
        //UserDAO udao = new UserDAO();
        if(!udao.userExist(name)){
            User u = new User();
            u.setLogin(name);
            u.setPassword(password);
            u.setAdmin(0);
            udao.add(u);
            return Server.checkLogin(name, password, c);
        } else {
            User u = udao.checkLogin(name, password);
            if(u == null){
                return "Senha incorreta!";
            } else {
                c.setUser(u);
            }
        }
        return "true";
    }

    /**
     * @return the elements
     */
    public static ArrayList<MapElement> getElements() {
        return elements;
    }

    /**
     * @return the fireballs
     */
    public static ArrayList<Fireball> getFireballs() {
        return fireballs;
    }
    
    public synchronized static void addFireball(Fireball f) {
        Server.getFireballs().add(f);
    }
    
    public synchronized static ArrayList<Fireball> getFireballList() {
        ArrayList<Fireball> list = new ArrayList<>();
        for(Fireball f : Server.getFireballs()) list.add(f);
        return list;
    }
    
    public synchronized static void removeFireball(Fireball f) {
        Iterator fbS = Server.getFireballs().iterator();
        while(fbS.hasNext()){
            Fireball fb = (Fireball) fbS.next();
            if(fb.getId() == f.getId()) fbS.remove();                  

        }
    }

    /**
     * @return the liferecovery
     */
    public static ArrayList<MapElement> getLiferecovery() {
        return liferecovery;
    }

    /**
     * @param aLiferecovery the liferecovery to set
     */
    public static void setLiferecovery(ArrayList<MapElement> aLiferecovery) {
        liferecovery = aLiferecovery;
    }
}
