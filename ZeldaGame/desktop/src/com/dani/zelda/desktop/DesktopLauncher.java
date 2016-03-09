package com.dani.zelda.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dani.zelda.GameMain;

public class DesktopLauncher
{
	public static void main(String[] args)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.useGL30 = true;
		config.title = "The Legend of Zelda: A Link to the Past";
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new GameMain(), config);
	}
}
