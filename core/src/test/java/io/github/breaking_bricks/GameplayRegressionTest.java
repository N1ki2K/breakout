package io.github.breaking_bricks;

import com.badlogic.gdx.utils.Array;
import io.github.breaking_bricks.objects.Ball;
import io.github.breaking_bricks.objects.Brick;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class GameplayRegressionTest {

    @ParameterizedTest
    @CsvSource({"1, 30, 0, 0, 300", "2, 20, 5, 0, 200", "3, 30, 8, 10, 300"})
    void levelsHaveExpectedDurabilityAndScore(int level, int count, int twoHit,
                                            int threeHit, int expectedScore) {
        Array<Brick> bricks = LevelBuilder.createBricks(level);
        GameState state = new GameState();
        int actualTwoHit = 0;
        int actualThreeHit = 0;

        assertEquals(count, bricks.size);
        assertFalse(GameLogic.allBricksDestroyed(bricks));
        for (Brick brick : bricks) {
            int type = brick.getTextureType();
            if (type == 4) actualTwoHit++;
            if (type == 5) actualThreeHit++;
            int hits = type == 5 ? 3 : type == 4 ? 2 : 1;
            int scoreBefore = state.getScore();
            for (int hit = 1; hit <= hits; hit++) {
                GameLogic.hitBrick(brick, state);
                assertEquals(hit == hits, brick.isDestroyed());
                assertEquals(scoreBefore + (hit == hits ? 10 : 0), state.getScore());
            }
        }
        assertEquals(twoHit, actualTwoHit);
        assertEquals(threeHit, actualThreeHit);
        assertEquals(expectedScore, state.getScore());
        assertTrue(GameLogic.allBricksDestroyed(bricks));
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -1, 0, 4, Integer.MAX_VALUE})
    void invalidLevelsUseExistingFallbackLayout(int level) {
        // LevelBuilder currently falls back to 30 one-hit bricks, rather than throwing.
        Array<Brick> bricks = LevelBuilder.createBricks(level);
        assertEquals(30, bricks.size);
        assertFalse(GameLogic.allBricksDestroyed(bricks));
        for (Brick brick : bricks) {
            assertEquals(1, brick.getTextureType());
            assertFalse(brick.isDestroyed());
            assertTrue(brick.getX() >= GameConfig.WALL_LEFT);
            assertTrue(brick.getX() + brick.getWidth() <= GameConfig.WALL_RIGHT);
            assertTrue(brick.getY() + brick.getHeight() <= GameConfig.WALL_TOP);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void destroyedBricksCannotAwardScoreAgain(int type) {
        Brick brick = new Brick(100, 100, 120, 40, type);
        GameState state = new GameState();
        for (int hit = 0; hit < 6; hit++) {
            GameLogic.hitBrick(brick, state);
        }
        assertTrue(brick.isDestroyed());
        assertEquals(10, state.getScore());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void rebuildingLevelCreatesFreshIndependentBricks(int level) {
        Array<Brick> first = LevelBuilder.createBricks(level);
        Array<Brick> second = LevelBuilder.createBricks(level);
        assertNotSame(first, second);
        for (int index = 0; index < first.size; index++) {
            Brick original = first.get(index);
            Brick fresh = second.get(index);
            int texture = fresh.getTextureType();
            for (int hit = 0; hit < 3; hit++) original.hit();
            assertNotSame(original, fresh);
            assertFalse(fresh.isDestroyed());
            assertEquals(texture, fresh.getTextureType());
        }
    }

    @Test
    void repeatedLifeLossReachesGameOverWithoutChangingScoreOrLevel() {
        GameState state = new GameState();
        state.addScore(150);
        state.nextLevel();
        for (int expectedLives = 2; expectedLives >= 0; expectedLives--) {
            state.loseLife();
            assertEquals(expectedLives, state.getLives());
            assertEquals(expectedLives == 0, state.isGameOver());
            assertEquals(150, state.getScore());
            assertEquals(2, state.getLevel());
        }
    }

    @Test
    void levelChangesPreserveRemainingLives() {
        GameState state = new GameState();
        state.loseLife();
        state.nextLevel();
        state.nextLevel();
        assertEquals(2, state.getLives());
        assertEquals(3, state.getLevel());
    }

    @Test
    void ballIsMissedOnlyAfterItsTopPassesMissLine() {
        Ball ball = new Ball();
        ball.setY(GameConfig.MISS_Y - ball.getSize());
        assertFalse(GameLogic.isBallMissed(ball));
        ball.setY(ball.getY() - 0.01f);
        assertTrue(GameLogic.isBallMissed(ball));
        ball.reset();
        assertFalse(GameLogic.isBallMissed(ball));
    }
}
