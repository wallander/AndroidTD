package com.chalmers.game.td;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

/**
 * Activity which will be used as main entry point for the application.
 * 
 * @author Fredrik Persson
 * @author Jonas Andersson
 * @author Ahmed Chaban
 * @author Jonas Wallander
 * @author Disa Faith
 * @author Daniel Arvidsson
 */
public class MenuGame extends Activity {
    
	private ProgressionRoutePanel progressMap;
	
    /**
     * Method called on application start.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Set the screen orientation to Portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        GameModel.initialize(this);
        
        // Bring up the progression route view
        progressMap = new ProgressionRoutePanel(this);
        setContentView(progressMap);        
    }
    
    /**
     * Method called upon application closure.
     */
    @Override
    public void onStop() {
    	super.onStop();
    	//progressMap.stopThread();
    	GamePanel.releaseSounds();
    }
}