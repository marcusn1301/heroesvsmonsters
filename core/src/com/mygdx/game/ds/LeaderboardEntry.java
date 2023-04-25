package com.mygdx.game.ds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Null;
import com.mygdx.game.ds.buttons.RectangleButton;
import com.mygdx.game.utils.Enums;

public class LeaderboardEntry {
    private final String name;

    private final int score;
    private final BitmapFont font;
    private int index;

    public float getLineHeight() {
        return lineHeight;
    }

    private float lineHeight;
    private final float lineWidth =  Gdx.graphics.getWidth();
    private RectangleButton trophy = null;

    public LeaderboardEntry(String name, int score)  {
        this.name = name;
        this.score = score;
        this.lineHeight = Gdx.graphics.getHeight() / 1.4f - ((Gdx.graphics.getHeight() / 16.5f) * index);
        this.font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(2f);
    }

    public int getScore() {
        return score;
    }

    public void updateLineHeight() {
        this.lineHeight = Gdx.graphics.getHeight() / 1.4f - ((Gdx.graphics.getHeight() / 16.5f) * index);
    }
    public void setIndex(int index) {
        this.index = index;
        updateLineHeight();
    }

    public void updateTrophy(int index) {
        switch (index) {
            case 0:
                trophy = new RectangleButton(0.15f, Gdx.graphics.getWidth() / 4 - 70, (int)(Gdx.graphics.getHeight() / 1.4f - ((Gdx.graphics.getHeight() / 16.5f) * (index + 0.6))),"images/trophy.png");
                break;
                case 1:
                    trophy = new RectangleButton(0.15f, Gdx.graphics.getWidth() / 4 - 70, (int)(Gdx.graphics.getHeight() / 1.4f - ((Gdx.graphics.getHeight() / 16.5f) * (index + 0.6))), "images/Trophy_2.png");
                    break;
                case 2:
                    trophy = new RectangleButton(0.15f, Gdx.graphics.getWidth() / 4 - 70, (int)(Gdx.graphics.getHeight() / 1.4f - ((Gdx.graphics.getHeight() / 16.5f) * (index + 0.6))), "images/Trophy_3.png");
                    break;
                default:
                    trophy = null;
                    break;
            }

    }

    public void render(SpriteBatch sb) {
        if (trophy != null) {
            trophy.render(sb);
        }
        font.draw(sb, name, lineWidth / 4f, lineHeight);
        font.draw(sb, Integer.toString(score), lineWidth / 2f, lineHeight);
        font.draw(sb, Integer.toString(index + 1), lineWidth / 1.385f, lineHeight);
    }

    public void dispose() {
        if (trophy != null) {
            trophy.dispose();
            font.dispose();
        }
    }
}
