package com.mygdx.game.ds.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class CircleButton {

    private final Vector2 position;
    private final Circle bounds;
    private final ShapeRenderer shape;
    private final int radius;
    private final Texture img;

    public CircleButton(float scale, String internalpath) {
        this.img = new Texture(internalpath);
        this.radius = (int) ((this.img.getWidth() / 2) / scale);
        this.position = new Vector2(Gdx.graphics.getWidth() / 2f - this.img.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - this.img.getHeight() / 2f);
        this.bounds = new Circle(this.position.x, this.position.y, this.radius);
        this.shape = new ShapeRenderer();
    }

    public CircleButton(float scale, boolean middle, int ystart, String internalpath) {
        this.img = new Texture(internalpath);
        this.radius = (int) ((this.img.getWidth() / 2) / scale);
        this.position = new Vector2(Gdx.graphics.getWidth() / 2f - this.img.getWidth() / 2f, ystart);
        this.bounds = new Circle(this.position.x, this.position.y, this.radius);
        this.shape = new ShapeRenderer();
    }

    public CircleButton(float scale, int xStart, boolean middle, String internalpath) {
        this.img = new Texture(internalpath);
        this.radius = (int) ((this.img.getWidth() / 2) / scale);
        this.position = new Vector2(xStart, Gdx.graphics.getHeight() / 2f - this.img.getHeight() / 2f);
        this.bounds = new Circle(this.position.x, this.position.y, this.radius);
        this.shape = new ShapeRenderer();
    }

    public CircleButton(int radius, int xStart, int yStart, String internalPath) {
        this.radius = radius;
        this.position = new Vector2(xStart, yStart);
        this.bounds = new Circle(this.position.x, this.position.y, this.radius);
        this.shape = new ShapeRenderer();
        this.img = new Texture(internalPath);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Circle getBounds() {
        return bounds;
    }

    public ShapeRenderer getShape() {
        return shape;
    }

    public int getRadius() {
        return radius;
    }

    public Texture getImg() {
        return img;
    }
}
