package edu.stanford.cs108.bunnyworld;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameActivity extends AppCompatActivity {
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //gameView =
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
                loadDatabase();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    SQLiteDatabase db;
    private void loadDatabase(){
        db = openOrCreateDatabase("BunnyWorld", MODE_PRIVATE, null);
        String query = "SELECT shapes FROM BunnyGames WHERE name = 'game1'"; // search all the information on shpaes
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            String msg = cursor.getString(0);
            System.out.println(msg);
        }
        System.out.println("shit");

    }

    public BunnyShape jsonToBunnyShape(String json){
        if(json.startsWith("error")){//这里可以做一下检测，如果不是json格式的就直接返回
            return null;
        }
        BunnyShape bunnyShape = new BunnyShape();//准备返回的pet对象  /*这里需要一个空构造函数*/
        try {
            JSONObject jsonObject=new JSONObject(json);//我们需要把json串看成一个大的对象
            JSONArray jsonArray=jsonObject.getJSONArray("bunnyShape");//这里获取的是装载有所有bunnyShape对象的数组
            JSONObject jsonbunnyShape = jsonArray.getJSONObject(0);//获取这个数组中第一个bunnyShape对象

            String shapeName = jsonbunnyShape.getString("shapeName");//获取pet对象的参数
            String type = jsonbunnyShape.getString("type");
            String right = jsonbunnyShape.getString("right");
            String top = jsonbunnyShape.getString("top");
            String bottom = jsonbunnyShape.getString("bottom");
            String left = jsonbunnyShape.getString("left");
            String selectScript = jsonbunnyShape.getString("selectScript");
            String moveable = jsonbunnyShape.getString("moveable");
            String visible = jsonbunnyShape.getString("visible");

            // This part can be used to set a bunnyShape object or return a String array to construct a object
            bunnyShape.setName(shapeName);
            bunnyShape.setTop(Integer.parseInt(type));
            bunnyShape.setLeft(Float.parseFloat(left));
            bunnyShape.setRight(Float.parseFloat(right));
            bunnyShape.setTop(Float.parseFloat(top));
            bunnyShape.setBottom(Float.parseFloat(bottom));
            bunnyShape.setSelectScript(selectScript);
            bunnyShape.setMoveable(Boolean.parseBoolean(moveable));
            bunnyShape.setVisiable(Boolean.parseBoolean(visible));   // Here the visiable is a spell mistake


        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("json To BunnyShape:"+ bunnyShape.toString());//打印出pet对象参数。
        return bunnyShape;
    }
}
