package io.github.breaking_bricks;

import com.badlogic.gdx.utils.Array;
import io.github.breaking_bricks.objects.Ball;
import io.github.breaking_bricks.objects.Brick;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/** Tests pass when an unwanted action or outcome is prevented. */
class NegativeGameplayTest {

    @ParameterizedTest(name = "Level {0}: one surviving brick prevents completion")
    @ValueSource(ints = {1, 2, 3})
    void shouldNotCompleteLevelWithOneDamagedBrickRemaining(int level) {
        Array<Brick> bricks = LevelBuilder.createBricks(level);
        Brick survivor = new Brick(100, 100, 120, 40, 5);
        bricks.add(survivor);
        for (int index = 0; index < bricks.size - 1; index++) {
            for (int hit = 0; hit < 3; hit++) {
                bricks.get(index).hit();
            }
            assertTrue(bricks.get(index).isDestroyed());
        }

        // Two hits are insufficient for an armored brick, even if all others are gone.
        survivor.hit();
        survivor.hit();
        assertFalse(survivor.isDestroyed());
        assertFalse(GameLogic.allBricksDestroyed(bricks),
            "A surviving brick must prevent level completion");

        survivor.hit();
        assertTrue(GameLogic.allBricksDestroyed(bricks));
    }

    @Test
    @DisplayName("Hitting an already destroyed brick must not award any score")
    void shouldIgnoreBrickDestroyedBeforeScoringAttempt() {
        Brick brick = new Brick(100, 100, 120, 40, 1);
        brick.hit();
        GameState state = new GameState();
        state.addScore(50);

        GameLogic.hitBrick(brick, state);

        assertEquals(50, state.getScore(), "An inactive brick cannot award points");
        assertEquals(3, state.getLives());
        assertEquals(1, state.getLevel());
    }

    @Test
    @DisplayName("Repeated attempts to advance past level 3 must not create level 4")
    void shouldRejectProgressionBeyondFinalLevel() {
        GameState state = new GameState();
        state.nextLevel();
        state.nextLevel();

        for (int attempt = 0; attempt < 10; attempt++) {
            state.nextLevel();
            assertEquals(3, state.getLevel(), "There is no fourth level");
            assertTrue(state.isFinalLevel());
        }
    }

    @ParameterizedTest(name = "At the {0} wall: moving away must not trigger another bounce")
    @ValueSource(strings = {"left", "right", "top"})
    void shouldNotBounceAgainWhileMovingAwayFromWall(String wall) {
        Ball ball = new Ball();
        if (wall.equals("left")) {
            ball.bounceX();
        }
        // First update reaches a wall and turns the ball back into the playfield.
        ball.update(100f);
        if (wall.equals("left")) {
            assertEquals(GameConfig.WALL_LEFT, ball.getX(), 0.001f);
            assertTrue(ball.getDx() > 0);
        } else if (wall.equals("right")) {
            assertEquals(GameConfig.WALL_RIGHT - ball.getSize(), ball.getX(), 0.001f);
            assertTrue(ball.getDx() < 0);
        } else {
            assertEquals(GameConfig.WALL_TOP - ball.getSize(), ball.getY(), 0.001f);
            assertTrue(ball.getDy() < 0);
        }
        float dx = ball.getDx();
        float dy = ball.getDy();

        // Still touching the wall, but already travelling away: do not reverse again.
        ball.update(0f);

        assertEquals(dx, ball.getDx(), 0.001f);
        assertEquals(dy, ball.getDy(), 0.001f);
    }

    @Test
    @DisplayName("Zero elapsed time must not move a ball in the playfield")
    void shouldNotMoveWhenNoTimeHasPassed() {
        Ball ball = new Ball();
        float x = ball.getX();
        float y = ball.getY();

        ball.update(0f);

        assertEquals(x, ball.getX(), 0.001f);
        assertEquals(y, ball.getY(), 0.001f);
    }
}
