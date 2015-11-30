package entity;

/**
 *
 * @author strudel
 */
public class MapElement extends Element {
    public MapElement(int pX, int pY){
        float posX=(32*pX)-320;
        float posY=((32*pY)-230)*-1;
        this.setPosX(posX);
        this.setPosY(posY);
        this.setWidth(32);
        this.setHeight(32);
    }
    
    public MapElement(float posX, float posY, float width, float height){
        this.setPosX(posX);
        this.setPosY(posY);
        this.setWidth(width);
        this.setHeight(height);
    }
}
