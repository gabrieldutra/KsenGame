package connection;

import entity.Attribute;
import entity.Character;
import entity.Fireball;
import entity.Ninja;
import entity.Warrior;
import entity.Wizard;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 *
 * @author gabriel
 */
public class Connection implements Runnable {
    private ArrayList<Integer> pings = new ArrayList<>();

    public Connection(Character personagem, Character[] players) {
        //Client.setPersonagem(personagem);
        Client.setPlayers(players);
    }
    
    private void tratar(String nl) throws SlickException{
        if(nl.contains("\\")){
            this.tratar(nl.substring(0,nl.indexOf("\\")));
            if(nl.substring(nl.indexOf("\\")).length() > 1) this.tratar(nl.substring(nl.indexOf("\\")+1));
        } else if(nl.length() > 3) {
            //System.out.println(nl+" "+players.length);
            String nltype = nl.substring(0, 3);
            String nlmsg = "";
            if(nl.length() > 4) nlmsg = nl.substring(4);
            
            // Informações do próprio usuário (iniciadas por US)
            
            // User Name
            if(nltype.equals("USN")){
                Client.getPersonagem().setNome(nlmsg);
            }
                
            // User disconnect
            if(nltype.equals("USD")){
                Client.remPersonagem(nlmsg);
                Client.addMessage(nlmsg+" se desconectou do servidor.", Color.red);
            }
            
            // User disconnect
            if(nltype.equals("USC")){
                Client.addMessage(nlmsg+" se conectou do servidor.", Color.green);
            }
            
            // User Life
            if(nltype.equals("USV")){
                int newLife = Integer.parseInt(nlmsg);
                if(newLife != Client.getPersonagem().getVida()){
                    Client.getPersonagem().setLifeChange(newLife-Client.getPersonagem().getVida());
                    Client.getPersonagem().setLifeTime(System.currentTimeMillis()+1500);
                }
                Client.getPersonagem().setVida(Integer.parseInt(nlmsg));
            }
            
            // User Mana
            if(nltype.equals("USM")){
                Client.getPersonagem().setMana(Integer.parseInt(nlmsg));
            }
            
            // User PosX
            if(nltype.equals("USX")){
                if(Client.getPersonagem().getPosX() == 0) Client.getPersonagem().setPosX(Float.parseFloat(nlmsg));
                if(Client.getPersonagem().getPosX1() == 0) Client.getPersonagem().setPosX1(Float.parseFloat(nlmsg));
                Client.getPersonagem().setPosX2(Float.parseFloat(nlmsg));
            }
            
            // User PosY
            if(nltype.equals("USY")){
                if(Client.getPersonagem().getPosY() == 0) Client.getPersonagem().setPosY(Float.parseFloat(nlmsg));
                if(Client.getPersonagem().getPosY1() == 0) Client.getPersonagem().setPosY1(Float.parseFloat(nlmsg));
                Client.getPersonagem().setPosY2(Float.parseFloat(nlmsg));
            }
            
            // User Attack
            if(nltype.equals("USA")){  
                if(Client.getPersonagem().getClass().getCanonicalName().contains("Warrior")){
                    Warrior wpersonage = (Warrior) Client.getPersonagem();
                    wpersonage.setSwAtacking(Boolean.parseBoolean(nlmsg));
                } else 
                Client.getPersonagem().setAttacking(Boolean.parseBoolean(nlmsg));  
            }
            
            // User Sprint
            if(nltype.equals("USS")){
                Client.getPersonagem().setSprinting(Boolean.parseBoolean(nlmsg));
            }
            
            // User Play Sound
            if(nltype.equals("USP")){
                new Sound(nlmsg).play();
                if(nlmsg.equals("res/sound/nelson.ogg")){
                    Client.setNelsonTime(System.currentTimeMillis()+1000);
                }
            }
            
            // User Set Attribute
            if(nltype.equals("UAA")){
                String atCode=nlmsg.substring(0, nlmsg.indexOf(":"));
                String atValue=nlmsg.substring(nlmsg.indexOf(":")+1);
                for(Attribute a : Client.getAttributes()){
                    if(a.getCode().equals(atCode)) a.setValue(atValue);
                }
            }
            
            // User Attribute Points
            if(nltype.equals("UAP")){
                int points=Integer.parseInt(nlmsg);
                Client.setPoints(points);
            }
            
            // User Attribute Points
            if(nltype.equals("USW")){
                int sword=Integer.parseInt(nlmsg);
                if(Client.getPersonagem().getClass().getCanonicalName().contains("Warrior")){
                    Warrior wpersonage = (Warrior) Client.getPersonagem();
                    if(sword == 0) wpersonage.setActualSword(Warrior.getSword1());
                    if(sword == 1) wpersonage.setActualSword(Warrior.getSword2());
                }
            }
            
            // New Fireball
            if(nltype.equals("FBN")){
                String[] vec = nlmsg.split(":");
                boolean nofireball=true;
                int id = Integer.parseInt(vec[0]);
                int direction = Integer.parseInt(vec[1]);
                String summoner = vec[2];       
                Fireball newF = new Fireball(id,Client.getPlayer(summoner));                    
                if(direction == 0) newF.setAnim(newF.getFireballUp());
                if(direction == 1) newF.setAnim(newF.getFireballDown());
                if(direction == 2) newF.setAnim(newF.getFireballLeft());
                if(direction == 3) newF.setAnim(newF.getFireballRight());
                Client.addFireball(newF);
            }
           
            // Destroy FB
            if(nltype.equals("FBD")){
                String[] vec = nlmsg.split(":");
                boolean nofireball=true;
                int id = Integer.parseInt(vec[0]); 
                Client.remFireball(id);
            }
            
            // Fireball Y
            if(nltype.equals("FBY")){
                String[] vec = nlmsg.split(":");
                boolean nofireball=true;
                int id = Integer.parseInt(vec[0]);
                float pos = Float.parseFloat(vec[1]);                
                for(Fireball f : Client.getFireballs()) {
                    if(f.getId() == id) {
                        if(f.getPosY() == 0){
                            f.setPosY(pos);
                            f.setPosY2(pos);
                        }
                        f.setPosY1(f.getPosY2());
                        f.setPosY2(pos);
                    }
                }
            }
            
            // Fireball Y
            if(nltype.equals("FBX")){
                String[] vec = nlmsg.split(":");
                boolean nofireball=true;
                int id = Integer.parseInt(vec[0]);
                float pos = Float.parseFloat(vec[1]);                
                for(Fireball f : Client.getFireballs()) {
                    if(f.getId() == id) {
                        if(f.getPosX() == 0){
                            f.setPosX(pos);
                            f.setPosX2(pos);
                        }
                        f.setPosX1(f.getPosX2());
                        f.setPosX2(pos);
                    }
                }
            }
            
            // New Player
            if(nltype.equals("PLN")){
                Character[] novo = new Character[Client.getPlayers().length+1];
                boolean noplayer=true;
                for(int i=0; i<Client.getPlayers().length;i++) if(Client.getPlayers()[i].getNome().equals(nlmsg.substring(1))) noplayer=false;
                if(noplayer){
                for(int i=0; i<Client.getPlayers().length;i++) novo[i] = Client.getPlayers()[i];
                    int classe = Integer.parseInt(nlmsg.substring(0,1));
                    System.out.println(classe);
                    if(classe == 1){                        
                        novo[Client.getPlayers().length] = new Ninja();
                    } else if(classe == 2){                        
                        novo[Client.getPlayers().length] = new Wizard();
                    } else if(classe == 3){                        
                        novo[Client.getPlayers().length] = new Warrior();
                    }
                    novo[Client.getPlayers().length].setNome(nlmsg.substring(1));
                    Client.setPlayers(novo);
                }
            }
            
            // Player Life
            if(nltype.equals("PLV")){
                String plName=nlmsg.substring(0, nlmsg.indexOf(":"));
                String plPos=nlmsg.substring(nlmsg.indexOf(":")+1);
                for(int i=0; i<Client.getPlayers().length;i++) if(Client.getPlayers()[i].getNome().equals(plName)) {
                    int newLife = Integer.parseInt(plPos);
                    if(newLife != Client.getPlayers()[i].getVida()){
                        Client.getPlayers()[i].setLifeChange(newLife-Client.getPlayers()[i].getVida());
                        Client.getPlayers()[i].setLifeTime(System.currentTimeMillis()+1500);
                    }
                    Client.getPlayers()[i].setVida(Integer.parseInt(plPos));
                }
            }
            
            // Player X
            if(nltype.equals("PLX")){
                String plName=nlmsg.substring(0, nlmsg.indexOf(":"));
                String plPos=nlmsg.substring(nlmsg.indexOf(":")+1);
                for(int i=0; i<Client.getPlayers().length;i++) if(Client.getPlayers()[i].getNome().equals(plName)) {
                    Client.getPlayers()[i].setPosX1(Client.getPlayers()[i].getPosX2());
                   Client.getPlayers()[i].setPosX2(Float.parseFloat(plPos));
                }
            }
            
            // Player Y
            if(nltype.equals("PLY")){
                String plName=nlmsg.substring(0, nlmsg.indexOf(":"));
                String plPos=nlmsg.substring(nlmsg.indexOf(":")+1);
                for(int i=0; i<Client.getPlayers().length;i++) if(Client.getPlayers()[i].getNome().equals(plName)){
                    Client.getPlayers()[i].setPosY1(Client.getPlayers()[i].getPosY2());
                    Client.getPlayers()[i].setPosY2(Float.parseFloat(plPos));
                }
            }
            
            // Player Attacking
            if(nltype.equals("PLA")){
                String plName=nlmsg.substring(0, nlmsg.indexOf(":"));
                String plPos=nlmsg.substring(nlmsg.indexOf(":")+1);
                for(int i=0; i<Client.getPlayers().length;i++) if(Client.getPlayers()[i].getNome().equals(plName)){
                    if(Client.getPlayers()[i].getClass().getCanonicalName().contains("Warrior")){
                        Warrior wpersonage = (Warrior) Client.getPlayers()[i];
                        wpersonage.setSwAtacking(Boolean.parseBoolean(plPos));
                    } else  
                    Client.getPlayers()[i].setAttacking(Boolean.parseBoolean(plPos));
                }
            }
            
            // Player Sprinting
            if(nltype.equals("PLS")){
                String plName=nlmsg.substring(0, nlmsg.indexOf(":"));
                String plPos=nlmsg.substring(nlmsg.indexOf(":")+1);
                for(int i=0; i<Client.getPlayers().length;i++) if(Client.getPlayers()[i].getNome().equals(plName)){
                    Client.getPlayers()[i].setSprinting(Boolean.parseBoolean(plPos));
                }
            }
            
            // Player Sword
            if(nltype.equals("PLE")){
                String plName=nlmsg.substring(0, nlmsg.indexOf(":"));
                String plPos=nlmsg.substring(nlmsg.indexOf(":")+1);
                for(int i=0; i<Client.getPlayers().length;i++) if(Client.getPlayers()[i].getNome().equals(plName)){
                    if(Client.getPlayers()[i].getClass().getCanonicalName().contains("Warrior")){
                        Warrior wpersonage = (Warrior) Client.getPlayers()[i];
                        int sword = Integer.parseInt(plPos);
                        if(sword == 0) wpersonage.setActualSword(Warrior.getSword1());
                        if(sword == 1) wpersonage.setActualSword(Warrior.getSword2());
                    }
                }
            }
            // Message
            if(nltype.equals("MSG")){
                String plName=nlmsg.substring(0, nlmsg.indexOf(":"));
                String message=nlmsg.substring(nlmsg.indexOf(":")+1).replaceAll("_", " ");
                if(plName.equalsIgnoreCase("server")){
                    Client.addMessage(plName.toUpperCase()+": "+message, Color.yellow);
                    
                }else
                Client.addMessage(plName+": "+message, Color.white);
                
            }
        }
    }

    @Override
    public void run() {
        Thread t = new Thread(new Update());
        t.start();
        Scanner s = null;
        try {
            s = new Scanner(Client.getCliente().getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (s.hasNextLine()) {
            String nl = s.nextLine();
            try {
                this.tratar(nl);
            } catch (SlickException | RuntimeException ex) { 
                //Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        utils.Message.error("Desconectado", "Você foi desconectado do servidor!");
        System.exit(0);
        /*
        while(true){
            try {               
                long time1= System.currentTimeMillis();
                //new PrintStream(cl.getCliente().getOutputStream()).println("ALL:\\"); 
                this.tratar(Client.nextMessage());
                long time2 = System.currentTimeMillis();
                int ping = (int) (time2-time1);
                this.pings.add(ping);
                if(this.pings.size() >= 10) {
                    int sum=0;
                    for(int a: this.pings) sum+=a;
                    Client.setPing(sum/this.pings.size());
                    this.pings.removeAll(pings);
                }
                Thread.sleep(100);
            } catch (IOException | NoAnswerException | SlickException ex ) {
                utils.Message.error("Erro", ex.getMessage());
                System.exit(0);
            } catch (InterruptedException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    }
    
}
