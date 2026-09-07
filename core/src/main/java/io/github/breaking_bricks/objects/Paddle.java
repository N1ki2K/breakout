package io.github.breaking_bricks.objects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;

import io.github.breaking_bricks.GameConfig;

public class Paddle {
    private float x;
    private float y;
    private float width;
    private float hight;
    private float speed;
    private float velocityX;
    private final Texture paddleTexture;
    public Paddle(){

        paddleTexture = new Texture(
            Gdx.files.internal("textures/paddle/png/paddle.png")
        );

        paddleTexture.setFilter(
            Texture.TextureFilter.Linear,
            Texture.TextureFilter.Linear
        );

        width = 120;
        hight = 20;
        speed = 500;
        velocityX = 0;

        x = (GameConfig.WORLD_WIDTH - width) / 2;
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
        if (x + width > GameConfig.WORLD_WIDTH) {
            x = GameConfig.WORLD_WIDTH - width;
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
