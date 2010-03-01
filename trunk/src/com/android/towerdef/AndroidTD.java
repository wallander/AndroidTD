package com.android.towerdef;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Activity which will be used as main entry point for the application.
 * 
 * @author martin
 */
public class AndroidTD extends Activity {
    
    /**
     * Method called on application start.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new GamePanel(this));
    }
}