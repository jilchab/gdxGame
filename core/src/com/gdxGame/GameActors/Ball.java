package com.gdxGame.GameActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class Ball extends Entity{


	public Ball() {
		sprite = new Sprite(new Texture(Gdx.files.internal("circle.png")));
		setOrigin(sprite.getWidth()/2,sprite.getHeight()/2);
		setScale(0.5f);

	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		System.out.println("TouchDown "+pointer);
		return true;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		System.out.println("TouchUp");
		return true;
	}
}
