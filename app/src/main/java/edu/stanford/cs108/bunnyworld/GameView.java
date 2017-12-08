package edu.stanford.cs108.bunnyworld;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.view.View;

/**
 * Created by gongzibo on 11/20/17.
 */

import android.view.View;
import android.widget.*;
import java.util.*;
import android.util.*;
import android.graphics.*;
import android.content.Context;
import android.app.Activity;
import android.view.*;


/**
 * Created by gongzibo on 11/7/17.
 */

public class GameView extends View {
    private DbHandler dbHandler;
    private MediaPlayer mp;
    float downX, downY;
    float preX, preY;
    boolean isSelected;
    boolean isDragging;
    boolean ondrop;

    BunnyPage currentPage;
    BunnyShape selectedShape;
    BunnyShape ondropShape;
    BunnyShape preOndropShape;
    Map<String, BunnyPage> pageMap;
    Map<String, BitmapDrawable> drawableMap;
    Inventory inventory;

    Map<String, Integer> resMap;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        isSelected = false;
        isDragging = false;
        ondrop = false;
        dbHandler = new DbHandler();
        pageMap = new HashMap<>();
        resMap = new HashMap<>();
        drawableMap = new HashMap<>();
        loadPages2();
        loadRes();
        loadDrawable();
        inventory = new Inventory(0, 1500, 430, 630);
        onEnterActions();
    }

    //just for test
    public void loadPages2() {
        BunnyPage startPage = new BunnyPage("start");
        startPage.addShape(new BunnyShape("test", 0, 10, 50, 10, 50, "onClick goto next", false, true));
        BunnyShape temp = new BunnyShape("gonext", 0, 10, 50, 200, 250, "onDrop test3 play hooray,onDrop test3 hide test3", true, true);
        startPage.addShape(temp);
        startPage.addShape(new BunnyShape("test2", 0, 100, 200, 100, 200, "onEnter play evillaugh", true, true));
        pageMap.put("start", startPage);
        BunnyPage nextPage = new BunnyPage("next");
        nextPage.addShape(new BunnyShape("test3", 0, 20, 500, 40, 100, "", true, true));
        nextPage.addShape(new BunnyShape("goback", 0, 110, 150, 200, 250, "onEnter play munch,onClick goto start", false, true));
        pageMap.put("next", nextPage);
        currentPage = pageMap.get("start");
        //invalidate();

    }

    public void loadPages(List<BunnyPage>pages){
        if (pages != null){
            for (BunnyPage page : pages) {
                pageMap.put(page.getName(), page);
                if (page.getName().equals("Page1")) {
                    currentPage = page;
                }
                for(BunnyShape shape: page.getShapes()) {
                    String imageString = shape.getImageString();
                    if(drawableMap.containsKey(imageString)) {
                        shape.setShapeDrawable(drawableMap.get(imageString));
                    }
                }
            }
        }
        System.out.println("Print out if pages are loaded correctly");
        //loadPages();
        invalidate();
    }


    private void loadRes() {
        resMap.put("carrotcarrotcarrot", R.raw.carrotcarrotcarrot);
        resMap.put("evillaugh", R.raw.evillaugh);
        resMap.put("hooray", R.raw.hooray);
        resMap.put("munch", R.raw.munch);
        resMap.put("munching", R.raw.munching);
        resMap.put("woof", R.raw.woof);
    }

    private void loadDrawable () {
        drawableMap.put("carrot", (BitmapDrawable) getResources().getDrawable(R.drawable.carrot));
        drawableMap.put("duck", (BitmapDrawable) getResources().getDrawable(R.drawable.duck));
        drawableMap.put("carrot2", (BitmapDrawable) getResources().getDrawable(R.drawable.carrot2));
        drawableMap.put("death", (BitmapDrawable) getResources().getDrawable(R.drawable.death));
        drawableMap.put("fire", (BitmapDrawable) getResources().getDrawable(R.drawable.fire));
        drawableMap.put("mystic", (BitmapDrawable) getResources().getDrawable(R.drawable.mystic));
        drawableMap.put("uparrow", (BitmapDrawable) getResources().getDrawable(R.drawable.uparrow));
        drawableMap.put("text", (BitmapDrawable) getResources().getDrawable(R.drawable.text));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        currentPage.draw(canvas);
        inventory.draw(canvas);
        if(selectedShape != null) selectedShape.draw(canvas);
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

    private void onEnterActions() {
        List<BunnyShape> shapes = currentPage.getShapes();
        for(BunnyShape shape : shapes) {
            for(String script : shape.getSelectScript().split(",")) {
                if(script.split(" ")[0].equals("onEnter")) {
                    doAction(script.substring(script.indexOf(" ") + 1));
                }
            }
        }
    }

    private void actionDown(MotionEvent event) {
        downX = event.getX();
        downY = event.getY();
        preX = downX;
        preY = downY;
        if(inventory.isInsideInventory(downX, downY)) {
            selectedShape = inventory.selectShape(downX, downY);
        }
        else {
            selectedShape = currentPage.selectShape(downX, downY);
        }
        if(selectedShape != null) {
            if(selectedShape.isInsideInventory == true) {
                inventory.removeShape(selectedShape);
            }
            else {
                currentPage.removeShape(selectedShape);
            }
            isSelected = true;
        }
    }

    private void actionMove(MotionEvent event) {
        isDragging = true;
        if(isSelected && selectedShape.moveable) {
            selectedShape.move(event.getX() - preX, event.getY() - preY);
            ondropShape = currentPage.selectShape(event.getX(), event.getY());
            if(preOndropShape != null) {
                preOndropShape.setIsOnDrop(false);
            }
            if(ondropShape != null) {
                ondrop = checkOnDrop(selectedShape, ondropShape);
                ondropShape.setIsOnDrop(true);
                ondropShape.setIsOnDropValid(ondrop);
            }
            else {
                ondrop = false;
            }
            preX = event.getX();
            preY = event.getY();
            preOndropShape = ondropShape;
            invalidate();
        }
    }

    private boolean checkOnDrop(BunnyShape selectedShape, BunnyShape targetShape) {
        String selectScript = selectedShape.getSelectScript();
        if(!selectScript.equals("")) {
            for(String script : selectScript.split(",")) {
                if(script.split(" ")[0].equals("onDrop") && targetShape.getName().equals(script.split(" ")[1])) return true;
            }
        }
        return false;
    }

    private void actionUp(MotionEvent event) {
        float upX = event.getX();
        float upY = event.getY();
        if(isSelected) {
            if(inventory.isInsideInventory(upX, upY)) {
                if(selectedShape.getMoveable()) {
                    inventory.addShape(selectedShape);
                    selectedShape.isInsideInventory = true;
                }
                else {
                    currentPage.addShape(selectedShape);
                }
            }
            else {
                //on drop
                if(isDragging && selectedShape.moveable) {
                    if(ondropShape != null) {
                        if (ondrop) {
                            String selectedScript = selectedShape.getSelectedScript();
                            for (String script : selectedScript.split(",")) {
                                if (script.split(" ")[0].equals("onDrop") && script.split(" ")[1].equals(ondropShape.getName())) {
                                    doAction(script.substring(script.indexOf(" ", script.indexOf(" ") + 1) + 1));
                                }
                            }
                            selectedShape.setInsideInventory(false);
                            currentPage.addShape(selectedShape);
                        }
                        else {
                            if(selectedShape.isInsideInventory) {
                                inventory.addShape(selectedShape);
                            }
                            else currentPage.addShape(selectedShape);
                            selectedShape.move(downX - upX, downY - upY);
                        }
                    }
                    else {
                        currentPage.addShape(selectedShape);
                        selectedShape.setInsideInventory(false);
                    }
                }
                //on click
                else if(!isDragging) {
                    currentPage.addShape(selectedShape);
                    String selectedScript = selectedShape.getSelectedScript();
                    if(!selectedScript.equals("")) {
                        //use CONSTANT VARIABLE later
                        for(String script : selectedScript.split(",")) {
                            if(script.split(" ")[0].equals("onClick")) {
                                doAction(script.substring(script.indexOf(" ") + 1));
                            }
                        }
                    }
                }
                else {
                    currentPage.addShape(selectedShape);
                }
                //selectedShape.isInsideInventory = false;
            }
            invalidate();
        }
        isDragging = false;
        isSelected = false;
        ondrop = false;
        selectedShape = null;
        ondropShape = null;
        if(preOndropShape != null) preOndropShape.setIsOnDrop(false);
    }

    private void doAction(String script) {
        System.out.println(script);
        String[] scriptArray = script.split(" ");
        if(scriptArray.length != 2) return;
        String action = scriptArray[0];
        String target = scriptArray[1];
        BunnyShape shape;
        switch (action) {
            case "goto":
                if (pageMap.containsKey(target)) {
                    currentPage = pageMap.get(target);
                    onEnterActions();
                    invalidate();
                }
                break;
            case "play":
                if (resMap.containsKey(target)) {
                    mp=MediaPlayer.create(getContext(), resMap.get(target));
                    mp.start();
                }
                break;
            case "hide":
                shape = currentPage.getShape(target);
                if (shape != null) {
                    shape.setVisiable(false);
                    invalidate();
                }
                break;
            case "show":
                shape = currentPage.getShape(target);
                if (shape != null) {
                    shape.setVisiable(true);
                    invalidate();
                }
                break;
        }
    }

    // Handle the script!
    private void handleScript(String script) {
        if(script.equals("aaa")) {
            currentPage = pageMap.get("next");
            invalidate();
        }
    }
}
