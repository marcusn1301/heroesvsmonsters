package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.FireBaseInterface;
import com.mygdx.game.FirebaseManager;
import com.mygdx.game.ds.buttons.CircleButton;
import com.mygdx.game.ds.buttons.RectangleButton;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;


import java.util.Random;

public class GameOverState extends State{
    private final GameStateManager gsm;
    private boolean gameOverBoolean;
    private final RectangleButton gameOver;
    private final SpriteBatch batch;

    // Singleton instance
    private static GameOverState instance;
    private final RectangleButton submitButton;
    private final CircleButton exitButton;
    private String username = "Unknown player";
    private BitmapFont font;
    private String randomName = "";
    private FireBaseInterface firebaseInterface;
    private int score = 0;


    private GameOverState() {
        super();
        this.firebaseInterface = FirebaseManager.getInstance().getFirebaseInterface();

        generateRandomName();
        gsm = GameStateManager.getGsm();
        gameOver = new RectangleButton(0.5f, null, (int)(Gdx.graphics.getHeight() / 1.4),"images/GameOver.png");
        batch = new SpriteBatch();
        submitButton = new RectangleButton(0.7f, null, Gdx.graphics.getHeight() / 14, "images/submit-button.png");
        exitButton = new CircleButton(70, (int)(Gdx.graphics.getWidth()/ 1.2), Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 8, "images/redExitCross.png");

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(5f);
    }

    // Static method to get the singleton instance
    public static GameOverState getInstance() {
        if (instance == null) {
            instance = new GameOverState();
        }
        return instance;
    }

    public boolean isGameOverBoolean() {
        return gameOverBoolean;
    }

    public void generateRandomName() {
        String[] animals = {"Narwhalrus", "Llamacorn", "Buntopus", "Penguitten", "Frogopotamus", "Sharktopus", "Hippocow", "Caterpillarilla", "Rhinocerhorse", "Octocat", "Pugicorn", "Crocoshark", "Giraffephant", "Turtlenuckle", "Ligerfly", "Squibbit", "Elephantopus", "Squirrelpion", "Wolpertinger", "Kangarooster", "Dolphowl", "Jellyfishark", "Pandaconda", "Pikaboo", "Capybunny", "Lemmingbird", "Hippopotabear", "Polar pug", "Anteagle", "Lionoceros", "Hippobear", "Moosequatch", "Rhinopig", "Gorillaphant", "Cockatooatoo", "Giraffalope", "Zebrooster", "Llama llama duck", "Chimparoo", "Elephankey", "Giraffigator", "Slothinoceros", "Koalacorn", "Porkybat", "Kangarooster", "Whalephant", "Platypusnail", "Flamingoceros", "Hamsterfly", "Tigercat", "Bumblelion", "Gorillapod", "Sheepigator", "Alpapaca", "Camelopard", "Pandaroo", "Hippopotabunny", "Squirrelshark", "Kangarilla", "Wombatcoon", "Pandowl", "Llamacat", "Tigermingo", "Tigermelon", "Kangaraptor", "Panduck", "Sharkodile", "Lionado", "Elephanana", "Dogopus", "Kangaroostrich", "Pandammit", "Rhinostrich", "Tigereagle", "Sharkow", "Barracoodile", "Platypotamus", "Eaglephant", "Squidgeroo", "Koalapiller", "Pandapilla", "Narwhalephant", "Sealrus", "Giraffecuda", "Bumblebeaver", "Kittenbird", "Porkypine", "Giraffearoo", "Hamsterpup", "Beetlebug", "Crocobunny", "Giraffephin", "Sharkatoa", "Squirreltopus", "Dolphigator", "Slothopotamus", "Pigloo", "Rhinopanda", "Kangaroostrich", "Turtleyawesome", "Hippocow", "Turtlemingo", "Jaguarturtle", "Pandalf", "Crocobear", "Giraffewhale", "Elephantosaurus", "Alpacaface", "Camelosaurus", "Hippopie", "Squirrelepig", "Kangaroonkey", "Whalephantom", "Platypusaurus", "Flamingozilla", "Hamsteratops", "Tigerventoraptor", "Gorilladillo", "Sheepurtle", "Pandahorse", "Lionow", "Elephanalope"};
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            String animal = animals[random.nextInt(animals.length)];
            int number = random.nextInt(100);
            System.out.println(animal + " " + number);
            randomName = animal + "" + number;
        }
    }

    public void setGameOverBoolean(boolean gameOverBoolean) {
        this.gameOverBoolean = gameOverBoolean;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        this.batch.begin();
        gameOver.render(batch);
        submitButton.render(batch);
        GlyphLayout yourName = new GlyphLayout(font, "Your name is:");
        GlyphLayout name = new GlyphLayout(font, randomName);
        font.getData().setScale(5f);
        font.draw(this.batch, yourName, Gdx.graphics.getWidth()/2f - yourName.width/2, Gdx.graphics.getHeight()/2.2f);
        font.getData().setScale(7f);
        font.draw(this.batch, name, (Gdx.graphics.getWidth()/2f) - name.width/2, Gdx.graphics.getHeight() / 3f);
        this.batch.end();
    }


    @Override
    public void handleInput() {

        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (submitButton.getBounds().contains(x,y)) {
                //implement firebase logic

                firebaseInterface.SetValueInDb("HighScore", randomName, getScore());


                gsm.set(new GameMenuState());
                gsm.push(new LeaderboardState());
            } else if(exitButton.getBounds().contains(x,y)) {
                gsm.set(new GameMenuState());
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        gameOver.dispose();
        submitButton.dispose();
    }
}
