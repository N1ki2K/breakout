package io.github.breaking_bricks;

import io.github.breaking_bricks.objects.Ball;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BallResetTest {

    @Test
    void resetBallShouldBeInsideLeftWall() {

        Ball ball = new Ball();

        ball.reset();

        assertTrue(
            ball.getX() >= GameConfig.WALL_LEFT
        );
    }

    @Test
    void resetBallShouldBeInsideRightWall() {

        Ball ball = new Ball();

        ball.reset();

        assertTrue(
            ball.getX() + ball.getSize()
                <= GameConfig.WALL_RIGHT
        );
    }

    @Test
    void resetBallShouldBeBelowTopWall() {

        Ball ball = new Ball();

        ball.reset();

        assertTrue(
            ball.getY() + ball.getSize()
                <= GameConfig.WALL_TOP
        );
    }

    @Test
    void resetBallShouldBeAboveMissZone() {

        Ball ball = new Ball();

        ball.reset();

        assertTrue(
            ball.getY()
                > GameConfig.MISS_Y
        );
    }

    @Test
    void resetBallShouldBeCenteredHorizontally() {

        Ball ball = new Ball();

        ball.reset();

        float expectedX =
            GameConfig.WALL_LEFT
                + (
                GameConfig.WALL_RIGHT
                    - GameConfig.WALL_LEFT
                    - ball.getSize()
            ) / 2f;

        assertEquals(
            expectedX,
            ball.getX(),
            0.01f
        );
    }
}
