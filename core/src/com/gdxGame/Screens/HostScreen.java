package com.gdxGame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdxGame.GameName;


public class HostScreen implements Screen {

	SpriteBatch batch;
	Skin skin;
	Table tButtons;
	Stage stage;
	GameName gamename;


	public HostScreen(final GameName gamename) {
		this.gamename = gamename;

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();

		gamename.bluetooth.enableBluetoothDiscovering(this);

		Label.LabelStyle ls = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("fonts/ebb_white.fnt")),Color.WHITE);
		final Button bt_back = new Button(gamename.bsback);
		Label text = new Label("Waiting player 2", ls);
		float baWidth = (100)*stage.getViewport().getScreenWidth()/1920;
		float baHeight = (100)*stage.getViewport().getScreenHeight()/1080;
		float pad = 50*stage.getViewport().getScreenHeight()/1080;
		tButtons = new Table();
		tButtons.setFillParent(true);
		//tButtons.setDebug(true);
		tButtons.top().left();
		tButtons.add(bt_back).width(baWidth).height(baHeight).pad(pad,pad,20,0).top().left();
		tButtons.row();
		tButtons.add(text).expandX().expandY().center();

		stage.addActor(tButtons);


		bt_back.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
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
