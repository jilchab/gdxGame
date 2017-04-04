package com.gdxGame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.gdxGame.Screens.MainMenuScreen;
import com.gdxGame.utils.Bluetooth;


public class GameName extends Game {

	public Skin skin;
	public TextButton.TextButtonStyle bs;
	public Button.ButtonStyle bsback;

	public final Bluetooth bluetooth;

	public GameName(Bluetooth bluetooth) {
		this.bluetooth = bluetooth;
	}

	public void create() {

		// Skin Setup
		skin = new Skin();
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("gdx-skins-master/buttons.txt"));
		skin.addRegions(atlas);

		bs = new TextButton.TextButtonStyle();
		bs.font = new BitmapFont(Gdx.files.internal("fonts/ebb.fnt"));
		bs.up = skin.getDrawable("select-press");
		bs.down = skin.newDrawable("select-press",0,1,0,1);
		bsback = new Button.ButtonStyle();
		bsback.up = skin.getDrawable("left");
		bsback.down = skin.newDrawable("left",1,1,1,0.5f);

		this.setScreen(new MainMenuScreen(this));
	}

}
