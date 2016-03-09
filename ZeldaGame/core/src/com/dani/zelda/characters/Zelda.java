package com.dani.zelda.characters;

public class Zelda extends Character
{
    public static float SPEED = 50f;
    public int lives;

    public Zelda(float x, float y)
    {
        super(x, y, "zelda");
        lives = 3;
    }

    public void attack()
    {

    }

    @Override
    public void die()
    {
        super.die();
        lives--;
    }
}
