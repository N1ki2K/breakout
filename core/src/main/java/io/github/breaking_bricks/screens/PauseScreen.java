package io.github.breaking_bricks.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.breaking_bricks.BreakoutGame;

public class PauseScreen implements Screen{

    private final BreakoutGame game;
    private final GameScreen gameScreen;

    private SpriteBatch batch;
    private BitmapFont font;

    public PauseScreen(BreakoutGame game, GameScreen gameScreen){
        this.game = game;
        this.gameScreen = gameScreen;

        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void render(float delta){
    ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1);

    if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
        game.setScreen(gameScreen);
        return;
    }

    if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
        game.setScreen(new GameScreen(game));
        return;
    }

    if(Gdx.input.isKeyJustPressed((Input.Keys.ESCAPE))){
        game.setScreen(new StartScreen(game));
        return;
    }
    batch.begin();

    font.draw(
        batch,
        "PAUSED",
        Gdx.graphics.getWidth() / 2f -30,
        Gdx.graphics.getHeight() / 2 +60
    );

    font.draw(
        batch,
        "P - Resume",
        Gdx.graphics.getWidth() / 2f -40,
        Gdx.graphics.getHeight() / 2
    );

    font.draw(
        batch,
        "R - Restart",
        Gdx.graphics.getWidth() / 2f -40,
        Gdx.graphics.getHeight() / 2 -30
    );

    font.draw(
        batch,
        "ESC - Main Menu",
        Gdx.graphics.getWidth() / 2f - 50,
        Gdx.graphics.getHeight() /2 -60
    );
    batch.end();
    }

    @Override
    public void show(){}

    @Override
    public void resize(int width, int height){}

    @Override
    public void pause(){}

    @Override
    public void resume(){}

    @Override
    public void hide(){}

    @Override
    public void dispose(){
        batch.dispose();
        font.dispose();
    }
}
