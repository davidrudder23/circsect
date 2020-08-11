package org.noses.cirsect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class EnemyMaker {

    private static EnemyMaker instance;

    private static List<Color> colors;

    static {
        colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.CHARTREUSE);
        colors.add(Color.LIME);
        colors.add(Color.PURPLE);
        colors.add(Color.ORANGE);
        colors.add(Color.CORAL);
        colors.add(Color.CYAN);
    }

    private Game game;

    public static EnemyMaker getInstance(Game game) {
        if (instance == null) {
            instance = new EnemyMaker(game);
        }

        return instance;
    }
    public EnemyMaker(Game game) {
        this.game = game;
    }

    public Enemy makeEnemy(List<Enemy> existingEnemies) {
        boolean found = false;
        Enemy enemy = null;

        while (!found) {
            float radius = (float)((Math.random() * 1.2) + 1.2) / game.getSmallestDimensionPercent();
            int x = (int)((Math.random() * (Gdx.graphics.getWidth()-radius))+radius);
            int y = (int)((Math.random() * (Gdx.graphics.getHeight()-radius))+radius);
            int angle = (int)(Math.random() * 360);

            Color color = colors.get((int)(Math.random()*colors.size()));
            enemy = new Enemy(x, y, radius, color, angle);

            found = true;
            for (Enemy otherEnemy : existingEnemies) {
                if (distance(enemy, otherEnemy) < (enemy.getRadius() + otherEnemy.getRadius())) {
                    found = false;
                }
            }
        }

        return enemy;
    }

    private static float distance(Enemy enemy1, Enemy enemy2) {
        return enemy1.distanceFrom(enemy2);
    }
}
