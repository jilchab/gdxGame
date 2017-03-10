package com.gdxGame;

import com.badlogic.gdx.Game;
import com.gdxGame.Screens.MainMenuScreen;


public class GameName extends Game {

	public void create() {

		this.setScreen(new MainMenuScreen(this));
	}

}
