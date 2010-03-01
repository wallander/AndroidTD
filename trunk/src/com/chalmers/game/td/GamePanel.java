package com.chalmers.game.td;

import java.util.ArrayList;

import java.util.Random;

import com.android.towerdef.R;

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
     * Constructor called on instantiation.
     * @param context Context of calling activity.
     */
    public GamePanel(Context context) {
        super(context);
        _model = new GameModel();
        
        getHolder().addCallback(this);
        _thread = new GameThread(this);
        setFocusable(true);
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
    
    /**
     * Update the physics of each item already added to the panel.
     * Not including items which are currently exploding and moved by a touch event.
     */
    public void updatePhysics() {
    	
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