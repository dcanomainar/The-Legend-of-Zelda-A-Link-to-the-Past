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

            spriteManager.blueEnemies.clear();
            loadEnemyBlue();
        }
        else if(currentLevel == 2)
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

            spriteManager.blueEnemies.clear();
            loadEnemyBlue();
        }
        else if(currentLevel == 3)
        {
            collisionLayer = map.getLayers().get("objects");
            transicionesLayer1 = map.getLayers().get("transiciones1");
            transicionesLayer2 = map.getLayers().get("transiciones2");
            transicionesLayer3 = map.getLayers().get("transiciones3");
            endGameLayer = map.getLayers().get("final");

            blueEnemyLayer = map.getLayers().get("blue");

            mapRenderer = new OrthogonalTiledMapRenderer(map);

            transiciones1.clear();
            loadTransiciones1();

            transiciones2.clear();
            loadTransiciones2();

            transiciones3.clear();
            loadTransiciones3();

            loadEndGame();

            spriteManager.blueEnemies.clear();
            loadEnemyBlue();
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
        for (MapObject ro : blueEnemyLayer.getObjects())
        {
            if (ro instanceof TextureMapObject)
            {
                continue;
            }

            if (ro instanceof RectangleMapObject)
            {
                Rectangle rectangle = ((RectangleMapObject)ro).getRectangle();
                BlueEnemy blueEnemy = new BlueEnemy(rectangle.x, rectangle.y, "blue", 0.1f);
                spriteManager.blueEnemies.add(blueEnemy);
            }
        }
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

    /*
    public void loadEnemies()
    {
        Enemy enemy = null;
        int speed = 0;
        // Carga los objetos "enemigo" del TiledMap
        for (MapObject object : map.getLayers().get("cosas").getObjects()) {
            if (object instanceof TextureMapObject) {
                TextureMapObject rectangleObject = (TextureMapObject) object;
                if (rectangleObject.getProperties().containsKey("bicho")) {
                    String enemyType = (String) rectangleObject.getProperties().get("bicho");
                    switch (enemyType) {
                        case "verde":
                            speed = Integer.parseInt((String) rectangleObject.getProperties().get("velocidad"));
                            enemy = new GreenEnemy(rectangleObject.getX(),
                                    rectangleObject.getY(), "green_bubble", speed);
                            break;
                        case "amarillo":
                            speed = Integer.parseInt((String) rectangleObject.getProperties().get("velocidad"));
                            int offset = Integer.parseInt((String) rectangleObject.getProperties().get("desplazamiento"));
                            enemy = new YellowEnemy(rectangleObject.getX(),
                                    rectangleObject.getY(), "yellow_bubble", speed, offset);
                        default:
                            break;
                    }
                    spriteManager.enemies.add(enemy);
                }
            }
        }
    }
    */

    public void loadItems()
    {

    }
}