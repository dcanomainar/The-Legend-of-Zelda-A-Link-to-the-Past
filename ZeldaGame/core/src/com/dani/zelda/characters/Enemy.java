package com.dani.zelda.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dani.zelda.managers.ResourceManager;

public abstract class Enemy
{
    public enum State
    {
        RIGHT, LEFT, UP, DOWN, STOP, ATTACK
    }

    public State state;

    float stateTime;
    public int frame;
    Animation rightAnimation;
    Animation leftAnimation;
    Animation upAnimation;
    Animation downAnimation;

    TextureRegion currentFrame;

    public Rectangle rect;
    boolean dead;

    int sw;

    public Vector2 position = new Vector2();
    public Vector2 velocity = new Vector2();

    public Enemy(float x, float y, String name)
    {
        //TODO PORQUE NO STRING???
        int i = 0;
        if(name.equals("blue"))
            i = 1;
        switch(i)
        {
            case 1:
                leftAnimation = new Animation(0.1f, ResourceManager.getAtlas3().findRegions("enemy1" + "_left"));
                rightAnimation = new Animation(0.1f, ResourceManager.getAtlas3().findRegions("enemy1" + "_right"));
                upAnimation = new Animation(0.1f, ResourceManager.getAtlas3().findRegions("enemy1" + "_up"));
                downAnimation = new Animation(0.1f, ResourceManager.getAtlas3().findRegions("enemy1" + "_down"));

                currentFrame = downAnimation.getKeyFrame(1);

                position.x = x;
                position.y = y;

                rect = new Rectangle(position.x, position.y, currentFrame.getRegionWidth() / 2, currentFrame.getRegionHeight() / 2);

                frame = 1;

                sw = 0;
                break;
        }
    }

    public void update(float dt)
    {
        //TODO IMPLEMENTAR CASOS EN FUNCION DEL NOMBRE DEL NPC

        rect.x = position.x;
        rect.y = position.y;
        rect.setWidth(currentFrame.getRegionWidth() / 2);
        rect.setHeight(currentFrame.getRegionHeight() / 2);

        //rect = new Rectangle(position.x, position.y, currentFrame.getRegionWidth() / 2, currentFrame.getRegionHeight() / 2);

        //TODO MAKE THE MOVEMENT

        stateTime += dt;

        //velocity.scl(dt);
        rect.set(position.x, position.y, rect.getWidth(), rect.getHeight());
        //position.add(velocity);
    }

    //TODO PASARLO AL RENDER DEL SPRITEMANAGER donde no ha muerto
    public void render(Batch batch)
    {
        //enemy.render
        batch.draw(currentFrame, position.x, position.y, rect.getWidth(), rect.getHeight());
    }

    public void die()
    {
        dead = true;
    }

    public boolean isDead()
    {
        return dead;
    }
}