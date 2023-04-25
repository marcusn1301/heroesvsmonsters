package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Null;
import com.mygdx.game.FireBaseInterface;
import com.mygdx.game.FirebaseManager;
import com.mygdx.game.ds.LeaderboardEntry;
import com.mygdx.game.ds.buttons.CircleButton;
import com.mygdx.game.ds.buttons.RectangleButton;
import com.mygdx.game.utils.Enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class LeaderboardState extends State {
    private final GameStateManager gsm = GameStateManager.getGsm();
    private ArrayList<LeaderboardEntry> entries = new ArrayList<>();
    private final CircleButton exitButton;
    private final RectangleButton highscoreHeader;
    private final BitmapFont font = new BitmapFont();
    private final Texture brickBackground;
    private final Texture highscoreBoard;
    private final FireBaseInterface firebaseInterface;
    private ArrayList<Map<String, Object>>  highScoreList = new ArrayList<>();
    private boolean updateLeaderboard = false;


    public LeaderboardState() {
        this.firebaseInterface = FirebaseManager.getInstance().getFirebaseInterface();
        firebaseInterface.SetOnValueChangedListener("name");

        brickBackground = new Texture("images/brickWall.png");
        highscoreBoard = new Texture("images/highscoreBoard.png");
        font.setColor(Color.BLACK);
        font.getData().setScale(5f);
        highscoreHeader = new RectangleButton(0.7f, null, Gdx.graphics.getHeight() - 170, "images/highscore.png");
        exitButton = new CircleButton(70, Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 140, "images/redExitCross.png");
        setHighScoreList();
    }

    public void setHighScoreList() {
        fetchData("HighScore");
        entries = populateLeaderboardEntries();
    }

    public void fetchData(String target) {
        firebaseInterface.getDataFromDatabase(target, new FireBaseInterface.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(ArrayList<Map<String, Object>> values) {
                highScoreList = values;
                updateLeaderboard = true;
            }
            @Override
            public void onError(Exception exception) {
                System.out.println(exception);
            }
        });
    }

    private ArrayList<LeaderboardEntry> populateLeaderboardEntries() {
        ArrayList<LeaderboardEntry> leaderboardList = new ArrayList<>();
        for (int i = 0; i < highScoreList.size(); i++) {
            Map<String, Object> entry = highScoreList.get(i);
            String name = (String) entry.get("name");
            Long score = (Long) entry.get("score");
            leaderboardList.add(new LeaderboardEntry(name, score.intValue()));
        }
        Collections.sort(leaderboardList, new Comparator<LeaderboardEntry>() {
            @Override
            public int compare(LeaderboardEntry entry1, LeaderboardEntry entry2) {
                return Integer.compare(entry2.getScore(), entry1.getScore());
            }
        });
        for (int i = 0; i < leaderboardList.size(); i++) {
            LeaderboardEntry entry = leaderboardList.get(i);
            entry.setIndex(i);
            entry.updateTrophy(i);
        }
        updateLeaderboard = false;
        return new ArrayList<>(leaderboardList.subList(0,Math.min(leaderboardList.size(), 10)));
    }

    @Override
    public void update(float dt) {
        handleInput();
        if (updateLeaderboard) {
            setHighScoreList();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.draw(brickBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(highscoreBoard, (float) ((Gdx.graphics.getWidth() - highscoreBoard.getWidth() * 1.3 ) / 2), 40, (float) (highscoreBoard.getWidth() * 1.3), (float) (highscoreBoard.getHeight() * 1.32));

        highscoreHeader.render(batch);
        font.draw(batch, "Name:", Gdx.graphics.getWidth() / 4.1f, Gdx.graphics.getHeight() / 1.4f + Gdx.graphics.getHeight() / 11f);
        font.draw(batch, "Score:", Gdx.graphics.getWidth() / 2f - Gdx.graphics.getWidth() / 28f, Gdx.graphics.getHeight() / 1.4f + Gdx.graphics.getHeight() / 11f);
        font.draw(batch, "Pos:", Gdx.graphics.getWidth() / 1.4f, Gdx.graphics.getHeight() / 1.4f + Gdx.graphics.getHeight() / 11f);
        for (LeaderboardEntry entry : entries) {
            entry.render(batch);
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
        for (LeaderboardEntry entry : entries) {
            entry.dispose();
        }
        exitButton.dispose();
    }
}
