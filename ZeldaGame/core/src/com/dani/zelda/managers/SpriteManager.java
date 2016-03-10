package com.dani.zelda.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dani.zelda.GameMain;
import com.dani.zelda.characters.*;
import com.badlogic.gdx.utils.Array;
import com.dani.zelda.characters.Character;
import com.dani.zelda.characters.item.Laser;
import com.dani.zelda.screens.*;
import com.dani.zelda.util.Constants;

import java.util.Random;

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

    public int gamestatus;
    public static final int GAME_PAUSED = 0;
    public static final int GAME_RUNNING = 1;
    public boolean giroYellow;
    public int sw4;
    public int sw5;
    public BitmapFont font;
    public int x1, x2;

    private Pool<Rectangle> rectPool = new Pool<Rectangle>()
    {
        @Override
        protected Rectangle newObject()
        {
            return new Rectangle();
        }
    };
    public Array<Enemy>listaEnemigos;
    public Array<Laser>listaLasers;

    public SpriteManager(GameMain game, int x, int y)
    {
        this.game = game;
		
		this.listaEnemigos = game.enemies;
        this.listaLasers = game.lasers;
        giroYellow = false;
        gamestatus = GAME_RUNNING;
        sw4 = 0;
        camera = new OrthographicCamera();
        sw5 = 0;
        camera.setToOrtho(false, 10 * Constants.TILE_WIDTH, 10 * Constants.TILE_HEIGHT);
        font = new BitmapFont();
        x1 = 400;
        x2 = 700;

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

        tiles = new Array<Rectangle>();

        //blueEnemies = new Array<Enemy>();

    }

    private void updateEnemigosAzules(float dt)
    {
        for (Enemy enemy: listaEnemigos)
        {
            if(enemy instanceof BlueEnemy)
                enemy.update(dt, "enemy1");
            else if(enemy instanceof YellowEnemy)
            {
                enemy.update(dt, "enemy2");
                enemy.fire();
                if (enemy.disparo)
                {
                    game.lasers.add(new Laser(enemy.position.x, enemy.position.y + 50, enemy.state));
                    enemy.disparo = false;
                }
            }
            else if(enemy instanceof  RedEnemy)
            {
                enemy.update(dt, "enemy3");
            }
        }
    }

    public void updateLasers(float dt)
    {
        for(Laser l : listaLasers)
        {
            l.update(dt);
            if(l.state == Character.State.RIGHT) {
                l.move(new Vector2(+dt, 0));
            }
            else{
                l.move(new Vector2(-dt, 0));
            }
        }
    }

    public void update(float dt)
    {
        camera.position.set(zelda.position.x, zelda.position.y, 0);

        handleInput();
        checkCollisions();

        if (!zelda.isDead())
        {
            zelda.update(dt, "zelda");
            if(game.levelManager.getCurrentLevel() == 1)
            {
                if(ConfigurationManager.isDifficultEnabled())
                    updateEnemigosAzules(dt*20);
                else
                    updateEnemigosAzules(dt);
                comprobarEnemigosAzules();
            }
            if(game.levelManager.getCurrentLevel() == 2)
            {
                if(ConfigurationManager.isDifficultEnabled())
                    updateEnemigosAzules(dt*2);
                else
                    updateEnemigosAzules(dt);
                comprobarEnemigosAzules();
                //TODO ACTIVAR EL UPDATE, SINO NO PINTARA LOS LASERS
                //updateLasers(dt);
            }
            if(game.levelManager.getCurrentLevel() == 3)
            {
                if(ConfigurationManager.isDifficultEnabled())
                    updateEnemigosAzules(dt*2);
                else
                    updateEnemigosAzules(dt);
                comprobarEnemigosAzules();
                //TODO ACTIVAR EL UPDATE, SINO NO PINTARA LOS LASERS
                //updateLasers(dt);
            }
            if(game.levelManager.getCurrentLevel() == 4)
            {
                if(ConfigurationManager.isDifficultEnabled())
                    updateEnemigosAzules(dt*2);
                else
                    updateEnemigosAzules(dt);
                comprobarEnemigosAzules();
                //TODO ACTIVAR EL UPDATE, SINO NO PINTARA LOS LASERS
                //updateLasers(dt);
            }
        }
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
        /*
        else if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            if(gamestatus == GAME_PAUSED)
                resumeGame();
            if(gamestatus == GAME_RUNNING)
                pauseGame();
        }
        */
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

    private void drawHud()
    {
        //camera.position.x - CAMERA_WIDTH / 2 + PLAYER_WIDTH * 4 + 10, CAMERA_HEIGHT - TILE_HEIGHT / 2
        font.draw(batch, " VIDAS " + zelda.lives,zelda.position.x -50,zelda.position.y +80);
        font.draw(batch, " NIVEL " + game.levelManager.getCurrentLevel(), zelda.position.x + 20, zelda.position.y + 80);
        font.setColor(Color.BLUE);

    }
    public void render()
    {
        if (gamestatus == GAME_RUNNING)
        {
            camera.update();
            game.levelManager.mapRenderer.setView(camera);
            game.levelManager.mapRenderer.render();

            batch.begin();


            if (!zelda.isDead())
            {
                zelda.render(batch);
                comprobarColisiones();
                combrobarColisiones2();

                for(Enemy e : listaEnemigos)
                {
                    if(e instanceof BlueEnemy)
                    {
                        if (camera.frustum.pointInFrustum(new Vector3(e.position.x, e.position.y, 0)))
                        {
                            if (e.position.x > zelda.position.x)
                            {
                                e.position.x--;
                                e.rect.x--;
                                e.state = Character.State.LEFT;

                                if(e.position.x - zelda.position.x < 10)
                                {
                                    if (e.position.y > zelda.position.y)
                                    {
                                        if(e.position.y - zelda.position.y > 10)
                                        {
                                            e.position.y--;
                                            e.rect.y--;
                                        }
                                    }
                                }
                                /*
                                else if(((e.position.x - zelda.position.x) == 10 && zelda.state == Character.State.ATTACK))
                                {
                                    ((BlueEnemy) e).position.x ++;
                                    ((BlueEnemy) e).rect.x++;
                                }
                                */
                            }
                            else if (e.position.x < zelda.position.x)
                            {
                                e.position.x++;
                                e.rect.x++;

                                if (e.position.y < zelda.position.y)
                                {
                                    e.position.y++;
                                    e.rect.y++;
                                }

                            }
                        }
                    }
                    else if(e instanceof YellowEnemy)
                    {
                        if(!giroYellow)
                        {
                            giroYellow = true;
                            Random n = new Random();
                            int t = n.nextInt(4);
                            if(t == 0)
                            {
                                ((YellowEnemy) e).state = Character.State.LEFT;
                            }
                            else if(t == 1)
                            {
                                ((YellowEnemy) e).state = Character.State.RIGHT;
                            }
                            else if(t == 2)
                            {
                                ((YellowEnemy) e).state = Character.State.UP;
                            }
                            else if(t == 3)
                            {
                                ((YellowEnemy) e).state = Character.State.DOWN;
                            }
                            Timer.schedule(new Timer.Task()
                            {
                                @Override
                                public void run()
                                {
                                    giroYellow = false;
                                }
                            }, 1);
                        }
                    }
                    //TODO APAÑAR
                    else if(e instanceof RedEnemy)
                    {
                        if (e.position.x < x2 && sw5 == 0)
                        {
                            ((RedEnemy) e).state = Character.State.RIGHT;
                            e.position.x ++;
                            if(e.position.x > x2)
                                sw5 = 1;
                        }
                        else if(e.position.x > x1 && sw5 == 1)
                        {
                            ((RedEnemy) e).state = Character.State.LEFT;
                            e.position.x--;
                            if(e.position.x < x1)
                                sw5 = 0;
                        }
                    }
                }

                if (game.levelManager.getCurrentLevel() == 0)
                {
                    comprobarTransiciones1();
                } else if (game.levelManager.getCurrentLevel() == 1)
                {
                    comprobarTransiciones1();
                    comprobarTransiciones2();

                    pintarEnemigosAzules();
                } else if (game.levelManager.getCurrentLevel() == 2)
                {
                    comprobarTransiciones1();
                    comprobarTransiciones2();

                    pintarEnemigosAzules();
                    //pintarLasers();
                } else if (game.levelManager.getCurrentLevel() == 3)
                {
                    comprobarTransiciones1();
                    comprobarTransiciones2();

                    pintarEnemigosAzules();
                } else if (game.levelManager.getCurrentLevel() == 4)
                {
                    comprobarTransiciones3();
                    comprobarEndGame();

                    pintarEnemigosAzules();
                }
                drawHud();
                batch.end();
            }
            else
            {
                Stage stage = new Stage();

                Table table = new Table(game.getSkin());
                table.setFillParent(true);
                table.center();

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
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        //gamestatus = GAME_RUNNING;
                        if (!ResourceManager.getMusic().isPlaying()) {
                            ResourceManager.startMusic();
                        }
                        //stage.clear();
                    }
                });
                TextButton desactivateButton = new TextButton("DESACTIVATE SOUND", game.getSkin());
                //playButton.setColor(Color.BLUE);
                continueButton.addListener(new ClickListener() {
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        //gamestatus = GAME_RUNNING;
                        if (ResourceManager.getMusic().isPlaying()) {
                            ResourceManager.stopMusic();
                        }
                    }
                });
                TextButton menu = new TextButton("MAIN MENU", game.getSkin());
                //menu.setColor(Color.BLUE);
                menu.addListener(new ClickListener() {
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
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
                table.add(continueButton).center().width(300).pad(5f);
                table.row().height(50);
                table.add(activateButton).center().width(300).pad(5f);
                table.row().height(50);
                table.add(desactivateButton).center().width(300).pad(5f);
                table.row().height(50);
                table.add(menu).center().width(300).pad(5f);
                table.row().height(50);
                table.add(exitButton).center().width(300).pad(5f);

                stage.addActor(table);

                stage.act();
                Gdx.input.setInputProcessor(stage);
                stage.draw();
            }
        }
    }

    /*
    /**
     * @Deprecated
     */
    private void checkCollisions()
    {
        Array<Rectangle> tiles = new Array<Rectangle>();

        // Recoge las celdas de la posici�n del jugador
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

    public void pintarLasers()
    {
        for(Laser laser : game.lasers)
        {
            laser.render(batch);
        }
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

                    if(ResourceManager.music != null)
                    {
                        ResourceManager.stopMusic();
                        ResourceManager.stopSound();
                    }

                    game.setScreen(new GameScreen(game, SECONDX, SECONDY));
                }
                else if(game.levelManager.getCurrentLevel() == 1)
                {
                    LevelManager.transiciones1.clear();
                    LevelManager.transiciones2.clear();
                    //listaEnemigos.clear();
                    game.levelManager.reduceLevel();

                    if(ResourceManager.music != null)
                    {
                        ResourceManager.stopMusic();
                        ResourceManager.stopSound();
                    }

                    game.reinstanciarEnemigos();

                    game.setScreen(new GameScreen(game, FIRSTX2, FIRSTY2));
                }
                else if(game.levelManager.getCurrentLevel() == 2)
                {
                    LevelManager.transiciones1.clear();
                    LevelManager.transiciones2.clear();
                    listaEnemigos.clear();
                    game.levelManager.reduceLevel();

                    if(ResourceManager.music != null)
                    {
                        ResourceManager.stopMusic();
                        ResourceManager.stopSound();
                    }

                    game.reinstanciarEnemigos();

                    game.setScreen(new GameScreen(game, SECONDX2, SECONDY2));
                }
                else if(game.levelManager.getCurrentLevel() == 3)
                {
                    LevelManager.transiciones1.clear();
                    LevelManager.transiciones2.clear();
                    LevelManager.transiciones3.clear();
                    listaEnemigos.clear();
                    game.levelManager.reduceLevel();

                    if(ResourceManager.music != null)
                    {
                        ResourceManager.stopMusic();
                        ResourceManager.stopSound();
                    }

                    game.reinstanciarEnemigos();

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
                    listaEnemigos.clear();
                    game.levelManager.passNextLevel();

                    if(ResourceManager.music != null)
                    {
                        ResourceManager.stopMusic();
                        ResourceManager.stopSound();
                    }

                    game.setScreen(new GameScreen(game, THIRDX, THIRDY));
                }
                else if(game.levelManager.getCurrentLevel() == 2)
                {
                    i++;
                    LevelManager.terreno2.clear();
                    LevelManager.terreno.clear();

                    LevelManager.transiciones1.clear();
                    LevelManager.transiciones2.clear();
                    listaEnemigos.clear();
                    game.levelManager.passNextLevel();

                    if(ResourceManager.music != null)
                    {
                        ResourceManager.stopMusic();
                        ResourceManager.stopSound();
                    }

                    game.setScreen(new GameScreen(game, FOURTHX, FOURTHY));
                }
                else if(game.levelManager.getCurrentLevel() == 3)
                {
                    game.levelManager.passNextLevel();
                    //listaEnemigos.clear();
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
                    //listaEnemigos.clear();
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

                    listaEnemigos.clear();

                    if(ResourceManager.music != null)
                    {
                        ResourceManager.stopMusic();
                        ResourceManager.stopSound();
                    }

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

    public void comprobarEnemigosAzules()
    {
        if(listaEnemigos.size == 0)
        {
            return;
        }
        for (Enemy enemy: listaEnemigos)
        {
            if(enemy.rect.overlaps(zelda.rect))
            {
                if(zelda.state != Character.State.ATTACK)
                {
                    if(!zelda.muerto)
                    {
                        zelda.lives--;
                        zelda.muerto = true;
                    }
                    Timer.schedule(new Timer.Task()
                    {
                        @Override
                        public void run()
                        {
                            zelda.muerto = false;
                        }
                    }, 4);
                }
                else
                    listaEnemigos.removeValue(enemy, true);
                System.out.print(zelda.lives);
                if(zelda.lives == 0)
                {
                    if (ResourceManager.music != null) {
                        ResourceManager.stopMusic();
                        ResourceManager.stopSound();
                    }
                    game.setScreen(new GameOverScreen(game));
                }
            }
        }
    }

    public void pintarEnemigosAzules()
    {
        for(Enemy enemy: listaEnemigos)
        {
            enemy.render(batch);
        }
    }
}