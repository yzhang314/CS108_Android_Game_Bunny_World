package edu.stanford.cs108.bunnyworld;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class EditorActivity extends AppCompatActivity {
    BunnyShape selected;
    BunnyPage currentPage;
    Map<String, BunnyPage> pageMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        EditorView editorView = (EditorView) findViewById(R.id.editorView);
        editorView.loadInialPage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    View view;

    public void onCreateNewPage(View view) {
        popupWindow1(view);
        this.view = view;
    }

    public void changePage(View view) {
        EditorView editorView = (EditorView) findViewById(R.id.editorView);
        this.pageMap = editorView.pageMap;

        popupWindow3(view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        EditorView editorView = (EditorView) findViewById(R.id.editorView);

        currentPage = editorView.currentPage;
        selected = currentPage.selectedShape;
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.onClickGoTo:
                if (selected == null) {
                    Log.i("null", "null");
                } else {
                    selected.setSelectScript("GoTo");

                    popupWindow2(view);

                    Log.i(selected.getName(), selected.getSelectScript());
                }

                return true;
            case R.id.onClickPlaySound:

                return true;
            case R.id.onClickShow:

                return true;
            case R.id.onEnterGoTo:

                return true;
            case R.id.onEnterPlaySound:

                return true;
            case R.id.onEnterShow:

                return true;
            case R.id.onDropGoTo:

                return true;
            case R.id.onDropPlaySound:

                return true;
            case R.id.onDropShow:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void setString() {


    }

    protected void creatNewGame() {

    }

    protected void deletePage() {

    }

    protected void addShape (BunnyShape shape) {
        switch(shape.getType()) {
            case 0: break;


        }

    }
    private void popupWindow1(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window,null);

            //We need to get the instance of the LayoutInflater, use the context of this activity

            //Inflate the view from a predefined XML layout

            final PopupWindow pw = new PopupWindow(layout, 800, 350, true);
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
                    EditText editText = (EditText) layout.findViewById(R.id.createPageName_text);
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
                    selected.setSelectScript(selected.getSelectScript() + goToPage);
                    editText.setText("");
                    Log.i(selected.getName(), selected.getSelectScript());
                    //This is used to change page which can be used during game
                    /*
                    EditorView editorView = (EditorView) findViewById(R.id.editorView);
                    editorView.openPage(goToPage);
                    */
                    pw.dismiss();


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void popupWindow3(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.popup_window3,null);


            //We need to get the instance of the LayoutInflater, use the context of this activity

            //Inflate the view from a predefined XML layout

            final PopupWindow pw = new PopupWindow(layout, 800, 350, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);


            Button enterBtn = (Button) layout.findViewById(R.id.openPageEnter_button);
            Button cancelBtn = (Button) layout.findViewById(R.id.openPageCancel_button);
            Button showPagesBtn = (Button) layout.findViewById(R.id.showCurrent_button);

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
                    EditText editText = (EditText) layout.findViewById(R.id.openPageName_text);
                    String openPage = editText.getText().toString();

                    EditorView editorView = (EditorView) findViewById(R.id.editorView);
                    editorView.openPage(openPage);

                    pw.dismiss();

                }
            });

            showPagesBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    TextView showPageInfo = layout.findViewById(R.id.showPages_text);
                    String result = "";
                    for (String s: pageMap.keySet()) {
                        result = result + s + " ";
                    }
                    showPageInfo.setText(result);


                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
