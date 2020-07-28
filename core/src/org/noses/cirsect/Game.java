package org.noses.cirsect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Game {

    CirsectGame parent;

    List<Enemy> enemies;

    List<Explosion> explosions;

    public Game(CirsectGame parent) {
        this.parent = parent;
        enemies = new ArrayList<>();
        explosions = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            enemies.add(EnemyMaker.makeEnemy(enemies));
        }

        Enemy enemy = new Enemy(320, 240, 10, Color.RED, 135);

        enemies.add(enemy);
    }

    public void checkForHit() {
        parent.addHit(1);
    }

    public void render(final Batch batch) {
        for (Enemy enemy: enemies) {
            enemy.render(batch);
        }
    }
}
