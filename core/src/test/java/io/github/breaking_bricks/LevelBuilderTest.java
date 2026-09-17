package io.github.breaking_bricks;

import com.badlogic.gdx.utils.Array;
import io.github.breaking_bricks.objects.Brick;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LevelBuilderTest {

    @Test
    void level1ShouldCreate30Bricks() {

        Array<Brick> bricks =
            LevelBuilder.createBricks(1);

        assertEquals(
            30,
            bricks.size
        );
    }

    @Test
    void level2ShouldCreate20Bricks() {

        Array<Brick> bricks =
            LevelBuilder.createBricks(2);

        assertEquals(
            20,
            bricks.size
        );
    }

    @Test
    void level3ShouldCreate30Bricks() {

        Array<Brick> bricks =
            LevelBuilder.createBricks(3);

        assertEquals(
            30,
            bricks.size
        );
    }

    @Test
    void level1ShouldOnlyUseOneHitBrickTypes() {

        Array<Brick> bricks =
            LevelBuilder.createBricks(1);

        for (Brick brick : bricks) {

            int textureType =
                brick.getTextureType();

            assertTrue(
                textureType >= 1
                    && textureType <= 3
            );
        }
    }

    @Test
    void level2ShouldContainTexture4Bricks() {

        Array<Brick> bricks =
            LevelBuilder.createBricks(2);

        boolean foundTexture4 = false;

        for (Brick brick : bricks) {

            if (brick.getTextureType() == 4) {
                foundTexture4 = true;
                break;
            }
        }

        assertTrue(foundTexture4);
    }

    @Test
    void level3ShouldContainTexture5Bricks() {

        Array<Brick> bricks =
            LevelBuilder.createBricks(3);

        boolean foundTexture5 = false;

        for (Brick brick : bricks) {

            if (brick.getTextureType() == 5) {
                foundTexture5 = true;
                break;
            }
        }

        assertTrue(foundTexture5);
    }

    @Test
    void bricksShouldStayInsideLeftWall() {

        Array<Brick> bricks =
            LevelBuilder.createBricks(1);

        for (Brick brick : bricks) {

            assertTrue(
                brick.getX()
                    >= GameConfig.WALL_LEFT
            );
        }
    }

    @Test
    void bricksShouldStayInsideRightWall() {

        Array<Brick> bricks =
            LevelBuilder.createBricks(1);

        for (Brick brick : bricks) {

            assertTrue(
                brick.getX()
                    + brick.getWidth()
                    <= GameConfig.WALL_RIGHT
            );
        }
    }

    @Test
    void bricksShouldStayBelowTopWall() {

        Array<Brick> bricks =
            LevelBuilder.createBricks(1);

        for (Brick brick : bricks) {

            assertTrue(
                brick.getY()
                    + brick.getHeight()
                    <= GameConfig.WALL_TOP
            );
        }
    }
}
