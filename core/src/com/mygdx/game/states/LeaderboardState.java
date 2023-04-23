package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Null;
import com.mygdx.game.ds.LeaderboardEntry;
import com.mygdx.game.ds.buttons.CircleButton;
import com.mygdx.game.ds.buttons.RectangleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LeaderboardState extends State {
    private final GameStateManager gsm = GameStateManager.getGsm();
    private final ArrayList<LeaderboardEntry> entries;
    private final CircleButton exitButton;
    private final ArrayList<RectangleButton> trophies;
    private final RectangleButton highscoreHeader;
    private final BitmapFont font = new BitmapFont();

    public LeaderboardState() {
        ArrayList<LeaderboardEntry> templist = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            templist.add(new LeaderboardEntry("player " + (i + 1), (int)(Math.random()*1000*(i+1)), 100*(i+1), i));
        }
        entries = populateLeaderboardEntries(templist);
        font.setColor(Color.WHITE);
        font.getData().setScale(2f);
        highscoreHeader = new RectangleButton(0.7f, null, Gdx.graphics.getHeight() - 160, "images/highscore.png");
        trophies = new ArrayList<>();
        trophies.add(new RectangleButton(0.15f, Gdx.graphics.getWidth() / 4 - 70, (int)(Gdx.graphics.getHeight() / 1.4 - (Gdx.graphics.getHeight() / 28)),"images/trophy.png"));
        trophies.add(new RectangleButton(0.15f, Gdx.graphics.getWidth() / 4 - 70, (int)(Gdx.graphics.getHeight() / 1.4 - (Gdx.graphics.getHeight() / 9.4)), "images/trophy.png"));
        trophies.add(new RectangleButton(0.15f, Gdx.graphics.getWidth() / 4 - 70, (int)(Gdx.graphics.getHeight() / 1.4 - (Gdx.graphics.getHeight() / 5.6)), "images/trophy.png"));
        trophies.subList(0, Math.min(entries.size(), 3));
        exitButton = new CircleButton(70, Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 140, "images/redExitCross.png");
    }

    private ArrayList<LeaderboardEntry> populateLeaderboardEntries(@Null ArrayList<LeaderboardEntry> dbEntries) {
        ArrayList<LeaderboardEntry> leaderboardList = new ArrayList<>();
        Collections.sort(dbEntries, new Comparator<LeaderboardEntry>() {
            @Override
            public int compare(LeaderboardEntry entry1, LeaderboardEntry entry2) {
                return Integer.compare(entry2.getScore(), entry1.getScore());
            }
        });
        for (int i = 0; i < dbEntries.size(); i++) {
            dbEntries.get(i).setIndex(i);
            leaderboardList.add(dbEntries.get(i));
            //save to firebase
        }
        return leaderboardList;
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        highscoreHeader.render(batch);
        font.draw(batch, "Name:", Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 1.4f + Gdx.graphics.getHeight() / 14f);
        font.draw(batch, "Score:", Gdx.graphics.getWidth() / 2.87f, Gdx.graphics.getHeight() / 1.4f + Gdx.graphics.getHeight() / 14f);
        font.draw(batch, "Time:", Gdx.graphics.getWidth() / 1.62f, Gdx.graphics.getHeight() / 1.4f + Gdx.graphics.getHeight() / 14f);
        font.draw(batch, "Pos:", Gdx.graphics.getWidth() / 1.4f, Gdx.graphics.getHeight() / 1.4f + Gdx.graphics.getHeight() / 14f);
        for (LeaderboardEntry entry : entries) {
            entry.render(batch);
        }
        for (RectangleButton trophy : trophies.subList(0, Math.min(3, entries.size()))) {
            trophy.render(batch);
        }
        exitButton.render(batch);
        batch.end();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (exitButton.getBounds().contains(x,y)) {
                dispose();
                gsm.pop();
            }
        }
    }

    @Override
    public void dispose() {
        font.dispose();
        for (RectangleButton trophy : trophies) {
            trophy.dispose();
        }
        for (LeaderboardEntry entry : entries) {
            entry.dispose();
        }
        exitButton.dispose();
    }
}
