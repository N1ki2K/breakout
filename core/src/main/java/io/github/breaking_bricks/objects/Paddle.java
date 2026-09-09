package io.github.breaking_bricks.objects;

import com.badlogic.gdx.math.Rectangle;

import io.github.breaking_bricks.GameConfig;

public class Paddle {

    private float x;
    private float y;

    private float width;
    private float hight;

    private float speed;
    private float velocityX;

    public Paddle() {

        width = 120;
        hight = 20;

        speed = 500;
        velocityX = 0;

        x = GameConfig.WALL_LEFT
            + (
            GameConfig.WALL_RIGHT
                - GameConfig.WALL_LEFT
                - width
        ) / 2f;

        y = GameConfig.MISS_Y + 30;
    }

    public void moveLeft(float delta) {

        velocityX = -speed;

        x += velocityX * delta;

        if (x < GameConfig.WALL_LEFT) {
            x = GameConfig.WALL_LEFT;
        }
    }

    public void moveRight(float delta) {

        velocityX = speed;

        x += velocityX * delta;

        if (x + width > GameConfig.WALL_RIGHT) {
            x = GameConfig.WALL_RIGHT - width;
        }
    }

    public void stop() {
        velocityX = 0;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public Rectangle getBounds() {
        return new Rectangle(
            x,
            y,
            width,
            hight
        );
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHight() {
        return hight;
    }
}
