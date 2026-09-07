package io.github.breaking_bricks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.breaking_bricks.BreakoutGame;
import io.github.breaking_bricks.GameConfig;

public class StartScreen implements Screen {

    private final BreakoutGame game;

    private final OrthographicCamera camera;
    private final FitViewport viewport;

    private final SpriteBatch batch;

    private final Texture background;

    private final BitmapFont titleFont;
    private final BitmapFont textFont;

    private final GlyphLayout layout;

    public StartScreen(BreakoutGame game) {
        this.game = game;

        camera = new OrthographicCamera();

        viewport = new FitViewport(
            GameConfig.WORLD_WIDTH,
            GameConfig.WORLD_HEIGHT,
            camera
        );

        batch = new SpriteBatch();

        background = new Texture(
            Gdx.files.internal("textures/screen_bg/png/start.png")
        );

        background.setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );

        FreeTypeFontGenerator titleGenerator =
            new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/Inter_28pt-Regular.ttf")
            );

        FreeTypeFontGenerator.FreeTypeFontParameter titleParameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

        titleParameter.size = 70;

        titleFont = titleGenerator.generateFont(titleParameter);

        titleGenerator.dispose();


        FreeTypeFontGenerator textGenerator =
            new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/Inter_18pt-Regular.ttf")
            );

        FreeTypeFontGenerator.FreeTypeFontParameter textParameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();

        textParameter.size = 36;

        textFont = textGenerator.generateFont(textParameter);

        textGenerator.dispose();


        titleFont.getRegion().getTexture().setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );

        textFont.getRegion().getTexture().setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );

        layout = new GlyphLayout();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        viewport.apply();

        batch.setProjectionMatrix(camera.combined);

        handleInput();

        drawScreen();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void drawScreen() {
        batch.begin();

        batch.draw(
            background,
            0,
            0,
            GameConfig.WORLD_WIDTH,
            GameConfig.WORLD_HEIGHT
        );

        drawCenteredText(
            titleFont,
            "BREAKOUT",
            104
        );

        drawCenteredText(
            textFont,
            "Press SPACE to start",
            300
        );

        drawCenteredText(
            textFont,
            "Press A or D to move",
            436
        );

        batch.end();
    }

    private void drawCenteredText(
        BitmapFont font,
        String text,
        float figmaY
    ) {
        layout.setText(font, text);

        float x =
            (GameConfig.WORLD_WIDTH - layout.width) / 2f;

        float y =
            GameConfig.WORLD_HEIGHT
                - (figmaY / 600f)
                * GameConfig.WORLD_HEIGHT;

        font.draw(
            batch,
            layout,
            x,
            y
        );
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(
            width,
            height,
            true
        );
    }

    @Override
    public void show() {
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

    @Override
    public void dispose() {
        background.dispose();
        batch.dispose();

        titleFont.dispose();
        textFont.dispose();
    }
}
