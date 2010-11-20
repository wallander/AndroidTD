package com.chalmers.game.td;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

/**
 * Activity which will be used as main entry point for the application.
 * 
 * @author Fredrik Persson
 * @author Jonas Andersson
 * @author Ahmed Chaban
 * @author Jonas Wallander
 * @author Disa Faith
 * @author Daniel Arvidsson
 */
public class MenuGame extends Activity {
    
	public ProgressionRouteView progressMap;
	private FileInputStream f_in;
	private ObjectInputStream obj_in;
	private Object obj;
	private com.chalmers.game.td.GameModel gmsave;
	private boolean save;
	
	GameModel gm;
	
    /**
     * Method called on application start.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MenuGame","onCreate()");
        
        // Set the screen orientation to Portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //
        
        
        
        
        
        SoundManager.initializeSound(this);
        
        // Bring up the progression route view
        progressMap = new ProgressionRouteView(this);
        
        // Check for saved state
        // Read from disk using FileInputStream.
           try{
               f_in = new FileInputStream (Environment.getExternalStorageDirectory() + "/savegameeskimotd.ser");
          	     obj_in = new ObjectInputStream (f_in);
          		 obj = obj_in.readObject ();
          		Toast.makeText(this, "obj Instance Of GameModel?", Toast.LENGTH_LONG).show();
   	        if (obj instanceof GameModel)
   	        {
   	        	 gmsave = (GameModel) obj;
   		         save = true;
   		         Toast.makeText(this, "Show pop up!", Toast.LENGTH_LONG).show();

   	        }
          } catch (FileNotFoundException ds){
       	   Toast.makeText(this, "File Not Found", Toast.LENGTH_LONG).show(); 	   
          } catch (IOException ioex){
       	   Toast.makeText(this, "IO Exception", Toast.LENGTH_LONG).show();
          } catch (ClassNotFoundException ioedsxm){
       	   Toast.makeText(this, "Class not found", Toast.LENGTH_LONG).show();
          }
        
        if(save){
        	Log.v("SAVE", "TRUE");
    		AlertDialog.Builder builder = new AlertDialog.Builder(MenuGame.this);
    		builder.setMessage("You have a saved game\n" + 
    				           "            Do you want to resume it?");
    		builder.setCancelable(false);
    		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

    			public void onClick(DialogInterface dialog, int id) {
    				gm = gmsave;
    				progressMap.setGameModel(gm, 2); //MENU LOAD GAME STATE
    				
    			}       
    		})      
    		.setNegativeButton("No", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {  
    				GameModel gm = new GameModel();
    				progressMap.setGameModel(gm, 1);//MENU GAME STATE
    				dialog.cancel();       
    			}      
    		});
    		AlertDialog alert = builder.create();
    		alert.show();

    		gm.initialize(this);
        	
        	
        	
		} else {
			GameModel gm = new GameModel();
			progressMap.setGameModel(gm, 1);//MENU GAME STATE
			gm.initialize(this);
		}
        
        
        setContentView(progressMap);        
    }
    
    public void startGamePanel() {
    	
    }
    
    /**
     * Method called upon application closure.
     */
    @Override
    public void onStop() {
    	super.onStop();
    	//Log.i("MenuGame","onStop()");
    	
    	gm.release();
    	SoundManager.releaseSounds();
    	finish();
    }
    
    @Override
    public void onRestart() {
    	//Log.i("MenuGame","onRestart()");
    	super.onRestart();
    }
    
    @Override
    public void onStart() {
    	//Log.i("MenuGame","onStart()");
    	super.onStart();
    }
    
    @Override
    public void onResume() {
    	//Log.i("MenuGame","onResume()");
    	super.onResume();
    
    }
    
    @Override
    public void onPause() {
    	//Log.i("MenuGame","onPause()");
    	super.onPause();
    
    }
    
    @Override
    public void onDestroy() {
       	//Log.i("MenuGame","onDestroy()");
    	super.onDestroy();
 
    }
}