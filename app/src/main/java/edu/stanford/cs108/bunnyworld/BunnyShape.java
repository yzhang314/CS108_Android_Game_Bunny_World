package edu.stanford.cs108.bunnyworld;

import android.graphics.*;

import java.util.Map;
import java.util.Random;

/**
 * Created by gongzibo on 11/19/17.
 */
// this is an example
public class BunnyShape {
    // map linking the string to the page that is going to be drawed
    static Map<String, BunnyPage> map;

    final int RECTANGLE = 0;
    final int IMAGE = 1;
    final int TEXT = 2;

    private String name;
    private String textString = "";
    private String imageString = "";
    private Paint shapePaint;
    private float left, right, bottom, top;
    boolean isInsideInventory;
    String selectScript;
    Canvas canvas;
    int type;

    boolean hidden;
    boolean moveable;

    BunnyShape(String name, int type, float left, float right, float top, float bottom, String selectScript, boolean moveable) {
        this.name = name;
        this.type = type;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.selectScript = selectScript;
        this.moveable = moveable;
        this.isInsideInventory = false;
        this.hidden = false;

        shapePaint = new Paint();
        Random random = new Random();
        int ranColor = 0xff000000 | random.nextInt(0x00ffffff);
        shapePaint.setColor(ranColor);
        shapePaint.setStyle(Paint.Style.FILL);
    }

    public String getTextString() {
        return textString;
    }

    public void setTextString(String textString) {
        this.textString = textString;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Paint getShapePaint() {
        return shapePaint;
    }

    public void setShapePaint(Paint shapePaint) {
        this.shapePaint = shapePaint;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public boolean isMoveable() {
        return moveable;
    }

    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    public boolean isInsideInventory() {
        return isInsideInventory;
    }

    public void setInsideInventory(boolean insideInventory) {
        isInsideInventory = insideInventory;
    }

    public String getSelectScript() {
        return selectScript;
    }

    public void setSelectScript(String selectScript) {
        this.selectScript = selectScript;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setType(int type) {
        this.type = type;
    }
    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }


    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public void draw(Canvas canvas) {
        this.canvas = canvas;
        canvas.drawRect(left, top, right, bottom, shapePaint);

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

    // The variables are temporarily made public in order to test them
    public String onClick = "";
    public String onEnter = "";
    public String onDrop = "";

    /*
    This is the method to parse the longer string to the field of the shap class
     */
    public void loadToDB () {
        String reaction = this.getSelectScript();
        String[] onClickString = reaction.split("onClick");
        String[] onClickEvent =onClickString[1].split("|");
        this.onClick = onClickEvent[0];
        String[] onEnterString = reaction.split("onEnter");
        String[] onEnterEvent = onEnterString[1].split("|");
        this.onEnter = onEnterEvent[0];
        String[] onDropString = reaction.split("onDrop");
        String[] onDropEvent = onDropString[1].split("|");
        this.onDrop = onDropEvent[0];

    }


}
