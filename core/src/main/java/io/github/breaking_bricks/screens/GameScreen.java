package io.github.breaking_bricks.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.breaking_bricks.GameConfig;

import io.github.breaking_bricks.objects.Brick;
import io.github.breaking_bricks.BreakoutGame;
import io.github.breaking_bricks.objects.Paddle;
import io.github.breaking_bricks.objects.Ball;

public class GameScreen implements Screen {
    private final BreakoutGame game;

    private Paddle paddle;
    private Ball ball;
    private Array<Brick> bricks;
    private ShapeRenderer shapeRenderer;

    private BitmapFont font;
    private SpriteBatch batch;

    private final OrthographicCamera camera;
    private final FitViewport viewport;

    private final Texture background;

    private final Texture paddleTexture;

    private int score;
    private int lives;
    private int level;

    public GameScreen(BreakoutGame game){
        this.game = game;

        camera = new OrthographicCamera();

        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);

        background = new Texture(Gdx.files.internal("textures/screen_bg/png/game_screen.png"));

        paddleTexture = new Texture(
            Gdx.files.internal("textures/paddle/png/paddle.png")
        );

        paddleTexture.setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );

        background.setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );

        paddle = new Paddle();
        ball = new Ball();
        bricks = new Array<>();
        shapeRenderer = new ShapeRenderer();

        batch = new SpriteBatch();
        font = new BitmapFont();

        score = 0;
        lives = 3;
        level = 1;

        createBricks();
    }

        @Override
    public void render(float delta){

            viewport.apply();

            batch.setProjectionMatrix(camera.combined);
            shapeRenderer.setProjectionMatrix(camera.combined);

       if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
           game.setScreen(new PauseScreen(game, this));
           return;
       }

        ScreenUtils.clear(0.5f,0.5f,0.8f,1);

        handleInput(delta);
        ball.update(delta);

        if (ball.getY() < 0) {
            lives--;
            if (lives > 0) {
                ball.reset();
            }
        }
        if (lives <= 0) {
            game.setScreen(new GameOverScreen(game));
            return;
        }

        for (Brick brick : bricks) {
            if (!brick.isDestroyed() && ball.getBounds().overlaps(brick.getBounds())) {
                brick.hit();

                if (brick.isDestroyed()) {

                    score += 10;
                }
                ball.bounceY();
                break;
            }
        }

        if (ball.getBounds().overlaps(paddle.getBounds())
            && ball.getDy() < 0) {

            ball.setY(paddle.getY() + paddle.getHight());

            float ballCenter = ball.getX() + ball.getSize() / 2f;

            float paddleCenter = paddle.getX() + paddle.getWidth() / 2f;

            float hitPosition = (ballCenter - paddleCenter) / (paddle.getWidth() / 2f);

            ball.bounceFromPaddle(
                hitPosition,
                paddle.getVelocityX()
            );
        }

        batch.begin();
        batch.draw(background, 0, 0, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.rect(
            ball.getX(),
            ball.getY(),
            ball.getSize(),
            ball.getSize()
        );

        for(Brick brick : bricks){
            if (!brick.isDestroyed()){

                if(brick.getHitsRemaining() == 3){
                    shapeRenderer.setColor(Color.DARK_GRAY);
                } else if (brick.getHitsRemaining() == 2) {
                    shapeRenderer.setColor(Color.GRAY);
                } else{
                    shapeRenderer.setColor(brick.getColor());
                }


                shapeRenderer.rect(
                    brick.getX(),
                    brick.getY(),
                    brick.getWidth(),
                    brick.getHeight()
                );

                shapeRenderer.setColor(Color.WHITE);
            }
        }
        shapeRenderer.end();

        batch.begin();

            batch.draw(
                paddleTexture,
                paddle.getX(),
                paddle.getY(),
                paddle.getWidth(),
                paddle.getHight()
            );
            batch.end();

        batch.begin();

        font.draw(batch,
            "Score: " + score,
            20, GameConfig.WORLD_HEIGHT - 20);

        font.draw(batch,
            "Lives: " + lives,
            GameConfig.WORLD_WIDTH - 100, GameConfig.WORLD_HEIGHT - 20);

        font.draw(batch,
            "Level: " + level,
            GameConfig.WORLD_WIDTH / 2 -30,
            GameConfig.WORLD_HEIGHT -20);
        batch.end();

        if(allBricksDestroyed()){

            if(level < 3){
                level++;
                bricks.clear();
                createBricks();
                ball.increaceSpeed(50);
                ball.reset();
            } else {
                game.setScreen(new WinScreen(game));
            }
            return;
        }
    }
    public void handleInput(float delta){

        paddle.stop();

        if(Gdx.input.isKeyPressed(Input.Keys.A)
        || Gdx.input.isKeyPressed(Input.Keys.LEFT) ){
        paddle.moveLeft(delta);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D)
        || Gdx.input.isKeyPressed(Input.Keys.RIGHT) ){
        paddle.moveRight(delta);
        }
    }

    public void createBricks(){

        int rows = switch (level){
        case 1 -> 3;
        case 2 -> 4;
        case 3 -> 5;
        default -> 3;
        };

        int columns = 10;
        switch (level) {
            case 1:
                rows = 3;

                break;
            case 2:
                rows = 4;
                break;
            case 3:
                rows =5;
                break;
        }

        float brickWidth = 60;

        float brickHeight = 20;

        float margin = 30;
        float gap = 8;

        float totalWidth =
            columns * brickWidth + (columns - 1) * gap;

        float availableWidht = GameConfig.WORLD_WIDTH - margin * 2 - gap * (columns -1 );

       brickWidth = availableWidht / columns;

       float startX = margin;
        float startY = GameConfig.WORLD_HEIGHT - 100;

        for(int row = 0; row < rows; row++){

            int hitReqired = 1;

            switch (level){
                case 1:
                    hitReqired = 1;
                    break;
                case 2:
                if(row == 0){
                    hitReqired = 2;
                }
                case 3:
                    if(row == 0){
                        hitReqired = 3;
                    } else if (row == 1) {
                        hitReqired = 2;
                    }
                    break;
            }

            for(int column = 0; column < columns; column++){

                boolean createBrick = switch (level){
                    case 1 -> true;
                    case 2 -> (row + column) % 2 == 0;
                    case 3 -> column >= row && columns < columns - row;
                    default -> true;
                };
                if(!createBrick){
                    continue;
                }

                float x = startX + column * (brickWidth + gap);
                float y = startY - row * (brickHeight + gap);

                Color color = switch (row){
                    case 0 -> Color.RED;
                    case 1 -> Color.GREEN;
                    case 2 -> Color.MAGENTA;
                    case 3 -> Color.GREEN;
                    case 4 -> Color.BLACK;
                    default -> Color.BLUE;
                };

                bricks.add(new Brick(x, y, brickWidth, brickHeight, color, hitReqired));
            }
        }
    }

    private boolean allBricksDestroyed(){
        for (Brick brick : bricks){
            if (!brick.isDestroyed()){
                return false;
            }
        }
        return true;
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
    public void dispose(){
        background.dispose();
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
        paddleTexture.dispose();
    }

    @Override
    public void hide(){}
}
