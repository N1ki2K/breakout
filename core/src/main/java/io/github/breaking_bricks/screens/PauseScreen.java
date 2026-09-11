package io.github.breaking_bricks.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import io.github.breaking_bricks.GameConfig;
import io.github.breaking_bricks.BreakoutGame;

public class PauseScreen implements Screen{

    private final BreakoutGame game;
    private final GameScreen gameScreen;

    private SpriteBatch batch;
    private BitmapFont font;

    private OrthographicCamera camera;
    private StretchViewport viewport;

    private Texture background;
    private GlyphLayout layout;

    public PauseScreen(BreakoutGame game, GameScreen gameScreen){
        this.game = game;
        this.gameScreen = gameScreen;

        batch = new SpriteBatch();
        layout = new GlyphLayout();

        camera = new OrthographicCamera();
        viewport = new StretchViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);

        background = new Texture(Gdx.files.internal("textures/screen_bg/png/start.png"));

        background.setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear);

        FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/Inter_18pt-Regular.ttf")
            );

        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 36;

        font = generator.generateFont(parameter);

        generator.dispose();

        font.getRegion()
            .getTexture()
            .setFilter(
                Texture.TextureFilter.Linear,
                Texture.TextureFilter.Linear
            );
    }

    @Override
    public void render(float delta){
    ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1);

    viewport.apply();

    batch.setProjectionMatrix(camera.combined);

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

    batch.draw(
        background,
        0,
        0,
        GameConfig.WORLD_WIDTH,
        GameConfig.WORLD_HEIGHT
    );

    drawCentered(
        "PAUSED",
        GameConfig.WORLD_HEIGHT * 0.62f
    );

    drawCentered(
        "P - Resume",
        GameConfig.WORLD_HEIGHT * 0.50f
    );

    drawCentered(
        "R - Restart",
        GameConfig.WORLD_HEIGHT * 0.43f
    );

    drawCentered(
        "ESC - Main Menu",
    GameConfig.WORLD_HEIGHT * 0.36f
    );

    batch.end();

    }

    private void drawCentered(String text, float y){

        layout.setText(font, text);

        float x =
            (GameConfig.WORLD_WIDTH - layout.width) / 2f;

        font.draw(
            batch,
            layout,
            x,
            y
        );

    }

    @Override
    public void show(){}

    @Override
    public void resize(int width, int height){
        viewport.update(
            width,
            height,
            true
        );
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
        font.dispose();
        background.dispose();
    }
}
