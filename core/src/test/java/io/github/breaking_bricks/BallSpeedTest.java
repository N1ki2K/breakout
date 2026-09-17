package io.github.breaking_bricks;

import io.github.breaking_bricks.objects.Ball;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BallSpeedTest {

    @Test
    void ballShouldStartWithSpeed250() {

        Ball ball = new Ball();

        assertEquals(
            250,
            ball.getSpeed(),
            0.01f
        );
    }

    @Test
    void ballSpeedShouldIncrease() {

        Ball ball = new Ball();

        ball.increaceSpeed(50);

        assertEquals(
            300,
            ball.getSpeed(),
            0.01f
        );
    }

    @Test
    void ballShouldUseNewSpeedAfterReset() {

        Ball ball = new Ball();

        ball.increaceSpeed(50);
        ball.reset();

        float startX = ball.getX();
        float startY = ball.getY();

        ball.update(1f);

        assertEquals(300f, ball.getDx(), 0.01f);
        assertEquals(300f, ball.getDy(), 0.01f);
        assertEquals(startX + 300f, ball.getX(), 0.01f);
        assertEquals(startY + 300f, ball.getY(), 0.01f);
    }

    @Test
    void speedShouldIncreaseMultipleTimes() {

        Ball ball = new Ball();

        ball.increaceSpeed(50);
        ball.increaceSpeed(50);

        assertEquals(
            350,
            ball.getSpeed(),
            0.01f
        );
    }
}
