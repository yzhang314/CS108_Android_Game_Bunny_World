package edu.stanford.cs108.bunnyworld;

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
    float downX, downY;
    boolean isSelected;
    boolean isDragging;

    BunnyPage currentPage;
    BunnyShape selectedShape;
    Map<String, BunnyPage> pageMap;
    Inventory inventory;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        isSelected = false;
        isDragging = false;
        dbHandler = new DbHandler();
        pageMap = new HashMap<>();
        loadPages();
        inventory = new Inventory(100, 800, 300, 500);
    }

    //just for test
    private void loadPages() {
        currentPage = new BunnyPage("start");
        currentPage.addShape(new BunnyShape("test", 10, 50, 10, 50, "aaa", false));
        currentPage.addShape(new BunnyShape("test2", 100, 200, 100, 200, "", true));
        pageMap.put("start", currentPage);
        BunnyPage nextPage = new BunnyPage("next");
        nextPage.addShape(new BunnyShape("test3", 20, 500, 40, 100, "", true));
        pageMap.put("next", nextPage);
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

    private void actionDown(MotionEvent event) {
        downX = event.getX();
        downY = event.getY();
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
            selectedShape.move(event.getX() - downX, event.getY() - downY);
            downX = event.getX();
            downY = event.getY();
            invalidate();
        }
    }

    private void actionUp(MotionEvent event) {
        float upX = event.getX();
        float upY = event.getY();
        if(selectedShape != null) currentPage.addShape(selectedShape);
        if(isSelected) {
            if(inventory.isInsideInventory(upX, upY)) {
                inventory.addShape(selectedShape);
                selectedShape.isInsideInventory = true;
            }
            else {
                if(isDragging && selectedShape.moveable) {
                    //BunnyShape targetShape = currentPage.selectShape(upX, upY);
                    String placeScript = selectedShape.getPlaceScript();
                    if (!placeScript.equals("")) {
                        handleScript(placeScript);
                    }
                }
                else if(!isDragging) {
                    String selectScript = selectedShape.getSelectedScript();
                    if(!selectScript.equals("")) {
                        //use CONSTANT VARIABLE later
                        handleScript(selectScript);
                    }
                }
                selectedShape.isInsideInventory = false;
            }
            invalidate();
            isDragging = false;
            isSelected = false;
            selectedShape = null;
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
