package com.chalmers.game.td;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends Activity implements OnClickListener{
	 
	Button mStartGameButton, mHelpButton, mOptionsButton, mCreditsButton;
	
	public void onClick(View v) {
		Intent i;
 		if (v == mStartGameButton)
 			i = new Intent(Menu.this, MenuGame.class);
 		else if (v == mHelpButton)
 			i = new Intent(Menu.this, MenuHelp.class);
 		else if (v == mOptionsButton)
 			i = new Intent(Menu.this, MenuOptions.class);
 		else /*if (v == CreditsButton)*/
 			i = new Intent(Menu.this, MenuCredits.class);
 		startActivity(i);
	}	
	
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
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
        
        Bundle b = getIntent().getExtras();
        if(b != null){
        	String value = b.getString("msg");
        	Toast.makeText(this, "Value : " + value, Toast.LENGTH_LONG).show();
        }
        /*  
        if (savedInstanceState != null) {
            Toast.makeText(this, "onCreate() : " + savedInstanceState.getString("TO_REMEMBER"), Toast.LENGTH_LONG).show();
        }
        */
    }
}