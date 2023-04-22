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
    SoundManager soundManager = SoundManager.getInstance();
    SpriteBatch sb;
    private final RectangleButton playButton;
    private final Texture menuBackground;
    private final RectangleButton logo;
    private final RectangleButton settingsButton;
    int width;
    int height;

    FireBaseInterface FBIC;

    public GameMenuState() {
        super();
        gsm = GameStateManager.getGsm();
        sb = new SpriteBatch();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        logo = new RectangleButton(0.3f, null, (int) (Gdx.graphics.getHeight() / 2.5), "HvsMstor.png");
        playButton = new RectangleButton(1f, null, 200, "playButton.png");
        settingsButton = new RectangleButton(0.4f, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 200, "settings-button.png");
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
        batch.end();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (playButton.getBounds().contains(x, y)) {
                gsm.push(new PlayState());
            } else if (settingsButton.getBounds().contains(x,y)) {
                gsm.push(new SettingsState(Enums.SettingsBackground.CITY, Enums.GameType.MENU));
            }
        }
    }



    @Override
    public void dispose() {
        playButton.dispose();
        System.out.print("Menu State Disposed");
    }
}
