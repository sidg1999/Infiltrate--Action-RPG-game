
package com.mygdx.implementtmx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Credits implements Screen {

    Texture creditsImg;
    Texture backButton;
    SpriteBatch batch;
    Texture backGround;
    Texture names;

    final private ImplementTmx game;

    public Credits(ImplementTmx g) {
        Gdx.app.log("Credits", "Attached");
        this.game = g;
        creditsImg = new Texture(Gdx.files.internal("CreditsS.png"));
        names = new Texture(Gdx.files.internal("names.png"));
        batch = new SpriteBatch();
        backButton = new Texture(Gdx.files.internal("Back.png"));
        backGround = new Texture(Gdx.files.internal("Space.jpg"));

    }

    @Override
    public void render(float delta) {
        // Sets a Color to Fill the Screen with (RGB = 10, 15, 230), Opacity of 1 (100%)
        Gdx.gl.glClearColor(0, 0, 0, 100);
        // Fills the screen with the selected color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backGround, 0, 0);
        batch.draw(creditsImg, 360, 470);
        batch.draw(backButton, 0, 0);
        batch.draw(names, 310, 120);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("Credits", "resizing");
    }

    @Override
    public void show() {
        Gdx.app.log("Credits", "show called");
    }

    @Override
    public void hide() {
        Gdx.app.log("Credits", "hide called");
    }

    @Override
    public void pause() {
        Gdx.app.log("Credits", "pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log("Credits", "resume called");
    }

    @Override
    public void dispose() {
        backGround.dispose();
        creditsImg.dispose();
        backButton.dispose();
        names.dispose();
        batch.dispose();
    }

}
