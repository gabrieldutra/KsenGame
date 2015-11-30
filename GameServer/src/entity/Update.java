package entity;

import connection.Server;
import db.CharacterDAO;

/**
 *
 * @author gabriel
 */
public class Update implements Runnable {

    @Override
    public void run() {
        while(true){
            for(Character p : Server.getPersonagens()){   
                // Check Level Up
                if(p.getScore() >= (5*Math.pow(2,p.getLevel()-1))){
                    p.setScore((p.getScore()-(int)(5*Math.pow(2,p.getLevel()-1))));
                    p.setLevel(p.getLevel()+1);
                    p.setPoints(p.getPoints()+1);
                    p.getConexao().sendMessage("USP:res/sound/levelup.ogg\\");
                    CharacterDAO cdao = Server.cdao;
                    cdao.update(p);
                }
                if(p.getFirecooldown() > 0) p.setFirecooldown(p.getFirecooldown()-1);
                if(p.getSlowedTime() > 0) p.setSlowedTime(p.getSlowedTime()-1);
                if(p.getMana() < 100) p.setMana(p.getMana()+1);
                if(p.getVida() > 0 && p.getVida() < 100){
                    for(MapElement e : Server.getLiferecovery()){
                        if(p.checaColisao(e)){                            
                            if(p.getCountVida() == 3) {
                                p.setVida(p.getVida()+1);
                                p.setCountVida(0);
                            } else p.setCountVida(p.getCountVida()+1);
                            break;
                        }
                    }
                }
                if(p.getSprintcount() < 0){
                    p.setSprintcount(p.getSprintcount()+1);
                }
                if(p.getSprintcount() > 0){
                    p.setSprintcount(p.getSprintcount()-1);
                    if(p.getSprintcount() == 0){
                        p.setVelocidade(p.getVelocidade()-4);
                    }
                }
                if(p.isAttacking() && (p.getClasse() == 1 || p.getClasse() == 3) ){
                    if(p.getMana() < 5){
                        //p.getConexao().sendMessage("MSG:Server:Você não possuí stamina suficiente!\\");
                        p.setAttacking(false);
                    } else p.setMana(p.getMana()-5);
                }
                for(Character p2 : Server.getPersonagens()){
                    if(p == p2) continue;
                    double dist = p.getDistance(p2);
                    
                    if(p.isAttacking() && p.getClasse() == 3 && p2.getVida() >= 0){
                        int difX=0;
                        int difY=0;
                        if(p.getDirection() == 0) difY=-32;
                        if(p.getDirection() == 1) difY=32;
                        if(p.getDirection() == 2) difX=-32;
                        if(p.getDirection() == 3) difX=32;
                        MapElement mtest = new MapElement(p.getPosX()+difX,p.getPosY()+difY,32,32);
                        if(mtest.checaColisao(p2) ){
                            if(p.getSword() == 1) p2.setSlowedTime(25);
                            p2.setVida(p2.getVida()-(p.calculateDamage((float)(0.3),p2)));
                            p2.checkKill(p);
                        }
                    }
                    
                    // Ninja attacking
                    if(dist < 64 && p.isAttacking() && p.getClasse() == 1){
                        if(p2.getClasse() >= 1 && p2.getVida() >= 0){
                            p2.setVida(p2.getVida()-(p.calculateDamage((float)(0.1666),p2)));
                            p2.checkKill(p);
                        }
                    }
                }                
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                //Logger.getLogger(Update.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        
    }
    
}
