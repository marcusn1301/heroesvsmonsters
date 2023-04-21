package com.mygdx.game.ds.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class RectangleButton {

    private final Vector2 position;
    private final Rectangle bounds;
    private final ShapeRenderer shape;
    private final Texture img;
    private final int width;
    private final int height;
    private final float scale;

    public RectangleButton(float scale, int xStart, int yStart, String internalPath) {
        this.scale = scale;
        this.img = new Texture(internalPath);
        this.width = (int) (img.getWidth() * scale);
        this.height = (int ) (img.getHeight() * scale);
        this.position = new Vector2(xStart, yStart);
        this.bounds = new Rectangle(xStart, yStart, width, height);
        this.shape = new ShapeRenderer();
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public ShapeRenderer getShape() {
        return shape;
    }

    public int getWidth() {return this.width;}

    public int getHeight() {return this.height;}

    public Texture getImg() {
        return img;
    }

    public float getScale() {
        return scale;
    }
}
