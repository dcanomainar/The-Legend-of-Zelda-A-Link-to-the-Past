package com.dani.zelda.characters;

import com.badlogic.gdx.math.Vector2;

public class BlueEnemy extends Enemy
{

    private float speed;

    public BlueEnemy(float x, float y, String name, float speed)
    {
        super(x, y, name,new Vector2(0, 0));
        this.speed = speed;
    }

    public void update(float dt)
    {

    }

    public void fire()
    {

    }
}