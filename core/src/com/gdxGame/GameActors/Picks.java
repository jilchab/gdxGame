package com.gdxGame.GameActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gdxGame.utils.UserData;


public class Picks extends GameActor{

	private static TextureRegion textureRegion= new TextureRegion(new Texture(Gdx.files.internal("pick.png")));
	public static final float DEFAULT_WIDTH = 4;
	public static final float DEFAULT_HEIGHT = 5;

	private int nb_picks;

	public Picks(World world, float x, float y, float width, float height) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(new Vector2(x, y));
		body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/2,height/2);
		body.createFixture(shape, 1);
		body.resetMassData();
		body.setUserData(new PicksUserData());
		userData = (UserData) body.getUserData();
		shape.dispose();

		setPosition(x,y);
		setSize(width,height);

		nb_picks = 1;

	}
	public Picks(World world,float x,float y,int nb) {
		this(world,x,y,nb*DEFAULT_WIDTH,DEFAULT_HEIGHT);

		nb_picks = nb;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch,parentAlpha);
		float x;
		if(nb_picks%2==1) x = getX() - ((float)(nb_picks-1)/2) - DEFAULT_WIDTH/2;
		else x = getX() - ((float)(nb_picks)/2)*DEFAULT_WIDTH;
		for(int i = 0;i<nb_picks;i++) {
			batch.draw(textureRegion,x+i*DEFAULT_WIDTH,body.getPosition().y-getHeight()/2,DEFAULT_WIDTH,DEFAULT_HEIGHT);
		}


	}

	@Override
	public PicksUserData getUserData() {
		return (PicksUserData) userData;
	}
}
