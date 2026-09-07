package io.github.breaking_bricks.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Paddle {
    private float x;
    private float y;
    private float width;
    private float hight;
    private float speed;
    private float velocityX;
    public Paddle(){
        width = 120;
        hight = 20;
        speed = 500;
        velocityX = 0;

        x = (Gdx.graphics.getWidth() - width) / 2;
        y = 40;
    }

    public void moveLeft(float delta){
        velocityX = -speed;

        x += velocityX * delta;

        if (x < 0){
            x = 0;
        }
    }

    public void moveRight(float delta) {

       velocityX = speed;
        x += velocityX * delta;
        if (x + width > Gdx.graphics.getWidth()) {
            x = Gdx.graphics.getWidth() - width;
        }
    }

    public void stop(){
        velocityX = 0;
    }

    public float getVelocityX(){
        return velocityX;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, hight);
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

    public float getHight(){
        return hight;
        }
    }
