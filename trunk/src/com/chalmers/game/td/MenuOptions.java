package com.chalmers.game.td;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MenuOptions extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MenuOptions","onCreate");
        
        setContentView(R.layout.options);     
        
        // Set the screen orientation to Portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        Button StartGameButton = (Button)findViewById(R.id.StartGame);
        StartGameButton.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
        		Intent GoToMenu = new Intent(MenuOptions.this, Menu.class);
        		Bundle b = new Bundle();
        		b.putString("msg", "Hej! Jag kommer från Options!");
        		GoToMenu.putExtras(b);
                startActivity(GoToMenu);
        		finish();
        	}
        });
        
        CheckBox toggleMusic = (CheckBox)findViewById(R.id.MusicCheckBox);
        toggleMusic.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				GameModel.setMusicEnabled(isChecked);
			}
		});
        
        toggleMusic.setChecked(GameModel.sMusicEnabled);
        toggleMusic.setText("Toggle Music");
        
        CheckBox toggleCheat = (CheckBox)findViewById(R.id.CheatCheckBox);
        toggleCheat.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				GameModel.setCheatEnabled(isChecked);
			}
		});
        
        toggleCheat.setChecked(GameModel.sCheatEnabled);
        toggleCheat.setText("Toggle Infinite Snowballs");
        
    }
    
    @Override
    public void onRestart() {
    	Log.i("MenuOptions","onRestart()");
    	super.onRestart();
    }
    
    @Override
    public void onStart() {
    	Log.i("MenuOptions","onStart()");
    	super.onStart();
    }
    
    @Override
    public void onResume() {
    	Log.i("MenuOptions","onResume()");
    	super.onResume();
    
    }
    
    @Override
    public void onPause() {
    	Log.i("MenuOptionsy","onPause()");
    	super.onPause();
    
    }
    
    @Override
    public void onStop() {
    	Log.i("MenuOptions","onStop()");
    	super.onStop();
    	
    }
    
    @Override
    public void onDestroy() {
       	Log.i("MenuOptions","onDestroy()");
    	super.onDestroy();
 
    }
}