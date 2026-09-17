package io.github.breaking_bricks;

import io.github.breaking_bricks.objects.Brick;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameFlowTest {

    @Test
    void destroyingBrickShouldIncreaseScore() {

        GameState state = new GameState();

        Brick brick = new Brick(
            100,
            100,
            120,
            40,
            1
        );

        GameLogic.hitBrick(brick, state);

        assertEquals(
            10,
            state.getScore()
        );
    }

    @Test
    void strongBrickShouldOnlyGiveScoreWhenDestroyed() {

        GameState state = new GameState();

        Brick brick = new Brick(
            100,
            100,
            120,
            40,
            5
        );

        GameLogic.hitBrick(brick, state);

        assertEquals(
            0,
            state.getScore()
        );

        GameLogic.hitBrick(brick, state);

        assertEquals(
            0,
            state.getScore()
        );

        GameLogic.hitBrick(brick, state);

        assertEquals(
            10,
            state.getScore()
        );
    }

    @Test
    void losingThreeLivesShouldCauseGameOver() {

        GameState state = new GameState();

        state.loseLife();
        state.loseLife();
        state.loseLife();

        assertEquals(
            0,
            state.getLives()
        );

        assertTrue(
            state.isGameOver()
        );
    }

    @Test
    void completingFirstLevelShouldMoveToLevelTwo() {

        GameState state = new GameState();

        state.nextLevel();

        assertEquals(
            2,
            state.getLevel()
        );

        assertFalse(
            state.isFinalLevel()
        );
    }

    @Test
    void completingSecondLevelShouldMoveToFinalLevel() {

        GameState state = new GameState();

        state.nextLevel();
        state.nextLevel();

        assertEquals(
            3,
            state.getLevel()
        );

        assertTrue(
            state.isFinalLevel()
        );
    }

    @Test
    void scoreShouldCarryAcrossLevels() {

        GameState state = new GameState();

        state.addScore(100);

        state.nextLevel();

        assertEquals(
            100,
            state.getScore()
        );

        state.addScore(50);

        state.nextLevel();

        assertEquals(
            150,
            state.getScore()
        );
    }
}
