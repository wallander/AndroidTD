package com.chalmers.game.td;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuCredits extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits);      
        
        // Set the screen orientation to Portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        Button StartGameButton = (Button)findViewById(R.id.StartGame);
        StartGameButton.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
        		Intent GoToMenu = new Intent(MenuCredits.this, Menu.class);
        		//Intent GoToMenu = new Intent("com.dotted.games.Menu");
                startActivity(GoToMenu);
        		Bundle b = new Bundle();
        		b.putString("msg", "Hej! Jag kommer från Credits!");
        		GoToMenu.putExtras(b);
        		
        		finish();
        	}
    	});
              
    }
}