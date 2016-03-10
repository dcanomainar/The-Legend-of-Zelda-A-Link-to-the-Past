package com.dani.zelda.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.dani.zelda.GameMain;
import com.dani.zelda.managers.ResourceManager;
import com.dani.zelda.util.Constants;

import static com.dani.zelda.util.Constants.*;

public class ConfigScreen implements Screen
{
    final GameMain game;
    private Stage stage;
    private Texture texture;
    Preferences prefs;

    public ConfigScreen(GameMain game)
    {
        this.game = game;

        texture = new Texture(Gdx.files.internal("resources/title.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        ResourceManager.loadTitleMusic();
    }

    @Override
    public void show()
    {
        loadPreferences();
        stage = new Stage();

        Table table = new Table(game.getSkin());
        table.setFillParent(true);
        table.center();

        final CheckBox checkSound = new CheckBox(" Sound", game.getSkin());
        checkSound.setChecked(prefs.getBoolean("sound"));
        checkSound.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                prefs.putBoolean("sound", checkSound.isChecked());
            }
        });

        final CheckBox checkDifficult = new CheckBox(" Difficult", game.getSkin());
        checkDifficult.setChecked(prefs.getBoolean("difficult"));
        checkDifficult.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                prefs.putBoolean("difficult", checkDifficult.isChecked());
            }
        });

        TextButton playButton = new TextButton("ACCEPT", game.getSkin());
        //playButton.setColor(Color.BLUE);
        playButton.addListener(new ClickListener()
        {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                ResourceManager.stopTitleMusic();
                dispose();
                //TODO LANZAR UNA PANTALLA DE INSTRUCCIONES
                game.setScreen(new MainMenuScreen(game));
            }
        });
        TextButton configButton = new TextButton("CANCEL", game.getSkin());
        //configButton.setColor(Color.BLUE);
        configButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                ResourceManager.stopTitleMusic();
                game.setScreen(new MainMenuScreen(game));
            }
        });
        table.add(checkSound);
        table.add(checkDifficult);
        table.row().height(50);
        table.add(playButton.center()).center().width(300).pad(5f);
        table.row().height(50);
        table.add(configButton).center().width(300).pad(5f);
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

    private void loadPreferences() {

        prefs = Gdx.app.getPreferences(Constants.APP);

        if (!prefs.contains("sound"))
            prefs.putBoolean("sound", true);

        if (!prefs.contains("difficult"))
            prefs.putBoolean("difficult", true);
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