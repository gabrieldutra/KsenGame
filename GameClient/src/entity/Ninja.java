package entity;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 *
 * @author gabrie   l
 */
public class Ninja extends Character {
    private static Image kunai;   

    /**
     * @return the skinN
     */
    public static int getSkinN() {
        return skinN;
    }
    private Sound kunaiSound; 
    private Sound runSound;
    private static final int skinN=2;
    private static final int skinX=6;
    private static final int skinY=4;
    
    static{
        try {        
            kunai = new Image("res/ninja/kunai.png");
        } catch (SlickException ex) {
            //Logger.getLogger(Ninja.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Ninja() throws SlickException{
        // Passa o parâmetro do local da skin do ninja juntamente com o tempo de cada animação
        super(skinN,skinX,skinY,150);
  
        float al = (float) 0.6;
        kunaiSound = new Sound("res/ninja/sound/kunai.ogg");
        runSound = new Sound("res/ninja/sound/ninjamove.ogg");
        
        
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
    public void attack(Graphics g, float fsX, float fsY) throws SlickException {
        //g.drawOval(fsX-32, fsY-32, 64+32, 64+32);

            //Image kunai = new Image("res/ninja/kunai.png");

            long time2 = 8+(System.currentTimeMillis()/15)%(32-8);
            for(int i = 360; i > 0; i-=45){      
                kunai.setCenterOfRotation(0, 0);
                kunai.setRotation(i);
                float alp = (32-(time2)); 
                kunai.setAlpha((float) Math.pow((alp)/32,0.1));           
                g.drawImage(kunai, (float) ((fsX+16)+(1*time2*Math.cos(i*0.0174532925))), (float) ((fsY+16)+(1*time2*Math.sin(i*0.0174532925))));
            }           

            //g.drawImage(kunai, fsX-time2, fsY+8);
            //this.setAnim(Ninja.getAttack());
       }

    @Override
    public Sound getAttackSound() {
        return this.getKunaiSound();
    }

    @Override
    public void sprint(Graphics g, float fsX, float fsY) throws SlickException {
        kunai.setAlpha(1);
        if(this.getAnim() == this.getWalkingDown()){
            kunai.setCenterOfRotation(0, 0);
            kunai.setRotation(94-(System.currentTimeMillis()%8));
            g.drawImage(kunai, fsX+12, fsY+24);
        }
        if(this.getAnim() == this.getWalkingUp()){
            kunai.setCenterOfRotation(0, 0);
            kunai.setRotation(-(94-(System.currentTimeMillis()%8)));
            g.drawImage(kunai, fsX+20, fsY+24);
        }
        if(this.getAnim() == this.getWalkingLeft()){
            kunai.setCenterOfRotation(0, 0);
            kunai.setRotation(184-(System.currentTimeMillis()%8));
            g.drawImage(kunai, fsX+12, fsY+32);            
        }
        if(this.getAnim() == this.getWalkingRight()){
            kunai.setCenterOfRotation(0, 0);
            kunai.setRotation(4-(System.currentTimeMillis()%8));
            g.drawImage(kunai, fsX+16, fsY+18); 
            
        }
    }

    /**
     * @return the runSound
     */
    @Override
    public Sound getRunSound() {
        return runSound;
    }

    /**
     * @param runSound the runSound to set
     */
    public void setRunSound(Sound runSound) {
        this.runSound = runSound;
    }
}
