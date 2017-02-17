/**
 * Bullet Class allows to create bullets with attributes such as the positions
 * and direction.
 * Author Sid & Ankur
 */
package com.mygdx.implementtmx;

public class Bullet {

    private int x, y;
    char dir;

    public Bullet(int x, int y, char dir) {
        this.dir = dir;
        this.x = x;
        this.y = y;
    }

    public char getDir() {
        return dir;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setDir(char dir) {
        this.dir = dir;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
