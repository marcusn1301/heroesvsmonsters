package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

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

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        cells = new int[rows][cols];
        textures = new Texture[rows][cols];
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        //Gdx.graphics.setWindowedMode(851, 393);

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

        /*int borderThickness = 20;

        int boardWidth = cols * textureWidth + borderThickness * 2;
        int boardHeight = rows * textureHeight + borderThickness * 2;


        // Adjust the position of the board to include the border
        int boardX = (int) ((Gdx.graphics.getWidth() - boardWidth) / 2.0f);
        int boardY = (int) ((Gdx.graphics.getHeight() - boardHeight) / 2.0f) + borderThickness;

        // Draw the border
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(boardX - borderThickness, boardY - borderThickness, boardWidth + borderThickness * 2, boardHeight + borderThickness * 2);

        // Draw a grey rectangle inside the border
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(boardX, boardY, boardWidth - borderThickness * 2, boardHeight - borderThickness * 2);

        shapeRenderer.end();
        */

        shapeRenderer.setAutoShapeType(true);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);

        // Draw dashed lines on top of cells
        int lines = rows - 6;
        for (int row = 0; row <= lines; row++) {
            for (int col = 0; col < cols - 1; col++) {
                float x1 = col * cellWidth;
                float y1 = row * cellHeight + textureHeight; // adjust y-coordinate
                float x2 = x1 + cellWidth;
                float y2 = y1;

                float dashLen = 8f; // length of each dash segment
                float gapLen = 8f; // length of each gap between dashes
                float segmentLen = dashLen + gapLen;

                float dist2 = Vector2.dst(x1, y1, x2, y2);
                float dist = cols * cellWidth;
                float segments = dist / segmentLen;
                float deltaX = (x2 - x1) / segments;
                float deltaY = (y2 - y1) / segments;

                float dashX = x1;
                float dashY = y1;

                for (int i = 0; i < segments; i++) {
                    shapeRenderer.rectLine(dashX, dashY, dashX + deltaX * dashLen, dashY + deltaY * dashLen, 3f);
                    dashX += deltaX * segmentLen;
                    dashY += deltaY * segmentLen;
                }
            }
        }

        shapeRenderer.end();


        this.batch.begin();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (getCell(row, col) == 1) {
                    Texture texture = textures[row][col];
                    batch.draw(texture, col * cellWidth, row * cellHeight, textureWidth, textureHeight);
                }
            }
        }
        this.batch.end();
    }
}
