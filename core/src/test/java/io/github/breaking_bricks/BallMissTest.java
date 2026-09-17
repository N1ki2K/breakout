package io.github.breaking_bricks;

import io.github.breaking_bricks.objects.Ball;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BallMissTest {

    @Test
    void ballAboveMissZoneShouldNotBeMissed() {

        Ball ball = new Ball();

        ball.setY(
            GameConfig.MISS_Y + 100
        );

        assertFalse(
            GameLogic.isBallMissed(ball)
        );
    }

    @Test
    void ballPartiallyInsideMissZoneShouldNotBeMissed() {

        Ball ball = new Ball();

        ball.setY(
            GameConfig.MISS_Y
                - ball.getSize() / 2f
        );

        assertFalse(
            GameLogic.isBallMissed(ball)
        );
    }

    @Test
    void ballCompletelyBelowMissZoneShouldBeMissed() {

        Ball ball = new Ball();

        ball.setY(
            GameConfig.MISS_Y
                - ball.getSize()
                - 1
        );

        assertTrue(
            GameLogic.isBallMissed(ball)
        );
    }

    @Test
    void missedBallShouldCauseLifeLoss() {

        Ball ball = new Ball();
        GameState state = new GameState();

        ball.setY(
            GameConfig.MISS_Y
                - ball.getSize()
                - 1
        );

        if (GameLogic.isBallMissed(ball)) {
            state.loseLife();
        }

        assertEquals(
            2,
            state.getLives()
        );
    }
}
