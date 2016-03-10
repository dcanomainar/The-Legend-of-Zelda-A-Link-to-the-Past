package com.dani.zelda.characters.item;

import static com.dani.zelda.characters.Character.State;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dani.zelda.managers.ResourceManager;

public class Laser
{
    public static float VELOCIDAD = 300f;
    TextureRegion currentFrame;
    public Rectangle rect;
    public Vector2 posicion;
    float stateTime;
    private boolean isRigth;
    public Animation bulletRight, bulletLeft;
    public State state;
    public int contLaser;

    public Laser(float x, float y, State state)
    {
        posicion = new Vector2(x,y);
        bulletRight = new Animation(0.25f, ResourceManager.getAtlas().findRegions("amarillo_derecha"));
        bulletLeft = new Animation(0.25f, ResourceManager.getAtlas().findRegions("amarillo_izquierda"));
        this.state = state;
        contLaser = 0;
        if(this.state == State.RIGHT)
        {
            currentFrame = bulletRight.getKeyFrame(0, true);
        }
        else
        {
            currentFrame = bulletLeft.getKeyFrame(0, true);
        }
        isRigth = true;
        rect = new Rectangle(posicion.x, posicion.y,currentFrame.getRegionWidth(),currentFrame.getRegionHeight());
    }

    public void move(Vector2 movement)
    {
        movement.scl(VELOCIDAD);
        posicion.add(movement);
    }

    public void render(Batch batch)
    {
        batch.draw(currentFrame, posicion.x, posicion.y);
    }

    public void update(float dt)
    {
        rect.set(posicion.x, posicion.y, rect.getWidth(), rect.getHeight());
        contLaser++;
        System.out.println(rect.x);
    }
}