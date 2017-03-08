package com.gdxGame.GameActors;


import java.util.ArrayList;
import java.util.List;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


abstract public class Entity implements InputProcessor {


    protected Sprite sprite;


    public Entity() {
    }

    public void draw(Batch batch) {
	    sprite.draw(batch);
    }

	public void setColor(float r, float g, float b, float a) {
		sprite.setColor(r,g,b,a);
	}
	public void setOrigin(float x,float y) {
		sprite.setOrigin(x,y);
	}
	public void setScale(float scale) {
		sprite.setScale(scale);
	}



	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {return true;}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {return true;}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
