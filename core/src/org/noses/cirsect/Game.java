package org.noses.cirsect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Game implements InputProcessor  {

    CirsectGame parent;

    List<Enemy> enemies;

    List<Explosion> explosions;

    public Game(CirsectGame parent) {
        this.parent = parent;

        enemies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            enemies.add(EnemyMaker.getInstance(this).makeEnemy(enemies));
        }

        explosions = new ArrayList<>();

        Gdx.input.setInputProcessor(this);
    }

    public void checkForHit() {
        parent.addHit(1);
    }

    public void render(final Batch batch) {
        for (Enemy enemy: enemies) {
            enemy.render(batch);
        }

        for (Explosion explosion: explosions) {
            explosion.render(batch);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (explosions.size()==0) {
            Explosion explosion = new Explosion(this, screenX, Gdx.graphics.getHeight() - screenY, Color.WHITE);
            explosions.add(explosion);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void removeExplosion(Explosion explosion) {
        explosions.remove(explosion);
    }

    public float percentPerPixelWidth() {
        return parent.percentPerPixelWidth;
    }

    public float percentPerPixelHeight() {
        return parent.percentPerPixelHeight;
    }

    public int getSmallestDimensionPixels() {
        return parent.smallestDimensionPixels;
    }

    public float getSmallestDimensionPercent() {
        return parent.smallestDimensionPercent;
    }

}
