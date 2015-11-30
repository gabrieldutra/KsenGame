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
public abstract class Character extends Element {
  
    private String nome="";
    private int vida=100;
    private int mana=100;
    private float posX1=0;
    private float posX2=0;
    private float posY1=0;
    private float posY2=0;
    private Animation walkingUp;
    private Animation walkingDown;
    private Animation walkingLeft;
    private Animation walkingRight;
    private Animation anim;
    private boolean attacking=false;
    private boolean sprinting=false;
    private static SpriteSheet vx_chars;
    private static SpriteSheet vx_chars2;
    private int lifeChange=0;
    private long lifeTime=0;
    
    static{
        try {
            vx_chars = new SpriteSheet("res/vx_characters.png",32,32);
            vx_chars2 = new SpriteSheet("res/vx_characters3.png",32,32);
        } catch (SlickException ex) {
            //Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Character(int spn, int skinX, int skinY, int time) throws SlickException{
        int duration[] = {time,time,time};
        if(spn == 1){            
            Image[] walkUp = {vx_chars.getSprite(0+skinX,3+skinY), vx_chars.getSprite(1+skinX,3+skinY), vx_chars.getSprite(2+skinX,3+skinY)};
            Image[] walkDown = {vx_chars.getSprite(0+skinX,0+skinY), vx_chars.getSprite(1+skinX,0+skinY), vx_chars.getSprite(2+skinX,0+skinY)};
            Image[] walkLeft = {vx_chars.getSprite(0+skinX,1+skinY), vx_chars.getSprite(1+skinX,1+skinY), vx_chars.getSprite(2+skinX,1+skinY)};
            Image[] walkRight = {vx_chars.getSprite(0+skinX,2+skinY), vx_chars.getSprite(1+skinX,2+skinY), vx_chars.getSprite(2+skinX,2+skinY)};
            this.setWalkingUp(new Animation(walkUp, duration, true));
            this.setWalkingDown(new Animation(walkDown, duration, true));
            this.setWalkingLeft(new Animation(walkLeft, duration, true));
            this.setWalkingRight(new Animation(walkRight, duration, true));
        }
        if(spn == 2){            
            Image[] walkUp = {vx_chars2.getSprite(0+skinX,3+skinY), vx_chars2.getSprite(1+skinX,3+skinY), vx_chars2.getSprite(2+skinX,3+skinY)};
            Image[] walkDown = {vx_chars2.getSprite(0+skinX,0+skinY), vx_chars2.getSprite(1+skinX,0+skinY), vx_chars2.getSprite(2+skinX,0+skinY)};
            Image[] walkLeft = {vx_chars2.getSprite(0+skinX,1+skinY), vx_chars2.getSprite(1+skinX,1+skinY), vx_chars2.getSprite(2+skinX,1+skinY)};
            Image[] walkRight = {vx_chars2.getSprite(0+skinX,2+skinY), vx_chars2.getSprite(1+skinX,2+skinY), vx_chars2.getSprite(2+skinX,2+skinY)};
            this.setWalkingUp(new Animation(walkUp, duration, true));
            this.setWalkingDown(new Animation(walkDown, duration, true));
            this.setWalkingLeft(new Animation(walkLeft, duration, true));
            this.setWalkingRight(new Animation(walkRight, duration, true));
        }
        /*Image[] walkUp = {new Image(this.getSkin()+"back.png"), new Image(this.getSkin()+"back-w1.png"), new Image(this.getSkin()+"back-w2.png")};
        Image[] walkDown = {new Image(this.getSkin()+"front.png"), new Image(this.getSkin()+"front-w1.png"), new Image(this.getSkin()+"front-w2.png")};
        Image[] walkLeft = {new Image(this.getSkin()+"left.png"), new Image(this.getSkin()+"left-w1.png"), new Image(this.getSkin()+"left-w2.png")};
        Image[] walkRight = {new Image(this.getSkin()+"right.png"), new Image(this.getSkin()+"right-w1.png"), new Image(this.getSkin()+"right-w2.png")};*/
        
        this.getWalkingDown().stop();
        this.setAnim(this.getWalkingDown());
    }
    
    public abstract Sound getAttackSound();
    public abstract Sound getRunSound();
    public abstract void attack(Graphics g, float fsX, float fsY)throws SlickException ;

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }
    

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the vida
     */
    public int getVida() {
        return vida;
    }  
    
     /**
     * @param num
     * @return the vx_chars
     */
    public static SpriteSheet getVx_chars(int num) {
        if(num == 2) return vx_chars2;
        return vx_chars;
    }
    /**
     * @param vida the vida to set
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * @return the skin
     */
    public String getSkin() {
        return this.getImage();
    }

    /**
     * @param skin the skin to set
     */
    public void setSkin(String skin) {
        this.setImage(skin);
    }

    /**
     * @return the walkingUp
     */
    public Animation getWalkingUp() {
        return walkingUp;
    }

    /**
     * @param walkingUp the walkingUp to set
     */
    public void setWalkingUp(Animation walkingUp) {
        this.walkingUp = walkingUp;
    }

    /**
     * @return the walkingDown
     */
    public Animation getWalkingDown() {
        return walkingDown;
    }

    /**
     * @param walkingDown the walkingDown to set
     */
    public void setWalkingDown(Animation walkingDown) {
        this.walkingDown = walkingDown;
    }

    /**
     * @return the walkingLeft
     */
    public Animation getWalkingLeft() {
        return walkingLeft;
    }

    /**
     * @param walkingLeft the walkingLeft to set
     */
    public void setWalkingLeft(Animation walkingLeft) {
        this.walkingLeft = walkingLeft;
    }

    /**
     * @return the walkingRight
     */
    public Animation getWalkingRight() {
        return walkingRight;
    }

    /**
     * @param walkingRight the walkingRight to set
     */
    public void setWalkingRight(Animation walkingRight) {
        this.walkingRight = walkingRight;
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
     * @return the attacking
     */
    public boolean isAttacking() {
        return attacking;
    }

    /**
     * @param attacking the attacking to set
     */
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    /**
     * @return the mana
     */
    public int getMana() {
        return mana;
    }

    /**
     * @param mana the mana to set
     */
    public void setMana(int mana) {
        this.mana = mana;
    }

    /**
     * @return the sprinting
     */
    public boolean isSprinting() {
        return sprinting;
    }

    /**
     * @param sprinting the sprinting to set
     */
    public void setSprinting(boolean sprinting) {
        this.sprinting = sprinting;
    }

    public abstract void sprint(Graphics g, float fsX, float fsY) throws SlickException;

    /**
     * @return the lifeChange
     */
    public int getLifeChange() {
        return lifeChange;
    }

    /**
     * @param lifeChange the lifeChange to set
     */
    public void setLifeChange(int lifeChange) {
        this.lifeChange = lifeChange;
    }

    /**
     * @return the lifeTime
     */
    public long getLifeTime() {
        return lifeTime;
    }

    /**
     * @param lifeTime the lifeTime to set
     */
    public void setLifeTime(long lifeTime) {
        this.lifeTime = lifeTime;
    }

}
