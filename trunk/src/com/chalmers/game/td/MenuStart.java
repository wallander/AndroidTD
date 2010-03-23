package com.chalmers.game.td;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;


public class MenuStart extends Activity {
   
    protected int _splashTime = 10000;
    protected Thread splashThread;
    private boolean nextActivityStarted = false;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        
        
        // thread for displaying the SplashScreen
        /*
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
                    	startActivity(new Intent(MenuStart.this, Menu.class));
                    	finish();
                	}
                    
                }
            }
        };
        splashThread.start();
        */
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
        	
        	
        		splashThread = null;
        		startActivity(new Intent(MenuStart.this, Menu.class));
          		finish();
        
        	
        }
        return true;
    }
}