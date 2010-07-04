package com.chalmers.game.td;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;


public class SplashActivity extends Activity {
    protected int mSplashTime = 3000;
    protected Thread mSplashThread;
    private boolean mNextActivityStarted;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.i("GameActivity", "onCreate()");
        
        setContentView(R.layout.splash);

        // Set the screen orientation to Portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        // thread for displaying the SplashScreen
        mSplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < mSplashTime) {
                        sleep(100);
                        waited += 100;
                    }
                } catch(InterruptedException e) {
                    // do nothing
                } finally {
                	if (mNextActivityStarted == false) {
             			mNextActivityStarted = true;
             			startActivity(new Intent(SplashActivity.this, Menu.class));
             			
             			finish();
                	}
                }
            }
        };
        mSplashThread.start();
    }

    @Override
    public void onRestart() {
    	//Log.i("GameActivity","onRestart()");
    	super.onRestart();
    }
    
    @Override
    public void onStart() {
    	//Log.i("GameActivity","onStart()");
    	super.onStart();
    }
    
    @Override
    public void onResume() {
    	//Log.i("GameActivity","onResume()");
    	super.onResume();
    
    }
    
    @Override
    public void onPause() {
    	//Log.i("GameActivity","onPause()");
    	super.onPause();
    
    }
    
    @Override
    public void onStop() {
    	//Log.i("GameActivity","onStop()");
    	super.onStop();
    	
    }
    
    @Override
    public void onDestroy() {
       	//Log.i("GameActivity","onDestroy()");
    	super.onDestroy();
 
    }
}