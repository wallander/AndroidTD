package com.chalmers.game.td;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.Random;

import com.chalmers.game.td.units.Tower;
import com.chalmers.game.td.units.Unit;
import com.chalmers.game.td.units.Mob;
import com.chalmers.game.td.units.Projectile;



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
 * @author Jonas Andersson, Daniel Arvidsson, Ahmed Chaban, Disa Faith, Fredrik Persson, Jonas Wallander
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    
    /**
     * Thread which contains our game loop.
     */
    private GameThread mGameThread;
    
    /**
     * Model which contains the game model
     */
    private GameModel mGameModel;
    
    /**
     * Cache variable for all used images.
     */
    private Map<Integer, Bitmap> mBitMapCache = new HashMap<Integer, Bitmap>();
    
    
    /**
     * Constructor called on instantiation.
     * @param context Context of calling activity.
     */
    public GamePanel(Context context) {
    	 
        super(context);
        Log.v("", "Before cache");
        fillBitmapCache();
        Log.v("", "Before Model");
        mGameModel = new GameModel();
        Log.v("", "Created Model");
        getHolder().addCallback(this);
        mGameThread = new GameThread(this);
        Log.v("", "Thread created");
        setFocusable(true);
    }
    
    /**
     * Fill the bitmap cache.
     */
    private void fillBitmapCache() {
        mBitMapCache.put(R.drawable.icon, BitmapFactory.decodeResource(getResources(), R.drawable.icon));
        mBitMapCache.put(R.drawable.abstrakt, BitmapFactory.decodeResource(getResources(), R.drawable.abstrakt));
        mBitMapCache.put(R.drawable.wallpaper, BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper));
        mBitMapCache.put(R.drawable.scissors, BitmapFactory.decodeResource(getResources(), R.drawable.scissors));
        mBitMapCache.put(R.drawable.paper, BitmapFactory.decodeResource(getResources(), R.drawable.paper));
        mBitMapCache.put(R.drawable.rock, BitmapFactory.decodeResource(getResources(), R.drawable.rock));
        mBitMapCache.put(R.drawable.smaller, BitmapFactory.decodeResource(getResources(), R.drawable.smaller));
        mBitMapCache.put(R.drawable.small, BitmapFactory.decodeResource(getResources(), R.drawable.small));
        mBitMapCache.put(R.drawable.big, BitmapFactory.decodeResource(getResources(), R.drawable.big));
        mBitMapCache.put(R.drawable.man, BitmapFactory.decodeResource(getResources(), R.drawable.man));
   
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
    	
    	// Check if any projectile has hit it's target
    	// Handle hit, remove projectile, calculate damage on mob, etc. etc.
    	for (int i = 0; i < mGameModel.mProjectiles.size(); i++) {
    		Projectile p = mGameModel.mProjectiles.get(i);
    			    		
    		// Update position for the projectiles
    		p.updatePosition();

    		if (p.hasCollided()) {
    			p.inflictDmg();
    			mGameModel.mProjectiles.remove(p);
    		}
    	}
    	
    	
    	
    	// Uppdatera koordinater f�r mobs och projectiles
    	//_model.updateUnits();
    	for (int i = 0; i < mGameModel.mMobs.size(); i++) {
    		Mob m = mGameModel.mMobs.get(i);
    		m.updatePosition();
    		
    		if (m.getHealth() <= 0) {
    			mGameModel.mMobs.remove(m);
    			mGameModel.mMobs.add(MobFactory.CreateMob(Mob.MobType.GROUND));
    			
    		}

    	}
    	
    	
    	
    	/*
    	 * F�r alla torn:
    	 * 	kolla vilka mobs man n�r
    	 * 	Skjut p� den n�rmsta (eller svagaste? �ndra sen) om cooldown �r nere
    	 *  (l�gg till ny Projectile i GameModel.
    	 * 
    	 */
    	for (int i=0; i<mGameModel.mTowers.size(); i++) {
    		Tower t = mGameModel.mTowers.get(i);
    		Projectile proj = t.tryToShoot(mGameModel.mMobs);
    		
    		if(proj != null){
    			mGameModel.mProjectiles.add(proj);
    		}
    		
    	}
    	
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
    
    	canvas.drawBitmap(mBitMapCache.get(R.drawable.abstrakt), 0 , 0, null);
     	for (int i = 0; i < mGameModel.mMobs.size(); i++) {
     		Mob m = mGameModel.mMobs.get(i);
    		canvas.drawBitmap(mBitMapCache.get(R.drawable.man), (int) m.mCoordinates.getX() , (int) m.mCoordinates.getY() , null);
    		
    	}
     	
     	for (int i = 0; i < mGameModel.mTowers.size(); i++) {
     		Tower t = mGameModel.mTowers.get(i);
    		canvas.drawBitmap(mBitMapCache.get(R.drawable.rock), (int) t.mCoordinates.getX() , (int) t.mCoordinates.getY() , null);
    		
    	}
     	
     	for (int i = 0; i < mGameModel.mProjectiles.size(); i++) {
     		Projectile p = mGameModel.mProjectiles.get(i);
    		canvas.drawBitmap(mBitMapCache.get(R.drawable.scissors), (int) p.mCoordinates.getX() , (int) p.mCoordinates.getY() , null);
    		
    	}
     	
    	 
    	 
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
        if (!mGameThread.isAlive()) {
            mGameThread = new GameThread(this);
        }
        mGameThread.setRunning(true);
        mGameThread.start();
    }

    /**
     * Called if the SurfaceView should be destroyed.
     * We try to finish the game loop thread here.
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        mGameThread.setRunning(false);
        while (retry) {
            try {
                mGameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // we will try it again and again...
            }
        }
        Log.i("thread", "Thread terminated...");
    }
}