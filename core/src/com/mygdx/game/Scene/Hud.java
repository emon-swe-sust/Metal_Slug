package com.mygdx.game.Scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

import java.util.*;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    public Integer life;
    private float timeCount;
    private static Integer score;
    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label lifeLabel;
    private Label marioLabel;
    public Image img,img1,img2;
    public Texture texture,texture1,texture2;
    public TextureRegion textureRegion,textureRegion1,textureRegion2;

    public Hud(SpriteBatch sb){
        score=0;
        life=3;

        Viewport viewport =new FillViewport(MyGdxGame.V_Width,MyGdxGame.V_Height,new OrthographicCamera());
        stage=new Stage(viewport,sb);
        Table table= new Table();
        table.top();
        table.setFillParent(true);
        scoreLabel=new Label(String.format("%04d",score),new Label.LabelStyle(new BitmapFont(), Color.RED));
        levelLabel=new Label("Stage-1",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lifeLabel = new Label(String.format("%d",MyGdxGame.life),new Label.LabelStyle(new BitmapFont(),Color.GOLD));
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(lifeLabel).expandX();


        stage.addActor(table);
    }
    public  void update(int scr,int life){
    this.life = life;
       score = score + scr*13;
       scoreLabel.setText(String.format("%03d",score));
       lifeLabel.setText(String.format("%d",MyGdxGame.life));
    }
    public static void addScore(int value){
        scoreLabel.setText(String.format("%06d",score));
        score+=value;
    }

    @java.lang.Override
    public void dispose() {
        stage.dispose();
    }
}