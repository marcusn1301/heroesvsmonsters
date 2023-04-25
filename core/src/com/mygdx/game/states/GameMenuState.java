package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.FireBaseInterface;
import com.mygdx.game.SoundManager;
import com.mygdx.game.ds.buttons.RectangleButton;
import com.mygdx.game.utils.Enums;

public class GameMenuState extends State {
    private final GameStateManager gsm;
    private final SoundManager soundManager = SoundManager.getInstance();
    private final RectangleButton singleplayerButton;
    private final RectangleButton multiplayerButton;

    private final Texture menuBackground;
    private final RectangleButton logo;
    private final RectangleButton settingsButton;
    private final RectangleButton trophyButton;
    int width;
    int height;

    FireBaseInterface FBIC;

    public GameMenuState() {
        super();
        gsm = GameStateManager.getGsm();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        int singleplayerButtonY = Gdx.graphics.getHeight() / 5;
        logo = new RectangleButton(0.3f, null, (int) (Gdx.graphics.getHeight() / 2.5), "images/HvsMstor.png");
        singleplayerButton = new RectangleButton(0.8f, null, singleplayerButtonY, "images/singleplayer-button.png");
        multiplayerButton = new RectangleButton(0.8f, (Integer) null, (int) (singleplayerButtonY - singleplayerButton.getHeight() - 20), "images/multiplayer-button.png");
        settingsButton = new RectangleButton(0.4f, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 200, "images/settings-button.png");
        trophyButton = new RectangleButton(0.5f, 80, Gdx.graphics.getHeight() - 210, "images/trophy.png");
        menuBackground = new Texture("images/menuBackground.png");
        System.out.println(gsm.getStates().size());
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
        batch.begin();
        batch.draw(menuBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        logo.render(batch);
        singleplayerButton.render(batch);
        multiplayerButton.render(batch);
        settingsButton.render(batch);
        trophyButton.render(batch);
        batch.end();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (singleplayerButton.getBounds().contains(x, y)) {
                soundManager.playSound("menuNavigate");
                System.out.println("Selected singleplayer");
                gsm.push(new IntroCutsceneState(Enums.GameType.SINGLEPLAYER));
            } else if (multiplayerButton.getBounds().contains(x,y)) {
                System.out.println("Selected multiplayer");
                soundManager.playSound("menuNavigate");
                gsm.set(new IntroCutsceneState(Enums.GameType.MULTIPLAYER));
            } else if (settingsButton.getBounds().contains(x,y)) {
                gsm.push(new SettingsState(Enums.SettingsBackground.CITY, Enums.GameType.MENU));
            } else if (trophyButton.getBounds().contains(x,y)) {
                gsm.set(new LeaderboardState());
            }
        }
    }

    @Override
    public void dispose() {
        singleplayerButton.dispose();
        multiplayerButton.dispose();
    }
}
