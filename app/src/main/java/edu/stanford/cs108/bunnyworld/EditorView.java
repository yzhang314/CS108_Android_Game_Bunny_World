package edu.stanford.cs108.bunnyworld;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
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

import static edu.stanford.cs108.bunnyworld.R.color.colorAccent;

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
    BunnyShape copyShape;
    int pageIndex = 1;
    BunnyShape selectedShape;
    String gameName = "";

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
                        mydrawText(shape);
                        break;
                    default:
                        ;
                }
            }


        initInventory();
        drawPageName();

        Paint p = new Paint();
        Random random1 = new Random();
        int ranColor1 = 0xff000000 | random1.nextInt(0x00ffffff);
        p.setColor(ranColor1);
        p.setStyle(Paint.Style.FILL);
        BitmapDrawable uparrow, downarrow;
        uparrow = (BitmapDrawable) getResources().getDrawable(R.drawable.uparrow);
        downarrow = (BitmapDrawable) getResources().getDrawable(R.drawable.downarrow);
        Bitmap uparrowMap = Bitmap.createScaledBitmap(uparrow.getBitmap(), 50, 50, true);
        Bitmap downarrowMap = Bitmap.createScaledBitmap(downarrow.getBitmap(), 50, 50, true);
        canvas.drawBitmap(uparrowMap, 1150f, 430f, null);
        canvas.drawBitmap(downarrowMap, 1150f, 500f, null);


//Bitmap fireMap = fireDrawble.getBitmap();
                //this.resizedFire = Bitmap.createScaledBitmap(fireMap, 200, 200, true);
//        canvas.drawRect(1150, 430.0f, 1200f, 490.0f, p);
//        canvas.drawRect(1150, 500.0f, 1200f, 550.0f, p);

    }

    public void loadInialPage() {
        page1 = new BunnyPage("Page1");
        currentPage = page1;
        pageMap.put("Page1", page1);
    }

    public void drawPageName() {
        TextPaint paint;
        paint = new TextPaint();
        // int ranColor = 0xff000000;
        paint.setTextSize(40);
        paint.setColor(Color.DKGRAY);
        String pageName = currentPage.getName();
        canvas.drawText(pageName, 10, 410 , paint);

    }

    public void copyShape() {
        if (selectedShape != null) {
            copyShape = new BunnyShape(selectedShape.getName(), selectedShape.getType(), selectedShape.getLeft() + 50, selectedShape.getRight() + 50, selectedShape.getTop(), selectedShape.getBottom(), "", true);
            copyShape.setImageString(selectedShape.getImageString());
            copyShape.setTextString(selectedShape.getTextString());
            copyShape.setSelectScript(selectedShape.getSelectScript());
        }
    }

    public void pasteShape() {

        if (copyShape != null) {
            BunnyShape current = new BunnyShape(copyShape.getName(), copyShape.getType(), copyShape.getLeft() + 50, copyShape.getRight() + 50, copyShape.getTop(), copyShape.getBottom(), "", true);
            current.setImageString(copyShape.getImageString());
            current.setTextString(copyShape.getTextString());
            current.setSelectScript(copyShape.getSelectScript());
            int index = currentPage.getShapes().size() + 1;
            current.setName("Shape" + index);
            currentPage.addShape(current);
            selectedShape = current;
            //copyShape = new BunnyShape(selectedShape.getName(), selectedShape.getType(), selectedShape.getLeft() + 50, selectedShape.getRight() + 50, selectedShape.getTop(), selectedShape.getBottom(), "", true);
            invalidate();
        }


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


    //to test some shapes lalaa
    public void init() {
//        page1.addShape(new BunnyShape("prototypeCarrot", 1, 100, 400, 100, 400, "aaa", false));
//        BunnyShape testText = new BunnyShape("test1", 2, 400, 700, 400, 700, "bbb", false);
//        testText.setTextString("lalallalall");
//        page1.addShape(testText);
    }

    public void drawImage(BunnyShape shape) {
        if (backupMap.containsKey(shape)) {
            RectF boundaryRectangle = new RectF(shape.getLeft(), shape.getTop(), shape.getRight(), shape.getBottom());
            BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(resourceMap.get(shape.getImageString()));
            Bitmap bm = bd.getBitmap();
            Paint paint = new Paint();
            paint.setAlpha(60);
            canvas.drawBitmap(bm, null, boundaryRectangle, paint);
        } else {
            RectF boundaryRectangle = new RectF(shape.getLeft(), shape.getTop(), shape.getRight(), shape.getBottom());
            BitmapDrawable test = (BitmapDrawable) getResources().getDrawable(resourceMap.get(shape.getImageString()));
            Bitmap testPicture = test.getBitmap();
            canvas.drawBitmap(testPicture, null, boundaryRectangle, null);

        }

    }

    public void mydrawText(BunnyShape shape) {

        if (backupMap.containsKey(shape)) {
            RectF boundaryRectangle = new RectF(shape.getLeft(), shape.getTop(), shape.getRight(), shape.getBottom());
            TextPaint paint;
            paint = new TextPaint();
            // int ranColor = 0xff000000;
            paint.setTextSize(50);
            paint.setColor(0xff000000);
/*
        Paint shapePaint;
        shapePaint = new Paint();
//        Random random1 = new Random();
//        int ranColor1 = 0xff000000 | random1.nextInt(0x00ffffff);
        int color = 0xff000000;
        shapePaint.setColor(color);
        shapePaint.setStyle(Paint.Style.FILL);
        */

            //canvas.drawRect(boundaryRectangle, shapePaint);

            StaticLayout sl = new StaticLayout("", paint, (int) boundaryRectangle.width(), Layout.Alignment.ALIGN_CENTER, 1, 1, false);

            canvas.save();
            canvas.translate(boundaryRectangle.left, boundaryRectangle.top);
            sl.draw(canvas);
            canvas.restore();

        } else {
            RectF boundaryRectangle = new RectF(shape.getLeft(), shape.getTop(), shape.getRight(), shape.getBottom());
            TextPaint paint;
            paint = new TextPaint();
            // int ranColor = 0xff000000;
            paint.setTextSize(50);
            paint.setColor(0xff000000);
/*
        Paint shapePaint;
        shapePaint = new Paint();
//        Random random1 = new Random();
//        int ranColor1 = 0xff000000 | random1.nextInt(0x00ffffff);
        int color = 0xff000000;
        shapePaint.setColor(color);
        shapePaint.setStyle(Paint.Style.FILL);
        */

            //canvas.drawRect(boundaryRectangle, shapePaint);

            StaticLayout sl = new StaticLayout(shape.getTextString(), paint, (int) boundaryRectangle.width(), Layout.Alignment.ALIGN_CENTER, 1, 1, false);

            canvas.save();
            canvas.translate(boundaryRectangle.left, boundaryRectangle.top);
            sl.draw(canvas);
            canvas.restore();
/*
        String textString = shape.getTextString();
        float centerX = (shape.getRight() + shape.getLeft()) / 2;
        float centerY = (shape.getTop() + shape.getBottom()) / 2;
        canvas.drawText(textString,shape.getLeft(), (shape.getTop() + shape.getBottom())/2 , paint);
        */


        }

    }

    private static int[] positionArray = new int[]{0, 200, 400, 600, 800};
    private static final int inventoryTop = 430;
    private BitmapDrawable carrotDrawable, duckDrawable, carrot2Drawble, deathDrawble, fireDrawble, mysticDrawble, uparrowDrawble,textDrawble;
    private Bitmap resizedCarrot, resizedDuck, resizedCarrot2, resizedDeath, resizedFire, resizedMystic, resizedUparrow,resizedText;


    public void loadTheDrawables() {

        this.carrotDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.carrot);
        this.duckDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.duck);
        this.carrot2Drawble = (BitmapDrawable) getResources().getDrawable(R.drawable.carrot2);
        this.deathDrawble = (BitmapDrawable) getResources().getDrawable(R.drawable.death);
        this.fireDrawble = (BitmapDrawable) getResources().getDrawable(R.drawable.fire);
        this.mysticDrawble = (BitmapDrawable) getResources().getDrawable(R.drawable.mystic);
        this.uparrowDrawble = (BitmapDrawable) getResources().getDrawable(R.drawable.uparrow);
        this.textDrawble = (BitmapDrawable) getResources().getDrawable(R.drawable.text);
        // insert a text png

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

        Bitmap mysticMap = mysticDrawble.getBitmap();
        this.resizedMystic = Bitmap.createScaledBitmap(mysticMap, 200, 200, true);

        Bitmap uparrowMap = uparrowDrawble.getBitmap();
        this.resizedUparrow = Bitmap.createScaledBitmap(uparrowMap, 200, 200, true);

        Bitmap textMap = textDrawble.getBitmap();
        this.resizedText = Bitmap.createScaledBitmap(textMap, 200, 200, true);





        resourceMap.put("carrot", R.drawable.carrot);
        resourceMap.put("duck", R.drawable.duck);
        resourceMap.put("carrot2", R.drawable.carrot2);
        resourceMap.put("death", R.drawable.death);
        resourceMap.put("fire", R.drawable.fire);
        resourceMap.put("mystic", R.drawable.mystic);
        resourceMap.put("uparrow", R.drawable.uparrow);
        resourceMap.put("text", R.drawable.uparrow);
        // insert text png

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
                /*
                canvas.drawBitmap(resizedCarrot, positionArray[4] + 120, inventoryTop, null);

                canvas.drawBitmap(resizedDuck, positionArray[3] + 90, inventoryTop, null);
                */

                canvas.drawBitmap(resizedText, positionArray[2] + 60, inventoryTop, null);

                canvas.drawBitmap(resizedUparrow, positionArray[1] + 30, inventoryTop, null);

                canvas.drawBitmap(resizedMystic, positionArray[0], inventoryTop, null);
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
        ((EditorActivity)getContext()).saveToUndo();
        judge = false;
        selectedShape = null;
        float downX = event.getX();
        float downY = event.getY();
        if(inventory.isInsideInventory(downX, downY)) {
            switch (inventoryControl) {
                case 1:
                    if (downX <= 200) {
                        BunnyShape prototypeCarrot = new BunnyShape("carrot", 1, 0, 200, inventoryTop - 100, inventoryTop + 100, "", true, true);
                        prototypeCarrot.setImageString("carrot");
                        selectedShape = prototypeCarrot;
                    } else if (downX > 230 && downX <= 430) {
                        BunnyShape prototypeDuck = new BunnyShape("duck", 1, 0, 200, inventoryTop, inventoryTop + 200, "", true, true);
                        prototypeDuck.setImageString("duck");
                        selectedShape = prototypeDuck;
                    } else if (downX > 460 && downX <= 660) {
                        BunnyShape prototypeCarrot2 = new BunnyShape("carrot2", 1, 0, 200, inventoryTop, inventoryTop + 200, "", true, true);
                        prototypeCarrot2.setImageString("carrot2");
                        selectedShape = prototypeCarrot2;
                    } else if (downX > 690 && downX <= 890) {
                        BunnyShape prototypeDeath = new BunnyShape("death", 1, 0, 200, inventoryTop, inventoryTop + 200, "", true, true);
                        prototypeDeath.setImageString("death");
                        selectedShape = prototypeDeath;

                    } else if (downX > 910 && downX <= 1100) {

                        BunnyShape prototypeFire = new BunnyShape("fire", 1, 0, 200, inventoryTop, inventoryTop + 200, "", true, true);
                        prototypeFire.setImageString("fire");
                        selectedShape = prototypeFire;

                    } else if (downX >= 1150 && downX <= 1200 && downY >= 440 && downY <= 490) {
                        inventoryControl = 1;
                        invalidate();
                    } else if (downX >= 1150 && downX <= 1200 && downY >= 500 && downY <= 550) {
                        inventoryControl = 2;
                        invalidate();
                    }

                    if (selectedShape != null) {
                        int index = currentPage.getShapes().size() + 1;
                        selectedShape.setName("Shape" + index);
                        currentPage.addShape(selectedShape);
                        currentPage.selectedShape = selectedShape;
                    }

                    break;
                case 2 :
                    if (downX <= 200) {
                        BunnyShape prototypeMystic = new BunnyShape("mystic", 1, 0, 200, inventoryTop, inventoryTop + 200, "", true, true);
                        prototypeMystic.setImageString("mystic");
                        selectedShape = prototypeMystic;
                    } else if (downX > 230 && downX <= 430) {
                        BunnyShape prototypeUpArrow = new BunnyShape("uparrow", 1, 0, 200, inventoryTop, inventoryTop + 200, "", true, true);
                        prototypeUpArrow.setImageString("uparrow");
                        selectedShape = prototypeUpArrow;
                    } else if (downX > 460 && downX <= 660) {
                        BunnyShape prototypetext = new BunnyShape("text", 2, 0, 200, inventoryTop, inventoryTop + 200, "", true, true);
                        prototypetext.setTextString("Enter you Text");
                        selectedShape = prototypetext;
                    } else if (downX > 690 && downX <= 890) {
                        /*
                        BunnyShape prototypeDuck = new BunnyShape("duck", 1, 0, 200, inventoryTop, inventoryTop + 200, "", true, true);
                        selectedShape = prototypeDuck;
                        */
                    } else if (downX > 910 && downX <= 1100) {
                        /*
                        BunnyShape prototypeCarrot = new BunnyShape("carrot", 1, 0, 200, inventoryTop - 100, inventoryTop + 100, "", true, true);
                        selectedShape = prototypeCarrot;
                        */
                    } else if (downX >= 1150 && downX <= 1200 && downY >= 440 && downY <= 490) {
                        inventoryControl = 1;
                        invalidate();
                    } else if (downX >= 1150 && downX <= 1200 && downY >= 500 && downY <= 550) {
                        inventoryControl = 2;
                        invalidate();
                    }
                    if (selectedShape != null) {
                        int index = currentPage.getShapes().size() + 1;
                        selectedShape.setName("Shape" + index);
                        currentPage.addShape(selectedShape);
                        currentPage.selectedShape = selectedShape;
                    }
                    break;
            }
        } else  {
            selectedShape = currentPage.selectShape(downX, downY);
            currentPage.selectedShape = selectedShape;
            judge = true;
            //((EditorActivity)getContext()).saveToUndo();
            invalidate();
        }

    }


    private void actionMove(MotionEvent event) {
        float upX = event.getX();
        float upY = event.getY();

        if(judge == true) {
            if (selectedShape != null) {
               // ((EditorActivity)getContext()).saveToUndo();

                float width = selectedShape.getRight() - selectedShape.getLeft();
                float height = selectedShape.getTop() - selectedShape.getBottom();

                selectedShape.setLeft(upX - width/2);
                selectedShape.setTop(upY + height/2);
                selectedShape.setBottom(selectedShape.getTop() - height);
                selectedShape.setRight(selectedShape.getLeft() + width);

                currentPage.selectedShape = selectedShape;



                //currentPage.addShape(selectedShape);
                //currentPage.selectedShape = selectedShape;


                //currentPage.addShape(selectedShape);
//                for (BunnyShape curtShape : currentPage.getShapes()) {
//                    Log.i(curtShape.getName(), curtShape.getSelectScript());
//                }
/*
                for (BunnyShape shapes: backupMap.keySet()) {
                    if (shapes == selectedShape) {
                        backupMap.put(selectedShape, backupMap.get(shapes));
                    }

                    if(backupMap.get(shapes) == selectedShape) {
                        backupMap.put(shapes, selectedShape);
                    }
                }
                */


                //currentPage.selectedShape = selectedShape;
                invalidate();
            }

        } else {
            return;
        }


    }



    private void actionUp(MotionEvent event) {
        float upX = event.getX();
        float upY = event.getY();
        ((EditorActivity)getContext()).saveToUndo();

        if (judge == false) {
            //((EditorActivity)getContext()).saveToUndo();
            if (selectedShape != null) {
                float width = selectedShape.getRight() - selectedShape.getLeft();
                float height = selectedShape.getTop() - selectedShape.getBottom();

                selectedShape.setLeft(upX - width/2);
                selectedShape.setTop(upY + height/2);
                selectedShape.setBottom(selectedShape.getTop() - height);
                selectedShape.setRight(selectedShape.getLeft() + width);

                currentPage.selectedShape = selectedShape;


                //currentPage.addShape(selectedShape);
                //currentPage.selectedShape = selectedShape;
                invalidate();
            }

        } else {
            return;


        }


    }

    Map<BunnyShape, BunnyShape> backupMap = new HashMap<>();

    private BunnyShape original, hidden;

    public void drawHidden(BunnyShape current) {

        if (current != null) {
            original = current;
            hidden = new BunnyShape(current.getName(), current.getType(), current.getLeft(), current.getRight(), current.getTop(), current.getBottom(), "", true);
            hidden.setImageString(current.getImageString());
            hidden.setTextString(current.getTextString());
            hidden.setSelectScript(current.getSelectScript());
            currentPage.addShape(hidden);
            selectedShape = hidden;
            currentPage.removeShape(current);
            currentPage.selectedShape = hidden;
            backupMap.put(hidden, original);
            invalidate();

        }


    }

    public void eraseHidden(BunnyShape current) {
        hidden = current;
        currentPage.removeShape(current);
        original = new BunnyShape(current.getName(), current.getType(), current.getLeft(), current.getRight(), current.getTop(), current.getBottom(), "", true);
        original.setImageString(current.getImageString());
        original.setTextString(current.getTextString());
        original.setSelectScript(current.getSelectScript());
        backupMap.put(hidden, original);
        currentPage.addShape(original);
        selectedShape = original;
        currentPage.selectedShape = selectedShape;

        invalidate();

    }



}
