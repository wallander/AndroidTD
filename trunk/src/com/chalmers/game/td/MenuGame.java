package com.chalmers.game.td;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
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
        Log.i("MenuGame","onCreate()");
        
        // Set the screen orientation to Portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        GameModel.initialize(this);
        
        // Bring up the progression route view
        progressMap = new ProgressionRoutePanel(this);
        setContentView(progressMap);        
    }
    
    public void startGamePanel() {
    	
    }
    
    /**
     * Method called upon application closure.
     */
    @Override
    public void onStop() {
    	super.onStop();
    	Log.i("MenuGame","onStop()");
    	
    	GamePanel.releaseSounds();
    	finish();
    }
    
    @Override
    public void onRestart() {
    	Log.i("MenuGame","onRestart()");
    	super.onRestart();
    }
    
    @Override
    public void onStart() {
    	Log.i("MenuGame","onStart()");
    	super.onStart();
    }
    
    @Override
    public void onResume() {
    	Log.i("MenuGame","onResume()");
    	super.onResume();
    
    }
    
    @Override
    public void onPause() {
    	Log.i("MenuGame","onPause()");
    	super.onPause();
    
    }
    
    @Override
    public void onDestroy() {
       	Log.i("MenuGame","onDestroy()");
    	super.onDestroy();
 
    }
}