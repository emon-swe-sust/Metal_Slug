package com.mygdx.game.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scene.Hud;
import com.mygdx.game.Sprites.Boss.FirstBoss;
import com.mygdx.game.Sprites.Enemies.Sniper;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.WorldContactListener;
import java.util.ArrayList;


public class PlayScreen implements Screen, InputProcessor {

    private long countDt=0;
    private float timeCount=0;
    private long Timer=1;

    private MyGdxGame game;

    //public Warrior player;
    public Player player;

    public FirstBoss boss1;

    public boolean bool;
    public boolean isHurt=false;
    public boolean enemyisHurt=false;
    public boolean cam = false;
    public boolean spacePressed = false;

    public Sniper sniper;
    public Sniper sniper1;
    public ArrayList<Sniper> snipers;

    public int k=1;

    //movement using inputprocessor
    boolean right, left, shoot, jump;


    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;


    public PlayScreen(MyGdxGame game)
    {
        this.game=game;

        gamecam= new OrthographicCamera();
        gamePort= new FitViewport(MyGdxGame.V_Width,MyGdxGame.V_Height,gamecam);
        hud=new Hud(game.batch);
        mapLoader =new TmxMapLoader();
        map=mapLoader.load("Map/map.tmx");
        renderer= new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        bool=false;
        world =new World (new Vector2(0,-150),true);
        b2dr= new Box2DDebugRenderer();
        new B2WorldCreator(world,map);
        //player= new Warrior(world,this);
        player = new Player(world, this,game);
        // world.setContactListener(new WorldContactListener());

        boss1 = new FirstBoss(world, this);

        sniper = new Sniper(world, this, 900, 130);
        sniper1 = new Sniper(world,this,1200,130);
        snipers = new ArrayList<Sniper>();

        snipers.add(new Sniper(world, this, 390, 130));
        snipers.add(new Sniper(world, this, 520, 130));
        snipers.add(new Sniper(world, this, 665, 130));
//        snipers.add(new Sniper(world, this, 750, 130));
//        snipers.add(new Sniper(world, this, 883, 130));
//        snipers.add(new Sniper(world, this, 900, 130));
        snipers.add(new Sniper(world, this, 1030, 130));
        snipers.add(new Sniper(world, this, 1190, 130));
        snipers.add(new Sniper(world, this, 1300, 130));
        snipers.add(new Sniper(world, this, 1480, 130));
        snipers.add(new Sniper(world, this, 1660, 130));
        snipers.add(new Sniper(world, this, 1842, 130));
        snipers.add(new Sniper(world, this, 1900, 130));
        snipers.add(new Sniper(world, this, 2150, 130));
        snipers.add(new Sniper(world, this, 2285, 130));
        snipers.add(new Sniper(world, this, 2500, 130));
        snipers.add(new Sniper(world, this, 2700, 130));
        snipers.add(new Sniper(world, this, 2885, 130));
        snipers.add(new Sniper(world, this, 3000, 130));
        snipers.add(new Sniper(world, this, 3250, 130));

        world.setContactListener(new WorldContactListener());

        //sniper = new Sniper(world, this, 600, 130);
        right = left = jump = shoot = false;

        Gdx.input.setInputProcessor(this);
    }

    @java.lang.Override
    public void show() {

    }
    public void handleInput(float dt) {
        timeCount+=dt;
        if(timeCount>=1){
            Timer++;
            timeCount=0;
        }
        countDt++;
        countDt=countDt%500;


        if(right && player.b2body.getLinearVelocity().x<=1000000)
            player.b2body.applyLinearImpulse(new Vector2(1000000f,0),player.b2body.getWorldCenter(),true);

        else if(left && player.b2body.getLinearVelocity().x>=-50)
            player.b2body.applyLinearImpulse(new Vector2(-50f,0),player.b2body.getWorldCenter(),true);

        if(player.b2body.getLinearVelocity().y == 0)
            spacePressed = false;

        //System.out.println(right + "ssds" + left);



        //System.out.println(right + "ssds" + left);
    }
    public void update(float dt){
        handleInput(dt);
        world.step(1/60f,6,2);

        //player
        player.update(dt, spacePressed);

        boss1.update(dt);

        //enemy


        int sccor = 0;
        for(int i=0;i<snipers.size();i++){
            snipers.get(i).update(dt,player.b2body.getPosition().x);
            if(snipers.get(i).isDead){
                snipers.remove(i);
                i--;
                sccor++;
            }
        }

        for(int i = 0;i < player.bullets.size() ; i++){
            player.bullets.get(i).update(dt);
        }

        for(int j=0;j<snipers.size();j++) {
            for (int i = 0; i < snipers.get(j).enemybullets.size(); i++) {
                snipers.get(j).enemybullets.get(i).update(dt);
            }
        }

        hud.update(sccor,MyGdxGame.life);

        gamecam.position.x= player.b2body.getPosition().x+80;
        if(player.b2body.getPosition().x > 3306 && player.b2body.getPosition().x < 3542) // 3306 3542)
            gamecam.position.y = player.b2body.getPosition().y+40;

        gamecam.update();
        renderer.setView(gamecam);
    }
    @java.lang.Override
    public void render(float delta) {
        update(delta);
        if(MyGdxGame.life == 0){
            game.setScreen(game.gameOverScreen);
        }

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        renderer.render();
        //render our box debug line
        b2dr.render(world,gamecam.combined);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();

        player.draw(game.batch);

        boss1.draw(game.batch);

        for (int i=0;i<snipers.size();i++){
            snipers.get(i).draw(game.batch);
        }
        for(int j=0;j<snipers.size();j++) {
            for (int i = 0; i < snipers.get(j).enemybullets.size(); i++)
                snipers.get(j).enemybullets.get(i).draw(game.batch);
        }

        //System.out.println(sniper.enemybullets.size());

        for(int i = 0 ; i < player.bullets.size() ; i++){
            player.bullets.get(i).draw(game.batch);
        }
        //System.out.println(" -- > " + player.bullets.size());
        game.batch.end();


        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();



    }

    @java.lang.Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    @java.lang.Override
    public void pause() {

    }

    @java.lang.Override
    public void resume() {

    }

    @java.lang.Override
    public void hide() {

    }

    @java.lang.Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }

    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Input.Keys.ESCAPE)
            game.setScreen(game.menuScreen);
        else if(keycode == Input.Keys.SPACE) {
            player.b2body.applyLinearImpulse(new Vector2(0, 153f), player.b2body.getWorldCenter(), true);
            spacePressed = true;
        }
        else if(keycode == Input.Keys.RIGHT)
            right = true;
        else if(keycode == Input.Keys.LEFT)
            left = true;

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {

        if(keycode == Input.Keys.RIGHT) {
            right = false;
            player.b2body.setLinearVelocity(0f, player.b2body.getLinearVelocity().y);
        }
        else if(keycode == Input.Keys.LEFT) {
            left = false;
            player.b2body.setLinearVelocity(0f, player.b2body.getLinearVelocity().y);
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}