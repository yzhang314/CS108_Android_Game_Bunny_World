package edu.stanford.cs108.bunnyworld;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.RectF;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.StaticLayout;
import android.text.Layout;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by yuwenzhang on 11/27/17.
 */


public class EditorView extends View {
    List<BunnyShape> shapeList = new ArrayList<>();
    Canvas canvas;
    Inventory inventory;
    Map<String, BunnyPage> pageMap = new HashMap<>();;
    BunnyPage page1;
    BunnyPage currentPage;
    int pageIndex = 1;

    public EditorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        loadInialPage();

        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*
        BunnyPage current = new BunnyPage("lalala");
        super.onDraw(canvas);
        current.draw(canvas);
        */



       this.canvas = canvas;


        for (BunnyShape shape: shapeList) {
            int type = shape.getType();
            switch(type) {
                case 0: shape.draw(canvas);break;
                case 1: drawImage(shape); break;
                case 2: drawText(shape); break;
                default:;
            }
        }


    }

    private void loadInialPage() {
        page1 = new BunnyPage("page1");
        currentPage = page1;
        pageIndex++;
    }


    public void createNewPage() {

        BunnyPage newPage = new BunnyPage("page" + pageIndex);
        pageMap.put("page" + pageIndex, newPage);
        currentPage = newPage;
        this.shapeList = currentPage.getShapes();
        pageIndex++;
        invalidate();

    }


    //to test some shapes
    public void init() {
        page1.addShape(new BunnyShape("test", 1, 100, 400, 100, 400, "aaa", false));
        BunnyShape testText = new BunnyShape("test1", 2, 400, 700, 400, 700, "bbb", false);
        testText.setTextString("lalallalall");
        page1.addShape(testText);
        pageMap.put("page1", page1);
        this.shapeList = page1.getShapes();
    }

    public void drawImage(BunnyShape shape) {
        RectF boundaryRectangle = new RectF(shape.getLeft(),shape.getTop(),shape.getRight(),shape.getBottom());
        BitmapDrawable test = (BitmapDrawable) getResources().getDrawable(R.drawable.carrot);
        Bitmap testPicture = test.getBitmap();
        canvas.drawBitmap(testPicture, null, boundaryRectangle, null);
    }

    public void drawText(BunnyShape shape) {
        RectF boundaryRectangle = new RectF(shape.getLeft(),shape.getTop(),shape.getRight(),shape.getBottom());

        TextPaint paint;
        paint = new TextPaint();
        Random random = new Random();
        int ranColor = 0xff000000 | random.nextInt(0x00ffffff);
        paint.setColor(ranColor);
        paint.setTextSize(50);

        Paint shapePaint;
        shapePaint = new Paint();
        Random random1 = new Random();
        int ranColor1 = 0xff000000 | random1.nextInt(0x00ffffff);
        shapePaint.setColor(ranColor1);
        shapePaint.setStyle(Paint.Style.FILL);

        canvas.drawRect(boundaryRectangle, shapePaint);

        StaticLayout sl = new StaticLayout(shape.getTextString(), paint, (int)boundaryRectangle.width(), Layout.Alignment.ALIGN_CENTER, 1, 1, false);

        canvas.save();
        canvas.translate(boundaryRectangle.left, boundaryRectangle.top);
        sl.draw(canvas);
        canvas.restore();
        /*

        String textString = shape.getTextString();
        //float centerX = (shape.getRight() + shape.getLeft()) / 2;
        float centerY = (shape.getTop() + shape.getBottom()) / 2;
        canvas.drawText(textString,shape.getLeft(),centerY, paint);
        */

    }
}
