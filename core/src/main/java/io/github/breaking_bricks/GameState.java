package io.github.breaking_bricks;

public class GameState {

    private int score;
    private int lives;
    private int level;

    public GameState() {
        score = 0;
        lives = 3;
        level = 1;
    }

    public void addScore(int amount) {
        score += amount;
    }

    public void loseLife() {
        lives--;
    }

    public void nextLevel() {
        if (level < 3) {
            level++;
        }
    }

    public boolean isGameOver() {
        return lives <= 0;
    }

    public boolean isFinalLevel() {
        return level >= 3;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public int getLevel() {
        return level;
    }
}
