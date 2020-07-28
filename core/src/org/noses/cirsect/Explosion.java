package org.noses.cirsect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;

public class Explosion extends Circle {
    int speedMultiplier;
    Game game;

    boolean enabled;

    int direction;

    public Explosion(Game game, int x, int y, Color color) {
        super (x, y, 1, color);

        this.game = game;
        speedMultiplier = 3;

        enabled = true;

        direction = 1;
    }

    public void clockTick(float delay) {
        if (radius > (15/game.getSmallestDimensionPercent())) {
            direction = -1;
        }

        if (radius < 1) {
            enabled = false;
        }

        radius += delay * speedMultiplier/game.getSmallestDimensionPercent() * direction;

    }
}
