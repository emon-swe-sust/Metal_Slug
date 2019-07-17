package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scene.Hud;
import com.mygdx.game.Sprites.Bullet;
import com.mygdx.game.Sprites.Man;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Tools.WorldCreator;

import java.util.ArrayList;

public class PlayScreen implements Screen {
    private MyGdxGame game;
    private OrthographicCamera gamecam;
    private Viewport gameport;
    private Hud hud;
    //private Man player;
    private Player player;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    ArrayList<Bullet> bullets;

    //box2d

    private World world;
    private Box2DDebugRenderer b2dr;

    public PlayScreen(MyGdxGame game)
    {
        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new StretchViewport(MyGdxGame.v_width / MyGdxGame.ppm,MyGdxGame.v_hight / MyGdxGame.ppm,gamecam);
        hud = new Hud(game.batch);

        maploader = new TmxMapLoader();
        map = maploader.load("Map/map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1/MyGdxGame.ppm);

        gamecam.position.set(gameport.getWorldWidth()/2.3f,gameport.getWorldHeight()/2.3f,0   );

        world = new World(new Vector2(0,-5f),true);
        b2dr = new Box2DDebugRenderer();

        new WorldCreator(world,map);
        bullets = new ArrayList<Bullet>();
        //player = new Man(world);
        player = new Player(world,this,bullets);
    }


    @Override
    public void show() {

    }

    public void handleInput(float dt) {

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            game.setScreen(game.menuScreen);
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.b2body.applyLinearImpulse(new Vector2(0, 2.5f), player.b2body.getWorldCenter(), true);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (player.b2body.getLinearVelocity().x <= 1))
            //player.b2body.setLinearVelocity(1f, 0f);
            player.b2body.applyLinearImpulse(new Vector2(0.2f, 0), player.b2body.getWorldCenter(), true);
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && (player.b2body.getLinearVelocity().x >= -1))
            player.b2body.setLinearVelocity(-1f, 0f);
//            player.b2body.applyLinearImpulse(new Vector2(-0.2f, 0), player.b2body.getWorldCenter(), true);
        }

        /*if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(game.menuScreen);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.b2body.applyLinearImpulse(new Vector2(0, 2f), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (player.b2body.getLinearVelocity().x <= 2)) {
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);

        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && (player.b2body.getLinearVelocity().x >= -2))
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && (player.b2body.getLinearVelocity().x >= -2))
            player.b2body.applyLinearImpulse(new Vector2( -0.1f,0) , player.b2body.getWorldCenter(),true);
        */


    public void update(float dt) {
        //System.out.println("player "+ player.b2body.getPosition().x + " " + player.b2body.getPosition().y);
        //System.out.printf("camera " + gamecam.position.x + " " + gamecam.position.y);

        // Fixed camera

        if(player.b2body.getPosition().x < 1.999f)
            gamecam.position.x = 1.998f;
        else if(player.b2body.getPosition().x > 38.64f)
            gamecam.position.x = 38.64f;
        else
            gamecam.position.x = player.b2body.getPosition().x ;
        //System.out.println(gamecam.position.x);


        if(player.b2body.getPosition().y < 0.432f)
            gamecam.position.y = 1.031f;
        else if(player.b2body.getPosition().y > .31f && player.b2body.getPosition().x < 33f)
            gamecam.position.y = 1.031f;
        else if(player.b2body.getPosition().x > 32.94f && player.b2body.getPosition().x < 34.5f) {
            gamecam.position.y = 1.031f;
            if(player.b2body.getPosition().x > 34.49f)
                gamecam.position.y = 1.231f;
        }
        if(player.b2body.getPosition().x > 34.5){
            gamecam.position.y = player.b2body.getPosition().y;
        }

        System.out.println(">>> ----- " + gamecam.position.y + " " + player.b2body.getPosition().x);


        handleInput(dt);

        world.step(1/60f,6,2);

        player.update(dt);

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //b2dr.render(world,gamecam.combined);
        //game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        if(player.b2body.getPosition().x > 35.31){
            player.setY(player.getY()-55);
        }
        player.draw(game.batch);
        //game.batch.draw(player.temp, player.getX(), player.getY(), player.getOriginX(), player.getOriginY(), player.getWidth(), player.getHeight(), player.getScaleX(), player.getScaleY(), player.getRotation());
        game.batch.end();

        for(Bullet bullet : bullets) {
//            bullet.setY(bullet.getY()+50);
//            bullet.setX((bullet.getX())*80+360f);
//            System.out.println(bullet.getX());
          //  if(bullet.getX() > 133.9f)
            bullet.update(delta);
            if(bullet.getX() > 3000 || bullet.getX() < 0 || bullet.getX() > player.b2body.getPosition().x+1000 || bullet.getX()  < player.b2body.getPosition().x -1000){
                bullet.settodestroy = true;
            }
            bullet.draw(game.batch);
        }
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }
}