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

public class TutorialScreen implements Screen {

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

    public TutorialScreen (MyGdxGame game) {

        this.game = game;

        gamecam = new OrthographicCamera();
        gameport = new FitViewport(MyGdxGame.V_Width ,MyGdxGame.V_Height ,gamecam);
        gamecam.position.set(gameport.getWorldWidth()/2.3f,gameport.getWorldHeight()/2.3f,0   );
        batch = new SpriteBatch();
        font =  new BitmapFont(Gdx.files.internal("Font/tashfe.fnt"));

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



        batch.begin();

        font.getData().setScale(1.5f,1.5f);
        font.draw(batch,"Metal Slug",250,450);

        font.getData().setScale(1f, 1f);
        font.draw(batch, "A 2d Shooting Game", 220, 400);

        font.getData().setScale(1f, 1);
        font.draw(batch, "  :-  Use Right and Left Button for move player Horizontally.", 0, 330);

        font.getData().setScale(1f, 1f);
        font.draw(batch, "  :-  Use Space button for Jump.", 0, 290);

        font.getData().setScale(1f, 1f);
        font.draw(batch, "  :-  Use Down button for get cover from bullets.", 0, 250);

        font.getData().setScale(1f, 1f);
        font.draw(batch, "  :-  Use 'Z' Button for Shoot to enemy.", 0, 210);

        font.getData().setScale(1f, 1f);
        font.draw(batch, "  :-  30 shot is needed to kill the final boss", 0, 170);

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

    }
}
