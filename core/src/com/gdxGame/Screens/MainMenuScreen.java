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
import com.gdxGame.GameName;

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


		final TextButton bt_1p = new TextButton("1 PLAYER",gamename.bs);
		final TextButton bt_2p = new TextButton("2 PLAYERS",gamename.bs);
		tButtons = new Table();
		tButtons.setFillParent(true);
		//tButtons.setDebug(true);
		tButtons.center();
		float btWidth = (600)*stage.getViewport().getScreenWidth()/1920;
		float btHeight = (300)*stage.getViewport().getScreenHeight()/1080;
		float pad = 100*stage.getViewport().getScreenHeight()/1080;
		System.out.println(btWidth+" "+btHeight);
		tButtons.add(bt_1p).width(btWidth).height(btHeight).pad(pad);
		tButtons.add(bt_2p).width(btWidth).height(btHeight).pad(pad);
		stage.addActor(tButtons);



		bt_1p.addListener(new ClickListener() {
			public void clicked(InputEvent event,float x, float y) {
				gamename.setScreen(new LevelSelection1PlayerScreen(gamename));
			}
		});
		bt_2p.addListener(new ClickListener() {
			public void clicked(InputEvent event,float x, float y) {
				gamename.setScreen(new HostJoinSelectionScreen(gamename));
			}
		});

	}

	public void render(float delta) {
		//Clear the screen
		Gdx.gl.glClearColor(49f/255f,157f/255f,224f/255f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Draw
		stage.draw();
		stage.act(delta);
	}
	@Override
	public void show() {}
	@Override
	public void resize(int width, int height) {



	}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
	@Override
	public void hide() {}
	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
		skin.dispose();
	}

}
