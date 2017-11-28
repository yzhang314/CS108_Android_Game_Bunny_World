package edu.stanford.cs108.bunnyworld;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongzibo on 11/20/17.
 */

public class Inventory {
    private List<BunnyShape> list;
    private float left, right, top, bottom;
    private Paint inventoryPaint;

    Inventory(float left, float right, float top, float bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        list = new ArrayList<>();
        inventoryPaint = new Paint();
        inventoryPaint.setColor(Color.GRAY);
        inventoryPaint.setStyle(Paint.Style.FILL);
    }

    public void addShape(BunnyShape shape) {
        list.add(shape);
    }

    public void removeShape(BunnyShape shape) {
        list.remove(shape);
    }

    public boolean isInsideInventory(float x, float y) {
        return (x >= left && x <= right && y >= top && y <= bottom);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(left, top, right, bottom, inventoryPaint);
        for(BunnyShape shape : list) {
            shape.draw(canvas);
        }
    }

    public BunnyShape selectShape(float x, float y) {
        for(int i = list.size() - 1; i >= 0; i--) {
            BunnyShape shape = list.get(i);
            if(shape.isInside(x, y)) return shape;
        }
        return null;
    }
}
