package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.FireBaseInterface;
import com.mygdx.game.SoundManager;

public class GameMenuState extends State {
    private GameStateManager gsm;
    SoundManager soundManager = SoundManager.getInstance();
    SpriteBatch sb;
    Texture startButton;
    Texture lobbyButton;
    Texture menuButton;
    private Texture menuBackground;

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
        menuButton = new Texture("Menu-button.png");
        logo = new Texture("HvsMstor.png");
        menuBackground = new Texture("menuBackground.png");
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



        // Calculate the logo position and size
        float logoWidth = width / 2.5f;
        float logoHeight = logoWidth * ((float) logo.getHeight() / logo.getWidth());
        float logoX = (width - logoWidth) / 2f;
        float logoY = ((height - logoHeight) / 2f) + height / 5f;


        // Calculate the button positions and sizes
        float buttonWidth = width / 7f;
        float startButtonHeight = buttonWidth * ((float) startButton.getHeight() / startButton.getWidth());
        float lobbyButtonHeight = buttonWidth * ((float) lobbyButton.getHeight() / lobbyButton.getWidth());
        float menuButtonHeight = buttonWidth * ((float) menuButton.getHeight() / menuButton.getWidth());


        float buttonX = (width - buttonWidth) / 2f;
        float startButtonY = height * 0.3f - startButtonHeight / 2f;
        float lobbyButtonY = height * 0.12f - lobbyButtonHeight / 2f;
        float menuButtonY = height * 0.21f - menuButtonHeight / 2f;



        batch.begin();
        batch.draw(menuBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.draw(logo, logoX, logoY, logoWidth, logoHeight);
        batch.draw(startButton, buttonX, startButtonY, buttonWidth, startButtonHeight);
        batch.draw(lobbyButton, buttonX, lobbyButtonY, buttonWidth, lobbyButtonHeight);
        batch.draw(menuButton, buttonX, menuButtonY, buttonWidth, menuButtonHeight);


        batch.end();




    }

    private boolean isStartButtonClicked(int x, int y) {
        float buttonWidth = width / 7f;
        float startButtonHeight = buttonWidth * ((float) startButton.getHeight() / startButton.getWidth());
        float buttonX = (width - buttonWidth) / 2f;
        float startButtonY = height * 0.3f - startButtonHeight / 2f;

        return Gdx.input.justTouched() && buttonX <= x && x <= buttonX + buttonWidth &&
                startButtonY <= Gdx.graphics.getHeight() - y && Gdx.graphics.getHeight() - y <= startButtonY + startButtonHeight;
    }

    private boolean isLobbyButtonClicked(int x, int y) {
        float buttonWidth = width / 7f;
        float lobbyButtonHeight = buttonWidth * ((float) lobbyButton.getHeight() / lobbyButton.getWidth());
        float buttonX = (width - buttonWidth) / 2f;
        float lobbyButtonY = height * 0.12f - lobbyButtonHeight / 2f;

        return Gdx.input.justTouched() && buttonX <= x && x <= buttonX + buttonWidth &&
                lobbyButtonY <= Gdx.graphics.getHeight() - y && Gdx.graphics.getHeight() - y <= lobbyButtonY + lobbyButtonHeight;
    }

    private boolean isMenuButtonClicked(int x, int y) {
        float buttonWidth = width / 7f;
        float menuButtonHeight = buttonWidth * ((float) menuButton.getHeight() / menuButton.getWidth());
        float buttonX = (width - buttonWidth) / 2f;
        float menuButtonY = height * 0.21f - menuButtonHeight / 2f;

        return Gdx.input.justTouched() && buttonX <= x && x <= buttonX + buttonWidth &&
                menuButtonY <= Gdx.graphics.getHeight() - y && Gdx.graphics.getHeight() - y <= menuButtonY + menuButtonHeight;
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            if (isStartButtonClicked(x, y)) {
                soundManager.playSound("menuNavigate");
                gsm.push(new IntroCutsceneState());
            }
            if (isLobbyButtonClicked(x, y)) {
                gsm.push(new DummyState());
            }
            if (isMenuButtonClicked(x, y)) {
                gsm.push(new StartState());
            }
        }
    }



    @Override
    public void dispose() {
        startButton.dispose();
        lobbyButton.dispose();
        menuButton.dispose();
        System.out.print("Menu State Disposed");
    }
}
