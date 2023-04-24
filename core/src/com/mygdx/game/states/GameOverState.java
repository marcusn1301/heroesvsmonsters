package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverState extends State{
    private boolean gameOverBoolean;
    private final Texture gameOver;
    private final Texture playAgain;
    private final Texture yes;
    private final Texture no;
    private int screenHeight;
    private int screenWidth;

    // Singleton instance
    private static GameOverState instance;

    public GameOverState() {
        gameOver = new Texture("images/GameOver.png");
        playAgain = new Texture("images/PlayAgain.png");
        yes = new Texture("images/Yes.png");
        no = new Texture("images/No.png");
    }

    // Static method to get the singleton instance
    public static GameOverState getInstance() {
        if (instance == null) {
            instance = new GameOverState();
        }
        return instance;
    }

    public boolean isGameOverBoolean() {
        return gameOverBoolean;
    }

    public void setGameOverBoolean(boolean gameOverBoolean) {
        this.gameOverBoolean = gameOverBoolean;
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        double gameOverX = screenHeight * 0.75;
        batch.draw(gameOver, (float) ((screenWidth + gameOver.getWidth())/2), (float) gameOverX, (float) (screenWidth / 2), (float) (screenWidth/6));
        batch.draw(playAgain, (float) ((screenWidth + playAgain.getWidth())/2), (float) (gameOverX - 100));
        batch.draw(yes, (float) ((screenWidth / 2) - playAgain.getWidth()), (float) (gameOverX - 200));
        batch.draw(no, (float) ((screenWidth + playAgain.getWidth())/2), (float) (gameOverX - 200));

        batch.end();

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }
}
