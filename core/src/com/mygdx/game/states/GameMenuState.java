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
    SpriteBatch sb;
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
        sb = new SpriteBatch();
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
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch batch) {
        ScreenUtils.clear(Color.BLACK);
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        batch.begin();

        batch.draw(logo, centerX - logo.getWidth() / 16f, height * 0.42f, logo.getWidth() / 4f, logo.getHeight() / 4f);

        //batch.draw(startButton, centerX - startButton.getWidth() / 3f, height * 0.2f, startButton.getWidth() * 1.5f, startButton.getHeight() * 1.5f);
        batch.draw(startButton, centerX - startButton.getWidth() / 4f, height * 0.2f, startButton.getWidth() / 1.5f, startButton.getHeight() / 1.5f);
        batch.draw(lobbyButton, centerX - lobbyButton.getWidth() / 4f, height * 0.2f, lobbyButton.getWidth() / 1.5f, lobbyButton.getHeight() / 1.5f);
        batch.end();



        /*if (Gdx.input.isTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            if (isStartButtonClicked(x, y) || isLobbyButtonClicked(x,y) ) {
                handleInput();
            }
        }*/
    }

    private boolean isStartButtonClicked(int x, int y) {
        int buttonX = (int) (width / 2f - startButton.getWidth() / 3f);
        int buttonY = (int) (height * 0.2f);
        int buttonWidth = startButton.getWidth() / 2;
        int buttonHeight = startButton.getHeight() / 2;
        return Gdx.input.justTouched() && buttonX <= x && x <= buttonX + buttonWidth &&
                buttonY <= Gdx.graphics.getHeight() - y && Gdx.graphics.getHeight() - y <= buttonY + buttonHeight;
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
    public void handleInput() {
        if (Gdx.input.isTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            if (isStartButtonClicked(x, y) || isLobbyButtonClicked(x,y) ) {
                gsm.push(new PlayState());
            }
        }
        /*if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            gsm.push(new GameViewState());
        }*/
    }

    @Override
    public void dispose() {
        startButton.dispose();
        lobbyButton.dispose();
        System.out.print("Menu State Disposed");
    }
}
