package com.mygdx.game.ds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.HeroesVsMonsters;
import com.mygdx.game.utils.Enums;
import com.mygdx.game.utils.SettingsData;

public class Slider {
    private final ShapeRenderer shapeRenderer;
    private final Enums.SliderType sliderType;
    private final float width;
    private final float height;
    private float sliderValue;
    private final float yPos;
    private final float barX;
    private final float barY;
    private final float knobY;
    private float knobX;
    private float isTouched;
    private final SettingsData settingsData;

    public Slider(Enums.SliderType type, float barWidth, float barHeight, float yPos) {
        shapeRenderer = new ShapeRenderer();
        float SCREEN_WIDTH = Gdx.graphics.getWidth();
        float SCREEN_HEIGHT = Gdx.graphics.getHeight();

        settingsData = SettingsData.loadSettings();

        switch (type) {
            case AUDIO:
                sliderValue = settingsData.AUDIO / 100.0f;
                break;
            case SFX:
                sliderValue = settingsData.SFX / 100.0f;
                break;
            default:
                break;
        }

        //Local variables
        this.sliderType = type;
        this.width = barWidth;
        this.height = barHeight;
        this.yPos = yPos;
        this.barX = SCREEN_WIDTH / 2 - width / 2;
        this.barY = SCREEN_HEIGHT / 2 - height / 2;
        this.knobY  = SCREEN_HEIGHT / 2 - height / 2;
        this.isTouched = -1;
        updateKnobX();
    }

    private void updateKnobX() {
        knobX = barX + sliderValue * (width - height);
    }

    private void drawSliderBody() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.8f, 0.8f, 0.8f, 1);
        shapeRenderer.rect(barX, barY + yPos, width, height);
        shapeRenderer.end();
    }

    private void drawSliderKnob() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.5f, 0.5f, 0.5f, 1);
        shapeRenderer.circle(knobX + height / 2, knobY + height / 2 + yPos, height);
        shapeRenderer.end();
    }

    private boolean checkBounds(float touchX, float touchY) {
        return touchX >= knobX && touchX <= knobX + height && touchY >= knobY + yPos && touchY <= knobY + height + yPos;
    }

    private void handleUpdateSlider() {
        float touchX = Gdx.input.getX();
        float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
        float newValue = (touchX - barX - height / 2) / (width - height);
        if (isTouched < 0.1f && isTouched >= 0f) {
            if (touchX > barX + width) {
                sliderValue = 1f;
            } else if (touchX < barX) {
                sliderValue = 0f;
            } else {
                sliderValue = newValue;
                sliderValue = MathUtils.clamp(sliderValue, 0, 1);
            }
            isTouched = 0f;
        } else {
            if (checkBounds(touchX, touchY)) {
                sliderValue = newValue;
                sliderValue = MathUtils.clamp(sliderValue, 0, 1);
                isTouched = 0f;
            }
        }
    }

    private void handleInactive() {
        if (isTouched < 0.1f && isTouched >= 0) {
            isTouched += Gdx.graphics.getDeltaTime();
        } else if (isTouched >= 0){
            switch (sliderType) {
                case AUDIO:
                    settingsData.AUDIO = ((int) (sliderValue * 100));
                    break;
                case SFX:
                    settingsData.SFX = ((int) (sliderValue * 100));
            }
            settingsData.saveSettings();
            isTouched = -1;
        }
    }

    public void render() {
        updateKnobX();
        drawSliderBody();
        drawSliderKnob();
        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            handleUpdateSlider();
        } else {
            handleInactive();
        }
    }

    public void dispose () {
        shapeRenderer.dispose();
    }
}
