package com.mygdx.implementtmx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;

public class ImplementTmx extends Game {
    private MenuSwitch menuS = new MenuSwitch();
    Sound main;
    
	@Override
	public void create () {
            menuS.setSwitch("Menu");
            //the start position of the character in level 1
            menuS.setX(170);
            menuS.setY(1380);
            setScreen(new Menu(this));
            main = Gdx.audio.newSound(Gdx.files.internal("ThemeSong.mp3"));
            main.play();
	}

	@Override
	public void render () {
            super.render();
            // code for menu
        
        if (menuS.getSwitch().equals("Lvl1")) { // if the screen is the screen of Level 1, then stop the music
            main.stop();//stop music
        }
        if (menuS.getSwitch().equals("Menu")) { // if its the menu screen then do tiis
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) { // is the left mouose button pressed?
                // now we add bounds for the cursor, if the mouse button is pressed and is in the nounfd of the button then bring up appropriate screen
                if ((Gdx.input.getX() >= 415) && (Gdx.input.getX() <= 415 + 197) && (Gdx.input.getY() >= 201 - 47) && (Gdx.input.getY() <= 201)) { // Main Screen / game screen
                    menuS.setSwitch("Lvl1"); // set type to the name of lvl1
                    setScreen(new Lvl1(this,menuS));// create a new lvl1 screen
                } else if ((Gdx.input.getX() >= 415) && (Gdx.input.getX() <= 415 + 195) && (Gdx.input.getY() >= 271 - 47) && (Gdx.input.getY() <= 275)) { // Instructions Screeen
                    menuS.setSwitch("Instructions");
                    setScreen(new Instructions(this));
                } else if ((Gdx.input.getX() >= 415) && (Gdx.input.getX() <= 415 + 191) && (Gdx.input.getY() >= 341 - 47) && (Gdx.input.getY() <= 341)) { // Credits Screen
                    menuS.setSwitch("Credits");
                    setScreen(new Credits(this));
                } else if ((Gdx.input.getX() >= 415) && (Gdx.input.getX() <= 415 + 197) && (Gdx.input.getY() >= 411 - 47) && (Gdx.input.getY() <= 411)) { // exit
                    Gdx.app.exit(); // exit the app if the exit button is clicked
                }
            }
        }
        if ((menuS.getSwitch().equals("Instructions")) || (menuS.getSwitch().equals("Credits"))) { // code for clicking the back button on credits and instructions screen
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) { // if the left mouse button is pressed
                if ((Gdx.input.getX() >= 0) && (Gdx.input.getX() <= 152) && (Gdx.input.getY() >= 576 - 40) && (Gdx.input.getY() <= 576)) { // and its within the bounds
                    setScreen(new Menu(this)); // set screen back to the menu screen
                    menuS.setSwitch("Menu"); // type is equal to menu since we are on the menu screen
                }
            }
        }

            if(menuS.getSwitch().equals("Lv2")){
                setScreen(new Lvl2(this,menuS));
                menuS.setSwitch("");
            }
            if(menuS.getSwitch().equals("Lv3")){
                setScreen(new Lvl3(this,menuS));
                menuS.setSwitch("");
            }
            if(menuS.getSwitch().equals("Lv6")){
                setScreen(new Lvl6(this,menuS));
                menuS.setSwitch("");
            }
            if(menuS.getSwitch().equals("Lv7")){
                setScreen(new Lvl7(this,menuS));
                menuS.setSwitch("");
            }
            if(menuS.getSwitch().equals("Lv1")){
                setScreen(new Lvl1(this,menuS));
                menuS.setSwitch("");
            }
            if(menuS.getSwitch().equals("Finish")){
                setScreen(new LeaderBoards(this,menuS));
                 menuS.setSwitch("");
            }
            if(menuS.getSwitch().equals("Leaderboards")){
                setScreen(new LeaderBoards(this,menuS));
                 menuS.setSwitch("");
            }
	}
	
	@Override
	public void dispose () {
            super.dispose();
	}
        @Override
	public void resize (int width, int height) {
            super.resize(width, height);
	}
        @Override
	public void pause () {
            super.pause();
	}
        @Override
	public void resume () {
            super.resume();
	}
}
