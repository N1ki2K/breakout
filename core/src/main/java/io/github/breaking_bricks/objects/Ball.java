package io.github.breaking_bricks.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Ball {
    private float x;
    private float y;

    private float dx;
    private float dy;

    private float size;

    public Ball(){
        size = 16;
        x = Gdx.graphics.getWidth() / 2;
        y = Gdx.graphics.getHeight() /2;

        dx = 250;
        dy = 250;
    }

    public void update(float delta){
        x += dx * delta;
        y += dy * delta;

        if(x <= 0){
            bounceX();
        }
        if(x + size >= Gdx.graphics.getWidth()){
            x = Gdx.graphics.getWidth() - size;
            bounceX();
        }
        if(y + size >= Gdx.graphics.getHeight()){
            y = Gdx.graphics.getHeight() - size;
            bounceY();
        }

    }
    public void bounceX(){
        dx = -dx;
    }

    public void bounceY(){
        dy = -dy;
    }
    public Rectangle getBounds(){
        return new Rectangle(x, y, size, size);
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getSize(){
        return size;
    }

    public float getDy(){
        return dy;
    }

    public void reset(){
        x = Gdx.graphics.getWidth() / 2f;
        y = Gdx.graphics.getHeight() / 2f;

        dx = 250;
        dy = 250;
    }
}
