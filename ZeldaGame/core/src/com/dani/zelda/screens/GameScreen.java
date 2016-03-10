package com.dani.zelda.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dani.zelda.GameMain;
import com.dani.zelda.managers.ConfigurationManager;
import com.dani.zelda.managers.ResourceManager;
import com.dani.zelda.managers.SpriteManager;
import static com.dani.zelda.util.Constants.*;

public class GameScreen implements Screen
{
    final GameMain game;
    ShaderProgram shader;
    public Batch spriteBatch;
    public BitmapFont font;
    public SpriteManager spriteManager;

    public GameScreen(GameMain game, int x, int y)
    {
        this.game = game;

        spriteBatch = new SpriteBatch();
        font = new BitmapFont();

        ResourceManager.loadAllResources();
        spriteManager = new SpriteManager(game, x, y);

        if(ConfigurationManager.isSoundEnabled())
        {
            ResourceManager.loadMusic();
        }

    }

    @Override
    public void show()
    {
        //TODO IMPLEMENTAR BARRA DE INFORMACIÓN CON LAS VIDAS DEL PERSONAJE ASÍ COMO EL MAPA EN EL QUE SE ENCUENTRA
        //ShaderProgram.pedantic = false;
        //shader = new ShaderProgram(Gdx.files.internal("shaders/passthrough.vsh"), Gdx.files.internal("shaders/passthrough.fsh"));
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteManager.update(delta);
        spriteManager.render();
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        spriteBatch.dispose();
        font.dispose();
    }
}
