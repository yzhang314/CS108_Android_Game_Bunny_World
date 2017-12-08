package edu.stanford.cs108.bunnyworld;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by gongzibo on 11/19/17.
 */
// this is an example
public class BunnyShape {
    // map linking the string to the page that is going to be drawed
    static Map<String, BunnyPage> map;

    // static final constant indicating the type of the shape
    final int RECTANGLE = 0;
    final int IMAGE = 1;
    final int TEXT = 2;

    private String name;
    private String textString = "";
    private String imageString = "";
    private Paint shapePaint;
    private BitmapDrawable shapeDrawable;
    private Paint onDropPaintTrue;
    private Paint onDropPaintFalse;
    private float left, right, bottom, top;
    boolean isInsideInventory;
    String selectScript;
    Canvas canvas;
    int type;
    boolean moveable;
    boolean visiable;
    boolean isOnDrop;
    boolean isOnDropValid;
    public String tempScript = "";
    //boolean hidden;


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

    //public Paint getShapePaint() {
    //    return shapePaint;
    //}

    //public void setShapePaint(Paint shapePaint) {
    //    this.shapePaint = shapePaint;
    //}

    public BitmapDrawable getShapeDrawable () { return this.shapeDrawable; }

    public void setShapeDrawable(BitmapDrawable shapeDrawable) { this.shapeDrawable = shapeDrawable; }

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

    public boolean getMoveable() {
        return moveable;
    }

    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    public boolean isVisiable() { return visiable; }

    public void setVisiable(boolean visiable) { this.visiable = visiable; }

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

    public void setIsOnDrop(boolean isOnDrop) { this.isOnDrop = isOnDrop; }

    public boolean getIsOnDrop() { return isOnDrop; }

    public void setIsOnDropValid(boolean isOnDropValid) { this.isOnDropValid = isOnDropValid; }

    public boolean getIsOnDropValid() {return isOnDropValid; }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setType(int type) {
        this.type = type;
    }

   /* public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
    */

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getSelectedScript() {
        return selectScript;
    }

    public String getPlaceScript() {
        return "";
    }

    BunnyShape(String name, int type, float left, float right, float top, float bottom, String selectScript, boolean moveable, boolean visiable) {
        this.name = name;
        this.type = type;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.selectScript = selectScript;
        this.moveable = moveable;
        this.visiable = visiable;
        this.isInsideInventory = false;

        shapePaint = new Paint();
        Random random = new Random();
        int ranColor = 0xff000000 | random.nextInt(0x00ffffff);
        shapePaint.setColor(ranColor);
        shapePaint.setStyle(Paint.Style.FILL);

        onDropPaintTrue = new Paint();
        onDropPaintTrue.setColor(Color.GREEN);
        onDropPaintTrue.setStyle(Paint.Style.STROKE);
        onDropPaintTrue.setStrokeWidth(15.0f);
        onDropPaintFalse = new Paint();
        onDropPaintFalse.setColor(Color.RED);
        onDropPaintFalse.setStyle(Paint.Style.STROKE);
        onDropPaintFalse.setStrokeWidth(15.0f);




    }



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
       // this.hidden = false;
        this.visiable = true;

        shapePaint = new Paint();
        Random random = new Random();
        int ranColor = 0xff000000 | random.nextInt(0x00ffffff);
        shapePaint.setColor(ranColor);
        shapePaint.setStyle(Paint.Style.FILL);
    }

    BunnyShape(){

    }

    public void draw(Canvas canvas) {
        this.canvas = canvas;
        if(visiable) {
            String textString = getTextString();
            if (textString != null) {
                RectF boundaryRectangle = new RectF(getLeft(), getTop(), getRight(), getBottom());
                TextPaint paint;
                paint = new TextPaint();
                paint.setTextSize(50);
                paint.setColor(0xff000000);

                StaticLayout sl = new StaticLayout(getTextString(), paint, (int) boundaryRectangle.width(), Layout.Alignment.ALIGN_CENTER, 1, 1, false);

                canvas.save();
                canvas.translate(boundaryRectangle.left, boundaryRectangle.top);
                sl.draw(canvas);
                canvas.restore();
            }
            else if(shapeDrawable != null) {
                canvas.drawBitmap(shapeDrawable.getBitmap(), null, new RectF(left, top, right, bottom), null);
            }
            else {
                canvas.drawRect(left, top, right, bottom, shapePaint);
            }

            if(isOnDrop) {
                if(isOnDropValid) {
                    canvas.drawRect(left, top, right, bottom, onDropPaintTrue);
                }
                else {
                    canvas.drawRect(left, top, right, bottom, onDropPaintFalse);
                }
            }
        }
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
    public List<String> onClick = new ArrayList<>();
    public List<String> onEnter = new ArrayList<>();
    public List<String> onDrop = new ArrayList<>();

    /*
    This is the method to parse the longer string to the field of the shap class
     */
    public void loadToDB () {
        String reaction = this.getSelectScript();
//        String[] onClickString = reaction.split("onClick");
//        String[] onClickEvent =onClickString[1].split("|");
//        this.onClick = onClickEvent[0];
//        String[] onEnterString = reaction.split("onEnter");
//        String[] onEnterEvent = onEnterString[1].split("|");
//        this.onEnter = onEnterEvent[0];
//        String[] onDropString = reaction.split("onDrop");
//        String[] onDropEvent = onDropString[1].split("|");
//        this.onDrop = onDropEvent[0];
        String[] allAction = reaction.split("|");
        for (String s : allAction) {
            if (s.indexOf("onClick") >= 0) {
                this.onClick.add(s.substring(7));
            } else if (s.indexOf("onEnter") >= 0) {
                this.onEnter.add(s.substring(7));
            } else if (s.indexOf("onDrop") >= 0) {
                this.onDrop.add(s.substring(6));
            }
        }



    }


}
