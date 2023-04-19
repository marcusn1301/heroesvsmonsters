package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Board {
    private int rows = 5;
    private int cols = 9;
    private int[][] cells;
    private Texture[][] textures;
    private ShapeRenderer shapeRenderer;
    private int textureWidth = 55;
    private int textureHeight = 72;
    private int cellWidth = 58;
    private int cellHeight = 78;
    private SpriteBatch batch;

    private int xOffset = 100; // Add xOffset for moving textures right
    private int yOffset = 0;  // Add yOffset for moving textures up or down
    private int dashOffset = 100; // Add dashOffset for moving dashed lines right

    private Texture[] displayTextures;
    private int displayTexturesCount = 5;
    private Vector2[] displayTexturePositions;
    private int draggingTextureIndex = -1;
    private Vector2 draggingTextureOffset = new Vector2();


    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        cells = new int[rows][cols];
        textures = new Texture[rows][cols];
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        Gdx.graphics.setWindowedMode(851, 393);
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
        drawLaneDividers();
        drawDisplayPanel(batch);

        this.batch.begin();

        for (int i = 0; i < displayTexturesCount; i++) {
            batch.draw(displayTextures[i], displayTexturePositions[i].x, displayTexturePositions[i].y, textureWidth, textureHeight);
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (getCell(row, col) == 1) {
                    Texture texture = textures[row][col];
                    batch.draw(texture, col * cellWidth + xOffset, row * cellHeight + yOffset, textureWidth, textureHeight);
                }
            }
        }
        this.batch.end();
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
}
