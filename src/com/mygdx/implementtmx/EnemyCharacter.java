/**
 * Enemy character that extends of the Abstract Character class and contains
 * attributes such as the position, collision detection, animations
 */
package com.mygdx.implementtmx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyCharacter extends AbstractCharacter {

    private boolean isWalking = false;
    private boolean walkingHoriz = false;
    private boolean walkingVert = false;
    private long startT;
    private long endT;

    private int VectorToPlayerX;
    private int VectorToPlayerY;
    
    private char lastDir = 'X';
    
    public EnemyCharacter(Texture walkSheet, int walkCols, int walkRows) {
        super(walkSheet, walkCols, walkRows);
    }

    public TextureRegion walk(EnemyCharacter enemy, int dist, char dir) {
        //make your way to walk to the player's last x position
        //if the current direction is the same as the last direction
        if(dir==lastDir){
            //then keep playing the current walking animation
            //get the current time
            endT = System.currentTimeMillis();
            //get current walking animation frame of this direction
             return getWalkFrame(endT, startT, dir);
        }else{
            //otherwise, play the new direction animation
            //get start, end time
            startT = System.currentTimeMillis();
            endT = System.currentTimeMillis();
            //reset the lastDir
            lastDir = dir;
            //return start frame of this direction
            return getWalkFrame(endT, startT, dir);
        }
    }

    public TextureRegion walkToPlayerX(EnemyCharacter enemy, UserCharacter player) {
        VectorToPlayerX = getHorizDist(enemy,player);
        // Now send command to the enemy to walk certain units on the 
        // X scale UNTIL they reach the user's last x position
        if (VectorToPlayerX > 0) {
            // right
            return enemy.walk(enemy, VectorToPlayerX, 'R');
        } else if (VectorToPlayerX < 0) {
            //left
            return enemy.walk(enemy, VectorToPlayerX, 'L');
        }else{
            return null;
        }
    }
    public TextureRegion walkToPlayerY(EnemyCharacter enemy, UserCharacter player) {
        VectorToPlayerY = getVertDist(enemy,player);
        // Now send command to the enemy to walk certain units on the 
        // X scale UNTIL they reach the user's last x position
        if (VectorToPlayerY > 0) {
            // up
            return enemy.walk(enemy, VectorToPlayerY, 'U');
        } else if (VectorToPlayerY < 0) {
            //down
            return enemy.walk(enemy, VectorToPlayerY, 'D');
        }else{
            return null;
        }
    }
    
    public boolean isHorizDist(EnemyCharacter enemy, UserCharacter player){
        VectorToPlayerX = getHorizDist(enemy,player);
        //if the distance is 1 unit, -1 unit, or 0 units, theyre belw/above the player
        if (VectorToPlayerX == 1 || VectorToPlayerX == -1 || VectorToPlayerX == 0) {
             return false;
        }else{
            return true;
        }
    }
    public boolean isVertDist(EnemyCharacter enemy, UserCharacter player){
        VectorToPlayerY = getVertDist(enemy,player);
        //if the distance is 1 unit, -1 unit, or 0 units, theyre belw/above the player
        if (VectorToPlayerY == 1 || VectorToPlayerY == -1 || VectorToPlayerY == 0) {
             return false;
        }else{
            return true;
        }
    }
    
    public int getHorizDist(EnemyCharacter enemy, UserCharacter player){
        int eX = enemy.getXPos();
        int pX = player.getXPos();
        //determines the horiz dist betw the enemy & the player
        return pX - eX;
    }
    public int getVertDist(EnemyCharacter enemy, UserCharacter player){
        int eY = enemy.getYPos();
        int pY = player.getYPos();
        //determines the horiz dist betw the enemy & the player
        return pY - eY;
    }

    /**
     * Receive commands to move in certain directions
     *
     * @param dir what direction the enemy should walk
     */


    /**
     * Check if the player is within a certain enemy
     *
     * @param boundryRadius in px, how big the circle is
     * @param player to get the coordinates of the player
     * @return true if the player is within boundries
     */
    public boolean checkBoundries(int boundryRadius, UserCharacter player) {
        // Get the coordinates of both enemy and player
        int playerX = player.getXPos();
        int playerY = player.getYPos();
        int enemyX = this.getXPos();
        int enemyY = this.getYPos();
        // Find the magnitude of the line between the two coordinates
        // (playerX, playerY) and (enenmyX, enemyY)
        // Root(x2-x1)^2 + (y2-y1)^2
        double magnitude = Math.abs(Math.sqrt(Math.pow(playerX - enemyX, 2) + Math.pow(playerY - enemyY, 2)));
        if (magnitude <= boundryRadius) {
            // from here also connect to walkToPlayer
            //walkToPlayer(this, player);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void moveU() {
        yPos += 2;
        rect.setY(yPos);
    }
    @Override
    public void moveD() {
        yPos -= 2;
        rect.setY(yPos);
    }
    @Override
    public void moveL() {
        xPos -= 2;
        rect.setX(xPos);
    }
    @Override
    public void moveR() {
        xPos += 2;
        rect.setX(xPos);
    }
    public char getDir(){
        return lastDir;
    }
    

    /**
     * Make sure the enemy walks back to their original location
     *
     * @param x original x value
     * @param y original y value
     */
    /* public void walkToOrigin(int x, int y) {
        isWalking = true;
        // get coordinates for the enemy
        int eX = this.getXPos();
        int eY = this.getYPos();
        // create a vector from the Newlocation to the original location
        int VectorToOgX = x - eX, VectorToOgY = y - eY;
        // determine whether to walk left or right
        if (VectorToOgX > 0) {
            // right
            this.walk(this, eX, 'R');
            isWalking = false;
        } else if (VectorToOgX < 0) {
            // left
            this.walk(this, eX, 'L');
            isWalking = false;
        }
        // up or down
        if (VectorToOgY > 0) {
            // up
            this.walk(this, eY, 'U');
            isWalking = false;
        } else if (VectorToOgY < 0) {
            // down
            this.walk(this, eY, 'D');
            isWalking = false;
        }
    }*/
    /**
     * If the player is within the radius of the enemy then the enemy will walk
     * over to the player and stop before him. (Approx 30px)
     *
     * @param enemy obj that will move
     * @param player obj to travel to (target)
     */
    /*public void walkToPlayer(EnemyCharacter enemy, UserCharacter player) {
        
        int eX = enemy.getXPos(), eY = enemy.getYPos();
        int pX = player.getXPos(), pY = player.getYPos();
        // create a vector from enemy to player. this gets the distance in x,y components
        //betw the enemy & the player
        int VectorToPlayerX = pX - eX, VectorToPlayerY = pY - eY;
        // Now send command to the enemy to walk certain units on the X scale and Y scale
        // determine to walk right or left
        if (VectorToPlayerX > 0) {
            // right
            enemy.walk(enemy, VectorToPlayerX, 'R');
        } else {
            enemy.walk(enemy, VectorToPlayerX, 'L');
        }
        // determine to walk up or down
        if (VectorToPlayerY > 0) {
            // up
            enemy.walk(enemy, VectorToPlayerY, 'U');
        } else {
            // down
            enemy.walk(enemy, VectorToPlayerY, 'D');
        }
    }*/
}
