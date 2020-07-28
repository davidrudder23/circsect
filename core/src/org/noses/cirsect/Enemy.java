package org.noses.cirsect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;

public class Enemy extends Circle {

    int angle;
    float speedMultiplier;

    public Enemy(int x, int y, float radius, Color color, int angle) {
        super(x, y, radius, color);

        this.angle = angle;

        speedMultiplier = 10f;
    }

    public void clockTick(float delta) {
        //System.out.println("angle="+angle+" delta="+delta+" calc="+ ((int)(Math.sin(angle) * speedMultiplier)));
        x = x + (int) (Math.cos((Math.PI*angle)/180) * speedMultiplier);
        y = y + (int) (Math.sin((Math.PI*angle)/180) * speedMultiplier);

        if (x < radius) {
            x = (int)radius+1;
            angle = 180 - angle;
        } else if (x > (Gdx.graphics.getWidth() - radius)) {
            x = Gdx.graphics.getWidth() - (int)radius;
            angle = 180 - angle;
        } else if (y < radius) {
            y =  (int)radius+1;
            angle = 360 - angle;
        } else if (y > (Gdx.graphics.getHeight() - (int)radius)) {
            y = Gdx.graphics.getHeight() - (int)radius;
            angle = 360 - angle;
        }

        if (angle < 0) {
            angle = 360 + angle;
        }
    }
}
