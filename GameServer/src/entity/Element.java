package entity;



/**
 *
 * @author strudel
 */
public abstract class Element {
    private float posX;
    private float posY;
    private float width;
    private float height;

    /**
     * @return the posX
     */
    public float getPosX() {
        return posX;
    }

    /**
     * @param posX the posX to set
     */
    public void setPosX(float posX) {
        this.posX = posX;
    }

    /**
     * @return the posY
     */
    public float getPosY() {
        return posY;
    }

    /**
     * @param posY the posY to set
     */
    public void setPosY(float posY) {
        this.posY = posY;
    }

    /**
     * @return the width
     */
    public float getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public float getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(float height) {
        this.height = height;
    }
    
    public double getDistance(Element e){
        float pbX1 = this.getPosX()+(this.getWidth()/2);
        float pbY1 = this.getPosY()+(this.getHeight()/2);
        float pbX2 = e.getPosX()+(e.getWidth()/2);
        float pbY2 = e.getPosY()+(e.getHeight()/2);
        float difX = pbX2-pbX1;
        if(difX < 0) difX*=-1;
        float difY = pbY2-pbY1;
        if(difY < 0) difY*=-1;
        return Math.sqrt((difX*difX) + (difY*difY));
    }
    
    public boolean checaColisao(Element e){
        float eX,eY, tX,tY,eW,eH,tW,tH;
        eX = e.getPosX();
        eY = e.getPosY();
        eW = e.getWidth();
        eH = e.getHeight(); 
        tX = this.getPosX();
        tY = this.getPosY();
        tW = this.getWidth();
        tH = this.getHeight();
        return (tX > eX-eW && tX < eW+eX) && (tY > eY-eH && tY < eH+eY);
    }
}
