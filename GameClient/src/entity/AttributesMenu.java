package entity;

import connection.Client;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author gabriel
 */
public class AttributesMenu {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private boolean moving=false;
    private String mouseOver = null;
        
    public AttributesMenu(int posX, int posY, int width, int height){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }
    
    public synchronized void draw(Graphics g){
        this.setHeight(40+50+Client.getAttributes().size()*40);

        Color before = g.getColor();
        g.setColor(Color.white);
        g.fillRoundRect(this.posX, this.posY, this.width, this.height, 5);    
        g.setColor(Color.black);
        g.fillRoundRect(this.posX, this.posY, this.width, 30, 5);     
        g.setColor(Color.white);
        g.drawString("Atributos", this.posX+10, this.posY+5);    
        g.setColor(Color.black);
        int aX = this.posX+10;
        int aY = this.posY+40;
        this.mouseOver = null;
        for(Attribute a : Client.getAttributes()){
            g.drawImage(a.getImage(), aX, aY);
            g.drawString(a.getName()+": "+a.getValue(), aX+40, aY+8);
            if(a.isAdd()){
                a.getPlusButton().setAlpha((float) 0.8);
                int xpos=Mouse.getX();
                int ypos=Mouse.getY();
                if(Client.getPoints() > 0 && xpos > aX+this.getWidth()-50 && xpos < aX+this.getWidth()-50+32 && ypos < 480-(aY) && ypos > 480-(aY+32)) {
                    this.mouseOver = a.getCode();
                    a.getPlusButton().setAlpha(1);
                }
                
                if(Client.getPoints() == 0) a.getPlusButton().setAlpha((float) 0.5);
                
                g.drawImage(a.getPlusButton(), aX+this.getWidth()-50, aY);                
            }   
            aY+=40;
        }
        g.setColor(new Color(150,0,0));
        if(Client.getPoints() > 0) g.setColor(new Color(0,150,0));
        g.drawString("Pontos: "+Client.getPoints(), aX, aY+10);
        g.setColor(before);
    }

    /**
     * @return the posX
     */
    public int getPosX() {
        return posX;
    }

    /**
     * @param posX the posX to set
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * @return the posY
     */
    public int getPosY() {
        return posY;
    }

    /**
     * @param posY the posY to set
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the moving
     */
    public boolean isMoving() {
        return moving;
    }

    /**
     * @param moving the moving to set
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }
    
    public boolean clickingTitle(int x, int y){
        return x > this.getPosX() && x < this.getPosX()+this.getWidth() && y > this.getPosY() && y < this.getPosY()+40;
    }

    /**
     * @return the mouseOver
     */
    public String getMouseOver() {
        return mouseOver;
    }

    /**
     * @param mouseOver the mouseOver to set
     */
    public void setMouseOver(String mouseOver) {
        this.mouseOver = mouseOver;
    }
    
}
