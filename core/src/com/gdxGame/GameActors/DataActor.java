package com.gdxGame.GameActors;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gdxGame.utils.UserData;

public abstract class DataActor extends Actor {
	protected UserData userData;

	public abstract UserData getUserData();
}
