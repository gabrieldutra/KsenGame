package connection;


import db.CharacterDAO;
import db.UserDAO;
import entity.Character;
import entity.Fireball;
import entity.MapElement;
import entity.User;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriel
 */
public class Connection implements Runnable {
    Socket con;
    Character personagem;
    private User user;
    ArrayList<String> msgs = new ArrayList<String>();
    
    public Connection(Socket c){
        this.con=c;
        this.personagem = new Character();
        this.personagem.setConexao(this);
    }
    
    private void tratar(String nl){
        if(nl.contains("\\")){
            this.tratar(nl.substring(0,nl.indexOf("\\")));
            if(nl.substring(nl.indexOf("\\")).length() > 1) this.tratar(nl.substring(nl.indexOf("\\")+1));
        } else if(nl.length() > 3) {
            //System.out.println(nl);
            String nltype = nl.substring(0, 3);
            String nlmsg = "";
            if(nl.length() > 4) nlmsg = nl.substring(4);
            if(nltype.equals("NEW")){
                String nick=nlmsg.substring(0, nlmsg.indexOf(":"));
                String senha=nlmsg.substring(nlmsg.indexOf(":")+1);
                try {
                    new PrintStream(con.getOutputStream()).println("RES:"+Server.checkName(nick,senha,this).replaceAll(" ", "_"));
                    if(Server.checkName(nick,senha,this).equals("true")){
                        
                        personagem.setNome(nick);
                        personagem.spawn();
                        Server.addPersonagem(personagem);
                        Server.sendMessageToAll("USC:"+personagem.getNome()+"\\");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(con.getInetAddress()+": Tentativa de entrada com nick \""+nick+"\" ("+Server.checkName(nick,senha,this)+")");
            }
            
            if(nltype.equals("CLS") && this.personagem != null && this.user != null){
                CharacterDAO cdao = Server.cdao;
                Character c;
                if(this.personagem != null) {
                    personagem.setClasse(Integer.parseInt(nlmsg));
                    if(cdao.userClass(user, Integer.parseInt(nlmsg)) == null){
                        cdao.add(personagem,user);                        
                    }
                    c = cdao.userClass(user, Integer.parseInt(nlmsg));
                    personagem.setId(c.getId());
                    personagem.setLevel(c.getLevel());
                    personagem.setScore(c.getScore());
                    personagem.setPoints(c.getPoints());
                    personagem.setAtaque(c.getAtaque());
                    personagem.setDefesa(c.getDefesa());
                    personagem.setPrecisao(c.getPrecisao());
                    personagem.setCritico(c.getCritico());
                    personagem.setVelocidade(c.getVelocidade());
                }
                
                this.sendMessage("MSG:Server:Bem vindo ao Ksen Game!"+"\\");
                this.sendMessage("MSG:Server:Classe: "+this.personagem.getClassName()+"\\");
                this.sendMessage("MSG:Server:Atributos: "+this.personagem.getClassSpecial()+"\\");
                this.sendMessage("MSG:Server:"+this.personagem.getClassAttack()+"\\");
            }

            if(nltype.equals("ALL") && this.personagem != null){
                try {
                   String response="";
                   response = response.concat("USN:"+personagem.getNome()+"\\");
                   response = response.concat("USV:"+personagem.getVida()+"\\");
                   response = response.concat("USM:"+personagem.getMana()+"\\");
                   response = response.concat("USX:"+personagem.getPosX()+"\\");
                   response = response.concat("USY:"+personagem.getPosY()+"\\");
                   response = response.concat("USA:"+personagem.isAttacking()+"\\");
                   response = response.concat("USS:"+(personagem.getSprintcount()>0)+"\\");                    
                   response = response.concat("UAA:level:"+personagem.getLevel()+" ("+personagem.getScore()+"/"+(int)(5*Math.pow(2,personagem.getLevel()-1))+")\\");                  
                   response = response.concat("UAA:attack:"+personagem.getAtaque()+"\\");                   
                   response = response.concat("UAA:defense:"+personagem.getDefesa()+"\\");                   
                   response = response.concat("UAA:dexterity:"+personagem.getPrecisao()+"%\\");                   
                   response = response.concat("UAA:critical:"+personagem.getCritico()+"%\\");
                   String spd = String.format("%.2f", personagem.getVelocidade());
                   response = response.concat("UAA:speed:"+spd+"\\");                   
                   response = response.concat("UAP:"+personagem.getPoints()+"\\");
                   if(personagem.getClasse() == 3) response = response.concat("USW:"+personagem.getSword()+"\\");
                   for(Character p: Server.getPersonagens()){
                        if(!p.getNome().equals(personagem.getNome())){                            
                            response = response.concat("PLN:"+p.getClasse()+p.getNome()+"\\");
                            response = response.concat("PLV:"+p.getNome()+":"+p.getVida()+"\\");
                            response = response.concat("PLM:"+p.getNome()+":"+p.getMana()+"\\");
                            response = response.concat("PLX:"+p.getNome()+":"+p.getPosX()+"\\");
                            response = response.concat("PLY:"+p.getNome()+":"+p.getPosY()+"\\");
                            response = response.concat("PLA:"+p.getNome()+":"+p.isAttacking()+"\\");
                            response = response.concat("PLS:"+p.getNome()+":"+(p.getSprintcount()>0)+"\\");
                            if(p.getClasse() == 3) response = response.concat("PLE:"+p.getNome()+":"+p.getSword()+"\\");
                        }
                   }
                   for(Fireball f : Server.getFireballList()){                       
                       response = response.concat("FBN:"+f.getId()+":"+f.getDirection()+":"+f.getSummoner()+"\\");
                       response = response.concat("FBX:"+f.getId()+":"+f.getPosX()+"\\");
                       response = response.concat("FBY:"+f.getId()+":"+f.getPosY()+"\\");
                   }
                   for(String str: msgs){
                       response = response.concat(str);
                   }
                   msgs.removeAll(msgs);
                   new PrintStream(con.getOutputStream()).println(response);
                
                
                } catch (IOException ex) {
                    Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                }
               //System.out.println(con.getInetAddress()+": Retornando Pos X: \""+personagem.getPosX()+"\"");
            }
            
            float atX,atY;
            atX=0;
            atY =0;
            if(personagem != null){
                atX = personagem.getPosX();
                atY = personagem.getPosY();
            }
            if(nltype.equals("UAA") && this.personagem != null){
                this.personagem.upAttribute(nlmsg);
            }
            if(nltype.equals("ATK") && this.personagem != null){
                boolean atk = Boolean.parseBoolean(nlmsg);
                if(personagem.getClasse() == 1 || personagem.getClasse() == 3) personagem.setAttacking(atk);                
            }
            if(nltype.equals("FBA") && this.personagem != null){
                if(personagem.getClasse() == 2){
                    if(personagem.getMana() >= 20 && personagem.getFirecooldown() <= 0){
                        personagem.setFirecooldown(6);
                        personagem.setMana(personagem.getMana()-20);
                        int direcao = Integer.parseInt(nlmsg);
                        // Up
                        if(direcao == 0){
                            Fireball fb = new Fireball(this.personagem,this.personagem.getPosX(),this.personagem.getPosY()+32,direcao);
                            new Thread(fb).start();
                        }
                        // Down
                        if(direcao == 1){
                            Fireball fb = new Fireball(this.personagem,this.personagem.getPosX(),this.personagem.getPosY()-32,direcao);
                            new Thread(fb).start();
                        }
                        // Left
                        if(direcao == 2){
                            Fireball fb = new Fireball(this.personagem,this.personagem.getPosX()-32,this.personagem.getPosY(),direcao);
                            new Thread(fb).start();
                        }
                        // Right
                        if(direcao == 3){
                            Fireball fb = new Fireball(this.personagem,this.personagem.getPosX()+32,this.personagem.getPosY(),direcao);
                            new Thread(fb).start();
                        }
                    } 
                    
                }
            }
            if(nltype.equals("SPT")&& this.personagem != null){
                if(personagem.getClasse() == 1){
                    if(personagem.getMana() >= 30 && personagem.getSprintcount() >=0 ){
                        personagem.setMana(personagem.getMana()-30);
                        if(personagem.getSprintcount() == 0) personagem.setVelocidade(personagem.getVelocidade()+4);
                        personagem.setSprintcount(personagem.getSprintcount()+5);
                    }
                }
                if(personagem.getClasse() == 2){                    
                    int direcao = Integer.parseInt(nlmsg);
                    
                    if(personagem.getMana() >= 25 && personagem.getSprintcount() >=0 ){
                        personagem.setMana(personagem.getMana()-25);
                        Server.soundToWhoCanSee(personagem, "res/sound/teleport.ogg");
                        int dist=74;
                        // Up
                        if(direcao == 0){
                            personagem.setPosY(personagem.getPosY()+dist);
                        }

                        // Down
                        if(direcao == 1){
                            personagem.setPosY(personagem.getPosY()-dist);
                        }

                        // Left
                        if(direcao == 2){
                            personagem.setPosX(personagem.getPosX()-dist);
                        }

                        //Right
                        if(direcao == 3){
                            personagem.setPosX(personagem.getPosX()+dist);
                        }
                    }
                }
                if(personagem.getClasse() == 3 && personagem.getFirecooldown() == 0){
                    if(personagem.getSword() == 0) personagem.setSword(1);
                    else personagem.setSword(0);
                    personagem.setFirecooldown(15);
                }
            }            
            float speed=personagem.getVelocidade();
            
            if(personagem.getClasse() == 3 && personagem.isAttacking() && personagem.getSword() == 0) {
                speed+=(float)1.5;
            }
            if(personagem.getClasse() == 1 || personagem.getClasse() == 3) {
                float temp=(float) (speed/2.0);
                speed=(float) (temp+(temp*(personagem.getMana()/100.0)));
            }
            if(personagem.getSlowedTime() > 0){
                speed/=2;
            }
            if(nltype.equals("GUP") && this.personagem != null){
                int delta = Integer.parseInt(nlmsg);
                if(personagem.getPosY() < 230 /*&& !personagem.isAttacking()*/) personagem.setPosY(personagem.getPosY()+(delta*.1f*speed));
                personagem.setDirection(1);                
            }

            if(nltype.equals("GDO") && this.personagem != null){
                int delta = Integer.parseInt(nlmsg);
                if(personagem.getPosY() > -1280+230+32 /*&& !personagem.isAttacking()*/) personagem.setPosY(personagem.getPosY()-(delta*.1f*speed));
                personagem.setDirection(0);
            }

            if(nltype.equals("GRI") && this.personagem != null){
                int delta = Integer.parseInt(nlmsg);
                if(personagem.getPosX() < 1280-320-32 /*&& !personagem.isAttacking()*/) personagem.setPosX(personagem.getPosX()+(delta*.1f*speed));
                personagem.setDirection(3);
            }

            if(nltype.equals("GLE") && this.personagem != null){
                int delta = Integer.parseInt(nlmsg);
               if(personagem.getPosX() > -320 /*&& !personagem.isAttacking()*/) personagem.setPosX(personagem.getPosX()-(delta*.1f*speed));
               personagem.setDirection(2);
            }
            
            if(nltype.equals("MSG")){
                String plName=nlmsg.substring(0, nlmsg.indexOf(":"));
                String message=nlmsg.substring(nlmsg.indexOf(":")+1);
                System.out.println("(MSG) "+plName+": "+message.replaceAll("_", " "));
                if(message.charAt(0) == '!' && user.getAdmin() >=1){
                    
                    if(message.length() < 2) this.sendMessage("MSG:Server:Mensagem inválida!"+"\\");
                    else Server.sendMessageToAll("MSG:Server:"+message.substring(1)+"\\");
                }else 
                if(message.charAt(0) == '/'){
                    if(message.equals("/quit") || message.equals("/q")){
                        this.finish();
                    }else
                    if(message.equals("/rank") || message.equals("/ranking")){
                        this.sendMessage("MSG:Server:Ranking de players conectados:"+"\\");
                        ArrayList<Character> rank = new ArrayList<>();
                        for(Character c : Server.getPersonagens()) rank.add(c);
                        Collections.sort(rank);
                        Collections.reverse(rank);
                        int count=0;
                        for(Character c : rank){
                            count++;
                            this.sendMessage("MSG:Server:"+count+" - "+c.getNome()+" - "+c.getLevel()+"\\");
                            if(count == 3) break;                            
                        }
                    }else
                    if(message.startsWith("/msg") && user.getAdmin() >=1){
                        if(message.length() < 6) this.sendMessage("MSG:Server:Mensagem inválida!"+"\\");
                        else Server.sendMessageToAll("MSG:Server:"+message.substring(5));
                    } else if(message.startsWith("/myspeed") && user.getAdmin() >=1){
                        if(message.length() < 10) this.sendMessage("MSG:Server:Mensagem inválida!"+"\\");
                        else personagem.setVelocidade(Float.parseFloat(message.substring(9)));
                    }else if(message.startsWith("/setadmin") && user.getAdmin() >= 2){
                        UserDAO udao = Server.udao;
                        if(message.length() < 11){
                            this.sendMessage("MSG:Server:Nick não especificado!"+"\\");
                        } else {
                            if(udao.getUser(message.substring(10)) == null){
                                this.sendMessage("MSG:Server:Nick inválido!"+"\\");
                            }else{
                                this.sendMessage("MSG:Server:Colocando admin: "+message.substring(10)+"\\");
                                
                                User u = udao.getUser(message.substring(10));
                                u.setAdmin(1);
                                udao.update(u);
                                try {
                                    Server.getPersonagem(message.substring(10)).getConexao().finish();
                                } catch (Throwable ex) {
                                    Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            
                        }
                    } else if(message.startsWith("/remadmin") && user.getAdmin() >= 2){
                        UserDAO udao = Server.udao;
                        if(message.length() < 11){
                            this.sendMessage("MSG:Server:Nick não especificado!"+"\\");
                        } else {
                            if(udao.getUser(message.substring(10)) == null){
                                this.sendMessage("MSG:Server:Nick inválido!"+"\\");
                            }else{
                                this.sendMessage("MSG:Server:Removendo admin: "+message.substring(10)+"\\");
                                User u = udao.getUser(message.substring(10));
                                u.setAdmin(0);
                                udao.update(u);
                                try {
                                    Server.getPersonagem(message.substring(10)).getConexao().finish();
                                } catch (Throwable ex) {
                                    Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            
                        }
                    } else if(message.startsWith("/kick") && user.getAdmin() >= 1){
                        if(message.length() < 7){
                            this.sendMessage("MSG:Server:Nick não especificado!"+"\\");
                        } else {
                            if(Server.getPersonagem(message.substring(6)) == null){
                                this.sendMessage("MSG:Server:Nick inválido!"+"\\");
                            }else{
                                Server.sendMessageToAll("MSG:Server:Kicking: "+message.substring(6)+"\\");
                                try {
                                    Server.getPersonagem(message.substring(6)).getConexao().finish();
                                } catch (Throwable ex) {
                                    Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            
                        }
                    } else                         this.sendMessage("MSG:Server:Comando inválido!");
                }else {
                    Server.sendMessageToAll(nl);
                }
            }
            
            if(personagem.getPosY() > 230 || personagem.getPosY() < -1280+230+32 || personagem.getPosX() > 1280-320-32 || personagem.getPosX() < -320){                
                personagem.setPosX(atX);
                personagem.setPosY(atY);
            }
            //Código de uso para ataque
            
            for(MapElement m : Server.getElements()){
                if(personagem.checaColisao(m)){
                    personagem.setPosX(atX);
                    personagem.setPosY(atY);
                }
            }
            
            for(Character p : Server.getPersonagens()){
                if(p == personagem) continue;
                if(personagem.checaColisao(p)){
                    if(personagem.getSprintcount() > 1 && personagem.getClasse() == 1 && p.getVida() > 0){
                        p.setVida(p.getVida()-personagem.calculateDamage(p));                   
                        personagem.setSprintcount(-10);     
                        personagem.setVelocidade(personagem.getVelocidade()-4);
                        Server.soundToWhoCanSee(personagem, "res/sound/knifeslice.ogg");
//                      personagem.getConexao().sendMessage("USP:res/sound/knifeslice.ogg\\");
//                      p.getConexao().sendMessage("USP:res/sound/knifeslice.ogg\\");
                        p.checkKill(personagem);
                    }
                    personagem.setPosX(atX);
                    personagem.setPosY(atY);
                }
            }
            
        }
    }
    
    public void sendMessage(String message){
        //new PrintStream(con.getOutputStream()).println(message.replaceAll(" ", "_"));  
        msgs.add(message.replaceAll(" ", "_"));
        System.out.println("Sending message: "+message.replaceAll(" ", "_"));
    }
    
    public void finish(){
        try {
            con.close();
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Conexão encerrada com o cliente " +this.con.getInetAddress().getHostAddress());    
        if(personagem != null){
            Server.remPersonagem(personagem);
            if(personagem.getNome() != null) Server.sendMessageToAll("USD:"+personagem.getNome()+"\\");
        
        }        
    }

    @Override
    public void run() {
        System.out.println("Nova conexão com o cliente " +this.con.getInetAddress().getHostAddress());    
        Scanner s = null;
        try {
            s = new Scanner(con.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (s.hasNextLine()) {
            String nl = s.nextLine();
            this.tratar(nl);
        }
        
        this.finish();
        
        s.close();
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
            
}
