package io.github.breaking_bricks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com .badlogic.gdx.utils.ScreenUtils;

import io.github.breaking_bricks.BreakoutGame;
import io.github.breaking_bricks.objects.Brick;

public class GameOverScreen implements Screen{

        private final BreakoutGame game;

        private SpriteBatch batch;
        private BitmapFont font;

        public GameOverScreen(BreakoutGame game){
            this.game = game;

            batch = new SpriteBatch();
            font = new BitmapFont();
        }

    @Override
    public void render(float delta) {
            ScreenUtils.clear(0.1f, 0.02f, 0.02f, 1);

            batch.begin();

            font.draw(
                batch,
                "GAME OVER",
                Gdx.graphics.getWidth() / 2f - 50,
                Gdx.graphics.getHeight() / 2f
            );

            font.draw(
                batch,
                "Press R ro restart",
                Gdx.graphics.getWidth() / 2f - 70,
                Gdx.graphics.getHeight() /2 - 40
            );

            batch.end();

            if(Gdx.input.isKeyPressed(Input.Keys.R)){
                game.setScreen(new GameScreen(game));
                return;
            }
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
