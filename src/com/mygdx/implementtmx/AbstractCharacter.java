/*Sid Gupta, Ankur Mishra, Muhammad Hassan
January 19th 2017
The abstract character class. This class initializes each instantiated character's spritesheets,
gets the current animated frame of a character within a certain time interval, and has attributes 
of the character (xPo, yPos, width, height, health, direction), which are assigned and referred
to during calculations in the program
*/

package com.mygdx.implementtmx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

abstract public class AbstractCharacter {

    //rows and columns of the walking sprite sheet 
    private int WALK_COLS, WALK_ROWS;
    
    //Objects used for animation
    private Animation<TextureRegion> walkUAnim;
    private Animation<TextureRegion> walkDAnim;
    private Animation<TextureRegion> walkLAnim;
    private Animation<TextureRegion> walkRAnim;
    private TextureRegion[] walkU;
    private TextureRegion[] walkL;
    private TextureRegion[] walkD;
    private TextureRegion[] walkR;
    protected int xPos, yPos;
    private int wsHeight, wsWidth;

    private int healthPotions = 2;
    private boolean shooting = false;
    private int health = 100, hpPots = 2;
    private char dir = 'D';
    
    //rectangle object used for collision
    Rectangle rect = new Rectangle();

    public AbstractCharacter(Texture walkSheet, int walkCols, int walkRows) {
        WALK_COLS = walkCols;
        WALK_ROWS = walkRows;
        initWalkSprites(walkSheet);
        //initialize properties of rect object
        rect.setX(0);
        rect.setY(0);
    }

    //initializes character objects when the UserCharacter object is instantiated
    public void initWalkSprites(Texture walkSheet) {
        //size the character rectangle
        rect.setWidth(walkSheet.getWidth() / WALK_COLS-40);
        rect.setHeight(15);
        //get length, width of spritesheets
        wsHeight  = walkSheet.getHeight() / WALK_ROWS;
        wsWidth = walkSheet.getWidth() / WALK_COLS;
        
        // Use the split utility method to create a 2D array of TextureRegions. This is 
        // possible because this sprite sheet contains frames of equal size and they are all aligned.
        TextureRegion[][] tmpWalk = TextureRegion.split(walkSheet, wsHeight, wsWidth);

        //each row in the walk spritesheet represents a direction. first row is walking up, second row walking left, etc. 
        //store the direction walking sprites each in their own arrays
        //each of these arrays points to its corresponding row in the tmp 2d array
        walkU = tmpWalk[0];
        walkL = tmpWalk[1];
        walkD = tmpWalk[2];
        walkR = tmpWalk[3];
        
        // Initialize the Animation with the frame interval and array of frames
        //**NOTE** the 'f' at the end of the numbers casts the value as a float
        walkUAnim = new Animation<TextureRegion>(0.111f, walkU);
        walkLAnim = new Animation<TextureRegion>(0.111f, walkL);
        walkDAnim = new Animation<TextureRegion>(0.111f, walkD);
        walkRAnim = new Animation<TextureRegion>(0.111f, walkR);
    }

    //gets the idle frame of the character; that is, the frame of the character when they are not moving
    public TextureRegion getIdle(char direction) {
        //if the character was previously going left, display the left idle sprite. if they were going right, display the right one, and so on.
        if (direction == 'L') {
            return walkL[0];
        } else if (direction == 'R') {
            return walkR[0];
        } else if (direction == 'U') {
            return walkU[0];
        } else {
            return walkD[0];
        }
    }
    //gets the current frame of the character based off how long the character has been moving in a 
    //certain direction for
    public TextureRegion getWalkFrame(long stateT, long startT, char direction) {
        float timePressd;
        if (direction == 'L') {
            timePressd = stateT - startT;
            return walkLAnim.getKeyFrame(timePressd, true);
        } else if (direction == 'R') {
            timePressd = stateT - startT;
            return walkRAnim.getKeyFrame(timePressd, true);
        } else if (direction == 'U') {
            timePressd = stateT - startT;
            return walkUAnim.getKeyFrame(timePressd, true);
        } else {
            timePressd = stateT - startT;
            return walkDAnim.getKeyFrame(timePressd, true);
        }
    }

//accessors and mutators for the abstract character's attributes
    public char getDir(){
        return dir;
    }
    public void setDir(char dir){
        this.dir = dir;
    }

    public void setXPos(int x) {
        xPos = x;
        rect.setX(x);
    }

    public void setYPos(int y) {
        yPos = y;
        rect.setY(y);
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
    public int getHeight(){
        return wsHeight;
    }
    public int getWidth(){
        return wsWidth;
    }
    
    public void startShooting(){
        shooting=true;
    }
    public void stopShooting(){
        shooting=false;
    }
    public boolean isShooting(){
       return shooting;
    }
    public int getHealth() {
        return health;
    }
    public int getHpPots() {
        return hpPots;
    }
     public void setHealth(int health) {
        this.health = health;
    }
       public int getPotion(){
        return healthPotions;
    }

    public void setHpPots(int hpPots) {
        this.hpPots = hpPots;
    }
    
    //methods to move the abstract character
    public void moveU() {
        yPos += 3;
        rect.setY(yPos);
    }

    public void moveD() {
        yPos -= 3;
        rect.setY(yPos);
    }

    public void moveL() {
        xPos -= 3;
        rect.setX(xPos);
    }

    public void moveR() {
        xPos += 3;
        rect.setX(xPos);
    }
    
    //returns a rectangle representing the abstract character (used for collision)
    public Rectangle getRectangle() {
        return rect;
    }
    //increments healthPotions attribute
    public void addPotion(){
        healthPotions ++;
    }
    //decrements healthPotion attribute (if possible)
    public boolean removePotion(){
        if(healthPotions>=0){
            healthPotions --;
            return true;
        } else {
            return false;
        }
    }
    //checks if the abstract character has died
    public boolean checkHP() {
        if (this.getHealth() <= 0) {
            //set laederBoard screen
            return true;
        }
        return false;
    }
}
