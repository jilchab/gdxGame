package com.gdxGame.GameActors;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gdxGame.GameActors.UserData.UserData;

public class Hero extends GameActor {

	private boolean isJumping,isRolling;
	private Body movingBody;
	private boolean rollLeft,rollRight;
	private TextureRegion textureRegion= new TextureRegion(new Texture(Gdx.files.internal("circle.png")));


	public static final float RADIUS = 2f;
	public static final float DENSITY = 1f;
	public static final Vector2 JUMPING_LINEAR_IMPULSE = new Vector2(0, 500f);
	public static final float LINEAR_GROUND_VELOCITY = 25f;
	public static final float LINEAR_AIR_VELOCITY = 15f;
	public static final float ANGULAR_VELOCITY = 10f;
	public static final float ANGULAR_TORQUE_BRAKE = 1000f;



	public Hero(World world, float x, float y, float radius, float density) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(new Vector2(x, y));
		body = world.createBody(bodyDef);
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);
		body.createFixture(shape, density);
		body.resetMassData();
		body.setUserData(new com.gdxGame.GameActors.UserData.HeroUserData());
		userData = (UserData) body.getUserData();
		shape.dispose();

		setPosition(x,y);
		setSize(radius*2,radius*2);

	}
	public Hero(World world, float x, float y) {
		this(world,x,y,RADIUS,DENSITY);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if(!isJumping && !isRolling) {
			if(body.getLinearVelocity().x > 0) {
				body.applyTorque(ANGULAR_TORQUE_BRAKE,true);
			}
			else if(body.getLinearVelocity().x < 0) {
				body.applyTorque(-ANGULAR_TORQUE_BRAKE,true);
			}
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch,parentAlpha);

		batch.draw(textureRegion,(body.getPosition().x-getWidth()/2),
				(body.getPosition().y-getHeight()/2),getWidth(),getHeight());
	}

	public void jump() {
		if (!isJumping) {
			isJumping = true;
			body.applyLinearImpulse(getUserData().getJumpingLinearImpulse(), body.getWorldCenter(), true);
		}
	}
	public void landed() {
		isJumping = false;
		if (!isRolling) {
			if(body.getLinearVelocity().x > 0)
				body.setLinearVelocity(5,body.getLinearVelocity().y);
			else if(body.getLinearVelocity().x < 0)
				body.setLinearVelocity(-5,body.getLinearVelocity().y);
		}
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
		} else if(!direction.equals("null")){

			if (!isJumping) {
				if(body.getLinearVelocity().x > 0)
					body.setLinearVelocity(5,body.getLinearVelocity().y);
				else if(body.getLinearVelocity().x < 0)
					body.setLinearVelocity(-5,body.getLinearVelocity().y);
			}

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
	public com.gdxGame.GameActors.UserData.HeroUserData getUserData() {
		return (com.gdxGame.GameActors.UserData.HeroUserData) userData;
	}



	public void setPos(float x, float y) {
		super.setPosition(x,y);
		body.setTransform(new Vector2(x,y),0);
	}
}
