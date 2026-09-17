package io.github.breaking_bricks;

import io.github.breaking_bricks.objects.Brick;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BrickTest {

    @Test
    void texture1BrickShouldBeDestroyedAfterOneHit() {

        Brick brick = new Brick(
            100,
            100,
            120,
            40,
            1
        );

        brick.hit();

        assertTrue(brick.isDestroyed());
    }

    @Test
    void texture2BrickShouldBeDestroyedAfterOneHit() {

        Brick brick = new Brick(
            100,
            100,
            120,
            40,
            2
        );

        brick.hit();

        assertTrue(brick.isDestroyed());
    }

    @Test
    void texture3BrickShouldBeDestroyedAfterOneHit() {

        Brick brick = new Brick(
            100,
            100,
            120,
            40,
            3
        );

        brick.hit();

        assertTrue(brick.isDestroyed());
    }

    @Test
    void texture4BrickShouldChangeToTexture3AfterFirstHit() {

        Brick brick = new Brick(
            100,
            100,
            120,
            40,
            4
        );

        brick.hit();

        assertFalse(brick.isDestroyed());
        assertEquals(3, brick.getTextureType());
    }

    @Test
    void texture4BrickShouldBeDestroyedAfterTwoHits() {

        Brick brick = new Brick(
            100,
            100,
            120,
            40,
            4
        );

        brick.hit();
        brick.hit();

        assertTrue(brick.isDestroyed());
    }

    @Test
    void texture5BrickShouldChangeToTexture4AfterFirstHit() {

        Brick brick = new Brick(
            100,
            100,
            120,
            40,
            5
        );

        brick.hit();

        assertFalse(brick.isDestroyed());
        assertEquals(4, brick.getTextureType());
    }

    @Test
    void texture5BrickShouldChangeToTexture3AfterSecondHit() {

        Brick brick = new Brick(
            100,
            100,
            120,
            40,
            5
        );

        brick.hit();
        brick.hit();

        assertFalse(brick.isDestroyed());
        assertEquals(3, brick.getTextureType());
    }

    @Test
    void texture5BrickShouldBeDestroyedAfterThreeHits() {

        Brick brick = new Brick(
            100,
            100,
            120,
            40,
            5
        );

        brick.hit();
        brick.hit();
        brick.hit();

        assertTrue(brick.isDestroyed());
    }

    @Test
    void brickBoundsShouldMatchItsPositionAndSize() {

        Brick brick = new Brick(
            100,
            200,
            120,
            40,
            1
        );

        assertEquals(100, brick.getBounds().x);
        assertEquals(200, brick.getBounds().y);
        assertEquals(120, brick.getBounds().width);
        assertEquals(40, brick.getBounds().height);
    }
}
