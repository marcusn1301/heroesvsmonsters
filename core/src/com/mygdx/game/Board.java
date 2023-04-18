package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Board {
    private int rows = 5;
    private int cols = 9;
    private int[][] cells;
    private Texture[][] textures;
    private ShapeRenderer shapeRenderer;
    private int textureWidth = 55;
    private int textureHeight = 70;
    private SpriteBatch batch;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        cells = new int[rows][cols];
        textures = new Texture[rows][cols];
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

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
        this.batch.begin();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (getCell(row, col) == 1) {
                    Texture texture = textures[row][col];
                    batch.draw(texture, col * 58, row * 60, textureWidth, textureHeight);
                }
            }
        }
        this.batch.end();
        // Draw the dotted lines
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        float lineThickness = 2f;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // Only draw lines for even rows
                if (row % 2 == 0) {
                    shapeRenderer.rect(col * textureWidth, row * textureHeight + textureHeight / 2 - lineThickness / 2, textureWidth, lineThickness);
                }
            }
        }

        shapeRenderer.end();
    }
}
