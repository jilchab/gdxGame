package com.gdxGame.utils;


import com.badlogic.gdx.physics.box2d.Body;
import com.gdxGame.GameActors.UserData.UserDataType;

public class BodyUtils {

	public static boolean bodyIsRunner(Body body) {
		com.gdxGame.GameActors.UserData.UserData userData = (com.gdxGame.GameActors.UserData.UserData) body.getUserData();
		return userData != null && userData.getUserDataType() == com.gdxGame.GameActors.UserData.UserDataType.RUNNER;
	}
	public static boolean bodyIsWall(Body body) {
		com.gdxGame.GameActors.UserData.UserData userData = (com.gdxGame.GameActors.UserData.UserData) body.getUserData();
		return userData != null && userData.getUserDataType() == com.gdxGame.GameActors.UserData.UserDataType.WALL;
	}
	public static boolean bodyIsGround(Body body) {
		com.gdxGame.GameActors.UserData.UserData userData = (com.gdxGame.GameActors.UserData.UserData) body.getUserData();
		return userData != null && userData.getUserDataType() == com.gdxGame.GameActors.UserData.UserDataType.GROUND;
	}
	public static boolean bodyIsPicks(Body body) {
		com.gdxGame.GameActors.UserData.UserData userData = (com.gdxGame.GameActors.UserData.UserData) body.getUserData();
		return userData != null && userData.getUserDataType() == com.gdxGame.GameActors.UserData.UserDataType.PICKS;
	}
	public static boolean bodyIsMovingBody(Body body) {
		com.gdxGame.GameActors.UserData.UserData userData = (com.gdxGame.GameActors.UserData.UserData) body.getUserData();
		return userData != null && userData.getUserDataType() == UserDataType.MOVINGBOX;
	}
}
