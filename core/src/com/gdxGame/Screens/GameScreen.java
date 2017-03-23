package com.gdxGame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxGame.GameName;
import com.gdxGame.GameStage;

public class GameScreen implements Screen {

	private GameStage stage;
	GameName gamename;

	public GameScreen(GameName gameName, int level) {
		this.gamename = gameName;
		this.stage = new GameStage(level);
		stage.setGame(gameName);
	}

	@Override
	public void render(float delta) {
		//Clear the screen
		Gdx.gl.glClearColor(1f,1f,1f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Update the stage
		stage.draw();
		stage.act(delta);

	}
	@Override
	public void show() {}
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
	@Override
	public void hide() {}
	@Override
	public void dispose() {

	}
}
