package com.mygdx.game.ds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class LeaderboardEntry {
    private final String name;

    private final int score;
    private final int time;
    private final Rectangle bounds;
    private final BitmapFont font;
    private int index;
    private float lineHeight;
    private final float lineWidth =  Gdx.graphics.getWidth();

    public int getScore() {
        return score;
    }

    public void updateLineHeight() {
        this.lineHeight = Gdx.graphics.getHeight() / 1.4f - ((Gdx.graphics.getHeight() / 14f) * index);
    }
    public void setIndex(int index) {
        this.index = index;
        updateLineHeight();
    }

    public LeaderboardEntry(String name, int score, int time, int index)  {
        this.name = name;
        this.score = score;
        this.time = time;
        this.index = index;
        this.lineHeight = Gdx.graphics.getHeight() / 1.4f - ((Gdx.graphics.getHeight() / 14f) * index);
        this.bounds = new Rectangle();
        this.font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2f);
    }

    public void render(SpriteBatch sb) {
        font.draw(sb, name, lineWidth / 4f, lineHeight);
        font.draw(sb, Integer.toString(score), lineWidth / 2.85f, lineHeight);
        font.draw(sb, Integer.toString(time), lineWidth / 1.61f, lineHeight);
        font.draw(sb, Integer.toString(index + 1), lineWidth / 1.385f, lineHeight);
    }

    public void dispose() {

    }
}
