package com.dani.zelda.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dani.zelda.GameMain;
import com.dani.zelda.managers.ResourceManager;

import static com.dani.zelda.util.Constants.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dani.zelda.GameMain;
import com.dani.zelda.managers.ResourceManager;
import com.dani.zelda.managers.SpriteManager;
import static com.dani.zelda.util.Constants.*;

public class InfoScreen implements Screen
{
    final GameMain game;
    private Stage stage;
    private Texture texture;

    public InfoScreen(GameMain game)
    {
        this.game = game;

        texture = new Texture(Gdx.files.internal("resources/zelda.jpg"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        ResourceManager.loadTitleMusic();

        //game.setScreen(new GameScreen(game, FIRSTX, FIRSTY));
    }

    @Override
    public void show()
    {
        stage = new Stage();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        stage.getBatch().begin();
        stage.getBatch().draw(texture, 1, 1, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.getBatch().end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height)
    {
        //stage.setViewport(width, height);
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

    }
}