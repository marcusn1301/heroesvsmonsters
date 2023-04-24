package com.mygdx.game.ds.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Null;

public class RectangleButton {

    private final Vector2 position;
    private final Rectangle bounds;
    private final Texture img;
    private final float width;
    private final float height;
    private final float scale;

    public RectangleButton(float scale, @Null Integer xStart,@Null Integer yStart, String internalPath) {
        float startPosX;
        float startPosY;
        this.scale = scale;
        this.img = new Texture(internalPath);
        this.width = img.getWidth() * scale;
        this.height = img.getHeight() * scale;

        if (xStart == null) {
            startPosX = Gdx.graphics.getWidth() / 2f - width / 2f;
        } else {
            startPosX = xStart;
        }

        if (yStart == null) {
            startPosY = Gdx.graphics.getHeight() / 2f - height / 2f;
        } else {
            startPosY = yStart;
        }

        this.position = new Vector2(startPosX, startPosY);
        this.bounds = new Rectangle(startPosX, startPosY, this.width, this.height);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getWidth() {return this.width;}

    public float getHeight() {return this.height;}

    public Texture getImg() {
        return img;
    }

    public void render(SpriteBatch sb) {
        sb.draw(img, position.x, position.y, width, height);
    }

    public void dispose() {

    }
}
