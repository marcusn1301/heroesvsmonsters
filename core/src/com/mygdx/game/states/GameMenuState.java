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
    private SoundManager soundManager = SoundManager.getInstance();
    private final RectangleButton playButton;
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
        logo = new RectangleButton(0.3f, null, (int) (Gdx.graphics.getHeight() / 2.5), "HvsMstor.png");
        playButton = new RectangleButton(1f, null, 150, "playButton.png");
        settingsButton = new RectangleButton(0.4f, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 200, "settings-button.png");
        trophyButton = new RectangleButton(0.5f, 80, Gdx.graphics.getHeight() - 210, "trophy.png");
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
        batch.begin();
        batch.draw(menuBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        logo.render(batch);
        playButton.render(batch);
        settingsButton.render(batch);
        trophyButton.render(batch);
        batch.end();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (playButton.getBounds().contains(x, y)) {
                soundManager.playSound("menuNavigate");
                gsm.push(new IntroCutsceneState());
            } else if (settingsButton.getBounds().contains(x,y)) {
                gsm.push(new SettingsState(Enums.SettingsBackground.CITY, Enums.GameType.MENU));
            } else if (trophyButton.getBounds().contains(x,y)) {
                gsm.push(new LeaderBoardState());
            }
        }
    }

    @Override
    public void dispose() {
        playButton.dispose();
        System.out.print("Menu State Disposed");
    }
}
