package com.dani.zelda.managers;

import com.badlogic.gdx.graphics.g3d.particles.values.MeshSpawnShapeValue;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dani.zelda.characters.BlueEnemy;
import com.dani.zelda.characters.Enemy;
import com.dani.zelda.characters.RedEnemy;
import com.dani.zelda.characters.YellowEnemy;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;

public class LevelManager
{
    private int currentLevel;
    private SpriteManager spriteManager;

    public static Array<Rectangle> terreno = new Array<Rectangle>();
    public static Array<Rectangle> transiciones1 = new Array<Rectangle>();
    public static Array<Rectangle> transiciones2 = new Array<Rectangle>();
    public static Array<Rectangle> transiciones3 = new Array<Rectangle>();
    public static Array<Rectangle> endGame = new Array<Rectangle>();

    //UNUSED
    public static Array<Polygon> terreno2 = new Array<Polygon>();

    TiledMap map;
    //TiledMapTileLayer collisionLayer;
    MapLayer collisionLayer;
    MapLayer collisionLayer3;
    MapLayer transicionesLayer1;
    MapLayer transicionesLayer2;
    MapLayer transicionesLayer3;
    MapLayer blueEnemyLayer;
    MapLayer yellowEnemyLayer;
    MapLayer redEnemyLayer;
    MapLayer endGameLayer;

    OrthogonalTiledMapRenderer mapRenderer;

    private static float ppt = 0;

    public LevelManager(SpriteManager spriteManager)
    {
        this.spriteManager = spriteManager;
        currentLevel = 0;

        //spriteManager.enemies = new Array<Enemy>();
    }

    public void loadCurrentLevel()
    {
        if(currentLevel > 3)
            map = new TmxMapLoader().load("map3" + ".tmx");
        else
            map = new TmxMapLoader().load("map" + currentLevel + ".tmx");
        //map = new TmxMapLoader().load("map1" + ".tmx");
        //collisionLayer = (TiledMapTileLayer) map.getLayers().get("colisiones");
        System.out.println(currentLevel);
        if(currentLevel == 0)
        {
            collisionLayer = map.getLayers().get("objects");
            transicionesLayer1 = map.getLayers().get("transiciones1");
            mapRenderer = new OrthogonalTiledMapRenderer(map);

            transiciones1.clear();
            loadTransiciones1();
        }
        else if(currentLevel == 1)
        {
            collisionLayer = map.getLayers().get("objects");
            transicionesLayer1 = map.getLayers().get("transiciones1");
            transicionesLayer2 = map.getLayers().get("transiciones2");

            blueEnemyLayer = map.getLayers().get("blue");

            mapRenderer = new OrthogonalTiledMapRenderer(map);

            transiciones1.clear();
            loadTransiciones1();

            transiciones2.clear();
            loadTransiciones2();

            //spriteManager.blueEnemies.clear();
            //loadEnemyBlue();
            loadEnemies();
        }
        else if(currentLevel == 2)
        {
            collisionLayer = map.getLayers().get("objects");
            transicionesLayer1 = map.getLayers().get("transiciones1");
            transicionesLayer2 = map.getLayers().get("transiciones2");

            blueEnemyLayer = map.getLayers().get("blue");
            yellowEnemyLayer = map.getLayers().get("yellow");

            mapRenderer = new OrthogonalTiledMapRenderer(map);

            transiciones1.clear();
            loadTransiciones1();

            transiciones2.clear();
            loadTransiciones2();

            //spriteManager.blueEnemies.clear();
            //loadEnemyBlue();
            //loadEnemyYellow();
            loadEnemies();
        }
        else if(currentLevel == 3)
        {
            collisionLayer = map.getLayers().get("objects");
            transicionesLayer1 = map.getLayers().get("transiciones1");
            transicionesLayer2 = map.getLayers().get("transiciones2");
            transicionesLayer3 = map.getLayers().get("transiciones3");
            endGameLayer = map.getLayers().get("final");

            blueEnemyLayer = map.getLayers().get("blue");
            yellowEnemyLayer = map.getLayers().get("yellow");

            mapRenderer = new OrthogonalTiledMapRenderer(map);

            transiciones1.clear();
            loadTransiciones1();

            transiciones2.clear();
            loadTransiciones2();

            transiciones3.clear();
            loadTransiciones3();

            loadEndGame();

            //spriteManager.listaEnemigos.clear();
            //loadEnemyBlue();
            //loadEnemyYellow();
            loadEnemies();
        }
        else if(currentLevel == 4)
        {
            collisionLayer = map.getLayers().get("objects");
            transicionesLayer1 = map.getLayers().get("transiciones1");
            transicionesLayer2 = map.getLayers().get("transiciones2");
            transicionesLayer3 = map.getLayers().get("transiciones3");
            endGameLayer = map.getLayers().get("final");

            redEnemyLayer = map.getLayers().get("red");

            mapRenderer = new OrthogonalTiledMapRenderer(map);

            transiciones1.clear();
            loadTransiciones1();

            transiciones2.clear();
            loadTransiciones2();

            transiciones3.clear();
            loadTransiciones3();

            loadEndGame();

            //spriteManager.listaEnemigos.clear();
            //loadEnemyBlue();
            //loadEnemyYellow();
            loadEnemies();
        }

        terreno.clear();
        loadTerreno();
    }

    public void setLevel(int i)
    {
        currentLevel = i;
    }

    public void loadTransiciones1()
    {
        for (MapObject ro : transicionesLayer1.getObjects())
        {
            if (ro instanceof TextureMapObject)
            {
                continue;
            }

            if (ro instanceof RectangleMapObject)
            {
                transiciones1.add(((RectangleMapObject) ro).getRectangle());
            }
            else
            {
                continue;
            }
        }
    }

    public void loadTransiciones2()
    {
        for (MapObject ro : transicionesLayer2.getObjects())
        {
            if (ro instanceof TextureMapObject)
            {
                continue;
            }

            if (ro instanceof RectangleMapObject)
            {
                transiciones2.add(((RectangleMapObject) ro).getRectangle());
            }
            else
            {
                continue;
            }
        }
    }

    public void loadTransiciones3()
    {
        for (MapObject ro : transicionesLayer3.getObjects())
        {
            if (ro instanceof TextureMapObject)
            {
                continue;
            }

            if (ro instanceof RectangleMapObject)
            {
                transiciones3.add(((RectangleMapObject) ro).getRectangle());
            }
            else
            {
                continue;
            }
        }
    }

    public void loadEndGame()
    {
        for (MapObject ro : endGameLayer.getObjects())
        {
            if (ro instanceof TextureMapObject)
            {
                continue;
            }

            if (ro instanceof RectangleMapObject)
            {
                endGame.add(((RectangleMapObject) ro).getRectangle());
            }
            else
            {
                continue;
            }
        }
    }

    private void loadEnemyBlue()
    {
        /*
        for (MapObject ro : blueEnemyLayer.getObjects())
        {
            if (ro instanceof TextureMapObject)
            {
                continue;
            }

            if (ro instanceof RectangleMapObject)
            {
                Rectangle rectangle = ((RectangleMapObject)ro).getRectangle();
                BlueEnemy blueEnemy = new BlueEnemy(rectangle.x, rectangle.y, "enemy1", 0.1f);
                spriteManager.listaEnemigos.add(blueEnemy);
            }
        }
        */
    }

    private void loadEnemyYellow()
    {
        /*
        for (MapObject ro : yellowEnemyLayer.getObjects())
        {
            if (ro instanceof TextureMapObject)
            {
                continue;
            }

            if (ro instanceof RectangleMapObject)
            {
                Rectangle rectangle = ((RectangleMapObject)ro).getRectangle();
                YellowEnemy yellowEnemy = new YellowEnemy(rectangle.x, rectangle.y, "enemy2", 1, 5);
                spriteManager.listaEnemigos.add(yellowEnemy);
            }
        }
        */
    }

    private void loadTerreno()
    {
        for (MapObject ro : collisionLayer.getObjects())
        {
            if (ro instanceof TextureMapObject)
            {
                continue;
            }
            //Shape shape;

            if (ro instanceof RectangleMapObject)
            {
                terreno.add(((RectangleMapObject)ro).getRectangle());
            }
            else if (ro instanceof PolygonMapObject)
            {
                terreno2.add(((PolygonMapObject)ro).getPolygon());
            }
            else
            {
                continue;
            }
        }
    }

    public void restarCurrentLevel()
    {
        currentLevel = 0;
    }

    public void passNextLevel()
    {
        currentLevel++;
    }

    public void reduceLevel()
    {
        if(currentLevel != 0)
            currentLevel--;
    }

    public int getCurrentLevel()
    {
        return this.currentLevel;
    }


    public void loadEnemies()
    {
        Enemy enemy = null;
        int speed = 0;
        for (MapObject object : map.getLayers().get("blue").getObjects())
        {
            if (object instanceof TextureMapObject)
            {
                TextureMapObject rectangleObject = (TextureMapObject) object;
                if (rectangleObject.getProperties().containsKey("blue"))
                {
                    System.out.println("ENEMIGO");
                    float velocidad = Float.parseFloat((String)rectangleObject.getProperties().get("velocidad"));
                    enemy = new BlueEnemy(rectangleObject.getX(),
                            rectangleObject.getY(), "enemy1",velocidad);
                    spriteManager.listaEnemigos.add(enemy);
                }
            }
        }
        if(currentLevel > 1)
        {
            for (MapObject object : map.getLayers().get("yellow").getObjects())
            {
                if (object instanceof TextureMapObject)
                {
                    TextureMapObject rectangleObject = (TextureMapObject) object;
                    if (rectangleObject.getProperties().containsKey("yellow"))
                    {
                        System.out.println("ENEMIGO 2");
                        float velocidad = Float.parseFloat((String) rectangleObject.getProperties().get("velocidad"));
                        enemy = new YellowEnemy(rectangleObject.getX(), rectangleObject.getY(), "enemy2", (int)velocidad, 20);
                        spriteManager.listaEnemigos.add(enemy);
                    }
                }
            }
        }
        if(currentLevel >= 3)
            for (MapObject object : map.getLayers().get("red").getObjects())
            {
                if (object instanceof TextureMapObject)
                {
                    TextureMapObject rectangleObject = (TextureMapObject) object;
                    if (rectangleObject.getProperties().containsKey("red"))
                    {
                        System.out.println("ENEMIGO 3");
                        float velocidad = Float.parseFloat((String) rectangleObject.getProperties().get("velocidad"));
                        enemy = new RedEnemy(rectangleObject.getX(), rectangleObject.getY(), "enemy3", velocidad);
                        spriteManager.listaEnemigos.add(enemy);
                    }
                }
            }
    }


    public void loadItems()
    {

    }
}