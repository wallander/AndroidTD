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
        Log.v("", "innan cache");
        fillBitmapCache();
        Log.v("", "Innan Model");
        _model = new GameModel();
        Log.v("", "Skapat Model");
        getHolder().addCallback(this);
        _thread = new GameThread(this);
        Log.v("", "Thread skapad");
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
        _bitmapCache.put(R.drawable.man, BitmapFactory.decodeResource(getResources(), R.drawable.man));
   
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

    	
    	// Kolla om n�gon projectile tr�ffat sin target
    	// Hantera tr�ff, ta bort projectile, ber�kna skada p� mob osv osv osv osv.
    	for (int i = 0; i < _model.projectiles.size(); i++) {
    		Projectile p = _model.projectiles.get(i);
    			
    		//uppdatera position f�r projectiles
    		p.updatePosition();

    		if (p.hasCollided()) {
    			p.inflictDmg();
    			_model.projectiles.remove(p);

    			
    			
    		}
    	}
    	
    	
    	
    	// Uppdatera koordinater f�r mobs och projectiles
    	//_model.updateUnits();
    	for (int i = 0; i < _model.mobs.size(); i++) {
    		Mob m = _model.mobs.get(i);
    		m.updatePosition();
    		
    		if (m.getHealth() <= 0) {
    			_model.mobs.remove(m);
    			_model.mobs.add(MobFactory.CreateMob(Mob.MobType.GROUND));
    			
    		}

    	}
    	
    
    	
    	
    	
    	
    	
    	
    	/*
    	 * F�r alla torn:
    	 * 	kolla vilka mobs man n�r
    	 * 	Skjut p� den n�rmsta (eller svagaste? �ndra sen) om cooldown �r nere
    	 *  (l�gg till ny Projectile i GameModel.
    	 * 
    	 */
    	for (Tower t : _model.towers) {
    		Projectile proj = t.tryToShoot(_model.mobs);
    		
    		if(proj != null){
    			_model.projectiles.add(proj);
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
    
    	canvas.drawBitmap(_bitmapCache.get(R.drawable.abstrakt), 0 , 0, null);
     	for (Mob m : _model.mobs) {

    		canvas.drawBitmap(_bitmapCache.get(R.drawable.man), (int) m.mCoordinates.getX() , (int) m.mCoordinates.getY() , null);
    		
    	}
     	
     	for (Tower t : _model.towers) {

    		canvas.drawBitmap(_bitmapCache.get(R.drawable.rock), (int) t.mCoordinates.getX() , (int) t.mCoordinates.getY() , null);
    		
    	}
     	
     	for (Projectile p : _model.projectiles) {

    		canvas.drawBitmap(_bitmapCache.get(R.drawable.scissors), (int) p.mCoordinates.getX() , (int) p.mCoordinates.getY() , null);
    		
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