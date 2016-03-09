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

/**
 * Created by Daniel on 09/03/2016.
 */
public class GameOverScreen implements Screen {
    final GameMain game;
    private Stage stage;
    private Texture texture;

    public GameOverScreen(GameMain game)
    {
        this.game = game;

        texture = new Texture(Gdx.files.internal("resources/game over.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        ResourceManager.loadGameOverMusic();
    }

    @Override
    public void show()
    {
        stage = new Stage();

        Table table = new Table(game.getSkin());
        table.setFillParent(true);
        table.center();

        //TODO LOAD AN IMAGE
        //Label title = new Label("")

        TextButton playButton = new TextButton("PLAY NEW GAME", game.getSkin());
        //playButton.setColor(Color.BLUE);
        playButton.addListener(new ClickListener()
        {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                ResourceManager.stopGameOverMusic();
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
                ResourceManager.stopGameOverMusic();
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        });
        TextButton exitButton = new TextButton("EXIT", game.getSkin());
        //exitButton.setColor(Color.BLUE);
        exitButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ResourceManager.stopGameOverMusic();
                dispose();
                System.exit(0);
            }
        });

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
        Gdx.gl.glClearColor(0, 0, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        //TODO PROBLEM WITH THE COLOR
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