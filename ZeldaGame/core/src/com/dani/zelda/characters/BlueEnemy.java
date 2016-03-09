package com.dani.zelda.characters;

public class BlueEnemy extends Enemy
{
    private float speed;

    public BlueEnemy(float x, float y, String name, float speed)
    {
        super(x, y, "blue");
        this.speed = speed;
    }

    public void fire()
    {

    }
}