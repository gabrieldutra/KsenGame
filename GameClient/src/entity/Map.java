package entity;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author strudel
 */
public class Map extends Element {
    private TiledMap map;
    
    public Map() throws SlickException{
        map = new TiledMap("res/map2.tmx");
        this.setHeight(1280);
        this.setWidth(1280);
    }

    public Map(String image){
        this.setPosX(0);
        this.setPosY(0);
        this.setImage(image);
    }

    /**
     * @return the map
     */
    public TiledMap getMap() {
        return map;
    }
    
    public int getLayerId(String layername){
        return map.getLayerIndex(layername);
    }

}
