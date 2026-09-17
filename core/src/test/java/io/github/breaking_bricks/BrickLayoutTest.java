package io.github.breaking_bricks;

import com.badlogic.gdx.utils.Array;
import io.github.breaking_bricks.objects.Brick;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BrickLayoutTest {

    @Test
    void level1BricksShouldNotOverlap() {

        Array<Brick> bricks =
            LevelBuilder.createBricks(1);

        assertNoOverlaps(bricks);
    }

    @Test
    void level2BricksShouldNotOverlap() {

        Array<Brick> bricks =
            LevelBuilder.createBricks(2);

        assertNoOverlaps(bricks);
    }

    @Test
    void level3BricksShouldNotOverlap() {

        Array<Brick> bricks =
            LevelBuilder.createBricks(3);

        assertNoOverlaps(bricks);
    }

    @Test
    void allBricksShouldHavePositiveWidthAndHeight() {

        Array<Brick> bricks =
            LevelBuilder.createBricks(1);

        for (Brick brick : bricks) {

            assertTrue(
                brick.getWidth() > 0
            );

            assertTrue(
                brick.getHeight() > 0
            );
        }
    }

    @Test
    void allBricksShouldBeInsideHorizontalWalls() {

        Array<Brick> bricks =
            LevelBuilder.createBricks(3);

        for (Brick brick : bricks) {

            assertTrue(
                brick.getX()
                    >= GameConfig.WALL_LEFT
            );

            assertTrue(
                brick.getX()
                    + brick.getWidth()
                    <= GameConfig.WALL_RIGHT
            );
        }
    }

    private void assertNoOverlaps(
        Array<Brick> bricks
    ) {

        for (int i = 0; i < bricks.size; i++) {

            for (int j = i + 1; j < bricks.size; j++) {

                Brick brickA = bricks.get(i);
                Brick brickB = bricks.get(j);

                assertFalse(
                    brickA.getBounds().overlaps(
                        brickB.getBounds()
                    )
                );
            }
        }
    }
}
