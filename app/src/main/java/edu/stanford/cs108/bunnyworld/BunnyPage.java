package edu.stanford.cs108.bunnyworld;

import android.graphics.Canvas;

import java.util.*;
/**
 * Created by gongzibo on 11/19/17.
 */

public class BunnyPage {
    private String name;
    private List<BunnyShape> shapes;

    BunnyPage(String name) {
        this.name = name;
        shapes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void draw(Canvas canvas) {
        for(BunnyShape shape: shapes) {
            shape.draw(canvas);
        }
    }

    public void addShape(BunnyShape shape) {
        if(shapes.contains(shape)) removeShape(shape);
        shapes.add(shape);
    }

    public void removeShape(BunnyShape shape) {
        shapes.remove(shape);
    }

    public BunnyShape selectShape(float x, float y) {
        for(int i = shapes.size() - 1; i >= 0; i--) {
            BunnyShape shape = shapes.get(i);
            if(shape.isInside(x, y)) return shape;
        }
        return null;
    }

    public BunnyShape getShape(String shapeName) {
        for(int i = shapes.size() - 1; i >= 0; i--) {
            BunnyShape shape = shapes.get(i);
            if(shape.getName().equals(shapeName)) return shape;
        }
        return null;
    }
}
