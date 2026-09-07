package io.github.breaking_bricks.objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
import io.github.breaking_bricks.GameConfig;

public class Ball {
    private float x;
    private float y;

    private float dx;
    private float dy;

    private float size;
    private float speed;

    public Ball(){
        size = 16;
        speed = 250;

        x = GameConfig.WORLD_WIDTH / 2f;
        y = GameConfig.WORLD_HEIGHT / 2f;

        dx = speed;
        dy = speed;
    }

    public void update(float delta){
        x += dx * delta;
        y += dy * delta;

        if(x <= 0){
            bounceX();
        }
        if(x + size >= GameConfig.WORLD_WIDTH){
            x = GameConfig.WORLD_WIDTH - size;
            bounceX();
        }
        if(y + size >= GameConfig.WORLD_HEIGHT){
            y = GameConfig.WORLD_HEIGHT - size;
            bounceY();
        }

    }

    public void bounceFromPaddle(float hitPosition, float paddleVelocity){
        dy = Math.abs(dy);

        dx += hitPosition * 250;
        dx += paddleVelocity * 0.15f;

        dx = MathUtils.clamp(dx, -500, 500);

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

    public void setY(float y){
        this.y = y;
    }

    public void reset(){
        x = GameConfig.WORLD_WIDTH / 2f;
        y = GameConfig.WORLD_HEIGHT / 2f;

        dx = speed;
        dy = speed;
    }
    public void increaceSpeed(float amount){
        speed += amount;
    }
}
