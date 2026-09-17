package io.github.breaking_bricks;

import io.github.breaking_bricks.objects.Ball;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BallTest {

    @Test
    void ballShouldMoveWhenUpdated() {

        Ball ball = new Ball();

        float startX = ball.getX();
        float startY = ball.getY();

        ball.update(0.1f);

        assertTrue(ball.getX() > startX);
        assertTrue(ball.getY() > startY);
    }

    @Test
    void ballShouldBounceFromRightWall() {

        Ball ball = new Ball();

        ball.update(100f);

        assertEquals(
            GameConfig.WALL_RIGHT - ball.getSize(),
            ball.getX(),
            0.01f
        );
    }

    @Test
    void ballShouldBounceFromLeftWall() {

        Ball ball = new Ball();

        ball.bounceX();

        ball.update(100f);

        assertEquals(
            GameConfig.WALL_LEFT,
            ball.getX(),
            0.01f
        );
    }

    @Test
    void ballShouldBounceFromTopWall() {

        Ball ball = new Ball();

        ball.update(100f);

        assertEquals(
            GameConfig.WALL_TOP - ball.getSize(),
            ball.getY(),
            0.01f
        );

        assertTrue(ball.getDy() < 0);
    }

    @Test
    void ballShouldBounceUpFromPaddle() {

        Ball ball = new Ball();

        ball.bounceY();

        assertTrue(ball.getDy() < 0);

        ball.bounceFromPaddle(
            0,
            0
        );

        assertTrue(ball.getDy() > 0);
    }

    @Test
    void ballShouldResetToCenter() {

        Ball ball = new Ball();

        ball.update(0.5f);

        ball.reset();

        float expectedX =
            GameConfig.WALL_LEFT
                + (
                GameConfig.WALL_RIGHT
                    - GameConfig.WALL_LEFT
                    - ball.getSize()
            ) / 2f;

        float expectedY =
            GameConfig.WORLD_HEIGHT / 2f;

        assertEquals(
            expectedX,
            ball.getX(),
            0.01f
        );

        assertEquals(
            expectedY,
            ball.getY(),
            0.01f
        );
    }

    @Test
    void ballBoundsShouldMatchBallPositionAndSize() {

        Ball ball = new Ball();

        assertEquals(
            ball.getX(),
            ball.getBounds().x,
            0.01f
        );

        assertEquals(
            ball.getY(),
            ball.getBounds().y,
            0.01f
        );

        assertEquals(
            ball.getSize(),
            ball.getBounds().width,
            0.01f
        );

        assertEquals(
            ball.getSize(),
            ball.getBounds().height,
            0.01f
        );
    }
}
