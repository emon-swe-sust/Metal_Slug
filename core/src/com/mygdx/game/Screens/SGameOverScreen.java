package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

public class SGameOverScreen implements Screen {


    private OrthographicCamera gamecam;
    private Viewport gameport;
    private MyGdxGame game;
    private Texture texture;
    private PlayScreen screen;
    private  SpriteBatch batch;
    private Sprite over;
    private float time ;
    private BitmapFont font;

    public SGameOverScreen(MyGdxGame game){
        this.game = game;
        time = 0;
        // MyGdxGame.life=3;
        // MyGdxGame.score = 0;

        update();

        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(MyGdxGame.V_Width ,MyGdxGame.V_Height ,gamecam);
        gamecam.position.set(gameport.getWorldWidth()/2.3f,gameport.getWorldHeight()/2.3f,0   );
        batch = new SpriteBatch();

        font =  new BitmapFont(Gdx.files.internal("Font/tashfe.fnt"));


        over = new Sprite(new Texture("extra/gameover.png"));
        over.setPosition(168,90);
        over.setScale(2.1f,3.2f);
    }

    public static void update(){
        MyGdxGame.life=3;
        if(MyGdxGame.score > MyGdxGame.highscore){
            MyGdxGame.highscore = MyGdxGame.score;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        time += delta;
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        over.draw(batch);
        font.getData().setScale(2f, 2f);
        font.draw(batch, "CONGRATULATION", 25, 270);
        batch.end();
        if(time >=3 ){
            long id = MenuScreen.menu.loop();
            game.setScreen(game.menuScreen);
        }
    }

    @Override
    public void resize(int width, int height) {

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
