/*Sid Gupta, Ankur Mishra, Muhammad Hassan
January 19th 2017
The level 1 class
This class implements all of the attributes of level 1 in the game. It adds all the enemies, chests, and doors of the level, setting their designated x,y position
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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

public class Lvl1 extends AbstractLevel implements Screen, LevelInterface {
    //variables used to create text on screen
    long millis;
    private BitmapFont chestFont;
    private String chestText;
    private Texture life = new Texture(Gdx.files.internal("life.png"));
    private boolean switchScreen;
    // Variables needed to implement a map
    private TiledMap map; // map itself
    private OrthogonalTiledMapRenderer renderer; // to render the map
    
    //objects used
    private TextureRegion userFrame;
    private OrthographicCamera cam;
    private SpriteBatch batch;
    private UserCharacter player;
    private Texture blank;
    private Texture bullet;
    
    /*enemy frames and enemy objects stored in arlists*/
    ArrayList<EnemyCharacter> enemies = new ArrayList<EnemyCharacter>();
    ArrayList<TextureRegion> enemyFrames = new ArrayList<TextureRegion>();
    
    //rectangle array stores all collisions
    private ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
    //arraylists holding the number of doors
    private ArrayList<Door> doors = new ArrayList<Door>();
    //used for switching betw lvls
    private MenuSwitch menuS = new MenuSwitch();
    //bullets arraylist for combat
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    public Lvl1(ImplementTmx g, MenuSwitch menuS) {
        // automatically called in the ImplementTmx file
        this.menuS = menuS;
    }

    @Override
    public void show() {
        //objects to show text on screen
        chestFont = new BitmapFont(Gdx.files.internal("chestText.fnt"));
        chestText = "You have acquired a health potion!";
        /*instantiates objects*/
        batch = new SpriteBatch();
        cam = new OrthographicCamera();
        map = new TiledMap();
        blank = new Texture("blank.png");
        bullet = new Texture("bullet.png");

        //sets up the tiledMap
        renderer = setTiledMap("Lvl1/Lvl1Detailed.tmx", cam, renderer, map, rects, "collisionObjects");
        map = renderer.getMap();
        
        //instantiate a new UserCharacter & Enemy object by passing through the spritesheets in the assets folder.
        player = new UserCharacter(new Texture(Gdx.files.internal("FBI_walk_cycle.png")),new Texture(Gdx.files.internal("fbi_shoot.png")), 9, 4);
        //there is only one enemy character in this level
        enemies.add(new EnemyCharacter(new Texture(Gdx.files.internal("enemyWalk.png")), 3, 4));
        //create a frame object for the single enemy
        enemyFrames.add(new TextureRegion());
        
        //gets the door objects from this tiledmap. there is only one, and it leads to Lv2, 
        //having a starting position of 910, 749
        addDoor("doorLv2", doors, map, "Lv2", 910, 749);

        //initializes the values of the character's x,y positions based off the MenuSwitch object's
        //x,y position. This is determined by what door the character came from
        player.setXPos(menuS.getX());
        player.setYPos(menuS.getY());

        //set initial position of the enemy
        enemies.get(0).setXPos(600);
        enemies.get(0).setYPos(1380);
        
        //add the chests of the map (there are two)
        addChest("Chest1", map);
        
    }

    @Override
    public void render(float delta) {
        //clears the screen every frame
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //renders tiledMap, updates cam info
        updateCam(cam, batch);
        renderTM(renderer, cam);

        /*gets user frames*/
        //checks for user input
        userFrame = getUserInput(player, cam, map, rects,bullets,enemies,menuS);

        //check if the character walks through a door
        checkWalkThroughDoor(doors, menuS);
        
        //move the bullets in the map, depending on the direction that they are moving
        for (int i = 0; i < bullets.size(); i++) {
            moveBullets(bullets.get(i));
        }
        
        /*gets enemy frames*/
        //clears previous frames & adds new ones
        enemyFrames.clear();
        
        //checks if the enemy has collided with the player, if so, the player takes damage
        for (int i = 0; i < enemies.size(); i++) {
            enemyFrames.add(checkEnemyMovement(enemies.get(i),player,rects));
            if (enemies.get(i).checkBoundries(50, player)) {
                player.setHealth(player.getHealth() - 1);
                switchScreen = player.checkHP();
            }
        }
        
        if(switchScreen == true){
            menuS.setSwitch("Leaderboards");
            //dispose the current map and set the menuSwitch to the leaderboarsds screen
            //this will run in the main implementTMX file and switch the level
            dispose();
        }
        
        // check if user is within chest zone
        boolean status = checkInChestZone();
        
        //starts drawing user and enemy (relative to where the camera is)
        batch.begin();
        batch.draw(userFrame, player.getXPos(), player.getYPos()); // Draw current character frame at the players x,y position
        
        //draws the health bar for the user
        drawHealthBar(batch,player,blank);
        //draws the health bar for the enemies
        // Enemies health bar color
        for (int i = 0; i < enemies.size(); i++) {
            drawHealthBar(batch,enemies.get(i),blank);
        }
        batch.setColor(Color.WHITE);
        
        //draw all the bullets
        for (int i = 0; i < bullets.size(); i++) {
            //shift the bullets right and up 11 units, just so it looks nicer
            batch.draw(bullet,bullets.get(i).getX()+18,bullets.get(i).getY()+18,20,20);
        }

        //draws the enemy
        for (int i = 0; i < enemies.size(); i++) {
            batch.draw(enemyFrames.get(i), enemies.get(i).getXPos(), enemies.get(i).getYPos());
        }
        
        // if the chest was just opened then 
        if (status == true) {
            millis = System.currentTimeMillis(); // begin timer
            status = false; // and set status to false to avoid re running timer
        }
        if ((System.currentTimeMillis() - millis < 3500)) { // display text for 3 seconds
            //draw text on screen when user opens a chest! 
            chestFont.setColor(Color.RED);
            chestFont.draw(batch, chestText, player.getXPos() + 50, player.getYPos() + 60);
        }
        drawHealth(batch); // keep calling draw health so the user knows how many potions they have
        batch.end();
        footSteps(); // keep calling this to play sound when user moves
        usePotion(); // keep calling this to see if user used a potion
        
        //if the bullets arraylist exceeds 50, then clear it to save space
        if(bullets.size()>50){
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
        blank.dispose();
    }

}
