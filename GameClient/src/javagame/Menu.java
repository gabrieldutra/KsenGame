package javagame;

import connection.Client;
import connection.InvalidNickException;
import connection.NoAnswerException;
import entity.Ninja;
import entity.Warrior;
import entity.Wizard;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import utils.TextField;
import org.newdawn.slick.state.*;

/**
 *
 * @author gabriel
 */
class Menu extends BasicGameState {
    
    
    Image bg;
    Image btPlay;
    Image btExit;
    private int acChar=0;
    Image[] character = new Image[3];
    String[] charName = new String[3];
    Color charNameColor1 = Color.gray;
    Color charNameColor2 = Color.gray;
    TextField nick;
    TextField password;
    String Errormsg = "";
    Music music;
    
    public String mouse = "No input yet!";

    public Menu(int state) {
        
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        gc.getGraphics().setBackground(Color.gray);
        this.btExit = new Image("res/exit.png");
        this.btExit.setAlpha((float) 0.8);
        this.btPlay = new Image("res/play.png");
        this.btPlay.setAlpha((float) 0.8);
        this.bg = new Image("res/menu.png");       
        this.character[0] = entity.Character.getVx_chars(Ninja.getSkinN()).getSprite(Ninja.getSkinX()+1, Ninja.getSkinY());
        this.character[1] = entity.Character.getVx_chars(Wizard.getSkinN()).getSprite(Wizard.getSkinX()+1, Wizard.getSkinY());
        this.character[2] = entity.Character.getVx_chars(Warrior.getSkinN()).getSprite(Warrior.getSkinX()+1, Warrior.getSkinY());
        this.charName[0] = "Ninja";
        this.charName[1] = "Mago";
        this.charName[2] = "Guerreiro";
        nick = new TextField(gc, gc.getDefaultFont(), 320, 145, btPlay.getWidth(), 20);
        password = new TextField(gc, gc.getDefaultFont(), 320, 170, btPlay.getWidth(), 20);
        password.setMaskEnabled(true);
        music = new Music("res/music/crywolf.ogg");
        music.loop();
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
       
       //g.drawRect(50, 100, 32, 32); // x, y. width, height
       //g.drawOval(200, 130, 130, 80);
       //400 x 479
       g.setLineWidth(1);
       g.drawImage(bg, 0, 0);
       g.drawImage(btPlay, 320, 200);
       g.drawImage(btExit, 320, 300);
       g.drawImage(character[getAcChar()], 240, 210);
       g.setColor(Color.black);
       g.drawString(charName[getAcChar()], 240-(charName[getAcChar()].length()), 245);
       
       
       g.setColor(Color.gray);
       g.drawString("Nick", 385+20, 145);
       g.drawString("Senha", 385+20, 170);
       g.drawString(Errormsg, 385+20, 120);
       g.setColor(Color.white);
       nick.render(gc, g);
       password.render(gc, g);
       g.setLineWidth(10);
       g.setColor(charNameColor1);       
       g.drawString("<", 220, 218);    
       g.setColor(charNameColor2);      
       g.drawString(">", 240+32+7, 218);
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        int xpos=Mouse.getX();
        int ypos=Mouse.getY();
        mouse = "X: "+xpos+" Y:"+ypos;
        
        if((xpos>320 && xpos<542) && (ypos >221 && ypos<280)){
            this.btPlay.setAlpha(1);
            if(Mouse.isButtonDown(0)){
                try {
                    //greensleeves.stop();
                    //greensleeves.fade(10, 0, true);
                    
                    Client.enterGame(nick.getText().replaceAll(" ", "_").replaceAll(":", ""),password.getText().replaceAll(" ", "_").replaceAll(":", ""),acChar+1);
                    music.pause();
                    sbg.enterState(1);                    
                } catch (IOException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidNickException | NoAnswerException ex) {
                    this.Errormsg = ex.getMessage();
                }
            
            }
        } else this.btPlay.setAlpha((float) 0.8);
        
        if((xpos > 240-32 && xpos < 240) && (ypos > 231 && ypos < 268)){
            charNameColor1 = Color.black;
        } else charNameColor1 = Color.gray;
        
        if((xpos > 240-32+32+20 && xpos < 240+32+20) && (ypos > 231 && ypos < 268)){
            charNameColor2 = Color.black;
        } else charNameColor2 = Color.gray;
        
        if((xpos>320 && xpos<542) && (ypos >121 && ypos<180)){
            if(Mouse.isButtonDown(0)){
                System.exit(0);
            }
            this.btExit.setAlpha(1);
        } else this.btExit.setAlpha((float) 0.8);
        
    }
    
    @Override
    public void mousePressed(int button, int x, int y){
        int xpos=Mouse.getX();
        int ypos=Mouse.getY();
        if(button == 0){
            if((xpos > 240-32 && xpos < 240) && (ypos > 231 && ypos < 268)){
                if(getAcChar() == 0) acChar = character.length-1;
                else acChar--;
            } 
        
            if((xpos > 240-32+32+20 && xpos < 240+32+20) && (ypos > 231 && ypos < 268)){
                
                if(getAcChar() == character.length-1) acChar = 0;
                else acChar++;
            } 
        }
        
    }
    
    @Override
    public int getID(){
        return 0;
    }

    /**
     * @return the acChar
     */
    public int getAcChar() {
        return acChar;
    }
}
