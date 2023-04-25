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
import com.mygdx.game.components.GameOverComponent;
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
    private static Engine engine;
    private Board board;
    private final GameStateManager gsm;
    private final CircleButton settingsButton;
    private final int screenWidth = Gdx.graphics.getWidth();
    private final int screenHeight = Gdx.graphics.getHeight();
    private int monsterCount = 0;
    private int waveCount = 0;
    private int totalKills = 0;
    private int monsterToKill = 0;
    private int score = 0;
<<<<<<< HEAD
    private final boolean singlePlayer;
    private boolean gameOver = false;
=======
    private boolean singlePlayer;
    private boolean gameOver;
>>>>>>> f377332093ee454bba05bb1903e8a3646dc6d61e

    public PlayState(Enums.GameType type) {
        //super(gsm);
        this.singlePlayer = type.equals(Enums.GameType.SINGLEPLAYER);
        settingsButton = new CircleButton(40, (Gdx.graphics.getWidth() - 140),  60, "images/settings-button.png");
        gsm = GameStateManager.getGsm();
        gameOver = false;
        initialize();
    }

    private void initialize() {
        initializeGameEngine();
        batch = new SpriteBatch();
        initFontStageAndRenderer();
        createBoard();
        soundManager.playSequence();
        //Game engine & systems
    }

    private void initializeGameEngine() {
        //Central game engine
        engine = new Engine();
        Entity gameOver = new Entity();
        gameOver.add(new GameOverComponent());

        //Systems for game logic
        HeroSystem heroSystem = new HeroSystem(engine);
        ProjectileMovementSystem projectileMovementSystem = new ProjectileMovementSystem(engine);
        WaveSystem waveSystem = new WaveSystem(engine, isSinglePlayer());
        MonsterMovementSystem monsterMovementSystem = new MonsterMovementSystem(engine);
        CollisionSystem collisionSystem = new CollisionSystem(engine);

        engine.addSystem(heroSystem);
        engine.addSystem(projectileMovementSystem);
        engine.addSystem(waveSystem);
        engine.addSystem(monsterMovementSystem);
        engine.addSystem(collisionSystem);
        engine.addEntity(gameOver);
    }

    private void initFontStageAndRenderer() {
        font = new BitmapFont();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(stage);
    }

    private void createBoard() {
        board = new Board(6,9, engine, isSinglePlayer());
        board.render(batch);
    }

    public boolean isSinglePlayer() {
        return singlePlayer;
    }

    @Override
    public void update(float dt) {
        stage.draw();
        handleInput();
        for (Entity entit1 : engine.getEntitiesFor(Family.all(GameOverComponent.class).get())) {
            if (entit1.getComponent(GameOverComponent.class).isGameOver()) {
                this.board.setGameOver(true);
                gsm.set(new GameOverState(this.score));
            }
        }
        engine.update(dt);
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
        if (Gdx.input.justTouched()) {
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
        board.dispose();
        shapeRenderer.dispose();
    }
}
