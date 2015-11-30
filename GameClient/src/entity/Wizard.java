package entity;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author gabriel
 */
public class Wizard extends Character {
    private static SpriteSheet fireball;

    /**
     * @return the fireball
     */
    public static SpriteSheet getFireball() {
        return fireball;
    }
    
    private static Image kunai;   

    /**
     * @return the skinN
     */
    public static int getSkinN() {
        return skinN;
    }
    private Sound kunaiSound;
    private static final int skinN=1;
    private static final int skinX=9;
    private static final int skinY=4;
    
    static{        
        try {
            fireball = new SpriteSheet("res/wizard/fireball.png",32,32);
            kunai = new Image("res/ninja/kunai.png");
        } catch (SlickException ex) {
            //Logger.getLogger(Wizard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Wizard() throws SlickException {
        super(skinN,skinX, skinY, 300);
        kunaiSound = new Sound("res/ninja/sound/kunai.ogg");
    }
    
    /**
     * @return the skinX
     */
    public static int getSkinX() {
        return skinX;
    }

    /**
     * @return the skinY
     */
    public static int getSkinY() {
        return skinY;
    }

    @Override
    public void attack(Graphics g, float fsX, float fsY) throws SlickException {
        //g.drawOval(fsX-32, fsY-32, 64+32, 64+32);



            //g.drawImage(kunai, fsX-time2, fsY+8);
            //this.setAnim(Ninja.getAttack());
       }
    
    /**
     * @return the kunaiSound
     */
    public Sound getKunaiSound() {
        return kunaiSound;
    }

    /**
     * @param aKunaiSound the kunaiSound to set
     */
    public void setKunaiSound(Sound aKunaiSound) {
        kunaiSound = aKunaiSound;
    }

    @Override
    public Sound getAttackSound() {
        return this.getKunaiSound();
    }

    @Override
    public void sprint(Graphics g, float fsX, float fsY) throws SlickException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sound getRunSound() {
        return this.getKunaiSound();
    }
    
    
}
