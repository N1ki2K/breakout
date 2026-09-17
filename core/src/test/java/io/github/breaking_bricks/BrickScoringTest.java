package io.github.breaking_bricks;

import io.github.breaking_bricks.objects.Brick;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BrickScoringTest {

    @Test
    void texture1ShouldGiveScoreAfterOneHit() {

        GameState state = new GameState();

        Brick brick = new Brick(
            100,
            100,
            120,
            40,
            1
        );

        hitBrick(brick, state);

        assertTrue(brick.isDestroyed());
        assertEquals(10, state.getScore());
    }

    @Test
    void texture2ShouldGiveScoreAfterOneHit() {

        GameState state = new GameState();

        Brick brick = new Brick(
            100,
            100,
            120,
            40,
            2
        );

        hitBrick(brick, state);

        assertTrue(brick.isDestroyed());
        assertEquals(10, state.getScore());
    }

    @Test
    void texture3ShouldGiveScoreAfterOneHit() {

        GameState state = new GameState();

        Brick brick = new Brick(
            100,
            100,
            120,
            40,
            3
        );

        hitBrick(brick, state);

        assertTrue(brick.isDestroyed());
        assertEquals(10, state.getScore());
    }

    @Test
    void texture4ShouldNotGiveScoreAfterFirstHit() {

        GameState state = new GameState();

        Brick brick = new Brick(
            100,
            100,
            120,
            40,
            4
        );

        hitBrick(brick, state);

        assertFalse(brick.isDestroyed());
        assertEquals(3, brick.getTextureType());
        assertEquals(0, state.getScore());
    }

    @Test
    void texture4ShouldGiveScoreAfterSecondHit() {

        GameState state = new GameState();

        Brick brick = new Brick(
            100,
            100,
            120,
            40,
            4
        );

        hitBrick(brick, state);
        hitBrick(brick, state);

        assertTrue(brick.isDestroyed());
        assertEquals(10, state.getScore());
    }

    @Test
    void texture5ShouldNotGiveScoreUntilThirdHit() {

        GameState state = new GameState();

        Brick brick = new Brick(
            100,
            100,
            120,
            40,
            5
        );

        hitBrick(brick, state);

        assertEquals(4, brick.getTextureType());
        assertEquals(0, state.getScore());

        hitBrick(brick, state);

        assertEquals(3, brick.getTextureType());
        assertEquals(0, state.getScore());

        hitBrick(brick, state);

        assertTrue(brick.isDestroyed());
        assertEquals(10, state.getScore());
    }

    private void hitBrick(
        Brick brick,
        GameState state
    ) {

        GameLogic.hitBrick(brick, state);
    }
}
