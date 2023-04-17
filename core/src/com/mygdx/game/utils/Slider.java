package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.HeroesVsMonsters;

import javax.swing.JViewport;

public class Slider {
    public enum SliderType {
        AUDIO,
        SFX
    }
    private float width;
    private float height;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch sb;
    private float sliderValue;
    private float SCREEN_WIDTH;
    private float SCREEN_HEIGHT;
    private float yPos;

    public Slider(SliderType type, float barWidth, float barHeight, float yPos) {
        sb = HeroesVsMonsters.getSb();
        shapeRenderer = new ShapeRenderer();
        switch (type) {
            case AUDIO:
                break;
            case SFX:
                break;
        }
        SCREEN_WIDTH = Gdx.graphics.getWidth();
        SCREEN_HEIGHT = Gdx.graphics.getHeight();
        this.width = barWidth;
        this.height = barHeight;
        this.yPos = yPos;
    }

    public void render() {
        float barX = SCREEN_WIDTH / 2 - width / 2;
        float barY = SCREEN_HEIGHT / 2 - height / 2;
        float knobX = barX + sliderValue * (width - height);
        float knobY = SCREEN_HEIGHT / 2 - height / 2;

        drawSliderBody(barX, barY);
        drawSliderKnob(knobX, knobY);

        handleInput(knobX, knobY, barX);
    }

    private void drawSliderBody(float barX, float barY) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.8f, 0.8f, 0.8f, 1); // set color of the slider bar
        shapeRenderer.rect(barX, barY + yPos, width, height);
        shapeRenderer.end();
    }

    private void drawSliderKnob(float knobX, float knobY) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1); // set color of the slider knob
        shapeRenderer.circle(knobX + height / 2, knobY + height / 2 + yPos, height);
        shapeRenderer.end();
    }

    private void handleInput(float knobX, float knobY, float barX) {
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (touchX >= knobX && touchX <= knobX + height && touchY >= knobY + yPos && touchY <= knobY + height + yPos) {
                sliderValue = (touchX - barX - height / 2) / (width - height);
                sliderValue = MathUtils.clamp(sliderValue, 0, 1); // clamp value between 0 and 1
            }
        }
    }

    public void dispose () {
        sb.dispose();
        shapeRenderer.dispose();
    }
}
