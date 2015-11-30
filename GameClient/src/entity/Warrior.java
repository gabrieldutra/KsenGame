package entity;

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
public class Warrior extends Character {
    
    private static Image kunai;   
    private static SpriteSheet swords;
    private static Animation sword1;
    private static Animation sword2;
    private boolean swAtacking;    
    private Animation actualSword;
    private Sound swordSound;
    

    /**
     * @return the skinN
     */
    public static int getSkinN() {
        return skinN;
    }
    
    /**
     * @return the sword2
     */
    public static Animation getSword2() {
        return sword2;
    }

    /**
     * @param aSword2 the sword2 to set
     */
    public static void setSword2(Animation aSword2) {
        sword2 = aSword2;
    }

    /**
     * @return the swords
     */
    public static SpriteSheet getSwords() {
        return swords;
    }

    /**
     * @return the sword1
     */
    public static Animation getSword1() {
        return sword1;
    }

    /**
     * @return the actualSword
     */
    public Animation getActualSword() {
        return actualSword;
    }

    /**
     * @param aActualSword the actualSword to set
     */
    public void setActualSword(Animation aActualSword) {
        actualSword = aActualSword;
    }
    private Sound kunaiSound;
    private static final int skinN=1;
    private static final int skinX=6;
    private static final int skinY=4;
    
    static{        
        try {
            kunai = new Image("res/ninja/kunai.png");
            swords = new SpriteSheet("res/swords.png",24,24);
            Image[] sw1 = {getSwords().getSprite(0, 0),getSwords().getSprite(1, 0),getSwords().getSprite(2, 0),getSwords().getSprite(3, 0),getSwords().getSprite(4, 0)};
            Image[] sw2 = {getSwords().getSprite(5, 0),getSwords().getSprite(6, 0),getSwords().getSprite(7, 0),getSwords().getSprite(8, 0),getSwords().getSprite(9, 0)};
            int[] duration = {200,200,200,200,200};
            sword1 = new Animation(sw1,duration,true);
            setSword2(new Animation(sw2,duration,true));
        } catch (SlickException ex) {
            //Logger.getLogger(Wizard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Warrior() throws SlickException {
        super(skinN,skinX, skinY, 300);
        this.actualSword = Warrior.sword1;
        this.swordSound = new Sound("res/sound/sword.ogg");
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

    /**
     * @return the swAtacking
     */
    public boolean isSwAtacking() {
        return swAtacking;
    }

    /**
     * @param swAtacking the swAtacking to set
     */
    public void setSwAtacking(boolean swAtacking) {
        this.swAtacking = swAtacking;
    }

    /**
     * @return the swordSound
     */
    public Sound getSwordSound() {
        return swordSound;
    }

    /**
     * @param swordSound the swordSound to set
     */
    public void setSwordSound(Sound swordSound) {
        this.swordSound = swordSound;
    }
    
    
}
