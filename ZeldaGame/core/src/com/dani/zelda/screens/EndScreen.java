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

public class EndScreen implements Screen
{
    GameMain game;
    private Stage stage;
    public SpriteManager spriteManager;
    private Texture texture;

    public EndScreen(GameMain game)
    {
        this.game = game;

        texture = new Texture(Gdx.files.internal("resources/end.jpg"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void show()
    {
        stage = new Stage();

        ResourceManager.loadTitleMusic();

        Table table = new Table(game.getSkin());
        table.setFillParent(true);
        table.center();

        Label title = new Label("THE END", game.getSkin());
        title.setFontScale(2);

        TextButton playButton = new TextButton("PLAY AGAIN", game.getSkin());
        //playButton.setColor(Color.BLUE);
        playButton.addListener(new ClickListener()
        {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                ResourceManager.stopTitleMusic();
                dispose();
                game.setScreen(new GameScreen(game, FIRSTX, FIRSTY));
            }
        });
        TextButton menu = new TextButton("MAIN MENU", game.getSkin());
        //menu.setColor(Color.BLUE);
        menu.addListener(new ClickListener()
        {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                ResourceManager.stopTitleMusic();
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });
        TextButton exitButton = new TextButton("EXIT", game.getSkin());
        //exitButton.setColor(Color.BLUE);
        exitButton.addListener(new ClickListener()
        {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                ResourceManager.stopEndMusic();
                dispose();
                System.exit(0);
            }
        });

        table.row().height(50);
        table.add(title).expandX();
                //.center().width(300).pad(5f);
        table.row().height(50);
        table.add(playButton.center()).center().width(300).pad(5f);
        table.row().height(50);
        table.add(menu).center().width(300).pad(5f);
        table.row().height(50);
        table.add(exitButton).center().width(300).pad(5f);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        //TODO MAKE IT AS A SPLASH

        stage.getBatch().begin();
        stage.getBatch().draw(texture, 1, 1, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.getBatch().end();

        stage.draw();
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