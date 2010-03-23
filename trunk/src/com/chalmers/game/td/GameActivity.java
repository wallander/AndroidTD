package com.chalmers.game.td;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;


public class GameActivity extends Activity {
    protected int _splashTime = 3000;
    protected Thread splashThread;
    private boolean nextActivityStarted;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        // Set the screen orientation to Portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        // thread for displaying the SplashScreen
        splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < _splashTime) {
                        sleep(100);
                        
                        waited += 100;
                        
                    }
                } catch(InterruptedException e) {
                    // do nothing
                } finally {
                	if (nextActivityStarted == false) {
             			nextActivityStarted = true;
             			startActivity(new Intent(GameActivity.this, MenuStart.class));
             			finish();
                	}
             		
                }
            }
        };
        splashThread.start();
    }
    
    /*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
        	
        	if (nextActivityStarted == false) {
        		nextActivityStarted = true;
        		splashThread = null;
        		startActivity(new Intent(GameActivity.this, MenuStart.class));
          		finish();
        	}
        	
        }
        return true;
    }
    */
}