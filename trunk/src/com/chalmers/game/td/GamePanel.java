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
 * This is a view that displays the entire game board. The onDraw method draws
 * all towers, mobs, projectiles and the map itself. This information is taken
 * from the GameModel.
 * 
 * In Android development, the "View" also handles user input, so this is where
 * we take care of all events that the user generates on the game field.
 * 
 * @author Fredrik Persson
 * @author Jonas Andersson
 * @author Ahmed Chaban
 * @author Jonas Wallander
 * @author Disa Faith
 * @author Daniel Arvidsson
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    
    /** Thread which contains our game loop. */
    private GameThread mGameThread;
    
    /** Model which contains the game model  */
    private GameModel mGameModel;
    
    /** Cache variable for all used images. */
    private Map<Integer, Bitmap> mBitMapCache = new HashMap<Integer, Bitmap>();
    
    
    /**
     * Constructor called on instantiation.
     * @param context Context of calling activity.
     */
    public GamePanel(Context context) {
    	 
        super(context);
        mGameModel = new GameModel();
        fillBitmapCache();
        getHolder().addCallback(this);
        mGameThread = new GameThread(this);
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
   
        /* TODO:
        for(every unit with a bitmap in the gamemodel) {
        add the bitmap to the cache;
        }
        */
        
}

    /**
     * TODO
     * 
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
     * This class is called from the GameThread. 
     * It updates the state of all towers, mobs and projectiles. 
     * It also handles projectile collisions with mobs dying and such.
     */
    public void updateModel() {

    	/*
    	 * for every tower:
    	 * 	create a new Projectile set to a Mob that the Tower can reach
    	 *  and add that to the list of Projectiles in the GameModel
    	 * 
    	 * tryToShoot() returns null if the tower can't reach any mob
    	 */
    	for (int i=0; i<mGameModel.mTowers.size(); i++) {
    		Tower t = mGameModel.mTowers.get(i);
    		Projectile proj = t.tryToShoot(mGameModel.mMobs);
    		
    		if(proj != null){
    			mGameModel.mProjectiles.add(proj);
    		}
    	}
    	
    	
    	// Check if any projectile has hit it's target
    	// Handle hit, remove projectile, calculate damage on mob, etc. etc.
    	for (int i = 0; i < mGameModel.mProjectiles.size(); i++) {
    		Projectile p = mGameModel.mProjectiles.get(i);

    		// Update position for the projectiles
    		p.updatePosition();

    		// If the projectile has collided, inflict damage and remove it.
    		if (p.hasCollided()) {
    			p.inflictDmg();
    			mGameModel.mProjectiles.remove(p);
    		}
    		
    		// if the projectile's target is dead, remove the projectile
    		if (p.getTargetedMob().getHealth() <= 0) {
    			mGameModel.mProjectiles.remove(p);
    		}
    	}
    	
    	
    	/*
    	 * For every mob:
    	 *  Update position
    	 *  If the mob has died, handle it
    	 */
    	for (int i = 0; i < mGameModel.mMobs.size(); i++) {
    		Mob m = mGameModel.mMobs.get(i);
    		m.updatePosition();
    		
    		if (m.getHealth() <= 0) {
    			mGameModel.mMobs.remove(m);
    		}
    	}
    }

    
    /**
     * Draw on the SurfaceView.
     * Order:
     * <ul>
     *  <li>Game map</li>
     *  <li>Mobs</li>
     *  <li>Towers</li>
     *  <li>Projectiles</li>
     * </ul>
     */
    
    @Override
    public void onDraw(Canvas canvas) {
    	
        // draw the background
    	canvas.drawBitmap(mBitMapCache.get(R.drawable.abstrakt), 0 , 0, null);
    	
    	// draw all mobs
     	for (int i = 0; i < mGameModel.mMobs.size(); i++) {
     		Mob m = mGameModel.mMobs.get(i);
     		//canvas.drawBitmap(mBitMapCache.get(m.getBitmap()), (int) m.getX() , (int) m.getY() , null);
    		canvas.drawBitmap(mBitMapCache.get(R.drawable.man), (int) m.getX() , (int) m.getY() , null);
    	/*
    	 * Here we should draw a healthbar for each mob aswell
    	 */
    		
     	}
     	
     	// draw all towers
     	for (int i = 0; i < mGameModel.mTowers.size(); i++) {
     		Tower t = mGameModel.mTowers.get(i);
     		////canvas.drawBitmap(mBitMapCache.get(t.getBitmap()), (int) t.getX() , (int) t.getY() , null);
    		canvas.drawBitmap(mBitMapCache.get(R.drawable.rock), (int) t.getX() , (int) t.getY() , null);
    	}
     	
     	// draw all projectiles
     	for (int i = 0; i < mGameModel.mProjectiles.size(); i++) {
     		Projectile p = mGameModel.mProjectiles.get(i);
     		////canvas.drawBitmap(mBitMapCache.get(p.getBitmap()), (int) p.getX() , (int) p.getY() , null);
    		canvas.drawBitmap(mBitMapCache.get(R.drawable.scissors), (int) p.getX() , (int) p.getY() , null);
    	}
     	
    	 // here we will draw the tower that the user is about to put on the map
    	 
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