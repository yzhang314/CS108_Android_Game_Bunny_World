package edu.stanford.cs108.bunnyworld;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameView = (GameView) findViewById(R.id.gameview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gamemenu, menu);
        /*
        MenuItem item = menu.findItem(R.id.movableSwitch);
        item.setActionView(R.layout.movable_switch_layout);
        */
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.loaddb:

                loadNames();
                popupWindowLoadGame(gameView);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    SQLiteDatabase db;
//    private void loadDatabase(){
//        db = openOrCreateDatabase("BunnyWorld", MODE_PRIVATE, null);
//        String query = "SELECT shapes FROM BunnyGames WHERE name = 'game1'"; // search all the information on shpaes
//        Cursor cursor = db.rawQuery(query,null);
//        while (cursor.moveToNext()){
//            String msg = cursor.getString(0);
//            System.out.println(msg);
//        }
//        System.out.println("shit");
//
//    }
//
//    public BunnyShape jsonToBunnyShape(String json){
//        if(json.startsWith("error")){//这里可以做一下检测，如果不是json格式的就直接返回
//            return null;
//        }
//        BunnyShape bunnyShape = new BunnyShape();//准备返回的pet对象  /*这里需要一个空构造函数*/
//        try {
//            JSONObject jsonObject=new JSONObject(json);//我们需要把json串看成一个大的对象
//            JSONArray jsonArray=jsonObject.getJSONArray("bunnyShape");//这里获取的是装载有所有bunnyShape对象的数组
//            JSONObject jsonbunnyShape = jsonArray.getJSONObject(0);//获取这个数组中第一个bunnyShape对象
//
//            String shapeName = jsonbunnyShape.getString("shapeName");//获取pet对象的参数
//            String type = jsonbunnyShape.getString("type");
//            String right = jsonbunnyShape.getString("right");
//            String top = jsonbunnyShape.getString("top");
//            String bottom = jsonbunnyShape.getString("bottom");
//            String left = jsonbunnyShape.getString("left");
//            String selectScript = jsonbunnyShape.getString("selectScript");
//            String moveable = jsonbunnyShape.getString("moveable");
//            String visible = jsonbunnyShape.getString("visible");
//
//            // This part can be used to set a bunnyShape object or return a String array to construct a object
//            bunnyShape.setName(shapeName);
//            bunnyShape.setTop(Integer.parseInt(type));
//            bunnyShape.setLeft(Float.parseFloat(left));
//            bunnyShape.setRight(Float.parseFloat(right));
//            bunnyShape.setTop(Float.parseFloat(top));
//            bunnyShape.setBottom(Float.parseFloat(bottom));
//            bunnyShape.setSelectScript(selectScript);
//            bunnyShape.setMoveable(Boolean.parseBoolean(moveable));
//            bunnyShape.setVisiable(Boolean.parseBoolean(visible));   // Here the visiable is a spell mistake
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        System.out.println("json To BunnyShape:"+ bunnyShape.toString());//打印出pet对象参数。
//        return bunnyShape;
//    }
    SQLiteDatabase db;
    List<String>gameNames = new ArrayList<>();

    // to get the names
    private void loadNames() {
        db = openOrCreateDatabase("BunnyWorld", MODE_PRIVATE, null);
        // There still need some changes on hard code game1;
        //String query = "SELECT shapes FROM BunnyGames WHERE name = 'game2'"; // search all the information on shapes
        String query = "SELECT * FROM BunnyGames";
        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()){
            String name = cursor.getString(0);
            gameNames.add(name);           // This list contains all the game names;
        }
        System.out.println(gameNames);
    }

    private void loadDatabase(){
        db = openOrCreateDatabase("BunnyWorld", MODE_PRIVATE, null);
        // There still need some changes on hard code game1;
        //String query = "SELECT shapes FROM BunnyGames WHERE name = 'game2'"; // search all the information on shapes

        String selectedName = nameofGametoLoad;      // The player chooses the game name to play, "game1"should be replaced
        String query = "SELECT shapes FROM BunnyGames WHERE name = '" + selectedName + "'";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            String msg = cursor.getString(0);
            List<BunnyPage>list = jsonToPageList(msg);
            //System.out.println(list.size() + "!!!");
            gameView.loadPages(list);
            System.out.println(msg);
        }

    }

    public List<BunnyPage> jsonToPageList(String json){
        if (json.startsWith("error")){        //这里可以做一下检测，如果不是json格式的就直接返回
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(json);//我们需要把json串看成一个大的对象
            JSONArray jsonArray = jsonObject.getJSONArray("bunnyPages");//这里获取的是装载有所有bunnyPage对象的数组
            System.out.println("shit:" + jsonArray.toString());
            List<BunnyPage>list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++){
                list.add(jsonToBunnyPage(jsonArray.getJSONObject(i)));
            }
            return list;
        } catch (JSONException e){
            e.printStackTrace();
        }
        System.out.println("bullshti");
        return null;

//        BunnyPage startPage = new BunnyPage("Page1");
//        startPage.addShape(new BunnyShape("test", 0, 10, 50, 10, 50, "onClick goto next", false, true));
//        BunnyShape temp = new BunnyShape("gonext", 0, 10, 50, 200, 250, "onDrop test3 play hooray,onDrop test3 hide test3", true, true);
//        startPage.addShape(temp);
//        startPage.addShape(new BunnyShape("test2", 0, 100, 200, 100, 200, "onEnter play evillaugh", true, true));
//        BunnyPage nextPage = new BunnyPage("next");
//        nextPage.addShape(new BunnyShape("test3", 0, 20, 500, 40, 100, "", true, true));
//        nextPage.addShape(new BunnyShape("goback", 0, 110, 150, 200, 250, "onEnter play munch,onClick goto start", false, true));
//        List<BunnyPage>list = new ArrayList<>();
//        list.add(startPage);
//        list.add(nextPage);
//        return list;
    }

    public BunnyPage jsonToBunnyPage(JSONObject bunnyPageObject){
        BunnyPage bunnyPage = new BunnyPage();     //准备返回的bunnyPage对象  /*这里需要一个空构造函数*/
        System.out.println("shit2" + bunnyPageObject.toString());
        try {
            JSONObject jsonbunnyPage = bunnyPageObject;
            String pageName = jsonbunnyPage.getString("pageName"); //获取pet对象的参数
            String shapes = jsonbunnyPage.getString("shapes");
            System.out.println("shit3" + shapes.toString());
            // This part can be used to set a bunnyShape object or return a String array to construct a object
            bunnyPage.setName(pageName);
            JSONObject shapeList = new JSONObject(shapes); // shapes store a list of shape
            JSONArray shapeArray = shapeList.getJSONArray("bunnyPage");
            for (int j = 0; j < shapeArray.length(); j++){
                //System.out.println("shit4" + shapeArray.getJSONObject(j).toString());
                bunnyPage.addShape(jsonToBunnyShape(shapeArray.getJSONObject(j)));
            }
            return bunnyPage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("json To BunnyShape:"+ bunnyPage.toString());//打印出pet对象参数。
        return null;
    }

    public BunnyShape jsonToBunnyShape(JSONObject jsonbunnyShape){
        //BunnyShape bunnyShape = new BunnyShape();//准备返回的pet对象  /*这里需要一个空构造函数*/
        try {
            String shapeName = jsonbunnyShape.getString("shapeName");//获取pet对象的参数
            String type = jsonbunnyShape.getString("type");
            String right = jsonbunnyShape.getString("right");
            String top = jsonbunnyShape.getString("top");
            String bottom = jsonbunnyShape.getString("bottom");
            String left = jsonbunnyShape.getString("left");
            String selectScript = jsonbunnyShape.getString("selectScript");
            String moveable = jsonbunnyShape.getString("moveable");
            String visible = jsonbunnyShape.getString("visible");
            String textString = jsonbunnyShape.getString("textString");
            String imageString = jsonbunnyShape.getString("imageString");
            BunnyShape bunnyShape = new BunnyShape(shapeName, Integer.parseInt(type), Float.parseFloat(left)
            , Float.parseFloat(right), Float.parseFloat(top), Float.parseFloat(bottom), selectScript,
            Boolean.parseBoolean(moveable), Boolean.parseBoolean(visible));//准备返回的pet对象  /*这里需要一个空构造函数*/
            bunnyShape.setTextString(textString);
            bunnyShape.setImageString(imageString);

            // This part can be used to set a bunnyShape object or return a String array to construct a object
//            bunnyShape.setName(shapeName);
//            bunnyShape.setTop(Integer.parseInt(type));
//            bunnyShape.setLeft(Float.parseFloat(left));
//            bunnyShape.setRight(Float.parseFloat(right));
//            bunnyShape.setTop(Float.parseFloat(top));
//            bunnyShape.setBottom(Float.parseFloat(bottom));
//            bunnyShape.setSelectScript(selectScript);
//            bunnyShape.setMoveable(Boolean.parseBoolean(moveable));
//            bunnyShape.setVisiable(Boolean.parseBoolean(visible));   // Here the visiable is a spell mistake
//            bunnyShape.setTextString(textString);
//            bunnyShape.setImageString(imageString);
            return bunnyShape;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("json To BunnyShape:");//打印出pet对象参数。
        return null;
    }

    String nameofGametoLoad = "";

    // popup window to load the game
    private void popupWindowLoadGame(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window_loadgame,null);

            final RadioGroup pageloadGroup = (RadioGroup) layout.findViewById(R.id.pageRadioGroup1);
            RadioButton radioButton = (RadioButton)layout.findViewById(R.id.game1);
            int i = 0;
            while (i <= 5) {
                switch(i) {
                    case 0: radioButton = (RadioButton) layout.findViewById(R.id.game1);
                        if (gameNames.size() > i)
                        radioButton.setText(gameNames.get(i));

                        break;
                    case 1: radioButton = (RadioButton) layout.findViewById(R.id.game2);
                        if (gameNames.size() > i) {
                            radioButton.setText(gameNames.get(i));
                        }
                        break;
                    case 2: radioButton = (RadioButton) layout.findViewById(R.id.game3);
                        if (gameNames.size() > i) {
                            radioButton.setText(gameNames.get(i));
                        }
                        break;
                    case 3: radioButton = (RadioButton) layout.findViewById(R.id.game4);
                        if (gameNames.size() > i) {
                            radioButton.setText(gameNames.get(i));
                        }
                        break;
                    case 4: radioButton = (RadioButton) layout.findViewById(R.id.game5);
                        if (gameNames.size() > i) {
                            radioButton.setText(gameNames.get(i));
                        }
                        break;
                    case 5: radioButton = (RadioButton) layout.findViewById(R.id.game6);
                        if (gameNames.size() > i) {
                            radioButton.setText(gameNames.get(i));
                        }
                        break;
                    default:
                }
                i++;
            }



            final PopupWindow pw = new PopupWindow(layout, 600, 600, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);

            Button enterBtn = (Button) layout.findViewById(R.id.loadgame_enter_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.loadgame_cancel_button);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();
                }
            });

            enterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currentCheck = pageloadGroup.getCheckedRadioButtonId();
                    String soundString = "";

                    switch(currentCheck) {
                        case R.id.game1:
                            nameofGametoLoad = gameNames.get(0);
                            break;
                        case R.id.game2:
                            nameofGametoLoad = gameNames.get(1);
                            break;
                        case R.id.game3:
                            nameofGametoLoad = gameNames.get(2);
                            break;
                        case R.id.game4:
                            nameofGametoLoad = gameNames.get(3);
                            break;
                        case R.id.game5:
                            nameofGametoLoad = gameNames.get(4);
                            break;
                        case R.id.game6:
                            nameofGametoLoad = gameNames.get(5);
                            break;
                        default:

                    }
                    pw.dismiss();
                    loadDatabase();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
