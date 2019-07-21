package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.*;
//import com.mygdx.game.Screens.HighScoreClass;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public static final int V_Height=205  ;
	public static final int V_Width=350;
	public static  final float PPM=100;
	public static int score = 0;
	public static int life = 3;
	public static int highscore;
	public static int wait = 0;
	public MenuScreen menuScreen;
	public GameOverScreen gameOverScreen;
	public SGameOverScreen sGameOverScreen;
	public About AboutScreen;
	public TutorialScreen tutorialScreem;

	public static final short DEFAULT_BIT=1;
	public static final short PLAYER_BIT = 4;
	public static final short BOSSS_BIT=8;
	public static final short ENEMYBULLET_BIT=16;//door bit / enemy bit
	public static final short GROUND_BIT=2;
	public static final short DESTROYED_BIT=32;
	public static final short BULLET_BIT = 64;
	public static final short ENEMY_BIT = 128;
	public static final short BOSS_BULLET_BIT = 256;

	@Override
	public void create () {
		batch = new SpriteBatch();
		menuScreen = new MenuScreen(this);

		gameOverScreen = new GameOverScreen(this);
		AboutScreen = new About(this);
		tutorialScreem = new TutorialScreen(this);
		sGameOverScreen = new SGameOverScreen(this);
		setScreen(menuScreen);
//		setScreen(highScoreClass);
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
