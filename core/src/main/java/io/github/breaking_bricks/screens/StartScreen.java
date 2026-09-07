package io.github.breaking_bricks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.breaking_bricks.BreakoutGame;

import static java.awt.SystemColor.text;

public class StartScreen implements Screen {

    private final BreakoutGame game;

    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;

    private final BitmapFont titleFont;
    private final BitmapFont font;

    private final GlyphLayout layout;

    private final Rectangle playButton;

    public StartScreen(BreakoutGame game) {
        this.game = game;

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        titleFont = new BitmapFont();
        font = new BitmapFont();

        layout = new GlyphLayout();

        float buttonWidth = 220;
        float buttonHight = 60;

        playButton = new Rectangle(
            Gdx.graphics.getWidth() / 2f - 20,
            Gdx.graphics.getHeight() / 2f - buttonWidth / 2f,
            buttonWidth,
            buttonHight

        );
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);

        hadnleInput();

        drawMenu();
    }

    private void hadnleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {

            Gdx.app.exit();
        }

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (Gdx.input.justTouched() && playButton.contains(mouseX, mouseY)) {
            game.setScreen((new GameScreen(game)));
        }
    }

    public void drawMenu() {
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        boolean hovering = playButton.contains(mouseX, mouseY);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        if (hovering) {
            shapeRenderer.setColor(Color.CYAN);
        } else {
            shapeRenderer.setColor(Color.DARK_GRAY);
        }
        shapeRenderer.rect(
            playButton.x,
            playButton.y,
            playButton.width,
            playButton.height
        );
        shapeRenderer.end();
        
        batch.begin();

        drawCentered(
            titleFont,
            "BREAKOUT",
            Gdx.graphics.getHeight() - 150
        );

        drawCentered(
            font,
            "PLAY",
            playButton.y + 38
        );

        drawCentered(
            font,
            "A / D - Move",
            Gdx.graphics.getHeight() / 2f - 120
        );

        drawCentered(
            font,
            "P - Pause",
            Gdx.graphics.getHeight() / 2f - 155
        );

        drawCentered(
            font,
            "SPACE - Start",
            Gdx.graphics.getHeight() / 2f - 190
        );

        drawCentered(
            font,
            "ESC - Quit",
            Gdx.graphics.getHeight() / 2f - 255
        );

        batch.end();
    }

    private void drawCentered(BitmapFont font, String text, float y) {

        layout.setText(font, text);

        float x = (Gdx.graphics.getWidth() - layout.width) / 2;

        font.draw(batch, text, x, y);

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
