package io.github.breaking_bricks.objects;

import com.badlogic.gdx.math.Rectangle;

public class Brick {

    private float x;
    private float y;
    private float width;
    private float height;

    private boolean destroyed;

    public Brick(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        destroyed = false;
    }

    public void destroy(){
        destroyed = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }}
