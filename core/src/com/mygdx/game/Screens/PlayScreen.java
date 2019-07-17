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
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scene.Hud;
import com.mygdx.game.Sprites.Man;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Tools.WorldCreator;

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
        //player = new Man(world);
        player = new Player(world);
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
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (player.b2body.getLinearVelocity().x <= 2))
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && (player.b2body.getLinearVelocity().x >= -2))
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    }

    public void update(float dt) {

        handleInput(dt);
        player.update(dt);

        if(player.b2body.getPosition().x < 1.999f)
            gamecam.position.x=1.998f;
        else if(player.b2body.getPosition().x > 38.64f)
            gamecam.position.x = 38.64f;
        else
            gamecam.position.x = player.b2body.getPosition().x;


        if(player.b2body.getPosition().y < 0.432f)
            gamecam.position.y = 1.039f;
        else if(player.b2body.getPosition().y > .31f && player.b2body.getPosition().x < 33f)
            gamecam.position.y = 1.031f;
        else if(player.b2body.getPosition().x > 33 && player.b2body.getPosition().x < 35.69f) {
            gamecam.position.y = player.b2body.getPosition().y + .5f;
            if (gamecam.position.y < 1.031f)
                gamecam.position.y = 1.031f;
        }

        world.step(1/60f,6,2);

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Vector2 vel = player.b2body.getLinearVelocity();
        if(vel.y > 0){

        }

        renderer.render();

        b2dr.render(world,gamecam.combined);

        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

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