package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import java.io.*;
import java.util.*;

public class HighScoreClass implements Screen {
    private OrthographicCamera gamecam;
    private Viewport gameport;
    private MyGdxGame game;
    private Texture texture;
    private PlayScreen screen;
    private SpriteBatch batch;
    private Sprite over;
    private float time ;
    public String str;
    private BitmapFont font;

    public HighScoreClass (MyGdxGame game) throws IOException {
        MyGdxGame.highscore = 0;
        File file = new File("HighScore.txt");

        Scanner sc = new Scanner(file);
        String str = "";
        while(sc.hasNextLine()){
            int i = sc.nextInt();
            System.out.println(i);
            str = str+(char)i;
        }
        sc.close();
        int k = 1;
        for(int i = str.length()-1;i>=0;i--){
            MyGdxGame.highscore += str.charAt(i)*k;
            k*=10;
        }

        this.game = game;
        time = 0;
        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(MyGdxGame.V_Width ,MyGdxGame.V_Height ,gamecam);
        gamecam.position.set(gameport.getWorldWidth()/2.3f,gameport.getWorldHeight()/2.3f,0   );
        batch = new SpriteBatch();
        font =  new BitmapFont(Gdx.files.internal("Font/tashfe.fnt"));
        over = new Sprite(new Texture("extra/HighScore.png"));

        over.setScale(1.15f,2.6f);
        over.setPosition(35,110);
    }

    public static void update(int score){
        if(score > MyGdxGame.highscore)
            MyGdxGame.highscore = score;

    }

    @Override
    public void show() {

    }

    public void handleinput(){
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            game.setScreen(game.menuScreen);
    }

    @Override
    public void render(float delta) {
        handleinput();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(MyGdxGame.highscore);

        batch.begin();
        //over.draw(batch);
        font.getData().setScale(1.5f,1.5f);
        font.draw(batch,"High Score",250,400);

        font.draw(batch,String.format("%d",MyGdxGame.highscore),250,250);
        font.getData().setScale(1,1);
        font.draw(batch,"Press ESCAPE to get back Main Menu.",30,50);
        batch.end();
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
        font.dispose();
    }
}
