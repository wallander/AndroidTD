package com.chalmers.game.td;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

/**
 * Activity which will be used as main entry point for the application.
 * 
 * @author Jonas Andersson, Daniel Arvidsson, Ahmed Chaban, Disa Faith, Fredrik Persson, Jonas Wallander
 */
public class GameActivity extends Activity {
    
    /**
     * Method called on application start.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	
        super.onCreate(savedInstanceState);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new GamePanel(this));
    }
}