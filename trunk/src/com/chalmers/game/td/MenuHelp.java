package com.chalmers.game.td;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuHelp extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MenuHelp","onCreate()");
        
        setContentView(R.layout.help);      
        
        // Set the screen orientation to Portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        Button StartGameButton = (Button)findViewById(R.id.StartGame);
        StartGameButton.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
        		Intent GoToMenu = new Intent(MenuHelp.this, Menu.class);
        		//Intent GoToMenu = new Intent("com.dotted.games.Menu");
                startActivity(GoToMenu);
        		Bundle b = new Bundle();
        		b.putString("msg", "Hej! Jag kommer från Help!");
        		GoToMenu.putExtras(b);
        		
        		finish();
        	}
    	});         
    }
    @Override
    public void onRestart() {
    	Log.i("MenuHelp","onRestart()");
    	super.onRestart();
    }
    
    @Override
    public void onStart() {
    	Log.i("MenuHelp","onStart()");
    	super.onStart();
    }
    
    @Override
    public void onResume() {
    	Log.i("MenuHelp","onResume()");
    	super.onResume();
    
    }
    
    @Override
    public void onPause() {
    	Log.i("MenuHelp","onPause()");
    	super.onPause();
    
    }
    
    @Override
    public void onStop() {
    	Log.i("MenuHelp","onStop()");
    	super.onStop();
    	
    }
    
    @Override
    public void onDestroy() {
       	Log.i("MenuHelp","onDestroy()");
    	super.onDestroy();
 
    }
}