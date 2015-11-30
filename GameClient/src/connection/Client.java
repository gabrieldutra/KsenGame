/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection;

import entity.Attribute;
import entity.Character;
import entity.Fireball;
import entity.Ninja;
import entity.Warrior;
import entity.Wizard;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

/**
 *
 * @author gabriel
 */
public class Client {
        
    private static Character personagem;
    private static Character[] players;
    private static final ArrayList<Fireball> fireballs = new ArrayList<>();
    private static Socket cliente = null;
    private static String[] mensagens = new String[4];
    private static Color[] msgCor = new Color[4];
    private static int ping=0;
    private static long nelsonTime=0;
    private static int points=2;
    private static ArrayList<Attribute> attributes = new ArrayList<>();

    /**
     * @return the cliente
     */
    public static Socket getCliente() {
        return cliente;
    }
    
    public static void setMessage(int i, String msg,Color cor){
        if(i >= 0 && i < 4){
            Client.mensagens[i] = msg;
            Client.getMsgCor()[i] = cor;
        }
    }
    
    public static void addMessage(String msg, Color cor){
        String message = msg.replaceAll("_", " ");
        Client.setMessage(0, Client.getMensagens()[1], Client.getMsgCor()[1]);
        Client.setMessage(1, Client.getMensagens()[2], Client.getMsgCor()[2]);
        Client.setMessage(2, Client.getMensagens()[3], Client.getMsgCor()[3]);
        Client.setMessage(3,message, cor);
    }
    
    public static String getIp(){
        try{
            Scanner sc = new Scanner(new File("config.ini"));
            sc.next();
            return sc.next();
        } catch(FileNotFoundException e){
            System.out.println("Erro: "+e.getMessage());
        }
        return null;
          
    }
    
    public static int getPort(){
        try{
            Scanner sc = new Scanner(new File("config.ini"));
            sc.next();
            sc.next();
            sc.next();
            return Integer.parseInt(sc.next());
        } catch(FileNotFoundException e){
            System.out.println("Erro: "+e.getMessage());
        }
        return 0;
    }
    
    public static void conectar() throws IOException{
        cliente = new Socket(Client.getIp(), Client.getPort());
        System.out.println("O cliente se conectou ao servidor! "); 
    }
    
    public static void enterGame(String name,String password, int charId) throws IOException, InvalidNickException, NoAnswerException, SlickException{
        new PrintStream(getCliente().getOutputStream()).println("NEW:"+name+":"+password+"\\"+"CLS:"+charId+"\\");     
        Scanner s = new Scanner(getCliente().getInputStream());
        
        if(s.hasNext()){
            String next=s.next();   
            if(!next.equals("RES:true"))throw new InvalidNickException(next.substring(4).replaceAll("_", " "));
            else {
                Client.attributes.add(new Attribute("Level","2","level","res/icon/level-icon.gif",false));
                switch(charId){
                    case 1: 
                        Client.personagem = new Ninja();
                        Client.attributes.add(new Attribute("Ataque Físico","30","attack","res/icon/strength-icon.jpg",true));
                        break;
                    case 2:
                        Client.personagem = new Wizard(); 
                        Client.attributes.add(new Attribute("Ataque Mágico","40","attack","res/icon/intelligence-icon.jpg",true));
                        break;
                    case 3: 
                        Client.personagem = new Warrior();
                        Client.attributes.add(new Attribute("Ataque Físico","30","attack","res/icon/strength-icon.jpg",true));
                        break;
                }
                Client.attributes.add(new Attribute("Defesa","0","defense","res/icon/constitution-icon.jpg",true));
                Client.attributes.add(new Attribute("Precisão","40%","dexterity","res/icon/dexterity-icon.jpg",true));
                Client.attributes.add(new Attribute("Crítico","10%","critical","res/icon/perception-icon.jpg",true));
                Client.attributes.add(new Attribute("Velocidade","2.0","speed","res/icon/speed-icon.jpg",true));
            }
        } else throw new NoAnswerException("Sem resposta do servidor!");
    }
    
    public static synchronized void addFireball(Fireball f){
        boolean nofireball=true;
        for(Fireball ff : Client.getFireballs()) if(ff.getId() == f.getId()) nofireball=false;
        if(nofireball) Client.getFireballs().add(f);
    }
    
    public static synchronized List<Fireball> getFireballList(){
        List<Fireball> list = new ArrayList<>();
        for(Fireball ff : Client.getFireballs()) list.add(ff);
        return list;
    }
    
    public static synchronized void remFireball(int id){
        Iterator fbS = Client.getFireballs().iterator();
        while(fbS.hasNext()){
            Fireball fb = (Fireball) fbS.next();
            if(fb.getId() == id) fbS.remove();                   

        }
    }
    
    public static void goUp(int delta) throws IOException{
        new PrintStream(getCliente().getOutputStream()).println("GUP:"+delta+"\\"); 
    }
    
     public static void sendMessage(String msg) throws IOException{
        String msg2 = msg.replace('\\', '/');
        new PrintStream(getCliente().getOutputStream()).println("MSG:"+Client.personagem.getNome()+":"+msg2+"\\"); 
    }
     
    public static void sprint(int direction) throws IOException{
        new PrintStream(getCliente().getOutputStream()).println("SPT:"+direction+"\\"); 
    }
     
    public static void attack(boolean at) throws IOException{
        new PrintStream(getCliente().getOutputStream()).println("ATK:"+at+"\\"); 
    }
    
    public static void upAttribute(String attribute) throws IOException{
        new PrintStream(getCliente().getOutputStream()).println("UAA:"+attribute+"\\"); 
    }
    
    public static void fireball(int direcao) throws IOException{
        new PrintStream(getCliente().getOutputStream()).println("FBA:"+direcao+"\\"); 
    }
    
    public static void requestAll() throws IOException{
        new PrintStream(Client.getCliente().getOutputStream()).println("ALL:ALL\\");
    }
    
    public static void goDown(int delta) throws IOException{
        new PrintStream(getCliente().getOutputStream()).println("GDO:"+delta+"\\"); 
    }
    
    public static void goRight(int delta) throws IOException{
        new PrintStream(getCliente().getOutputStream()).println("GRI:"+delta+"\\"); 
    }
    
    public static void goLeft(int delta) throws IOException{
        new PrintStream(getCliente().getOutputStream()).println("GLE:"+delta+"\\"); 
    } 
    
    public static String nextMessage() throws NoAnswerException, IOException{
        Scanner s = new Scanner(getCliente().getInputStream());
        if(s.hasNext()){
            return s.next();
        } else throw new NoAnswerException("Sem resposta do servidor!");
    }
    
    public static void desconectar() throws IOException{
        if(getCliente() != null) getCliente().close();
    }

    /**
     * @return the personagem
     */
    public static Character getPersonagem() {
        return personagem;
    }

    /**
     * @param aPersonagem the personagem to set
     */
    public static void setPersonagem(Character aPersonagem) {
        personagem = aPersonagem;
    }

    /**
     * @return the players
     */
    public static Character[] getPlayers() {
        return players;
    }

    /**
     * @param aPlayers the players to set
     */
    public static void setPlayers(Character[] aPlayers) {
        players = aPlayers;
    }
    
    public static Character getPlayer(String nick){
        Character c = null;
        for(Character cc : Client.getPlayers()) if(cc.getNome().equals(nick)) return cc;
        return c;
    }
    
    
    
     public static void remPersonagem(String p){
        if(Client.getPlayers().length > 0){
            Character[] novo = new Character[Client.getPlayers().length-1];
            int count=0;
            for(int i=0;i<Client.getPlayers().length;i++){
                if(Client.getPlayers()[i].getNome().equals(p)) continue;
                if(count == Client.getPlayers().length-1) break;
                novo[count] = Client.getPlayers()[i];
                count++;
            }
            if(count < Client.getPlayers().length) Client.setPlayers(novo);
        }
    }

    /**
     * @return the mensagens
     */
    public static String[] getMensagens() {
        return mensagens;
    }

    /**
     * @return the msgCor
     */
    public static Color[] getMsgCor() {
        return msgCor;
    }

    /**
     * @param aMsgCor the msgCor to set
     */
    public static void setMsgCor(Color[] aMsgCor) {
        msgCor = aMsgCor;
    }

    /**
     * @return the ping
     */
    public static int getPing() {
        return ping;
    }

    /**
     * @param aPing the ping to set
     */
    public static void setPing(int aPing) {
        ping = aPing;
    }

    /**
     * @return the nelsonTime
     */
    public static long getNelsonTime() {
        return nelsonTime;
    }

    /**
     * @param aNelsonTime the nelsonTime to set
     */
    public static void setNelsonTime(long aNelsonTime) {
        nelsonTime = aNelsonTime;
    }

    /**
     * @return the fireballs
     */
    public static ArrayList<Fireball> getFireballs() {
        return fireballs;
    }

    /**
     * @return the attributes
     */
    public static ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * @param aAttributes the attributes to set
     */
    public static void setAttributes(ArrayList<Attribute> aAttributes) {
        attributes = aAttributes;
    }

    /**
     * @return the points
     */
    public static int getPoints() {
        return points;
    }

    /**
     * @param aPoints the points to set
     */
    public static void setPoints(int aPoints) {
        points = aPoints;
    }
 
}
