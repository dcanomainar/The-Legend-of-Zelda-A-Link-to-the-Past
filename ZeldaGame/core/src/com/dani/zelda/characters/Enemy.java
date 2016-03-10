package com.dani.zelda.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dani.zelda.managers.ResourceManager;

public abstract class Enemy extends Character
{
    public boolean disparo;

    public Enemy(float x, float y, String name,Vector2 velocity)
    {
        super(x, y, name,velocity);
        disparo = false;
    }

    public void fire()
    {

    }
}