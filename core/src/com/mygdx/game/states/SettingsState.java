package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.ds.buttons.CircleButton;
import com.mygdx.game.ds.buttons.RectangleButton;
import com.mygdx.game.utils.Enums;
import com.mygdx.game.utils.SettingsData;
import com.mygdx.game.ds.Slider;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsState extends State {
    private final GameStateManager gsm;
    private final BitmapFont font;
    private ArrayList<Float> bgColor;
    private Boolean isBlack;
    private final CircleButton exitButton;
    private final RectangleButton leaveGameButton;
    private final RectangleButton settingsHeader;
    private final Slider audioBar;
    private final Slider sfxBar;
    private Texture bgImage;
    private final Enums.GameType gameType;

    public SettingsState(Enums.SettingsBackground bg, Enums.GameType type) {
        gsm = GameStateManager.getGsm();
        font = new BitmapFont();
        font.getData().setScale(3f);
        gameType = type;

        switch (bg) {
            case MENU:
                bgColor = new ArrayList<>(Arrays.asList(0.0f, 0.0f, 0.7f, 1f));
                bgImage = new Texture("images/City.jpg");
                isBlack = false;
                break;
            case CITY:
                bgColor = new ArrayList<>(Arrays.asList(0.35f, 0.35f, 0.35f, 1f));
                bgImage = new Texture("images/City.jpg");
                isBlack = false;
                break;
        }

        leaveGameButton = new RectangleButton(0.7f, Gdx.graphics.getWidth() / 2, 30, "images/leave-game.png");
        exitButton = new CircleButton(70, Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 140, "images/redExitCross.png");
        settingsHeader = new RectangleButton(1f, null, Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 4, "images/settings.png");
        audioBar = new Slider(Enums.SliderType.AUDIO, 800, 30, 100);
        sfxBar = new Slider(Enums.SliderType.SFX, 800, 30, -100);
        if (isBlack) {
            font.setColor(Color.BLACK);
        } else {
            font.setColor(Color.WHITE);
        }
    }

    private void renderText(SpriteBatch sb) {
        font.draw(sb, "Audio", Gdx.graphics.getWidth() / 2f - 400, Gdx.graphics.getHeight() / 2f + 170);
        font.draw(sb, "SFX", Gdx.graphics.getWidth() / 2f - 400, Gdx.graphics.getHeight() / 2f -30);
    }

    private void renderLeaveGame(SpriteBatch sb) {
        switch (gameType) {
            case SINGLEPLAYER:
            case MULTIPLAYER:
                sb.draw(leaveGameButton.getImg(), leaveGameButton.getPosition().x - leaveGameButton.getWidth() / 2f, leaveGameButton.getPosition().y, leaveGameButton.getWidth(), leaveGameButton.getHeight());
                break;
            default:
                break;
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        ScreenUtils.clear(bgColor.get(0), bgColor.get(1), bgColor.get(2), bgColor.get(3));
        sb.begin();
        sb.draw(bgImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderText(sb);
        settingsHeader.render(sb);
        exitButton.render(sb);
        renderLeaveGame(sb);
        sb.end();
        audioBar.render();
        sfxBar.render();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (exitButton.getBounds().contains(touchX, touchY)) {
                dispose();
                gsm.pop();
            } else if(leaveGameButton.getBounds().contains(touchX, touchY)) {
                gsm.set(new GameMenuState());
            } else {
                SettingsData settingsData = SettingsData.loadSettings();
                settingsData.saveSettings();
            }
        }
    }

    @Override
    public void dispose() {
        font.dispose();
        audioBar.dispose();
        sfxBar.dispose();
    }


}
