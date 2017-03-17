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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdxGame.GameName;


public class JoinScreen implements Screen {

	SpriteBatch batch;
	Skin skin;
	Table tButtons;
	Stage stage;
	GameName gamename;


	public JoinScreen(final GameName gamename) {
		this.gamename = gamename;

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();

		gamename.bluetooth.startDiscoverBluetooth();

		Label.LabelStyle ls = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("fonts/ebb_white.fnt")),Color.WHITE);
		final Button bt_back = new Button(gamename.bsback);
		Label text = new Label("Searching...", ls);
		tButtons = new Table();
		tButtons.setFillParent(true);
		//tButtons.setDebug(true);
		tButtons.top().left();
		tButtons.add(bt_back).width(100).height(100).pad(50,50,20,0).top().left();
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
		batch.begin();

		batch.end();

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
