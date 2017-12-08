package edu.stanford.cs108.bunnyworld;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static edu.stanford.cs108.bunnyworld.R.raw.carrotcarrotcarrot;
import static java.security.AccessController.getContext;

public class EditorActivity extends AppCompatActivity {
    BunnyShape selected;
    BunnyPage currentPage;
    Map<String, BunnyPage> pageMap = new HashMap<>();
    EditorView editorView;
    DbHandler dbHandler = new DbHandler();

    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        editorView = (EditorView) findViewById(R.id.editorView);
        editorView.loadInialPage();
        this.currentPage = editorView.currentPage;
        pageMap.put(currentPage.getName(), currentPage);
        this.view = editorView;
//        popupWindowCreateNewGame(view);

    }


    public void onCreateNewPage(View view) {
        //this.view = view;
        //EditorView editorView = (EditorView) findViewById(R.id.editorView);
        this.pageMap = editorView.pageMap;
        this.currentPage = editorView.currentPage;
        popupWindow1(view);
        //System.out.println("shit");
        //System.out.println(currentPage.getName());
        pageMap.put(currentPage.getName(), currentPage);
    }

    public void changePageName(View view) {
        this.pageMap = editorView.pageMap;
        this.currentPage = editorView.currentPage;
        popupWindow7(view);
    }

    public void changePage(View view) {
        //EditorView editorView = (EditorView) findViewById(R.id.editorView);
        this.pageMap = editorView.pageMap;
        this.currentPage = editorView.currentPage;
        popupWindow3(view);
    }

    public void deletePage(View view) {
        //EditorView editorView = (EditorView) findViewById(R.id.editorView);
        this.pageMap = editorView.pageMap;
        this.currentPage = editorView.currentPage;
        popupWindow4(view);

    }

    public void clearCurrentPage(View view) {
       // EditorView editorView = (EditorView) findViewById(R.id.editorView);
        this.pageMap = editorView.pageMap;
        this.currentPage = editorView.currentPage;
        //pageMap.remove(currentPage.getName());
        currentPage.removeAllShapes();
        //pageMap.put(currentPage.getName(),currentPage);
        editorView.openPage(currentPage.getName());
    }

    public void getScript() {
        popupWindow5(view);
    }

/*
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem movable = menu.findItem(R.id.movableSwitch);
        MenuItem hidden = menu.findItem(R.id.hiddenSwitch);
        if (selected != null) {
            if(selected.isMoveable()) {
                movable.setChecked(true);
            } else {
                movable.setChecked(false);
            }

            if (selected.isHidden()) {
                hidden.setChecked(true);
            } else {
                hidden.setChecked(false);
            }
        } else {
            movable.setChecked(false);
            hidden.setChecked(false);

        }

        return true;
    }
    */

    public void undo(View view) {
        this.pageMap = editorView.pageMap;
        this.currentPage = editorView.currentPage;
        loadUndoDatabase();
//        BunnyShape bunnyShape = this.currentPage.getShapes().get(0);
//        System.out.println("this.currentPage.getShapes" + this.currentPage.getShapes().size() + bunnyShape.getLeft());
        pageMap.put(currentPage.getName(),currentPage);
        editorView.pageMap = pageMap;
        //System.out.println("this.currentPage.getName()" + this.currentPage.getName());
        editorView.openPage(this.currentPage.getName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        /*
        MenuItem item = menu.findItem(R.id.movableSwitch);
        item.setActionView(R.layout.movable_switch_layout);
        */
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //EditorView editorView = (EditorView) findViewById(R.id.editorView);

        currentPage = editorView.currentPage;
        selected = currentPage.selectedShape;
        // Handle item selection

        switch (item.getItemId()) {

//            case R.id.createScript:
//                if (selected != null) {
//                    selected.setSelectScript(selected.tempScript);
//                }
//                return true;
            case R.id.showScript:
                getScript();
                //selected.setSelectScript(selected.tempScript);
                return true;

            case R.id.setProperty:
                if(selected == null) {

                } else {
                    //selected.setSelectScript(selected.tempScript);
                    popupWindow6(view);
                }

                return true;

            case R.id.changeText:
                if (selected.type == 2) {
                    popupWindowChangeText(view);
                }
                return true;

            case R.id.copyShape:
                if (selected != null) {
                    editorView.copyShape();
                }
                return true;
            case R.id.pasteShape:

                    editorView.pasteShape();

                return true;

            case R.id.resize:
                if (selected != null) {
                    popupWindowResize(view);
                }
                return true;
            case R.id.deleteShape:
                if (selected != null) {
                    currentPage.removeShape(selected);
                    editorView.openPage(currentPage.getName());

                }
                return true;

            case R.id.onClickGoTo:
                if (selected == null) {
                    Log.i("null", "null");
                } else {
                    //currentPage.removeShape(selected);
                    selected.setSelectScript(selected.getSelectedScript() + "onClick goto ");
                    //currentPage.addShape(selected);
                    popupWindow2(view);
                    Log.i(selected.getName(), selected.getSelectScript());
                }

                return true;
            case R.id.onClickPlaySound:

                if (selected == null) {
                    Log.i("null", "null");
                } else {
                    //currentPage.removeShape(selected);
                    selected.setSelectScript(selected.getSelectedScript() + "onClick play ");
                    //currentPage.addShape(selected);
                    popupWindowSound(view);
                    Log.i(selected.getName(), selected.getSelectScript());
                }
                return true;
            case R.id.onClickShow:

                if (selected == null) {
                    Log.i("null", "null");
                } else {
                    //currentPage.removeShape(selected);
                    selected.setSelectScript(selected.getSelectedScript() + "onClick show ");
                    //currentPage.addShape(selected);
                    popupWindowGetonlyObj(view);
                }
                return true;
            case R.id.onClickHide:
                if (selected != null) {
                    selected.setSelectScript(selected.getSelectedScript() + "onClick hide ");
                    popupWindowGetonlyObj(view);
                }
                return true;
            case R.id.onEnterGoTo:
                if (selected == null) {
                    Log.i("null", "null");
                } else {
                    //currentPage.removeShape(selected);
                    selected.setSelectScript(selected.getSelectedScript() + "onEnter goto");
                    //currentPage.addShape(selected);
                    popupWindow2(view);
                    Log.i(selected.getName(), selected.getSelectScript());
                }

                return true;
            case R.id.onEnterPlaySound:
                if (selected == null) {
                    Log.i("null", "null");
                } else {
                    currentPage.removeShape(selected);
                    selected.setSelectScript(selected.getSelectedScript() + "onEnter play ");
                    currentPage.addShape(selected);
                    popupWindowSound(view);
                    Log.i(selected.getName(), selected.getSelectScript());
                }

                return true;
            case R.id.onEnterShow:
                if (selected == null) {
                    Log.i("null", "null");
                } else {
                    //currentPage.removeShape(selected);
                    selected.setSelectScript(selected.getSelectedScript() + "onEnter show ");
                    popupWindowGetonlyObj(view);
                    //currentPage.addShape(selected);
                    Log.i(selected.getName(), selected.getSelectScript());
                }

                return true;
            case R.id.onEnterHide:
                if (selected != null) {
                    selected.setSelectScript(selected.getSelectedScript() + "onEnter hide ");
                    popupWindowGetonlyObj(view);
                }
                return true;
            case R.id.onDropGoTo:
                if (selected == null) {
                    Log.i("null", "null");
                } else {
                    //currentPage.removeShape(selected);
                    selected.setSelectScript(selected.getSelectedScript() + "onDrop ");
                    popupWindowGetObj(view);

                    //currentPage.addShape(selected);

                    Log.i(selected.getName(), selected.getSelectScript());
                }

                return true;
            case R.id.onDropPlaySound:
                if (selected == null) {
                    Log.i("null", "null");
                } else {
                    //currentPage.removeShape(selected);
                    selected.setSelectScript(selected.getSelectedScript() + "onDrop ");
                    popupWindowGetObj2(view);
                    //currentPage.addShape(selected);
                    //popupWindowSound(view);
                    Log.i(selected.getName(), selected.getSelectScript());
                }

                return true;
            case R.id.onDropShow:
                if (selected == null) {
                    Log.i("null", "null");
                } else {
                    //currentPage.removeShape(selected);
                    selected.setSelectScript(selected.getSelectedScript() + "onDrop ");
                    popupWindowGetObj3(view);
                    //currentPage.addShape(selected);
                    Log.i(selected.getName(), selected.getSelectScript());
                }

                return true;
            case R.id.onDropHide:
                if (selected != null) {
                    selected.setSelectScript(selected.getSelectedScript() + "onDrop ");
                    popupWindowGetObjhide1(view);

                }
                return true;
            case R.id.savetodb:
                popupWindowCreateNewGame(view);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void errorCheck() {
        if (selected != null) {

        }
    }



    protected void addShape (BunnyShape shape) {
        switch(shape.getType()) {
            case 0: break;


        }

    }
    //This is for create a new Page
    private void popupWindow1(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window,null);

            //We need to get the instance of the LayoutInflater, use the context of this activity

            //Inflate the view from a predefined XML layout
            final EditText editText = (EditText) layout.findViewById(R.id.createPageName_text);

            int currentIndex = pageMap.size() + 1;

            String defaultName = "Page" + currentIndex;
            editText.setText(defaultName);

            final PopupWindow pw = new PopupWindow(layout, 800, 300, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);

            Button enterBtn = (Button) layout.findViewById(R.id.createPageEnter_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.createPageCancel_button);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();
                }
            });


            enterBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    String newPageName = editText.getText().toString();
                    EditorView editorView = (EditorView) findViewById(R.id.editorView);
                    editorView.createNewPage(newPageName);
                    editText.setText("");
                    pw.dismiss();


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // This is for go to another page (create script for on click, on enter and on drop)
    private void popupWindow2(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window2,null);

            //We need to get the instance of the LayoutInflater, use the context of this activity

            //Inflate the view from a predefined XML layout

            final PopupWindow pw = new PopupWindow(layout, 800, 350, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);

            Button enterBtn = (Button) layout.findViewById(R.id.goToPageEnter_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.goToPageCancel_button);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();
                }
            });


            enterBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    EditText editText = (EditText) layout.findViewById(R.id.goToPage_text);
                    String goToPage = editText.getText().toString();

                        if (pageMap.keySet().contains(goToPage)) {
                            //selected.setSelectScript(selected.getSelectScript() + "onClickGoTo");
                            selected.setSelectScript(selected.getSelectScript() + goToPage + ",");
                            //selected.tempScript = selected.tempScript + selected.getSelectScript();
                            editText.setText("");
                            Log.i(selected.getName(), selected.getSelectScript());
                            //This is used to change page which can be used during game
                    /*
                    EditorView editorView = (EditorView) findViewById(R.id.editorView);
                    editorView.openPage(goToPage);
                    */
                            pw.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "Page does not exist", Toast.LENGTH_LONG).show();

                            selected.setSelectScript(selected.getSelectedScript().substring(0, selected.getSelectScript().length() - 13));
//                            if (selected.getSelectScript().length() != 0) {
//                            if (selected.getSelectedScript().charAt(selected.getSelectedScript().length() - 1) == '|') {
//                                selected.setSelectScript(selected.getSelectedScript().substring(0, selected.getSelectScript().length() - 1));
//                            }
//                            }
                            pw.dismiss();
                        }
                }


            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void popupWindowSound(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_windowsound,null);

            //We need to get the instance of the LayoutInflater, use the context of this activity

            //Inflate the view from a predefined XML layout
            final RadioGroup soundgroup = (RadioGroup) layout.findViewById(R.id.soundRadioGroup);
            RadioButton radioButton = (RadioButton)layout.findViewById(R.id.sound1);
            int i = 0;
            while (i <= 5) {
                switch(i) {
                    case 0: radioButton = (RadioButton) layout.findViewById(R.id.sound1);
                        radioButton.setText("carrotcarrotcarrot");

                        break;
                    case 1: radioButton = (RadioButton) layout.findViewById(R.id.sound2);
                        radioButton.setText("evillaugh");
                        break;
                    case 2: radioButton = (RadioButton) layout.findViewById(R.id.sound3);
                        radioButton.setText("hooray");
                        break;
                    case 3: radioButton = (RadioButton) layout.findViewById(R.id.sound4);
                        radioButton.setText("munch");
                        break;
                    case 4: radioButton = (RadioButton) layout.findViewById(R.id.sound5);
                        radioButton.setText("munching");
                        break;
                    case 5: radioButton = (RadioButton) layout.findViewById(R.id.sound6);
                        radioButton.setText("woof");
                        break;
                    default:
                }
                i++;
            }



            final PopupWindow pw = new PopupWindow(layout, 600, 600, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);

            Button enterBtn = (Button) layout.findViewById(R.id.playSoundEnter_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.playSoundCancel_button);
            Button playBtn = (Button) layout.findViewById(R.id.playSound_button);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();
                }
            });

            enterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currentCheck = soundgroup.getCheckedRadioButtonId();
                    String soundString = "";

                    switch(currentCheck) {
                        case R.id.sound1:
                            soundString = "carrotcarrotcarrot";
                            break;
                        case R.id.sound2:
                            soundString = "evillaugh";
                            break;
                        case R.id.sound3:
                            soundString = "hooray";
                            break;
                        case R.id.sound4:
                            soundString = "munch";
                            break;
                        case R.id.sound5:
                            soundString = "munching";
                            break;
                        case R.id.sound6:
                            soundString = "woof";
                            break;
                        default:

                    }
                    selected.setSelectScript(selected.getSelectScript() + soundString + ",");
                    pw.dismiss();
                }
            });


            playBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int currentCheck = soundgroup.getCheckedRadioButtonId();
                    MediaPlayer mp = MediaPlayer.create(EditorActivity.this , R.raw.carrotcarrotcarrot);
                    String soundString = "";

                    switch(currentCheck) {
                        case R.id.sound1:
                            mp = MediaPlayer.create(EditorActivity.this , R.raw.carrotcarrotcarrot);
                            soundString = "carrotcarrotcarrot";
                            mp.start();
                            break;
                        case R.id.sound2:
                            mp = MediaPlayer.create(EditorActivity.this , R.raw.evillaugh);
                            soundString = "evillaugh";
                            mp.start();
                            break;
                        case R.id.sound3:
                            mp = MediaPlayer.create(EditorActivity.this , R.raw.hooray);
                            soundString = "hooray";
                            mp.start();
                            break;
                        case R.id.sound4:
                            mp = MediaPlayer.create(EditorActivity.this , R.raw.munch);
                            soundString = "munch";
                            mp.start();
                            break;
                        case R.id.sound5:
                            mp = MediaPlayer.create(EditorActivity.this , R.raw.munching);
                            soundString = "munching";
                            mp.start();
                            break;
                        case R.id.sound6:
                            mp = MediaPlayer.create(EditorActivity.this , R.raw.woof);
                            soundString = "woof";
                            mp.start();
                            break;
                        default:

                    }




                    /*
                    EditText editText = (EditText) layout.findViewById(R.id.sound_text);
                    String soundString = editText.getText().toString();
                    selected.setSelectScript(selected.getSelectScript() + soundString + ",");
                    editText.setText("");
                    Log.i(selected.getName(), selected.getSelectScript());
                    */
                    //This is used to change page which can be used during game
                    /*
                    EditorView editorView = (EditorView) findViewById(R.id.editorView);
                    editorView.openPage(goToPage);
                    */


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // This is for changing the page
    private void popupWindow3(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window3,null);
            int i = 0;
            final RadioGroup group = (RadioGroup) layout.findViewById(R.id.pageRadioGroup);
            for (String s: pageMap.keySet()) {
                RadioButton testButton = (RadioButton) layout.findViewById(R.id.first);

                switch(i) {
                    case 0: testButton = (RadioButton) layout.findViewById(R.id.first);
                        if (s.equals(currentPage.getName())) {
                            group.check(R.id.first);
                        }

                        break;
                    case 1: testButton = (RadioButton) layout.findViewById(R.id.second);
                        if (s.equals(currentPage.getName())) {
                            group.check(R.id.second);
                        }

                        break;
                    case 2: testButton = (RadioButton) layout.findViewById(R.id.third); {
                        if (s.equals(currentPage.getName())) {
                            group.check(R.id.third);
                        }
                        break;
                    }
                    case 3: testButton = (RadioButton) layout.findViewById(R.id.fourth);
                        if (s.equals(currentPage.getName())) {
                            group.check(R.id.fourth);
                        }
                        break;
                    case 4: testButton = (RadioButton) layout.findViewById(R.id.fifth);
                        if (s.equals(currentPage.getName())) {
                            group.check(R.id.fifth);
                        }break;
                    case 5: testButton = (RadioButton) layout.findViewById(R.id.sixth);
                        if (s.equals(currentPage.getName())) {
                            group.check(R.id.sixth);
                        }break;
                    default:
                }

                testButton.setText(s);
                i++;
            }

            final PopupWindow pw = new PopupWindow(layout, 400, 650, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);


            Button enterBtn = (Button) layout.findViewById(R.id.changePageEnter_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.changePageCancel_button);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();
                }
            });


            enterBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int currentCheck = group.getCheckedRadioButtonId();

                    RadioButton currentcheckedBotton = (RadioButton) layout.findViewById(currentCheck);

                    String currentName = currentcheckedBotton.getText().toString();

                    //EditorView editorView = (EditorView) findViewById(R.id.editorView);
                    editorView.openPage(currentName);


                    pw.dismiss();

                }
            });




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //This is for deleting page
    private void popupWindow4(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window4,null);
            int i = 0;
            final RadioGroup group = (RadioGroup) layout.findViewById(R.id.pageRadioGroup1);

            for (String s: pageMap.keySet()) {
                RadioButton testButton = (RadioButton) layout.findViewById(R.id.first1);

                switch(i) {
                    case 0: testButton = (RadioButton) layout.findViewById(R.id.first1);
                        if (s.equals(currentPage.getName())) {
                            group.check(R.id.first1);
                        }
                        break;
                    case 1: testButton = (RadioButton) layout.findViewById(R.id.second1);
                        if (s.equals(currentPage.getName())) {
                            group.check(R.id.second1);
                        }
                        break;
                    case 2: testButton = (RadioButton) layout.findViewById(R.id.third1);
                        if (s.equals(currentPage.getName())) {
                            group.check(R.id.third1);
                        }
                        break;
                    case 3: testButton = (RadioButton) layout.findViewById(R.id.fourth1);
                        if (s.equals(currentPage.getName())) {
                            group.check(R.id.fourth1);
                        }
                        break;
                    case 4: testButton = (RadioButton) layout.findViewById(R.id.fifth1);
                        if (s.equals(currentPage.getName())) {
                            group.check(R.id.fifth1);
                        }
                        break;
                    case 5: testButton = (RadioButton) layout.findViewById(R.id.sixth1);
                        if (s.equals(currentPage.getName())) {
                            group.check(R.id.sixth1);
                        }
                        break;
                    default:
                }

                testButton.setText(s);
                i++;

            }


            final PopupWindow pw = new PopupWindow(layout, 400, 650, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);


            Button enterBtn = (Button) layout.findViewById(R.id.deletePageEnter_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.deletePageCancel_button);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();
                }
            });


            enterBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    int currentCheck = group.getCheckedRadioButtonId();

                    RadioButton currentcheckedBotton = (RadioButton) layout.findViewById(currentCheck);

                    String currentName = currentcheckedBotton.getText().toString();


                    if (pageMap.size() == 1) {
                        return;
                    }

                    if (currentName == "Page1") {
                        popupWindow9(view);
                        return;
                    }

                    //EditorView editorView = (EditorView) findViewById(R.id.editorView);


                    for(String s: pageMap.keySet()) {
                        if (s.equals(currentName)) {
                            editorView.pageMap.remove(s);
                            break;
                        }

                    }

                    if (currentName.equals(currentPage.getName())) {
                        editorView.openPage("Page1");

                    }



                    pw.dismiss();

                }
            });




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This is for showing and changing script of the selcted shape
    private void popupWindow5(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window5,null);
            EditText showScript = (EditText) layout.findViewById(R.id.showCurrentScript_text);

            if (selected == null) {
                return;

            }
            String script = selected.getSelectedScript();
            showScript.setText(script);





            final PopupWindow pw = new PopupWindow(layout, 800, 350, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);


            Button enterBtn = (Button) layout.findViewById(R.id.changeScript_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.changeScriptCancel_button);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();


                }
            });

            enterBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (selected != null) {
                        EditText showScript = (EditText) layout.findViewById(R.id.showCurrentScript_text);
                        String updatedScript = showScript.getText().toString();
                        String selectedName = selected.getName();
                        //currentPage.removeShape(selected);
                        selected.setSelectScript(updatedScript);
                        //currentPage.addShape(selected);

                        Log.i(selected.getName(),selected.getSelectScript());
                    }

                    pw.dismiss();

                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This is for changing the property of current shape
    private void popupWindow6(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window6,null);

            final Switch movableSwitch = (Switch) layout.findViewById(R.id.movable_switch);
            final Switch hiddenSwitch = (Switch) layout.findViewById(R.id.hidden_switch);
            if (selected == null) {
                return;
            }

            String previousName = selected.getName();


            EditText name = (EditText)layout.findViewById(R.id.showCurrentPageName_text);

            name.setText(previousName);

            EditText x1Text = (EditText)layout.findViewById(R.id.showX1_text);
            EditText x2Text = (EditText)layout.findViewById(R.id.showX2_text);
            EditText y1Text = (EditText)layout.findViewById(R.id.showY1_text);
            EditText y2Text = (EditText)layout.findViewById(R.id.showY2_text);

            y1Text.setText(selected.getTop() + "");
            y2Text.setText(selected.getBottom() + "");
            x1Text.setText(selected.getLeft() + "");
            x2Text.setText(selected.getRight() + "");


            movableSwitch.setChecked(selected.getMoveable());
            hiddenSwitch.setChecked(!selected.isVisiable());

            final PopupWindow pw = new PopupWindow(layout, 700, 900, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);


            Button enterBtn = (Button) layout.findViewById(R.id.changeProperty_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.cancelProperty_button);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    pw.dismiss();

                }
            });

            enterBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (selected != null) {
                        EditText name = (EditText)layout.findViewById(R.id.showCurrentPageName_text);
                        String currentName = name.getText().toString();
                        selected.setName(currentName);


                        EditText x1Text = (EditText)layout.findViewById(R.id.showX1_text);
                        EditText x2Text = (EditText)layout.findViewById(R.id.showX2_text);
                        EditText y1Text = (EditText)layout.findViewById(R.id.showY1_text);
                        EditText y2Text = (EditText)layout.findViewById(R.id.showY2_text);

                        float x1 = Float.parseFloat(x1Text.getText().toString());
                        selected.setLeft(x1);

                        float x2 = Float.parseFloat(x2Text.getText().toString());
                        selected.setRight(x2);

                        float y1 = Float.parseFloat(y1Text.getText().toString());
                        selected.setTop(y1);

                        float y2 = Float.parseFloat(y2Text.getText().toString());
                        selected.setBottom(y2);


                        selected.setMoveable(movableSwitch.isChecked());
                        selected.setVisiable(!hiddenSwitch.isChecked());


                        if (hiddenSwitch.isChecked()) {
                            editorView.drawHidden(selected);

                        } else {
                            editorView.eraseHidden(selected);
                        }

/*
                        currentPage = editorView.currentPage;
 */
                        selected = currentPage.selectedShape;


                        selected.setMoveable(movableSwitch.isChecked());
                        selected.setVisiable(!hiddenSwitch.isChecked());


                    }



                    pw.dismiss();

                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This is for renaming the current page
    public void popupWindow7(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window7,null);

            if (currentPage == null) {
              return;
            }



            if (currentPage.getName().equals("Page1")) {
                popupWindow8(view);
                return;
            }


            String previousName = currentPage.getName();


            EditText name = (EditText)layout.findViewById(R.id.showCurrentPageName_text);

            name.setText(previousName);


            final PopupWindow pw = new PopupWindow(layout, 800, 350, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);




            Button enterBtn = (Button) layout.findViewById(R.id.changePageName_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.cancelPageName_button);


            enterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String previousName = currentPage.getName();
                    EditText name = (EditText)layout.findViewById(R.id.showCurrentPageName_text) ;
                    String currentName = name.getText().toString();
                    currentPage.setName(currentName);

                    for(String s: pageMap.keySet()) {
                        if (s.equals(previousName)) {
                            pageMap.remove(s);
                            break;
                        }
                    }
                    pageMap.put(currentPage.getName(),currentPage);
                    editorView.openPage(currentName);

                    pw.dismiss();

                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();

                }
            });




        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    // This is a reminder for we can't change name of Page1
    public void popupWindow8(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window8,null);


            final PopupWindow pw = new PopupWindow(layout, 800, 220, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);



            Button enterBtn = (Button) layout.findViewById(R.id.OKforNoChange_button);


            enterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();

                }
            });




        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // This is a reminder for we can't delete Page1
    public void popupWindow9(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window9,null);



            final PopupWindow pw = new PopupWindow(layout, 800, 220, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);



            Button enterBtn = (Button) layout.findViewById(R.id.OKforNoChange_button);


            enterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();

                }
            });




        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //This is for change the text of a shape of text
    private void popupWindowChangeText(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window_changetext,null);
            final EditText showScript = (EditText) layout.findViewById(R.id.showtext_changetext);

            if (selected != null && selected.type == 2) {
                String script = selected.getTextString();

                showScript.setText(script);
            }


            final PopupWindow pw = new PopupWindow(layout, 800, 350, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);


            Button enterBtn = (Button) layout.findViewById(R.id.changeScript_changetext_btn);
            Button cancelBtn = (Button) layout.findViewById(R.id.changeScriptCancel_changetext_btn);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();


                }
            });

            enterBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (selected != null) {
                        String updatedScript = showScript.getText().toString();
                        String selectedName = selected.getName();
                        //currentPage.removeShape(selected);
                        selected.setTextString(updatedScript);
                        //currentPage.addShape(selected);

                        Log.i(selected.getName(),selected.getTextString());
                    }

                    pw.dismiss();
                    view.invalidate();

                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this is the function to pop up a window and change the size of the current shape
    private void popupWindowResize(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window_resize,null);
            final EditText heightEditText = (EditText) layout.findViewById(R.id.new_height);
            final EditText widthEditText = (EditText) layout.findViewById(R.id.new_width);




            final PopupWindow pw = new PopupWindow(layout, 800, 350, true);
            if (selected != null) {
                widthEditText.setText(selected.getRight() - selected.getLeft() + "");
                heightEditText.setText(selected.getBottom() - selected.getTop() + "");

            }
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);


            Button enterBtn = (Button) layout.findViewById(R.id.change_resize_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.cancel_resize_button);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();


                }
            });

            enterBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (selected != null) {
                        Float newheight = Float.parseFloat(heightEditText.getText().toString());
                        Float newwidth = Float.parseFloat(widthEditText.getText().toString());
                        selected.setRight(selected.getLeft() + newwidth);
                        selected.setBottom(selected.getTop() + newheight);
                    }

                    pw.dismiss();
                    view.invalidate();

                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    String newgamename = "";

    // This is the function to create a new game
    private void popupWindowCreateNewGame(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window_creategame,null);
            final EditText editText = (EditText) layout.findViewById(R.id.gamename_text);

            final PopupWindow pw = new PopupWindow(layout, 800, 350, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);


            Button enterBtn = (Button) layout.findViewById(R.id.gamename_enter_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.gamename_cancel_button);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();
                }
            });

            enterBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (selected != null) {
                        String updatedScript = editText.getText().toString();
                        newgamename = updatedScript;
                        editorView.gameName = updatedScript;
                        saveToDatabase();
                    }
                    pw.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void popupWindowSetShapeName(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window7,null);

            if (selected == null) {
                return;
            }

            String previousName = selected.getName();


            EditText name = (EditText)layout.findViewById(R.id.showCurrentPageName_text);

            name.setText(previousName);



            final PopupWindow pw = new PopupWindow(layout, 800, 350, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);




            Button enterBtn = (Button) layout.findViewById(R.id.changePageName_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.cancelPageName_button);


            enterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText name = (EditText)layout.findViewById(R.id.showCurrentPageName_text) ;
                    String currentName = name.getText().toString();
                    selected.setName(currentName);

                    /*
                    String previousName = currentPage.getName();
                    EditText name = (EditText)layout.findViewById(R.id.showCurrentPageName_text) ;
                    String currentName = name.getText().toString();
                    currentPage.setName(currentName);

                    for(String s: pageMap.keySet()) {
                        if (s.equals(previousName)) {
                            pageMap.remove(s);
                            break;
                        }
                    }
                    pageMap.put(currentPage.getName(),currentPage);
                    editorView.openPage(currentName);
                    */

                    pw.dismiss();

                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();

                }
            });




        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    String objname;


    //This is for create a new Page
    private void popupWindowGetObj(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window_getobjname,null);

            //We need to get the instance of the LayoutInflater, use the context of this activity

            //Inflate the view from a predefined XML layout
            final EditText editText = (EditText) layout.findViewById(R.id.getobjname_text);
            TextView textView = (TextView) layout.findViewById(R.id.objtext_textview);
            textView.setText("Please Enter the name of the shape to drop on");


            final PopupWindow pw = new PopupWindow(layout, 800, 350, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);

            Button enterBtn = (Button) layout.findViewById(R.id.getobjEnter_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.getobjCancel_button);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();
                }
            });


            enterBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    objname = editText.getText().toString();
                    //editText.setText("");
                    pw.dismiss();
                    selected.setSelectScript(selected.getSelectedScript() + objname + " ");
                    selected.setSelectScript(selected.getSelectedScript() + "goto ");
                    popupWindow2(view);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This is to select the object of motion to hide the other object
    private void popupWindowGetObjhide1(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window_getobjname,null);

            //We need to get the instance of the LayoutInflater, use the context of this activity

            //Inflate the view from a predefined XML

            TextView textView = (TextView) layout.findViewById(R.id.objtext_textview);
            textView.setText("Please Enter the name of the shape to drop on");
            final EditText editText = (EditText) layout.findViewById(R.id.getobjname_text);



            final PopupWindow pw = new PopupWindow(layout, 800, 350, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);

            Button enterBtn = (Button) layout.findViewById(R.id.getobjEnter_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.getobjCancel_button);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();
                }
            });


            enterBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    objname = editText.getText().toString();
                    //editText.setText("");
                    pw.dismiss();
                    selected.setSelectScript(selected.getSelectedScript() + objname + " ");
                    selected.setSelectScript(selected.getSelectedScript() + "hide ");
                    popupWindowGetonlyObj(view);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this is the popup window to get the shape name for the object to be operated to playsound
    private void popupWindowGetObj2(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window_getobjname,null);

            //We need to get the instance of the LayoutInflater, use the context of this activity

            //Inflate the view from a predefined XML layout
            final EditText editText = (EditText) layout.findViewById(R.id.getobjname_text);
            TextView textView = (TextView) layout.findViewById(R.id.objtext_textview);
            textView.setText("Please Enter the name of the shape to drop on");


            final PopupWindow pw = new PopupWindow(layout, 800, 350, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);

            Button enterBtn = (Button) layout.findViewById(R.id.getobjEnter_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.getobjCancel_button);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();
                }
            });


            enterBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    objname = editText.getText().toString();
                    //editText.setText("");
                    pw.dismiss();
                    selected.setSelectScript(selected.getSelectedScript() + objname + " ");
                    selected.setSelectScript(selected.getSelectedScript() + "play ");
                    popupWindowSound(view);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this is the popup window to get the name of the shape that to be shown
    private void popupWindowGetObj3(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window_getobjname,null);

            //We need to get the instance of the LayoutInflater, use the context of this activity

            //Inflate the view from a predefined XML layout
            TextView textView = (TextView) layout.findViewById(R.id.objtext_textview);
            textView.setText("Please Enter the name of the shape to drop on");
            final EditText editText = (EditText) layout.findViewById(R.id.getobjname_text);


            final PopupWindow pw = new PopupWindow(layout, 800, 350, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);

            Button enterBtn = (Button) layout.findViewById(R.id.getobjEnter_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.getobjCancel_button);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();
                }
            });


            enterBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    objname = editText.getText().toString();
                    //editText.setText("");
                    pw.dismiss();
                    selected.setSelectScript(selected.getSelectedScript() + objname + " ");
                    selected.setSelectScript(selected.getSelectedScript() + "show ");
                    popupWindowGetonlyObj(view);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this is the popup window to get the name of the object to operate
    private void popupWindowGetonlyObj(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window_getobjname,null);

            //We need to get the instance of the LayoutInflater, use the context of this activity

            //Inflate the view from a predefined XML layout
            final EditText editText = (EditText) layout.findViewById(R.id.getobjname_text);


            final PopupWindow pw = new PopupWindow(layout, 800, 350, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);

            Button enterBtn = (Button) layout.findViewById(R.id.getobjEnter_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.getobjCancel_button);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();
                }
            });


            enterBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    objname = editText.getText().toString();
                    //editText.setText("");
                    pw.dismiss();
                    selected.setSelectScript(selected.getSelectedScript() + objname + " ,");


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //This is for drop on
    private void popupWindowGetonlyObj1(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window_getobjname,null);

            //We need to get the instance of the LayoutInflater, use the context of this activity

            //Inflate the view from a predefined XML layout
            final EditText editText = (EditText) layout.findViewById(R.id.getobjname_text);
            TextView textView = (TextView) layout.findViewById(R.id.objtext_textview);
            textView.setText("Please Enter the name of the shape to drop on");



            final PopupWindow pw = new PopupWindow(layout, 800, 350, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);

            Button enterBtn = (Button) layout.findViewById(R.id.getobjEnter_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.getobjCancel_button);

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pw.dismiss();
                }
            });


            enterBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    objname = editText.getText().toString();
                    //editText.setText("");
                    pw.dismiss();
                    selected.setSelectScript(selected.getSelectedScript() + objname + " ");


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    SQLiteDatabase db;
    private void saveToDatabase(){
        db = openOrCreateDatabase("BunnyWorld", MODE_PRIVATE, null);
        setupDatabase();
        String ifExist = "select * from sqlite_master where type='table' and name = 'BunnyGames';";
        Cursor cursor = db.rawQuery(ifExist,null);
        if (cursor.getCount() == 0){
            setupDatabase();
        }
        saveInformation();
    }

    // Each record stores a game,not a page
    // Game name, all the information, id
    private void setupDatabase(){
        // update the pagemap

        String dropStr = "DROP TABLE IF EXISTS BunnyGames;";
        db.execSQL(dropStr);
        String setupStr = "CREATE TABLE BunnyGames ("
                + "name TEXT, shapes TEXT,"
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT"
                + ");";
        db.execSQL(setupStr);
    }

    private void saveInformation(){
        //System.out.print(editorView.pageMap.size());
        for (String s : editorView.pageMap.keySet()) {
            if (!this.pageMap.containsKey(s)) {
                this.pageMap.put(s, editorView.pageMap.get(s));
            }
        }
        String string = bunnyPageToJson(); // all the information in bunny pages
        String query = "SELECT * FROM BunnyGames WHERE name = '" + newgamename + "'";
        Cursor cursor = db.rawQuery(query,null);
        String dataString;
        if (cursor.moveToNext()){
            dataString = "UPDATE BunnyGames SET shapes = '" + string +"' WHERE name = '" + newgamename +"';";
        } else {
            dataString = "REPLACE INTO BunnyGames VALUES " + "('" + newgamename+ "'" + ",'" + string + "',NULL);";
        }
//        UPDATE COMPANY SET ADDRESS = 'Texas' WHERE ID = 6;
//        INSERT OR REPLACE INTO Employee (id, name, role)
//        VALUES (1, 'John Foo', 'CEO');
//        String newDataString = "UPDATE BunnyGames SET shapes = '" + string +"' WHERE name = '" + newgamename +"';";
//        System.out.println(newDataString);
//        System.out.println(newgamename);
//        String finalDataStr = "INSERT OR REPLACE INTO BunnyGames (name, shapes) VALUES ('" + newgamename + "', '" + string + "');";
//        String dataString = "REPLACE INTO BunnyGames VALUES " +
//                "('" + newgamename+ "'" + ",'" +
//                string + "',NULL);";
//        System.out.println(dataString);
        db.execSQL(dataString);

    }

    // change a list of pages to json
    private String bunnyPageToJson() {
        String bunnyPageStr = "";
        System.out.println(pageMap.keySet().size());
        JSONObject object = new JSONObject();          //创建一个总的对象，这个对象对整个json串
        try {
            JSONArray jsonarray = new JSONArray();     //json数组，里面包含的内容为bunnyPage的所有对象
            for (String key : pageMap.keySet()){
                BunnyPage bunnyPage = pageMap.get(key);
                JSONObject bunnyPageObj = new JSONObject();//bunnyPage对象，json形式

                bunnyPageObj.put("pageName", bunnyPage.getName());
                bunnyPageObj.put("shapes", bunnyShapeToJson(bunnyPage.getShapes()));

                jsonarray.put(bunnyPageObj);              //向json数组里面添加bunnyPage对象
            }
            object.put("bunnyPages", jsonarray);       //向总对象里面添加包含bunnyPage的数组
            bunnyPageStr = object.toString();         //生成返回字符串
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.i("生成的json串为:"+bunnyPageStr,"");
        return bunnyPageStr;
    }

    // change a list of bunnyShapes to json
    private String bunnyShapeToJson(List<BunnyShape> shapes){
        String bunnyShapeStr = "";//定义返回字符串
        JSONObject object = new JSONObject();//创建一个总的对象，这个对象对整个json串
        try {
            JSONArray jsonarray = new JSONArray();//json数组，里面包含的内容为bunnyPage的所有对象

            for (BunnyShape bunnyShape : shapes){
                JSONObject bunnyShapeObj = new JSONObject();//bunnyPage对象，json形式

                bunnyShapeObj.put("shapeName", bunnyShape.getName());
                bunnyShapeObj.put("type", bunnyShape.getType());
                bunnyShapeObj.put("left", bunnyShape.getLeft());
                bunnyShapeObj.put("right", bunnyShape.getRight());
                bunnyShapeObj.put("top", bunnyShape.getTop());
                bunnyShapeObj.put("bottom", bunnyShape.getBottom());
                bunnyShapeObj.put("selectScript", bunnyShape.getSelectScript());
                bunnyShapeObj.put("moveable", bunnyShape.getMoveable());   //
                bunnyShapeObj.put("visible", bunnyShape.isVisiable());         //
                bunnyShapeObj.put("textString",bunnyShape.getTextString());
                bunnyShapeObj.put("imageString",bunnyShape.getImageString());

                jsonarray.put(bunnyShapeObj);      //向json数组里面添加bunnyPage对象
            }
            object.put("bunnyPage", jsonarray);//向总对象里面添加包含bunnyPage的数组
            bunnyShapeStr = object.toString(); //生成返回字符串
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //
        // Log.i("生成的json串为:"+bunnyShapeStr,"");
        return bunnyShapeStr;
    }



    /**This part is for undo!*/
    void saveToUndo(){
        db = openOrCreateDatabase("BunnyWorld", MODE_PRIVATE, null);
        String ifExist = "select * from sqlite_master where type='table' and name = 'Undo';";
        Cursor cursor = db.rawQuery(ifExist,null);
        if (cursor.getCount() == 0){
            setupUndoDatabase();
        }
        saveUndoInformation();
    }

    private void setupUndoDatabase(){
        String dropStr = "DROP TABLE IF EXISTS Undo;";
        db.execSQL(dropStr);
        String setupStr = "CREATE TABLE Undo ("
                + "name TEXT, shapes TEXT,"
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT"
                + ");";
        db.execSQL(setupStr);
    }

    private void saveUndoInformation(){
        String current = currentBunnyPageToJson();          // all the information in bunny pages
        String query = "SELECT shapes FROM Undo WHERE name = 'current'";   // actually only one record in db!
        //String query2 = "SELECT * FROM Undo WHERE name = 'past'";
        Cursor cursor = db.rawQuery(query,null);
        //Cursor cursor2 = db.rawQuery(query2,null);
        System.out.println("string: " + current);

        String past = "";
        String dataString;
        String dataString2;
        if (cursor.moveToNext()){   // exist current
            past = cursor.getString(0);  // get current
            System.out.println("past:" + past);
            dataString = "UPDATE Undo SET shapes = '" + past +"' WHERE name = 'past';"; // set current to past
            dataString2 = "UPDATE Undo SET shapes = '" + current +"' WHERE name = 'current';";   // update current
        } else {
            dataString = "INSERT INTO Undo VALUES " + "('past'" + ",'" + current + "',NULL);";
            dataString2 = "INSERT INTO Undo VALUES " + "('current'" + ",'" + current + "',NULL);";
        }
//        System.out.println("dataString: " + dataString);
//        System.out.println("dataString2: " + dataString2);
        db.execSQL(dataString);
        db.execSQL(dataString2);
    }


    private String currentBunnyPageToJson(){
        String bunnyPageStr = "";
        try {
            BunnyPage bunnyPage = currentPage;
            List<BunnyShape>shapes = currentPage.getShapes();
            if (shapes.size() != 0){
                BunnyShape shape = shapes.get(0);
                System.out.println("Shape position:" + shape.getLeft());
            }

            JSONObject bunnyPageObj = new JSONObject();     //bunnyPage对象，json形式

            bunnyPageObj.put("pageName", bunnyPage.getName());
            bunnyPageObj.put("shapes", bunnyShapeToJson(bunnyPage.getShapes()));

            bunnyPageStr = bunnyPageObj.toString();         //生成返回字符串
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bunnyPageStr;
    }

    /** This part is for to undo*/
    private void loadUndoDatabase(){
        db = openOrCreateDatabase("BunnyWorld", MODE_PRIVATE, null);
        String query = "SELECT shapes FROM Undo WHERE name = 'current'";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToNext()){
            String msg = cursor.getString(0);
            System.out.println(msg);
            this.currentPage = jsonToBunnyPage(msg);
        }
        saveUndoInformation();
    }

    public BunnyPage jsonToBunnyPage(String json){
        if (json.startsWith("error")){        //这里可以做一下检测，如果不是json格式的就直接返回
            return null;
        }
        BunnyPage bunnyPage = new BunnyPage();     //准备返回的bunnyPage对象  /*这里需要一个空构造函数*/
        try {
            JSONObject shapeList = new JSONObject(json); // shapes store a list of shape
            String name = shapeList.getString("pageName");
            String shapes = shapeList.getString("shapes");
            //System.out.println("shapes: " + shapes);
            JSONObject shape = new JSONObject(shapes);

            JSONArray shapeArray = shape.getJSONArray("bunnyPage");
            for (int j = 0; j < shapeArray.length(); j++){
                bunnyPage.addShape(dbHandler.jsonToBunnyShape(shapeArray.getJSONObject(j)));
            }
            bunnyPage.setName(name);
            //System.out.println("name: " + name);
            return bunnyPage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("json To BunnyShape:"+ bunnyPage.toString());
        return null;
    }
}

