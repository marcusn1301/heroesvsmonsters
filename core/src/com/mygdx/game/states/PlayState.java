package com.mygdx.game.states;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.components.WaveComponent;
import com.mygdx.game.ds.buttons.CircleButton;
import com.mygdx.game.systems.MoneySystem;
import com.mygdx.game.SoundManager;
import com.mygdx.game.components.AttackComponent;
import com.mygdx.game.components.HeroComponent;
import com.mygdx.game.components.MonsterComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.ProjectileComponent;
import com.mygdx.game.components.SpriteComponent;
import com.mygdx.game.ds.Board;
import com.mygdx.game.ds.buttons.RectangleButton;
import com.mygdx.game.systems.CollisionSystem;
import com.mygdx.game.systems.HeroSystem;
import com.mygdx.game.systems.MonsterMovementSystem;
import com.mygdx.game.systems.ProjectileMovementSystem;
import com.mygdx.game.systems.WaveSystem;
import com.mygdx.game.utils.Enums;

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
    private final GameStateManager gsm;
    private final CircleButton settingsButton;
    private int screenWidth = Gdx.graphics.getWidth();
    private int screenHeight = Gdx.graphics.getHeight();
    private int monsterCount = 0;
    private int waveCount = 0;
    private int totalKills = 0;
    private int monsterToKill = 0;
    private int score = 0;
    private boolean gameOver = false;

    public PlayState() {
        //super(gsm);
        settingsButton = new CircleButton(40, (Gdx.graphics.getWidth() - 140),  60, "images/settings-button.png");
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
        WaveSystem waveSystem = new WaveSystem(engine);
        MonsterMovementSystem monsterMovementSystem = new MonsterMovementSystem(engine);
        CollisionSystem collisionSystem = new CollisionSystem(engine);

        engine.addSystem(heroSystem);
        engine.addSystem(projectileMovementSystem);
        engine.addSystem(waveSystem);
        engine.addSystem(monsterMovementSystem);
        engine.addSystem(collisionSystem);

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

        if (!GameOverState.getInstance().isGameOverBoolean()) {
            engine.update(dt);
            System.out.println("engine running");
        } else {
            gameOver = true;
            try {
                // Sleep for a short duration to reduce CPU usage when the game is over
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        handleInput();
    }

    public void calculateMoney() {
        float iconSize = Gdx.graphics.getWidth() / 30f;
        BitmapFont counterFont = new BitmapFont();
        counterFont.getData().setScale(4);
        // First counter
        counterText1 = new TextButton(String.valueOf(moneySystem.getMoney()), new TextButton.TextButtonStyle(null, null, null, counterFont));
        counterText1.pad(2);
        counterText1.setWidth(iconSize);
        counterText1.setHeight(iconSize / 2);
        board.getRightTable().add(counterText1);

        Texture counterIconTexture1 = new Texture("images/coin2.png");
        Image counterIcon1 = new Image(counterIconTexture1);
        board.getRightTable().add(counterIcon1).size(iconSize, iconSize).pad(5);
        board.getRightTable().row();

        // Second counter
        TextButton counterText2 = new TextButton("2000", new TextButton.TextButtonStyle(null, null, null, counterFont));
        counterText2.pad(2);
        counterText2.setWidth(iconSize);
        counterText2.setHeight(iconSize / 2);
        board.getRightTable().add(counterText2);

        Texture counterIconTexture2 = new Texture("images/coin2.png");
        Image counterIcon2 = new Image(counterIconTexture2);
        board.getRightTable().add(counterIcon2).size(iconSize, iconSize).pad(5);
        board.getRightTable().row();
    }

    public void renderGameOver(SpriteBatch batch) {
        GameOverState.getInstance().handleInput();
        GameOverState.getInstance().render(batch);
    }

    public void renderHeroes(SpriteBatch batch) {
        for (Entity e : engine.getEntitiesFor(Family.all(HeroComponent.class, AttackComponent.class).get())) {
            Texture sprite = e.getComponent(SpriteComponent.class).getSprite();
            Vector2 position = e.getComponent(PositionComponent.class).getPosition();

            batch.draw(sprite, position.x, position.y, (float) board.getTextureWidth(), (float) board.getTextureHeight());
        }
    }



    public void renderProjectiles(SpriteBatch batch) {
        for (Entity e : engine.getEntitiesFor(Family.all(ProjectileComponent.class, PositionComponent.class).get())) {
            Texture sprite = e.getComponent(SpriteComponent.class).getSprite();
            Vector2 position = e.getComponent(PositionComponent.class).getPosition();

            batch.draw(sprite, position.x, position.y, (float) board.getTextureWidth(), (float) board.getTextureHeight());
        }
    }

    public void renderMonsters(SpriteBatch batch) {
        for (Entity e : engine.getEntitiesFor(Family.all(MonsterComponent.class, PositionComponent.class).get())) {
            Texture sprite = e.getComponent(SpriteComponent.class).getSprite();
            Vector2 position = e.getComponent(PositionComponent.class).getPosition();

            batch.draw(sprite, position.x, position.y, (float) board.getTextureWidth(), (float) board.getTextureHeight());
        }
    }

    public void renderWaveInfo(SpriteBatch batch) {
        for (Entity e : engine.getEntitiesFor(Family.all(WaveComponent.class).get())) {
            monsterCount = e.getComponent(WaveComponent.class).getNumberOfMonsters();
            waveCount = e.getComponent(WaveComponent.class).getWaveNumber();
            totalKills = e.getComponent(WaveComponent.class).getMonstersKilled();
            monsterToKill = e.getComponent(WaveComponent.class).getMonstersToKill();
            score = e.getComponent(WaveComponent.class).getScore();
        }

        font.setColor(Color.WHITE);
        font.getData().setScale(3.0f);
        font.draw(batch, "Wave: " + waveCount, screenWidth/7.5f, screenHeight - screenHeight/25f);
        font.draw(batch, "Total kills: " + totalKills + "/" + monsterToKill, screenWidth/4f, screenHeight - screenHeight/25f);
        font.draw(batch, "Score: " + score, screenWidth/2.4f, screenHeight - screenHeight/25f);
        font.setColor(Color.RED);
        font.draw(batch, "Monster left: " + monsterCount, screenWidth - screenWidth/2.3f, screenHeight - screenHeight/25f);
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //calculateMoney();

        batch.begin();
        board.render(batch);
        renderHeroes(batch);
        renderProjectiles(batch);
        renderMonsters(batch);
        renderWaveInfo(batch);
        settingsButton.render(batch);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (settingsButton.getBounds().contains(touchX, touchY)) {
                gsm.push(new SettingsState(Enums.SettingsBackground.CITY, Enums.GameType.SINGLEPLAYER));
            }
        }
    }


    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
        shapeRenderer.dispose();
    }
}
