package entity;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

/**
 *
 * @author strudel
 */
public class Fireball extends Element {
    private int id;
    private Character summoner;
    private static SpriteSheet fireball = Wizard.getFireball();
    private Sound fireballSound;
    private Animation fireballUp;
    private Animation fireballDown;
    private Animation fireballLeft;
    private Animation fireballRight;
    private Animation anim;
    private float posX1;
    private float posX2=0;
    private float posY1;
    private float posY2=0;    
    
    public Fireball(int id, Character summoner){
        try {
            fireballSound = new Sound("res/sound/fireball.ogg");
        } catch (SlickException ex) {
            Logger.getLogger(Fireball.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.id=id;
        this.summoner = summoner;
        Image[] fireUp = {fireball.getSprite(0, 3),fireball.getSprite(1, 3),fireball.getSprite(2, 3)};
        Image[] fireDown = {fireball.getSprite(0, 0),fireball.getSprite(1, 0),fireball.getSprite(2, 0)};
        Image[] fireLeft = {fireball.getSprite(0, 1),fireball.getSprite(1, 1),fireball.getSprite(2, 1)};
        Image[] fireRight = {fireball.getSprite(0, 2),fireball.getSprite(1, 2),fireball.getSprite(2, 2)};
        int[] duration = {100,100,100};
        fireballUp = new Animation(fireUp,duration,true);
        fireballDown = new Animation(fireDown,duration,true);
        fireballLeft = new Animation(fireLeft,duration,true);
        fireballRight = new Animation(fireRight,duration,true);
        this.anim = fireballDown;
    }
    
    /**
     * @return the posX1
     */
    public float getPosX1() {
        return posX1;
    }

    /**
     * @param posX1 the posX1 to set
     */
    public void setPosX1(float posX1) {
        this.posX1 = posX1;
    }

    /**
     * @return the posX2
     */
    public float getPosX2() {
        return posX2;
    }

    /**
     * @param posX2 the posX2 to set
     */
    public void setPosX2(float posX2) {
        this.posX2 = posX2;
    }

    /**
     * @return the posY1
     */
    public float getPosY1() {
        return posY1;
    }

    /**
     * @param posY1 the posY1 to set
     */
    public void setPosY1(float posY1) {
        this.posY1 = posY1;
    }

    /**
     * @return the posY2
     */
    public float getPosY2() {
        return posY2;
    }

    /**
     * @param posY2 the posY2 to set
     */
    public void setPosY2(float posY2) {
        this.posY2 = posY2;
    }

    /**
     * @return the fireballUp
     */
    public Animation getFireballUp() {
        return fireballUp;
    }

    /**
     * @return the fireballDown
     */
    public Animation getFireballDown() {
        return fireballDown;
    }

    /**
     * @return the fireballLeft
     */
    public Animation getFireballLeft() {
        return fireballLeft;
    }

    /**
     * @return the fireballRight
     */
    public Animation getFireballRight() {
        return fireballRight;
    }

    /**
     * @return the anim
     */
    public Animation getAnim() {
        return anim;
    }

    /**
     * @param anim the anim to set
     */
    public void setAnim(Animation anim) {
        this.anim = anim;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the fireballSound
     */
    public Sound getFireballSound() {
        return fireballSound;
    }
    
}
