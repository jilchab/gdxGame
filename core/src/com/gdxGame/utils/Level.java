package com.gdxGame.utils;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;

public class Level {
	public Group actors;
	public CameraInfo camInfo;
	public World world;

	public Level( World world, Group actors, CameraInfo camInfo) {
		this.world = world;
		this.actors = actors;
		this.camInfo = camInfo;
	}
}