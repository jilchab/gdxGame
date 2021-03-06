package com.gdxGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gdxGame.GameActors.*;
import com.gdxGame.Screens.GameScreen;
import com.gdxGame.Screens.LevelSelection1PlayerScreen;
import com.gdxGame.utils.*;


public class GameStage extends Stage implements ContactListener,InputProcessor {


	private Level level;
	private Hero hero;
	private Skin skin;
	private OrthographicCamera camera;
	private Box2DDebugRenderer renderer;
	private int currentLevel;
	private String sCurrentLevel;
	private GameName game;
	private float timestep = 1f / 60f;
	private boolean isPause = false,isWin = false;
	private Image pauseMenu = new Image(new Texture(Gdx.files.internal("pause.png"))),
	winMenu = new Image(new Texture(Gdx.files.internal("win.png")));
	private float srX,srY;


	public GameStage(int level) {
		super();
		pauseMenu.setScale(0.1f); // Menu too big
		winMenu.setScale(0.1f);
		currentLevel = level;
		sCurrentLevel = "level_"+currentLevel;
		setupWorld();
		setupHero();
		setupCamera();
		setupButtons();
		renderer = new Box2DDebugRenderer();
		srX = getViewport().getScreenWidth() / 1920f;
		srY = getViewport().getScreenHeight() / 1080f;
	}

	@Override
	public void act(float delta) {
		if (timestep >0) super.act(delta);

		if ( !level.camInfo.fixed && !isWin) {
			camera.position.set(hero.getX(), hero.getY(), 0f);
			camera.update();
		}

		winMenu.setPosition(camera.position.x-winMenu.getWidth()/20,camera.position.y-winMenu.getHeight()/20);
		pauseMenu.setPosition(camera.position.x-pauseMenu.getWidth()/20,camera.position.y-pauseMenu.getHeight()/20);

		if(hero.getX() > level.exit.x + 1 && hero.getX() < level.exit.x + 3         //Found Exit
		&& hero.getY() > level.exit.y + 1 && hero.getY() < level.exit.y + 3) {
			win();
		}

		level.world.step(timestep, 6, 2);
	}

	@Override
	public void draw() {

		Batch batch = getBatch();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		level.actors.draw(batch, 1); // Draw all actors
		if(!isWin)  hero.draw(batch, 1); // Draw the ball
		else winMenu.draw(batch,1f);
		if(isPause && !isWin) pauseMenu.draw(batch,1f);


		batch.end();



		//getBatch().setProjectionMatrix(camera.combined);

		if(!isPause && !isWin)    super.draw(); //Draw UI (buttons,etc.)
		//renderer.render(level.world, camera.combined);   //Debug collision
	}

	private void setupWorld() {

		com.gdxGame.utils.Levels levels = new com.gdxGame.utils.Levels();
		level = levels.load(sCurrentLevel);
		level.world.setContactListener(this);
		level.actors.setVisible(false);
		addActor(level.actors);
	}

	private void setupHero() {
		hero = new Hero(level
				.world,level.spawn.x,level.spawn.y);
		hero.setVisible(false);
		addActor(hero);
	}

	private void setupCamera() {
		camera = new OrthographicCamera(level.camInfo.viewportWidth, level.camInfo.viewportHeight);
		camera.position.set(level.camInfo.x, level.camInfo.y, 0f);
		camera.update();
	}

	public void pause() {
		if(timestep == 0)  {
			timestep = 1f/60f;
			isPause = false;
		}
		else {
			timestep = 0;
			isPause = true;
		}

	}
	public void win() {
		hero.remove();
		isWin = true;
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
			restart();
			dispose();
			game.setScreen(new GameScreen(game,currentLevel));
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
		super.dispose();
	}
	public void setGame(GameName game) {
		this.game = game;
	}

	public String getTouchAction(int x, int y) {
		
		if(isPause) {
			if (x > 1050*srX && y > 600*srY && x < 1450*srX && y < 800*srY)
				return "pause";
			else if (x > 500*srX && y > 600*srY && x < 900*srX && y < 800*srY)
				game.setScreen(new LevelSelection1PlayerScreen(game));
		} else if(isWin) {
			if (x>1050*srX && y>650*srY && x<1450*srX && y<800*srY) {
				if(currentLevel < Levels.LAST_LEVEL)
					game.setScreen(new GameScreen(game,currentLevel+1));
				else
					game.setScreen(new LevelSelection1PlayerScreen(game));
			}

			else if(x>500*srX && y>650*srY && x<900*srX && y<800*srY)
				game.setScreen(new LevelSelection1PlayerScreen(game));
		} else {
			if (y > (int)(750f*srY)) {
				//System.out.println((int)(750f*srY)+"  "+y);
				if (x > 1500*srX) return "jump";
				else if (x < 400*srX) return "left";
				else if (x < 800*srX) return "right";
			} else if (y < 330*srY && x < 330*srX) {
				return "pause";
			}
		}
		return "null";

	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		//System.out.println(screenX+" "+screenY);
		String action = getTouchAction(screenX,screenY);
		if(action.equals("pause")) pause();
		else if(action.equals("jump")) hero.jump();
		else hero.roll(action);

		return true;
	}

	public void restart() {
		hero.setPos(level.spawn.x,level.spawn.y);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(getTouchAction(screenX,screenY).equals("left") || getTouchAction(screenX,screenY).equals("right"))
		hero.roll("stop");
		return true;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		String action = getTouchAction(screenX, screenY);
		if (    (action.equals("left") && hero.rollingState().equals("right")) ||
				(action.equals("right") && hero.rollingState().equals("left"))) {

			hero.roll("stop");
			hero.roll(action);
		}
		return true;
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
		Button.ButtonStyle sPause = new Button.ButtonStyle();
		sPause.up = skin.getDrawable("pause");


		final Button bt_right = new Button(sArrowRight);
		final Button bt_left = new Button(sArrowLeft);
		final Button bt_up = new Button(sArrowUp);
		final Button bt_pause = new Button(sPause);

		Table tButtons = new Table();
		addActor(tButtons);

		// Adaptation for different resolutions
		float x100 =100*getViewport().getScreenWidth()/1920;
		float y100 =100*getViewport().getScreenHeight()/1080;
		float x200 =200*getViewport().getScreenWidth()/1920;
		float y50 = 50*getViewport().getScreenHeight()/1080;

		tButtons.setFillParent(true);
		//tButtons.setDebug(true);
		tButtons.top().left();
		tButtons.add(bt_pause).width(x100).height(y100).pad(y50, 0, 0, 0);
		tButtons.row();
		tButtons.add(bt_left).width(x100).height(y100).pad(0, x200, y100, x100).expandY().bottom().left();
		tButtons.add(bt_right).width(x100).height(y100).pad(0, x100, y100, 0).expandY().bottom().left();
		tButtons.add(bt_up).width(x100).height(y100).pad(0, 0, y50, x200).expandX().bottom().right();

		/*
		{
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
		*/
	}
}
