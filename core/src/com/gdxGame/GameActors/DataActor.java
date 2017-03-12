package com.gdxGame.GameActors;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gdxGame.GameActors.UserData.UserData;

public abstract class DataActor extends Actor {
	protected UserData userData;

	public abstract UserData getUserData();
}
