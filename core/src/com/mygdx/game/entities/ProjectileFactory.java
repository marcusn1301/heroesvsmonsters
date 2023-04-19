package com.mygdx.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.AttackComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.HealthComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.ProjectileComponent;
import com.mygdx.game.components.SpriteComponent;
import com.mygdx.game.types.HeroType;

public class ProjectileFactory {
    public static Projectile createProjectile(HeroType heroType, Vector2 startingPosition, float velocity, Entity sourceEntity) {
        Projectile projectile = new Projectile();
        projectile.setProjectileComponent(new ProjectileComponent(true, velocity, sourceEntity));
        projectile.setSpriteComponent(new SpriteComponent(getProjectileSprite(heroType)));
        projectile.setCollisionComponent(new CollisionComponent(new Rectangle(startingPosition.x, startingPosition.y, 10f, 10f)));
        projectile.setPositionComponent(new PositionComponent(startingPosition));
        return projectile;
    }

    private static Texture getProjectileSprite(HeroType heroType) {
        //TODO endre path til en projectile
        Texture texture = null;
        switch (heroType) {
            case IRONMAN:
                return new Texture(Gdx.files.internal("characterIcon3.png"));
            case HULK:
                return new Texture(Gdx.files.internal("characterIcon3.png"));
            case SPIDERMAN:
                return new Texture(Gdx.files.internal("characterIcon3.png"));
            case THOR:
                return new Texture(Gdx.files.internal("characterIcon3.png"));
            case CAPTAIN_AMERICA:
                return new Texture(Gdx.files.internal("characterIcon3.png"));
            default:
                return null;
        }
    }


}
