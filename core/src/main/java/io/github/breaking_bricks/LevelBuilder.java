package io.github.breaking_bricks;

import com.badlogic.gdx.utils.Array;

import io.github.breaking_bricks.objects.Brick;

public class LevelBuilder {

    public static Array<Brick> createBricks(int level) {

        Array<Brick> bricks = new Array<>();

        int columns = 10;

        float brickHeight = 40;
        float gap = 12;
        float sideMargin = 40;

        int rows = switch (level) {
            case 1 -> 3;
            case 2 -> 4;
            case 3 -> 5;
            default -> 3;
        };

        float availableWidth =
            GameConfig.WALL_RIGHT
                - GameConfig.WALL_LEFT
                - sideMargin * 2
                - gap * (columns - 1);

        float brickWidth =
            availableWidth / columns;

        float startX =
            GameConfig.WALL_LEFT + sideMargin;

        float startY =
            GameConfig.WALL_TOP - 100;

        for (int row = 0; row < rows; row++) {

            for (int column = 0; column < columns; column++) {

                boolean createBrick = switch (level) {
                    case 1 -> true;

                    case 2 ->
                        (row + column) % 2 == 0;

                    case 3 ->
                        column >= row
                            && column < columns - row;

                    default -> true;
                };

                if (!createBrick) {
                    continue;
                }

                float x =
                    startX
                        + column * (brickWidth + gap);

                float y =
                    startY
                        - row * (brickHeight + gap);

                int textureType;

                switch (level) {

                    case 1:
                        textureType = (row % 3) + 1;
                        break;

                    case 2:
                        if (row == 0) {
                            textureType = 4;
                        } else {
                            textureType = (row % 3) + 1;
                        }
                        break;

                    case 3:
                        if (row == 0) {
                            textureType = 5;
                        } else if (row == 1) {
                            textureType = 4;
                        } else {
                            textureType = (row % 3) + 1;
                        }
                        break;

                    default:
                        textureType = 1;
                        break;
                }

                bricks.add(
                    new Brick(
                        x,
                        y,
                        brickWidth,
                        brickHeight,
                        textureType
                    )
                );
            }
        }

        return bricks;
    }
}
