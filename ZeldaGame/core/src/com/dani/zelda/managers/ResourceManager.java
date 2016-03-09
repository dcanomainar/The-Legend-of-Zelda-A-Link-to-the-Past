package com.dani.zelda.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ResourceManager
{
    private static TextureAtlas atlas;
    private static TextureAtlas atlas2;
    private static TextureAtlas atlas3;
    private static Music music;
    public static Music musicTitle;
    public static Music musicEnd;
    public static Music musicGameOver;
    public static Music castleMusic;
    private static Sound sound;

    public static void loadAllResources()
    {
        atlas = new TextureAtlas(Gdx.files.internal("resources/zelda/zelda.atlas"));
        atlas2 = new TextureAtlas(Gdx.files.internal("resources/zelda/attack/zelda.atlas"));
        atlas3 = new TextureAtlas(Gdx.files.internal("resources/enemies/enemy_1/blueEnemy.atlas"));
		//sound.setVolume(10);
    }

    public static void loadTitleMusic()
    {
        musicTitle = Gdx.audio.newMusic(Gdx.files.internal("title screen.mp3"));
        musicTitle.setLooping(true);
        musicTitle.setVolume(5);
        startMusicTitle();
    }

    public static void loadEndGameMusic()
    {
        musicEnd = Gdx.audio.newMusic(Gdx.files.internal("end game.mp3"));
        //musicEnd.setLooping(true);
        musicEnd.setVolume(5);
        startEndMusic();
    }

    public static void stopTitleMusic()
    {
        musicTitle.stop();
    }

    public static void loadMusic()
    {
        music = Gdx.audio.newMusic(Gdx.files.internal("base music.mp3"));
        music.setLooping(true);
        music.setVolume(5);
        startMusic();

        sound = Gdx.audio.newSound(Gdx.files.internal("sword attack.ogg"));
    }

    public static void loadGameOverMusic()
    {
        musicGameOver = Gdx.audio.newMusic(Gdx.files.internal("game_over.mp3"));
        musicGameOver.setLooping(true);
        musicGameOver.setVolume(5);
        startGameOverMusic();
    }

    public static void loadCastleMusic()
    {
        castleMusic = Gdx.audio.newMusic(Gdx.files.internal("castle.mp3"));
        castleMusic.setLooping(true);
        castleMusic.setVolume(5);
        startGameOverMusic();

        startCastleMusic();

        sound = Gdx.audio.newSound(Gdx.files.internal("sword attack.ogg"));
    }

    public static void startGameOverMusic()
    {
        musicGameOver.play();
    }

    public static void startCastleMusic()
    {
        castleMusic.play();
    }

    public static void stopCastleMusic()
    {
        castleMusic.stop();
    }

    public static void stopGameOverMusic()
    {
        musicGameOver.stop();
    }

    public static Music getGameOverMusic()
    {
        return musicGameOver;
    }

    public static TextureAtlas getAtlas()
    {
        return atlas;
    }

    public static TextureAtlas getAtlas2()
    {
        return atlas2;
    }

    public static TextureAtlas getAtlas3()
    {
        return atlas3;
    }

    public Sound getSound()
    {
        return sound;
    }

    public static void stopSound()
    {
        sound.dispose();
    }

    public static void startSound()
    {
        sound.play();
    }

    public static Music getMusic()
    {
        return music;
    }

    public static void stopMusic()
    {
        music.dispose();
    }

    public static void startMusic()
    {
        music.play();
    }

    public static void startMusicTitle()
    {
        musicTitle.play();
    }

    public static void startEndMusic()
    {
        musicEnd.play();
    }

    public static void stopEndMusic()
    {
        musicEnd.stop();
    }

    public static Music getEndMusic()
    {
        return musicEnd;
    }
}