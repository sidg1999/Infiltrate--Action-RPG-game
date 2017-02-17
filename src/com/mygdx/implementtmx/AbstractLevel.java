/*Sid Gupta, Ankur Mishra, Muhammad Hassan
January 19th 2017
The abstract level class. This class sets properties for all the levels in the game.
 */
package com.mygdx.implementtmx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;

abstract public class AbstractLevel implements Screen, LevelInterface {

    //objects used for user input
    private TextureRegion currentFrame;
    private UserCharacter player;
    // variables for tracking elapsed time for the animation
    long stateTL;
    long stateTR;
    long stateTU;
    long stateTD;
    long startWalkT;
    long startShootT;
    long endShootT;
    //time it takes for shoot 
    long shootTTime = 300;
    //variables used for moving char
    boolean isKeyD = false;
    char keyD = 'U';
    // an array list that holds all the chests in the game
    ArrayList<Chest> chests = new ArrayList<Chest>();

    //method which sets up the tiled map given a filepath of that .tmx file
    public OrthogonalTiledMapRenderer setTiledMap(String filePath, OrthographicCamera cam, OrthogonalTiledMapRenderer renderer, TiledMap map, ArrayList<Rectangle> rects, String objectLayerID) {
        // Instantiate a map loader to find and load the tmx into the screen
        TmxMapLoader loader = new TmxMapLoader();
        // Use it to actually load the map (looks in assets folder)
        map = loader.load(filePath);
        // sets up the tiledmap
        // Render the map
        renderer = new OrthogonalTiledMapRenderer(map);
        //this ensures that the camera lets us view only a 800x480 window of our game
        cam.setToOrtho(false, 750, 500);

        /**
         * gets the collision layer and stores all the collision objects in the
         * 'rects' arraylist
         */
        addCollisionObjects(rects, objectLayerID, map);

        //returns the renderer to be used
        return renderer;
    }

    public void addCollisionObjects(ArrayList<Rectangle> rects, String objectLayerID, TiledMap map) {
        //the object layer is the  layer named "collisionObjects"
        //map.getLayers() returns a list of all the layers in the map. What we're doing here
        //is getting the 'Walls' layer and storing that in the collisionLayer variable
        //stores all the mapObjects (the little clear rectangles) in the objects variable
        MapObjects collisionObjects = map.getLayers().get(objectLayerID).getObjects();
        //converts all of these mapObjects to rectangles, and stores them in the rectArlist arraylist
        Array<RectangleMapObject> rectArlist = collisionObjects.getByType(RectangleMapObject.class);
        //loop each rectangle in the rectArlist
        for (int i = 0; i < rectArlist.size; i++) {
            //Let the currect rectangleobject in the arlist be represented by rectObj
            RectangleMapObject rectObject = rectArlist.get(i);
            //Let this rectangleMapObject be represented by a rectangle object
            Rectangle rectangle = rectObject.getRectangle();
            rects.add(rectangle);
        }
    }

    /*all user input stuff*/
    public TextureRegion getUserInput(UserCharacter player, OrthographicCamera cam, TiledMap map, ArrayList<Rectangle> rects, ArrayList<Bullet> bullets, ArrayList<EnemyCharacter> enemies,MenuSwitch menuS) {
        this.player = player;
        //check if the user is currently shooting
        if (player.isShooting()) {
            //theyre shooting
            //is the 'd' key being pressed?
            if (!Gdx.input.isKeyPressed(Keys.D)) {
                //they're not shooting
                player.stopShooting();
            } else {
                //otherwise
                /*  ENEMY HIT DETECTION */
                //loop through all enemies in the level
                for (int i = 0; i < enemies.size(); i++) {
                    //if the user is facing up
                    if (player.getDir() == 'U') {
                        //check if they are within the range of the enemies x position
                        if (player.getXPos() >= (enemies.get(i).getXPos() - 2) && player.getXPos() <= (enemies.get(i).getXPos() + 2)) {
                            // lower the health of the enemy
                            enemies.get(i).setHealth(enemies.get(i).getHealth() - 50);
                            // if dead then remove
                            if (enemies.get(i).getHealth() <= 0) {
                                enemies.remove(i);
                                //increase the user's score
                                menuS.scoreIncrKill();
                            }
                        }
                    } else if (player.getDir() == 'D') {//down
                        //check if they are within the range of the enemies x position
                        if (player.getXPos() >= (enemies.get(i).getXPos() - 2) && player.getXPos() <= (enemies.get(i).getXPos() + 2)) {
                            // lower the health of the enemy
                            enemies.get(i).setHealth(enemies.get(i).getHealth() - 50);
                            // if dead then remove
                            if (enemies.get(i).getHealth() <= 0) {
                                enemies.remove(i);
                                //increase the user's score
                                menuS.scoreIncrKill();
                            }
                        }

                    } else if (player.getDir() == 'L') {//left
                        //check if they are within the range of the enemies y position
                        if (player.getYPos() >= (enemies.get(i).getYPos() - 4) && player.getYPos() <= (enemies.get(i).getYPos() + 4)) {
                            // lower the health of the enemy
                            enemies.get(i).setHealth(enemies.get(i).getHealth() - 50);
                            // if dead then remove
                            if (enemies.get(i).getHealth() <= 0) {
                                enemies.remove(i);
                                //increase the user's score
                                menuS.scoreIncrKill();
                            }
                        }

                    } else//right
                    //check if they are within the range of the enemies y position
                    if (player.getYPos() >= (enemies.get(i).getYPos() - 4) && player.getYPos() <= (enemies.get(i).getYPos() + 4)) {
                        // lower the health of the enemy
                        enemies.get(i).setHealth(enemies.get(i).getHealth() - 50);
                        // if dead then remove
                        if (enemies.get(i).getHealth() <= 0) {
                            enemies.remove(i);
                            //increase the user's score
                            menuS.scoreIncrKill();
                        }
                    }
                }

                //return the current shooting frame
                return player.getShootFrame(player.getDir());
            }
        } else //check if the user JUST pressed the 'd' key
        {
            if (Gdx.input.isKeyPressed(Keys.D)) {
                //they want to start shooting. 
                //create a new bullet object at the player's current x,y position
                bullets.add(new Bullet(player.getXPos(), player.getYPos(), player.getDir()));
                //the player is starting to shoot
                player.startShooting();
                return player.getShootFrame(player.getDir());
            } else {

                //check if the user JUST pressed a key to walk. if they did, start running that direction animation. 
                //ex; if the user JUST pressed left, start running left walk animation
                //if a key is already down, then dont check if a user just pressed a key. this prevents the user from preventing multiple keystrokes 
                if (isKeyD == false) {
                    checkJustPressd(cam, rects);
                }
                //if a key is being held down
                if (isKeyD == true) {
                    //the user is continuing to press a key. keep scrolling through their spritesheet direction
                    checkKeepPress(cam, rects);
                } else {
                    //the user is NOT continuing to press a key.
                    //display their corresponding idle sprite
                    currentFrame = player.getIdle(keyD);
                }
                player.setDir(keyD);
            }
        }
        return currentFrame;
    }

    public void checkJustPressd(OrthographicCamera cam, ArrayList<Rectangle> rects) {
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            //so the program scrolls through the spritesheet and displays the image based off how long the user has pressed the button. 
            //initialize a startT to figure out when the user STARTED to press the button. this is used to figure out how long theyv pressed it for
            startWalkT = System.currentTimeMillis();
            stateTL = System.currentTimeMillis();
            //set keyD so we can tell if the user continues to hold down the key
            keyD = 'L';
            //a key is down
            isKeyD = true;
            //move player left
            player.moveL();
            //check if there is collision
            if (collisionDetection(rects, player)) {
                //if there is, move them back in the opposite direction
                player.moveR();
                //make isKeyD false so the currentFrame will be the idle frame
                isKeyD = false;
            } else {
                //otherwise, translate the camera and get the according frame of the character
                cam.translate(-3, 0);
                //get the according frame of the character (it should be the first one when they JUST pressed it)
                currentFrame = player.getWalkFrame(stateTL, startWalkT, keyD);
            }

        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            startWalkT = System.currentTimeMillis();
            stateTR = System.currentTimeMillis();
            keyD = 'R';
            isKeyD = true;
            player.moveR();
            if (collisionDetection(rects, player)) {
                player.moveL();
                isKeyD = false;
            } else {
                cam.translate(3, 0);
                currentFrame = player.getWalkFrame(stateTR, startWalkT, keyD);
            }
        } else if (Gdx.input.isKeyPressed(Keys.UP)) {
            startWalkT = System.currentTimeMillis();
            stateTU = System.currentTimeMillis();
            keyD = 'U';
            isKeyD = true;
            player.moveU();
            if (collisionDetection(rects, player)) {
                player.moveD();
                isKeyD = false;

            } else {
                cam.translate(0, 3);
                currentFrame = player.getWalkFrame(stateTU, startWalkT, keyD);
            }
        } else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            startWalkT = System.currentTimeMillis();
            stateTD = System.currentTimeMillis();
            keyD = 'D';
            isKeyD = true;
            player.moveD();
            if (collisionDetection(rects, player)) {
                player.moveU();
                isKeyD = false;
            } else {
                cam.translate(0, -3);
                currentFrame = player.getWalkFrame(stateTD, startWalkT, keyD);
            }
        }
    }

    public void checkKeepPress(OrthographicCamera cam, ArrayList<Rectangle> rects) {
        //check what key the user pressed, and if that key is equal to the one that they pressed at the start
        if (Gdx.input.isKeyPressed(Keys.LEFT) && keyD == 'L') {
            //the start time is initialized when the user first JUST pressed the button. So now, they continue to press it. Launch the corresponding frame
            //for how long theyv pressed it
            stateTL = System.currentTimeMillis();
            currentFrame = player.getWalkFrame(stateTL, startWalkT, keyD);
            //move player left
            player.moveL();
            if (collisionDetection(rects, player)) {
                player.moveR();
                isKeyD = false;
            } else {
                cam.translate(-3, 0);
                currentFrame = player.getWalkFrame(stateTL, startWalkT, keyD);
            }

        } else if (Gdx.input.isKeyPressed(Keys.RIGHT) && keyD == 'R') {
            stateTR = System.currentTimeMillis();
            currentFrame = player.getWalkFrame(stateTR, startWalkT, keyD);
            player.moveR();
            if (collisionDetection(rects, player)) {
                player.moveL();
                isKeyD = false;
            } else {
                cam.translate(3, 0);
                currentFrame = player.getWalkFrame(stateTR, startWalkT, keyD);
            }
        } else if (Gdx.input.isKeyPressed(Keys.UP) && keyD == 'U') {
            stateTU = System.currentTimeMillis();
            currentFrame = player.getWalkFrame(stateTU, startWalkT, keyD);
            player.moveU();
            if (collisionDetection(rects, player)) {
                player.moveD();
                isKeyD = false;
            } else {
                cam.translate(0, 3);
                currentFrame = player.getWalkFrame(stateTU, startWalkT, keyD);
            }
        } else if (Gdx.input.isKeyPressed(Keys.DOWN) && keyD == 'D') {
            stateTD = System.currentTimeMillis();
            currentFrame = player.getWalkFrame(stateTD, startWalkT, keyD);
            player.moveD();
            if (collisionDetection(rects, player)) {
                player.moveU();
                isKeyD = false;
            } else {
                cam.translate(0, -3);
                currentFrame = player.getWalkFrame(stateTD, startWalkT, keyD);
            }
        } else {
            //isKeyDown was once true, but the user didn't press any of the keys. Therefore, they let go. 
            isKeyD = false;
        }
    }

    /*collision detection code*/
    public boolean collisionDetection(ArrayList<Rectangle> rects, AbstractCharacter player) {
        //pRect represents player rectangle object
        Rectangle pRect = player.getRectangle();

        //loop each rectangle in the rect Arlist
        for (Rectangle rect : rects) {
            //all the rectangles are shifted 20 units. shift them, check for overlap, and shift em back
            rect.setX(rect.getX() - 20);
            //check if the player intersects with this rectangle object
            if (rect.overlaps(pRect)) {
                //lies in the rectangles x zone
                rect.setX(rect.getX() + 20);
                return true;
            } else {
                rect.setX(rect.getX() + 20);
            }
        }
        //otherwise its good
        return false;
    }

    //algorithm that stores the door leading to a level (in string name) to the level's doors arraylist
    public void addDoor(String layerID, ArrayList<Door> doors, TiledMap map, String level, int startX, int startY) {
        MapObjects doorObjects = map.getLayers().get(layerID).getObjects();
        Array<RectangleMapObject> rectMP = doorObjects.getByType(RectangleMapObject.class);
        System.out.println(rectMP.size);
        RectangleMapObject rectObject = rectMP.get(0);
        Rectangle rect = rectObject.getRectangle();
        Door addDoor = new Door((int) rect.getX(), (int) rect.getX(), (int) rect.getWidth(), (int) rect.getHeight(), level, startX, startY);
        doors.add(addDoor);
    }

    public void updateCam(OrthographicCamera cam, SpriteBatch batch) {
        //updates the camera every frame.
        cam.update();
        //essentially tells the batch spritebatch object to use the coordinate system updated by the camera.
        batch.setProjectionMatrix(cam.combined);
    }

    public void renderTM(OrthogonalTiledMapRenderer renderer, OrthographicCamera cam) {
        // Allows the tilemap to be rendered.
        renderer.setView(cam);
        renderer.render();
    }

    public void checkWalkThroughDoor(ArrayList<Door> doors, MenuSwitch menuS) {
        //if the player enters one of the doors in the level
        for (int i = 0; i < doors.size(); i++) {
            if (doors.get(i).getRect().overlaps(player.getRectangle())) {
                //dispose the current map and set the menuSwitch to the ID of the door ( the level it lead to)
                //this will run in the main implementTMX file and switch the level
                //check if the user walks through the final level door
                if(doors.get(i).getID().equals("Finish")){
                    menuS.scoreIncrComplete();
                }
                menuS.setSwitch(doors.get(i).getID());
                menuS.setX(doors.get(i).getStartX());
                menuS.setY(doors.get(i).getStartY());
                dispose();
            }
        }
    }

    //movement for the enemy. uses collision detection method
    public void moveEnemy(char dir, EnemyCharacter enemy, ArrayList<Rectangle> rects) {
        if (dir == 'U') {
            enemy.moveU();
            if (collisionDetection(rects, enemy)) {
                enemy.moveD();
            }
        } else if (dir == 'D') {
            enemy.moveD();
            if (collisionDetection(rects, enemy)) {
                enemy.moveU();
            }
        } else if (dir == 'L') {
            enemy.moveL();
            if (collisionDetection(rects, enemy)) {
                enemy.moveR();
            }
        } else {
            enemy.moveR();
            if (collisionDetection(rects, enemy)) {
                enemy.moveL();
            }
        }
    }

    public TextureRegion checkEnemyMovement(EnemyCharacter enemy, UserCharacter player, ArrayList<Rectangle> rects) {
        TextureRegion enemyFrame = new TextureRegion();
        char enemyDir;
        // check if the enemy within the boundries of the player
        if (enemy.checkBoundries(200, player)) {
            //check if the enemy has horizontal distance to travel in the first place
            if (enemy.isHorizDist(enemy, player)) {
                //if they do, walk horizontally. get the current enemy animation frame
                enemyFrame = enemy.walkToPlayerX(enemy, player);
                enemyDir = enemy.getDir();
                moveEnemy(enemyDir, enemy, rects);
                return enemyFrame;
            } else if (enemy.isVertDist(enemy, player)) {
                //if they don't, check if they have vertical distance to travel
                //if they do, walk vertically. get the current enemy animation frame
                enemyFrame = enemy.walkToPlayerY(enemy, player);
                enemyDir = enemy.getDir();
                moveEnemy(enemyDir, enemy, rects);
                return enemyFrame;
            } else {
                enemyFrame = enemy.getIdle('D');
                return enemyFrame;
            }
        } else {
            enemyFrame = enemy.getIdle('D');
            return enemyFrame;
        }
    }

    public void drawHealthBar(SpriteBatch batch, AbstractCharacter character, Texture blank) {
        // set the health bar colour according to HP: player
        if (character.getHealth() > 60) {
            batch.setColor(Color.GREEN);
        } else if (character.getHealth() > 20) {
            batch.setColor(Color.ORANGE);
        } else {
            batch.setColor(Color.RED);
        }
        // Draw a health bar above the player
        batch.draw(blank, character.getXPos() - 5, character.getYPos() + 60, character.getHealth(), 5);
    }

    public boolean checkInChestZone() {
        //if the player enters the chest zone
        for (int i = 0; i < chests.size(); i++) {
            if (chests.get(i).getRect().overlaps(player.getRectangle()) && (chests.get(i).Opened() == false)) { // is user in zone and hasnt opened a chest?
                if (Gdx.input.isKeyPressed(Keys.SPACE)) { // if conditions match and user opens chest then add health potion
                    player.addPotion();
                    chests.get(i).isOpened(); //chest has been opeend
                    return true;
                }
            }
        }
        return false;
    }
    private int addDist = 30;
    private Texture life = new Texture(Gdx.files.internal("life.png"));

    public void drawHealth(SpriteBatch batch) {
        for (int i = 0; i < player.getPotion(); i++) {
            batch.draw(life, player.getXPos() - 480 + (i * addDist), player.getYPos() - 250);
        }
    }

    int num = 1;
    int oldX = 212;
    int oldY = 1370;
    Sound footSteps = Gdx.audio.newSound(Gdx.files.internal("footSteps.mp3"));

    public void footSteps() {
        // if the x and y values of character are changing then play sound otherwise dont
        if ((player.getXPos() != oldX) || (player.getYPos() != oldY)) {
            oldX = player.getXPos();
            oldY = player.getYPos();
            if (num == 1) {
                footSteps.play();
                num++;
            }
        } else if ((player.getXPos() == oldX) || (player.getYPos() == oldY)) {
            footSteps.pause();
            num = 1;
        }

    }

    public void usePotion() {
        if ((player.getPotion() > 0) && (Gdx.input.isKeyJustPressed(Keys.Z))) {
            player.setHealth(100);
            player.removePotion();
        }

    }

    public void addChest(String chestID, TiledMap map) { // add a chest to the array list of chests
        MapObjects chestObjects = map.getLayers().get(chestID).getObjects();
        Array<RectangleMapObject> rectMP = chestObjects.getByType(RectangleMapObject.class);
        RectangleMapObject rectObject = rectMP.get(0);
        Rectangle rect = rectObject.getRectangle();
        Chest addChest = new Chest((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight(), chestID);
        chests.add(addChest);
    }

    //moves the bullets in their corresponding directions
    public void moveBullets(Bullet bullet) {
        if (bullet.getDir() == 'U') {
            bullet.setY(bullet.getY() + 8);
        } else if (bullet.getDir() == 'D') {
            bullet.setY(bullet.getY() - 8);
        } else if (bullet.getDir() == 'R') {
            bullet.setX(bullet.getX() + 8);
        } else {
            bullet.setX(bullet.getX() - 8);
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

}
