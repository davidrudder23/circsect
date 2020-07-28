package org.noses.cirsect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Circle {

    int x, y;
    float radius;

    Color color;

    public void render(Batch batch) {
        ShapeRenderer sr = new ShapeRenderer();

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(color);
        sr.circle(x, y, radius);
        sr.end();
    }

    public int distanceFrom(Circle circle) {
        return (int) Math.sqrt(
                ((getX() - circle.getX()) * (getX() - circle.getX())) +
                        ((getY() - circle.getY()) * (getY() - circle.getY()))
        );
    }
}
