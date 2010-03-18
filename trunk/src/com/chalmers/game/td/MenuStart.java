package com.chalmers.game.td;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;


public class MenuStart extends Activity {
    protected boolean _active = true;
    protected int _splashTime = 10000;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        
        // thread for displaying the SplashScreen
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if(_active) {
                            waited += 100;
                        }
                    }
                } catch(InterruptedException e) {
                    // do nothing
                } finally {
                    if (_active == true) {
                    	startActivity(new Intent(MenuStart.this, Menu.class));
                    	finish();
                    }
                }
            }
        };
        splashTread.start();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
        	if (_active == true) {
        		_active = false;
        		startActivity(new Intent(MenuStart.this, Menu.class));
          		finish();
          		
        	}
        }
        return true;
    }
}