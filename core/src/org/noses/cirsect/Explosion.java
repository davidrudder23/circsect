package org.noses.cirsect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;

public class Explosion extends Circle {
    Timer.Task timer;
    int speedMultiplier;
    Game game;

    long start;

    public Explosion(Game game, int x, int y, Color color) {
        super (x, y, 1, color);

        this.game = game;
        speedMultiplier = 3;

        timer = Timer.schedule(new Timer.Task() {
                                   @Override
                                   public void run() {
                                       clockTick(1 / 10.0f);
                                   }
                               }
                , 0f, 1 / (10.0f * speedMultiplier));

        start = System.currentTimeMillis();
    }

    public void clockTick(float delay) {
        radius += delay * speedMultiplier/game.getSmallestDimensionPercent();

        if (radius > (10/game.getSmallestDimensionPercent())) {
            game.removeExplosion(this);
            timer.cancel();
            radius = 0;

            System.out.println("Took "+(System.currentTimeMillis() - start));
        }

    }
}
