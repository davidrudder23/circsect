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
    int radius;

    Color color;

    public void render (Batch batch) {
        ShapeRenderer sr = new ShapeRenderer();
        sr.setColor(color);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.circle(x, y, radius);
        sr.end();
    }
}
