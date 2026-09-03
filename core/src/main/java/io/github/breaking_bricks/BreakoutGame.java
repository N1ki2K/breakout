package io.github.breaking_bricks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import io.github.breaking_bricks.screens.GameScreen;
import io.github.breaking_bricks.screens.StartScreen;

public class BreakoutGame extends Game {

    @Override
    public void create() {
        showStartScreen();
    }

    public void showStartScreen() {
        changeScreen(new StartScreen(this));
    }

    public void startGame() {
        changeScreen(new GameScreen(this));
    }

    private void changeScreen(Screen newScreen) {
        Screen oldScreen = getScreen();

        setScreen(newScreen);

        if (oldScreen != null) {
            oldScreen.dispose();
        }
    }

    @Override
    public void dispose() {
        if (getScreen() != null) {
            getScreen().dispose();
        }
    }
}
