package com.chalmers.game.td;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuCredits extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.i("MenuCredits","onCreate()");
        
        setContentView(R.layout.credits);      
        
        // Set the screen orientation to Portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        Button GoBack = (Button)findViewById(R.id.GoBack);
        GoBack.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
        		Intent GoToMenu = new Intent(MenuCredits.this, Menu.class);
        		//Intent GoToMenu = new Intent("com.dotted.games.Menu");
                startActivity(GoToMenu);        		
        		finish();
        	}
    	});
              
    }
    
    @Override
    public void onRestart() {
    	//Log.i("MenuCredits","onRestart()");
    	super.onRestart();
    }
    
    @Override
    public void onStart() {
    	//Log.i("MenuCredits","onStart()");
    	super.onStart();
    }
    
    @Override
    public void onResume() {
    	//Log.i("MenuCredits","onResume()");
    	super.onResume();
    
    }
    
    @Override
    public void onPause() {
    	//Log.i("MenuCredits","onPause()");
    	super.onPause();
    
    }
    
    @Override
    public void onStop() {
    	//Log.i("MenuCredits","onStop()");
    	super.onStop();
    	
    }
    
    @Override
    public void onDestroy() {
       	//Log.i("MenuCredits","onDestroy()");
    	super.onDestroy();
 
    }
}