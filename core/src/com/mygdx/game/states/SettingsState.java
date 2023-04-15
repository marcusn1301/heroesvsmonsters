package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsState extends State {
    private GameStateManager gsm;
    private ShapeRenderer shapeRenderer;
    public enum SettingsBackground {
        MENU,
        CITY,
    }
    private ArrayList<Float> bgColor;
    private Boolean isBlack;

    private BitmapFont font;

    public SettingsState(SettingsBackground bg) {
        gsm = GameStateManager.getGsm();
        shapeRenderer = new ShapeRenderer();
        switch (bg) {
            case MENU:
                bgColor = new ArrayList<>(Arrays.asList(0.0f, 0.0f, 0.7f, 1f));
                isBlack = false;
                break;
            case CITY:
                bgColor = new ArrayList<>(Arrays.asList(0.35f, 0.35f, 0.35f, 1f));
                isBlack = false;
                break;
        }
        font = new BitmapFont();
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
        renderHeader(sb);
        renderExitButton();
        sb.end();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isTouched()) {
            return;
        }
    }

    @Override
    public void dispose() {

    }

    private void renderHeader(SpriteBatch sb) {
        font.getData().setScale(8f);
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, "Settings");
        font.draw(sb, "Settings", Gdx.graphics.getWidth() / 2 - glyphLayout.width / 2, Gdx.graphics.getHeight() - 80);
    }

    private void renderExitButton() {

    }

}
