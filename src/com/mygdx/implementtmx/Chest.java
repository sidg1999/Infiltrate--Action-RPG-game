/**
 * A chest object that contains a health pot,
 * Has attributes such as location, rectangles for hit detection and dimensions
 * A: Sid Gupta
 */
package com.mygdx.implementtmx;

import com.badlogic.gdx.math.Rectangle;

public class Chest {
private boolean isOpened;
    private int x, y, width, height;
    private String chest;
    private Rectangle rect = new Rectangle();

    public Chest(int x, int y, int width, int height, String chestID) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        chest = chestID;
        rect.setX(x);
        rect.setY(y);
        rect.setWidth(width);
        rect.setHeight(height);
        isOpened = false;
    }

    public Rectangle getRect() {
        return rect;
    }
    
    
    public void isOpened(){
        this.isOpened = true;
    }
    
    public boolean Opened(){
        return isOpened;
    }
}
