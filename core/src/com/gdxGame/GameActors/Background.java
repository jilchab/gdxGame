package com.gdxGame.GameActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor {

	Texture texture;
	TextureRegion textureRegion;
	public Sprite sprite;

	public Background(String path) {
		texture = new Texture(Gdx.files.internal(path));
		textureRegion = new TextureRegion(texture);
		sprite = new Sprite(textureRegion);
		sprite.setPosition(0,0);
		sprite.setSize(80,45);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(new Texture(Gdx.files.internal("background.png")),-50,-50,100,100);

	}
}
