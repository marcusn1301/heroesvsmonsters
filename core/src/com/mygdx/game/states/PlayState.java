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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Board;
import com.mygdx.game.MoneySystem;
import com.mygdx.game.SoundManager;
import com.mygdx.game.entities.DisplayHero;
import com.mygdx.game.entities.HeroFactory;
import com.mygdx.game.systems.HeroSystem;
import com.mygdx.game.systems.ProjectileMovementSystem;
import com.mygdx.game.types.HeroType;

import java.util.ArrayList;
import java.util.List;

public class PlayState extends State{
    private SpriteBatch batch;
    private Texture[] buttonTextures;
    private BitmapFont font;
    private Stage stage;
    private ShapeRenderer shapeRenderer;
    private boolean isGridTableVisible = true;
    private DisplayHero chosenCharacter;
    private List<DisplayHero> displayHeroes;
    SoundManager soundManager = SoundManager.getInstance();
    private TextButton counterText1;

    private MoneySystem moneySystem;
    private boolean isPlacementAllowed = false;
    private static Engine engine;
    private Board board;
    private Table boardTable;
    private Table leftTable;
    private Table rightTable;


    public PlayState() {
        //super(gsm);
        initialize();
    }

    private void initialize() {
        batch = new SpriteBatch();
        setDisplayHeroes();
        initButtonTextures();
        moneySystem = new MoneySystem(4000);
        initFontStageAndRenderer();
        createLeftTable();
        createRightTable();
        createBoard();
        soundManager.playSequence();
        //Game engine & systems
        initializeGameEngine();
    }

    private void initializeGameEngine() {
        //Central game engine
        engine = new Engine();

        //Systems for game logic
        HeroSystem heroSystem = new HeroSystem(engine);
        ProjectileMovementSystem projectileMovementSystem = new ProjectileMovementSystem(engine);

        engine.addSystem(heroSystem);
        engine.addSystem(projectileMovementSystem);

        Entity spiderman = HeroFactory.createHero(HeroType.SPIDERMAN, new Vector2(50, 50));
        Entity captain = HeroFactory.createHero(HeroType.CAPTAIN_AMERICA, new Vector2(50, 50));
        engine.addEntity(spiderman);
        engine.addEntity(captain);
    }

    private void initButtonTextures() {
        buttonTextures = new Texture[5];

        for (int i = 0; i < displayHeroes.size(); i++) {
            buttonTextures[i] = displayHeroes.get(i).getSpriteComponent().getSprite();
        }
    }

    private void initFontStageAndRenderer() {
        font = new BitmapFont();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(stage);
    }

    private void createLeftTable() {
        leftTable = new Table();
        leftTable.setFillParent(true);
        leftTable.top().left().padLeft(Gdx.graphics.getWidth() / 40).padTop(Gdx.graphics.getHeight() / 40);
        for (int i = 0; i < 5; i++) {
            //This line changes the size of the characters, based on device
            float circleRadius = Gdx.graphics.getHeight() / 15;

            Stack stack = new Stack();
            Image whiteCircle = new Image(createWhiteCircle(circleRadius));
            Image button = new Image(buttonTextures[i]);
            stack.add(whiteCircle);
            stack.add(button);

            final int finalI = i;
            button.addListener(new ClickListener() {

                // When player clicks a character icon
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    chosenCharacter = displayHeroes.get(finalI);
                    isGridTableVisible = !isGridTableVisible;
                    boardTable.setVisible(true);
                }
            });

            leftTable.add(stack).size(circleRadius * 2, circleRadius * 2).pad(5).fill().center();
            leftTable.row();
            BitmapFont biggerFont = new BitmapFont();
            biggerFont.getData().setScale(2);
            leftTable.add(new TextButton(Integer.toString(displayHeroes.get(i).getPriceComponent().getPrice()), new TextButton.TextButtonStyle(null, null, null, biggerFont))).pad(5);
            leftTable.row();
        }
        stage.addActor(leftTable);
    }

    private void createRightTable() {
        rightTable = new Table();
        rightTable.setFillParent(true);
        rightTable.top().right().padRight(Gdx.graphics.getWidth() / 60);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        float menuButtonSize = Gdx.graphics.getWidth() / 8;
        TextButton menuButton = new TextButton("≡", skin, "default");
        menuButton.setSize(menuButtonSize, menuButtonSize);
        menuButton.getStyle().up = menuButton.getStyle().down;
        menuButton.setColor(1f,1f,1f,1f);
        menuButton.setPosition(Gdx.graphics.getWidth() * 7 / 8 + 10, 10);

        float iconSize = Gdx.graphics.getWidth() / 30;

        BitmapFont counterFont = new BitmapFont();
        counterFont.getData().setScale(4);

        // First counter
        counterText1 = new TextButton(String.valueOf(moneySystem.getMoney()), new TextButton.TextButtonStyle(null, null, null, counterFont));
        counterText1.pad(2);
        counterText1.setWidth(iconSize);
        counterText1.setHeight(iconSize / 2);
        rightTable.add(counterText1);

        Texture counterIconTexture1 = new Texture("coin.png");
        Image counterIcon1 = new Image(counterIconTexture1);
        rightTable.add(counterIcon1).size(iconSize, iconSize).pad(5);
        rightTable.row();

        // Second counter
        TextButton counterText2 = new TextButton("2000", new TextButton.TextButtonStyle(null, null, null, counterFont));
        counterText2.pad(2);
        counterText2.setWidth(iconSize);
        counterText2.setHeight(iconSize / 2);
        rightTable.add(counterText2);

        Texture counterIconTexture2 = new Texture("coin.png");
        Image counterIcon2 = new Image(counterIconTexture2);
        rightTable.add(counterIcon2).size(iconSize, iconSize).pad(5);
        rightTable.row();

        stage.addActor(rightTable);
    }

    private void createBoard() {
        board = new Board(6,9);
        boardTable = new Table();
        boardTable.setFillParent(true);
        boardTable.add(board);
        boardTable.center();
        float boardWidth = Gdx.graphics.getWidth() - leftTable.getWidth() - rightTable.getWidth();
        boardTable.setWidth(boardWidth);


        board.render(batch);
    }

    @Override
    public void update(float dt) {
        stage.draw();
        engine.update(dt);
    }

    @Override
    public void render(SpriteBatch batch) {

        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawPaneBackgrounds();
        //drawLaneDividers();
        batch.begin();
        board.render(batch);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void handleInput() {}

    private void drawPaneBackgrounds() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        Color leftRightPaneColor = new Color(0.3f, 0.3f, 0.3f, 1);
        // Draw left pane background
        shapeRenderer.setColor(leftRightPaneColor);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight());

        // Draw right pane background
        shapeRenderer.setColor(leftRightPaneColor);
        shapeRenderer.rect(Gdx.graphics.getWidth() * 7 / 8, 0, Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight());

        shapeRenderer.end();
    }

    private void drawLaneDividers() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        int laneWidth = Gdx.graphics.getWidth() * 3 / 4;
        int laneHeight = Gdx.graphics.getHeight() / 6;

        for (int i = 1; i < 6; i++) {
            Color dividerColor = Color.WHITE;
            if (i == 3) {
                dividerColor = Color.YELLOW;
            }

            shapeRenderer.setColor(dividerColor);
            float lineWidth = 5f;
            float lineX = Gdx.graphics.getWidth() / 8;
            float lineY = laneHeight * i - (lineWidth / 2);

            // Draw a dotted line
            int numOfDots = laneWidth / 20;
            for (int j = 0; j < numOfDots; j++) {
                float dotWidth = 10f;
                float dotX = lineX + (20 * j);
                shapeRenderer.rect(dotX, lineY, dotWidth, lineWidth);
            }
        }
        shapeRenderer.end();
    }

    private Texture createWhiteCircle(float circleRadius) {
        int diameter = (int) (circleRadius * 2);
        Pixmap pixmap = new Pixmap(diameter, diameter, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fillCircle(diameter / 2, diameter / 2, (int) circleRadius);
        Texture circleTexture = new Texture(pixmap);
        pixmap.dispose();

        return circleTexture;
    }

    public void dispose() {
        batch.dispose();
        for (Texture buttonTexture : buttonTextures) {
            buttonTexture.dispose();
        }
        font.dispose();
        stage.dispose();
        shapeRenderer.dispose();
        //TODO dispose displayHeroes
    }
    public void setDisplayHeroes() {
        displayHeroes = new ArrayList<>();
        DisplayHero hulk = HeroFactory.createDisplayHero(HeroType.HULK);
        DisplayHero spiderman = HeroFactory.createDisplayHero(HeroType.SPIDERMAN);
        DisplayHero cpt_america = HeroFactory.createDisplayHero(HeroType.CAPTAIN_AMERICA);
        DisplayHero ironman = HeroFactory.createDisplayHero(HeroType.IRONMAN);
        DisplayHero thor = HeroFactory.createDisplayHero(HeroType.THOR);
        this.displayHeroes.add(hulk);
        this.displayHeroes.add(spiderman);
        this.displayHeroes.add(cpt_america);
        this.displayHeroes.add(ironman);
        this.displayHeroes.add(thor);
    }
}
