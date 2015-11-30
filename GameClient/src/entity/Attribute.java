package entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author gabriel
 */
public class Attribute {
    private String name;
    private String value;
    private String code;
    private Image image;
    private Image plusButton;
    private boolean add;
    
    public Attribute(String name, String value, String code, String image, boolean add) throws SlickException{
        this.name = name;
        this.value = value;
        this.code = code;
        this.image = new Image(image);
        this.plusButton = new Image("res/icon/plusbutton.png");
        this.add = add;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * @return the plusButton
     */
    public Image getPlusButton() {
        return plusButton;
    }

    /**
     * @param plusButton the plusButton to set
     */
    public void setPlusButton(Image plusButton) {
        this.plusButton = plusButton;
    }

    /**
     * @return the add
     */
    public boolean isAdd() {
        return add;
    }

    /**
     * @param add the add to set
     */
    public void setAdd(boolean add) {
        this.add = add;
    }
}
