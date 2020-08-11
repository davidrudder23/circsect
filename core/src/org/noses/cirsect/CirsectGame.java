package org.noses.cirsect;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import lombok.Data;

public class CirsectGame extends ApplicationAdapter {

	enum State {
		START,
		INSTRUCTIONS,
		GAME
	}

	State state;
	SpriteBatch batch;

	int score;

	Game game;

	float percentPerPixelWidth;
	float percentPerPixelHeight;
	float smallestDimensionPercent;

	int smallestDimensionPixels;

	StartScreen startScreen;

	Music backgroundMusic;

	AssetManager manager;

	InstructionsScreen instructionsScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();

		updateDimensions();

		game = new Game(this);
		score = 0;
		startScreen = new StartScreen(this, null);

		instructionsScreen = new InstructionsScreen(this);

		state = State.START;

		manager = new AssetManager();
		manager.load("music.mp3", Music.class);
		manager.load("poof.mp3", Sound.class);
		manager.finishLoading();

		backgroundMusic = manager.get("music.mp3");
		backgroundMusic.setLooping(true);
		backgroundMusic.play();

		instructionsScreen.create();

		startScreen.start();

	}

	private void updateDimensions() {
		percentPerPixelWidth = 100f/Gdx.graphics.getWidth();
		percentPerPixelHeight = 100f/Gdx.graphics.getHeight();
		smallestDimensionPercent = percentPerPixelWidth;
		if (percentPerPixelHeight < smallestDimensionPercent) {
			smallestDimensionPercent = percentPerPixelHeight;
		}

		System.out.println("smallestDimensionPercent="+smallestDimensionPercent);

		smallestDimensionPixels = Gdx.graphics.getWidth();
		if (Gdx.graphics.getHeight() < smallestDimensionPixels) {
			smallestDimensionPixels = Gdx.graphics.getHeight();
		}
	}

	public void startGame() {
		/*if (backgroundLoop != null) {
			backgroundLoop.stop();
			backgroundLoop = manager.get("music.mp3");
			backgroundLoop.loop();
		}*/
		state = State.GAME;
		game.start();
	}

	public void endGame() {
		state = State.START;
		startScreen.start();
	}

	public void instructions() {
		state = State.INSTRUCTIONS;
		instructionsScreen.create();
	}


	public void mainMenu() {
		state = State.START;
		startScreen.start();
	}

	public void playPoof() {
		Sound poof = manager.get("poof.mp3");
		poof.play();
	}

	@Override
	public void render () {
		if (state == State.GAME) {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			game.render(batch);
		} else if (state == state.INSTRUCTIONS) {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			instructionsScreen.render(Gdx.graphics.getDeltaTime());
		} else {
			Gdx.gl.glClearColor(255, 255, 255, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			startScreen.render(batch);
		}

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public void addHit(int score) {
		score += score;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		instructionsScreen.resize(width, height);
		updateDimensions();
	}
}
