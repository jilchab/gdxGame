package com.gdxGame.GameActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gdxGame.GameActors.UserData.UserData;


//test git
public class Box extends DataActor{
	public Body bUp,body;
	World world;
	static Texture texture = new Texture(Gdx.files.internal("bluebox.png"));

	public Box(World world,float left,float bottom,float right,float top) {
		this.world=world;

		float width = right-left, height = top-bottom;
		float x = left + width/2f, y = bottom + height/2f;

		PolygonShape hShape = new PolygonShape();
		hShape.setAsBox(width/2, 0.05f);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/2, height/2);

		BodyDef bUpDef= new BodyDef();
		bUpDef.position.set(new Vector2(x, y+height/2));
		BodyDef bodyDef= new BodyDef();
		bodyDef.position.set(new Vector2(x, y));

		bUp = world.createBody(bUpDef);
		bUp.createFixture(hShape,0f);
		bUp.setUserData(new com.gdxGame.GameActors.UserData.GroundUserData());

		body = world.createBody(bodyDef);
		body.createFixture(shape,0f);
		body.setUserData(new com.gdxGame.GameActors.UserData.WallUserData());

		userData = (UserData) body.getUserData();
		hShape.dispose();
		shape.dispose();

		setPosition(x,y);
		setSize(width,height);

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch,parentAlpha);
		batch.draw(texture,body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2,getWidth(),getHeight());
	}

	@Override
	public com.gdxGame.GameActors.UserData.BoxUserData getUserData() {
		return (com.gdxGame.GameActors.UserData.BoxUserData) userData;
	}


}
