package io.github.breaking_bricks;

import io.github.breaking_bricks.objects.Ball;
import io.github.breaking_bricks.objects.Brick;
import io.github.breaking_bricks.objects.Paddle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionTest {

    @Test
    void ballShouldCollideWithBrick() {

        Ball ball = new Ball();

        Brick brick = new Brick(
            ball.getX(),
            ball.getY(),
            100,
            40,
            1
        );

        assertTrue(
            ball.getBounds().overlaps(
                brick.getBounds()
            )
        );
    }

    @Test
    void ballShouldNotCollideWithDistantBrick() {

        Ball ball = new Ball();

        Brick brick = new Brick(
            GameConfig.WALL_LEFT,
            GameConfig.WALL_TOP - 100,
            100,
            40,
            1
        );

        assertFalse(
            ball.getBounds().overlaps(
                brick.getBounds()
            )
        );
    }

    @Test
    void ballShouldCollideWithPaddle() {

        Ball ball = new Ball();
        Paddle paddle = new Paddle();

        ball.setY(
            paddle.getY()
                + paddle.getHight()
                - ball.getSize() / 2f
        );

        assertTrue(
            ball.getBounds().overlaps(
                paddle.getBounds()
            )
        );
    }

    @Test
    void ballShouldNotInitiallyCollideWithPaddle() {

        Ball ball = new Ball();
        Paddle paddle = new Paddle();

        assertFalse(
            ball.getBounds().overlaps(
                paddle.getBounds()
            )
        );
    }

    @Test
    void destroyedBrickShouldStillKeepItsBounds() {

        Ball ball = new Ball();

        Brick brick = new Brick(
            ball.getX(),
            ball.getY(),
            100,
            40,
            1
        );

        brick.hit();

        assertTrue(
            brick.isDestroyed()
        );

        assertTrue(
            ball.getBounds().overlaps(
                brick.getBounds()
            )
        );
    }
}
