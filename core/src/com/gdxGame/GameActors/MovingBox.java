package com.gdxGame.GameActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gdxGame.GameActors.UserData.GroundUserData;
import com.gdxGame.GameActors.UserData.MovingBoxUserData;
import com.gdxGame.GameActors.UserData.UserData;


//test git
public class MovingBox extends DataActor{

	public Body bUp,body;
	World world;
	static Texture texture = new Texture(Gdx.files.internal("bluebox.png"));

	Vector2 pos1,pos2;
	float speed;
	float time = 0;



	public MovingBox(World world,float left, float bottom, float width,float height,
	                 float left2, float bottom2, float speed) {
		this.world=world;

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
		bUp.setUserData(new GroundUserData());

		body = world.createBody(bodyDef);
		body.createFixture(shape,0f);
		body.setUserData(new com.gdxGame.GameActors.UserData.WallUserData());

		userData = (UserData) bUp.getUserData();
		hShape.dispose();
		shape.dispose();

		setPosition(x,y);
		setSize(width,height);
		pos1 = new Vector2(x,y);
		pos2 = new Vector2(left2 + width/2f,bottom2 + height/2f);
		this.speed = speed;


	}

	@Override
	public void act(float delta) {
		super.act(delta);
		time += delta;
		Vector2 pos = pos1.cpy();
		if (time <= speed) {
			pos.lerp(pos2, time / speed);
		} else {
			time = 0;
			Vector2 pos_swap = pos2.cpy();
			pos2 = pos1.cpy();
			pos1 = pos_swap;
			pos = pos1.cpy();
		}
		//System.out.println("pos : "+pos+"   pos1 : "+pos1+"   pos2 : "+pos2);
		body.setTransform(pos,0);
		pos.y+=getHeight()/2;
		bUp.setTransform(pos,0);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch,parentAlpha);
		batch.draw(texture,body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2,getWidth(),getHeight());
	}

	@Override
	public MovingBoxUserData getUserData() {
		return (MovingBoxUserData) userData;
	}


}
