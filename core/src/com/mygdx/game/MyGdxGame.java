package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.GameOverScreen;
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Screens.PlayScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public static final int V_Height=205  ;
	public static final int V_Width=350;
	public static  final float PPM=100;
	public static int score = 0;
	public static int life = 3;
	public MenuScreen menuScreen;
	public GameOverScreen gameOverScreen;

	public static final short DEFAULT_BIT=1;
	public static final short PLAYER_BIT = 4;
	public static final short WARRIOR_BIT=8;
	public static final short ENEMYBULLET_BIT=16;//door bit / enemy bit
	public static final short GROUND_BIT=2;
	public static final short DESTROYED_BIT=32;
	public static final short BULLET_BIT = 64;
	public static final short ENEMY_BIT = 128;



	@Override
	public void create () {
		batch = new SpriteBatch();
		menuScreen = new MenuScreen(this);
		gameOverScreen = new GameOverScreen(this);
		setScreen(menuScreen);
	}

	@Override
	public void render () {

		super.render();

	}

	@Override
	public  void  dispose() {
		batch.dispose();
	}


}
