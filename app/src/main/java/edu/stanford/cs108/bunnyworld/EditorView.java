package edu.stanford.cs108.bunnyworld;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
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
    Map<String, Integer> resourceMap = new HashMap<>();
    Canvas canvas;
    Inventory inventory;
    Map<String, BunnyPage> pageMap = new HashMap<>();
    BunnyPage page1;
    BunnyPage currentPage;
    int pageIndex = 1;
    BunnyShape selectedShape;

    public EditorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        loadInialPage();

       // init();
        loadTheDrawables();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*
        BunnyPage current = new BunnyPage("lalala");
        super.onDraw(canvas);
        current.draw(canvas);
        */


        this.canvas = canvas;

        shapeList = currentPage.getShapes();
        Paint selectPaint = new Paint();


        for (BunnyShape shape : shapeList) {

            if (shape == currentPage.selectedShape) {

                selectPaint.setColor(Color.BLUE);
                selectPaint.setStyle(Paint.Style.STROKE);
                selectPaint.setStrokeWidth(15.0f);

                RectF boundaryRectangle = new RectF(shape.getLeft(), shape.getTop(), shape.getRight(), shape.getBottom());
                canvas.drawRect(boundaryRectangle, selectPaint);

            }

                int type = shape.getType();
                switch (type) {
                    case 0:
                        shape.draw(canvas);
                        break;
                    case 1:
                        drawImage(shape);
                        break;
                    case 2:
                        drawText(shape);
                        break;
                    default:
                        ;
                }
            }


        initInventory();

    }

    public void loadInialPage() {
        page1 = new BunnyPage("Page1");
        currentPage = page1;
        pageMap.put("Page1", page1);
    }


    public void createNewPage(String pageName) {
        BunnyPage newPage = new BunnyPage(pageName);
        pageMap.put(pageName, newPage);
        currentPage = newPage;
        invalidate();

    }

    public void openPage(String pageName) {
        if (pageMap.containsKey(pageName)) {
            currentPage = pageMap.get(pageName);
            invalidate();
        }

    }

    public void deletePage() {

    }


    //to test some shapes lalaa
    public void init() {
//        page1.addShape(new BunnyShape("prototypeCarrot", 1, 100, 400, 100, 400, "aaa", false));
//        BunnyShape testText = new BunnyShape("test1", 2, 400, 700, 400, 700, "bbb", false);
//        testText.setTextString("lalallalall");
//        page1.addShape(testText);
    }

    public void drawImage(BunnyShape shape) {
        RectF boundaryRectangle = new RectF(shape.getLeft(), shape.getTop(), shape.getRight(), shape.getBottom());
        BitmapDrawable test = (BitmapDrawable) getResources().getDrawable(resourceMap.get(shape.getName()));
        Bitmap testPicture = test.getBitmap();
        canvas.drawBitmap(testPicture, null, boundaryRectangle, null);
    }

    public void drawText(BunnyShape shape) {
        RectF boundaryRectangle = new RectF(shape.getLeft(), shape.getTop(), shape.getRight(), shape.getBottom());

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

        StaticLayout sl = new StaticLayout(shape.getTextString(), paint, (int) boundaryRectangle.width(), Layout.Alignment.ALIGN_CENTER, 1, 1, false);

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

    private static int[] positionArray = new int[]{0, 200, 400, 600, 800};
    private static final int inventoryTop = 430;
    private BitmapDrawable carrotDrawable, duckDrawable;
    private Bitmap carrotBitMap, resizedDuck;
    protected Paint myPaint;

    public void loadTheDrawables() {

        this.carrotDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.carrot);
        this.duckDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.duck);
        this.carrotBitMap = carrotDrawable.getBitmap();
        Bitmap duckMap = duckDrawable.getBitmap();
        this.resizedDuck = Bitmap.createScaledBitmap(duckMap, 200, 200, true);
        resourceMap.put("prototypeCarrot", R.drawable.carrot);
        resourceMap.put("prototypeDuck", R.drawable.duck);
        myPaint = new Paint();
        myPaint.setColor(Color.rgb(140,21,21));
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(5.0f);
    }

    public void initInventory() {
        inventory = new Inventory(0, 1500, 430, 630);
        inventory.draw(canvas);

        canvas.drawBitmap(carrotBitMap, positionArray[0], inventoryTop, null);

        canvas.drawBitmap(resizedDuck, positionArray[1], inventoryTop, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                actionDown(event);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                actionMove(event);
                break;
            }
            case MotionEvent.ACTION_UP: {
                actionUp(event);
                break;
            }

        }

        return true;

    }

     boolean judge;

    private void actionDown(MotionEvent event) {
        judge = false;
        selectedShape = null;
        float downX = event.getX();
        float downY = event.getY();
        if(inventory.isInsideInventory(downX, downY)) {
                    if (downX <= 200) {
                        BunnyShape prototypeCarrot = new BunnyShape("prototypeCarrot", 1, 0, 200, inventoryTop - 100, inventoryTop + 100, "", true);
                        selectedShape = prototypeCarrot;
                    } else if (downX > 200 && downX <= 400) {
                        BunnyShape prototypeDuck = new BunnyShape("prototypeDuck", 1, 0, 200, inventoryTop, inventoryTop + 200, "", true);
                        selectedShape = prototypeDuck;
                    }
        } else  {
            selectedShape = currentPage.selectShape(downX, downY);
            currentPage.selectedShape = selectedShape;
            judge = true;
            invalidate();
        }

    }


    private void actionMove(MotionEvent event) {
        float upX = event.getX();
        float upY = event.getY();

        if(judge == true) {
            if (selectedShape != null) {

                selectedShape.setLeft(upX - 100);
                selectedShape.setTop(upY - 100);
                selectedShape.setBottom(selectedShape.getTop() + 200);
                selectedShape.setRight(selectedShape.getLeft() + 200);

                currentPage.addShape(selectedShape);
                currentPage.selectedShape = selectedShape;
                invalidate();
            }

        } else {
            return;
        }


    }



    private void actionUp(MotionEvent event) {
        float upX = event.getX();
        float upY = event.getY();

        if (judge == false) {
            if (selectedShape != null) {

                selectedShape.setLeft(upX - 100);
                selectedShape.setTop(upY - 100);
                selectedShape.setBottom(selectedShape.getTop() + 200);
                selectedShape.setRight(selectedShape.getLeft() + 200);

                currentPage.addShape(selectedShape);
                currentPage.selectedShape = selectedShape;
                invalidate();
            }

        } else {
            return;
        }

    }



}
