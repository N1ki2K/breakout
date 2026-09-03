package io.github.breaking_bricks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
    private int score;
    private int lives;
    private BitmapFont font;
    private SpriteBatch batch;

    public GameScreen(BreakoutGame game){
        this.game = game;
        paddle = new Paddle();
        ball = new Ball();
        bricks = new Array<>();
        shapeRenderer = new ShapeRenderer();

        batch = new SpriteBatch();
        font = new BitmapFont();

        score = 0;
        lives = 3;

        createBricks();
    }

        @Override
    public void render(float delta){
        ScreenUtils.clear(0.5f,0.5f,0.8f,1);

        handleInput(delta);
        ball.update(delta);

        if(ball.getY()< 0){
            lives --;
            if(lives > 0) {
                ball.reset();
            }
        }
        if(lives <= 0){
            game.setScreen(new GameOverScreen(game));
            return;
        }

        for(Brick brick : bricks){
            if (!brick.isDestroyed() && ball.getBounds().overlaps(brick.getBounds())){
                brick.destroy();
                score += 10;
                ball.bounceY();
                break;
            }
        }
        if(ball.getBounds().overlaps(paddle.getBounds()) && ball.getDy() < 0){
            ball.bounceY();
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.rect(
            paddle.getX(),
            paddle.getY(),
            paddle.getWidth(),
            paddle.getHight()
        );

        shapeRenderer.rect(
            ball.getX(),
            ball.getY(),
            ball.getSize(),
            ball.getSize()
        );

        for(Brick brick : bricks){
            if (!brick.isDestroyed()){
                shapeRenderer.rect(
                    brick.getX(),
                    brick.getY(),
                    brick.getWidth(),
                    brick.getHeight()
                );
            }
        }
        shapeRenderer.end();

        batch.begin();

        font.draw(batch,
            "Score: " + score,
            20, Gdx.graphics.getHeight() - 20);

        font.draw(batch,
            "Lives: " + lives,
            Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 20);
        batch.end();
    }
    public void handleInput(float delta){
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
        int rows = 10;
        int columns = 30;

        float brickWidth = 60;
        float brickHeight = 20;

        float margin = 30;
        float gap = 8;

        float totalWidth =
            columns * brickWidth + (columns - 1) * gap;

        float availableWidht = Gdx.graphics.getWidth() - margin * 2 - gap * (columns -1 );

       brickWidth = availableWidht / columns;

       float startX = margin;

//        float startX = (Gdx.graphics.getWidth() - totalWidth / 2f);
        float startY = Gdx.graphics.getHeight() - 100;

        for(int row = 0; row < rows; row++){
            for(int column = 0; column < columns; column++){
                float x = startX + column * (brickWidth + gap);
                float y = startY - row * (brickHeight + gap);

                bricks.add(new Brick(x, y, brickWidth, brickHeight));
            }
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
    public void dispose(){
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
    }

    @Override
    public void hide(){}
}
