package com.dani.zelda.characters;

import com.badlogic.gdx.math.Vector2;
import com.dani.zelda.managers.SpriteManager;

import java.util.Random;

public class YellowEnemy extends Enemy
{
    private int speed;
    private int distancia;
    private float posicionInicial;
    public boolean derecha;
    public int conttiempoBalas;

    public YellowEnemy(float x, float y, String name, int speed, int offset)
    {
        super(x, y, name,new Vector2(0, 0));
        this.speed = speed;
        this.distancia = offset;
        posicionInicial = x;
        derecha = true;
    }

    /*
    @Override
    public void fire()
    {
        Random r = new Random();
        int numero = r.nextInt(100);
        if(numero == 5 && conttiempoBalas > 50)
        {
            disparo = true;
            conttiempoBalas = 0;
        }
        else
        {
            conttiempoBalas++;
        }
        super.fire();
    }
*/
    /*
    public void update(float dt)
    {
        super.update(dt, "enemy2");
        if (derecha)
        {
            state = State.RIGHT;
            position.x += speed * dt;
            if (position.x >= posicionInicial + distancia)
                derecha = false;
        }
        else
        {
            state = State.LEFT;
            position.x -= speed * dt;
            if (position.x <= posicionInicial)
                derecha = true;
        }
    }
    */
}