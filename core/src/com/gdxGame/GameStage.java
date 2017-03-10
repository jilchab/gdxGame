package com.gdxGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdxGame.GameActors.*;
import com.gdxGame.utils.BodyUtils;
import com.gdxGame.utils.Level;


public class GameStage extends Stage implements ContactListener {


	private Level level;
	private Hero hero;
	private Skin skin;
	private float accumulator = 0f;
	private OrthographicCamera camera;
	private Box2DDebugRenderer renderer;
	private String currentLevel;

	public GameStage(String level) {
		super();
		currentLevel = level;
		setupWorld();
		setupHero();
		setupCamera();
		setupButtons();
		renderer = new Box2DDebugRenderer();
	}


	@Override
	public void act(float delta) {
		super.act(delta);

		if (!level.camInfo.fixed) {
			camera.position.set(hero.getX(), hero.getY(), 0f);
			camera.update();
		}

		accumulator += delta;
		while (accumulator >= delta) {
			float TIME_STEP = 1 / 300f;
			level.world.step(TIME_STEP, 6, 2);
			accumulator -= TIME_STEP;
		}
	}

	@Override
	public void draw() {

		Batch batch = getBatch();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		hero.draw(batch, 1); // Draw the ball
		level.actors.draw(batch, 1); // Draw all actors
		batch.end();

		super.draw(); //Draw UI (buttons,etc.)
		//renderer.render(world, camera.combined);   //Debug collision
	}

	private void setupWorld() {

		Levels levels = new Levels();
		level = levels.load(currentLevel);
		level.world.setContactListener(this);
	}

	private void setupHero() {
		hero = new Hero(level.world);
		addActor(hero);
	}

	private void setupCamera() {
		camera = new OrthographicCamera(level.camInfo.viewportWidth, level.camInfo.viewportHeight);
		camera.position.set(level.camInfo.x, level.camInfo.y, 0f);
		camera.update();
	}

	private void setupButtons() {

		Gdx.input.setInputProcessor(this);
		skin = new Skin();
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("gdx-skins-master/buttons.txt"));
		skin.addRegions(atlas);
		Button.ButtonStyle sArrowLeft = new Button.ButtonStyle();
		sArrowLeft.up = skin.getDrawable("left");
		Button.ButtonStyle sArrowRight = new Button.ButtonStyle();
		sArrowRight.up = skin.getDrawable("right");
		Button.ButtonStyle sArrowUp = new Button.ButtonStyle();
		sArrowUp.up = skin.getDrawable("up");


		final Button bt_right = new Button(sArrowRight);
		final Button bt_left = new Button(sArrowLeft);
		final Button bt_up = new Button(sArrowUp);
		Table tButtons = new Table();
		addActor(tButtons);

		tButtons.setFillParent(true);
		//tButtons.setDebug(true);
		tButtons.bottom().left();
		tButtons.add(bt_left).width(100).height(100).pad(0, 200, 100, 100);
		tButtons.add(bt_right).width(100).height(100).pad(0, 100, 100, 0);
		tButtons.add(bt_up).width(100).height(100).pad(0, 0, 100, 200).expandX().right();

		bt_up.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				hero.jump();
				return true;
			}
		});
		bt_left.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				hero.roll("left");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				hero.roll("stop");
			}
		});
		bt_right.addListener(new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				hero.roll("right");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				hero.roll("stop");
			}
		});
	}

	@Override
	public void beginContact(Contact contact) {
		Body a = contact.getFixtureA().getBody();
		Body b = contact.getFixtureB().getBody();


		if ((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsGround(b)) ||
				(BodyUtils.bodyIsGround(a) && BodyUtils.bodyIsRunner(b))) {
			hero.landed();
			hero.roll(hero.rollingState());
		} else if ((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsPicks(b)) ||
				(BodyUtils.bodyIsPicks(a) && BodyUtils.bodyIsRunner(b))){

		}

	}

	@Override
	public void endContact(Contact contact) {
		Body a = contact.getFixtureA().getBody();
		Body b = contact.getFixtureB().getBody();

		if ((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsGround(b)) ||
				(BodyUtils.bodyIsGround(a) && BodyUtils.bodyIsRunner(b))) {
			hero.setJump();

		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}

	@Override
	public void dispose() {
		skin.dispose();
	}


}
