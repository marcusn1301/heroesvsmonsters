package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.ds.buttons.RectangleButton;
import com.mygdx.game.utils.Enums;

public class GameOverState extends State{
    private final GameStateManager gsm;
    private boolean gameOverBoolean;
    private final Texture gameOver;
    private final Texture playAgain;
    private final RectangleButton yes;
    private final RectangleButton no;
    private int screenHeight = Gdx.graphics.getHeight();
    private int screenWidth = Gdx.graphics.getWidth();
    private SpriteBatch batch;
    private float playAgainY;
    private float playAgainX;

    // Singleton instance
    private static GameOverState instance;
    private final RectangleButton submitButton;

    public GameOverState() {
        super();
        gsm = GameStateManager.getGsm();
        gameOver = new Texture("images/GameOver.png");
        playAgain = new Texture("images/PlayAgain.png");
        Texture yesTexture = new Texture("images/Yes.png");

        playAgainX = (screenWidth - playAgain.getWidth()) / 2;
        playAgainY = (screenHeight / 2) - (playAgain.getHeight());

        float yesX = (screenWidth / 2) - yesTexture.getWidth() - 40;
        float yesY = (int) playAgainY - playAgain.getHeight() * 2;
        yes = new RectangleButton(1F, (int) yesX, (int) yesY, "images/Yes.png");

        float noX = (screenWidth / 2) + 10;
        float noY = (int) (playAgainY - playAgain.getHeight() * 2);
        no = new RectangleButton(1F, (int) noX, (int) noY, "images/No.png");
        batch = new SpriteBatch();

        submitButton = new RectangleButton(1f, null, Gdx.graphics.getHeight() / 14, "images/submit-button.png");
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
        float gameOverX = (screenWidth - gameOver.getWidth()) / 2;
        float gameOverY = (screenHeight + gameOver.getHeight()) / 2;
        batch.draw(gameOver, gameOverX, gameOverY);
        batch.draw(playAgain, playAgainX, playAgainY);
        submitButton.render(batch);
        yes.render(batch);
        no.render(batch);

        this.batch.end();

    }


    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (yes.getBounds().contains(x, y)) {
                gsm.push(new PlayState());
                System.out.println("Clicked on yes");
                setGameOverBoolean(false);
            } else if (no.getBounds().contains(x,y)) {
                gsm.push(new GameMenuState());
                setGameOverBoolean(false);
            } else if (submitButton.getBounds().contains(x,y)) {
                //implement firebase logic
                gsm.pop();
                gsm.push(new GameMenuState());
                gsm.push(new LeaderboardState());
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        gameOver.dispose();
        playAgain.dispose();
        yes.dispose();
        no.dispose();
    }
}
