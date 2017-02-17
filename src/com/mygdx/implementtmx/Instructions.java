/**
 * A screen that displays the instructions to play this game.
 * A: Muhammad Hassaan
 */ 
package com.mygdx.implementtmx;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Instructions implements Screen {

    Texture backButton;
    Texture title;
    SpriteBatch batch;
    Texture backGround;
    Texture text;
    final private ImplementTmx game;

    public Instructions(ImplementTmx g) {
        Gdx.app.log("Instructions", "Attached");
        this.game = g;
        batch = new SpriteBatch();
        batch = new SpriteBatch();
        title = new Texture(Gdx.files.internal("InstructionsTitle.png"));
        backButton = new Texture(Gdx.files.internal("Back.png"));
        backGround = new Texture(Gdx.files.internal("Space.jpg"));
        text = new Texture(Gdx.files.internal("InstructionsText.png"));
    }

    @Override
    public void render(float delta) {
        // Sets a Color to Fill the Screen with (RGB = 10, 15, 230), Opacity of 1 (100%)
        Gdx.gl.glClearColor(0, 0, 0, 100);
        // Fills the screen with the selected color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backGround, 0, 0);
        batch.draw(backButton, 0, 0);
        batch.draw(title,300,470);
        batch.draw(text,1,50);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("Instructions", "resizing");
    }

    @Override
    public void show() {
        Gdx.app.log("Instructions", "show called");
    }

    @Override
    public void hide() {
        Gdx.app.log("Instructions", "hide called");
    }

    @Override
    public void pause() {
        Gdx.app.log("Instructions", "pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log("Instructions", "resume called");
    }

    @Override
    public void dispose() {
        backGround.dispose();
        backButton.dispose();
        batch.dispose();
    }

}
