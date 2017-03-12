package com.gdxGame.GameActors.UserData;

import com.badlogic.gdx.math.Vector2;
import com.gdxGame.GameActors.Hero;

public class HeroUserData extends UserData {

	private Vector2 jumpingLinearImpulse;

	public HeroUserData() {
		super();
		jumpingLinearImpulse = Hero.JUMPING_LINEAR_IMPULSE;
		userDataType = UserDataType.RUNNER;
	}

	public Vector2 getJumpingLinearImpulse() {
		return jumpingLinearImpulse;
	}
}