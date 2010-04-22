package com.chalmers.game.td;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;


public class MenuStart extends Activity {
   
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MenuStart","onCreate()");
        
        setContentView(R.layout.start);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

        		startActivity(new Intent(MenuStart.this, Menu.class));
          		finish();
        }
        return true;
    }
    

    @Override
    public void onRestart() {
    	Log.i("MenuStart","onRestart()");
    	super.onRestart();
    }
    
    @Override
    public void onStart() {
    	Log.i("MenuStart","onStart()");
    	super.onStart();
    }
    
    @Override
    public void onResume() {
    	Log.i("MenuStart","onResume()");
    	super.onResume();
    
    }
    
    @Override
    public void onPause() {
    	Log.i("MenuStart","onPause()");
    	super.onPause();
    
    }
    
    @Override
    public void onStop() {
    	Log.i("MenuStart","onStop()");
    	super.onStop();
    	
    }
    
    @Override
    public void onDestroy() {
       	Log.i("MenuStart","onDestroy()");
    	super.onDestroy();
 
    }
}