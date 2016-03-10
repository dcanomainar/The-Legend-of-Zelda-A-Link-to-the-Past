package com.dani.zelda.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.dani.zelda.util.Constants;

public class ConfigurationManager
{
    private static Preferences prefs = Gdx.app.getPreferences(Constants.APP);;

    public static boolean isSoundEnabled()
    {
        return prefs.getBoolean("sound");
    }

    public static boolean isDifficultEnabled()
    {
        return prefs.getBoolean("difficult");
    }
}