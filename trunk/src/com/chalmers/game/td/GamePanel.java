package com.chalmers.game.td;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.Random;

import com.chalmers.game.td.units.Mob;
import com.droidnova.android.games.R;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Custom SurfaceView to handle everything we need from physics to drawings.
 * 
 * @author martin
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    
    /**
     * Thread which contains our game loop.
     */
    private GameThread _thread;
    
    /**
     * Model which contains the game model
     */
    private GameModel _model;
    
    /**
     * Cache variable for all used images.
     */
    private Map<Integer, Bitmap> _bitmapCache = new HashMap<Integer, Bitmap>();
    
    
    /**
     * Constructor called on instantiation.
     * @param context Context of calling activity.
     */
    public GamePanel(Context context) {
        super(context);
        fillBitmapCache();
        _model = new GameModel();
        
        getHolder().addCallback(this);
        _thread = new GameThread(this);
        setFocusable(true);
    }
    
    /**
     * Fill the bitmap cache.
     */
    private void fillBitmapCache() {
        _bitmapCache.put(R.drawable.icon, BitmapFactory.decodeResource(getResources(), R.drawable.icon));
        _bitmapCache.put(R.drawable.abstrakt, BitmapFactory.decodeResource(getResources(), R.drawable.abstrakt));
        _bitmapCache.put(R.drawable.wallpaper, BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper));
        _bitmapCache.put(R.drawable.scissors, BitmapFactory.decodeResource(getResources(), R.drawable.scissors));
        _bitmapCache.put(R.drawable.paper, BitmapFactory.decodeResource(getResources(), R.drawable.paper));
        _bitmapCache.put(R.drawable.rock, BitmapFactory.decodeResource(getResources(), R.drawable.rock));
        _bitmapCache.put(R.drawable.smaller, BitmapFactory.decodeResource(getResources(), R.drawable.smaller));
        _bitmapCache.put(R.drawable.small, BitmapFactory.decodeResource(getResources(), R.drawable.small));
        _bitmapCache.put(R.drawable.big, BitmapFactory.decodeResource(getResources(), R.drawable.big));
    }

    /**
     * Process the MotionEvent.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	
   	
        synchronized (getHolder()) {
            
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                
                
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                
            }
            
        }
        try {
        	Thread.sleep(16);
        } catch (InterruptedException e) {
        	Log.v("App: ", "Error 2");
        }
     
        return true;
        
    
    }
    

    public void updateModel() {

    	// Uppdatera koordinater för mobs och projectiles
    	//_model.updateUnits();
    	for (Mob m : _model.mobs) {
    		//uppdatera position för mobs
    	}
    	
    	for (Projectile p : _model.projectiles) {
    		//uppdatera position för projectiles
    	}
    	
    	
    	// Kolla om någon projectile träffat sin target
    	// Hantera träff, ta bort projectile, beräkna skada på mob osv osv osv osv.
    	
    	
    	/*
    	 * För alla torn:
    	 * 	kolla vilka mobs man når
    	 * 	Skjut på den närmsta (eller svagaste? ändra sen) om cooldown är nere
    	 *  (lägg till ny Projectile i GameModel.
    	 * 
    	 */
    	
    
    	
    }
    


    
    /**
     * Draw on the SurfaceView.
     * Order:
     * <ul>
     *  <li>Background image</li>
     *  <li>Items on the panel</li>
     *  <li>Explosions</li>
     *  <li>Item moved by hand</li>
     * </ul>
     */
    @Override
    public void onDraw(Canvas canvas) {
        // draw the background
    	 canvas.drawBitmap(_bitmapCache.get(R.drawable.abstrakt), 0 , 0, null);
        
    }

    /**
     * Called if you change the configuration like open the keypad.
     */
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /**
     * Called on creation of the SurfaceView.
     * Which could be on first start or relaunch.
     */
    public void surfaceCreated(SurfaceHolder holder) {
        if (!_thread.isAlive()) {
            _thread = new GameThread(this);
        }
        _thread.setRunning(true);
        _thread.start();
    }

    /**
     * Called if the SurfaceView should be destroyed.
     * We try to finish the game loop thread here.
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        _thread.setRunning(false);
        while (retry) {
            try {
                _thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // we will try it again and again...
            }
        }
        Log.i("thread", "Thread terminated...");
    }
}