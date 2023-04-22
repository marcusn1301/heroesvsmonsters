package com.mygdx.game.ds;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MoneySystem;
import com.mygdx.game.components.AttackComponent;
import com.mygdx.game.components.HeroComponent;
import com.mygdx.game.components.MonsterComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.PriceComponent;
import com.mygdx.game.components.SpriteComponent;
import com.mygdx.game.entities.DisplayHero;
import com.mygdx.game.entities.HeroFactory;
import com.mygdx.game.entities.MonsterFactory;
import com.mygdx.game.types.HeroType;
import com.mygdx.game.ds.buttons.DisplayHeroButton;
import com.mygdx.game.types.MonsterType;

public class Board extends Actor {
    private final int screenHeight = Gdx.graphics.getHeight();
    private final int screenWidth = Gdx.graphics.getWidth();
    private final int rows;
    private final int cols;
    private final int[][] cells;
    private Texture[][] textures;
    private final ShapeRenderer shapeRenderer;
    private final int textureWidth;
    private final int textureHeight;
    private int cellWidth;
    private int cellHeight;
    private final SpriteBatch batch;

    private final int yOffset = 0;  // Add yOffset for moving textures up or down
    private int xOffset = Gdx.graphics.getWidth()/8; // Add xOffset for moving textures right
    private int dashOffset = Gdx.graphics.getWidth()/8; // Add dashOffset for moving dashed lines right

    private Texture[] buttonTextures;
    private Texture[] displayTextures;
    private int displayTexturesCount = 5;
    private Vector2[] displayTexturePositions;
    private final Stage stage;

    private Array<DisplayHero> displayHeroes;
    private Array<DisplayHeroButton> displayHeroButtons;
    private Array<Entity> displayMonsters;

    private boolean placeHero = false;
    private HeroType chosenHeroType;
    private MonsterType chosenMonsterType;
    private final InputMultiplexer multiplexer;
    private final int gridWidth, gridHeight, startX, startY;
    private boolean gridDrawn;
    private boolean isInputProcessorAdded;
    private int rightPaneWidth;
    private final Engine engine;
    private final MoneySystem moneySystem = new MoneySystem(8000);



    public Board(int rows, int cols, Engine engine) {
        this.engine = engine;
        this.rows = rows;
        this.cols = cols;
        gridWidth = cols * cellWidth;
        gridHeight = rows * cellHeight;
        startX = (screenWidth - gridWidth) / 2;
        startY = (screenHeight - gridHeight) / 2;
        gridDrawn = false;
        isInputProcessorAdded = false;

        cellWidth = Gdx.graphics.getWidth() / (rows + 6);
        cellHeight = Gdx.graphics.getHeight() / (rows );

        textureWidth = cellHeight - 70;
        textureHeight = cellHeight;
        cells = new int[rows][cols];
        textures = new Texture[rows][cols];
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        loadDisplayTextures();
        setDisplayHeroes();
        setDisplayMonsters();
        initButtonTextures();
        //setupInputProcessor();
        displayHeroButtons = new Array<>();
        createDisplayHeroButtons(displayHeroes);
        multiplexer = new InputMultiplexer();
        stage = new Stage();
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void drawCounter() {
        float iconSize = Gdx.graphics.getHeight() / 15f;
        float iconX = screenWidth - iconSize * 2 - 160; // Move 200 pixels to the left
        float iconY = screenHeight - iconSize - 50;

        BitmapFont font = new BitmapFont();
        font.getData().setScale(3.5f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Texture counterIcon = new Texture("coin.png");

        this.batch.begin();
        font.draw(batch, String.valueOf(moneySystem.getMoney()), iconX + iconSize * 1.5f, iconY + iconSize * 0.75f);

        batch.draw(counterIcon, iconX, iconY, iconSize, iconSize);
        this.batch.end();
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
        drawGrid();
        drawLaneDividers();
        drawPaneBackgrounds();
        loadDisplayTextures();
        if (!isInputProcessorAdded) {
            setupInputProcessor();
            isInputProcessorAdded = true;
        }

        //createRightTable();
        //drawDisplayPanel(batch);

        this.batch.begin();
        drawHeroes();
        this.batch.end();

        drawDisplayHeroButtons();
        drawDisplayMonsterButtons();
        this.stage.act();
        this.stage.draw();
        drawCounter();

    }

    private void setupInputProcessor() {
        multiplexer.addProcessor(new InputAdapter() {
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
        System.out.println("Cell clicked: row " + row + ", col " + col);
        int displayPanelWidth = Gdx.graphics.getWidth() / 8;
        int middleOfCellX = (col * cellWidth) + displayPanelWidth;
        int middleOfCellY = row * cellHeight;

        System.out.print(" x: " + middleOfCellX + " y: " + middleOfCellY);
        Vector2 entityPlacement = new Vector2(middleOfCellX, middleOfCellY);

        //Creates new hero entity and sets its position to the middle of the clicked cell
        getChosenHeroType();
        placeHero(entityPlacement);

        //Creates a new monster entity and places it in the midddle of the cell
        getChosenMonsterType();
        placeMonster(entityPlacement);

        System.out.println("Cell clicked: row " + row + ", col " + col);
        moneySystem.removeMoney(450);
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

    public void drawGrid() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // draw horizontal lines
        for (int i = 0; i <= rows; i++) {
            int y = startY + i * cellHeight;
            shapeRenderer.line(startX, y, startX + gridWidth, y);
        }

        // draw vertical lines
        for (int i = 0; i <= cols; i++) {
            int x = startX + i * cellWidth;
            shapeRenderer.line(x, startY, x, startY + gridHeight);
        }

        shapeRenderer.end();
        if (!gridDrawn) {
            gridDrawn = true;
            //setGridInputAdapter();
        }
    }


    private void setGridInputAdapter() {
        // add click listener to each cell
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                int col = (screenX - startX) / cellWidth;
                int row = (screenY - startY) / cellHeight;
                int x = startX + col * cellWidth;
                int y = startY + row * cellHeight;

                if (screenX >= x && screenX < x + cellWidth && screenY >= y && screenY < y + cellHeight) {
                    System.out.println("Cell clicked: (" + row + ", " + col + ")");
                }

                return super.touchDown(screenX, screenY, pointer, button);
            }
        });
    }

    public void createDisplayHeroButtons(Array<DisplayHero> displayHeroes) {
        displayHeroButtons = new Array<>();
        for (DisplayHero displayHero : displayHeroes) {
            DisplayHeroButton button = new DisplayHeroButton(displayHero);
            displayHeroButtons.add(button);
        }
    }

    public void drawDisplayMonsterButtons() {
        float circleRadius = Gdx.graphics.getHeight() / 15;
        int diameter = (int) ((circleRadius * 2) + 5);
        Texture circleTexture = createWhiteCircle(circleRadius);

        BitmapFont font = new BitmapFont(); // Create a BitmapFont instance
        font.getData().setScale(2); // Increase the font size
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE); // Set the font and color (white) for the label style

        for (final Entity button : displayMonsters) {
            Group buttonGroup = new Group();
            Texture sprite = button.getComponent(SpriteComponent.class).getSprite();
            Vector2 position = button.getComponent(PositionComponent.class).getPosition();

            // Button background
            Image circle = new Image(circleTexture);
            circle.setPosition((screenWidth - circle.getWidth() - (((rightPaneWidth - circle.getWidth()) / 2))), position.y);
            circle.setSize(diameter, diameter);
            buttonGroup.addActor(circle);

            // Button with monster-texture
            final Button buttonClickable = new Button(new TextureRegionDrawable(new TextureRegion(sprite)));
            buttonClickable.setPosition((screenWidth - circle.getWidth() - (((rightPaneWidth - circle.getWidth()) / 2))), position.y);
            buttonClickable.setSize(screenWidth / 12, screenHeight / 7);


            final MonsterType monsterType = button.getComponent(MonsterComponent.class).getMonsterType();
            buttonClickable.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Button position: (" + monsterType + ", at " + buttonClickable.getX() + ", " + buttonClickable.getY() + ")");
                    setChosenMonsterType(monsterType);
                }
            });
            buttonGroup.addActor(buttonClickable);

            int price = button.getComponent(PriceComponent.class).getPrice();
            // Create the label for the button number
            Label buttonNumber = new Label(Integer.toString(price), labelStyle);

            // Adjust the label's position to be centered horizontally and vertically below the button
            float labelX = screenWidth - (rightPaneWidth / 2);
            float labelY = position.y - (buttonNumber.getHeight() * 1.5f);
            buttonNumber.setPosition(labelX, labelY);

            buttonGroup.addActor(buttonNumber); // Add the label to the buttonGroup
            stage.addActor(buttonGroup); // Add the buttonGroup to the stage
        }
    }

    public void drawDisplayHeroButtons() {
        float circleRadius = Gdx.graphics.getHeight() / 15;
        int diameter = (int) ((circleRadius * 2) + 5);
        Texture circleTexture = createWhiteCircle(circleRadius);

        BitmapFont font = new BitmapFont(); // Create a BitmapFont instance
        font.getData().setScale(2); // Increase the font size
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE); // Set the font and color (white) for the label style


        for (final DisplayHeroButton button : displayHeroButtons) {
            Group buttonGroup = new Group();
            // Group for the DisplayHero-button

            // Button background
            Image circle = new Image(circleTexture);
            circle.setPosition(button.getPosition().x - button.getPosition().x / 2, button.getPosition().y);
            circle.setSize(diameter, diameter);
            buttonGroup.addActor(circle);

            // Button with hero-texture
            final Button buttonClickable = new Button(new TextureRegionDrawable(new TextureRegion(button.getTexture())));
            buttonClickable.setPosition(button.getPosition().x - (screenWidth / 130), button.getPosition().y);
            buttonClickable.setSize(screenWidth / 16, screenHeight / 7);

            // Event listener
            buttonClickable.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Button position: (" + button.getHeroType() + ", at " + buttonClickable.getX() + ", " + buttonClickable.getY() + ")");
                    setChosenHeroType(button.getHeroType());
                }
            });

            buttonGroup.addActor(buttonClickable);

            // Create the label for the button number
            Label buttonNumber = new Label(Integer.toString(button.getPrice()), labelStyle);

            // Adjust the label's position to be centered horizontally and vertically below the button
            float labelX = button.getPosition().x + (button.getWidth() / 2) - (buttonNumber.getWidth() / 2);
            float labelY = button.getPosition().y - (buttonNumber.getHeight() * 1.5f);
            buttonNumber.setPosition(labelX, labelY);

            buttonGroup.addActor(buttonNumber); // Add the label to the buttonGroup
            stage.addActor(buttonGroup); // Add the buttonGroup to the stage

        }
    }


    /* Tegner heroesene og den hvite sirkelen bak.
        for (DisplayHeroButton button : displayHeroButtons) {
            //draw circles
            batch.draw(circle, button.getPosition().x, button.getPosition().y, diameter, diameter);
            //draw display hero
            batch.draw(button.getTexture(), button.getPosition().x, button.getPosition().y, button.getWidth(), button.getHeight());
        }
     */

    public MonsterType getChosenMonsterType() {
        return chosenMonsterType;
    }

    public void setChosenMonsterType(MonsterType monsterType) {
        this.chosenMonsterType = monsterType;
    }

    private void setChosenHeroType(HeroType heroType) {
        this.chosenHeroType = heroType;
        this.placeHero = !this.placeHero;
    }

    public HeroType getChosenHeroType() {
        return chosenHeroType;
    }

    private void placeHero(Vector2 placementPosition) {
        if (chosenHeroType != null) {
            Entity hero = HeroFactory.createHero(getChosenHeroType(), placementPosition);
            engine.addEntity(hero);
            System.out.println("Created new hero entity and added to game engine");
            System.out.println("all heroes: :)");
            for (Entity e : engine.getEntitiesFor(Family.all(HeroComponent.class).get())) {
                System.out.println(e.getComponent(HeroComponent.class).getHeroType());
            }
        }
    }

    private void placeMonster(Vector2 placementPosition) {
        if (chosenMonsterType != null) {
            //Check if monster is already placed in the cell
            boolean cellHasMonster = false;
            float epsilon = 0.0001f;
            ComponentMapper<PositionComponent> positionMapper;
            positionMapper = ComponentMapper.getFor(PositionComponent.class);
            for (Entity e : engine.getEntitiesFor(Family.all(HeroComponent.class, AttackComponent.class).get())) {
                PositionComponent position = positionMapper.get(e);
                Vector2 monsterPos = position.getPosition();
                if (monsterPos.epsilonEquals(placementPosition, epsilon)) {
                    System.out.println("There is already a monster in this cell");
                    cellHasMonster = true;
                    break;
                }
            }
            //Place a new monster entity at the given position if the cell is empty
            if (!cellHasMonster) {
                Entity monster = MonsterFactory.createMonster(getChosenMonsterType(), placementPosition);
                engine.addEntity(monster);
                System.out.println("Created new monster entity and added to game engine");
            }
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

        // Draw right pane background with increased padding
        rightPaneWidth = (Gdx.graphics.getWidth() / 8);
        shapeRenderer.setColor(leftRightPaneColor);
        shapeRenderer.rect(Gdx.graphics.getWidth() - rightPaneWidth, 0, rightPaneWidth, Gdx.graphics.getHeight());

        shapeRenderer.end();
    }


    public Table getRightTable() {
        Table rightTable = new Table();
        return rightTable;
    }

    private void initButtonTextures() {
        buttonTextures = new Texture[5];

        for (int i = 0; i < displayHeroes.size; i++) {
            buttonTextures[i] = displayHeroes.get(i).getSpriteComponent().getSprite();
        }
    }

    public int getTextureWidth() {
        return textureWidth;
    }

    public int getTextureHeight() {
        return textureHeight;
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

    public void setDisplayHeroes() {
        displayHeroes = new Array<>();
        DisplayHero hero = HeroFactory.createDisplayHero(HeroType.HULK, new Vector2(displayTexturePositions[0].x + textureWidth/3, displayTexturePositions[0].y + textureHeight / 2));
        DisplayHero spiderman = HeroFactory.createDisplayHero(HeroType.SPIDERMAN, new Vector2(displayTexturePositions[1].x + textureWidth/3, displayTexturePositions[1].y + textureHeight / 2));
        DisplayHero cpt_america = HeroFactory.createDisplayHero(HeroType.CAPTAIN_AMERICA, new Vector2(displayTexturePositions[2].x + textureWidth/3, displayTexturePositions[2].y + textureHeight / 2));
        DisplayHero ironman = HeroFactory.createDisplayHero(HeroType.IRONMAN, new Vector2(displayTexturePositions[3].x + textureWidth/3, displayTexturePositions[3].y + textureHeight / 2));
        DisplayHero thor = HeroFactory.createDisplayHero(HeroType.THOR, new Vector2(displayTexturePositions[4].x + textureWidth/3, displayTexturePositions[4].y + textureHeight / 2));

        this.displayHeroes.add(hero);
        this.displayHeroes.add(spiderman);
        this.displayHeroes.add(cpt_america);
        this.displayHeroes.add(ironman);
        this.displayHeroes.add(thor);
    }

    public void setDisplayMonsters() {
        displayMonsters = new Array<>();
        Entity magneto = MonsterFactory.createDisplayMonster(MonsterType.MAGNETO, new Vector2(displayTexturePositions[0].x, displayTexturePositions[0].y + textureHeight / 2));
        Entity juggernaut = MonsterFactory.createDisplayMonster(MonsterType.JUGGERNAUT, new Vector2(displayTexturePositions[1].x + textureWidth/3, displayTexturePositions[1].y + textureHeight / 2));
        Entity venom = MonsterFactory.createDisplayMonster(MonsterType.VENOM, new Vector2(displayTexturePositions[2].x + textureWidth/3, displayTexturePositions[2].y + textureHeight / 2));
        Entity hoboblin = MonsterFactory.createDisplayMonster(MonsterType.HOBGOBLIN, new Vector2(displayTexturePositions[3].x + textureWidth/3, displayTexturePositions[3].y + textureHeight / 2));
        Entity goblin_glider = MonsterFactory.createDisplayMonster(MonsterType.GOBLIN_GLIDER, new Vector2(displayTexturePositions[4].x + textureWidth/3, displayTexturePositions[4].y + textureHeight / 2));

        this.displayMonsters.add(magneto);
        this.displayMonsters.add(juggernaut);
        this.displayMonsters.add(venom);
        this.displayMonsters.add(hoboblin);
        this.displayMonsters.add(goblin_glider);
    }

    private void dispose() {
        for (Texture buttonTexture : buttonTextures) {
            buttonTexture.dispose();
        }
        batch.dispose();

    }
}
