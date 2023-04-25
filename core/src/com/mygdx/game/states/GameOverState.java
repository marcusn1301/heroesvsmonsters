package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ds.buttons.CircleButton;
import com.mygdx.game.ds.buttons.RectangleButton;

public class GameOverState extends State{
    private final GameStateManager gsm;
    private boolean gameOverBoolean;
    private final RectangleButton gameOver;
    private final SpriteBatch batch;

    // Singleton instance
    private static GameOverState instance;
    private final RectangleButton submitButton;
    private final CircleButton exitButton;
    private String username = "Unknown player";

    private GameOverState() {
        super();
        gsm = GameStateManager.getGsm();
        gameOver = new RectangleButton(0.5f, null, (int)(Gdx.graphics.getHeight() / 1.4),"images/GameOver.png");
        batch = new SpriteBatch();
        submitButton = new RectangleButton(0.7f, null, Gdx.graphics.getHeight() / 14, "images/submit-button.png");
        exitButton = new CircleButton(70, (int)(Gdx.graphics.getWidth()/ 1.2), Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 8, "images/redExitCross.png");
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
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        this.batch.begin();
        gameOver.render(batch);
        submitButton.render(batch);
        exitButton.render(batch);
        this.batch.end();
    }


    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (submitButton.getBounds().contains(x,y)) {
                //implement firebase logic
                gsm.set(new GameMenuState());
                gsm.push(new LeaderboardState());
            } else if(exitButton.getBounds().contains(x,y)) {
                gsm.set(new GameMenuState());
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        gameOver.dispose();
        submitButton.dispose();
    }
}
