package com.gdxGame.utils;


import com.badlogic.gdx.physics.box2d.Body;

public class BodyUtils {

	public static boolean bodyIsRunner(Body body) {
		UserData userData = (UserData) body.getUserData();

		return userData != null && userData.getUserDataType() == UserDataType.RUNNER;
	}
	public static boolean bodyIsWall(Body body) {
		UserData userData = (UserData) body.getUserData();

		return userData != null && userData.getUserDataType() == UserDataType.WALL;
	}

	public static boolean bodyIsGround(Body body) {
		UserData userData = (UserData) body.getUserData();

		return userData != null && userData.getUserDataType() == UserDataType.GROUND;
	}
}
