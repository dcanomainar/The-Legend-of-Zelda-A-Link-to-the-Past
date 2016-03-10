package com.dani.zelda;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.dani.zelda.characters.Enemy;
import com.dani.zelda.characters.item.Laser;
import com.dani.zelda.managers.LevelManager;
import com.dani.zelda.managers.ResourceManager;
import com.dani.zelda.managers.SpriteManager;
import com.dani.zelda.screens.*;

public class GameMain extends Game
{
	public boolean paused;
	public Skin skin;
	public int sw;
	public LevelManager levelManager;
	public Array<Enemy> enemies;
	public Array<Laser> lasers;

	@Override
	public void create ()
	{
		sw = 0;

		enemies = new Array<Enemy>();
		lasers = new Array<Laser>();
		setScreen(new MainMenuScreen(this));
		//setScreen(new EndScreen(this));
		//setScreen(new Credits(this));
		//setScreen(new GameOverScreen(this));
	}

	@Override
	public void render ()
	{
		super.render();
	}

	public Skin getSkin()
	{
		if(skin == null)
			skin = new Skin(Gdx.files.internal("uiskin.json"));
		return skin;
	}

	public void reinstanciarEnemigos()
	{
		this.enemies = new Array<Enemy>();
	}
}