package com.chalmers.game.td;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends Activity implements OnClickListener{
	 
	Button mStartGameButton, mHelpButton, mOptionsButton, mCreditsButton, mExitButton;
	
	public void onClick(View v) {
		
		if (v == mExitButton) {
			//exit game
			finish();
 			//Toast.makeText(Menu.this, "Exit game", Toast.LENGTH_SHORT).show();
		}
		else {
			Intent i;
			if (v == mStartGameButton)
				i = new Intent(Menu.this, MenuGame.class);
			else if (v == mHelpButton)
				i = new Intent(Menu.this, MenuHelp.class);
			else if (v == mOptionsButton)
				i = new Intent(Menu.this, MenuOptions.class);
			else /*if (v == mCreditsButton)*/
				i = new Intent(Menu.this, MenuCredits.class);
			startActivity(i);
		}
	}	
	
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	Log.i("Menu","onCreate()");
    	
        setContentView(R.layout.main);
        
        // Set the screen orientation to Portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    	
    	mStartGameButton = (Button)findViewById(R.id.StartGame); 
    	mStartGameButton.setOnClickListener(this);
    	
    	mHelpButton = (Button)findViewById(R.id.Help);
        mHelpButton.setOnClickListener(this);
        
    	mOptionsButton = (Button)findViewById(R.id.Options);
    	mOptionsButton.setOnClickListener(this);
        
    	mCreditsButton = (Button)findViewById(R.id.Credits);
        mCreditsButton.setOnClickListener(this);
        
        mExitButton = (Button)findViewById(R.id.Exit);
        mExitButton.setOnClickListener(this);
        
        Bundle b = getIntent().getExtras();
        if(b != null){
        	String value = b.getString("msg");
        	Toast.makeText(this, "Value : " + value, Toast.LENGTH_LONG).show();
        }
       
        
    }
    @Override
    public void onRestart() {
    	Log.i("Menu","onRestart()");
    	super.onRestart();
    }
    
    @Override
    public void onStart() {
    	Log.i("Menu","onStart()");
    	super.onStart();
    }
    
    @Override
    public void onResume() {
    	Log.i("Menu","onResume()");
    	super.onResume();
    
    }
    
    @Override
    public void onPause() {
    	Log.i("Menu","onPause()");
    	super.onPause();
    
    }
    
    @Override
    public void onStop() {
    	Log.i("Menu","onStop()");
    	super.onStop();
    	
    }
    
    @Override
    public void onDestroy() {
       	Log.i("Menu","onDestroy()");
    	super.onDestroy();
 
    }
}