package org.noses.cirsect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public abstract class Circle {

    int x, y;
    float radius;
    Color color;
    ShapeRenderer sr;

    protected Circle(int x, int y, float radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        sr = new ShapeRenderer();

        System.out.println("radius="+radius);
    }


    public void render(Batch batch) {

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(color);
        sr.circle(x, y, radius);
        sr.end();
    }

    public float distanceFrom(Circle circle) {
        return (float)Math.sqrt(
                ((getX() - circle.getX()) * (getX() - circle.getX())) +
                        ((getY() - circle.getY()) * (getY() - circle.getY()))
        );
    }
}
