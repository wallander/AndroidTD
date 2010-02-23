package com.android.towerdef;

import android.app.Activity;
import android.os.Bundle;

// GIT TEST
import android.widget.TextView;
////

public class AndroidTD extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // GIT TEST
        TextView tv = new TextView(this);
        tv.setText("git RULES!");
        setContentView(tv);
        ////
    }
}