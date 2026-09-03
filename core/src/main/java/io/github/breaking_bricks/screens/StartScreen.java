package io.github.breaking_bricks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.breaking_bricks.BreakoutGame;
public class StartScreen implements Screen{

    private final BreakoutGame game;

    public StartScreen(BreakoutGame game){
        this.game = game;
    }
    @Override
    public void render(float delta){
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            game.setScreen(new GameScreen(game));
            }
        }
    @Override
    public void show(){}

    @Override
    public void resize(int width, int hight){}

    @Override
    public void pause(){}

    @Override
    public void resume(){}

    @Override
    public void dispose(){}

    @Override
    public void hide(){}
}
