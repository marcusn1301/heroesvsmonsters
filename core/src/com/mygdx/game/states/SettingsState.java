package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.sprites.CircleButton;
import com.mygdx.game.utils.SettingsData;
import com.mygdx.game.utils.Slider;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class SettingsState extends State {
    private GameStateManager gsm;
    private BitmapFont font;
    public enum SettingsBackground {
        MENU,
        CITY,
    }
    private ArrayList<Float> bgColor;
    private Boolean isBlack;
    private final CircleButton exitButton;
    private final Slider audioBar;
    private final Slider sfxBar;
    private Texture bgImage;

    public SettingsState(SettingsBackground bg) {
        gsm = GameStateManager.getGsm();
        font = new BitmapFont();

        switch (bg) {
            case MENU:
                bgColor = new ArrayList<>(Arrays.asList(0.0f, 0.0f, 0.7f, 1f));
                bgImage = new Texture("City.jpg");
                isBlack = false;
                break;
            case CITY:
                bgColor = new ArrayList<>(Arrays.asList(0.35f, 0.35f, 0.35f, 1f));
                bgImage = new Texture("City.jpg");
                isBlack = false;
                break;
        }

        exitButton = new CircleButton(70, Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 140, "redExitCross.png");
        audioBar = new Slider(Slider.SliderType.AUDIO, 800, 30, 100);
        sfxBar = new Slider(Slider.SliderType.SFX, 800, 30, -100);
        if (isBlack) {
            font.setColor(Color.BLACK);
        } else {
            font.setColor(Color.WHITE);
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
        renderHeader(sb);
        renderExitButton(sb);
        sb.end();
        renderAudioBar();
        renderSfxBar();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (exitButton.getBounds().contains(touchX, touchY)) {
                //dispose();
                gsm.pop();
            } else {
                SettingsData settingsData = SettingsData.loadSettings();
                settingsData.saveSettings();
            }
        }
    }

    @Override
    public void dispose() {
        audioBar.dispose();
        sfxBar.dispose();
        font.dispose();
    }

    private void renderHeader(SpriteBatch sb) {
        font.getData().setScale(8f);
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, "Settings");
        font.draw(sb, "Settings", Gdx.graphics.getWidth() / 2 - glyphLayout.width / 2, Gdx.graphics.getHeight() - 80);
    }

    private void renderExitButton(SpriteBatch sb) {
        sb.draw(exitButton.getImg(), exitButton.getPosition().x - 40, exitButton.getPosition().y - 40, 80, 80);
    }

    private void renderAudioBar() {
        audioBar.render();
    }

    private void renderSfxBar() {
        sfxBar.render();
    }

}
