package com.gdxGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxGame.GameActors.*;
import com.gdxGame.utils.BodyUtils;
import com.gdxGame.utils.WorldUtils;

import java.util.ArrayList;
import java.util.List;

public class GameStage extends Stage implements ContactListener{

	private World world;
	private Hero hero;
	private Skin skin;
	private float accumulator = 0f;
	private OrthographicCamera camera;
	private Box2DDebugRenderer renderer;
	List<Box> boxes;
	Table tButtons;
	Table tBoxes;

	public GameStage() {
		super();
		setupWorld();
		setupHero();
		setupCamera();
		setupButtons();
		renderer = new Box2DDebugRenderer();
	}


	@Override
	public void act(float delta) {
		super.act(delta);

		camera.position.set(hero.getX(), hero.getY(),0f);
		camera.update();

		accumulator += delta;
		while (accumulator >= delta) {
			float TIME_STEP = 1 / 300f;
			world.step(TIME_STEP, 6, 2);
			accumulator -= TIME_STEP;
		}
	}

	@Override
	public void draw() {

		super.draw();
		renderer.render(world, camera.combined);

		Batch batch = getBatch();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		hero.draw(batch,1);
		for (Box b:boxes) {
			b.draw(batch,1f);
		}
		batch.end();

	}

	private void setupWorld () {
		world = new World(WorldUtils.WORLD_GRAVITY, true);
		world.setContactListener(this);

		boxes = new ArrayList<Box>();
		boxes.add(new Box(world,WorldUtils.GROUND_X,WorldUtils.GROUND_Y,WorldUtils.GROUND_WIDTH,WorldUtils.GROUND_HEIGHT));
		boxes.add(new Box(world,40,10+ WorldUtils.GROUND_Y,1,20));
		boxes.add(new Box(world,-40,10+ WorldUtils.GROUND_Y,1,20));
		boxes.add(new Box(world,0,3,15,2));
		boxes.add(new Box(world,10,15,15,2));

		tBoxes = new Table();
		addActor(tBoxes);

		for (Box b:boxes) {
			//addActor(b);
			tBoxes.add(b);
		}


	}
	private void setupHero() {
		hero = new Hero(world);
		addActor(hero);
		//tButtons.add(hero);
	}
	private void setupCamera() {
		camera = new OrthographicCamera(WorldUtils.VIEWPORT_WIDTH, WorldUtils.VIEWPORT_HEIGHT);
		camera.position.set(hero.getX(), hero.getY(),0f);
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
		tButtons = new Table();
		addActor(tButtons);

		tButtons.setFillParent(true);
		//tButtons.setDebug(true);
		tButtons.bottom().left();
		tButtons.add(bt_left).width(100).height(100).pad(0,200,100,100);
		tButtons.add(bt_right).width(100).height(100).pad(0,100,100,0);
		tButtons.add(bt_up).width(100).height(100).pad(0,0,100,200).expandX().right();

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
	public void preSolve(Contact contact, Manifold oldManifold) {}
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {}

	@Override
	public void dispose() {
		skin.dispose();
	}



}
