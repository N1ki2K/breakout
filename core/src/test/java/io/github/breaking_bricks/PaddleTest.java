package io.github.breaking_bricks;

import io.github.breaking_bricks.objects.Paddle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaddleTest {

    @Test
    void paddleShouldStartCentered() {

        Paddle paddle = new Paddle();

        float expectedX =
            GameConfig.WALL_LEFT
                + (
                GameConfig.WALL_RIGHT
                    - GameConfig.WALL_LEFT
                    - paddle.getWidth()
            ) / 2f;

        assertEquals(
            expectedX,
            paddle.getX(),
            0.01f
        );
    }

    @Test
    void paddleShouldMoveLeft() {

        Paddle paddle = new Paddle();

        float startX = paddle.getX();

        paddle.moveLeft(0.1f);

        assertTrue(
            paddle.getX() < startX
        );
    }

    @Test
    void paddleShouldMoveRight() {

        Paddle paddle = new Paddle();

        float startX = paddle.getX();

        paddle.moveRight(0.1f);

        assertTrue(
            paddle.getX() > startX
        );
    }

    @Test
    void paddleShouldNotCrossLeftWall() {

        Paddle paddle = new Paddle();

        paddle.moveLeft(100f);

        assertEquals(
            GameConfig.WALL_LEFT,
            paddle.getX(),
            0.01f
        );
    }

    @Test
    void paddleShouldNotCrossRightWall() {

        Paddle paddle = new Paddle();

        paddle.moveRight(100f);

        assertEquals(
            GameConfig.WALL_RIGHT - paddle.getWidth(),
            paddle.getX(),
            0.01f
        );
    }

    @Test
    void paddleShouldStop() {

        Paddle paddle = new Paddle();

        paddle.moveRight(0.1f);

        assertTrue(
            paddle.getVelocityX() > 0
        );

        paddle.stop();

        assertEquals(
            0,
            paddle.getVelocityX(),
            0.01f
        );
    }

    @Test
    void paddleBoundsShouldMatchPositionAndSize() {

        Paddle paddle = new Paddle();

        assertEquals(
            paddle.getX(),
            paddle.getBounds().x,
            0.01f
        );

        assertEquals(
            paddle.getY(),
            paddle.getBounds().y,
            0.01f
        );

        assertEquals(
            paddle.getWidth(),
            paddle.getBounds().width,
            0.01f
        );

        assertEquals(
            paddle.getHight(),
            paddle.getBounds().height,
            0.01f
        );
    }
}
