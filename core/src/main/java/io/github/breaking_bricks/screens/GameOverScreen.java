package io.github.breaking_bricks.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import io.github.breaking_bricks.BreakoutGame;
import io.github.breaking_bricks.GameConfig;

public class GameOverScreen implements Screen{

    private final BreakoutGame game;
    private final int score;

    private SpriteBatch batch;

    private OrthographicCamera camera;
    private StretchViewport viewport;

    private Texture background;

    private BitmapFont titleFont;
    private BitmapFont scoreFont;
    private BitmapFont messageFont;
    private BitmapFont buttonFont;

    private GlyphLayout layout;

    public GameOverScreen(BreakoutGame game, int score){
         this.game = game;
         this.score = score;

         batch = new SpriteBatch();

         camera = new OrthographicCamera();
         viewport = new StretchViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);

         background = new Texture(Gdx.files.internal("textures/screen_bg/png/game-over.png"));

         background.setFilter(
             Texture.TextureFilter.Linear,
             Texture.TextureFilter.Linear
         );

        titleFont = createFont(
            "fonts/Inter_28pt-Regular.ttf",
            100
        );

        scoreFont = createFont(
            "fonts/Inter_18pt-Regular.ttf",
            52
        );

        messageFont = createFont(
            "fonts/Inter_18pt-Regular.ttf",
            48
        );

        buttonFont = createFont(
            "fonts/Inter_28pt-Regular.ttf",
            48
        );

        layout = new GlyphLayout();

    }

    private BitmapFont createFont(String path, int size){

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = size;

        BitmapFont font = generator.generateFont(parameter);

        generator.dispose();

        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear);
        return font;

    }

    private void drawCentered(BitmapFont font, String text, float y){
        layout.setText(font, text);

        float x  = (GameConfig.WORLD_WIDTH - layout.width) / 2f;

        font.draw(
            batch,
            layout,
            x,
            y
        );
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        viewport.apply();

        batch.setProjectionMatrix(camera.combined);

        if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
             game.setScreen(new GameScreen(game));
             return;
        }

        batch.begin();

        batch.draw(
            background,
           0,
           0,
           GameConfig.WORLD_WIDTH,
           GameConfig.WORLD_HEIGHT
        );

        drawCentered(
            titleFont,
            "Game Over",
            GameConfig.WORLD_HEIGHT * 0.78f
        );

        drawCentered(
            scoreFont,
            "Score: " + score,
            GameConfig.WORLD_HEIGHT * 0.60f
        );

        drawCentered(
            messageFont,
            "Better luck next time",
            GameConfig.WORLD_HEIGHT * 0.44f
        );

        drawCentered(
            buttonFont,
            "Press R to restart",
            GameConfig.WORLD_HEIGHT * 0.27f
        );

        batch.end();

    }

    @Override
    public void show(){}

    @Override
    public void resize(int width, int height){
        viewport.update(width, height, true);
    }

    @Override
    public void pause(){}

    @Override
    public void resume(){}

    @Override
    public void hide(){}

    @Override
    public void dispose(){
            batch.dispose();
            background.dispose();
            titleFont.dispose();
            buttonFont.dispose();
            scoreFont.dispose();
            messageFont.dispose();
    }
}
