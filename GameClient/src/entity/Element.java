package entity;

/**
 *
 * @author strudel
 */
public abstract class Element {
    private float posX=0;
    private float posY=0;
    private float width;
    private float height;
    private String image;

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

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
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
        return ((eX < tX+tW || tX < eX+eW)&& (eY < tY+tH || tY < eY+eH));
    }
}
