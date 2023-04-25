package com.mygdx.game.ds.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Null;

public class CircleButton {

    private final Vector2 position;
    private final Circle bounds;
    private final ShapeRenderer shape;
    private final int radius;
    private final Texture img;

    public CircleButton(int radius, @Null Integer xStart, @Null Integer yStart, String internalPath) {
        float startPosX;
        float startPosY;
        this.radius = radius;
        if (xStart == null) {
            startPosX = Gdx.graphics.getWidth() / 2f - radius;
        } else {
            startPosX = xStart;
        }

        if (yStart == null) {
            startPosY = Gdx.graphics.getHeight() / 2f - radius;
        } else {
            startPosY = yStart;
        }
        this.position = new Vector2(startPosX, startPosY);
        this.bounds = new Circle(this.position.x, this.position.y, this.radius);
        this.shape = new ShapeRenderer();
        this.img = new Texture(internalPath);
    }

    public Circle getBounds() {
        return bounds;
    }


    public void render(SpriteBatch sb) {
        sb.draw(img, position.x - radius, position.y - radius, radius * 2, radius * 2);
    }

    public void dispose() {
        shape.dispose();
    }
}
