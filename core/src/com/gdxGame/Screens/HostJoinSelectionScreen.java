package com.gdxGame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdxGame.*;

public class HostJoinSelectionScreen implements Screen {

	SpriteBatch batch;
	Skin skin;
	Table tButtons;
	Stage stage;
	GameName gamename;

	public HostJoinSelectionScreen(final GameName gamename) {
		this.gamename = gamename;

		gamename.bluetooth.turnBluetoothOn();

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		batch = new SpriteBatch();

		final Button bt_back = new Button(gamename.bsback);
		final TextButton bt_host = new TextButton("HOST",gamename.bs);
		final TextButton bt_join = new TextButton("JOIN",gamename.bs);
		float x100 = (100)*stage.getViewport().getScreenWidth()/1920;
		float y100 = (100)*stage.getViewport().getScreenHeight()/1080;
		float x600 = (600)*stage.getViewport().getScreenWidth()/1920;
		float y300 = (300)*stage.getViewport().getScreenHeight()/1080;
		float x50 = (50)*stage.getViewport().getScreenWidth()/1920;
		float y50 = (50)*stage.getViewport().getScreenHeight()/1080;
		tButtons = new Table();
		tButtons.setFillParent(true);
		//tButtons.setDebug(true);
		tButtons.top().left();
		tButtons.add(bt_back).width(x100).height(y100).pad(y50,x50,20,0).top().left();
		tButtons.row();
		tButtons.add(bt_host).width(x600).height(y300).pad(x100).expandX().center();
		tButtons.row();
		tButtons.add(bt_join).width(x600).height(y300).expandX().center();
		stage.addActor(tButtons);


		bt_back.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				gamename.setScreen(new MainMenuScreen(gamename));
			}
		});
		bt_join.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				gamename.setScreen(new JoinScreen(gamename));
			}
		});
		bt_host.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				gamename.setScreen(new HostScreen(gamename));
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
	public void resize(int width, int height) {}
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
