package edu.stanford.cs108.bunnyworld;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gongzibo on 11/19/17.
 */

// this an example

public class DbHandler {
    int test;
    DbHandler() {
        test = 1;
    }
    void savePageInfo() {

    }

    void loadPageInfo(int pageId) {

    }

    void saveCurrentStatus() {

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

            return bunnyShape;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("json To BunnyShape:");//打印出pet对象参数。
        return null;
    }


}
