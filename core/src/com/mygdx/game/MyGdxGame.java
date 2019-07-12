package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Screens.PlayScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public static final int v_width = 400;
	public static final int v_hight = 205;
	public static final float ppm = 100;

	public MenuScreen menuScreen;

	@Override
	public void create () {
		menuScreen = new MenuScreen(this);
		batch = new SpriteBatch();
		setScreen(menuScreen);
	}

	@Override
	public void render () {
		super.render();
	}
}
