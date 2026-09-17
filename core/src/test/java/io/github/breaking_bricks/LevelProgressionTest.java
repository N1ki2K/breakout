package io.github.breaking_bricks;

import com.badlogic.gdx.utils.Array;

import io.github.breaking_bricks.objects.Brick;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LevelProgressionTest {

    @Test
    void gameShouldProgressFromLevel1ToLevel2() {

        GameState state = new GameState();

        Array<Brick> bricks =
            LevelBuilder.createBricks(
                state.getLevel()
            );

        destroyAllBricks(bricks);

        assertTrue(
            GameLogic.allBricksDestroyed(bricks)
        );

        state.nextLevel();

        assertEquals(
            2,
            state.getLevel()
        );
    }

    @Test
    void gameShouldProgressFromLevel2ToLevel3() {

        GameState state = new GameState();

        state.nextLevel();

        Array<Brick> bricks =
            LevelBuilder.createBricks(
                state.getLevel()
            );

        destroyAllBricks(bricks);

        assertTrue(
            GameLogic.allBricksDestroyed(bricks)
        );

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
    void completingLevel3ShouldMeanGameIsAtFinalLevel() {

        GameState state = new GameState();

        state.nextLevel();
        state.nextLevel();

        Array<Brick> bricks =
            LevelBuilder.createBricks(
                state.getLevel()
            );

        destroyAllBricks(bricks);

        assertTrue(
            GameLogic.allBricksDestroyed(bricks)
        );

        assertTrue(
            state.isFinalLevel()
        );

        assertEquals(
            3,
            state.getLevel()
        );
    }

    @Test
    void allThreeLevelsShouldBeCompletable() {

        GameState state = new GameState();

        for (int level = 1; level <= 3; level++) {

            Array<Brick> bricks =
                LevelBuilder.createBricks(
                    state.getLevel()
                );

            destroyAllBricks(bricks);

            assertTrue(
                GameLogic.allBricksDestroyed(bricks)
            );

            if (!state.isFinalLevel()) {
                state.nextLevel();
            }
        }

        assertEquals(
            3,
            state.getLevel()
        );

        assertTrue(
            state.isFinalLevel()
        );
    }

    private void destroyAllBricks(
        Array<Brick> bricks
    ) {

        for (Brick brick : bricks) {

            for (int hit = 0; hit < 3 && !brick.isDestroyed(); hit++) {
                brick.hit();
            }
            assertTrue(brick.isDestroyed(), "Every brick must break within three hits");
        }
    }
}
