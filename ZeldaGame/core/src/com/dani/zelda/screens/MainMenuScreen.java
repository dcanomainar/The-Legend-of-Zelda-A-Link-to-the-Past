package com.dani.zelda.screens;

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
import com.badlogic.gdx.utils.Timer;
import com.dani.zelda.GameMain;
import com.dani.zelda.managers.ResourceManager;
import com.dani.zelda.managers.SpriteManager;
import static com.dani.zelda.util.Constants.*;

public class MainMenuScreen implements Screen
{
    final GameMain game;
    private Stage stage;
    private Texture texture;

    public MainMenuScreen(GameMain game)
    {
        this.game = game;

        texture = new Texture(Gdx.files.internal("resources/title.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        ResourceManager.loadTitleMusic();
    }

    @Override
    public void show()
    {
        stage = new Stage();

        Table table = new Table(game.getSkin());
        table.setFillParent(true);
        table.center();

        TextButton playButton = new TextButton("PLAY NEW GAME", game.getSkin());
        //playButton.setColor(Color.BLUE);
        playButton.addListener(new ClickListener()
        {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                ResourceManager.stopTitleMusic();
                dispose();
                //TODO LANZAR UNA PANTALLA DE INSTRUCCIONES
                game.setScreen(new InfoScreen(game));
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        ResourceManager.stopTitleMusic();
                        game.setScreen(new GameScreen(game, FIRSTX, FIRSTY));
                    }
                }, 4);
            }
        });
        TextButton configButton = new TextButton("CONFIGURATION", game.getSkin());
        //configButton.setColor(Color.BLUE);
        configButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ResourceManager.stopTitleMusic();
                dispose();
                game.setScreen(new ConfigScreen(game));

            }
        });
        TextButton exitButton = new TextButton("EXIT", game.getSkin());
        //exitButton.setColor(Color.BLUE);
        exitButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ResourceManager.stopTitleMusic();
                dispose();
                System.exit(0);
            }
        });

        table.row().height(50);
        table.add(playButton.center()).center().width(300).pad(5f);
        table.row().height(50);
        table.add(configButton).center().width(300).pad(5f);
        table.row().height(50);
        table.add(exitButton).center().width(300).pad(5f);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
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