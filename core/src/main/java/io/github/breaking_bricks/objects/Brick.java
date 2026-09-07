package io.github.breaking_bricks.objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;

public class Brick {

    private float x;
    private float y;
    private float width;
    private float height;
    private Color color;
    private int hitsRemaining;

    private boolean destroyed;

    public Brick(float x, float y, float width, float height, Color color, int hitsRemaining){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;

        this.hitsRemaining = hitsRemaining;
        this.destroyed = false;
    }

    public void hit(){
        hitsRemaining--;

        if(hitsRemaining <= 0){
            destroyed = true;
        }
    }
    public int getHitsRemaining(){
        return hitsRemaining;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }

    public Color getColor(){return  color;}

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


