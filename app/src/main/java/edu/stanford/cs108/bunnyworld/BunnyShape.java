package edu.stanford.cs108.bunnyworld;

import android.graphics.*;

import java.util.Map;
import java.util.Random;

/**
 * Created by gongzibo on 11/19/17.
 */

public class BunnyShape {
    // map linking the string to the page that is going to be drawed
    static Map<String, BunnyPage> map;

    final int RECTANGLE = 0; 
    final int IMAGE = 1; 
    final int TEXT = 2;

    private String name;
    private String storedInfo;
    private Paint shapePaint;
    private float left, right, bottom, top;
    boolean moveable;
    boolean isInsideInventory;
    String selectScript;
    Canvas canvas;
    int type;

    BunnyShape(String name, String storedInfo, int type, float left, float right, float top, float bottom, String selectScript, boolean moveable) {
        this.name = name;
        this.storedInfo = storedInfo;
        this.type = type;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.selectScript = selectScript;
        this.moveable = moveable;
        this.isInsideInventory = false;

        shapePaint = new Paint();
        Random random = new Random();
        int ranColor = 0xff000000 | random.nextInt(0x00ffffff);
        shapePaint.setColor(ranColor);
        shapePaint.setStyle(Paint.Style.FILL);
    }

    public String getName() {
        return name;
    }

    public void draw(Canvas canvas) {
        this.canvas = canvas;
        switch(type) {
            case 0: drawRectangle();
                break;
            case 1: drawImage();
                break;
            case 2: drawText();
        }

    }
    public void drawRectangle() {
        canvas.drawRect(left, top, right, bottom, shapePaint);

    }

    public void drawImage() {

    }

    public void drawText() {
        
    }

    public String getSelectedScript() {
        return selectScript;
    }

    public String getPlaceScript() {
        return "";
    }

    public void move(float x, float y) {
        this.left += x;
        this.right += x;
        this.top += y;
        this.bottom += y;
    }

    public boolean isInside(float x, float y) {
        return (x >= left && x <= right && y >= top && y <= bottom);
    }

    private void onClick (String onClickScript) {
        // parse the string to movement and destination the example shows a way to parse the string by ":"
        String[] movement = onClickScript.split(":");
        switch(movement[0]) {
            case "goto" :
                goTo(movement[1], canvas);
                break;
            case "play" :
                break;
            case "show" :
                break;
            default:
                break;
        }
    }

    private void goTo(String gotoString, Canvas canvas) {
        BunnyPage curtPage = map.get(gotoString);
        curtPage.draw(canvas);
    }
}
