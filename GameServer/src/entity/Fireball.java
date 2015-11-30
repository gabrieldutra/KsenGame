package entity;

import connection.Server;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author strudel
 */
public class Fireball extends Element implements Runnable {
    private int id;
    private Character summoner;
    private int count;
    private int direction; // 0 = Up, 1 = Down, 2 = Left, 3 = Right
    private static int countid=0;
    
    public Fireball(Character summoner, float pX, float pY, int direction){
        countid++;
        id = countid;
        this.direction = direction;
        this.summoner = summoner;
        this.setPosX(pX);
        this.setPosY(pY);
        this.setHeight(32);
        this.setWidth(32);
        count = 150;
    }

    @Override
    public void run() {
        Server.addFireball(this);
        while(count > 0){
            try {
                float dif = (float) 2.5;
                if(this.getDirection() == 0){
                    this.setPosY(this.getPosY()+dif);
                }
                
                if(this.getDirection() == 1){
                    this.setPosY(this.getPosY()-dif);                    
                }
                
                if(this.getDirection() == 2){
                    this.setPosX(this.getPosX()-dif);
                }
                
                if(this.getDirection() == 3){
                    this.setPosX(this.getPosX()+dif);
                }
                boolean brk=false;
                for(MapElement m : Server.getElements()){
                    if(this.checaColisao(m)){
                        Server.soundToWhoCanSee(this, "res/sound/explosion.ogg");
                        brk=true;
                    }
                }
                
                if(!brk) for(Character pl : Server.getPersonagens()){
                    if(this.checaColisao(pl) && pl != this.summoner){
                        Server.soundToWhoCanSee(this, "res/sound/explosion.ogg");                        
                        pl.setVida(pl.getVida()-(this.getSummoner().calculateDamage(pl)));
                        pl.checkKill(this.getSummoner());
                        brk=true;
                    }
                }
                if(brk) break;
                count--;
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                //Logger.getLogger(Fireball.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Server.sendMessageToAll("FBD:"+this.getId()+"\\");
        //Server.getFireballs().remove(this);
        Server.removeFireball(this);
    }

    /**
     * @return the summoner
     */
    public Character getSummoner() {
        return summoner;
    }

    /**
     * @param summoner the summoner to set
     */
    public void setSummoner(Character summoner) {
        this.summoner = summoner;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(int direction) {
        this.direction = direction;
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
    
}
