package io.github.breaking_bricks;

import io.github.breaking_bricks.objects.Ball;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaddleBounceTest {

    @Test
    void ballShouldBounceUpFromPaddle() {

        Ball ball = new Ball();

        ball.bounceY();

        assertTrue(ball.getDy() < 0);

        ball.bounceFromPaddle(0, 0);

        assertTrue(ball.getDy() > 0);
    }

    @Test
    void hittingRightSideShouldPushBallRight() {

        Ball ball = new Ball();

        float before = ball.getDx();

        ball.bounceFromPaddle(1f, 0);

        assertTrue(
            ball.getDx() > before
        );
    }

    @Test
    void hittingLeftSideShouldPushBallLeft() {

        Ball ball = new Ball();

        ball.bounceX();

        float before = ball.getDx();

        ball.bounceFromPaddle(-1f, 0);

        assertTrue(
            ball.getDx() < before
        );
    }

    @Test
    void movingPaddleRightShouldAddRightVelocity() {

        Ball ball = new Ball();

        float before = ball.getDx();

        ball.bounceFromPaddle(
            0,
            500
        );

        assertTrue(
            ball.getDx() > before
        );
    }

    @Test
    void horizontalSpeedShouldNotExceedMaximum() {

        Ball ball = new Ball();

        ball.bounceFromPaddle(
            1f,
            10000
        );

        assertTrue(
            ball.getDx() <= 500
        );
    }

    @Test
    void horizontalSpeedShouldNotGoBelowMinimum() {

        Ball ball = new Ball();

        ball.bounceX();

        ball.bounceFromPaddle(
            -1f,
            -10000
        );

        assertTrue(
            ball.getDx() >= -500
        );
    }
}
