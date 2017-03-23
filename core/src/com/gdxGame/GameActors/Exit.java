package com.gdxGame.GameActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gdxGame.GameActors.UserData.UserData;



public class Exit extends Image {

	public final float DOOR_WIDTH = 4;
	public final float DOOR_HEIGHT = 8;

	public Exit(float x, float y) {
		super(new TextureRegion(new Texture(Gdx.files.internal("door.png"))));
		setPosition(x,y);
		setSize(DOOR_WIDTH,DOOR_HEIGHT);


	}
}
