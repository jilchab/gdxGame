package com.gdxGame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdxGame.*;

public class LevelSelection1PlayerScreen implements Screen {

	SpriteBatch batch;
	Skin skin;
	Table tButtons;
	Stage stage;
	GameName gamename;

	public LevelSelection1PlayerScreen(final GameName gamename) {
		this.gamename = gamename;

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		batch = new SpriteBatch();


		final Button bt_back = new Button(gamename.bsback);
		final TextButton bt_1 = new TextButton(" 1",gamename.bs);
		final TextButton bt_2 = new TextButton(" 2",gamename.bs);
		final TextButton bt_3 = new TextButton(" 3",gamename.bs);
		final TextButton bt_4 = new TextButton(" 4",gamename.bs);

		float baWidth = (100)*stage.getViewport().getScreenWidth()/1920;
		float baHeight = (100)*stage.getViewport().getScreenHeight()/1080;
		float btWidth = (200)*stage.getViewport().getScreenWidth()/1920;
		float btHeight = (200)*stage.getViewport().getScreenHeight()/1080;
		float pad = 50*stage.getViewport().getScreenHeight()/1080;
		tButtons = new Table();
		tButtons.setFillParent(true);
		//tButtons.setDebug(true);
		tButtons.left().top();
		tButtons.add(bt_back).width(baWidth).height(baHeight).pad(pad,pad,20,0).top().left();
		tButtons.row();
		tButtons.add(bt_1).width(btWidth).height(btHeight).pad(pad);
		tButtons.add(bt_2).width(btWidth).height(btHeight).pad(pad);
		tButtons.add(bt_3).width(btWidth).height(btHeight).pad(pad);
		tButtons.add(bt_4).width(btWidth).height(btHeight).pad(pad);
		stage.addActor(tButtons);


		bt_back.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				gamename.setScreen(new MainMenuScreen(gamename));
			}
		});
		bt_1.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y){
				gamename.setScreen(new GameScreen(gamename,1));
			}

		});
		bt_2.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				gamename.setScreen(new GameScreen(gamename,2));
			}
		});
		bt_3.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				gamename.setScreen(new GameScreen(gamename, 3));
			}
		});
		bt_4.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				gamename.setScreen(new GameScreen(gamename, 4));
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
