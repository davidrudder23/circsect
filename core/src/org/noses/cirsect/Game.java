package org.noses.cirsect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Game implements InputProcessor  {

    CirsectGame parent;

    List<Enemy> enemies;

    List<Explosion> explosions;

    int score;

    int level;

    boolean hasTakenTurn;

    Timer.Task timer;

    int speedMultiplier;
    BitmapFont font;
    Label scoreLabel;

    int numEnemiesToBeKilled;
    int numEnemiesTotal;

    float radius;

    GlyphLayout layout;

    Skin skin;

    public Game(CirsectGame parent) {
        this.parent = parent;

        skin = new Skin(Gdx.files.internal("skin/level-plane/skin/level-plane-ui.json"));


        enemies = new ArrayList<>();

        explosions = new ArrayList<>();

        Gdx.input.setInputProcessor(this);

        speedMultiplier = 1;

        score = 0;

        level = 0;

        layout = new GlyphLayout();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/score.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        font = generator.generateFont(parameter);
        font.getData().setScale(.07f/getSmallestDimensionPercent());
        generator.dispose();

    }

    public void start() {

        Gdx.input.setInputProcessor(this);

        timer = Timer.schedule(new Timer.Task() {
                                   @Override
                                   public void run() {
                                       clockTick(1 / 10.0f);
                                   }
                               }
                , 0f, 1 / (10.0f * speedMultiplier));

        level = 1;
        startNewLevel();
    }

    public void clockTick(float delay) {
        for (Enemy enemy: enemies) {
            enemy.clockTick(delay);
        }

        for (Explosion explosion: explosions) {
            explosion.clockTick(delay);
        }

        List<Enemy> enemiesToExplode = new ArrayList<>();
        for (Explosion explosion: explosions) {
            for (Enemy enemy : getEnemies()) {
                if (explosion.distanceFrom(enemy) < explosion.getRadius() + enemy.getRadius()) {
                    enemiesToExplode.add(enemy);
                }
            }
        }

        Iterator<Enemy> enemiesToExplodeIterator = enemiesToExplode.iterator();
        while (enemiesToExplodeIterator.hasNext()) {
            Enemy enemyToExplode = enemiesToExplodeIterator.next();
            explodeEnemy(enemyToExplode);
        }

        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            if (enemiesToExplode.contains(enemy)) {
                enemyIterator.remove();
            }
        }

        Iterator<Explosion> i = explosions.iterator();
        while (i.hasNext()) {
            Explosion explosion = i.next();
            if (!explosion.enabled) {
                i.remove();
            }
        }

        if (hasTakenTurn && explosions.size()==0) {
            endLevel();
        }
    }

    public void render(final Batch batch) {
        for (Enemy enemy: enemies) {
            enemy.render(batch);
        }

        for (Explosion explosion: explosions) {
            explosion.render(batch);
        }

        batch.begin();
        layout.setText(font, "Level: "+level);
        font.draw(batch, "Score: "+score, 30, Gdx.graphics.getHeight() - font.getLineHeight());
        font.draw(batch, "Level: "+level, Gdx.graphics.getWidth() - layout.width - 30, Gdx.graphics.getHeight() - font.getLineHeight());
        font.draw(batch, "Progress: "+(numEnemiesTotal - enemies.size())+"/"+numEnemiesToBeKilled, 30, Gdx.graphics.getHeight() - (font.getLineHeight() * 2));
        batch.end();


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
        if (hasTakenTurn) {
            return false;
        }

        Explosion explosion = new Explosion(this, screenX, Gdx.graphics.getHeight() - screenY, Color.WHITE);
        explosions.add(explosion);
        hasTakenTurn = true;

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

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void explodeEnemy(Enemy enemy) {
        Explosion explosion = new Explosion(this, enemy.getX(), enemy.getY(), enemy.color);
        explosions.add(explosion);

        score++;
        parent.playPoof();
    }

    private void endLevel() {
        level++;
        hasTakenTurn = false;

        if (enemies.size() > (numEnemiesTotal - numEnemiesToBeKilled)) {
            // failure
            parent.endGame();
        } else {
            startNewLevel();
        }
    }

    private void startNewLevel() {
        enemies.clear();

        numEnemiesTotal = level * 3;
        numEnemiesToBeKilled = numEnemiesTotal/2;
        for (int i = 0; i < numEnemiesTotal; i++) {
            enemies.add(EnemyMaker.getInstance(this).makeEnemy(enemies));
        }

        System.out.println("Have "+enemies.size()+" enemies");
    }
}
