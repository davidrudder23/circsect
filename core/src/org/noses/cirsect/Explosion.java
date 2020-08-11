package org.noses.cirsect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;

public class Explosion extends Circle {
    Game game;

    boolean enabled;

    int direction;

    float maxSize;

    float speedMultiplier;

    public Explosion(Game game, int x, int y, Color color) {
        super (x, y, 1, color);

        maxSize = Math.max(5, 15 - (game.getLevel()/2));
        System.out.println("MaxSize="+maxSize);

        this.game = game;
        speedMultiplier = 3;

        enabled = true;

        direction = 1;

        speedMultiplier = 1f+ (game.getLevel()/5);
    }

    public void clockTick(float delay) {
        if (radius > (maxSize/game.getSmallestDimensionPercent())) {
            direction = -1;
        }

        if (radius < 1) {
            enabled = false;
        }

        radius += delay * speedMultiplier/game.getSmallestDimensionPercent() * direction;

    }
}
