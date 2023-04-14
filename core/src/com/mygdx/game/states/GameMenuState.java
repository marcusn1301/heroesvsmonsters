package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.FireBaseInterface;

public class GameMenuState extends State {
    private GameStateManager gsm;
    SpriteBatch batch;
    Texture startButton;
    Texture lobbyButton;

    Texture logo;
    Texture img;
    int width;
    int height;

    FireBaseInterface FBIC;

    public GameMenuState() {
        super();
        gsm = GameStateManager.getGsm();
        batch = new SpriteBatch();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        startButton = new Texture("Start-button.png");
        lobbyButton = new Texture("Lobby-button.png");
        logo = new Texture("HvsMstor.png");
        img = new Texture("badlogic.jpg");
    }

    public void create() {
        /*FBIC.SomeFunction();
        FBIC.FirstFirebaseTest();
        FBIC.SetOnValueChangedListener();
        FBIC.SetValueInDb("message", "Updated message!");*/
        logo = new Texture("HvsMstor.png");
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch batch) {
        ScreenUtils.clear(Color.BLACK);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        batch.begin();

        batch.draw(logo, centerX - logo.getWidth() / 16f, height * 0.55f, logo.getWidth() / 8f, logo.getHeight() / 8f);

        batch.draw(startButton, centerX - startButton.getWidth() / 4f, height * 0.2f, startButton.getWidth() / 2f, startButton.getHeight() / 2f);
        batch.draw(lobbyButton, centerX - lobbyButton.getWidth() / 4f, height * 0.1f, lobbyButton.getWidth() / 2f, lobbyButton.getHeight() / 2f);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            handleInput();
        }

        /*if (Gdx.input.isTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            if (isStartButtonClicked(x, y) || isLobbyButtonClicked(x,y) ) {
                handleInput();
            }
        }*/
    }

    private boolean isStartButtonClicked(int x, int y) {
        int buttonX = (width/2) - (height/2);
        int buttonY = height/4;
        int buttonWidth = startButton.getWidth()/2;
        int buttonHeight = startButton.getHeight()/2;
        return Gdx.input.justTouched() && buttonX <= Gdx.input.getX() && Gdx.input.getX() <= buttonX + buttonWidth &&
                buttonY <= Gdx.graphics.getHeight() - Gdx.input.getY() && Gdx.graphics.getHeight() - Gdx.input.getY() <= buttonY + buttonHeight;
    }

    private boolean isLobbyButtonClicked(int x, int y) {
        int buttonX = (width/2) - (height/2);
        int buttonY = height/6;
        int buttonWidth = lobbyButton.getWidth()/2;
        int buttonHeight = lobbyButton.getHeight()/2;
        System.out.println(buttonWidth);
        System.out.println(buttonHeight);

        return (x >= buttonX && x <= buttonX + buttonWidth && y >= buttonY && y <= buttonY + buttonHeight);
    }

    @Override
    public void render(SpriteBatch batch) {
        ScreenUtils.clear(Color.BLUE);
        batch.begin();
        batch.draw(startButton, (width/2) - (height/2), height/4, startButton.getWidth()/2, startButton.getHeight()/2);
        batch.draw(lobbyButton, (width/2) - (height/2), height/6, lobbyButton.getWidth()/2, lobbyButton.getHeight()/2);

        batch.end();


        /*
        if (Gdx.input.isTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            if (isStartButtonClicked(x, y) || isLobbyButtonClicked(x,y) ) {
                handleInput();
            }
        }*/
    }
/*
    private boolean isStartButtonClicked(int x, int y) {
        int buttonX = (width/2) - (height/2);
        int buttonY = height/4;
        int buttonWidth = startButton.getWidth()/2;
        int buttonHeight = startButton.getHeight()/2;
        return (x >= buttonX && x <= buttonX + buttonWidth && y >= buttonY && y <= buttonY + buttonHeight);
    }

    private boolean isLobbyButtonClicked(int x, int y) {
        int buttonX = (width/2) - (height/2);
        int buttonY = height/6;
        int buttonWidth = lobbyButton.getWidth()/2;
        int buttonHeight = lobbyButton.getHeight()/2;
        System.out.println(buttonWidth);
        System.out.println(buttonHeight);

        return (x >= buttonX && x <= buttonX + buttonWidth && y >= buttonY && y <= buttonY + buttonHeight);
    }*/

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            gsm.push(new GameViewState());
        }
    }

    @Override
    public void dispose() {
        startButton.dispose();
        lobbyButton.dispose();
        System.out.print("Menu State Disposed");
    }
}
