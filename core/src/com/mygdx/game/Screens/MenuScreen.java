package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

public class MenuScreen implements Screen {

    private OrthographicCamera gamecam;
    private Viewport gameport;
    private MyGdxGame game;
    Texture img;
    Sprite sprite,highscoresprite,loadgamesprite,newgamesprite,stagesprite,exitsprite,bgsprite,coversprite,tanksprite;
    SpriteBatch batch;
    Integer pos = 1;
    PlayScreen playScreen;
    public static Sound menu,click,newgame;

    public  MenuScreen(MyGdxGame game){
        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(MyGdxGame.V_Width ,MyGdxGame.V_Height ,gamecam);
        gamecam.position.set(gameport.getWorldWidth(),gameport.getWorldHeight(),0   );
        batch = new SpriteBatch();

        menu = Gdx.audio.newSound(Gdx.files.internal("Sound/menu.mp3"));
        click = Gdx.audio.newSound(Gdx.files.internal("Sound/guli.mp3"));
        newgame = Gdx.audio.newSound((Gdx.files.internal("Sound/newgame.mp3")));

        long id = menu.loop();

        //here loadgamesprite means tutorial sprites

        highscoresprite = new Sprite(new Texture("Menu/HighScore.jpg"));
        loadgamesprite = new Sprite(new Texture("Menu/Tutorial.jpg"));
        newgamesprite = new Sprite(new Texture("Menu/NewGame.jpg"));
        exitsprite = new Sprite(new Texture("Menu/Exit.jpg"));
        sprite = new Sprite(new Texture("Menu/metalslug1.png"));
        bgsprite = new Sprite(new Texture("Menu/back.png"));
        coversprite = new Sprite(new Texture("Menu/cover.png"));
        tanksprite = new Sprite(new Texture("Menu/tank.png"));

        newgamesprite.setPosition(60,300);
        loadgamesprite.setPosition(60,250);
        highscoresprite.setPosition(60,200);
        exitsprite.setPosition(60,150);
        coversprite.setPosition(newgamesprite.getX()-5,newgamesprite.getY()-5);
        tanksprite.setPosition(-180,-140);
        sprite.setPosition(220,200);

        bgsprite.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        sprite.setScale(.7f);
        tanksprite.setScale(.3f);
    }

    @Override
    public void show() {

    }

    public void handleinput(){

       if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
           if(pos == 1){
               menu.stop();
               newgame.play();
               playScreen = new PlayScreen(game);
               game.setScreen(playScreen);
           }
           if(pos == 2){
               // will send tutorial screen
           }
           if(pos == 3){
               // will send highscore screen
           }
           if(pos == 4)
               Gdx.app.exit();
       }
       if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
           pos--;
           if(pos < 1 )
               pos = 4;
           click.play();
       }
       if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
           pos++;
           if(pos > 4)
               pos = 1;
           click.play();
       }

       if(pos == 1)
           coversprite.setPosition(newgamesprite.getX()-5,newgamesprite.getY()-5);
       if(pos == 2)
           coversprite.setPosition(loadgamesprite.getX()-5,loadgamesprite.getY()-5);
       if(pos == 3)
           coversprite.setPosition(highscoresprite.getX()-5,highscoresprite.getY()-5);
       if(pos == 4)
           coversprite.setPosition(exitsprite.getX()-5,exitsprite.getY()-5);

}

    @Override
    public void render(float delta) {
        handleinput();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //sprite.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.begin();
        bgsprite.draw(batch);
        sprite.draw(batch);
        highscoresprite.draw(batch);
        loadgamesprite.draw(batch);
        newgamesprite.draw(batch);
        exitsprite.draw(batch);
        coversprite.draw(batch);
        tanksprite.draw(batch);
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
        menu.dispose();
        click.dispose();
    }
}
