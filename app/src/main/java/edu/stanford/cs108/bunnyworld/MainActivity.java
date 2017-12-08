package edu.stanford.cs108.bunnyworld;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static edu.stanford.cs108.bunnyworld.R.id.editorView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View v = (View) findViewById(R.id.startbutton);
        popupWindow(v);

    }

    @Override
    protected void onResume() {
        super.onResume();
        View v = (View) findViewById(R.id.startbutton);
        popupWindow(v);
    }

    public void pop(View view){
        popupWindow(view);
    }

    public void goEditor(View view) {
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    public void goGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void popupWindow(View v) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.welcomepage,null);


            final PopupWindow pw = new PopupWindow(layout, 520, 800, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, 0, 0);


            Button loadGameBtn = (Button) layout.findViewById(R.id.loadgame_button);
            Button createGameBtn = (Button) layout.findViewById(R.id.createnewgame_button);

            loadGameBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    goGame(v);
                    pw.dismiss();
                }
            });
            createGameBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    goEditor(v);
                    pw.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
