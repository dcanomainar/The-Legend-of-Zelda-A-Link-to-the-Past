package com.dani.zelda.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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

public class Credits implements Screen
{
    GameMain game;
    private Stage stage;
    public SpriteManager spriteManager;
    private Texture texture;

    public Credits(GameMain game)
    {
        this.game = game;

        texture = new Texture(Gdx.files.internal("resources/end.jpg"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void show()
    {
        stage = new Stage();
        ResourceManager.loadEndGameMusic();

        Table table = new Table(game.getSkin());
        //TODO TOP OR CENTER ??
        //table.top();
        //table.center();
        table.setFillParent(true);

        Label title = new Label("Special Thanks to:", game.getSkin());
        Label name1 = new Label("Guillermo Tovar", game.getSkin());
        Label name2 = new Label("Ainoa Andres", game.getSkin());
        title.setFontScale(2);
        name1.setFontScale(2);
        name2.setFontScale(2);

        table.row().height(50);
        table.add(title).expandX();
        table.row().height(50);
        table.add(name1).expandX();
        table.row().height(50);
        table.add(name2).expandX();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        stage.getBatch().begin();
        stage.getBatch().draw(texture, 1, 1, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.getBatch().end();

        stage.draw();

        if(!ResourceManager.getEndMusic().isPlaying())
        {
            ResourceManager.stopEndMusic();
            game.setScreen(new EndScreen(game));
        }
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

    }
}