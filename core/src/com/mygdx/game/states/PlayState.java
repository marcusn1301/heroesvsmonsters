package com.mygdx.game.states;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Board;
import com.mygdx.game.MoneySystem;
import com.mygdx.game.SoundManager;
import com.mygdx.game.entities.DisplayHero;
import com.mygdx.game.entities.HeroFactory;
import com.mygdx.game.ds.buttons.RectangleButton;
import com.mygdx.game.systems.HeroSystem;
import com.mygdx.game.systems.ProjectileMovementSystem;
import com.mygdx.game.types.HeroType;
import com.mygdx.game.utils.Enums;

import java.util.ArrayList;
import java.util.List;

public class PlayState extends State{
    private SpriteBatch batch;
    private BitmapFont font;
    private Stage stage;
    private ShapeRenderer shapeRenderer;
    private SoundManager soundManager = SoundManager.getInstance();
    private MoneySystem moneySystem;
    private boolean isPlacementAllowed = false;
    private static Engine engine;
    private Board board;
    private TextButton counterText1;
    private final RectangleButton menuButton;
    private final GameStateManager gsm;


    public PlayState() {
        //super(gsm);
        menuButton = new RectangleButton(0.5f, Gdx.graphics.getWidth() - 137, Gdx.graphics.getHeight() - 100, "Lobby-button.png");
        gsm = GameStateManager.getGsm();
        initialize();
    }

    private void initialize() {
        initializeGameEngine();
        batch = new SpriteBatch();
        moneySystem = new MoneySystem(4000);
        initFontStageAndRenderer();
        createBoard();
        soundManager.playSequence();
        //Game engine & systems
    }

    private void initializeGameEngine() {
        //Central game engine
        engine = new Engine();

        //Systems for game logic
        HeroSystem heroSystem = new HeroSystem(engine);
        ProjectileMovementSystem projectileMovementSystem = new ProjectileMovementSystem(engine);

        engine.addSystem(heroSystem);
        engine.addSystem(projectileMovementSystem);

        /*Entity spiderman = HeroFactory.createHero(HeroType.SPIDERMAN, new Vector2(50, 50));
        Entity captain = HeroFactory.createHero(HeroType.CAPTAIN_AMERICA, new Vector2(50, 50));
        engine.addEntity(spiderman);
        engine.addEntity(captain);*/
    }

    private void initFontStageAndRenderer() {
        font = new BitmapFont();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(stage);
    }

    private void createBoard() {
        board = new Board(6,9, engine);
        board.render(batch);
    }

    @Override
    public void update(float dt) {
        stage.draw();
        engine.update(dt);
    }

    public void calculateMoney() {
        float iconSize = Gdx.graphics.getWidth() / 30;
        BitmapFont counterFont = new BitmapFont();
        counterFont.getData().setScale(4);
        // First counter
        counterText1 = new TextButton(String.valueOf(moneySystem.getMoney()), new TextButton.TextButtonStyle(null, null, null, counterFont));
        counterText1.pad(2);
        counterText1.setWidth(iconSize);
        counterText1.setHeight(iconSize / 2);
        board.getRightTable().add(counterText1);

        Texture counterIconTexture1 = new Texture("coin2.png");
        Image counterIcon1 = new Image(counterIconTexture1);
        board.getRightTable().add(counterIcon1).size(iconSize, iconSize).pad(5);
        board.getRightTable().row();

        // Second counter
        TextButton counterText2 = new TextButton("2000", new TextButton.TextButtonStyle(null, null, null, counterFont));
        counterText2.pad(2);
        counterText2.setWidth(iconSize);
        counterText2.setHeight(iconSize / 2);
        board.getRightTable().add(counterText2);

        Texture counterIconTexture2 = new Texture("coin2.png");
        Image counterIcon2 = new Image(counterIconTexture2);
        board.getRightTable().add(counterIcon2).size(iconSize, iconSize).pad(5);
        board.getRightTable().row();
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //calculateMoney();

        batch.begin();
        board.render(batch);
        batch.draw(menuButton.getImg(), menuButton.getPosition().x - menuButton.getWidth() / 2f, menuButton.getPosition().y, menuButton.getWidth(), menuButton.getHeight());
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (menuButton.getBounds().contains(touchX, touchY)) {
                //Implement Game.pause();
                gsm.push(new SettingsState(Enums.SettingsBackground.CITY, Enums.GameType.SINGLEPLAYER));
            }
        }
    }


    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
        shapeRenderer.dispose();
        //TODO dispose displayHeroes
    }
}
