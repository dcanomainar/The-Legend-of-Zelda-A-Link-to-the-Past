package com.dani.zelda.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dani.zelda.GameMain;
import com.dani.zelda.characters.*;
import com.badlogic.gdx.utils.Array;
import com.dani.zelda.characters.Character;
import com.dani.zelda.screens.Credits;
import com.dani.zelda.screens.EndScreen;
import com.dani.zelda.screens.GameScreen;
import com.dani.zelda.screens.MainMenuScreen;
import com.dani.zelda.util.Constants;
import static com.dani.zelda.util.Constants.*;

public class SpriteManager
{
    GameMain game;

    int sw;

    public OrthographicCamera camera;
    Batch batch;
    int i = 0;
    public Array<Rectangle> tiles;

    Zelda zelda;
    public Array<Enemy> blueEnemies;

    public int gamestatus;
    public static final int GAME_PAUSED = 0;
    public static final int GAME_RUNNING = 1;

    //TODO ARRAY enemigos
    //Array<Enemy> enemies;
    //TODO ???
    //Array<Explosion> explosions;

    private Pool<Rectangle> rectPool = new Pool<Rectangle>()
    {
        @Override
        protected Rectangle newObject()
        {
            return new Rectangle();
        }
    };
    public static Array<Enemy> blueEnemy = new Array<Enemy>();

    public SpriteManager(GameMain game, int x, int y)
    {
        this.game = game;

        gamestatus = GAME_RUNNING;

        camera = new OrthographicCamera();

        camera.setToOrtho(false, 10 * Constants.TILE_WIDTH, 10 * Constants.TILE_HEIGHT);

        camera.update();

        if(game.sw == 0)
        {
            game.levelManager = new LevelManager(this);

            game.sw = 1;
            zelda = new Zelda(100, 100);
        }

        game.levelManager.loadCurrentLevel();

        batch = game.levelManager.mapRenderer.getBatch();

        //zelda = new Zelda(15 * Constants.TILE_WIDTH, 10 * Constants.TILE_HEIGHT);
        if(game.levelManager.getCurrentLevel() == 0)
            zelda = new Zelda(x, y);
        else if(game.levelManager.getCurrentLevel() == 1)
            zelda = new Zelda(x, y);
        else if(game.levelManager.getCurrentLevel() == 2)
            zelda = new Zelda(x, y);
        else if(game.levelManager.getCurrentLevel() == 3)
            zelda = new Zelda(x, y);

        sw = 0;

        //TODO UNUSED
        tiles = new Array<Rectangle>();

        blueEnemies = new Array<Enemy>();

    }

    public void update(float dt)
    {
        camera.position.set(zelda.position.x, zelda.position.y, 0);

        handleInput();
        //TODO UNUSED
        checkCollisions();

        if (!zelda.isDead())
        {
            zelda.update(dt);

            for(Enemy e : blueEnemies)
            {
                e.update(dt);
                //TODO COMPROBAR COLISIONES ETC
                //TODO COMPROBAR MUERTE
            }
        }

        //TODO CARGA ENEMIGOS
    }

    private void handleInput()
    {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && gamestatus == GAME_RUNNING)
        {
            zelda.velocity.x = -Zelda.SPEED;
            zelda.state = Zelda.State.LEFT;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && gamestatus == GAME_RUNNING)
        {
            zelda.velocity.x = Zelda.SPEED;
            zelda.state = Zelda.State.RIGHT;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP) && gamestatus == GAME_RUNNING)
        {
            zelda.velocity.y = Zelda.SPEED;
            zelda.state = Zelda.State.UP;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && gamestatus == GAME_RUNNING)
        {
            zelda.velocity.y = -Zelda.SPEED;
            zelda.state = Zelda.State.DOWN;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.Z) && gamestatus == GAME_RUNNING)
        {
            zelda.state = Zelda.State.ATTACK;
        }
        /*
        else if(Gdx.input.isKeyPressed(Input.Keys.X))
        {
            zelda.state = Zelda.State.DEAD;
        }
        */
        else if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            if(gamestatus == GAME_PAUSED)
                resumeGame();
            if(gamestatus == GAME_RUNNING)
                pauseGame();
        }
        else
        {
            zelda.velocity.x = 0;
            zelda.velocity.y = 0;
            zelda.state = Zelda.State.STOP;
        }
    }

    public void pauseGame()
    {
        gamestatus = GAME_PAUSED;
    }

    public void resumeGame()
    {
        gamestatus = GAME_RUNNING;
    }

    public void render()
    {
        if (gamestatus == GAME_RUNNING)
        {
            camera.update();
            game.levelManager.mapRenderer.setView(camera);
            game.levelManager.mapRenderer.render();

            //TODO CARGAR ENEMIGOS

            batch.begin();

            if (!zelda.isDead())
            {
                zelda.render(batch);
                comprobarColisiones();
                combrobarColisiones2();

                if(game.levelManager.getCurrentLevel() == 0)
                {
                    comprobarTransiciones1();
                }
                else if(game.levelManager.getCurrentLevel() == 1)
                {
                    comprobarTransiciones1();
                    comprobarTransiciones2();

                    //TODO COMPROBAR CHOQUES CON ENEMIGOS
                }
                else if(game.levelManager.getCurrentLevel() == 2)
                {
                    comprobarTransiciones1();
                    comprobarTransiciones2();

                    //TODO COMPROBAR CHOQUES CON ENEMIGOS
                }
                else if(game.levelManager.getCurrentLevel() == 3)
                {
                    comprobarTransiciones1();
                    comprobarTransiciones2();

                    //TODO COMPROBAR CHOQUES CON ENEMIGOS
                }
                else if(game.levelManager.getCurrentLevel() == 4)
                {
                    comprobarTransiciones3();
                    comprobarEndGame();
                }

                //TODO CARGAR ENEMIGOS
                for(Enemy e : blueEnemies)
                {
                    e.render(batch);
                    //TODO COMPROBAR COLISIONES ETC
                    //TODO COMPROBAR MUERTE
                }

                //TODO COMPROBAR RESTO DE TRANSICCIONES
            }

            //TODO WHEN DIE, GO TO ANOTHER SCREEN
            //shader.setUniform2fv("resolution", resolution, 0, 2);
            //shader.setUniformf("radius", radius);

            batch.end();
        }
        else
        {
            Stage stage = new Stage();

            Table table = new Table(game.getSkin());
            table.setFillParent(true);
            table.center();

            //TODO LOAD AN IMAGE
            //Label title = new Label("")

            TextButton continueButton = new TextButton("CONTINUE", game.getSkin());
            //playButton.setColor(Color.BLUE);
            continueButton.addListener(new ClickListener() {
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    gamestatus = GAME_RUNNING;
                    //stage.clear();
                }
            });
            TextButton activateButton = new TextButton("ACTIVATE SOUND", game.getSkin());
            //playButton.setColor(Color.BLUE);
            continueButton.addListener(new ClickListener() {
                public void touchUp(InputEvent event, float x, float y, int pointer, int button)
                {
                    gamestatus = GAME_RUNNING;
                    if(!ResourceManager.getMusic().isPlaying())
                    {
                        ResourceManager.startMusic();
                    }
                    //stage.clear();
                }
            });
            TextButton desactivateButton = new TextButton("DESACTIVATE SOUND", game.getSkin());
            //playButton.setColor(Color.BLUE);
            continueButton.addListener(new ClickListener() {
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    gamestatus = GAME_RUNNING;

                    if(ResourceManager.getMusic().isPlaying())
                    {
                        ResourceManager.stopMusic();
                    }
                }
            });
            TextButton menu = new TextButton("MAIN MENU", game.getSkin());
            //menu.setColor(Color.BLUE);
            menu.addListener(new ClickListener()
            {
                public void touchUp(InputEvent event, float x, float y, int pointer, int button)
                {
                    ResourceManager.stopMusic();
                    game.dispose();
                    game.setScreen(new MainMenuScreen(game));
                }
            });
            TextButton exitButton = new TextButton("EXIT", game.getSkin());
            //exitButton.setColor(Color.BLUE);
            exitButton.addListener(new ClickListener() {
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    ResourceManager.stopTitleMusic();
                    game.dispose();
                    System.exit(0);
                }
            });

            table.row().height(50);
            table.add(continueButton.center()).center().width(300).pad(5f);
            table.row().height(50);
            table.add(activateButton.center()).center().width(300).pad(5f);
            table.row().height(50);
            table.add(desactivateButton.center()).center().width(300).pad(5f);
            table.row().height(50);
            table.add(menu).center().width(300).pad(5f);
            table.row().height(50);
            table.add(exitButton).center().width(300).pad(5f);
            stage.addActor(table);
            Gdx.input.setInputProcessor(stage);

            stage.act();


            stage.draw();
        }
    }

    /**
     * @Deprecated
     */
    private void checkCollisions()
    {
        Array<Rectangle> tiles = new Array<Rectangle>();

        // Recoge las celdas de la posición del jugador
        int startX = (int) (zelda.position.x + zelda.velocity.x);
        int endX = (int) (zelda.position.x + Constants.PLAYER_WIDTH + zelda.velocity.x);
        int startY = (int) (zelda.position.y + zelda.velocity.y);
        int endY = (int) (zelda.position.y + Constants.PLAYER_HEIGHT + zelda.velocity.y);
        //getCollisionTiles(startX, startY, endX, endY);

        // TODO Comprueba las colisiones con el jugador??
        for (Rectangle tile : tiles)
        {
            if (tile.overlaps(zelda.rect))
            {
                switch (zelda.state)
                {
                    case LEFT:
                        zelda.position.x = tile.getX();
                        break;
                    case RIGHT:
                        zelda.position.x = tile.getX() - Constants.PLAYER_WIDTH - zelda.velocity.x;
                        break;
                    case UP:
                        zelda.position.y = tile.getY() - Constants.PLAYER_HEIGHT - zelda.velocity.y;
                        break;
                    case DOWN:
                        zelda.position.y = tile.getY() + zelda.velocity.y + 1;
                        break;
                    default:
                }
            }
        }

        //TODO
    }

    public void comprobarTransiciones1()
    {
        Rectangle rectangle = new Rectangle(0,0,0,0);
        for (Rectangle r : LevelManager.transiciones1)
        {
            if (Intersector.intersectRectangles(zelda.rect, r, rectangle))
            {
                i++;
                LevelManager.terreno2.clear();
                LevelManager.terreno.clear();

                if(game.levelManager.getCurrentLevel() == 0)
                {
                    LevelManager.transiciones1.clear();
                    game.levelManager.passNextLevel();

                    ResourceManager.stopMusic();
                    ResourceManager.stopSound();

                    game.setScreen(new GameScreen(game, SECONDX, SECONDY));
                }
                else if(game.levelManager.getCurrentLevel() == 1)
                {
                    LevelManager.transiciones1.clear();
                    LevelManager.transiciones2.clear();
                    blueEnemies.clear();
                    game.levelManager.reduceLevel();

                    ResourceManager.stopMusic();
                    ResourceManager.stopSound();


                    game.setScreen(new GameScreen(game, FIRSTX2, FIRSTY2));
                }
                else if(game.levelManager.getCurrentLevel() == 2)
                {
                    LevelManager.transiciones1.clear();
                    LevelManager.transiciones2.clear();
                    blueEnemies.clear();
                    game.levelManager.reduceLevel();

                    ResourceManager.stopMusic();
                    ResourceManager.stopSound();

                    game.setScreen(new GameScreen(game, SECONDX2, SECONDY2));
                }
                else if(game.levelManager.getCurrentLevel() == 3)
                {
                    LevelManager.transiciones1.clear();
                    LevelManager.transiciones2.clear();
                    LevelManager.transiciones3.clear();
                    blueEnemies.clear();
                    game.levelManager.reduceLevel();

                    ResourceManager.stopMusic();
                    ResourceManager.stopSound();

                    game.setScreen(new GameScreen(game, THIRDX2, THIRDY2));
                }
            }
        }
    }

    public void comprobarTransiciones2()
    {
        Rectangle rectangle = new Rectangle(0,0,0,0);
        for (Rectangle r : LevelManager.transiciones2)
        {
            if (Intersector.intersectRectangles(zelda.rect, r, rectangle))
            {
                if(game.levelManager.getCurrentLevel() == 1)
                {
                    i++;
                    LevelManager.terreno2.clear();
                    LevelManager.terreno.clear();

                    LevelManager.transiciones1.clear();
                    LevelManager.transiciones2.clear();
                    blueEnemies.clear();
                    game.levelManager.passNextLevel();

                    ResourceManager.stopMusic();
                    ResourceManager.stopSound();

                    game.setScreen(new GameScreen(game, THIRDX, THIRDY));
                }
                else if(game.levelManager.getCurrentLevel() == 2)
                {
                    i++;
                    LevelManager.terreno2.clear();
                    LevelManager.terreno.clear();

                    LevelManager.transiciones1.clear();
                    LevelManager.transiciones2.clear();
                    blueEnemies.clear();
                    game.levelManager.passNextLevel();

                    ResourceManager.stopMusic();
                    ResourceManager.stopSound();

                    game.setScreen(new GameScreen(game, FOURTHX, FOURTHY));
                }
                else if(game.levelManager.getCurrentLevel() == 3)
                {
                    game.levelManager.passNextLevel();
                    System.out.println(game.levelManager.getCurrentLevel());
                    zelda.position.x = FIFTHX;
                    zelda.rect.x = FIFTHX;
                    zelda.position.y = FIFTHY;
                    zelda.rect.y = FIFTHY;
                }
            }
        }
    }

    public void comprobarTransiciones3()
    {
        Rectangle rectangle = new Rectangle(0,0,0,0);
        for (Rectangle r : LevelManager.transiciones3)
        {
            if (Intersector.intersectRectangles(zelda.rect, r, rectangle))
            {
                if(game.levelManager.getCurrentLevel() == 4)
                {
                    game.levelManager.reduceLevel();
                    zelda.position.x = FOURTHX2;
                    zelda.rect.x = FOURTHX2;
                    zelda.position.y = FOURTHY2;
                    zelda.rect.y = FOURTHY2;
                }
            }
        }
    }

    public void comprobarEndGame()
    {
        Rectangle rectangle = new Rectangle(0,0,0,0);
        for (Rectangle r : LevelManager.endGame)
        {
            if (Intersector.intersectRectangles(zelda.rect, r, rectangle))
            {
                if(game.levelManager.getCurrentLevel() == 4)
                {
                    LevelManager.terreno2.clear();
                    LevelManager.terreno.clear();

                    LevelManager.transiciones1.clear();
                    LevelManager.transiciones2.clear();
                    LevelManager.transiciones3.clear();
                    LevelManager.endGame.clear();

                    blueEnemies.clear();

                    ResourceManager.stopMusic();
                    ResourceManager.stopSound();

                    game.sw = 0;
                    game.levelManager.setLevel(0);

                    //TODO CHANGE IT IF NEEDED OR NOT
                    game.setScreen(new Credits(game));
                    //game.setScreen(new EndScreen(game));
                }
            }
        }
    }

    public void comprobarColisiones()
    {
        Rectangle rectangle = new Rectangle(0,0,0,0);
        for (Rectangle r : LevelManager.terreno)
        {
            if (Intersector.intersectRectangles(zelda.rect, r, rectangle))
                colisionSolida(zelda,r,rectangle);
        }
    }

    /**
     * @Deprecated
     */
    public void combrobarColisiones2()
    {
        Polygon polygon = new Polygon(new float[] { 0, 0, zelda.rect.width, 0, zelda.rect.width, zelda.rect.height, 0, zelda.rect.height });
        for(Polygon p : LevelManager.terreno2)
        {
            polygon.setPosition(zelda.rect.x, zelda.rect.y);
            if(Intersector.overlapConvexPolygons(polygon, p))
            {
                colisionSolida2(zelda, p, polygon);
            }
        }
    }

    private void colisionSolida(Character g1, Rectangle r2, Rectangle intersection)
    {
        if(intersection.y > g1.position.y)
        {
            if ((g1.position.y + g1.rect.height) - r2.y < 7f)
            {
                if (g1.velocity.y > 0)
                {
                    if (g1.position.x - (r2.x + r2.width) == -1f)
                        return;
                    else if ((g1.position.x + g1.rect.width) - r2.x == 1f)
                        return;
                    g1.position.y += -intersection.height;
                }
            }
        }
        if(intersection.y + intersection.height < g1.position.y + g1.rect.height)
        {
            if (((r2.y + r2.height) - g1.position.y < 7f))
            {
                if (g1.position.x - (r2.x + r2.width) == -1f)
                    return;
                else if ((g1.position.x + g1.rect.width) - r2.x == 1f)
                    return;
                g1.position.y += intersection.height - 1f;
            }
        }
        if(intersection.x > g1.position.x)
        {
            if ((g1.position.x + g1.rect.width) - r2.x < 7f)
            {
                if (g1.position.y - (r2.y + r2.height) == -1f)
                    return;
                g1.position.x += -intersection.width + 1f;
            }
        }
        if(intersection.x + intersection.width < g1.position.x + g1.rect.width)
        {
            if ((r2.x + r2.width) - g1.position.x < 7f)
            {
                if (g1.position.y - (r2.y + r2.height) == -1f)
                    return;
                g1.position.x += intersection.width - 1f;
            }
        }
    }

    //TODO IMPERFECTA TODAVIA
    private void colisionSolida2(Character g1, Polygon p2, Polygon intersection)
    {
        if(intersection.getOriginY() > g1.position.y)
        {
            if ((g1.position.y + g1.rect.height) - p2.getOriginY() < 7f)
            {
                if (g1.velocity.y > 0)
                {
                    if (g1.position.x - (p2.getOriginX() + p2.getBoundingRectangle().width) == -1f)
                        return;
                    else if ((g1.position.x + g1.rect.width) - p2.getOriginX() == 1f)
                        return;
                    g1.position.y += -intersection.getBoundingRectangle().height;
                }
            }
        }
        if(intersection.getOriginY() + intersection.getBoundingRectangle().height < g1.position.y + g1.rect.height)
        {
            if (((p2.getOriginY() + p2.getBoundingRectangle().height) - g1.position.y < 7f))
            {
                if (g1.position.x - (p2.getOriginX() + p2.getBoundingRectangle().width) == -1f)
                    return;
                else if ((g1.position.x + g1.rect.width) - p2.getOriginX() == 1f)
                    return;
                g1.position.y += intersection.getBoundingRectangle().height - 1f;
            }
        }
        if(intersection.getX() > g1.position.x)
        {
            if ((g1.position.x + g1.rect.width) - p2.getOriginX() < 7f)
            {
                if (g1.position.y - (p2.getOriginY() + p2.getBoundingRectangle().height) == -1f)
                    return;
                g1.position.x += -intersection.getBoundingRectangle().width + 1f;
            }
        }
        if(intersection.getX() + intersection.getBoundingRectangle().width < g1.position.x + g1.rect.width)
        {
            if ((p2.getOriginX() + p2.getBoundingRectangle().width) - g1.position.x < 7f)
            {
                if (g1.position.y - (p2.getOriginY() + p2.getBoundingRectangle().height) == -1f)
                    return;
                g1.position.x += intersection.getBoundingRectangle().width - 1f;
            }
        }
    }

    public void resize(int width, int height)
    {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.update();
    }
}