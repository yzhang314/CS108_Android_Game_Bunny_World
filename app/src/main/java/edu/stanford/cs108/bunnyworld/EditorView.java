package edu.stanford.cs108.bunnyworld;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.RectF;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by yuwenzhang on 11/27/17.
 */


public class EditorView extends View {
    List<BunnyShape> shapeList;
    Canvas canvas;

    public EditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*
        BunnyPage current = new BunnyPage("lalala");
        super.onDraw(canvas);
        current.draw(canvas);
        */
        shapeList = new ArrayList<>();
        shapeList.add(new BunnyShape("test", 1, 100, 400, 100, 400, "aaa", false));
        this.canvas = canvas;

        for (BunnyShape shape: shapeList) {
            int type = shape.getType();
            switch(type) {
                case 0: shape.draw(canvas);break;
                case 1: drawImage(shape); break;
                case 2: drawText(shape); break;
                default:
            }
        }




    }

    public void drawImage(BunnyShape shape) {
        RectF boundaryRectangle = new RectF(shape.getLeft(),shape.getTop(),shape.getRight(),shape.getBottom());
        BitmapDrawable test = (BitmapDrawable) getResources().getDrawable(R.drawable.carrot);
        Bitmap testPicture = test.getBitmap();
        canvas.drawBitmap(testPicture, null, boundaryRectangle, null);
    }

    public void drawText(BunnyShape shape) {
        String textString = shape.getTextString();
        //canvas.drawText("textString",);

    }
}
