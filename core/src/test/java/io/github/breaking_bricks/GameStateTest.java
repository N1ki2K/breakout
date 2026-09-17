package io.github.breaking_bricks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

    @Test
    void gameShouldStartWithDefaultValues() {

        GameState state = new GameState();

        assertEquals(0, state.getScore());
        assertEquals(3, state.getLives());
        assertEquals(1, state.getLevel());
    }

    @Test
    void scoreShouldIncrease() {

        GameState state = new GameState();

        state.addScore(10);

        assertEquals(
            10,
            state.getScore()
        );
    }

    @Test
    void lifeShouldDecrease() {

        GameState state = new GameState();

        state.loseLife();

        assertEquals(
            2,
            state.getLives()
        );
    }

    @Test
    void gameShouldBeOverWhenLivesReachZero() {

        GameState state = new GameState();

        state.loseLife();
        state.loseLife();
        state.loseLife();

        assertTrue(
            state.isGameOver()
        );
    }

    @Test
    void gameShouldNotBeOverWithLivesRemaining() {

        GameState state = new GameState();

        state.loseLife();

        assertFalse(
            state.isGameOver()
        );
    }

    @Test
    void levelShouldIncrease() {

        GameState state = new GameState();

        state.nextLevel();

        assertEquals(
            2,
            state.getLevel()
        );
    }

    @Test
    void levelShouldNotGoAboveThree() {

        GameState state = new GameState();

        state.nextLevel();
        state.nextLevel();
        state.nextLevel();
        state.nextLevel();

        assertEquals(
            3,
            state.getLevel()
        );
    }

    @Test
    void levelThreeShouldBeFinalLevel() {

        GameState state = new GameState();

        state.nextLevel();
        state.nextLevel();

        assertTrue(
            state.isFinalLevel()
        );
    }

    @Test
    void levelOneShouldNotBeFinalLevel() {

        GameState state = new GameState();

        assertFalse(
            state.isFinalLevel()
        );
    }
}
