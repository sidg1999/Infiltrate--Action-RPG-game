/*Sid Gupta, Ankur Mishra, Muhammad Hassan
January 19th 2017
The level 7 class
This class implements all of the attributes of level 7 in the game. It adds all the enemies, chests, and doors of the level, setting their designated x,y position
according to the level. It handles the player interaction with the level, such as interacting with doors, chests, and character-enemy combat. Is a subclass of the
AbstractLevel class */

package com.mygdx.implementtmx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;

public class Lvl7 extends AbstractLevel implements Screen {

    private Texture life = new Texture(Gdx.files.internal("life.png"));
    // Variables needed to implement a map
    private TiledMap map; // map itself
    private OrthogonalTiledMapRenderer renderer; // to render the map
    //objects used
    private Texture walkSheetUser;
    private Texture walkSheetEnemy;
    private TextureRegion userFrame;
    private OrthographicCamera cam;
    private SpriteBatch batch;
    private UserCharacter player;
    private Texture blank;
    /*enemy frames and enemy objects stored in arlists*/
    private ArrayList<EnemyCharacter> lowerEnemies;
    private ArrayList<EnemyCharacter> upperEnemies;
    private ArrayList<TextureRegion> upperEnemyFrames = new ArrayList<TextureRegion>();
    private ArrayList<TextureRegion> lowerEnemyFrames = new ArrayList<TextureRegion>();
    //rectangle array stores all collision objects
    private ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
    //arraylists holding the number of doors
    private ArrayList<Door> doors = new ArrayList<Door>();
    //used for switching betw lvls
    private MenuSwitch menuS = new MenuSwitch();
    //upstairs and downstairs rectangles in this level
    private Rectangle upStair;
    private Rectangle downStair;
    //varb telling us whether we are up or down
    private boolean up;
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private Texture bullet;
    private boolean switchScreen = false;

    public Lvl7(ImplementTmx g, MenuSwitch menuS) {
        // automatically called in the ImplementTmx file
        this.menuS = menuS;

    }

    @Override
    public void show() {
        //instantiates objects
        batch = new SpriteBatch();
        cam = new OrthographicCamera();
        map = new TiledMap();
        upperEnemies = new ArrayList<EnemyCharacter>();
        lowerEnemies = new ArrayList<EnemyCharacter>();

        //set up the tiledmap and store all the collision objects in the rects arraylist
        renderer = setTiledMap("Lvl7/Level7Detailed.tmx", cam, renderer, map, rects, "LowerCollisionLayer");
        map = renderer.getMap();
        blank = new Texture("blank.png");
        bullet = new Texture("bullet.png");

        // Load the UserCharacter & EnemyCharacter sprite sheets as a Texture. looks in assets folder
        walkSheetUser = new Texture(Gdx.files.internal("FBI_walk_cycle.png"));
        walkSheetEnemy = new Texture(Gdx.files.internal("enemyWalk.png"));
        //instantiate a new UserCharacter object by passing through the spritesheet.
        player = new UserCharacter(new Texture(Gdx.files.internal("FBI_walk_cycle.png")), new Texture(Gdx.files.internal("fbi_shoot.png")), 9, 4);

        // instantiate a number of enemies
        //in level 7, there are upper and lower floor enemies. instantiate both types
        for (int i = 0; i < 3; i++) {
            upperEnemies.add(new EnemyCharacter(walkSheetEnemy, 3, 4));
        }
        for (int i = 0; i < 4; i++) {
            lowerEnemies.add(new EnemyCharacter(walkSheetEnemy, 3, 4));
        }

        //gets the door objects from this tiledmap. there is one door in this map*/
        addDoor("doorLv3", doors, map, "Lv3", 670, 969);
        addDoor("doorLv2", doors, map, "Lv2", 1374, 930);
        //fixes the position of the doors
        doors.get(0).setY(doors.get(0).getY() + 100);
        doors.get(1).setY(doors.get(0).getY() + 150);

        //the stairs that leads to the top floor is represented by the rectangle upStairs.
        //the stairs that leads to the bottom floor is represented by the rectangle downStairs.
        upStair = addStair("upStair", upStair);
        downStair = addStair("downStair", downStair);

        //initializes the values of the character & enemy object 
        //sets initial x,y pos to starting position
        player.setXPos(menuS.getX());
        player.setYPos(menuS.getY());

        // sets initial position x,y for enemies
        //upper enemies
        upperEnemies.get(0).setXPos(1029);
        upperEnemies.get(0).setYPos(1170);
        upperEnemyFrames.add(0, upperEnemies.get(0).getIdle('L'));
        upperEnemies.get(1).setXPos(684);
        upperEnemies.get(1).setYPos(1194);
        upperEnemyFrames.add(1, upperEnemies.get(1).getIdle('L'));
        upperEnemies.get(2).setXPos(693);
        upperEnemies.get(2).setYPos(1392);
        upperEnemyFrames.add(2, upperEnemies.get(2).getIdle('L'));
        //lower enemies
        lowerEnemies.get(0).setXPos(735);
        lowerEnemies.get(0).setYPos(1293);
        lowerEnemyFrames.add(0, lowerEnemies.get(0).getIdle('L'));
        lowerEnemies.get(1).setXPos(879);
        lowerEnemies.get(1).setYPos(1263);
        lowerEnemyFrames.add(1, lowerEnemies.get(1).getIdle('L'));
        lowerEnemies.get(2).setXPos(955);
        lowerEnemies.get(2).setYPos(1041);
        lowerEnemyFrames.add(2, lowerEnemies.get(2).getIdle('L'));
        lowerEnemies.get(3).setXPos(963);
        lowerEnemies.get(3).setYPos(1383);
        lowerEnemyFrames.add(3, lowerEnemies.get(3).getIdle('L'));

        //check if we should load the upper level first
        if (menuS.getUpper() == true) {
            //yes we should.
            //display the upper level and all of it's tiles
            map.getLayers().get("upperLevel").setVisible(true);
            map.getLayers().get("upperLevelStairs").setVisible(true);

            //clear all current collision objects
            rects.clear();
            //add all the upper level collision objects
            addCollisionObjects(rects, "UpperCollisionLayer", map);
            map.getLayers().get("downStair").setVisible(true);

            menuS.setUpper(false);
            up = true;
        } else {
            up = false;
        }

    }

    @Override
    public void render(float delta) {
        // clears the screen every frame
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //renders tiledMap, updates cam info
        updateCam(cam, batch);
        renderTM(renderer, cam);
        //checks for user input
        //if in upper level
        if(up==true){
            userFrame = getUserInput(player, cam, map, rects,bullets,upperEnemies,menuS);
        }else{
            userFrame = getUserInput(player, cam, map, rects,bullets,lowerEnemies,menuS);
        }
        //check if the character walks through a door
        checkWalkThroughDoor(doors, menuS);
        
        //move the bullets in the map, depending on the direction that they are moving
        for (int i = 0; i < bullets.size(); i++) {
            moveBullets(bullets.get(i));
        }

        
        //checks if the enemy has collided with the player, if so, the player takes damage
        if(up==true){
        for (int i = 0; i < upperEnemies.size(); i++) {
            upperEnemyFrames.add(checkEnemyMovement(upperEnemies.get(i),player,rects));
            if (upperEnemies.get(i).checkBoundries(50, player)) {
                 player.setHealth(player.getHealth() - 1);
                switchScreen=player.checkHP();
            }
        }
        }else{
          for (int i = 0; i < lowerEnemies.size(); i++) {
            lowerEnemyFrames.add(checkEnemyMovement(lowerEnemies.get(i),player,rects));
            if (lowerEnemies.get(i).checkBoundries(50, player)) {
                player.setHealth(player.getHealth() - 1);
                switchScreen=player.checkHP();
            }
        }  
        }
        
        
   
        
        if(switchScreen == true){
            menuS.setSwitch("Leaderboards");
            //dispose the current map and set the menuSwitch to the leaderboarsds screen
            //this will run in the main implementTMX file and switch the level
            dispose();
        }
        
    

        checkStairCollide();

        //starts drawing user and enemy (relative to where the camera is)
        batch.begin();
        batch.draw(userFrame, player.getXPos(), player.getYPos()); // Draw current character frame at the players x,y position
        //draws the health bar for the user
        drawHealthBar(batch, player, blank);
        //draws the enemy health bar and enemies
        //upper level
        if (up == true) {
            // Enemies health bar color
            for (int i = 0; i < upperEnemies.size(); i++) {
                drawHealthBar(batch, upperEnemies.get(i), blank);
            }
            batch.setColor(Color.WHITE);
            //draws the enemies
            //if they are on the bottom floor, draw bottom enemies
            for (int i = 0; i < upperEnemies.size(); i++) {
                batch.draw(upperEnemyFrames.get(i), upperEnemies.get(i).getXPos(), upperEnemies.get(i).getYPos());
            }
        } else {
            //lower level
            for (int i = 0; i < lowerEnemies.size(); i++) {
                drawHealthBar(batch, lowerEnemies.get(i), blank);
            }
            batch.setColor(Color.WHITE);
            System.out.println(lowerEnemies.size());
            //draws the enemies
            //if they are on the bottom floor, draw bottom enemies
            for (int i = 0; i < lowerEnemies.size(); i++) {
                batch.draw(lowerEnemyFrames.get(i), lowerEnemies.get(i).getXPos(), lowerEnemies.get(i).getYPos());
            }
        }
        //draw all the bullets
        for (int i = 0; i < bullets.size(); i++) {
            //shift the bullets right and up 11 units, just so it looks nicer
            batch.draw(bullet, bullets.get(i).getX() + 18, bullets.get(i).getY() + 18, 20, 20);
        }

        drawHealth(batch); // keep calling draw health so the user knows how many potions they have
        batch.end();
        footSteps(); // keep calling this to play sound when user moves
        usePotion(); // keep calling this to see if user used a potion
        
        //if the bullets arraylist exceeds 50, then clear it to save space
        if (bullets.size() > 50) {
            bullets.clear();
        }
        

    }

    @Override
    public void resize(int width, int height) {
        //Set the view of the camera to the size of the map
        cam.viewportWidth = width;
        cam.viewportHeight = height;
        //translate the camera so that it follows the character
        cam.position.x = player.getXPos();
        cam.position.y = player.getYPos();
        cam.update();
    }

    @Override
    public void dispose() {
        // Garbages the map file when closed and the Sprite batches the renderer holds.
        map.dispose();
        renderer.dispose();
    }

    public Rectangle addStair(String layerID, Rectangle stair) {
        MapObjects doorObjects = map.getLayers().get(layerID).getObjects();
        Array<RectangleMapObject> rectMP = doorObjects.getByType(RectangleMapObject.class);
        RectangleMapObject rectObject = rectMP.get(0);
        stair = rectObject.getRectangle();
        return stair;
    }

    public void checkStairCollide() {
        //if the player is in the bottom level
        //check if they are entering the stair that goes up
        if (!map.getLayers().get("upperLevel").isVisible()) {
            if (upStair.overlaps(player.getRectangle())) {
                //display the upper level and all of it's tiles
                map.getLayers().get("upperLevel").setVisible(true);
                map.getLayers().get("upperLevelStairs").setVisible(true);

                //clear all current collision objects
                rects.clear();
                //add all the upper level collision objects
                addCollisionObjects(rects, "UpperCollisionLayer", map);
                map.getLayers().get("downStair").setVisible(true);
                up=true;
            }
        } else //otherwise they are in the top level. check if they are entering the stair that goes down
        {
            if (downStair.overlaps(player.getRectangle())) {
                //remove display for the upper level and all of it's tiles
                map.getLayers().get("upperLevel").setVisible(false);
                map.getLayers().get("upperLevelStairs").setVisible(false);

                //clear all current collision objects
                rects.clear();
                //add all the lower level collision objects
                addCollisionObjects(rects, "LowerCollisionLayer", map);
                map.getLayers().get("upStair").setVisible(true);
                up=false;
            }
        }
    }
}
