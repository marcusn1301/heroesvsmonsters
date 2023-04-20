package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.entities.DisplayHero;
import com.mygdx.game.entities.HeroFactory;
import com.mygdx.game.types.HeroType;
import com.mygdx.game.utils.DisplayHeroButton;

import java.util.ArrayList;
import java.util.List;

public class Board extends Actor {
    private int rows;
    private int cols;
    private int[][] cells;
    private Texture[][] textures;
    private ShapeRenderer shapeRenderer;
    private int textureWidth;
    private int textureHeight;
    private int cellWidth;
    private int cellHeight;
    private SpriteBatch batch;

    private int xOffset = 315; // Add xOffset for moving textures right
    private int yOffset = 0;  // Add yOffset for moving textures up or down
    private int dashOffset = 315; // Add dashOffset for moving dashed lines right

    private Texture[] buttonTextures;
    private Texture[] displayTextures;
    private int displayTexturesCount = 5;
    private Vector2[] displayTexturePositions;
    private int draggingTextureIndex = -1;
    private Vector2 draggingTextureOffset = new Vector2();
    private Stage stage;

    private Table leftTable;
    private Table rightTable;
    private Table boardTable;
    private DisplayHero chosenCharacter;
    private List<DisplayHero> displayHeroes;
    private TextButton counterText1;
    private boolean isGridTableVisible = true;
    private boolean isPlacementAllowed = false;
    private List<DisplayHeroButton> displayHeroButtons;


    public Board(int rows, int cols) {

        this.rows = rows;
        this.cols = cols;

        cellWidth = Gdx.graphics.getWidth() / (rows + 6);
        cellHeight = Gdx.graphics.getHeight() / (rows );
        System.out.print("ScreenHeight: " + Gdx.graphics.getHeight());

        textureWidth = cellHeight - 70;
        textureHeight = cellHeight;
        cells = new int[rows][cols];
        textures = new Texture[rows][cols];
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        setDisplayHeroes();
        initButtonTextures();
        loadDisplayTextures();
        setupInputProcessor();

    }

    public Board() {

    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Texture[][] getTextures() {
        return textures;
    }

    public void setTextures(Texture[][] textures) {
        this.textures = textures;
    }

    public void setTexture(int row, int col, Texture texture) {
        setCell(row, col, 1);
        textures[row][col] = texture;
    }

    public int getCell(int row, int col) {
        return cells[row][col];
    }

    public void setCell(int row, int col, int value) {
        cells[row][col] = value;
    }

    public void render(SpriteBatch batch) {
        //createLeftTable();
        drawLaneDividers();
        drawPaneBackgrounds();
        //createRightTable();
        drawDisplayPanel(batch);

        this.batch.begin();

        createDisplayHeroButtons();
        drawHeroes();
        this.batch.end();
    }

    public void drawHeroes() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (getCell(row, col) == 1) {
                    Texture texture = textures[row][col];
                    batch.draw(texture, col * cellWidth + xOffset, row * cellHeight + yOffset, textureWidth, textureHeight);
                }
            }
        }
    }

    public void createDisplayHeroButtons(List<DisplayHero> displayHeroes) {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        for (DisplayHero displayHero : displayHeroes) {
            DisplayHeroButton button = new DisplayHeroButton(displayHero);
            displayHeroButtons.add(button);
        }

        for (DisplayHeroButton button : displayHeroButtons) {

        }

        for (int i = 0; i < displayTexturesCount; i++) {
            stage = new Stage();
            Gdx.input.setInputProcessor(stage);

            batch.draw(displayTextures[i], displayTexturePositions[i].x, displayTexturePositions[i].y, textureWidth, textureHeight);
        }

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

    private void createMiddleBoard() {
        boardTable = new Table();
        boardTable.setFillParent(true);
        boardTable.add(new Board(rows, cols));
        boardTable.center();
        float boardWidth = Gdx.graphics.getWidth() - leftTable.getWidth() - rightTable.getWidth();
        boardTable.setWidth(boardWidth);
    }

    private void createLeftTable() {
        leftTable = new Table();
        leftTable.setFillParent(true);
        leftTable.top().left().padLeft(Gdx.graphics.getWidth() / 40).padTop(Gdx.graphics.getHeight() / 40);

        for (int i = 0; i < 5; i++) {
            System.out.println(i);

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

        stage.addActor(rightTable);
    }

    public Table getRightTable() {
        return rightTable;
    }

    private void initButtonTextures() {
        buttonTextures = new Texture[5];

        for (int i = 0; i < displayHeroes.size(); i++) {
            buttonTextures[i] = displayHeroes.get(i).getSpriteComponent().getSprite();
        }
    }

    private void setupInputProcessor() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                int row, col;
                for (row = 0; row < rows; row++) {
                    for (col = 0; col < cols; col++) {
                        Rectangle rect = new Rectangle(col * cellWidth + xOffset, row * cellHeight + yOffset, textureWidth, textureHeight);
                        if (rect.contains(screenX, Gdx.graphics.getHeight() - screenY)) {
                            onCellClicked(row, col);
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }
    protected void onCellClicked(int row, int col) {
        // Add your logic here for when a cell is clicked
        Texture texture = new Texture("characterIcon5.png");
        setTexture(row, col, texture);
        System.out.println("Cell clicked: row " + row + ", col " + col);
    }


    public void drawLaneDividers() {
        int dashLength = 10;
        int spaceLength = 5;
        boolean drawDash = true;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < cols; col++) {
                int xStart = col * cellWidth + dashOffset;
                int yStart = row * cellHeight + cellHeight;
                int xEnd = xStart + cellWidth;
                int yEnd = yStart;

                for (int x = xStart; x < xEnd; x += dashLength + spaceLength) {
                    if (drawDash) {
                        shapeRenderer.rectLine(x, yStart, Math.min(x + dashLength, xEnd), yEnd, 4);
                    }
                    drawDash = !drawDash;
                }
            }
        }
        shapeRenderer.end();
    }

    private void loadDisplayTextures() {
        displayTextures = new Texture[displayTexturesCount];
        displayTexturePositions = new Vector2[displayTexturesCount];

        for (int i = 0; i < displayTexturesCount; i++) {
            displayTextures[i] = new Texture("characterIcon" + (i + 1) + ".png");
            displayTexturePositions[i] = new Vector2(20, 20 + (i * (textureHeight + 20)));
        }
    }

    public void drawDisplayPanel(SpriteBatch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.LIGHT_GRAY);

        for (Vector2 position : displayTexturePositions) {
            shapeRenderer.circle(position.x + textureWidth / 2, position.y + textureHeight / 2, textureWidth / 2 + 5);
        }

        shapeRenderer.end();
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

    private void dispose() {
        for (Texture buttonTexture : buttonTextures) {
            buttonTexture.dispose();
        }
    }
}
