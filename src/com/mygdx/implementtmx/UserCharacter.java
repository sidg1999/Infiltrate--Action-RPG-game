/*
 * An extention of the AbstractCharacter, the user character that contains location and dimensions
 * also contains the Sprite sheets for animations and shooting animations
 * Author: Sig Gupta
 */
package com.mygdx.implementtmx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class UserCharacter extends AbstractCharacter{
    private Animation<TextureRegion> shootUAnim;
    private Animation<TextureRegion> shootDAnim;
    private Animation<TextureRegion> shootLAnim;
    private Animation<TextureRegion> shootRAnim;
    private TextureRegion[] shootU;
    private TextureRegion[] shootL;
    private TextureRegion[] shootD;
    private TextureRegion[] shootR;
    private int ssHeight, ssWidth;
    private int WALK_ROWS, WALK_COLS;
        
    public UserCharacter(Texture walkSheet, Texture shootSheet, int walkCols, int walkRows) {
        super(walkSheet,walkCols,walkRows);
        WALK_ROWS = walkRows;
        WALK_COLS = walkCols;
        initShootSprites(shootSheet);
        
    }
       public void initShootSprites(Texture shootSheet) {
        //get length, width of spritesheets
        ssHeight  = shootSheet.getHeight() / 4;
        ssWidth = shootSheet.getWidth() / 2;
        
        // Use the split utility method to create a 2D array of TextureRegions. This is 
        // possible because this sprite sheet contains frames of equal size and they are all aligned.
        TextureRegion[][] tmpShoot = TextureRegion.split(shootSheet, ssWidth, ssHeight);
        
        //each row in the shoot spritesheet represents a direction (same as walking animation)
        //each of these arrays points to its corresponding row in the tmp 2d array
        shootL = tmpShoot[0];
        shootU = tmpShoot[1];
        shootD = tmpShoot[2];
        shootR = tmpShoot[3];
        
        shootUAnim = new Animation<TextureRegion>(0.111f, shootU);
        shootLAnim = new Animation<TextureRegion>(0.111f, shootL);
        shootDAnim = new Animation<TextureRegion>(0.111f, shootD);
        shootRAnim = new Animation<TextureRegion>(0.111f, shootR);
    }
    public TextureRegion getShootFrame(char direction) {
        if (direction == 'L') {
            return shootL[0];
        } else if (direction == 'R') {
          return shootR[0];
        } else if (direction == 'U') {
            return shootU[0];
        } else {
            return shootD[0];
        }
    }
}

