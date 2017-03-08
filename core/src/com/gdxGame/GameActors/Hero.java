package com.gdxGame.GameActors;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gdxGame.utils.UserData;

public class Hero extends GameActor {

	private boolean isJumping,isRolling;
	private boolean rollLeft,rollRight;
	private TextureRegion textureRegion= new TextureRegion(new Texture(Gdx.files.internal("circle.png")));

	public static final float DEFAULT_X = 0;
	public static final float DEFAULT_Y = 0;
	public static final float RADIUS = 2f;
	public static final float DENSITY = 0.5f;
	public static final Vector2 JUMPING_LINEAR_IMPULSE = new Vector2(0, 400f);
	public static final float LINEAR_GROUND_VELOCITY = 20f;
	public static final float LINEAR_AIR_VELOCITY = 15f;
	public static final float ANGULAR_VELOCITY = 5f;
	public static final float ANGULAR_TORQUE_BRAKE = 1500f;



	public Hero(World world, float x, float y, float radius, float density) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(new Vector2(x, y));
		body = world.createBody(bodyDef);
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);
		body.createFixture(shape, density);
		body.resetMassData();
		body.setUserData(new HeroUserData());
		userData = (UserData) body.getUserData();
		shape.dispose();

		setPosition(x,y);
		setSize(radius*2,radius*2);

	}
	public Hero(World world) {
		this(world, DEFAULT_X, DEFAULT_Y,RADIUS,DENSITY);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if(!isJumping && !isRolling) {
			if(body.getLinearVelocity().x > 0) body.applyTorque(ANGULAR_TORQUE_BRAKE,true);
			else if(body.getLinearVelocity().x < 0) body.applyTorque(-ANGULAR_TORQUE_BRAKE,true);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch,parentAlpha);
		batch.draw(textureRegion,body.getPosition().x-getWidth()/2,body.getPosition().y-getHeight()/2,getWidth(),getHeight());
	}

	public void jump() {
		if (!isJumping) {
			isJumping = true;
			body.applyLinearImpulse(getUserData().getJumpingLinearImpulse(), body.getWorldCenter(), true);
		}
	}
	public void landed() {
		isJumping = false;
	}

	public void roll(String direction) {
		if(direction.equals("right")) {
			rollRight = true;
			if(!isJumping) body.setLinearVelocity(LINEAR_GROUND_VELOCITY,body.getLinearVelocity().y);
			else body.setLinearVelocity(LINEAR_AIR_VELOCITY,body.getLinearVelocity().y);
			body.setAngularVelocity(-ANGULAR_VELOCITY);
			isRolling = true;
		} else if(direction.equals("left")) {
			rollLeft = true;
			if(!isJumping) body.setLinearVelocity(-LINEAR_GROUND_VELOCITY,body.getLinearVelocity().y);
			else body.setLinearVelocity(-LINEAR_AIR_VELOCITY,body.getLinearVelocity().y);
			body.setAngularVelocity(ANGULAR_VELOCITY);
			isRolling = true;
		} else {
			rollLeft = false;
			rollRight = false;
			isRolling = false;
		}

	}
	public String rollingState() {
		if (rollLeft) return "left";
		else if (rollRight) return "right";
		else return "stop";
	}
	public void setJump() {
		isJumping = true;
	}

	@Override
	public HeroUserData getUserData() {
		return (HeroUserData) userData;
	}


}
