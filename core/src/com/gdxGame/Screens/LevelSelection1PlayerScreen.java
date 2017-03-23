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
		final TextButton bt_0 = new TextButton(" 0",gamename.bs);
		final TextButton bt_1 = new TextButton(" 1",gamename.bs);
		final TextButton bt_2 = new TextButton(" 2",gamename.bs);
		tButtons = new Table();
		tButtons.setFillParent(true);
		//tButtons.setDebug(true);
		tButtons.left().top();
		tButtons.add(bt_back).width(100).height(100).pad(50,50,20,0).top().left();
		tButtons.row();
		tButtons.add(bt_0).width(200).height(200).pad(50);
		tButtons.add(bt_1).width(200).height(200).pad(50);
		tButtons.add(bt_2).width(200).height(200).pad(50);
		stage.addActor(tButtons);


		bt_back.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				gamename.setScreen(new MainMenuScreen(gamename));
			}
		});
		bt_0.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y){
				gamename.setScreen(new GameScreen(gamename,0));
			}

		});
		bt_1.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				gamename.setScreen(new GameScreen(gamename,1));
			}
		});
		bt_2.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				gamename.setScreen(new GameScreen(gamename, 2));
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
