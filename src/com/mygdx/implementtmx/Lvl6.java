/*Sid Gupta, Ankur Mishra, Muhammad Hassan
January 19th 2017
The level 6 class
This class implements all of the attributes of level 6 in the game. It adds all the enemies, chests, and doors of the level, setting their designated x,y position
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
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

public class Lvl6 extends AbstractLevel implements Screen {

    //variables used to create text on screen
    long millis;
    private BitmapFont chestFont;
    private String chestText;
    private Texture life = new Texture(Gdx.files.internal("life.png"));
// Variables needed to implement a map
    private TiledMap map; // map itself
    private OrthogonalTiledMapRenderer renderer; // to render the map
    //objects used
    private Texture walkSheetUser;
    private Texture walkSheetEnemy;
    private Texture blank;
    private Texture bullet;

    private TextureRegion userFrame;
    private OrthographicCamera cam;
    private SpriteBatch batch;
    private UserCharacter player;
    /*enemy frames and enemy objects stored in arlists*/
    private ArrayList<EnemyCharacter> enemies;
    private ArrayList<TextureRegion> enemyFrames = new ArrayList<TextureRegion>();
    //rectangle array stores all collision objects
    private ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
    //arraylists holding the number of doors
    private ArrayList<Door> doors = new ArrayList<Door>();
    //used for switching betw lvls
    private MenuSwitch menuS = new MenuSwitch();
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private boolean switchScreen = false;

    public Lvl6(ImplementTmx g, MenuSwitch menuS) {
        // automatically called in the ImplementTmx file
        this.menuS = menuS;
        //resets player x,y coordinates
    }

    @Override
    public void show() {
        //instantiates objects
        batch = new SpriteBatch();
        cam = new OrthographicCamera();
        map = new TiledMap();
        enemies = new ArrayList<EnemyCharacter>();
        //set up the tiledmap and store all the collision objects in the rects arraylist
        renderer = setTiledMap("Lvl6/Lvl6Detailed.tmx", cam, renderer, map, rects, "collisionObjects");
        map = renderer.getMap();
        blank = new Texture("blank.png");
        bullet = new Texture("bullet.png");

        // Load the UserCharacter & EnemyCharacter sprite sheets as a Texture. looks in assets folder
        walkSheetUser = new Texture(Gdx.files.internal("FBI_walk_cycle.png"));
        walkSheetEnemy = new Texture(Gdx.files.internal("enemyWalk.png"));
        //instantiate a new UserCharacter object by passing through the spritesheet.
        player = new UserCharacter(new Texture(Gdx.files.internal("FBI_walk_cycle.png")), new Texture(Gdx.files.internal("fbi_shoot.png")), 9, 4);

        // instantiate a number of enemies, and give them each a enemyFrame in the enemyFrame arraylist
        for (int i = 0; i <= 10; i++) {
            enemies.add(new EnemyCharacter(walkSheetEnemy, 3, 4));
        }

        
        
        //gets the door objects from this tiledmap. there are two doors in level 6 map, one leading back to lv3,
        //and one leading to the finish
        addDoor("doorLv3", doors, map, "Lv3", 740, 1389);
        addDoor("doorFinish", doors, map, "Finish", 998, 1057);

        //adjust position of doors
        doors.get(0).setY(doors.get(0).getY() - 340);
        doors.get(1).setY(doors.get(1).getY() + 1000);
        /*these door objects returned a false x,y coordinate from the tiledmap for some reason. initialize to them the correct values*/

        //initializes the values of the character's x,y positions based off the MenuSwitch object's
        //x,y position. This is determined by what door the character came from
        player.setXPos(menuS.getX());
        player.setYPos(menuS.getY());

        // sets initial position x,y for enemies
        enemies.get(0).setXPos(774);
        enemies.get(0).setYPos(229);
        enemyFrames.add(0, enemies.get(0).getIdle('L'));
        enemies.get(1).setXPos(1011);
        enemies.get(1).setYPos(535);
        enemyFrames.add(1, enemies.get(1).getIdle('L'));
        enemies.get(2).setXPos(753);
        enemies.get(2).setYPos(517);
        enemyFrames.add(2, enemies.get(2).getIdle('L'));
        enemies.get(3).setXPos(777);
        enemies.get(3).setYPos(685);
        enemyFrames.add(3, enemies.get(3).getIdle('L'));
        enemies.get(4).setXPos(945);
        enemies.get(4).setYPos(862);
        enemyFrames.add(4, enemies.get(4).getIdle('L'));
        enemies.get(5).setXPos(675);
        enemies.get(5).setYPos(997);
        enemyFrames.add(5, enemies.get(5).getIdle('L'));
        enemies.get(6).setXPos(561);
        enemies.get(6).setYPos(1159);
        enemyFrames.add(6, enemies.get(6).getIdle('L'));
        enemies.get(7).setXPos(513);
        enemies.get(7).setYPos(1417);
        enemyFrames.add(7, enemies.get(7).getIdle('L'));
        enemies.get(8).setXPos(666);
        enemies.get(8).setYPos(1405);
        enemyFrames.add(8, enemies.get(8).getIdle('L'));
        enemies.get(9).setXPos(825);
        enemies.get(9).setYPos(1411);
        enemyFrames.add(9, enemies.get(9).getIdle('L'));

    }

    @Override
    public void render(float delta) {
        // clears the screen every frame
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //renders tiledMap, updates cam info
        updateCam(cam, batch);
        renderTM(renderer, cam);
        //checks for user input
        userFrame = getUserInput(player, cam, map, rects, bullets,enemies,menuS);

        //check if the character walks through a door
        checkWalkThroughDoor(doors, menuS);

        /*gets enemy frames*/
        //clears previous frames & adds new ones
        enemyFrames.clear();
        for (int i = 0; i < enemies.size(); i++) {
            enemyFrames.add(checkEnemyMovement(enemies.get(i), player, rects));
        }
        //move the bullets in the map, depending on the direction that they are moving
        for (int i = 0; i < bullets.size(); i++) {
            moveBullets(bullets.get(i));
        }

        // check if user is within chest zone
        boolean status = checkInChestZone();

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
        
        //starts drawing user and enemy (relative to where the camera is)
        batch.begin();
        batch.draw(userFrame, player.getXPos(), player.getYPos()); // Draw current character frame at the players x,y position
        //draws the health bar for the user
        drawHealthBar(batch, player, blank);
        //draws the health bar for the enemies
        // Enemies health bar color
        for (int i = 0; i < enemies.size(); i++) {
            drawHealthBar(batch, enemies.get(i), blank);
        }
        batch.setColor(Color.WHITE);
        //draws the enemies
        for (int i = 0; i < enemies.size(); i++) {
            batch.draw(enemyFrames.get(i), enemies.get(i).getXPos(), enemies.get(i).getYPos());
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
        // Set the view of the camera to the size of the map
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

}
