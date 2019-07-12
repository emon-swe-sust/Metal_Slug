package com.mygdx.game.Scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewport;

    private Integer worldtimer;
    private float timecount;
    private Integer score;

    Label countdownlabel;
    Label scorelabel;
    Label timelabel;
    Label levellabel;
    Label worldlebel;
    Label marioLabel;


    public Hud(SpriteBatch sb)
    {
        worldtimer = 300;
        timecount = 0;
        score = 0;

        viewport = new FitViewport(MyGdxGame.v_width,MyGdxGame.v_hight,new OrthographicCamera());

        stage = new Stage(viewport,sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownlabel = new Label(String.format("%03d", worldtimer) , new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scorelabel = new Label(String.format("%06dt", score) , new Label.LabelStyle(new BitmapFont(), Color.WHITE));;
        timelabel = new Label("Time" , new Label.LabelStyle(new BitmapFont(), Color.WHITE));;
        levellabel = new Label("1-1" , new Label.LabelStyle(new BitmapFont(), Color.WHITE));;
        worldlebel = new Label("World", new Label.LabelStyle(new BitmapFont(), Color.WHITE));;
        marioLabel = new Label("Mario" , new Label.LabelStyle(new BitmapFont(), Color.WHITE));;

        table.add(marioLabel).expandX().padTop(10);
        table.add(worldlebel).expandX().padTop(10);
        table.add(timelabel).expandX().padTop(10);
        table.row();
        table.add(scorelabel).expandX();
        table.add(levellabel).expandX();
        table.add(countdownlabel).expandX();

        stage.addActor(table);
    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}
