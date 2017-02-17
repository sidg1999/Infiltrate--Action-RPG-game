/*
 * The main menu screen, allows access to a plethra of options such as Play, Credits, Exit and Instructions
 * Author: Muhammad And Sid
 */
package com.mygdx.implementtmx;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Menu extends Game implements Screen {

    final private SpriteBatch batch;
    final private ImplementTmx game;
    //BitmapFont title;
    //String myText;
    Texture playButton;
    Texture exitButton;
    Texture instructionsButton;
    Texture creditsButton;
    Texture backGround;
    Texture title;

    Boolean menu = true;

    public Menu(ImplementTmx g) {
        Gdx.app.log("Menu", "Attached");
        batch = new SpriteBatch();
        this.game = g;
        title = new Texture(Gdx.files.internal("TitleText.png"));
        playButton = new Texture(Gdx.files.internal("Play.png"));
        exitButton = new Texture(Gdx.files.internal("Exit.png"));
        instructionsButton = new Texture(Gdx.files.internal("Instructions.png"));
        creditsButton = new Texture(Gdx.files.internal("Credits.png"));
        backGround = new Texture(Gdx.files.internal("Space.jpg"));

    }

    @Override
    public void render(float delta) {
        // Sets a Color to Fill the Screen with (RGB = 10, 15, 230), Opacity of 1 (100%)
        Gdx.gl.glClearColor(10 / 255.0f, 15 / 255.0f, 230 / 255.0f, 1f);
        // Fills the screen with the selected color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin(); 
        batch.draw(backGround, 0, 0);
        batch.draw(title, 340, 440);
        batch.draw(playButton, 415, 375);
        batch.draw(instructionsButton, 415, 375 - 70);
        batch.draw(creditsButton, 415, 375 - 140);
        batch.draw(exitButton, 415, 375 - 210);
        batch.end();
    }

    @Override
    public void dispose() {
        playButton.dispose();
        exitButton.dispose();
        creditsButton.dispose();
        instructionsButton.dispose();
        title.dispose();
        backGround.dispose();
        batch.dispose();
    }
    @Override
    public void resize(int width, int height) {
        Gdx.app.log("Menu", "resizing");
    }

    @Override
    public void show() {
        Gdx.app.log("Menu", "show called");
    }

    @Override
    public void hide() {
        Gdx.app.log("Menu", "hide called");
    }

    @Override
    public void pause() {
        Gdx.app.log("Menu", "pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log("Menu", "resume called");
    }



    @Override
    public void create() {
    }
}
