package javagame;

import connection.Client;
import connection.Connection;
import entity.AttributesMenu;
import entity.Character;
import entity.Fireball;
import entity.Map;
import entity.Warrior;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.*;

/**
 *
 * @author gabriel
 */
class Play extends BasicGameState{
    
    Character personagem;
    Character[] players = new Character[0];
    Connection connection;
    Map worldMap;    
    TextField chat;
    int shiftX=320;
    int shiftY=230;
    ArrayList<Sound> playingSounds = new ArrayList<>();
    Image chatBack;
    Image nelsonImg;
    AttributesMenu atmen;
    boolean minimap=true;
    boolean attributes=false;
    Music music;
    Sound createwindow;

    public Play(int state) {
        
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        worldMap = new Map();
        createwindow = new Sound("res/sound/createwindow.ogg");
        chatBack = new Image("res/black.png");
        nelsonImg = new Image("res/nelson.png");
        chat = new TextField(gc, gc.getDefaultFont(), 10, 450, 620 , 20);
        chat.setMaxLength(55);
        Client.setMessage(0, "",Color.white);
        Client.setMessage(1, "",Color.white);
        Client.setMessage(2, "",Color.white);
        Client.setMessage(3, "",Color.white);
        atmen = new AttributesMenu(140,50,300,240);
        music = new Music("res/music/game.ogg");
        music.setVolume((float)0.3);
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        if(!music.playing()) {
            music.loop();
            music.setVolume((float) 0.5);
        }
        if(connection == null) {
            connection = new Connection(personagem,players);
            // cria a thread em cima deste objeto
             Thread t = new Thread(connection);
            // inicia a thread
            t.start();
        }
        players = Client.getPlayers();
        
        float difx=Client.getPersonagem().getPosX2()-Client.getPersonagem().getPosX();
        float dify=Client.getPersonagem().getPosY2()-Client.getPersonagem().getPosY();
        if(gc.getFPS() >= 12){            
            Client.getPersonagem().setPosX(Client.getPersonagem().getPosX()+(difx/(gc.getFPS()/12)));
            Client.getPersonagem().setPosY(Client.getPersonagem().getPosY()+(dify/(gc.getFPS()/12)));
        } else {
            Client.getPersonagem().setPosX(Client.getPersonagem().getPosX2());
            Client.getPersonagem().setPosY(Client.getPersonagem().getPosY2());
        }
        
        float pX = Client.getPersonagem().getPosX();
        float pY = Client.getPersonagem().getPosY();
        float fsX = shiftX;
        float fsY = shiftY;
        if(pX<=0) {
            fsX=(shiftX+pX);
            pX=0;
        }

        if(pY>=0) {
            fsY=(shiftY-pY);
            pY=0;
        }
        
        if(pX>=worldMap.getWidth()-gc.getWidth()){
            fsX=shiftX+(pX-(worldMap.getWidth()-gc.getWidth()));
            pX=worldMap.getWidth()-gc.getWidth();
        }
        
        if(pY<=-(worldMap.getHeight()-gc.getHeight())){
            fsY=shiftY-(pY+(worldMap.getHeight()-gc.getHeight()));
            pY=-(worldMap.getHeight()-gc.getHeight());
        }
        int ppX = (int) -pX;
        int ppY = (int) pY;
        worldMap.getMap().render(ppX, ppY,worldMap.getLayerId("Grass"));
        worldMap.getMap().render(ppX, ppY,worldMap.getLayerId("Streets"));
        //worldMap.getMap().render(ppX, ppY,worldMap.getLayerId("Sand"));
        //worldMap.getMap().render(ppX, ppY,worldMap.getLayerId("Water"));
        //worldMap.getMap().render(ppX, ppY,worldMap.getLayerId("Grass2"));
        //worldMap.getMap().render(ppX, ppY,worldMap.getLayerId("City"));
        
        
        //g.drawImage(worldMap, -pX, pY);
        if(Client.getPersonagem().getClass().getCanonicalName().contains("Warrior")){
            Warrior wpersonage = (Warrior) Client.getPersonagem();
            if(wpersonage.isSwAtacking()){
                if(!wpersonage.getSwordSound().playing()) {
                    wpersonage.getSwordSound().stop();
                    wpersonage.getSwordSound().loop();
                } 
            } else wpersonage.getSwordSound().stop();
            if(Client.getPersonagem().getAnim() == Client.getPersonagem().getWalkingUp()){            
                int ang = (int) (System.currentTimeMillis()%(90*3))/3;
                if(!wpersonage.isSwAtacking()) ang=90;     
                 g.rotate(fsX+20-16+24, fsY+24+4, ang);
                 wpersonage.getActualSword().draw(fsX+20-16, fsY+4);
                 g.rotate(fsX+20-16+24, fsY+24+4, -ang);
             }
             if(Client.getPersonagem().getAnim() == Client.getPersonagem().getWalkingDown()){            
                 
                int ang = -90- (int) (System.currentTimeMillis()%(90*3))/3;                 
                if(!wpersonage.isSwAtacking()) ang=-90;  
                 g.rotate(fsX-16+24, fsY+24-4, ang);
                 wpersonage.getActualSword().draw(fsX-16, fsY-4);
                 g.rotate(fsX-16+24, fsY+24-4, -ang);
             }
             if(Client.getPersonagem().getAnim() == Client.getPersonagem().getWalkingLeft()){            
                int ang = - (int) (System.currentTimeMillis()%(90*3))/3;                 
                if(!wpersonage.isSwAtacking()) ang=0;  
                g.rotate(fsX-12+24, fsY+24, ang);
                wpersonage.getActualSword().draw(fsX-12, fsY);
                g.rotate(fsX-12+24, fsY+24, -ang);
             }
             if(Client.getPersonagem().getAnim() == Client.getPersonagem().getWalkingRight()){            
                int ang =90+ (int) (System.currentTimeMillis()%(90*3))/3;
                if(!wpersonage.isSwAtacking()) ang=90;     
                g.rotate(fsX-2+24, fsY+24+4, ang);
                wpersonage.getActualSword().draw(fsX-2, fsY+4);
                g.rotate(fsX-2+24, fsY+24+4, -ang);
             }
        }

        Client.getPersonagem().getAnim().draw(fsX,fsY);
        ArrayList<Sound> keepSound = new ArrayList<>();
        for(Character pl: players){  
            float difxp=pl.getPosX2()-pl.getPosX();
            float difyp=pl.getPosY2()-pl.getPosY();
            
            if(gc.getFPS() >= 12){
                pl.setPosX(pl.getPosX()+(difxp/(gc.getFPS()/12)));
                pl.setPosY(pl.getPosY()+(difyp/(gc.getFPS()/12)));
            } else {
                pl.setPosX(pl.getPosX2());
                pl.setPosY(pl.getPosY2());
            }
            
            difxp = pl.getPosX2()-pl.getPosX1();
            difyp = pl.getPosY2()-pl.getPosY1();
            if(difxp == 0){
                pl.getWalkingRight().stop();
                pl.getWalkingLeft().stop();
            }
            if(difyp == 0){
                pl.getWalkingUp().stop();
                pl.getWalkingDown().stop();
            }
            if(difxp > 0) {
                pl.setAnim(pl.getWalkingRight());
                pl.getWalkingRight().start();
            } else if(difxp < 0) {
                pl.setAnim(pl.getWalkingLeft());
                pl.getWalkingLeft().start();
            } else if(difyp > 0) {
                pl.setAnim(pl.getWalkingUp());
                pl.getWalkingUp().start();
            } else if(difyp < 0) {
                pl.setAnim(pl.getWalkingDown());
                pl.getWalkingDown().start();
            }
            float plX = pl.getPosX();  
            float plY = pl.getPosY();
            boolean visible=plX > (pX-shiftX-32) && plX < (pX-shiftX)+gc.getWidth() && plY > (pY-shiftX) && plY < (pY-shiftY)+gc.getHeight();
            float eX,eY, tX,tY,eW,eH,tW,tH;
            eX = Client.getPersonagem().getPosX();
            eY = Client.getPersonagem().getPosY();
            eW = gc.getWidth()/4;
            eH = gc.getHeight()/4; 
            tX = pl.getPosX();
            tY = pl.getPosY();
            tW = gc.getWidth()/4;
            tH = gc.getHeight()/4;
            boolean visninja=(plX > eX-eW && tX < eW+eX) && (tY > eY-eH && tY < eH+eY);
            //if(pl.getClass().getCanonicalName().contains("Ninja")) visible=visible&&visninja;
            if(visible){
                float dX=plX+shiftX-pX;
                float dY=shiftY+pY-plY;  
                g.setColor(Color.green);
                if(pl.getVida() <= 50) g.setColor(Color.yellow);
                if(pl.getVida() <= 30) g.setColor(Color.red);
                //g.setLineWidth(5);
                g.fillRect(dX-10, dY-12, pl.getVida()/2, 10);
                //g.drawRect(dX-10, dY-10, pl.getVida()/2, 5); // x, y. width, height
                if(System.currentTimeMillis() < pl.getLifeTime()){
                    String st="";
                    if(pl.getLifeChange() < 0) g.setColor(Color.red);
                    else {
                        st="+";
                        g.setColor(Color.green);
                    }
                    
                    g.drawString(st+pl.getLifeChange(), dX, dY-40);
                }
                g.setColor(Color.white);
                g.drawString(pl.getNome(), dX+3-(pl.getNome().length()*2), dY-30);
                if(pl.isAttacking()) {
                    pl.attack(g, dX, dY);
                    if(!playingSounds.contains(pl.getAttackSound())) {
                       playingSounds.add(pl.getAttackSound());  
                    }
                    if(!pl.getAttackSound().playing()){
                        pl.getAttackSound().stop();
                        pl.getAttackSound().loop();
                    }
                    keepSound.add(pl.getAttackSound());
                }
                if(pl.isSprinting()) {
                    pl.sprint(g, dX, dY);
                    if(!playingSounds.contains(pl.getRunSound())) {
                       playingSounds.add(pl.getRunSound());  
                    }
                    if(!pl.getRunSound().playing()){
                        pl.getRunSound().stop();
                        pl.getRunSound().loop();
                    }
                    keepSound.add(pl.getRunSound());
                }
                if(pl.getClass().getCanonicalName().contains("Warrior")){
                    Warrior wpersonage = (Warrior) pl;
                    if(wpersonage.isSwAtacking()){
                        if(!playingSounds.contains(wpersonage.getSwordSound())) {
                            playingSounds.add(wpersonage.getSwordSound());  
                         }
                         if(!wpersonage.getSwordSound().playing()){
                            wpersonage.getSwordSound().stop();
                            wpersonage.getSwordSound().loop();
                        }
                    }
                    if(pl.getAnim() == pl.getWalkingUp()){            
                        int ang = (int) (System.currentTimeMillis()%(90*3))/3;
                        if(!wpersonage.isSwAtacking()) ang=90;     
                         g.rotate(dX+20-16+24, dY+24+4, ang);
                         wpersonage.getActualSword().draw(dX+20-16, dY+4);
                         g.rotate(dX+20-16+24, dY+24+4, -ang);
                     }
                     if(pl.getAnim() == pl.getWalkingDown()){          

                        int ang = -90- (int) (System.currentTimeMillis()%(90*3))/3;                 
                        if(!wpersonage.isSwAtacking()) ang=-90;  
                         g.rotate(dX-16+24, dY+24-4, ang);
                         wpersonage.getActualSword().draw(dX-16, dY-4);
                         g.rotate(dX-16+24, dY+24-4, -ang);
                     }
                     if(pl.getAnim() == pl.getWalkingLeft()){            
                        int ang = - (int) (System.currentTimeMillis()%(90*3))/3;                 
                        if(!wpersonage.isSwAtacking()) ang=0;  
                        g.rotate(dX-12+24, dY+24, ang);
                        wpersonage.getActualSword().draw(dX-12, dY);
                        g.rotate(dX-12+24, dY+24, -ang);
                     }
                     if(pl.getAnim() == pl.getWalkingRight()){            
                        int ang =90+ (int) (System.currentTimeMillis()%(90*3))/3;
                        if(!wpersonage.isSwAtacking()) ang=90;     
                        g.rotate(dX-2+24, dY+24+4, ang);
                        wpersonage.getActualSword().draw(dX-2, dY+4);
                        g.rotate(dX-2+24, dY+24+4, -ang);
                     }
                }
                pl.getAnim().draw(dX,dY);
            }
        }
        
        
        
        for(Fireball pl : Client.getFireballList()){
            float fpsdiv = (gc.getFPS()/12);    
            if(gc.getFPS() >= 12){
                
                float difxp=pl.getPosX2()-pl.getPosX();      
                pl.setPosX(pl.getPosX()+(difxp/fpsdiv));
                float difyp=pl.getPosY2()-pl.getPosY();
                pl.setPosY(pl.getPosY()+(difyp/fpsdiv));
            } else {                  
                pl.setPosX(pl.getPosX2());
                pl.setPosY(pl.getPosY2());
            }
            /*
            if(Float.isNaN(pl.getPosX())){
                pl.setPosX(pl.getPosX2());
            }
            
            if(Float.isNaN(pl.getPosY())){
                pl.setPosY(pl.getPosY2());
            }*/
            
            float plX = pl.getPosX();  
            float plY = pl.getPosY();
            boolean visible=plX > (pX-shiftX-32) && plX < (pX-shiftX)+gc.getWidth() && plY > (pY-shiftX) && plY < (pY-shiftY)+gc.getHeight();
            float eX,eY, tX,tY,eW,eH,tW,tH;
            eX = Client.getPersonagem().getPosX();
            eY = Client.getPersonagem().getPosY();
            eW = gc.getWidth()/4;
            eH = gc.getHeight()/4; 
            tX = pl.getPosX();
            tY = pl.getPosY();
            tW = gc.getWidth()/4;
            tH = gc.getHeight()/4;
            boolean visninja=(plX > eX-eW && tX < eW+eX) && (tY > eY-eH && tY < eH+eY);
            //if(pl.getClass().getCanonicalName().contains("Ninja")) visible=visible&&visninja;
            if(visible){
                float dX=plX+shiftX-pX;
                float dY=shiftY+pY-plY;  
                pl.getAnim().draw(dX,dY);
                if(!playingSounds.contains(pl.getFireballSound())) {
                       playingSounds.add(pl.getFireballSound());  
                }
                if(!pl.getFireballSound().playing()){
                        pl.getFireballSound().stop();
                        pl.getFireballSound().loop();
                    }
                    keepSound.add(pl.getFireballSound());
            }
            
            
        }
        
        
        Iterator plS = playingSounds.iterator();
        while(plS.hasNext()){
            Sound s = (Sound) plS.next();
            
            if(!keepSound.contains(s)) {
                plS.remove();
                if(s.playing()){
                    s.stop();
                }
            }
            
        }
        
        worldMap.getMap().render(ppX, ppY,worldMap.getLayerId("Objects"));
        
        
        //HUD
        g.drawString("HP", 10, 30);
        g.setColor(Color.black);
        g.fillRect(10-1, 50-1, 100+2, 15+2);
        g.fillRect(10-1, 90-1, 100+2, 15+2);
        g.setColor(Color.green);
        if(Client.getPersonagem().getVida() <= 50) g.setColor(Color.yellow);
        if(Client.getPersonagem().getVida() <= 30) g.setColor(Color.red);
        g.fillRect(10, 50, Client.getPersonagem().getVida(), 15);
        if(Client.getPersonagem().getClass().getCanonicalName().contains("Ninja") || Client.getPersonagem().getClass().getCanonicalName().contains("Warrior")){
            g.setColor(Color.white);
            g.drawString("SP", 10, 70);
            int val1=102-( (Client.getPersonagem().getMana())*1);
            int val2=(int) (52-( (Client.getPersonagem().getMana())*0.5 ));
            g.setColor(new Color(255-val1,128-val2,0));
            //if(Client.getPersonagem().getMana() <= 50) g.setColor(new Color(204,102,0));
            //if(Client.getPersonagem().getMana() <= 30) g.setColor(new Color(153,76,0));
        } else {   
            g.setColor(Color.white);
            g.drawString("MP", 10, 70);
            g.setColor(new Color(100,100,255));
        }
        g.fillRect(10, 90, Client.getPersonagem().getMana(), 15);
        g.setColor(Color.white);
        g.drawString(Client.getPersonagem().getNome(), 10, 110);
        g.setColor(Color.green);
        if(Client.getPersonagem().getVida() <= 50) g.setColor(Color.yellow);
        if(Client.getPersonagem().getVida() <= 30) g.setColor(Color.red);
        //g.setLineWidth(5);
        g.fillRect(fsX-10, fsY-12, Client.getPersonagem().getVida()/2, 10);
        //g.drawRect(fsX-10, fsY-10, Client.getPersonagem().getVida()/2, 5); // x, y. width, height

        if(System.currentTimeMillis() < Client.getPersonagem().getLifeTime()){
            String st="";
            if(Client.getPersonagem().getLifeChange() < 0) g.setColor(Color.red);
            else {
                st="+";
                g.setColor(Color.green);
            }
            g.drawString(st+Client.getPersonagem().getLifeChange(), fsX, fsY-40);
        }
        g.setColor(Color.white);
        g.drawString(Client.getPersonagem().getNome(), fsX+3-(Client.getPersonagem().getNome().length()*2), fsY-30);
        
        Input input = gc.getInput();
        if(Client.getPersonagem().isAttacking()) {  
            Client.getPersonagem().attack(g, fsX, fsY);
            if(!Client.getPersonagem().getAttackSound().playing()){                
                Client.getPersonagem().getAttackSound().stop();
                Client.getPersonagem().getAttackSound().loop();
            }
        } else {
            if(Client.getPersonagem().getAttackSound().playing()) Client.getPersonagem().getAttackSound().stop();
        }
        
        if(Client.getPersonagem().isSprinting()){
            Client.getPersonagem().sprint(g,fsX,fsY);
            if(!Client.getPersonagem().getRunSound().playing()){                
                Client.getPersonagem().getRunSound().stop();
                Client.getPersonagem().getRunSound().loop();
            }
        } else {
            if(Client.getPersonagem().getRunSound().playing()) Client.getPersonagem().getRunSound().stop();
        }
        //Chat  
        g.setColor(Color.black);
        g.setLineWidth(1);
        if(chat.hasFocus()) chatBack.setAlpha((float) 0.9);
        else chatBack.setAlpha((float) 0.4);
        g.drawImage(chatBack, 10, 385);
        
        
        
        g.setColor(Client.getMsgCor()[0]);        
        g.drawString(Client.getMensagens()[0], 12, 385);
        g.setColor(Client.getMsgCor()[1]); 
        g.drawString(Client.getMensagens()[1], 12, 400);
        g.setColor(Client.getMsgCor()[2]); 
        g.drawString(Client.getMensagens()[2], 12, 415);
        g.setColor(Client.getMsgCor()[3]); 
        g.drawString(Client.getMensagens()[3], 12, 430);
        g.setColor(Color.white);
        this.chat.render(gc, g);
        //g.drawRect(10, 400, 670, 40); // x, y. width, height
        if(Client.getNelsonTime() > System.currentTimeMillis()){
            nelsonImg.draw(0, 480-(nelsonImg.getHeight()/2),(float)0.5);
        } 
        
        if(attributes){
            atmen.draw(g);
        }
            
        if(minimap){          
            g.scale((float) 0.06, (float)0.06);
            float startX=600*15;
            float startY = 10*15;
            g.setColor(Color.black);
            g.fillRect(startX-(1*15), startY-(1*15), worldMap.getWidth()+(2*15), worldMap.getHeight()+(2*15));
            worldMap.getMap().render((int) startX,(int)startY);
            float npX=Client.getPersonagem().getPosX()+shiftX;
            float npY=(-1*Client.getPersonagem().getPosY())+shiftY;
            g.setColor(Color.red);
            for(Character p : Client.getPlayers()) g.fillRect(startX+(p.getPosX()+shiftX-16), startY+(-1*p.getPosY()+shiftY-16), 64, 64);
            g.setColor(Color.green);
            g.fillRect(startX+npX-16, startY+npY-16, 64, 64);
            g.scale(1,1);
            g.setColor(Color.white);  
        }
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException { 
        Input input = gc.getInput();
        if(input.isKeyDown(Input.KEY_T))                    
     
        chat.setFocus(true);
        if(input.isKeyDown(Input.KEY_RETURN)){
            if(chat.hasFocus()){
                chat.setFocus(false);
                try {
                    if(chat.getText().length() > 0) Client.sendMessage(chat.getText());
                } catch (IOException ex) {
                    Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                }
                chat.setText("");
            }
        }
        if(!chat.hasFocus()){
            if(input.isKeyDown(Input.KEY_UP)){
                Client.getPersonagem().setAnim(Client.getPersonagem().getWalkingUp());
                Client.getPersonagem().getWalkingUp().start();
                try {
                    Client.goUp(delta);
                } catch (IOException ex) {
                    Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                }
                //if(Client.getPersonagem().getPosY() < 230) Client.getPersonagem().setPosY(Client.getPersonagem().getPosY()+(delta*.1f*Client.getPersonagem().getVelocidade()));
            }

            if(input.isKeyDown(Input.KEY_DOWN)){
                Client.getPersonagem().setAnim(Client.getPersonagem().getWalkingDown());
                Client.getPersonagem().getWalkingDown().start();
                try {
                    Client.goDown(delta);
                } catch (IOException ex) {
                    Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                }
                //if(Client.getPersonagem().getPosY() > -799) Client.getPersonagem().setPosY(Client.getPersonagem().getPosY()-(delta*.1f*Client.getPersonagem().getVelocidade()));
            }

            if(input.isKeyDown(Input.KEY_LEFT)){
                Client.getPersonagem().setAnim(Client.getPersonagem().getWalkingLeft());
                Client.getPersonagem().getWalkingLeft().start();
                try {
                    Client.goLeft(delta);
                } catch (IOException ex) {
                    Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                }
                //if(Client.getPersonagem().getPosX() > -320) Client.getPersonagem().setPosX(Client.getPersonagem().getPosX()-(delta*.1f*Client.getPersonagem().getVelocidade()));
            }

            if(input.isKeyDown(Input.KEY_RIGHT)){
                Client.getPersonagem().setAnim(Client.getPersonagem().getWalkingRight());
                Client.getPersonagem().getWalkingRight().start();
                try {
                    Client.goRight(delta);
                } catch (IOException ex) {
                    Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                }
                //if(Client.getPersonagem().getPosX() < 1003) Client.getPersonagem().setPosX(Client.getPersonagem().getPosX()+(delta*.1f*Client.getPersonagem().getVelocidade()));
            }
            
        }
         
         if(!input.isKeyDown(Input.KEY_UP)) Client.getPersonagem().getWalkingUp().stop();
         if(!input.isKeyDown(Input.KEY_DOWN)) Client.getPersonagem().getWalkingDown().stop();
         if(!input.isKeyDown(Input.KEY_LEFT)) Client.getPersonagem().getWalkingLeft().stop();
         if(!input.isKeyDown(Input.KEY_RIGHT)) Client.getPersonagem().getWalkingRight().stop();
            
    }
    
    @Override
    public void keyReleased(int key, char c){        
        if(!chat.hasFocus()){
            if(key == Input.KEY_SPACE){
               try {
                    Client.attack(false);
                } catch (IOException ex) {
                    //Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
            
        }
    }
    
    @Override
    public void keyPressed(int key, char c){
        if(!chat.hasFocus()){
            int direcao=0;
            if(Client.getPersonagem().getAnim() == Client.getPersonagem().getWalkingDown()) direcao=1;
            if(Client.getPersonagem().getAnim() == Client.getPersonagem().getWalkingLeft()) direcao=2;
            if(Client.getPersonagem().getAnim() == Client.getPersonagem().getWalkingRight()) direcao=3;
            if(key == Input.KEY_M) minimap=!minimap;
            if(key == Input.KEY_C) {
                attributes=!attributes;
                createwindow.play();
                atmen.setMoving(false);
            }
            if(key == Input.KEY_S) {
                if(music.getVolume() == 0) music.setVolume((float) 0.5);
                else music.setVolume(0);
                    
            }
            if(key == Input.KEY_SPACE){
                try {
                    Client.attack(true);
                    
                    Client.fireball(direcao);
                } catch (IOException ex) {
                    //Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(key == Input.KEY_LSHIFT){
                try {
                    Client.sprint(direcao);
                } catch (IOException ex) {
                    //Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    @Override
    public void mousePressed(int button, int x, int y){
        if(button == Input.MOUSE_LEFT_BUTTON){
            if(attributes){
                if(atmen.getMouseOver() != null) try {
                    Client.upAttribute(atmen.getMouseOver());
                } catch (IOException ex) {
                    //Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(atmen.clickingTitle(x, y)) atmen.setMoving(true);
        }
    }
    
    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy){
        int difx = newx-oldx;
        int dify = newy-oldy;
        if(atmen.isMoving()){
            atmen.setPosX(atmen.getPosX()+difx);
            atmen.setPosY(atmen.getPosY()+dify);
        }
    }
    
    @Override
    public void mouseReleased(int button, int x, int y){
        atmen.setMoving(false);
    }
    
    @Override
    public int getID(){
        return 1;
    }
}
