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

public class PlayScreen implements Screen {
    private MyGdxGame game;
    private OrthographicCamera gamecam;
    private Viewport gameport;
    private Hud hud;
    private Man player;

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
        map = maploader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1/MyGdxGame.ppm);

        gamecam.position.set(gameport.getWorldWidth()/2.3f,gameport.getWorldHeight()/2.3f,0   );

        world = new World(new Vector2(0,-5f),true);
        b2dr = new Box2DDebugRenderer();

        player = new Man(world);

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/MyGdxGame.ppm,(rect.getY()+rect.getHeight()/2)/MyGdxGame.ppm);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2 / MyGdxGame.ppm,rect.getHeight()/2 / MyGdxGame.ppm);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }


    @Override
    public void show() {

    }

    public void handleInput(float dt) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0,3f), player.b2body.getWorldCenter() , true);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (player.b2body.getLinearVelocity().x <= 2))
            player.b2body.applyLinearImpulse(new Vector2( 0.1f,0) , player.b2body.getWorldCenter(),true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && (player.b2body.getLinearVelocity().x >= -2))
            player.b2body.applyLinearImpulse(new Vector2( -0.1f,0) , player.b2body.getWorldCenter(),true);

    }

    public void update(float dt) {
        handleInput(dt);

        world.step(1/60f,6,2);

        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.position.y = player.b2body.getPosition().y + .6f;

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world,gamecam.combined);

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

    }
}
