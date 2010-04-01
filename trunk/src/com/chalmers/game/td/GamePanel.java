package com.chalmers.game.td;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chalmers.game.td.units.Tower;
import com.chalmers.game.td.R;

import com.chalmers.game.td.units.Mob;
import com.chalmers.game.td.units.Projectile;
import com.chalmers.game.td.units.SlowTower;
import com.chalmers.game.td.units.Snowball;
import com.chalmers.game.td.units.SplashTower;
import com.chalmers.game.td.units.Mob.MobType;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
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

	private static final int STATE_RUNNING = 1;
	private static final int STATE_GAMEOVER = 2;
	private static final int STATE_WIN = 3;

	private int GAME_STATE = STATE_RUNNING;

	/** Thread which contains our game loop. */
	private GameThread mGameThread;

	/** Model which contains the game model  */
	private GameModel mGameModel;

	private MobFactory	mobFactory;

	/** Cache variable for all used images. */
	private Map<Integer, Bitmap> mBitMapCache = new HashMap<Integer, Bitmap>();


	/** Current x and y cord. for the touched tower     */
	private int tx;
	private int ty;

	private Tower currentTower;
	private Tower selectedTower;
	private Snowball currentSnowball;


	/** Keeps track of the delay between creation of Mobs in waves */
	private static final int MOB_DELAY_MAX = 30;
	private int mMobDelayI = 0;

	// Graphic elements used in the GUI
	private static final RectF sellButton = new RectF(90,180,150,230);
	private static final RectF upgradeButton = new RectF(165,180,300,230);
	private static final RectF selectedTowerBox = new RectF(70,50,320,240);
	private static final RectF buttonGroup = new RectF(450,0,480,320);
	private static final RectF button1 = new RectF(420,15,475,65);
	private static final RectF button2 = new RectF(420,15+60,475,65+60);
	private static final RectF button3 = new RectF(420,15+120,475,65+120);
	private static final RectF button4 = new RectF(420,15+180,475,65+180);
	private static final RectF button5 = new RectF(420,15+240,475,65+240);

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
	private static final Paint boxTextPaintTitle = new Paint();
	private static final Paint snowPaint = new Paint();
	private static final Paint borderPaint = new Paint();

	/** Debug */
	TDDebug debug;    


	// TODO accelerometer stuff
	private SensorManager mSensorManager;
	private SensorEvent latestSensorEvent;

	// use this to make the phone vibrate. vibrator.vibrate(int time);
	private Vibrator vibrator;

	private boolean accelerometerSupported;
	private boolean showTooltip;
	private boolean allowBuild;



	protected Tower Tower1 = new Tower(0,0);
	protected SplashTower Tower2 = new SplashTower(0,0);
	protected SlowTower Tower3 = new SlowTower(0,0);




	/**
	 * Constructor called on instantiation.
	 * @param context Context of calling activity.
	 */
	public GamePanel(Context context) {

		super(context);

		// makes sure the screen can't turn off while playing
		setKeepScreenOn(true);


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

		// do settings to all paint objects used in the GUI
		setupPaint();

		// get a reference to the vibrator in the phone
		vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

		// start listening to accelerometer events
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		accelerometerSupported = !mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).isEmpty();

		if (accelerometerSupported)
			mSensorManager.registerListener(mAccelerometerListener,
					mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_FASTEST);

		// start listening to telephone events (incoming calls etc)
		((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
		.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);



	}

	/**
	 * Handle accelerometer events
	 */
	private SensorEventListener mAccelerometerListener = new SensorEventListener() {

		public void onAccuracyChanged(Sensor sensor, int accuracy) {}

		public void onSensorChanged(SensorEvent event) {
			latestSensorEvent = event;
		}

	};

	/**
	 * Listener that handles telephone events
	 */
	private PhoneStateListener mPhoneListener = new PhoneStateListener() {

		@Override 
		public void onCallStateChanged(int state, String incomingNumber) { 
			super.onCallStateChanged(state, incomingNumber); 

			switch (state){ 
			case TelephonyManager.CALL_STATE_RINGING: 
				Log.d("PhoneState", "ringing"); 
				// TODO handle incoming calls
				// maybe pause and such and such
				break; 

			case TelephonyManager.CALL_STATE_IDLE: 
				Log.d("PhoneState", "idle"); 
				break; 

			case TelephonyManager.CALL_STATE_OFFHOOK : 
				Log.d("PhoneState", "offhook"); 
				break;
			} 

		}
	};


	/**
	 * Fill the bitmap cache.
	 */
	private void fillBitmapCache() {
		mBitMapCache.put(R.drawable.icon, BitmapFactory.decodeResource(getResources(), R.drawable.icon));
		mBitMapCache.put(R.drawable.abstrakt, BitmapFactory.decodeResource(getResources(), R.drawable.abstrakt));
		mBitMapCache.put(R.drawable.wallpaper, BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper));
		mBitMapCache.put(R.drawable.scissors, BitmapFactory.decodeResource(getResources(), R.drawable.scissors));
		mBitMapCache.put(R.drawable.paper, BitmapFactory.decodeResource(getResources(), R.drawable.paper));
		mBitMapCache.put(R.drawable.basictower, BitmapFactory.decodeResource(getResources(), R.drawable.basictower));
		mBitMapCache.put(R.drawable.basictower2, BitmapFactory.decodeResource(getResources(), R.drawable.basictower2));
		mBitMapCache.put(R.drawable.basictower3, BitmapFactory.decodeResource(getResources(), R.drawable.basictower3));
		mBitMapCache.put(R.drawable.basictower4, BitmapFactory.decodeResource(getResources(), R.drawable.basictower4));
		mBitMapCache.put(R.drawable.splashtower, BitmapFactory.decodeResource(getResources(), R.drawable.splashtower));
		mBitMapCache.put(R.drawable.splashtower2, BitmapFactory.decodeResource(getResources(), R.drawable.splashtower2));
		mBitMapCache.put(R.drawable.splashtower3, BitmapFactory.decodeResource(getResources(), R.drawable.splashtower3));
		mBitMapCache.put(R.drawable.splashtower4, BitmapFactory.decodeResource(getResources(), R.drawable.splashtower4));
		mBitMapCache.put(R.drawable.slowtower, BitmapFactory.decodeResource(getResources(), R.drawable.slowtower));
		mBitMapCache.put(R.drawable.slowtower2, BitmapFactory.decodeResource(getResources(), R.drawable.slowtower2));
		mBitMapCache.put(R.drawable.slowtower3, BitmapFactory.decodeResource(getResources(), R.drawable.slowtower3));
		mBitMapCache.put(R.drawable.slowtower4, BitmapFactory.decodeResource(getResources(), R.drawable.slowtower4));
		mBitMapCache.put(R.drawable.smaller, BitmapFactory.decodeResource(getResources(), R.drawable.smaller));
		mBitMapCache.put(R.drawable.small, BitmapFactory.decodeResource(getResources(), R.drawable.small));
		mBitMapCache.put(R.drawable.man2, BitmapFactory.decodeResource(getResources(), R.drawable.man2));
		mBitMapCache.put(R.drawable.b, BitmapFactory.decodeResource(getResources(), R.drawable.b));
		mBitMapCache.put(R.drawable.upgrade, BitmapFactory.decodeResource(getResources(), R.drawable.upgrade));
		mBitMapCache.put(R.drawable.base, BitmapFactory.decodeResource(getResources(), R.drawable.base));
		mBitMapCache.put(R.drawable.money, BitmapFactory.decodeResource(getResources(), R.drawable.money));
		mBitMapCache.put(R.drawable.lives, BitmapFactory.decodeResource(getResources(), R.drawable.lives));
		mBitMapCache.put(R.drawable.snowmap, BitmapFactory.decodeResource(getResources(), R.drawable.snowmap));
		mBitMapCache.put(R.drawable.penguinmob, BitmapFactory.decodeResource(getResources(), R.drawable.penguinmob));
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


			switch (GAME_STATE) {
			case STATE_RUNNING:
				// store the coordinates of the event
				// change the x coordinate with an offset of -60 pixels
				tx = (int) event.getX() - 60;
				ty = (int) event.getY();

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:

					// If the user has selected a Tower
					if (selectedTower != null) {

						// Upgrade button pressed
						if (upgradeButton.contains(event.getX(), event.getY())) {

							if (mGameModel.currentPlayer.getMoney() >= selectedTower.getUpgradeCost()) {
								mGameModel.currentPlayer.changeMoney(-selectedTower.getUpgradeCost());
								selectedTower.upgrade();
							}
						} else if (sellButton.contains(event.getX(), event.getY()) ) {
							// Sell button pressed
							mGameModel.currentPlayer.changeMoney(selectedTower.sell());
							mGameModel.removeTower(selectedTower);
							selectedTower = null;
						} else 
							selectedTower = null;


					} else {
						// if the user has NOT selected a tower

						allowBuild = false;

						// game field clicked
						if (event.getX() < 410) {
							showTooltip = false;

							// if a tower was clicked, mark it as selected
							for (int i = 0; i < mGameModel.mTowers.size(); i++){
								Tower t = mGameModel.mTowers.get(i);

								if (t.selectTower(event.getX(), event.getY())){
									selectedTower = t;
									break;
								}
							}

						} else if(button1.contains(event.getX(),event.getY())) {
							// button 1
							if (Tower1.getCost() <= mGameModel.currentPlayer.getMoney()) {
								allowBuild = true;
							}	
							currentTower = new Tower(tx ,ty);
							showTooltip = true;
						} else if(button2.contains(event.getX(),event.getY())) {
							// button 2
							if (Tower2.getCost() <= mGameModel.currentPlayer.getMoney()) {
								allowBuild = true;
							}	

							currentTower = new SplashTower(tx ,ty);
							showTooltip = true;

						} else if(button3.contains(event.getX(),event.getY())) {
							// button 3
							if (Tower3.getCost() <= mGameModel.currentPlayer.getMoney()) {
								allowBuild = true;
							}	
							currentTower = new SlowTower(tx ,ty);
							showTooltip = true;
						} else if(button4.contains(event.getX(),event.getY())) {
							// button 4
							if (accelerometerSupported)
								currentSnowball = new Snowball(tx,ty);

						} else if(button5.contains(event.getX(),event.getY())) {
							// button 5 TODO remove this when done

							Mob mTemp = new Mob(MobType.ARMORED);
							Path mTempPath = Path.getInstance();
							mTemp.setPath(mTempPath);

							mGameModel.mMobs.add(mTemp);
						}
					}

					break;
				case MotionEvent.ACTION_MOVE:


					if(currentTower != null){
						showTooltip = buttonGroup.contains(event.getX(),event.getY());
						if(!showTooltip && !allowBuild) {
							currentTower = null;
						} else  {
							currentTower.setX(tx);
							currentTower.setY(ty);
						}
					} else if (currentSnowball != null) {
						currentSnowball.setX(tx);
						currentSnowball.setY(ty);
					}
					break;

				case MotionEvent.ACTION_UP:
					//if a tower is placed on the game field
					if(currentTower != null) {

						// if building is allowed
						if (!buttonGroup.contains(event.getX(), event.getY()) && allowBuild) {

							// build the tower and remove money from player
							mGameModel.buildTower(currentTower, 
									(int)currentTower.getX() / GameModel.GAME_TILE_SIZE,
									(int)currentTower.getY() / GameModel.GAME_TILE_SIZE);
							mGameModel.currentPlayer.changeMoney(-currentTower.getCost());
							currentTower = null;
						}
					} else if (currentSnowball != null) {
						// if a snowball is being placed
						mGameModel.mSnowballs.add(currentSnowball);
						currentSnowball = null;
					}
					break;
				}
				break;
				
			case STATE_GAMEOVER:
//				TODO handle input when in "GAMEOVER" state
//				two buttons? "New Game" and "exit" maybe? yes? no? yes?
//				
//				switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:
//					break;
//				case MotionEvent.ACTION_MOVE:
//					break;
//				case MotionEvent.ACTION_UP:
//					break;
//				}
				break;
			case STATE_WIN:
//				TODO handle input when in "WIN" state
//				two buttons? "New Game" and "exit" maybe? yes? no? yes?
//				
//				switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:
//					break;
//				case MotionEvent.ACTION_MOVE:
//					break;
//				case MotionEvent.ACTION_UP:
//					break;
//				}
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

		if (GAME_STATE == STATE_RUNNING) {
			Mob mNewMob = createMobs();
			if (mNewMob != null) {

				mGameModel.mMobs.add(mNewMob);
				Log.v("GAME MOBS", "Added new mob of type: "
						+ mNewMob.getType().toString());
			}

			/*
			 * for every tower:
			 * 	create a new Projectile set to a Mob that the Tower can reach
			 *  and add that to the list of Projectiles in the GameModel
			 * 
			 * tryToShoot() returns null if the tower can't reach any mob
			 */
			for (int i = 0; i < mGameModel.mTowers.size(); i++) {
				Tower t = mGameModel.mTowers.get(i);
				List<Projectile> newProjectiles = null;

				if (mGameModel.mMobs.size() > 0) 
					newProjectiles = t.tryToShoot(mGameModel);

				if (newProjectiles != null) 
					mGameModel.mProjectiles.addAll(newProjectiles);
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
			 * For every snowball:
			 * update position
			 * do damage to any mob it hits
			 */
			for (int i = 0; i < mGameModel.mSnowballs.size(); i++) {
				Snowball s = mGameModel.mSnowballs.get(i);

				// if the ball is moving ON a tower in the game field
				// lower it's speed
				s.setSlowed(false);

				for (int j = 0; j < mGameModel.mTowers.size(); j++) {
					Tower t = mGameModel.mTowers.get(j);
					Coordinate mobCoordinate = new Coordinate(t.getX()+16,t.getY()+16);
					double distance = Coordinate.getSqrDistance(s.getCoordinates(), mobCoordinate);

					if (distance < 10 + s.getCharges() + 16)
						s.setSlowed(true);
				}


				// update position with accelerometer
				s.updatePosition(latestSensorEvent);

				// read what mobs are hit
				List<Mob> deadMobs = s.getCollidedMobs(mGameModel.mMobs);

				// handle mobs that were hit
				for (int j = 0; j < deadMobs.size(); j++) {
					Mob m = deadMobs.get(j);
					mGameModel.currentPlayer.changeMoney(m.getReward());
				}
				mGameModel.mMobs.removeAll(deadMobs);

				// if the snowball is out of charges, remove it
				if (s.getCharges() <= 0) {
					mGameModel.mSnowballs.remove(s);
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
				if (!m.updatePosition()) {
					mGameModel.mMobs.remove(m);
					mGameModel.currentPlayer.removeLife();
				}

				// handle mob death
				if (m.getHealth() <= 0) {
					mGameModel.currentPlayer.changeMoney(m.getReward());
					mGameModel.mMobs.remove(m);

				}
			}

			// If the player has 0 or less lives remaining, change game state
			if (mGameModel.currentPlayer.getRemainingLives() <= 0) {
				GAME_STATE = STATE_GAMEOVER;
			}

			// TODO check if the user has won
			if (mobFactory.hasMoreMobs() == false && mGameModel.mMobs.isEmpty()) {
				GAME_STATE = STATE_WIN;
			}
		}

	}


	/**
	 * Draw on the SurfaceView.
	 * Order:
	 * <ul>
	 *  <li>Game map and environments</li>
	 *  <li>Mobs</li>
	 *  <li>Snowballs</li>
	 *  <li>Towers></li>
	 *  <li>Projectiles</li>
	 *  <li>Buttons</li>
	 *  <li>Current Tower/Snowball</li>
	 *  <li>Stats</li>
	 *  <li>Win- and Lose screen</li>
	 * </ul>
	 */
	@Override
	public void onDraw(Canvas canvas) {
		// TODO: Dela in subtask i subfunktioner. Ser snyggare ut! / Jonas
		// DONE AND DONE /Fredrik

		drawBackground(canvas);
		drawMobs(canvas);
		drawTowers(canvas);
		drawSnowballs(canvas);
		drawProjectiles(canvas);
		drawButtons(canvas);
		drawStatisticsText(canvas);


		switch (GAME_STATE) {
		case STATE_RUNNING:
			// if a tower is being bought
			// draw either the tooltip for it, or how it would be placed.
			if (currentTower != null) {
				if (showTooltip)
					drawTooltip(canvas);
				else
					drawCurrentTower(canvas);
			}

			// draw current snowball
			if (currentSnowball != null) {
				canvas.drawCircle(
						(int)currentSnowball.getX(),
						(int)currentSnowball.getY(),
						10 + currentSnowball.getCharges(),snowPaint);
			}

			// if a tower is selected for upgrades and such and such
			if(selectedTower != null){	
				drawUpgradeWindow(canvas);
			}
			break;
			
		case STATE_GAMEOVER: // loser screen
			canvas.drawRoundRect(selectedTowerBox,10,10,selectedTowerBoxPaint);
			canvas.drawText("YOU LOSE! SUCKER!",100,150 ,boxTextPaint);
			// TODO draw some buttons, "new game" and "exit" maybe. oh and show some stats
			break;
			
		case STATE_WIN: // winner screen
			canvas.drawRoundRect(selectedTowerBox,10,10,selectedTowerBoxPaint);
			canvas.drawText("YOU ARE WINRAR!",100,150 ,boxTextPaint);
			// TODO draw some button. show stats etc etc osv and so on.
			break;
		}

	}



	private void drawStatisticsText(Canvas canvas) {
		// draw debug messages in the top left corner
		canvas.drawText("FPS: "+Float.toString(debug.getFPS()) + " Mobs:"+ mGameModel.mMobs.size()+
				" Proj:"+mGameModel.mProjectiles.size() + " Towers:"+ mGameModel.mTowers.size(), 10, 320,textPaint);

		// show stats of the player    	
		canvas.drawBitmap(mBitMapCache.get(R.drawable.money),20,0, null);
		canvas.drawText("" + (int)mGameModel.currentPlayer.getMoney(), 45, 20, textPaint);
		canvas.drawBitmap(mBitMapCache.get(R.drawable.lives), 100, 0, null);
		canvas.drawText("" + mGameModel.currentPlayer.getRemainingLives(), 125, 20, textPaint);
		canvas.drawText(mobFactory.getWaveNr() + "/" + mobFactory.getTotalNrOfWaves(), 170, 20, textPaint); //TODO: Count the wave
		canvas.drawText("Score: 0", 230, 20, textPaint); //TODO: Count score
		

	}

	private void drawUpgradeWindow(Canvas canvas) {
		// draw a circle that shows the tower's range
		canvas.drawCircle(
				GameModel.GAME_TILE_SIZE * ((float)selectedTower.getX() / GameModel.GAME_TILE_SIZE + (selectedTower.getWidth()/2)),
				GameModel.GAME_TILE_SIZE * ((float)selectedTower.getY() / GameModel.GAME_TILE_SIZE + (selectedTower.getHeight()/2)),
				selectedTower.getRange(), rangeIndicationPaint);


		// draw box for the selected tower
		canvas.drawRoundRect(selectedTowerBox,10,10,selectedTowerBoxPaint);

		canvas.drawBitmap(mBitMapCache.get(selectedTower.getImage()), 100, 80,null);

		canvas.drawText(selectedTower.getName(), 170, 90, boxTextPaintTitle);
		canvas.drawText("Level " + selectedTower.getLevel(), 170, 117, boxTextPaint);
		canvas.drawText("Damage: " + selectedTower.getDamage(), 170, 139, boxTextPaint);
		canvas.drawText("Range: " + selectedTower.getRange(), 170, 161, boxTextPaint);

		canvas.drawRoundRect(sellButton,10,10,buttonBoxPaint);
		canvas.drawRoundRect(upgradeButton,6,6,buttonBoxPaint);

		canvas.drawText("Sell", sellButton.left+10, sellButton.top+(sellButton.height()/2), boxTextPaint);
		canvas.drawText("Upgrade for " + selectedTower.getUpgradeCost() + "$",
				upgradeButton.left+10, upgradeButton.top+(sellButton.height()/2), boxTextPaint);

	}

	private void drawCurrentTower(Canvas canvas) {


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
		// one color if it can be placed on current location, another if can't
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

	private void drawTooltip(Canvas canvas) {

		// draw tooltip for the current tower
		canvas.drawRoundRect(selectedTowerBox,10,10, selectedTowerBoxPaint);

		canvas.drawBitmap(mBitMapCache.get(currentTower.getImage()), 100, 80,null);

		canvas.drawText(currentTower.getName(), 170, 90, boxTextPaintTitle);
		canvas.drawText("Level " + currentTower.getLevel(), 170, 117, boxTextPaint);
		canvas.drawText("Damage: " + currentTower.getDamage(), 170, 139, boxTextPaint);
		canvas.drawText("Range: " + currentTower.getRange(), 170, 161, boxTextPaint);

		canvas.drawText("Drag buy this tower!", 130, 180, boxTextPaint);
	}

	private void drawButtons(Canvas canvas) {

		Paint paint = new Paint();
		paint.setARGB(100,100,100,100);
		paint.setStyle(Paint.Style.FILL);

		canvas.drawRoundRect(button1, 5, 5, paint);
		canvas.drawRoundRect(button2, 5, 5, paint);
		canvas.drawRoundRect(button3, 5, 5, paint);
		canvas.drawRoundRect(button4, 5, 5, paint);
		canvas.drawRoundRect(button5, 5, 5, paint);


		Paint paintalfa = new Paint();

		//if the tower build buttons should be "unavaliable" or not
		if(Tower1.getCost() >= mGameModel.currentPlayer.getMoney()) {
			paintalfa.setAlpha(100);
		} else {
			paintalfa.setAlpha(255);
		}
		canvas.drawBitmap(mBitMapCache.get(R.drawable.basictower),432,25,paintalfa);

		if(Tower2.getCost() >= mGameModel.currentPlayer.getMoney()) {
			paintalfa.setAlpha(100);
		} else {
			paintalfa.setAlpha(255);
		}
		canvas.drawBitmap(mBitMapCache.get(R.drawable.splashtower),432,85,paintalfa);

		if(Tower3.getCost() >= mGameModel.currentPlayer.getMoney()) {
			paintalfa.setAlpha(100);
		} else {
			paintalfa.setAlpha(255);
		}
		canvas.drawBitmap(mBitMapCache.get(R.drawable.slowtower),432,145,paintalfa);

		canvas.drawBitmap(mBitMapCache.get(R.drawable.penguinmob), 437,270,null);

	}

	private void drawProjectiles(Canvas canvas) {
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
	}

	private void drawTowers(Canvas canvas) {
		// draw all towers
		for (int i = 0; i < mGameModel.mTowers.size(); i++) {
			Tower t = mGameModel.mTowers.get(i);
			canvas.drawBitmap(mBitMapCache.get(t.getImage()), (int) t.getX() , (int) t.getY() , null);
		}
	}

	private void drawSnowballs(Canvas canvas) {
		// draw snowballs
		for (int i = 0; i < mGameModel.mSnowballs.size(); i++) {
			Snowball s = mGameModel.mSnowballs.get(i);

			canvas.drawCircle((float)s.getX(), (float)s.getY(), 10 + s.getCharges(), snowPaint);
			canvas.drawCircle((float)s.getX(), (float)s.getY(), 10 + s.getCharges(), borderPaint);
		}
	}

	private void drawMobs(Canvas canvas) {
		// draw all mobs
		for (int i = mGameModel.mMobs.size()-1; i >= 0; i--) {
			Mob m = mGameModel.mMobs.get(i);

			canvas.drawBitmap(mBitMapCache.get(R.drawable.penguinmob), (int) m.getX() , (int) m.getY() , null);

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
	}

	private void drawBackground(Canvas canvas) {
		// draw the background
		canvas.drawBitmap(mBitMapCache.get(R.drawable.snowmap), 0 , 0, null);

		// dra the "end-point-base"
		canvas.drawBitmap(mBitMapCache.get(R.drawable.base),403,0,null);
	}

	/**
	 * Configures all Paint-objects used in onDraw().
	 */
	private void setupPaint() {

		// set text size of the FPS meter and such and such
		textPaint.setTextSize(18);

		// set color of the selected tower box
		selectedTowerBoxPaint.setARGB(90, 51, 51, 51);

		// set color of the upgrade- and sell buttons in the selected tower box
		buttonBoxPaint.setARGB(255, 51, 51, 51);

		// set text size and color of the text in selected tower box
		boxTextPaint.setARGB(255, 255, 255, 255);
		boxTextPaint.setTextSize(16);


		boxTextPaintTitle.setARGB(255, 255, 255, 255);
		boxTextPaintTitle.setTextSize(22);


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

		snowPaint.setARGB(255, 159, 182, 205);

		borderPaint.setARGB(255, 0, 0, 0);
		borderPaint.setStyle(Paint.Style.STROKE);
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