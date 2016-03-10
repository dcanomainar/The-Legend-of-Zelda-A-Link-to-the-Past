package com.dani.zelda.characters;

import com.badlogic.gdx.math.Vector2;

public class Zelda extends Character
{
    public static float SPEED = 50f;
    public int lives;
    public boolean muerto = false;

    public Zelda(float x, float y)
    {
        super(x, y, "zelda", new Vector2(0,0));
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
