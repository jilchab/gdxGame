package com.gdxGame.GameActors;

import com.badlogic.gdx.physics.box2d.Body;


public abstract class GameActor extends DataActor {

	protected Body body;

	@Override
	public float getX() {
		return body.getPosition().x;
	}
	@Override
	public float getY() {
		return body.getPosition().y;
	}
}
