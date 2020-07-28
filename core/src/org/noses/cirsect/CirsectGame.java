package org.noses.cirsect;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Data;

public class CirsectGame extends ApplicationAdapter {
	SpriteBatch batch;

	int score;

	Game game;

	float percentPerPixelWidth;
	float percentPerPixelHeight;
	float smallestDimensionPercent;

	int smallestDimensionPixels;

	@Override
	public void create () {
		batch = new SpriteBatch();

		updateDimensions();

		game = new Game(this);
		score = 0;

		Sound backgroundLoop = Gdx.audio.newSound(Gdx.files.internal("music.mp3"));
		backgroundLoop.loop();
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

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		game.render(batch);
		batch.end();


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
		updateDimensions();
	}
}
