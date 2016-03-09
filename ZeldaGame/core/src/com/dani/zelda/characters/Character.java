package com.dani.zelda.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.dani.zelda.managers.ResourceManager;
import com.dani.zelda.managers.SpriteManager;
import static com.dani.zelda.util.Constants.*;

public class Character
{
    public enum State
    {
        RIGHT, LEFT, UP, DOWN, STOP, ATTACK, DEAD
    }

    public State state;

    public String lastMove;

    float stateTime;
    public int frame;
    Animation rightAnimation;
    Animation leftAnimation;
    Animation upAnimation;
    Animation downAnimation;

    Animation downAttackAnimation;
    Animation upAttackAnimation;
    Animation leftAttackAnimation;
    Animation rightAttackAnimation;

    Animation deadAnimation;

    TextureRegion currentFrame;
    public TextureRegion heart;

    public Rectangle rect;
    boolean dead;

    int sw;

    public Vector2 position = new Vector2();
    public Vector2 velocity = new Vector2();

    public Character(float x, float y, String name)
    {
        lastMove = "DOWN";

        leftAnimation = new Animation(0.1f, ResourceManager.getAtlas().findRegions(name + "_left"));
        rightAnimation = new Animation(0.1f, ResourceManager.getAtlas().findRegions(name + "_right"));
        upAnimation = new Animation(0.1f, ResourceManager.getAtlas().findRegions(name + "_up"));
        downAnimation = new Animation(0.1f, ResourceManager.getAtlas().findRegions(name + "_down"));

        //TODO WE HAVE A PROBLEM WITH THIS PIECE OF SHIT
        deadAnimation = new Animation(0.1f, ResourceManager.getAtlas().findRegions("zelda_dead"));

        downAttackAnimation =  new Animation(0.08f, ResourceManager.getAtlas2().findRegions(name + "" + "_down_attack"));
        upAttackAnimation =  new Animation(0.08f, ResourceManager.getAtlas2().findRegions(name + "" + "_up_attack"));
        leftAttackAnimation =  new Animation(0.08f, ResourceManager.getAtlas2().findRegions(name + "" + "_left_attack"));
        rightAttackAnimation =  new Animation(0.08f, ResourceManager.getAtlas2().findRegions(name + "" + "_right_attack"));

        heart = ResourceManager.getAtlas().findRegion("heart");

        currentFrame = downAnimation.getKeyFrame(3);

        position.x = x;
        position.y = y;

        rect = new Rectangle(position.x, position.y, currentFrame.getRegionWidth() / 2, currentFrame.getRegionHeight() / 2);

        frame = 1;

        sw = 0;
    }

    public void update(float dt)
    {
        rect.x = position.x;
        rect.y = position.y;
        rect.setWidth(currentFrame.getRegionWidth() / 2);
        rect.setHeight(currentFrame.getRegionHeight() / 2);

        //rect = new Rectangle(position.x, position.y, currentFrame.getRegionWidth() / 2, currentFrame.getRegionHeight() / 2);

        switch (state)
        {
            case LEFT:
                currentFrame = leftAnimation.getKeyFrame(stateTime, true);
                lastMove = "LEFT";
                break;
            case RIGHT:
                currentFrame = rightAnimation.getKeyFrame(stateTime, true);
                lastMove = "RIGHT";
                break;
            case UP:
                currentFrame = upAnimation.getKeyFrame(stateTime, true);
                lastMove = "UP";
                break;
            case DOWN:
                currentFrame = downAnimation.getKeyFrame(stateTime, true);
                lastMove = "DOWN";
                break;
            case DEAD:
                currentFrame = deadAnimation.getKeyFrame(stateTime, true);
                break;
            case ATTACK:
                if(lastMove.equals("DOWN"))
                {
                    currentFrame = downAttackAnimation.getKeyFrame(stateTime, true);
                    ResourceManager.startSound();

                    //if(!downAnimation.isAnimationFinished(stateTime))
                        //ResourceManager.s
                    //if(downAnimation.isAnimationFinished(stateTime))
                    //{
                    //     state = State.ATTACK;
                    //}
                }
                else if(lastMove.equals("LEFT"))
                {
                    currentFrame = leftAttackAnimation.getKeyFrame(stateTime, true);
                    //if(leftAttackAnimation.isAnimationFinished(stateTime))
                    //{
                    //    state = State.LEFT;
                    //}
                    ResourceManager.startSound();
                }
                else if(lastMove.equals("RIGHT"))
                {
                    currentFrame = rightAttackAnimation.getKeyFrame(stateTime, true);
                    //if(rightAttackAnimation.isAnimationFinished(stateTime))
                    //{
                    //    state = State.RIGHT;
                    //}
                    ResourceManager.startSound();

                }
                else if(lastMove.equals("UP"))
                {
                    currentFrame = upAttackAnimation.getKeyFrame(stateTime, true);
                    //if(upAttackAnimation.isAnimationFinished(stateTime))
                    //{
                        //state = State.UP;
                    //}
                    ResourceManager.startSound();
                }
                break;
            case STOP:
                //TODO GET THE STOP IMAGE
                if(lastMove.equals("UP"))
                    currentFrame = upAnimation.getKeyFrame(4);
                if(lastMove.equals("DOWN"))
                    currentFrame = downAnimation.getKeyFrame(4);
                if(lastMove.equals("LEFT"))
                    currentFrame = leftAnimation.getKeyFrame(4);
                if(lastMove.equals("RIGHT"))
                    currentFrame = rightAnimation.getKeyFrame(4);
                break;
        }

        stateTime += dt;

        velocity.scl(dt);
        rect.set(position.x, position.y, rect.getWidth(), rect.getHeight());
        position.add(velocity);
    }

    /*
    public void checkCollisions(SpriteManager spriteManager)
    {
        int startY, endY, startX, endX;
        // Comprueba las colisiones en el eje Y
        if (velocity.y > 0)
            startY = endY = (int) (position.y + rect.getHeight() + velocity.y);
        else
            startY = endY = (int) (position.y + velocity.y);

        startX = (int) position.x;
        endX = (int) (position.x + rect.getWidth());

        spriteManager.getCollisionTiles(this, startX, endX, startY, endY);
        rect.y += velocity.y;
        for (Rectangle tile : spriteManager.tiles)
        {
            if (rect.overlaps(tile))
            {
                if (velocity.y > 0)
                {
                    position.y = tile.y - rect.getHeight();
                } else {
                    position.y = tile.y + TILE_HEIGHT;
                }
                velocity.y = 0;
                break;
            }
        }

        // Comprueba las colisiones en el eje X
        if (velocity.x > 0)
            startX = endX = (int) (position.x + rect.getWidth() + velocity.x);
        else
            startX = endX = (int) (position.x + velocity.x);

        startY = (int) position.y;
        endY = (int) (position.y + rect.getHeight());

        spriteManager.getCollisionTiles(this, startX, endX, startY, endY);
        rect.x += velocity.x;
        for (Rectangle tile : spriteManager.tiles)
        {
            if (rect.overlaps(tile))
            {
                //if (this instanceof Enemy)
                //{
                    //velocity.x = -velocity.x;
               // }
                //else
                    velocity.x = 0;
                // break;
            }

            velocity.x = 0;
        }
        rect.x = position.x;
    }
    */

    public void render(Batch batch)
    {
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