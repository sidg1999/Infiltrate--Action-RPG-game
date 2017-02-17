/*
 * Allows the screens to be switched fluidly and also holds the scoring system.
 * A: Sid Gupta
 */
package com.mygdx.implementtmx;

public class MenuSwitch {
    private String switchTo = "";
    private int x;
    private int y;
    private boolean upper = false;
    private int score=0;
    public MenuSwitch(){
        
    }
    public String getSwitch(){
        return switchTo;
    }
    public void setSwitch(String switchTo){
        this.switchTo = switchTo;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setUpper(boolean upper){
        this.upper = upper;
    }
    public boolean getUpper(){
        return upper;
    }
    public void scoreIncrKill(){
        score+=50;
    }
    public void scoreIncrComplete(){
        score+=50;
    }
    public int getScore(){
        return score;
    }
}
