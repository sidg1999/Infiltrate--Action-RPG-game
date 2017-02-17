/*
 * Interface that contains all the methods that the levels have in common.
 * Author: Sid Gupta 
 */
package com.mygdx.implementtmx;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

public interface LevelInterface {

    //method which sets up the tiled map given a filepath of that .tmx file
    public OrthogonalTiledMapRenderer setTiledMap(String filePath, OrthographicCamera cam, OrthogonalTiledMapRenderer renderer, TiledMap map, ArrayList<Rectangle> rects,String objectLayerID);
    
    //method which gets user input
    //public TextureRegion getUserInput(UserCharacter player, OrthographicCamera cam, TiledMap map, ArrayList<Rectangle> rects);
    public void checkJustPressd(OrthographicCamera cam, ArrayList<Rectangle> rects);
    public void checkKeepPress(OrthographicCamera cam, ArrayList<Rectangle> rects);
    
    //method that detects if character hits a wall
    public boolean collisionDetection(ArrayList<Rectangle> rects, AbstractCharacter player);
    
    //stores all the doors in the tiledmaps
    public void addDoor(String levelID, ArrayList<Door> doors, TiledMap map, String lv, int startX, int startY);
    
    //checks if the user goes through a door
    public void checkWalkThroughDoor(ArrayList<Door> doors, MenuSwitch menuS);
    
    //methods to update the camera and render the tiledmap each frame
    public void updateCam(OrthographicCamera cam, SpriteBatch batch);
    public void renderTM(OrthogonalTiledMapRenderer renderer, OrthographicCamera cam);
    
}
