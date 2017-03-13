package com.gdxGame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdxGame.*;

public class MainMenuScreen implements Screen {

	SpriteBatch batch;
	Skin skin;
	Table tButtons;
	Stage stage;
	GameName gamename;

	public MainMenuScreen(final GameName gamename) {
		this.gamename = gamename;

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		batch = new SpriteBatch();
		skin = new Skin();
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("gdx-skins-master/buttons.txt"));
		skin.addRegions(atlas);

		TextButton.TextButtonStyle bs = new TextButton.TextButtonStyle();
		bs.font = new BitmapFont();
		bs.up = skin.getDrawable("select-press");


		final TextButton bt_0 = new TextButton("LEVEL 0",bs);
		final TextButton bt_1 = new TextButton("LEVEL 1",bs);
		tButtons = new Table();
		tButtons.setFillParent(true);
		//ztButtons.setDebug(true);
		tButtons.left().top();
		tButtons.add(bt_0).width(200).height(200).pad(50);
		tButtons.add(bt_1).width(200).height(200).pad(50);
		stage.addActor(tButtons);



		bt_0.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				gamename.setScreen(new GameScreen(gamename,"level_0"));
				return true;
			}
		});
		bt_1.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				gamename.setScreen(new GameScreen(gamename,"level_1"));
				System.out.println("play");
				return true;
			}
		});

	}

	public void render(float delta) {
		//Clear the screen
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Draw
		stage.draw();
		stage.act(delta);
	}
	@Override
	public void show() {}
	@Override
	public void resize(int width, int height) {}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
	@Override
	public void hide() {}
	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}

}
