package com.chalmers.game.td;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class MenuHelp extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);      
        
        // Set the screen orientation to Portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
              
    }
}