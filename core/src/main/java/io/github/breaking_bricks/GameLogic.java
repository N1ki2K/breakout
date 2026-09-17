package io.github.breaking_bricks;

import com.badlogic.gdx.utils.Array;
import io.github.breaking_bricks.objects.Brick;
import io.github.breaking_bricks.objects.Ball;

public class GameLogic {

    public static boolean allBricksDestroyed(
        Array<Brick> bricks
    ) {
        if (bricks.isEmpty()) {
            return false;
        }

        for (Brick brick : bricks) {

            if (!brick.isDestroyed()) {
                return false;
            }
        }

        return true;
    }

    public static boolean isBallMissed(Ball ball) {
        return ball.getY() + ball.getSize() < GameConfig.MISS_Y;
    }

    public static void hitBrick(Brick brick, GameState state) {
        if (brick.isDestroyed()) {
            return;
        }

        brick.hit();
        if (brick.isDestroyed()) {
            state.addScore(10);
        }
    }

}
