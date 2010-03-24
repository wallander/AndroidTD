package com.chalmers.game.td;

import java.util.HashMap;
import java.util.Map;

import com.chalmers.game.td.units.Tower;
import com.chalmers.game.td.R;

import com.chalmers.game.td.units.Mob;
import com.chalmers.game.td.units.Projectile;
import com.chalmers.game.td.units.SlowTower;
import com.chalmers.game.td.units.SplashTower;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
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
    
    // Graphic elements used in the GUI
    private static final RectF sellButton = new RectF(90,180,150,230);
	private static final RectF upgradeButton = new RectF(165,180,300,230);
	private static final RectF selectedTowerBox = new RectF(70,50,320,240);
    

	// Paints
	private static final Paint buttonBoxPaint = new Paint();
	private static final Paint boxTextPaint = new Paint();
	private static final Paint linePaint = new Paint();
	private static final Paint selectedTowerBoxPaint = new Paint();
	private static final Paint textPaint = new Paint();
	private static final Paint rangeIndicationPaint = new Paint();
	private static final Paint noRangeIndicationPaint = new Paint();
	private static final Paint gridpaint = new Paint();
	private static final Paint healthBarPaint = new Paint();
	
	
    /** Debug */
    TDDebug debug;    


    /**
     * Constructor called on instantiation.
     * @param context Context of calling activity.
     */
    public GamePanel(Context context) {
    	 
        super(context);
      

        debug = new TDDebug();
        debug.InitGameTime();
        
        
       
        
        
        
        mobFactory = MobFactory.getInstance(); 
        mobFactory.setContext(context); 
        Path.getInstance().setTrackPath(0); // TODO remove fulkod
        
        mGameModel = new GameModel();
        
        fillBitmapCache();
        getHolder().addCallback(this);
        mGameThread = new GameThread(this);
        setFocusable(true);
        
        setupPaint();
        
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
        mBitMapCache.put(R.drawable.rock2, BitmapFactory.decodeResource(getResources(), R.drawable.rock2));
        
        

    }

    /**
     * 
     * Processes MotionEvents. This is basically where all user input is handled
     * during the game play.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	
    	
        synchronized (getHolder()) {
            
        	
        	switch (event.getAction()) {
        	case MotionEvent.ACTION_DOWN:
        		
        		if (selectedTower != null) {
        			
        			if (upgradeButton.contains(event.getX(), event.getY())) {
        				
        				if (mGameModel.currentPlayer.getMoney() >= selectedTower.getUpgradeCost()) {
            				
        					selectedTower.upgrade();
        					mGameModel.currentPlayer.setMoney(
        							mGameModel.currentPlayer.getMoney() - selectedTower.getUpgradeCost());
        			
        				}
        				
        			} else if (sellButton.contains(event.getX(), event.getY()) ) {
        				
        				mGameModel.currentPlayer.setMoney(
    							mGameModel.currentPlayer.getMoney() + selectedTower.sell());
        				mGameModel.removeTower(selectedTower);
        				selectedTower = null;
        			} else {
        				selectedTower = null;
        			}
	
        			
        		} else {
        			selectedTower = null;
	        		// If the ACTION_DOWN event was not in the button section but on a tower, select the clicked tower
	            	if (event.getX() < 410) {
	            		
	            		for (int i = 0; i < mGameModel.mTowers.size(); i++){
	            			Tower t = mGameModel.mTowers.get(i);
	            			
	            			if (t.selectTower(event.getX(), event.getY())){
		            			selectedTower = t;
	            				break;
	            			}
	            		}
	            	}
	
	            	
	            	// button 1,
	                if(event.getY() > 15  && event.getY() < 65 && event.getX() > 410) {
	                	tx = (int) event.getX() - 60;
	
	                	ty = (int) event.getY();
	                	currentTower = new Tower(tx ,ty);
	            		currentTower.setSize(2);
	                }
	                
	                // button 2
	                if(event.getY() > 15+60  && event.getY() < 65+60 && event.getX() > 410) {
	                	tx = (int) event.getX() - 60;
	                	
	                	ty = (int) event.getY();
	                	currentTower = new SplashTower(tx ,ty);
	            		currentTower.setSize(2);

	                }
	                
	                // button 3
	                if(event.getY() > 15+120  && event.getY() < 65+120 && event.getX() > 410) {
	                	tx = (int) event.getX() - 60;
	                	
	                	ty = (int) event.getY();
	                	currentTower = new SlowTower(tx ,ty);
	            		currentTower.setSize(2);
	                	
	                }
	                
	                // button 4
	                if(event.getY() > 15+180  && event.getY() < 65+180 && event.getX() > 410) {

	                }
	                
	                // button 5
	                if(event.getY() > 15+240  && event.getY() < 65+240 && event.getX() > 410) {
	                	
	                	mGameModel.mMobs.add(new Mob(Path.getInstance(), null));

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
    		
    	return mobFactory.getNextMob(0); // TODO do not use hard code..
    	
    		
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
    			proj = t.tryToShoot(mGameModel);
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
    		// TODO: solve this in a better way
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
    			mGameModel.currentPlayer.removeLife();
    		}
    		
    		// TODO handle mob death
    		if (m.getHealth() <= 0) {
//    			give money to the player
    			mGameModel.currentPlayer.setMoney(mGameModel.currentPlayer.getMoney() + m.getReward());

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
    
    @SuppressWarnings("static-access")
	@Override
    public void onDraw(Canvas canvas) {
    	// TODO: Dela in subtask i subfunktioner. Ser snyggare ut! / Jonas
    	
        // draw the background
    	canvas.drawBitmap(mBitMapCache.get(R.drawable.abstrakt), 0 , 0, null);
    	
    	// draw debug messages in the top left corner
    	canvas.drawText("FPS: "+Float.toString(debug.getFPS()) + " Mobs:"+ mGameModel.mMobs.size()+
    			" Proj:"+mGameModel.mProjectiles.size() + " Towers:"+ mGameModel.mTowers.size(), 10, 10,textPaint);
    	
    	// show stats of the player    	
    	canvas.drawText("Player: " + mGameModel.currentPlayer.getName() + " Money: " 
    			+ mGameModel.currentPlayer.getMoney() + " Remaining lives: " +  mGameModel.currentPlayer.getRemainingLives(), 
    			20, 300, textPaint);
	
    	// draw all mobs
     	for (int i = 0; i < mGameModel.mMobs.size(); i++) {
     		Mob m = mGameModel.mMobs.get(i);
     		
    		canvas.drawBitmap(mBitMapCache.get(R.drawable.man), (int) m.getX() , (int) m.getY() , null);
    		
    		int hpRatio = (int)(255* (double)m.getHealth() / (double)m.getMaxHealth());
    		
    		// drawing health bars for each mob, first a black background
    		healthBarPaint.setARGB(255, 0, 0, 0);
    		canvas.drawRect(
    				(float)m.getX() - 2,
    				(float) m.getY() - 5,
    				(float) (m.getX() + 24),
    				(float) m.getY() - 2,
    				healthBarPaint);
    		
    		// draw current health on the health bar
    		healthBarPaint.setARGB(255, 255 - hpRatio, hpRatio, 0);
    		canvas.drawRect(
    				(float)m.getX() - 2,
    				(float) m.getY() - 5,
    				(float) (m.getX() + (24 * hpRatio/255)),
    				(float) m.getY() - 2,
    				healthBarPaint);
    		
    		
     	}
     	
    	// draw all towers
     	for (int i = 0; i < mGameModel.mTowers.size(); i++) {
     		Tower t = mGameModel.mTowers.get(i);
    		canvas.drawBitmap(mBitMapCache.get(t.getImage()), (int) t.getX() , (int) t.getY() , null);
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
			
			// draw some temporary images for the buttons
			// TODO fix some image buttons ffs
			canvas.drawBitmap(mBitMapCache.get(R.drawable.rock),425,25,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.rock2),425,85,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.big),425,145,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.man), 430,270,null);
			
		} else {
			
			// draw the chosen tower
			canvas.drawBitmap(
					mBitMapCache.get(currentTower.getImage()), GameModel.GAME_TILE_SIZE*(tx / GameModel.GAME_TILE_SIZE) ,
					GameModel.GAME_TILE_SIZE*(ty / GameModel.GAME_TILE_SIZE) , null);
			
			
			// draw a red transparent rectangle on every occupied tile
			for (Point p : mGameModel.mOccupiedTilePositions) {
				canvas.drawRect(
						p.x*GameModel.GAME_TILE_SIZE,
						p.y*GameModel.GAME_TILE_SIZE,
						(1+p.x)*GameModel.GAME_TILE_SIZE,
						(1+p.y)*GameModel.GAME_TILE_SIZE,
						gridpaint);
			}
			
			// draw a circle that shows the tower's range
			if (mGameModel.canAddTower(currentTower)) {
				canvas.drawCircle(
						GameModel.GAME_TILE_SIZE*(tx / GameModel.GAME_TILE_SIZE + (currentTower.getWidth()/2)),
						GameModel.GAME_TILE_SIZE*(ty / GameModel.GAME_TILE_SIZE + (currentTower.getHeight() / 2)),
						currentTower.getRange(),
						rangeIndicationPaint);
			} else {
				canvas.drawCircle(
						GameModel.GAME_TILE_SIZE*(tx / GameModel.GAME_TILE_SIZE + (currentTower.getWidth()/2)),
						GameModel.GAME_TILE_SIZE*(ty / GameModel.GAME_TILE_SIZE + (currentTower.getHeight() / 2)),
						currentTower.getRange(),
						noRangeIndicationPaint);
			}
		
		}
		
		// if a tower is selected for upgrades and such and such
		if(selectedTower != null){	
			
			// draw a circle that shows the tower's range
			canvas.drawCircle(
					GameModel.GAME_TILE_SIZE * ((float)selectedTower.getX() / GameModel.GAME_TILE_SIZE + (selectedTower.getWidth()/2)),
					GameModel.GAME_TILE_SIZE * ((float)selectedTower.getY() / GameModel.GAME_TILE_SIZE + (selectedTower.getHeight()/2)),
					selectedTower.getRange(), rangeIndicationPaint);
		
			
    		// draw box for the selected tower
    		canvas.drawRoundRect(selectedTowerBox,10,10,selectedTowerBoxPaint);
    		
    		canvas.drawBitmap(mBitMapCache.get(selectedTower.getImage()), 100, 80,null);

    		canvas.drawLine(150, 60, 150, 160, linePaint);
    		canvas.drawLine(80, 165, 310, 165, linePaint);
    		

    		canvas.drawText(selectedTower.getName(), 170, 80, boxTextPaint);
    		canvas.drawText("Level " + selectedTower.getLevel(), 170, 102, boxTextPaint);
    		canvas.drawText("Damage: " + selectedTower.getDamage(), 170, 124, boxTextPaint);
    		canvas.drawText("Range: " + selectedTower.getRange(), 170, 146, boxTextPaint);
    		
    		canvas.drawRoundRect(sellButton,10,10,buttonBoxPaint);
    		canvas.drawRoundRect(upgradeButton,6,6,buttonBoxPaint);
    		
    		canvas.drawText("Sell", sellButton.left+10, sellButton.top+(sellButton.height()/2), boxTextPaint);
    		canvas.drawText("Upgrade for " + selectedTower.getUpgradeCost() + "$",
    				upgradeButton.left+10, upgradeButton.top+(sellButton.height()/2), boxTextPaint);
    		
		}
		
    }

    /**
     * Configures all Paint-objects used in onDraw().
     */
    private void setupPaint() {
    	
    	// set text size of the FPS meter and such and such
    	textPaint.setTextSize(18);
    	
    	// set color of the selected tower box
		selectedTowerBoxPaint.setARGB(175, 51, 51, 51);
    	
    	// set color of the upgrade- and sell buttons in the selected tower box
    	buttonBoxPaint.setARGB(255, 51, 51, 51);

    	// set text size and color of the text in selected tower box
    	boxTextPaint.setARGB(255, 255, 255, 255);
		boxTextPaint.setTextSize(16);
		
		// set color and width of the lines in the selected tower box
		linePaint.setARGB(255, 255, 255, 0);
		linePaint.setStrokeWidth(5);
		
		// set color and style of the range indicators
		rangeIndicationPaint.setARGB(80, 255, 255, 255);
		rangeIndicationPaint.setStyle(Paint.Style.FILL);
		noRangeIndicationPaint.setARGB(80, 255, 0, 0);
		noRangeIndicationPaint.setStyle(Paint.Style.FILL);
    	
		// set color of the grid showing where you can put new towers
		gridpaint.setARGB(50,255,0,0);
		gridpaint.setStyle(Paint.Style.FILL);
		
		healthBarPaint.setStyle(Paint.Style.FILL);
		
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