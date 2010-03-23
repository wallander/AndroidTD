package com.chalmers.game.td;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chalmers.game.td.units.Tower;
import com.chalmers.game.td.R;

import com.chalmers.game.td.units.Mob;
import com.chalmers.game.td.units.Projectile;



import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

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
    
    
    /** Current x and y cord. for the touched tower     */
    private int tx;
    private int ty;
    

    /** Keeps track of the delay between creation of Mobs in waves */
    private static final int MOB_DELAY_MAX = 30;
    private int mMobDelayI = 0;
    
    private Tower currentTower;

    private MobFactory	mobFactory;

    private Tower selectedTower;
    
 

    


    /** Debug */
    TDDebug debug;    


    /**
     * Constructor called on instantiation.
     * @param context Context of calling activity.
     */
    public GamePanel(Context context) {
    	 
        super(context);

        mobFactory = MobFactory.getInstance(); 
        mobFactory.setContext(context); // Have to send a reference to context to be able to read the xml-file initwaves.xml in resources            
        
      //  Context cx = Context.enter();




        

        debug = new TDDebug();
        debug.InitGameTime();
        
        
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
        mBitMapCache.put(R.drawable.b, BitmapFactory.decodeResource(getResources(), R.drawable.b));
        mBitMapCache.put(R.drawable.upgrade, BitmapFactory.decodeResource(getResources(), R.drawable.upgrade));
        
        
}

    /**
     * TODO fixa knappevents
     * 
     * Process the MotionEvent.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	
    	
        synchronized (getHolder()) {
            
        	
        	switch (event.getAction()) {
        	case MotionEvent.ACTION_DOWN:
        		
        		//When upgrade window is up, press to upgrade or outside to go back
        		if(selectedTower != null && event.getY() > 40  && event.getY() < 240 && event.getX() > 70 && event.getX() < 350){
        			
        			Toast.makeText(getContext(),"Debug: Pressed to upgrade!", Toast.LENGTH_SHORT).show(); //TODO remove
  
//        			showUpgradeWindow = false;
        		} else {

//        			showUpgradeWindow = false;
        			selectedTower = null;
	        		// If the ACTION_DOWN event was not in the button section but on a tower, select the clicked tower
	            	if (event.getX() < 410) {
	            		
	            		for (int i = 0; i < mGameModel.mTowers.size(); i++){
	            			Tower t = mGameModel.mTowers.get(i);
	            			
	            			if (t.selectTower(event.getX(), event.getY())){
	            				
		            			selectedTower = t;
//		            			showUpgradeWindow = true;
	            				
	            			//	cx.startActivity(new Intent(cx, UpgradeTowerDialog.class));
	            				Toast.makeText(getContext(),"Debug: clicked tower #" + i, Toast.LENGTH_SHORT).show(); //TODO remove
	            				break;
	            			}
	            		}
	            	}
	
	            	
	            	// button 1,
	
	
	                if(event.getY() > 15  && event.getY() < 65 && event.getX() > 410) {
	                	
	                	tx = (int) event.getX() - 60;
	                }
	
	
	                if(event.getY() > 15  && event.getY() < 65 && event.getX() > 410) {
	                	tx = (int) event.getX() - 60;
	
	                	ty = (int) event.getY();
	                	currentTower = new Tower(tx ,ty);
	            		currentTower.setSize(2);
	                }
	                
	                // button 2
	                if(event.getY() > 15+60  && event.getY() < 65+60 && event.getX() > 410) {
	                	Toast.makeText(getContext(), "Debug: knapp 2", Toast.LENGTH_SHORT).show();
	                }
	                
	                // button 3
	                if(event.getY() > 15+120  && event.getY() < 65+120 && event.getX() > 410) {
	                	Toast.makeText(getContext(), "Debug: knapp 3", Toast.LENGTH_SHORT).show();
	                }
	                
	                // button 4
	                if(event.getY() > 15+180  && event.getY() < 65+180 && event.getX() > 410) {
	                	Toast.makeText(getContext(), "Debug: knapp 4", Toast.LENGTH_SHORT).show();
	                }
	                
	                // button 5
	                if(event.getY() > 15+240  && event.getY() < 65+240 && event.getX() > 410) {
	                	
	                	mGameModel.mMobs.add(new Mob(Path.getInstance(), null));
	                	
	                	Toast.makeText(getContext(), "Debug: knapp 5", Toast.LENGTH_SHORT).show();
	                }

        		}
                
    
        		
        	case MotionEvent.ACTION_MOVE:
        		
        		 if(currentTower != null){
                 	
                 	tx = (int) event.getX() - 60;
                 	ty = (int) event.getY();
                 	currentTower.setX(tx);
                 	currentTower.setY(ty);
                 }
        		
        		break;
        		
        	case MotionEvent.ACTION_UP:
        		
        		if(currentTower != null){
            		mGameModel.buildTower(currentTower, (int)currentTower.getX() / GameModel.GAME_TILE_SIZE, (int)currentTower.getY() / GameModel.GAME_TILE_SIZE);
            		currentTower = null;
            	}
        		
        		break;
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
     * This class is called each frame. 
     * It keeps track of the creation of the mobs from the waves of the current map
     * Called from updateModel 
     */
    public Mob createMobs() {  	    	    	    	        	    	    	
    	
    	switch (mMobDelayI) {
    	case MOB_DELAY_MAX:
    		mMobDelayI = 0;
    		
    	return mobFactory.getNextMob();
    	
    		
    	default:
    		++mMobDelayI;    		
    		return null;
    	}    
    }

    /**
     * This class is called from the GameThread. 
     * It updates the state of all towers, mobs and projectiles. 
     * It also handles projectile collisions with mobs dying and such.
     */
    public void updateModel() {
    	
    	debug.UpdateFPS();
    	//Log.v("FPS",Float.toString(debug.getFPS()));
    	
    	Mob mNewMob = createMobs();
    	
    	if(mNewMob != null) {
    	    		
    		mGameModel.mMobs.add(mNewMob);
    	}
    	
    	/*
    	 * for every tower:
    	 * 	create a new Projectile set to a Mob that the Tower can reach
    	 *  and add that to the list of Projectiles in the GameModel
    	 * 
    	 * tryToShoot() returns null if the tower can't reach any mob
    	 */
    	for (int i=0; i<mGameModel.mTowers.size(); i++) {
    		Tower t = mGameModel.mTowers.get(i);
    		Projectile proj = null;
    		
    		if (mGameModel.mMobs.size() > 0) {
    			proj = t.tryToShoot(mGameModel.mMobs);
    		}
    		
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
    		// TODO: solve it in a better way, this is fugly
    		if (p.getMob().getHealth() <= 0) {
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
    		
    		// update position, if the mob reached the last checkpoint, handle it
    		if (!m.updatePosition()){
    			mGameModel.mMobs.remove(m);

    		}
    		
    		// handle mob death TODO
    		if (m.getHealth() <= 0) {
//    			give money to the player?
//    			give experience to the player?


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
    	// TODO: Dela in subtask i subfunktioner. Ser snyggare ut! / Jonas
    	
        // draw the background
    	canvas.drawBitmap(mBitMapCache.get(R.drawable.abstrakt), 0 , 0, null);
    	
    	canvas.drawText("FPS: "+Float.toString(debug.getFPS()) + " Mobs:"+ mGameModel.mMobs.size()+ " Proj:"+mGameModel.mProjectiles.size() + " Towers:"+ mGameModel.mTowers.size(), 10, 10, new Paint());
    	
    	
    	

    	
    	// draw all mobs
     	for (int i = 0; i < mGameModel.mMobs.size(); i++) {
     		Mob m = mGameModel.mMobs.get(i);
     		//canvas.drawBitmap(mBitMapCache.get(m.getBitmap()), (int) m.getX() , (int) m.getY() , null);
    		canvas.drawBitmap(mBitMapCache.get(R.drawable.man), (int) m.getX() , (int) m.getY() , null);
    		int hpRatio = (int)(255* (double)m.getHealth() / (double)m.getMaxHealth());
    		
    		// drawing health bars for each mob
    		Paint paint = new Paint();
    		paint.setARGB(255, 0, 0, 0);
    		paint.setStyle(Paint.Style.FILL);
    		float left = (float)m.getX() - 2;
    		float top = (float) m.getY() - 5;
    		float right = (float) (m.getX() + 24);
    		float bottom = (float) m.getY() - 2;
    		canvas.drawRect(left, top, right, bottom, paint);
    		

    		paint.setARGB(255, 255 - hpRatio, hpRatio, 0);
    		paint.setStyle(Paint.Style.FILL);
    		left = (float)m.getX() - 2;
    		top = (float) m.getY() - 5;
    		right = (float) (m.getX() + (24 * hpRatio/255));
    		bottom = (float) m.getY() - 2;
    		canvas.drawRect(left, top, right, bottom, paint);
    		
    		
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
     		Bitmap bitmapOrg = mBitMapCache.get(R.drawable.scissors);
     		Matrix matrix = new Matrix(); 
     		
     		

            // rotate the Bitmap 
            matrix.postRotate((float) (-1*p.getAngle()/Math.PI*180));
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true); 
  
     		canvas.drawBitmap(resizedBitmap, (int) p.getX(), (int) p.getY(), null);
    		
    	}
     	
     	
     	Paint paint = new Paint();
		paint.setARGB(100,255,0,0);
		paint.setStyle(Paint.Style.FILL);
		float left, top, right, bottom;
		
	
		if(currentTower == null) {

	    	// draw 5 placeholder buttons in the lower part of the screen
	    	
			for (int i = 0; i < 5; i++) {
				top = 15 + 60*i;
				left = 410;
				bottom = 65 + 60*i;
				right = 470;
				RectF rect = new RectF(left, top, right, bottom);
		     	canvas.drawRoundRect(rect, 5, 5, paint);
			}
			
		} else {
			// draw grid to show where the new mob can be placed
			
			Paint gridpaint = new Paint();
			gridpaint.setARGB(50,255,0,0);
			gridpaint.setStyle(Paint.Style.FILL);
			
			// draw the chosen tower
			canvas.drawBitmap(mBitMapCache.get(R.drawable.rock), GameModel.GAME_TILE_SIZE*(tx / GameModel.GAME_TILE_SIZE) , GameModel.GAME_TILE_SIZE*(ty / GameModel.GAME_TILE_SIZE) , null);
			
			
			// draw a red transparent rectangle on every occupied tile
			for (Point p : mGameModel.mOccupiedTilePositions) {
				canvas.drawRect(p.x*GameModel.GAME_TILE_SIZE, p.y*GameModel.GAME_TILE_SIZE, (1+p.x)*GameModel.GAME_TILE_SIZE, (1+p.y)*GameModel.GAME_TILE_SIZE, gridpaint);
			}
			
			
			
			// draw a circle that shows the tower's range
			if (mGameModel.canAddTower(currentTower))
				gridpaint.setARGB(40, 255, 255, 255);
			else
				gridpaint.setARGB(40, 255, 0, 0);
			
			canvas.drawCircle(GameModel.GAME_TILE_SIZE*(tx / GameModel.GAME_TILE_SIZE + (currentTower.getWidth()/2)), GameModel.GAME_TILE_SIZE*(ty / GameModel.GAME_TILE_SIZE + (currentTower.getHeight() / 2)), currentTower.getRange(), gridpaint);
		
		}
		
		if(selectedTower != null){
			/*
	     	Paint paint2 = new Paint();
			paint2.setARGB(100,255,0,0);
			paint2.setStyle(Paint.Style.FILL);
			float left2, top2, right2, bottom2;
			
			top2 = 15 + 60;
			left2 = 210;
			bottom2 = 65 + 60;
			right2 = 270;
			RectF rect2 = new RectF(left2, top2, right2, bo
			ttom2);
	     	canvas.drawRoundRect(rect2, 5, 5, paint);*/
			
			
			// draw a circle that shows the tower's range
    		Paint gridpaint2 = new Paint();
			gridpaint2.setARGB(40, 255, 255, 255);
			canvas.drawCircle(GameModel.GAME_TILE_SIZE*((float)selectedTower.getX()/ GameModel.GAME_TILE_SIZE + (selectedTower.getWidth()/2)), GameModel.GAME_TILE_SIZE*((float)selectedTower.getY() / GameModel.GAME_TILE_SIZE + (selectedTower.getHeight() / 2)), selectedTower.getRange(), gridpaint2);
		
			
    		canvas.drawBitmap(mBitMapCache.get(R.drawable.upgrade), 70 , 40 , null);


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