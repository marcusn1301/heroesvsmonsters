package com.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class PlayState extends State{
    private SpriteBatch batch;
    private Texture[] buttonTextures;
    private BitmapFont font;
    private Stage stage;
    private ShapeRenderer shapeRenderer;
    private boolean isGridTableVisible = true;

    private Table gridTable;

    private String chosenCharacter = "";


    public PlayState() {
        //super(gsm);
        init();
    }

    private void init() {
        batch = new SpriteBatch();
        buttonTextures = new Texture[5];
        for (int i = 0; i < 5; i++) {
            buttonTextures[i] = new Texture("characterIcon" + (i + 1) + ".png");
        }
        font = new BitmapFont();
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(stage);

        Table leftTable = new Table();
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


                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    chosenCharacter = "characterIcon" + (finalI + 1) + ".png";
                    isGridTableVisible = !isGridTableVisible;
                    gridTable.setVisible(true);
                }
            });

            leftTable.add(stack).size(circleRadius * 2, circleRadius * 2).pad(5).fill().center();
            leftTable.row();
            BitmapFont biggerFont = new BitmapFont();
            biggerFont.getData().setScale(2);
            leftTable.add(new TextButton(Integer.toString(100 * (i + 1)), new TextButton.TextButtonStyle(null, null, null, biggerFont))).pad(5);
            leftTable.row();
        }
        stage.addActor(leftTable);

        Table rightTable = new Table();
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
        TextButton counterText1 = new TextButton("1000", new TextButton.TextButtonStyle(null, null, null, counterFont));
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
        stage.addActor(menuButton);

        // Create a new table for the grid
        gridTable = new Table();
        gridTable.setFillParent(true);
        gridTable.center();

        // Define the number of rows and columns in the grid
        int numRows = 6;
        int numCols = 9;

        // Define the size of each cell in the grid
        final float cellSize = Gdx.graphics.getHeight() / (numRows + 3);

        // Loop through each row and column, adding an Image to represent each cell
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                // Create a new Image for the cell and add it to the table
                final Image cellImage = new Image(new Texture("invisible.png"));
                cellImage.setSize(cellSize, cellSize);
                gridTable.add(cellImage).size(cellSize).padBottom(90).padRight(20);

                cellImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);

                        // Create a new Image to fill the clicked cell
                        Image fillImage = new Image(new Texture(chosenCharacter));
                        fillImage.setSize(cellSize, cellSize);

                        // Replace the clicked cell with the new Image
                        gridTable.getCell(cellImage).setActor(fillImage);
                        //hidePanelCells();
                        /*isGridTableVisible = false;
                        gridTable.setVisible(isGridTableVisible);*/

                    }
                });

            }
            // Move to the next row in the grid
            gridTable.row();
        }

        gridTable.setVisible(false);
        // Add the grid table to the stage
        stage.addActor(gridTable);
    }

    public void hidePanelCells() {
        System.out.println("HIDDEN");
        // Loop through each cell in the grid
        for (Cell cell : gridTable.getCells()) {
            // Get the actor inside the cell
            Actor actor = cell.getActor();
            System.out.println(actor.getName());
            if (actor != null && actor.getName() != null && actor.getName().equals("panel.png")) {
                // Hide the actor
                actor.setVisible(false);
            }
        }
    }



    @Override
    public void update(float dt) {
        stage.draw();
    }

    @Override
    public void render(SpriteBatch sb) {

        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawPaneBackgrounds();
        drawLaneDividers();
        //drawButtonBackgroundCircles();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void handleInput() {

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
    }
}
