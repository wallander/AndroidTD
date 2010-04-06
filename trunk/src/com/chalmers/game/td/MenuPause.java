package com.chalmers.game.td;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MenuPause extends Activity implements OnClickListener{
	 
	Button mContinueButton,mRestartButton, mExitButton;
	
	public void onClick(View v) {

		Intent i;
		if (v == mContinueButton)
			setResult(1);
		else if (v == mRestartButton)
			i = new Intent(MenuPause.this, MenuGame.class);
		else /*if (v == mExitButton)*/
			i = new Intent(MenuPause.this, MenuCredits.class);
		//startActivity(i);
	}
	
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Set the screen orientation to Portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    	
    	mContinueButton = (Button)findViewById(R.id.StartGame); 
    	mContinueButton.setOnClickListener(this);
    	
    	mRestartButton = (Button)findViewById(R.id.Help);
        mRestartButton.setOnClickListener(this);
        
    	mExitButton = (Button)findViewById(R.id.Options);
    	mExitButton.setOnClickListener(this);
        
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