package io.github.breaking_bricks.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import io.github.breaking_bricks.GameConfig;

public class Ball {

    private float x;
    private float y;

    private float dx;
    private float dy;

    private float size;
    private float speed;

    public Ball() {
        size = 16;
        speed = 250;

        reset();
    }

    public void update(float delta) {
        x += dx * delta;
        y += dy * delta;

        if (x <= GameConfig.WALL_LEFT && dx < 0) {
            x = GameConfig.WALL_LEFT;
            bounceX();
        }

        if (x + size >= GameConfig.WALL_RIGHT && dx > 0) {
            x = GameConfig.WALL_RIGHT - size;
            bounceX();
        }

        if (y + size >= GameConfig.WALL_TOP && dy > 0) {
            y = GameConfig.WALL_TOP - size;
            bounceY();
        }
    }

    public void bounceFromPaddle(
        float hitPosition,
        float paddleVelocity
    ) {
        dy = Math.abs(dy);

        dx += hitPosition * 250;
        dx += paddleVelocity * 0.15f;

        dx = MathUtils.clamp(dx, -500, 500);
    }

    public void bounceX() {
        dx = -dx;
    }

    public void bounceY() {
        dy = -dy;
    }

    public Rectangle getBounds() {
        return new Rectangle(
            x,
            y,
            size,
            size
        );
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSize() {
        return size;
    }

    public float getDy() {
        return dy;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void reset() {
        x =
            GameConfig.WALL_LEFT
                + (
                GameConfig.WALL_RIGHT
                    - GameConfig.WALL_LEFT
                    - size
            ) / 2f;

        y = GameConfig.WORLD_HEIGHT / 2f;

        dx = speed;
        dy = speed;
    }

    public void increaceSpeed(float amount) {
        speed += amount;
    }

    public float getDx() {
        return dx;
    }

    public float getSpeed() {
        return speed;
    }

}


