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

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import io.github.breaking_bricks.BreakoutGame;
import io.github.breaking_bricks.GameConfig;
import io.github.breaking_bricks.GameLogic;
import io.github.breaking_bricks.GameState;
import io.github.breaking_bricks.LevelBuilder;

import io.github.breaking_bricks.objects.Ball;
import io.github.breaking_bricks.objects.Brick;
import io.github.breaking_bricks.objects.Paddle;

public class GameScreen implements Screen {

    private final BreakoutGame game;

    private Paddle paddle;
    private Ball ball;
    private Array<Brick> bricks;

    private GameState gameState;

    private final SpriteBatch batch;
    private final BitmapFont font;
    private final GlyphLayout hudLayout;

    private final OrthographicCamera camera;
    private final StretchViewport viewport;

    private final Texture background;
    private final Texture paddleTexture;
    private final Texture ballTexture;

    private final Texture brickTexture1;
    private final Texture brickTexture2;
    private final Texture brickTexture3;
    private final Texture brickTexture4;
    private final Texture brickTexture5;


    public GameScreen(BreakoutGame game) {

        this.game = game;

        camera = new OrthographicCamera();

        viewport = new StretchViewport(
            GameConfig.WORLD_WIDTH,
            GameConfig.WORLD_HEIGHT,
            camera
        );

        batch = new SpriteBatch();
        hudLayout = new GlyphLayout();


        brickTexture1 = new Texture(
            Gdx.files.internal(
                "textures/bricks/png/brick1.png"
            )
        );

        brickTexture2 = new Texture(
            Gdx.files.internal(
                "textures/bricks/png/brick2.png"
            )
        );

        brickTexture3 = new Texture(
            Gdx.files.internal(
                "textures/bricks/png/brick3.png"
            )
        );

        brickTexture4 = new Texture(
            Gdx.files.internal(
                "textures/bricks/png/brick4.png"
            )
        );

        brickTexture5 = new Texture(
            Gdx.files.internal(
                "textures/bricks/png/brick5.png"
            )
        );


        background = new Texture(
            Gdx.files.internal(
                "textures/screen_bg/png/game_screen.png"
            )
        );

        ballTexture = new Texture(
            Gdx.files.internal(
                "textures/ball/png/ball.png"
            )
        );

        paddleTexture = new Texture(
            Gdx.files.internal(
                "textures/paddle/png/paddle.png"
            )
        );


        setLinearFilter(background);
        setLinearFilter(ballTexture);
        setLinearFilter(paddleTexture);

        setLinearFilter(brickTexture1);
        setLinearFilter(brickTexture2);
        setLinearFilter(brickTexture3);
        setLinearFilter(brickTexture4);
        setLinearFilter(brickTexture5);


        paddle = new Paddle();
        ball = new Ball();

        gameState = new GameState();

        bricks = new Array<>();

        createBricks();


        FreeTypeFontGenerator generator =
            new FreeTypeFontGenerator(
                Gdx.files.internal(
                    "fonts/Inter_18pt-Regular.ttf"
                )
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


    private void setLinearFilter(Texture texture) {

        texture.setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );
    }


    private Texture getBrickTexture(Brick brick) {

        return switch (brick.getTextureType()) {

            case 1 -> brickTexture1;
            case 2 -> brickTexture2;
            case 3 -> brickTexture3;
            case 4 -> brickTexture4;
            case 5 -> brickTexture5;

            default -> brickTexture1;
        };
    }


    @Override
    public void render(float delta) {

        ScreenUtils.clear(
            0.5f,
            0.5f,
            0.8f,
            1
        );

        viewport.apply();

        batch.setProjectionMatrix(
            camera.combined
        );


        if (
            Gdx.input.isKeyJustPressed(
                Input.Keys.P
            )
        ) {

            game.setScreen(
                new PauseScreen(
                    game,
                    this
                )
            );

            return;
        }


        handleInput(delta);

        ball.update(delta);


        if (GameLogic.isBallMissed(ball)) {

            gameState.loseLife();

            if (gameState.isGameOver()) {

                game.setScreen(
                    new GameOverScreen(
                        game,
                        gameState.getScore()
                    )
                );

                return;
            }

            ball.reset();
        }


        handleBrickCollisions();

        handlePaddleCollision();


        if (
            GameLogic.allBricksDestroyed(
                bricks
            )
        ) {

            if (!gameState.isFinalLevel()) {

                gameState.nextLevel();

                createBricks();

                ball.increaceSpeed(50);

                ball.reset();

            } else {

                game.setScreen(
                    new WinScreen(
                        game,
                        gameState.getScore()
                    )
                );

                return;
            }
        }


        drawGame();
    }


    private void handleBrickCollisions() {

        for (Brick brick : bricks) {

            if (
                !brick.isDestroyed()
                    && ball.getBounds()
                    .overlaps(
                        brick.getBounds()
                    )
            ) {

                GameLogic.hitBrick(brick, gameState);

                ball.bounceY();

                break;
            }
        }
    }


    private void handlePaddleCollision() {

        if (
            ball.getBounds()
                .overlaps(
                    paddle.getBounds()
                )
                && ball.getDy() < 0
        ) {

            ball.setY(
                paddle.getY()
                    + paddle.getHight()
            );


            float ballCenter =
                ball.getX()
                    + ball.getSize() / 2f;


            float paddleCenter =
                paddle.getX()
                    + paddle.getWidth() / 2f;


            float hitPosition =
                (
                    ballCenter
                        - paddleCenter
                )
                    / (
                    paddle.getWidth()
                        / 2f
                );


            ball.bounceFromPaddle(
                hitPosition,
                paddle.getVelocityX()
            );
        }
    }


    private void drawGame() {

        batch.begin();


        batch.draw(
            background,
            0,
            0,
            GameConfig.WORLD_WIDTH,
            GameConfig.WORLD_HEIGHT
        );


        for (Brick brick : bricks) {

            if (!brick.isDestroyed()) {

                batch.draw(
                    getBrickTexture(brick),
                    brick.getX(),
                    brick.getY(),
                    brick.getWidth(),
                    brick.getHeight()
                );
            }
        }


        batch.draw(
            paddleTexture,
            paddle.getX(),
            paddle.getY(),
            paddle.getWidth(),
            paddle.getHight()
        );


        batch.draw(
            ballTexture,
            ball.getX(),
            ball.getY(),
            ball.getSize(),
            ball.getSize()
        );


        drawHud();


        batch.end();
    }


    private void drawHud() {

        float margin = 35f;

        float hudY =
            GameConfig.WORLD_HEIGHT
                - 35f;


        String scoreText =
            "Score: "
                + gameState.getScore();

        font.draw(
            batch,
            scoreText,
            margin,
            hudY
        );


        String levelText =
            "Level: "
                + gameState.getLevel();

        hudLayout.setText(
            font,
            levelText
        );

        float levelX =
            (
                GameConfig.WORLD_WIDTH
                    - hudLayout.width
            )
                / 2f;

        font.draw(
            batch,
            levelText,
            levelX,
            hudY
        );


        String livesText =
            "Lives: "
                + gameState.getLives();

        hudLayout.setText(
            font,
            livesText
        );

        float livesX =
            GameConfig.WORLD_WIDTH
                - margin
                - hudLayout.width;

        font.draw(
            batch,
            livesText,
            livesX,
            hudY
        );
    }


    public void handleInput(float delta) {

        paddle.stop();


        if (
            Gdx.input.isKeyPressed(
                Input.Keys.A
            )
                || Gdx.input.isKeyPressed(
                Input.Keys.LEFT
            )
        ) {

            paddle.moveLeft(delta);
        }


        if (
            Gdx.input.isKeyPressed(
                Input.Keys.D
            )
                || Gdx.input.isKeyPressed(
                Input.Keys.RIGHT
            )
        ) {

            paddle.moveRight(delta);
        }
    }


    public void createBricks() {

        bricks =
            LevelBuilder.createBricks(
                gameState.getLevel()
            );
    }


    @Override
    public void resize(
        int width,
        int height
    ) {

        viewport.update(
            width,
            height,
            true
        );
    }


    @Override
    public void dispose() {

        background.dispose();

        batch.dispose();
        font.dispose();

        paddleTexture.dispose();
        ballTexture.dispose();

        brickTexture1.dispose();
        brickTexture2.dispose();
        brickTexture3.dispose();
        brickTexture4.dispose();
        brickTexture5.dispose();
    }


    @Override
    public void show() {}


    @Override
    public void pause() {}


    @Override
    public void resume() {}


    @Override
    public void hide() {}
}
