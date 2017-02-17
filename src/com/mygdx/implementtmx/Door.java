/**
 * Door object that allows transitions between the levels and includes the attributes 
 * such as location, dimensions and spawning location
 * A: Sid Gupta
 */
package com.mygdx.implementtmx;

import com.badlogic.gdx.math.Rectangle;

public class Door {
    private int x, y, width, height,startX, startY;
    private String lvlLeads;
    private Rectangle rect = new Rectangle();
    /*a door object has it's x,y corrdinates, dimensions, what level it leads to, and where the user will
        be in that level position wise when they enter the door
    */
    public Door(int x, int y, int width, int height,String lv, int startX, int startY){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        lvlLeads = lv;
        rect.setX(x);
        rect.setY(y);
        rect.setWidth(width);
        rect.setHeight(height);
        this.startX = startX;
        this.startY = startY;
    }
    public Rectangle getRect(){
        return rect;
    }
    public String getID(){
        return lvlLeads;
    }
    public int getX(){
        return x;
    }
    public int getStartX(){
        return startX;
    }
    public int getStartY(){
        return startY;
    }
    public int getY(){
        return y;
    }
    public void setX(int x){
        this.x = x;
        rect.setX(x);
    }
    public void setY(int y){
        this.y = y;
        rect.setY(y);
    }

}
