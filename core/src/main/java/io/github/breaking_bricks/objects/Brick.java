package io.github.breaking_bricks.objects;

import com.badlogic.gdx.math.Rectangle;

public class Brick {

    private float x;
    private float y;
    private float width;
    private float height;

    private int textureType;

    private boolean destroyed;

    public Brick(
        float x,
        float y,
        float width,
        float height,
        int textureType
    ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.textureType = textureType;

        destroyed = false;
    }

    public void hit() {

        switch (textureType) {

            case 5:
                textureType = 4;
                break;

            case 4:
                textureType = 3;
                break;

            default:
                destroyed = true;
                break;
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public Rectangle getBounds() {
        return new Rectangle(
            x,
            y,
            width,
            height
        );
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getTextureType() {
        return textureType;
    }
}
