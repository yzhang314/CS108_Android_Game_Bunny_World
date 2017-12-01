package edu.stanford.cs108.bunnyworld;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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
import android.widget.Toast;

public class EditorActivity extends AppCompatActivity {
    BunnyShape selected;
    BunnyPage currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    View view;

    public void onCreateNewPage(View view) {
        EditorView editorView = (EditorView) findViewById(R.id.editorView);
        editorView.createNewPage();
        this.view = view;
        //initiatePopupWindow(view);
//        currentPage = editorView.currentPage;
//        selected = editorView.selectedShape;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        EditorView editorView = (EditorView) findViewById(R.id.editorView);
//        editorView.createNewPage();
//        initiatePopupWindow(view);
        currentPage = editorView.currentPage;
        selected = currentPage.selectedShape;
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.onClickGoTo:
                if (selected == null) {
                    Log.i("null", "null");
                } else {
                    selected.setSelectScript("GoTo");
                    initiatePopupWindow(view);

                    //Log.i(selected.getName(), selected.getSelectScript());
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

    public void getPageToGo(View view) {


        EditText pageToGoEntered = (EditText) findViewById(R.id.page_text);
        String pageString = pageToGoEntered.getText().toString();


        Log.i("lalala", pageString);
    }


    public void setString() {
        EditorView editorView = (EditorView) findViewById(R.id.editorView);
        currentPage = editorView.currentPage;
        selected = currentPage.selectedShape;

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

    private void initiatePopupWindow(View v) {
        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            final View layout = inflater.inflate(R.layout.popup_window,
                    null);
            // create a 300px width and 470px height PopupWindow
            final PopupWindow pw = new PopupWindow(layout, 400, 400, true);

            // display the popup in the center

            pw.showAtLocation(v, Gravity.CENTER, 0, 0);
            Toast.makeText(getApplicationContext(), ((EditText) findViewById(R.id.page_text)).toString(), Toast.LENGTH_LONG).show();
            Button btn = (Button) findViewById(R.id.enter_button);
            btn.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.GINGERBREAD)
                public void onClick(View v) {
                    //EditText name = (EditText) findViewById(R.id.page_text);
                    Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
                }
            });



            layout.setOnTouchListener(new View.OnTouchListener() {
                @TargetApi(Build.VERSION_CODES.GINGERBREAD)
                public boolean onTouch(View view, MotionEvent event) {
                    pw.dismiss();
                    return true;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
