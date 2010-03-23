package com.chalmers.game.td;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends Activity {
	
	private boolean nextActivityStarted = false;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.main);
        
        // Set the screen orientation to Portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        
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
        Button StartGameButton = (Button)findViewById(R.id.StartGame);
        StartGameButton.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
        		
        		if (nextActivityStarted == false) {
        			nextActivityStarted = true;
        			Intent StartGameIntent = new Intent(Menu.this,MenuGame.class); 
        			startActivity(StartGameIntent);
        		}
        	}
        });
        
        Button HelpButton = (Button)findViewById(R.id.Help);
        HelpButton.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
        		if (nextActivityStarted == false) {
        			nextActivityStarted = true;
        			Intent HelpIntent = new Intent(Menu.this,MenuHelp.class);
        			startActivity(HelpIntent);
        		}
        	}
        });
        
        Button OptionsButton = (Button)findViewById(R.id.Options);
        OptionsButton.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
        		if (nextActivityStarted == false ) {
        			nextActivityStarted = true;
        			Intent OptionsIntent = new Intent(Menu.this,MenuOptions.class);
        			startActivity(OptionsIntent);
        		}
        	}
        });
        
        Button CreditsButton = (Button)findViewById(R.id.Credits);
        CreditsButton.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
        		if (nextActivityStarted == false ) {
        			nextActivityStarted = true;
        			Intent CreditsIntent= new Intent(Menu.this,MenuCredits.class);
        			startActivity(CreditsIntent);
        		}
        	}
        });
    }
}