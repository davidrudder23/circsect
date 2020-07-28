package org.noses.cirsect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class EnemyMaker {

    private static List<Color> colors;

    static {
        colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.CHARTREUSE);
        colors.add(Color.LIME);
        colors.add(Color.OLIVE);
        colors.add(Color.ORANGE);
        colors.add(Color.CORAL);
        colors.add(Color.CYAN);

    }

    public static Enemy makeEnemy(List<Enemy> existingEnemies) {
        boolean found = false;
        Enemy enemy = null;

        while (!found) {
            int radius = (int)(Math.random() * 5) + 5;
            int x = (int) (Math.random() * (Gdx.graphics.getWidth()-radius))+radius;
            int y = (int) (Math.random() * (Gdx.graphics.getHeight()-radius))+radius;
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

    private static int distance(Enemy enemy1, Enemy enemy2) {
        return (int) Math.sqrt(
                ((enemy1.getX() - enemy2.getX()) * (enemy1.getX() - enemy2.getX())) +
                        ((enemy1.getY() - enemy2.getY()) * (enemy1.getY() - enemy2.getY()))
        );
    }
}
