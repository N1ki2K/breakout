package io.github.breaking_bricks;

import com.badlogic.gdx.utils.Array;
import io.github.breaking_bricks.objects.Brick;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameLogicTest {

    @Test
    void shouldReturnFalseWhenBricksRemain() {

        Array<Brick> bricks = new Array<>();

        bricks.add(
            new Brick(
                100,
                100,
                120,
                40,
                1
            )
        );

        assertFalse(
            GameLogic.allBricksDestroyed(bricks)
        );
    }

    @Test
    void shouldReturnTrueWhenAllBricksAreDestroyed() {

        Array<Brick> bricks = new Array<>();

        Brick brick1 =
            new Brick(
                100,
                100,
                120,
                40,
                1
            );

        Brick brick2 =
            new Brick(
                250,
                100,
                120,
                40,
                1
            );

        brick1.hit();
        brick2.hit();

        bricks.add(brick1);
        bricks.add(brick2);

        assertTrue(
            GameLogic.allBricksDestroyed(bricks)
        );
    }

    @Test
    void shouldReturnFalseWhenOnlySomeBricksAreDestroyed() {

        Array<Brick> bricks = new Array<>();

        Brick destroyedBrick =
            new Brick(
                100,
                100,
                120,
                40,
                1
            );

        Brick activeBrick =
            new Brick(
                250,
                100,
                120,
                40,
                1
            );

        destroyedBrick.hit();

        bricks.add(destroyedBrick);
        bricks.add(activeBrick);

        assertFalse(
            GameLogic.allBricksDestroyed(bricks)
        );
    }

    @Test
    void emptyBrickListShouldNotCountAsDestroyed() {

        Array<Brick> bricks = new Array<>();

        assertFalse(
            GameLogic.allBricksDestroyed(bricks)
        );
    }

}
