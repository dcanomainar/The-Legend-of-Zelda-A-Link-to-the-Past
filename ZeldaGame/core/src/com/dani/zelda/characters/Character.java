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
    public String name;

    public Character(float x, float y, String name,Vector2 velocity)
    {
        lastMove = "DOWN";
        this.name = name;

        leftAnimation = new Animation(0.1f, ResourceManager.getAtlas().findRegions(name + "_left"));
        rightAnimation = new Animation(0.1f, ResourceManager.getAtlas().findRegions(name + "_right"));
        upAnimation = new Animation(0.1f, ResourceManager.getAtlas().findRegions(name + "_up"));
        downAnimation = new Animation(0.1f, ResourceManager.getAtlas().findRegions(name + "_down"));
        currentFrame = downAnimation.getKeyFrame(1, true);

        state = State.DOWN;

        if(name.equals("zelda"))
        {
            deadAnimation = new Animation(0.1f, ResourceManager.getAtlas().findRegions("zelda_dead"));

            downAttackAnimation =  new Animation(0.08f, ResourceManager.getAtlas().findRegions(name + "" + "_down_attack"));
            upAttackAnimation =  new Animation(0.08f, ResourceManager.getAtlas().findRegions(name + "" + "_up_attack"));
            leftAttackAnimation =  new Animation(0.08f, ResourceManager.getAtlas().findRegions(name + "" + "_left_attack"));
            rightAttackAnimation =  new Animation(0.08f, ResourceManager.getAtlas().findRegions(name + "" + "_right_attack"));

            currentFrame = downAnimation.getKeyFrame(1, true);
        }

        //heart = ResourceManager.getAtlas().findRegion("heart");

        position.x = x;
        position.y = y;

        rect = new Rectangle(position.x, position.y, currentFrame.getRegionWidth() / 2, currentFrame.getRegionHeight() / 2);

        frame = 1;

        sw = 0;
    }

    public void update(float dt, String name)
    {
        if(name.equals("zelda"))
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
                    rect.x = rect.x*3;
                    rect.y = rect.y*3;
                    if(lastMove.equals("DOWN"))
                    {
                        currentFrame = downAttackAnimation.getKeyFrame(stateTime, true);
                        if(ResourceManager.sound != null)
                        {
                            ResourceManager.startSound();
                        }

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
                        if(ResourceManager.sound != null)
                        {
                            ResourceManager.startSound();
                        }
                    }
                    else if(lastMove.equals("RIGHT"))
                    {
                        currentFrame = rightAttackAnimation.getKeyFrame(stateTime, true);
                        //if(rightAttackAnimation.isAnimationFinished(stateTime))
                        //{
                        //    state = State.RIGHT;
                        //}
                        if(ResourceManager.sound != null)
                        {
                            ResourceManager.startSound();
                        }

                    }
                    else if(lastMove.equals("UP"))
                    {
                        currentFrame = upAttackAnimation.getKeyFrame(stateTime, true);
                        //if(upAttackAnimation.isAnimationFinished(stateTime))
                        //{
                        //state = State.UP;
                        //}
                        if(ResourceManager.sound != null)
                        {
                            ResourceManager.startSound();
                        }
                    }
                    break;
                case STOP:
                    //TODO GET THE STOP IMAGE
                    if(lastMove.equals("UP"))
                        currentFrame = upAnimation.getKeyFrame(4, true);
                    if(lastMove.equals("DOWN"))
                        currentFrame = downAnimation.getKeyFrame(4, true);
                    if(lastMove.equals("LEFT"))
                        currentFrame = leftAnimation.getKeyFrame(4, true);
                    if(lastMove.equals("RIGHT"))
                        currentFrame = rightAnimation.getKeyFrame(4, true);
                    break;
            }
        }
        else if(name.equals("enemy1"))
        {
            rect.x = position.x;
            rect.y = position.y;
            rect.setWidth(currentFrame.getRegionWidth() / 2);
            rect.setHeight(currentFrame.getRegionHeight() / 2);

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
                case STOP:
                    //TODO GET THE STOP IMAGE
                    if(lastMove.equals("UP"))
                        currentFrame = upAnimation.getKeyFrame(4, true);
                    if(lastMove.equals("DOWN"))
                        currentFrame = downAnimation.getKeyFrame(4, true);
                    if(lastMove.equals("LEFT"))
                        currentFrame = leftAnimation.getKeyFrame(4, true);
                    if(lastMove.equals("RIGHT"))
                        currentFrame = rightAnimation.getKeyFrame(4, true);
                    break;
            }
        }
        else if(name.equals("enemy2"))
        {
            rect.x = position.x;
            rect.y = position.y;
            rect.setWidth(currentFrame.getRegionWidth() / 2);
            rect.setHeight(currentFrame.getRegionHeight() / 2);

            switch (state)
            {
                case UP:
                    currentFrame = upAnimation.getKeyFrame(stateTime, true);
                    lastMove = "UP";
                    break;
                case DOWN:
                    currentFrame = downAnimation.getKeyFrame(stateTime, true);
                    lastMove = "DOWN";
                    break;
                case LEFT:
                    currentFrame = leftAnimation.getKeyFrame(stateTime, true);
                    lastMove = "LEFT";
                    break;
                case RIGHT:
                    currentFrame = rightAnimation.getKeyFrame(stateTime, true);
                    lastMove = "RIGHT";
                    break;
                case STOP:
                    if(lastMove.equals("UP"))
                        currentFrame = upAnimation.getKeyFrame(1, true);
                    if(lastMove.equals("DOWN"))
                        currentFrame = downAnimation.getKeyFrame(1, true);
                    if(lastMove.equals("LEFT"))
                        currentFrame = leftAnimation.getKeyFrame(1, true);
                    if(lastMove.equals("RIGHT"))
                        currentFrame = rightAnimation.getKeyFrame(1, true);
                    break;
            }
        }
        else if(name.equals("enemy3"))
        {
            rect.x = position.x;
            rect.y = position.y;
            rect.setWidth(currentFrame.getRegionWidth() / 2);
            rect.setHeight(currentFrame.getRegionHeight() / 2);

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
                case STOP:
                    if(lastMove.equals("UP"))
                        currentFrame = upAnimation.getKeyFrame(1, true);
                    if(lastMove.equals("DOWN"))
                        currentFrame = downAnimation.getKeyFrame(1, true);
                    if(lastMove.equals("LEFT"))
                        currentFrame = leftAnimation.getKeyFrame(1, true);
                    if(lastMove.equals("RIGHT"))
                        currentFrame = rightAnimation.getKeyFrame(1, true);
                    break;
            }
        }


        stateTime += dt;

        velocity.scl(dt);
        rect.set(position.x, position.y, rect.getWidth(), rect.getHeight());
        position.add(velocity);
    }

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