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
    Inventory inventoryNextPage;
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
        Paint p = new Paint();
        Random random1 = new Random();
        int ranColor1 = 0xff000000 | random1.nextInt(0x00ffffff);
        p.setColor(ranColor1);
        p.setStyle(Paint.Style.FILL);
        canvas.drawRect(1150, 430.0f, 1200f, 490.0f, p);
        canvas.drawRect(1150, 500.0f, 1200f, 550.0f, p);

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
    private BitmapDrawable carrotDrawable, duckDrawable, carrot2Drawble, deathDrawble, fireDrawble;
    private Bitmap resizedCarrot, resizedDuck, resizedCarrot2, resizedDeath, resizedFire;


    public void loadTheDrawables() {

        this.carrotDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.carrot);
        this.duckDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.duck);
        this.carrot2Drawble = (BitmapDrawable) getResources().getDrawable(R.drawable.carrot2);
        this.deathDrawble = (BitmapDrawable) getResources().getDrawable(R.drawable.death);
        this.fireDrawble = (BitmapDrawable) getResources().getDrawable(R.drawable.fire);

        Bitmap carrotBitMap = carrotDrawable.getBitmap();
        this.resizedCarrot =  Bitmap.createScaledBitmap(carrotBitMap, 200, 200, true);

        Bitmap duckMap = duckDrawable.getBitmap();
        this.resizedDuck = Bitmap.createScaledBitmap(duckMap, 200, 200, true);

        Bitmap carrot2Map = carrot2Drawble.getBitmap();
        this.resizedCarrot2 = Bitmap.createScaledBitmap(carrot2Map, 200, 200, true);

        Bitmap deathMap = deathDrawble.getBitmap();
        this.resizedDeath = Bitmap.createScaledBitmap(deathMap, 200, 200, true);

        Bitmap fireMap = fireDrawble.getBitmap();
        this.resizedFire = Bitmap.createScaledBitmap(fireMap, 200, 200, true);


        resourceMap.put("carrot", R.drawable.carrot);
        resourceMap.put("duck", R.drawable.duck);
        resourceMap.put("carrot2", R.drawable.carrot2);
        resourceMap.put("death", R.drawable.death);
        resourceMap.put("fire", R.drawable.fire);

    }

    int inventoryControl = 1;
    public void initInventory() {


        inventory = new Inventory(0, 1500, 430, 630);
        inventoryNextPage = new Inventory(0, 1500, 430, 630);


        switch (inventoryControl) {
            case 1: inventory.draw(canvas);
                canvas.drawBitmap(resizedCarrot, positionArray[0], inventoryTop, null);

                canvas.drawBitmap(resizedDuck, positionArray[1] + 30, inventoryTop, null);

                canvas.drawBitmap(resizedCarrot2, positionArray[2] + 60, inventoryTop, null);

                canvas.drawBitmap(resizedDeath, positionArray[3] + 90, inventoryTop, null);

                canvas.drawBitmap(resizedFire, positionArray[4] + 120, inventoryTop, null);
                break;
            case 2: inventoryNextPage.draw(canvas);
                canvas.drawBitmap(resizedCarrot, positionArray[4] + 120, inventoryTop, null);

                canvas.drawBitmap(resizedDuck, positionArray[3] + 90, inventoryTop, null);

                canvas.drawBitmap(resizedCarrot2, positionArray[2] + 60, inventoryTop, null);

                canvas.drawBitmap(resizedDeath, positionArray[1] + 30, inventoryTop, null);

                canvas.drawBitmap(resizedFire, positionArray[0], inventoryTop, null);
                break;
        }







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
                        BunnyShape prototypeCarrot = new BunnyShape("carrot", 1, 0, 200, inventoryTop - 100, inventoryTop + 100, "", true);
                        selectedShape = prototypeCarrot;
                    } else if (downX > 230 && downX <= 430) {
                        BunnyShape prototypeDuck = new BunnyShape("duck", 1, 0, 200, inventoryTop, inventoryTop + 200, "", true);
                        selectedShape = prototypeDuck;
                    } else if (downX > 460 && downX <= 660) {
                        BunnyShape prototypeCarrot2 = new BunnyShape("carrot2", 1, 0, 200, inventoryTop, inventoryTop + 200, "", true);
                        selectedShape = prototypeCarrot2;
                    } else if (downX > 690 && downX <= 890) {
                        BunnyShape prototypeDeath = new BunnyShape("death", 1, 0, 200, inventoryTop, inventoryTop + 200, "", true);
                        selectedShape = prototypeDeath;
                    } else if (downX > 910 && downX <= 1100) {
                        BunnyShape prototypeFire = new BunnyShape("fire", 1, 0, 200, inventoryTop, inventoryTop + 200, "", true);
                        selectedShape = prototypeFire;
                    } else if (downX >= 1150 && downX <= 1200 && downY >= 440 && downY <= 490) {
                        inventoryControl = 1;
                        invalidate();
                    } else if (downX >= 1150 && downX <= 1200 && downY >= 500 && downY <= 550) {
                        inventoryControl = 2;
                        invalidate();
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

    private BunnyShape backUp;

    public void drawHidden(BunnyShape current) {
        Paint shapePaint;
        shapePaint = new Paint();
        Random random1 = new Random();
        int ranColor1 = 0xff000000 | random1.nextInt(0x00ffffff);
        shapePaint.setColor(ranColor1);
        shapePaint.setStyle(Paint.Style.FILL);

        if (current != null) {
            backUp = current;
            currentPage.removeShape(current);
            BunnyShape newShape = new BunnyShape("test", 0, current.getLeft(), current.getRight(), current.getTop(), current.getBottom(), "", true);
            currentPage.addShape(newShape);
            selectedShape = newShape;
            currentPage.selectedShape = selectedShape;


        }

        invalidate();
    }

    public void eraseHidden(BunnyShape current) {
        currentPage.removeShape(current);
        selectedShape = backUp;
        currentPage.selectedShape = selectedShape;
        currentPage.addShape(backUp);
        invalidate();

    }



}
