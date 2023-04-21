package com.mygdx.game.ds.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class CircleButton {

    private Vector2 position;
    private Circle bounds;
    private ShapeRenderer shape;
    private int radius;
    private Texture img;

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
