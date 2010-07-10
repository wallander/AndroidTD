package com.chalmers.game.td;

import java.util.HashMap;
import java.util.Map;

import com.chalmers.game.td.units.Tower;
import com.chalmers.game.td.R;

import com.chalmers.game.td.units.AirTower;
import com.chalmers.game.td.units.BasicProjectile;
import com.chalmers.game.td.units.BasicTower;
import com.chalmers.game.td.units.Mob;
import com.chalmers.game.td.units.Projectile;
import com.chalmers.game.td.units.SlowTower;
import com.chalmers.game.td.units.Snowball;
import com.chalmers.game.td.units.SplashProjectile;
import com.chalmers.game.td.units.SplashTower;
import com.chalmers.game.td.units.SlowProjectile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
//import android.util.Log;
import android.view.KeyEvent;
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
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	/** Thread which contains our game loop. */
	private GameThread mGameThread;
	private MobFactory mMobFactory;

	/** Cache variable for all used images. */
	private Map<Integer, Bitmap> mBitMapCache = new HashMap<Integer, Bitmap>();


	/** Current x coordinate for the touched tower. */
	private int mTx;
	/** Current y coordinate for the touched tower. */
	private int mTy;
	


	private int mWateranimation = 0;

	private int menuPic = 0;
	private int mButtonBorder = 420;

	// Graphic elements used in the GUI

	private static final RectF sBtnSell = new RectF(110,185,180,230);
	private static final RectF sBtnUpgrade = new RectF(195,185,330,230);

	//box with options on. Used for tooltip and upgrade window

	private static final RectF sTransparentBox = new RectF(70,50,320,240);
	private static final RectF sBtn1 = new RectF(420,15,480,65);
	private static final RectF sBtn2 = new RectF(420,15+60,480,65+60);
	private static final RectF sBtn3 = new RectF(420,15+120,480,65+120);
	private static final RectF sBtn4 = new RectF(420,15+180,480,65+180);
	private static final RectF sBtn5 = new RectF(420,15+240,480,65+240);

	// Paints
	private static final Paint sPaintBtnBox = new Paint();
	private static final Paint sPaintBoxText = new Paint();
	private static final Paint sPaintBoxRed = new Paint();
	private static final Paint sPaintBoxGreen = new Paint();
	private static final Paint sPaintLine = new Paint();
	private static final Paint sPaintTransparentBox = new Paint();
	private static final Paint sPaintText = new Paint();
	private static final Paint sPaintTextWhite = new Paint();
	private static final Paint sPaintTextBlack = new Paint();
	private static final Paint rangeIndicationPaint = new Paint();
	private static final Paint noRangeIndicationPaint = new Paint();
	private static final Paint gridpaint = new Paint();
	private static final Paint healthBarPaint = new Paint();
	private static final Paint boxTextPaintTitle = new Paint();
	private static final Paint snowPaint = new Paint();
	private static final Paint borderPaint = new Paint();
	private static final Paint mBtnPaint = new Paint();
	private static final Paint sMoneyAfterDead = new Paint();
	private static final Paint sMoneyAfterDeadBg = new Paint();
	private static final Paint sMobPaint = new Paint();
	
	/** Debug */
	private TDDebug debug;    


	// accelerometer stuff
	private SensorManager mSensorManager;


	// use this to make the phone vibrate. vibrator.vibrate(int time);
	public static Vibrator mVibrator;

	private boolean mAccelerometerSupported;


	private Tower mTower1 = new BasicTower(0,0);
	private Tower mTower2 = new SplashTower(0,0);
	private Tower mTower3 = new SlowTower(0,0);
	private Tower mTower4 = new AirTower(0,0);

	private static final int mSnowballTreshold = 4000;
	private int mUsedSnowballs;

	private AudioManager mAudioManager; // Move to SoundManager? No, it is used to control volume

	/**
	 * Returns the MediaPlayer for which ever track is active
	 * 
	 * @param pTrack
	 */
	public MediaPlayer playTrackMusic(int pTrack) {

		switch(pTrack) {

		case 1:
			return SoundManager.getTrackOneMusic();

		case 2:
			return SoundManager.getTrackTwoMusic();

		case 3:
			return SoundManager.getTrackThreeMusic();

		case 4:
			return SoundManager.getTrackFourMusic();

		case 5:
			return SoundManager.getTrackFiveMusic();

		}

		return null;
	}

	public void updateSounds() {
		switch (GameModel.GAME_STATE) {
		case GameModel.STATE_RUNNING:

			try {
				if (GameModel.sMusicEnabled) {

					SoundManager.pauseMusic(SoundManager.getFastForwardMusic());
					SoundManager.playMusic(playTrackMusic(GameModel.getTrack()));
				}

			} catch (IllegalStateException ise) {
				SoundManager.initializeSound(getContext());
			}
			break;
		default:
			SoundManager.pauseMusic(playTrackMusic(GameModel.getTrack()));
			SoundManager.pauseMusic(SoundManager.getFastForwardMusic());
			break;
		}
	}

	/**
	 * Constructor called on instantiation.
	 * @param context Context of calling activity.
	 */
	public GameView(Context context) {

		super(context);		
		// makes sure the screen can't turn off while playing
		setKeepScreenOn(true);

		debug = new TDDebug();
		debug.InitGameTime();

		startTrack(GameModel.getTrack());

		SoundManager.initializeSound(context);

		fillBitmapCache();
		getHolder().addCallback(this);
		mGameThread = new GameThread(this);
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();
		// do settings to all paint objects used in the GUI
		setupPaint();

		mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

		// get a reference to the vibrator in the phone
		mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

		// start listening to accelerometer events
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		mAccelerometerSupported = !mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).isEmpty();

		if (mAccelerometerSupported)
			mSensorManager.registerListener(mAccelerometerListener,
					mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_FASTEST);

		// start listening to telephone events (incoming calls etc)
		((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
		.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

	}

	/**
	 * Resets the gamefield to the given track
	 */
	private void startTrack(int track){
		GameModel.setTrack(track);
		GameModel.initialize(getContext());

		// TODO Move to splash screen
		mMobFactory = MobFactory.getInstance(); 
		mMobFactory.setContext(getContext()); 

		GameModel.sCurrentPlayer.setCurrentScore(0);

		GameModel.setFast(false);

		GameModel.GAME_STATE = GameModel.STATE_RUNNING;
	}

	/**
	 * Handles accelerometer events
	 */
	private SensorEventListener mAccelerometerListener = new SensorEventListener() {

		public void onAccuracyChanged(Sensor sensor, int accuracy) {}

		public void onSensorChanged(SensorEvent event) {
			GameModel.mLatestSensorEvent = event;
		}
	};

	/**
	 * Handles telephony events
	 */
	private PhoneStateListener mPhoneListener = new PhoneStateListener() {

		@Override 
		public void onCallStateChanged(int state, String incomingNumber) { 
			super.onCallStateChanged(state, incomingNumber); 

			switch (state){ 
			case TelephonyManager.CALL_STATE_RINGING: 
				//Log.d("PhoneState", "ringing"); 
				// handle incoming calls
				GameModel.GAME_STATE = GameModel.STATE_PAUSED;
				break; 

			case TelephonyManager.CALL_STATE_IDLE: 
				//Log.d("PhoneState", "idle"); 
				break; 

			case TelephonyManager.CALL_STATE_OFFHOOK : 
				//Log.d("PhoneState", "offhook"); 
				break;
			}
		}
	};


	/**
	 * Fill the bitmap cache.
	 */
	private void fillBitmapCache() {
		mBitMapCache = new HashMap<Integer, Bitmap>();
		mBitMapCache.put(R.drawable.icon, 
				BitmapFactory.decodeResource(getResources(), R.drawable.icon));
		mBitMapCache.put(R.drawable.abstrakt, 
				BitmapFactory.decodeResource(getResources(), R.drawable.abstrakt));
		mBitMapCache.put(R.drawable.wallpaper, 
				BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper));
		mBitMapCache.put(R.drawable.snowball_small, 
				BitmapFactory.decodeResource(getResources(), R.drawable.snowball_small));
		mBitMapCache.put(R.drawable.snowball, 
				BitmapFactory.decodeResource(getResources(), R.drawable.snowball));
		mBitMapCache.put(R.drawable.paper, 
				BitmapFactory.decodeResource(getResources(), R.drawable.paper));
		mBitMapCache.put(R.drawable.basictower, 
				BitmapFactory.decodeResource(getResources(), R.drawable.basictower));
		mBitMapCache.put(R.drawable.basictower2, 
				BitmapFactory.decodeResource(getResources(), R.drawable.basictower2));
		mBitMapCache.put(R.drawable.basictower3, 
				BitmapFactory.decodeResource(getResources(), R.drawable.basictower3));
		mBitMapCache.put(R.drawable.basictower4, 
				BitmapFactory.decodeResource(getResources(), R.drawable.basictower4));
		mBitMapCache.put(R.drawable.splashtower, 
				BitmapFactory.decodeResource(getResources(), R.drawable.splashtower));
		mBitMapCache.put(R.drawable.splashtower2, 
				BitmapFactory.decodeResource(getResources(), R.drawable.splashtower2));
		mBitMapCache.put(R.drawable.splashtower3, 
				BitmapFactory.decodeResource(getResources(), R.drawable.splashtower3));
		mBitMapCache.put(R.drawable.splashtower4, 
				BitmapFactory.decodeResource(getResources(), R.drawable.splashtower4));
		mBitMapCache.put(R.drawable.slowtower, 
				BitmapFactory.decodeResource(getResources(), R.drawable.slowtower));
		mBitMapCache.put(R.drawable.slowtower2, 
				BitmapFactory.decodeResource(getResources(), R.drawable.slowtower2));
		mBitMapCache.put(R.drawable.slowtower3, 
				BitmapFactory.decodeResource(getResources(), R.drawable.slowtower3));
		mBitMapCache.put(R.drawable.slowtower4, 
				BitmapFactory.decodeResource(getResources(), R.drawable.slowtower4));
		mBitMapCache.put(R.drawable.smaller, 
				BitmapFactory.decodeResource(getResources(), R.drawable.smaller));
		mBitMapCache.put(R.drawable.small, 
				BitmapFactory.decodeResource(getResources(), R.drawable.small));
		mBitMapCache.put(R.drawable.man2, 
				BitmapFactory.decodeResource(getResources(), R.drawable.man2));
		mBitMapCache.put(R.drawable.b, 
				BitmapFactory.decodeResource(getResources(), R.drawable.b));
		mBitMapCache.put(R.drawable.upgrade, 
				BitmapFactory.decodeResource(getResources(), R.drawable.upgrade));
		mBitMapCache.put(R.drawable.base, 
				BitmapFactory.decodeResource(getResources(), R.drawable.base));
		mBitMapCache.put(R.drawable.basee, 
				BitmapFactory.decodeResource(getResources(), R.drawable.basee));
		mBitMapCache.put(R.drawable.money, 
				BitmapFactory.decodeResource(getResources(), R.drawable.money));
		mBitMapCache.put(R.drawable.lives, 
				BitmapFactory.decodeResource(getResources(), R.drawable.lives));
		mBitMapCache.put(R.drawable.map1, 
				BitmapFactory.decodeResource(getResources(), R.drawable.map1));
		mBitMapCache.put(R.drawable.map2, 
				BitmapFactory.decodeResource(getResources(), R.drawable.map2));
		mBitMapCache.put(R.drawable.map3, 
				BitmapFactory.decodeResource(getResources(), R.drawable.map3));
		mBitMapCache.put(R.drawable.map4, 
				BitmapFactory.decodeResource(getResources(), R.drawable.map4));
		mBitMapCache.put(R.drawable.map5, 
				BitmapFactory.decodeResource(getResources(), R.drawable.map5));
		mBitMapCache.put(R.drawable.penguinmob, 
				BitmapFactory.decodeResource(getResources(), R.drawable.penguinmob));
		mBitMapCache.put(R.drawable.penguinmobleft, 
				BitmapFactory.decodeResource(getResources(), R.drawable.penguinmobleft));
		mBitMapCache.put(R.drawable.penguinmobright, 
				BitmapFactory.decodeResource(getResources(), R.drawable.penguinmobright));
		mBitMapCache.put(R.drawable.rock2, 
				BitmapFactory.decodeResource(getResources(), R.drawable.rock2));
		mBitMapCache.put(R.drawable.water, 
				BitmapFactory.decodeResource(getResources(), R.drawable.water));
		mBitMapCache.put(R.drawable.water2, 
				BitmapFactory.decodeResource(getResources(), R.drawable.water2));
		mBitMapCache.put(R.drawable.water3, 
				BitmapFactory.decodeResource(getResources(), R.drawable.water3));
		mBitMapCache.put(R.drawable.bigsnowball, 
				BitmapFactory.decodeResource(getResources(), R.drawable.bigsnowball));
		mBitMapCache.put(R.drawable.projsplash_big, 
				BitmapFactory.decodeResource(getResources(), R.drawable.projsplash_big));
		mBitMapCache.put(R.drawable.projslow, 
				BitmapFactory.decodeResource(getResources(), R.drawable.projslow));
		mBitMapCache.put(R.drawable.pause, 
				BitmapFactory.decodeResource(getResources(), R.drawable.pause));
		mBitMapCache.put(R.drawable.pause2, 
				BitmapFactory.decodeResource(getResources(), R.drawable.pause2));
		mBitMapCache.put(R.drawable.walrus, 
				BitmapFactory.decodeResource(getResources(), R.drawable.walrus));
		mBitMapCache.put(R.drawable.bear, 
				BitmapFactory.decodeResource(getResources(), R.drawable.bear));
		mBitMapCache.put(R.drawable.bearleft, 
				BitmapFactory.decodeResource(getResources(), R.drawable.bearleft));
		mBitMapCache.put(R.drawable.bearright, 
				BitmapFactory.decodeResource(getResources(), R.drawable.bearright));
		mBitMapCache.put(R.drawable.icebear, 
				BitmapFactory.decodeResource(getResources(), R.drawable.icebear));
		mBitMapCache.put(R.drawable.fastforward, 
				BitmapFactory.decodeResource(getResources(), R.drawable.fastforward));
		mBitMapCache.put(R.drawable.fastforward2, 
				BitmapFactory.decodeResource(getResources(), R.drawable.fastforward2));
		mBitMapCache.put(R.drawable.flyingpenguin, 
				BitmapFactory.decodeResource(getResources(), R.drawable.flyingpenguin));
		mBitMapCache.put(R.drawable.airtower, 
				BitmapFactory.decodeResource(getResources(), R.drawable.airtower));
		mBitMapCache.put(R.drawable.airtower1, 
				BitmapFactory.decodeResource(getResources(), R.drawable.airtower1));
		mBitMapCache.put(R.drawable.airtower2, 
				BitmapFactory.decodeResource(getResources(), R.drawable.airtower2));
		mBitMapCache.put(R.drawable.airtower3, 
				BitmapFactory.decodeResource(getResources(), R.drawable.airtower3));
		mBitMapCache.put(R.drawable.eskimotowersplash, 
				BitmapFactory.decodeResource(getResources(), R.drawable.eskimotowersplash));
		mBitMapCache.put(R.drawable.eskimotowersplash2, 
				BitmapFactory.decodeResource(getResources(), R.drawable.eskimotowersplash2));
		mBitMapCache.put(R.drawable.eskimotowersplash3, 
				BitmapFactory.decodeResource(getResources(), R.drawable.eskimotowersplash3));
		mBitMapCache.put(R.drawable.eskimotowersplash4, 
				BitmapFactory.decodeResource(getResources(), R.drawable.eskimotowersplash4));
		mBitMapCache.put(R.drawable.menutop, 
				BitmapFactory.decodeResource(getResources(), R.drawable.menutop));
		mBitMapCache.put(R.drawable.menumid, 
				BitmapFactory.decodeResource(getResources(), R.drawable.menumid));
		mBitMapCache.put(R.drawable.menubot, 
				BitmapFactory.decodeResource(getResources(), R.drawable.menubot));
		mBitMapCache.put(R.drawable.menutop2, 
				BitmapFactory.decodeResource(getResources(), R.drawable.menutop2));
		mBitMapCache.put(R.drawable.menumid2, 
				BitmapFactory.decodeResource(getResources(), R.drawable.menumid2));
		mBitMapCache.put(R.drawable.menubot2, 
				BitmapFactory.decodeResource(getResources(), R.drawable.menubot2));
		mBitMapCache.put(R.drawable.line, 
				BitmapFactory.decodeResource(getResources(), R.drawable.line));
		mBitMapCache.put(R.drawable.expl1, 
				BitmapFactory.decodeResource(getResources(), R.drawable.expl1));
		mBitMapCache.put(R.drawable.expl2, 
				BitmapFactory.decodeResource(getResources(), R.drawable.expl2));
		mBitMapCache.put(R.drawable.expl3, 
				BitmapFactory.decodeResource(getResources(), R.drawable.expl3));
		mBitMapCache.put(R.drawable.expl4, 
				BitmapFactory.decodeResource(getResources(), R.drawable.expl4));
		mBitMapCache.put(R.drawable.expl5, 
				BitmapFactory.decodeResource(getResources(), R.drawable.expl5));
		mBitMapCache.put(R.drawable.nexpl1, 
				BitmapFactory.decodeResource(getResources(), R.drawable.nexpl1));
		mBitMapCache.put(R.drawable.nexpl2, 
				BitmapFactory.decodeResource(getResources(), R.drawable.nexpl2));
		mBitMapCache.put(R.drawable.nexpl3, 
				BitmapFactory.decodeResource(getResources(), R.drawable.nexpl3));
		mBitMapCache.put(R.drawable.nexpl4, 
				BitmapFactory.decodeResource(getResources(), R.drawable.nexpl4));
		mBitMapCache.put(R.drawable.blankexpl, 
				BitmapFactory.decodeResource(getResources(), R.drawable.blankexpl));
		mBitMapCache.put(R.drawable.sexpl1, 
				BitmapFactory.decodeResource(getResources(), R.drawable.sexpl1));
		mBitMapCache.put(R.drawable.sexpl2, 
				BitmapFactory.decodeResource(getResources(), R.drawable.sexpl2));
		mBitMapCache.put(R.drawable.sexpl3, 
				BitmapFactory.decodeResource(getResources(), R.drawable.sexpl3));
		mBitMapCache.put(R.drawable.accept, 
				BitmapFactory.decodeResource(getResources(), R.drawable.accept));
		mBitMapCache.put(R.drawable.deny, 
				BitmapFactory.decodeResource(getResources(), R.drawable.deny));
	}

	/**
	 * Processes KeyEvents. Hardware buttons etc.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			// Handle hardware menu button
			GameModel.GAME_STATE = GameModel.STATE_PAUSED;			
			break;
		case KeyEvent.KEYCODE_BACK:
			// Handle hardware "back" button
			GameModel.GAME_STATE = GameModel.STATE_PAUSED;
			break;
		case KeyEvent.KEYCODE_VOLUME_UP:
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0);
			break;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0);
			break;
		}
		return true;
	}

	/**
	 * 
	 * Processes MotionEvents. This is basically where all user input is handled
	 * during the game play.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		synchronized (getHolder()) {

			switch (GameModel.GAME_STATE) {
			case GameModel.STATE_RUNNING:
				// store the coordinates of the event, the x coordinate with an offset of -60 pixels
				//if(!GameModel.mWaitingToBuild){
					mTx = (int) event.getX() - 60;
					mTy = (int) event.getY();
				//}
				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:

					if(GameModel.mWaitingToBuild){ //If a moveable tower is on the gamefield
						if (event.getX() < mButtonBorder)
							touchGameFieldEvent(event);
						// The buttons on right side of the screen were touched
						else 
							touchRightButtonsEvent(event);
					
					
						// Accepts the build
						if(event.getX() > 50 && event.getX() < 100 && event.getY() > 270 && event.getY() < 320){
							if (GameModel.canAddTower(GameModel.mMovableTower.getTower()) && GameModel.mAllowBuild && 
									GameModel.mMovableTower.mXPos < mButtonBorder - 20) {
	
	
								// build the tower and remove money from player
								GameModel.buildTower(GameModel.mMovableTower.getTower(), 
										(int)GameModel.mMovableTower.getTower().getX() / GameModel.GAME_TILE_SIZE,
										(int)GameModel.mMovableTower.getTower().getY() / GameModel.GAME_TILE_SIZE);
								GameModel.sCurrentPlayer.changeMoney(-GameModel.mMovableTower.getTower().getCost());
	
							}
							GameModel.mMovableTower = null;
							
						// Deny the build
						} else if (event.getX() > 100 && event.getX() < 150 && event.getY() > 270 && event.getY() < 320){
							GameModel.mMovableTower = null;
							GameModel.mWaitingToBuild = false;
						}
						// If the user has selected a Tower and is touching the upgrade window while trying to build
						if(GameModel.mSelectedTower != null && sTransparentBox.contains(mTx, mTy)){
							GameModel.mMovableTower = null;
							GameModel.mWaitingToBuild = false;
							touchRightButtonsEvent(event);
						}
						
					} else {
						// If the user has selected a Tower and is touching the upgrade window
						if (GameModel.mSelectedTower != null && sTransparentBox.contains(mTx, mTy)) {

	
							touchUpgradeWindowEvent(event);
	
						} else { // if the user has NOT selected a tower, or if the user selected a tower but touched outside the upgrade window.
	
							GameModel.mSelectedTower = null; //deselect any selected tower
							GameModel.mAllowBuild = false;
	
							// game field touched
							if (event.getX() < mButtonBorder)
								touchGameFieldEvent(event);
	
							// The buttons on right side of the screen were touched
							else 
								touchRightButtonsEvent(event);
						}
					}
					break;

				case MotionEvent.ACTION_MOVE:

					GameModel.mShowTooltip =  event.getX() > mButtonBorder; //show tooltip if tower is on the button menu
					// if a tower is being bought
					/*
					if(GameModel.mWaitingToBuild){
	
						mTx = (int) event.getX() - 60;
						mTy = (int) event.getY();						
						
					}
					*/
					if (GameModel.mMovableTower != null) {
						if (GameModel.mAllowBuild) {
							GameModel.mMovableTower.mXPos = mTx;
							GameModel.mMovableTower.mYPos = mTy;
						}
					} else if (GameModel.mCurrentSnowball != null) {
						if (GameModel.mAllowBuild) {
							GameModel.mCurrentSnowball.setX(mTx);
							GameModel.mCurrentSnowball.setY(mTy);
						}
					}
					break;

				case MotionEvent.ACTION_UP:
					//if a tower is placed on the game field
					if(GameModel.mMovableTower != null) {

						GameModel.mWaitingToBuild = true;
						GameModel.mShowTooltip = false;
						//Accept the current possition of the tower, if you want to place it there
						
							/*
							if (GameModel.canAddTower(GameModel.mMovableTower.getTower()) && GameModel.mAllowBuild && 
									mTx + 60 < mButtonBorder) {
	
	
								// build the tower and remove money from player
								GameModel.buildTower(GameModel.mMovableTower.getTower(), 
										(int)GameModel.mMovableTower.getTower().getX() / GameModel.GAME_TILE_SIZE,
										(int)GameModel.mMovableTower.getTower().getY() / GameModel.GAME_TILE_SIZE);
								GameModel.sCurrentPlayer.changeMoney(-GameModel.mMovableTower.getTower().getCost());
	
							}
							GameModel.mMovableTower = null;
							*/
						
					} else if (GameModel.mCurrentSnowball != null) {
						// if a snowball is being placed
						if (GameModel.mAllowBuild && event.getX() < mButtonBorder) {
							GameModel.sSnowballs.add(GameModel.mCurrentSnowball);
							mUsedSnowballs++;
						}
						GameModel.mCurrentSnowball = null;
					}
					break;
				}
				break;

			case GameModel.STATE_GAMEOVER:
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					break;
				case MotionEvent.ACTION_MOVE:

					if(event.getX() >= 100 && event.getX() <= 344 && event.getY() >= 80+34 && 
							event.getY() <= 80+34+36){
						menuPic = 1;
					}
					else if(event.getX() >= 100 && event.getX() <= 344 && 
							event.getY() >=	80+34+36 && event.getY() <= 80+34+36+36){
						menuPic = 2;
					}else if(event.getX() >= 100 && event.getX() <= 344 && 
							event.getY() >=	80+34+36+36 && event.getY() <= 80+34+36+36+34){
						menuPic = 3;
					} else {
						menuPic = 0;
					}

					break;
				case MotionEvent.ACTION_UP:
					// Restart button
					if(event.getX() >= 100 && event.getX() <= 344 && event.getY() >= 80+34 && 
							event.getY() <= 80+34+36){
						startTrack(GameModel.getTrack());						
						GameModel.GAME_STATE = GameModel.STATE_RUNNING;	
						mMobFactory.resetWaveIndex(); // Resets the wave counter 
						mMobFactory.resetMobIndex();
					}
					else if(event.getX() >= 100 && event.getX() <= 344 && 
							event.getY() >=	80+34+36 &&  event.getY() <= 80+34+36+36){
						// go back to progression route
						mGameThread.setRunning(false);
						mBitMapCache = null;
						getHolder().removeCallback(this);
						Activity parent = (Activity) getContext();
						parent.setContentView(new ProgressionRouteView(getContext()));
					}
					else if(event.getX() >= 100 && event.getX() <= 344 && event.getY() >= 
						80+34+36+36 &&  event.getY() <= 80+34+36+36+34){
						// go back to main menu
						mGameThread.setRunning(false);
						mBitMapCache = null;
						getHolder().removeCallback(this);
						Activity parent = (Activity) getContext();
						parent.finish();
					}
					menuPic = 0;
					break;
				}
				break;
			case GameModel.STATE_WIN:							

				switch (event.getAction()) {								

				case MotionEvent.ACTION_DOWN:

					break;
				case MotionEvent.ACTION_MOVE:

					if(event.getX() >= 100 && event.getX() <= 344 && event.getY() >= 80+34 && 
							event.getY() <= 80+34+36){
						menuPic = 1;
					}
					else if(event.getX() >= 100 && event.getX() <= 344 && 
							event.getY() >=	80+34+36 &&  event.getY() <= 80+34+36+36){
						menuPic = 2;
					}
					else if(event.getX() >= 100 && event.getX() <= 344 && 
							event.getY() >= 80+34+36+36 &&  event.getY() <= 80+34+36+36+34){
						menuPic = 3;
					} else {
						menuPic = 0;
					}

					break;
				case MotionEvent.ACTION_UP:

					if(event.getX() >= 100 && event.getX() <= 344 && event.getY() >= 80+34 
							&& event.getY() <= 80+34+36){
						// go back to progression route
						mGameThread.setRunning(false);
						mBitMapCache = null;
						getHolder().removeCallback(this);
						Activity parent = (Activity) getContext();
						parent.setContentView(new ProgressionRouteView(getContext()));

					}
					else if(event.getX() >= 100 && event.getX() <= 344 && event.getY() >= 80+34+36 
							&&  event.getY() <= 80+34+36+36){
						// restart level
						startTrack(GameModel.getTrack());												
						GameModel.GAME_STATE = GameModel.STATE_RUNNING;
						mMobFactory.resetWaveIndex(); // Resets the wave counter
						mMobFactory.resetMobIndex();
					}
					else if(event.getX() >= 100 && event.getX() <= 344 
							&& event.getY() >= 80+34+36+36 &&  event.getY() <= 80+34+36+36+34){
						// go back to main menu
						mGameThread.setRunning(false);
						mBitMapCache = null;
						getHolder().removeCallback(this);
						Activity parent = (Activity) getContext();
						parent.finish();
					}

					menuPic = 0;

					break;
				}
				break;

			case GameModel.STATE_PAUSED:	
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// do nothing
					break;
				case MotionEvent.ACTION_MOVE:

					if(event.getX() >= 100 && event.getX() <= 344 && event.getY() >= 80+34 && 
							event.getY() <= 80+34+36){

						menuPic = 1;
					} else if(event.getX() >= 100 && event.getX() <= 344 && 
							event.getY() >= 80+34+36 &&  event.getY() <= 80+34+36+36){

						menuPic = 2;
					} else if(event.getX() >= 100 && event.getX() <= 344 && 
							event.getY() >= 80+34+36+36 &&  event.getY() <= 80+34+36+36+34){

						menuPic = 3;
					} else if(event.getX() >= 100 && event.getX() <=344 && 
							event.getY() >= 80+34+36+36+36 && event.getY() <= 80+34+36+36+36+34){

						menuPic = 4;
					}
					else {
						menuPic = 0;
					}
					break;
				case MotionEvent.ACTION_UP:

					if(event.getX() >= 100 && event.getX() <= 344 && event.getY() >= 80+34 &&
							event.getY() <= 80+34+36){

						menuPic = 1;
					} else if(event.getX() >= 100 && event.getX() <= 344 && 
							event.getY() >= 80+34+36 &&  event.getY() <= 80+34+36+36){

						menuPic = 2;
					} else if(event.getX() >= 100 && event.getX() <= 344 && 
							event.getY() >= 80+34+36+36 &&  event.getY() <= 80+34+36+36+34){

						menuPic = 3;
					} else if(event.getX() >= 100 && event.getX() <=344 && 
							event.getY() >= 80+34+36+36+36 && event.getY() <= 80+34+36+36+36+34){

						menuPic = 4;
					}
					else {
						menuPic = 0;
					}

					if(event.getX() >= 100 && event.getX() <= 344 && event.getY() >= 80+34 && 
							event.getY() <= 80+34+36){

						GameModel.GAME_STATE = GameModel.STATE_RUNNING;
					}
					else if(event.getX() >= 100 && event.getX() <= 344 && 
							event.getY() >= 80+34+36 && event.getY() <= 80+34+36+36){

						// restart
						startTrack(GameModel.getTrack());											
						GameModel.GAME_STATE = GameModel.STATE_RUNNING;			
						mMobFactory.resetWaveIndex(); // Resets the wave counter
						mMobFactory.resetMobIndex();
					}
					else if(event.getX() >= 100 && event.getX() <= 344 && 
							event.getY() >= 80+34+36+36 && event.getY() <= 80+34+36+36+34){

						// go back to progression route
						mGameThread.setRunning(false);
						mBitMapCache = null;
						getHolder().removeCallback(this);
						Activity parent = (Activity) getContext();
						parent.setContentView(new ProgressionRouteView(getContext()));
					} else if(event.getX() >= 100 && event.getX() <=344 && 
							event.getY() >= 80+34+36+36+36 && event.getY() <= 80+34+36+36+36+34) {

						// go back to progression route
						mGameThread.setRunning(false);
						mBitMapCache = null;
						getHolder().removeCallback(this);
						Activity parent = (Activity) getContext();
						parent.finish();
					}
					menuPic = 0;
					break;
				}
				break;
			}
		}


		// sleep for 16 milliseconds, to avoid being flooded by onTouchEvents
		try {
			Thread.sleep(16);
		} catch (InterruptedException e) {
			//Log.v("App: ", "Error 2");
		}

		return true;
	}

	/**
	 * Sub-method to onTouchEvent that handles action down on the upgrade window.
	 * 
	 * @param event The motion event.
	 */
	//called by onTouchEvent if the upgrade window is touched
	private void touchUpgradeWindowEvent(MotionEvent event){
		// Upgrade button pressed, and selected tower is upgradeable
		if (sBtnUpgrade.contains(event.getX(), event.getY()) && GameModel.mSelectedTower.canUpgrade()) {

			if (GameModel.sCurrentPlayer.getMoney() >= GameModel.mSelectedTower.getUpgradeCost() && 
					GameModel.mSelectedTower.getUpgradeCost() != 0) {

				GameModel.sCurrentPlayer.changeMoney(-GameModel.mSelectedTower.getUpgradeCost());
				GameModel.mSelectedTower.upgrade();
			}
		} else if (sBtnSell.contains(event.getX(), event.getY()) ) {
			// Sell button pressed
			GameModel.sCurrentPlayer.changeMoney(GameModel.mSelectedTower.sellPrice());
			GameModel.removeTower(GameModel.mSelectedTower);
			GameModel.mSelectedTower = null;
		} else 
			GameModel.mSelectedTower = null;
	}

	/**
	 * Sub-method to onTouchEvent that handles action down on the game field.
	 * 
	 * @param event The motion event.
	 */
	//method called by onTouchEvent if the game field have been touched
	private void touchGameFieldEvent(MotionEvent event){
		GameModel.mShowTooltip = false;

		// if a tower was touched, mark it as selected
		int size = GameModel.sTowers.size();
		for (int i = 0; i < size; i++){
			Tower t = GameModel.sTowers.get(i);

			if (t.selectTower(event.getX(), event.getY())){
				GameModel.mSelectedTower = t;
				break;
			}
		}

		// if pause button was touched, pause the game
		if (event.getX() > 0 && event.getX() < 40 && event.getY() > 0 && event.getY() < 50){
			GameModel.GAME_STATE = GameModel.STATE_PAUSED;
			GameModel.mMovableTower = null;
		}

		// if fast forward was touched, toggle fast forward
		if(event.getX() > 0 && event.getX() < 50 && event.getY() > 260 && event.getY() < 320){
			GameModel.toggleFast();
			GameModel.mMovableTower = null;
		}
		
		GameModel.mWaitingToBuild = false;
		
	}

	/**
	 * Sub-method to onTouchEvent that handles action down on the right side menu ("the tower store")
	 * 
	 * @param event The current motion event.
	 */
	//method called by onTouchEvent if the menu of buttons on the right side of the screen
	//has been touched.
	private void touchRightButtonsEvent(MotionEvent event){

		// button 1
		if(sBtn1.contains(event.getX(),event.getY())) {

			if (mTower1.getCost() <= GameModel.sCurrentPlayer.getMoney())
				GameModel.mAllowBuild = true;

			GameModel.mMovableTower = new MovableTower(new BasicTower(mTx ,mTy), mTx, mTy);
			GameModel.mShowTooltip = true;

			// button 2
		} else if(sBtn2.contains(event.getX(),event.getY())) {

			if (mTower2.getCost() <= GameModel.sCurrentPlayer.getMoney())
				GameModel.mAllowBuild = true;	

			GameModel.mMovableTower = new MovableTower(new SplashTower(mTx ,mTy),mTx,mTy);
			GameModel.mShowTooltip = true;

			// button 3
		} else if(sBtn3.contains(event.getX(),event.getY())) {

			if (mTower3.getCost() <= GameModel.sCurrentPlayer.getMoney())
				GameModel.mAllowBuild = true;

			GameModel.mMovableTower = new MovableTower(new SlowTower(mTx ,mTy),mTx,mTy);
			GameModel.mShowTooltip = true;

			// button 4
		} else if(sBtn4.contains(event.getX(),event.getY())) {

			if (mTower4.getCost() <= GameModel.sCurrentPlayer.getMoney())
				GameModel.mAllowBuild = true;

			GameModel.mMovableTower = new MovableTower(new AirTower(mTx ,mTy),mTx,mTy);
			GameModel.mShowTooltip = true;

			//button 5
		} else if(sBtn5.contains(event.getX(),event.getY())) {

			if (mAccelerometerSupported) {

				if (GameModel.sCurrentPlayer.getCurrentTrackScore() 
						>= mSnowballTreshold*(1+mUsedSnowballs))
					GameModel.mAllowBuild = true;

				if (GameModel.sCheatEnabled)
					GameModel.mAllowBuild = true;

				GameModel.mCurrentSnowball = new Snowball(mTx,mTy);
				GameModel.mShowTooltip = true;
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
		// Dela in subtask i subfunktioner. Ser snyggare ut! / Jonas
		// DONE AND DONE /Fredrik

		drawBackground(canvas);
		drawSplashWater(canvas);
		drawTowers(canvas);
		drawMobs(canvas);
		drawExplForProj(canvas);
		drawSnowballs(canvas);
		drawProjectiles(canvas);
		drawButtons(canvas);
		drawStatisticsText(canvas);
		drawRewardsAfterDeadMob(canvas);	
		


		switch (GameModel.GAME_STATE) {
		case GameModel.STATE_RUNNING:
			// if a tower is being bought
			// draw either the tooltip for it, or how it would be placed.
			if (GameModel.mMovableTower != null || GameModel.mCurrentSnowball != null) {
				if (GameModel.mShowTooltip){
					drawTooltip(canvas);
					drawCurrentTower(canvas); //ahmed visa torn nŠr man hŒller šver
				} else if (GameModel.mMovableTower != null && GameModel.mAllowBuild)
					drawCurrentTower(canvas);
				else if (GameModel.mCurrentSnowball != null && GameModel.mAllowBuild) {
					canvas.drawCircle(
							(int)GameModel.mCurrentSnowball.getX(),
							(int)GameModel.mCurrentSnowball.getY(),
							GameModel.mCurrentSnowball.getRadius(),snowPaint);
				}
			}
			//accept or deny build
			if(GameModel.mWaitingToBuild){
				canvas.drawBitmap(mBitMapCache.get(R.drawable.accept),50,270,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.deny),100,270,null);

			}

			// if a tower is selected for upgrades and such and such
			if(GameModel.mSelectedTower != null){	
				drawUpgradeWindow(canvas);
			}
			break;

		case GameModel.STATE_GAMEOVER: // loser screen

			canvas.drawBitmap(mBitMapCache.get(R.drawable.menutop),100,80,null);
			if(menuPic == 1){
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid2),100,80+34,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34+36,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menubot),100,80+34+36+36,null);				
			} else if(menuPic == 2){
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid2),100,80+34+36,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menubot),100,80+34+36+36,null);	
			}  else if(menuPic == 3){
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34+36,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menubot2),100,80+34+36+36,null);	
			} else {
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34+36,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menubot),100,80+34+36+36,null);	
			}

			canvas.drawBitmap(mBitMapCache.get(R.drawable.line),100,80+34,null);


			canvas.drawText("Game over!", 171,80+20+2,sPaintTextBlack);
			canvas.drawText("Restart",181,80+34+20+2,sPaintTextBlack);
			canvas.drawText("Go to map",181,80+34+36+20+2,sPaintTextBlack);
			canvas.drawText("Exit", 181, 80+34+36+36+20+2, sPaintTextBlack);

			canvas.drawText("Game over!", 171,80+20,sPaintTextWhite);
			canvas.drawText("Restart",180,80+34+20,sPaintTextWhite);
			canvas.drawText("Go to map",180,80+34+36+20,sPaintTextWhite);
			canvas.drawText("Exit", 180, 80+34+36+36+20, sPaintTextWhite);

			break;

		case GameModel.STATE_WIN: // winner screen

			canvas.drawBitmap(mBitMapCache.get(R.drawable.menutop),100,80,null);
			if(menuPic == 1){
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid2),100,80+34,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34+36,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menubot),100,80+34+36+36,null);				
			} else if(menuPic == 2){
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid2),100,80+34+36,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menubot),100,80+34+36+36,null);	
			}  else if(menuPic == 3){
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34+36,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menubot2),100,80+34+36+36,null);	
			} else {
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34+36,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menubot),100,80+34+36+36,null);	
			}


			canvas.drawBitmap(mBitMapCache.get(R.drawable.line),100,80+34,null);

			canvas.drawText("Level complete!", 156,80+20+2,sPaintTextBlack);
			canvas.drawText("Go to map",181,80+34+20+2,sPaintTextBlack);
			canvas.drawText("Restart",181,80+34+36+20+2,sPaintTextBlack);
			canvas.drawText("Exit", 181, 80+34+36+36+20+2, sPaintTextBlack);

			canvas.drawText("Level complete!", 155,80+20,sPaintTextWhite);
			canvas.drawText("Go to map",180,80+34+20,sPaintTextWhite);
			canvas.drawText("Restart",180,80+34+36+20,sPaintTextWhite);
			canvas.drawText("Exit", 180, 80+34+36+36+20, sPaintTextWhite);

			break;

		case GameModel.STATE_PAUSED: // pause screen

			canvas.drawBitmap(mBitMapCache.get(R.drawable.menutop),100,80,null);
			if(menuPic == 1){
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid2),100,80+34,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34+36,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34+36+36,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menubot),100,80+34+36+36+36,null);				
			} else if(menuPic == 2){
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid2),100,80+34+36,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34+36+36,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menubot),100,80+34+36+36+36,null);	
			}  else if(menuPic == 3){
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34+36,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid2),100,80+34+36+36,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menubot),100,80+34+36+36+36,null);	
			} else if (menuPic == 4){
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34+36,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34+36+36,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menubot2),100,80+34+36+36+36,null);	
			} else {
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34+36,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34+36+36,null);
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menubot),100,80+34+36+36+36,null);
			}

			canvas.drawBitmap(mBitMapCache.get(R.drawable.line),100,80+34,null);

			canvas.drawText("GAME PAUSED!", 156,80+20+2,sPaintTextBlack);
			canvas.drawText("Resume",181,80+34+20+2,sPaintTextBlack);
			canvas.drawText("Restart",181,80+34+36+20+2,sPaintTextBlack);
			canvas.drawText("Go to map", 181, 80+34+36+36+20+2, sPaintTextBlack);
			canvas.drawText("Exit", 181, 80+34+36+36+36+20+2, sPaintTextBlack);

			canvas.drawText("GAME PAUSED!", 155,80+20,sPaintTextWhite);
			canvas.drawText("Resume",180,80+34+20,sPaintTextWhite);
			canvas.drawText("Restart",180,80+34+36+20,sPaintTextWhite);
			canvas.drawText("Go to map", 180, 80+34+36+36+20, sPaintTextWhite);
			canvas.drawText("Exit", 180, 80+34+36+36+36+20, sPaintTextWhite);

			break;
		}
	}

	// TODO: solve animations in a non-ugly way
	private void drawSplashWater(Canvas canvas){
		if(GameModel.mSplash){
			int x = 422;
			int y = 130;
			if(mWateranimation >= 0 && mWateranimation < 5){
				canvas.drawBitmap(mBitMapCache.get(R.drawable.water),x,y,null);
				mWateranimation++;
			} else if(mWateranimation >= 5 && mWateranimation < 10){
				canvas.drawBitmap(mBitMapCache.get(R.drawable.water2),x,y,null);
				mWateranimation++;
			} else if(mWateranimation >= 10 && mWateranimation < 15){
				canvas.drawBitmap(mBitMapCache.get(R.drawable.water3),x,y,null);
				// To prevent latency that distorts the sound
				// just play the sound one time
				if(mWateranimation == 11) { 
					SoundManager.playSound(SoundManager.getWaterSplashSound());
				}
				mWateranimation++;				
			} else if(mWateranimation >= 15 && mWateranimation < 20){
				canvas.drawBitmap(mBitMapCache.get(R.drawable.water2),x,y,null);
				mWateranimation++;
			} else if(mWateranimation >= 20 && mWateranimation < 25){
				canvas.drawBitmap(mBitMapCache.get(R.drawable.water),x,y,null);
				mWateranimation++;
			}						
		}

		if(mWateranimation >= 25){ 		
			mWateranimation = 0;
			GameModel.mSplash = false;			
		}				
	}

	private void drawStatisticsText(Canvas canvas) {
		// draw debug messages in the top left corner
		//canvas.drawText("FPS: "+Float.toString(debug.getFPS()) + " Mobs:"+ GameModel.mMobs.size()+
		//		" Proj:"+GameModel.mProjectiles.size() + " Towers:"+ 
		//GameModel.mTowers.size(), 10, 320,sPaintText);

		// show stats of the player    	
		canvas.drawBitmap(mBitMapCache.get(R.drawable.money),80,3, null);
		canvas.drawText("" + (int)GameModel.sCurrentPlayer.getMoney(), 105, 20, sPaintText);
		canvas.drawBitmap(mBitMapCache.get(R.drawable.lives), 160, 3, null);
		canvas.drawText("" + GameModel.sCurrentPlayer.getRemainingLives(), 185, 20, sPaintText);
		canvas.drawText(mMobFactory.getNextWaveNr() + "/" + mMobFactory.getTotalNrOfWaves(), 
				230, 20, sPaintText);
		canvas.drawText("Score: " + (int)GameModel.sCurrentPlayer.getCurrentTrackScore(), 
				290, 20, sPaintText);

		if(!mMobFactory.lastWaveHasEntered()){
			int mWaveTime = mMobFactory.getWaveTime();

			canvas.drawText("Next wave: " + mMobFactory.getNextWaveType() + 
					"(" + mWaveTime + ")", 230, 300, sPaintText);
		}
	}


	private void drawUpgradeWindow(Canvas canvas) {
		// draw a circle that shows the tower's range
		canvas.drawCircle(
				GameModel.GAME_TILE_SIZE * ((float)GameModel.mSelectedTower.getX() / 
						GameModel.GAME_TILE_SIZE + (GameModel.mSelectedTower.getWidth()/2)),
						GameModel.GAME_TILE_SIZE * ((float)GameModel.mSelectedTower.getY() / 
								GameModel.GAME_TILE_SIZE + (GameModel.mSelectedTower.getHeight()/2)),
								GameModel.mSelectedTower.getRange(), rangeIndicationPaint);

		canvas.drawBitmap(mBitMapCache.get(R.drawable.menutop),100,60,null);
		canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,60+34,null);
		canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,60+34+36,null);
		canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,60+34+36+36,null);
		canvas.drawBitmap(mBitMapCache.get(R.drawable.menubot),100,60+34+36+36+36,null);

		canvas.drawBitmap(mBitMapCache.get(GameModel.mSelectedTower.getImage()), 110, 70, null);

		//Draw general info
		int lvl = GameModel.mSelectedTower.getLevel();
		canvas.drawText(GameModel.mSelectedTower.getName(), 161, 90+2, sPaintTextBlack);
		canvas.drawText("Level " + (lvl), 151, 112+2, sPaintTextBlack);
		canvas.drawText("Speed: " + GameModel.mSelectedTower.getAttackSpeed(), 151, 128+2, sPaintTextBlack);
		canvas.drawText("Damage: " + GameModel.mSelectedTower.getDamage(), 151, 144+2, sPaintTextBlack);
		canvas.drawText("Range: " + GameModel.mSelectedTower.getRange(), 151, 160+2, sPaintTextBlack);

		if (GameModel.mSelectedTower.getType() == Tower.SLOW)
			canvas.drawText("Slow: " + GameModel.mSelectedTower.getSlow() + "%", 151, 176+2, sPaintTextBlack);
		else if (GameModel.mSelectedTower.getType() == Tower.SPLASH)
			canvas.drawText("Splash: " + GameModel.mSelectedTower.getSplashRadius(), 151, 176+2, sPaintTextBlack);


		//name white
		canvas.drawText(GameModel.mSelectedTower.getName(), 160, 90, sPaintTextWhite);
		//level white
		canvas.drawText("Level " + (lvl), 150, 112, sPaintTextWhite);
		//attack speed white
		canvas.drawText("Speed: " + GameModel.mSelectedTower.getAttackSpeed(), 150, 128, sPaintTextWhite);
		//damage white
		canvas.drawText("Damage: " + GameModel.mSelectedTower.getDamage(), 150, 144, sPaintTextWhite);
		//range white
		canvas.drawText("Range: " + GameModel.mSelectedTower.getRange(), 150, 160, sPaintTextWhite);

		if (GameModel.mSelectedTower.getType() == Tower.SLOW)
			canvas.drawText("Slow: " + GameModel.mSelectedTower.getSlow() + "%", 150, 176, sPaintTextWhite);
		else if (GameModel.mSelectedTower.getType() == Tower.SPLASH)
			canvas.drawText("Splash: " + GameModel.mSelectedTower.getSplashRadius(), 150, 176, sPaintTextWhite);

		if(GameModel.mSelectedTower.canUpgrade()) {

			//Draw upgrade info
			Paint paintRedOrGreen;
			if (GameModel.sCurrentPlayer.getMoney() >= GameModel.mSelectedTower.getUpgradeCost())
				paintRedOrGreen = sPaintBoxGreen;
			else
				paintRedOrGreen = sPaintBoxRed;

			canvas.drawText(" -> " + (lvl+1), 256, 112+2, sPaintTextBlack);
			canvas.drawText(" -> " + (lvl+1), 255, 112, paintRedOrGreen);


			switch (GameModel.mSelectedTower.getType()) {
			case Tower.BASIC:
				//letter shadows in black
				canvas.drawText(" -> " + BasicTower.sCoolDown[lvl], 256, 128+2, sPaintTextBlack);
				canvas.drawText(" -> " + BasicTower.sDamage[lvl], 256, 144+2, sPaintTextBlack);
				canvas.drawText(" -> " + BasicTower.sRange[lvl], 256, 160+2, sPaintTextBlack);

				//letters in green or red depending on if upgrade can be afforded or not
				canvas.drawText(" -> " + BasicTower.sCoolDown[lvl], 255, 128, paintRedOrGreen);
				canvas.drawText(" -> " + BasicTower.sDamage[lvl], 255, 144, paintRedOrGreen);
				canvas.drawText(" -> " + BasicTower.sRange[lvl], 255, 160, paintRedOrGreen);
				break;
			case Tower.AIR:
				//letter shadows in black
				canvas.drawText(" -> " + AirTower.sCoolDown[lvl], 256, 128+2, sPaintTextBlack);
				canvas.drawText(" -> " + AirTower.sDamage[lvl], 256, 144+2, sPaintTextBlack);
				canvas.drawText(" -> " + AirTower.sRange[lvl], 256, 160+2, sPaintTextBlack);

				//letters in green or red depending on if upgrade can be afforded or not
				canvas.drawText(" -> " + AirTower.sCoolDown[lvl], 255, 128, paintRedOrGreen);
				canvas.drawText(" -> " + AirTower.sDamage[lvl], 255, 144, paintRedOrGreen);
				canvas.drawText(" -> " + AirTower.sRange[lvl], 255, 160, paintRedOrGreen);
				break;
			case Tower.SLOW:
				//letter shadows in black
				canvas.drawText(" -> " + SlowTower.sCoolDown[lvl], 256, 128+2, sPaintTextBlack);
				canvas.drawText(" -> " + SlowTower.sDamage[lvl], 256, 144+2, sPaintTextBlack);
				canvas.drawText(" -> " + SlowTower.sRange[lvl], 256, 160+2, sPaintTextBlack);
				canvas.drawText(" -> " + SlowTower.sSlow[lvl] + "%", 256, 176+2, sPaintTextBlack);

				//letters in green or red depending on if upgrade can be afforded or not
				canvas.drawText(" -> " + SlowTower.sCoolDown[lvl], 255, 128, paintRedOrGreen);
				canvas.drawText(" -> " + SlowTower.sDamage[lvl], 255, 144, paintRedOrGreen);
				canvas.drawText(" -> " + SlowTower.sRange[lvl], 255, 160, paintRedOrGreen);
				canvas.drawText(" -> " + SlowTower.sSlow[lvl] + "%", 255, 176, paintRedOrGreen);
				break;
			case Tower.SPLASH:
				//letter shadows in black
				canvas.drawText(" -> " + SplashTower.sCoolDown[lvl], 256, 128+2, sPaintTextBlack);
				canvas.drawText(" -> " + SplashTower.sDamage[lvl], 256, 144+2, sPaintTextBlack);
				canvas.drawText(" -> " + SplashTower.sRange[lvl], 256, 160+2, sPaintTextBlack);
				canvas.drawText(" -> " + SplashTower.sSplashRadius[lvl], 256, 176+2, sPaintTextBlack);

				//letters in green or red depending on if upgrade can be afforded or not
				canvas.drawText(" -> " + SplashTower.sCoolDown[lvl], 255, 128, paintRedOrGreen);
				canvas.drawText(" -> " + SplashTower.sDamage[lvl], 255, 144, paintRedOrGreen);
				canvas.drawText(" -> " + SplashTower.sRange[lvl], 255, 160, paintRedOrGreen);
				canvas.drawText(" -> " + SplashTower.sSplashRadius[lvl], 255, 176, paintRedOrGreen);
				break;
			}
		}

		//paint sell-button
		canvas.drawRoundRect(sBtnSell,10,10,sPaintBtnBox);
		canvas.drawText("Sell", sBtnSell.left+18, sBtnSell.top+20, sPaintTextBlack);
		canvas.drawText("Sell", sBtnSell.left+17, sBtnSell.top+18, sPaintTextWhite);

		canvas.drawText(GameModel.mSelectedTower.sellPrice()+"$", sBtnSell.left+14, 
				sBtnSell.top+(sBtnSell.height()/2)+15, sPaintTextBlack);
		canvas.drawText(GameModel.mSelectedTower.sellPrice()+"$", sBtnSell.left+12, 
				sBtnSell.top+(sBtnSell.height()/2)+13, sPaintTextWhite);


		// if the tower is not fully upgraded and the player affords upgrading it
		if (GameModel.mSelectedTower.canUpgrade() && 
				GameModel.sCurrentPlayer.getMoney() >= GameModel.mSelectedTower.getUpgradeCost()) {

			canvas.drawRoundRect(sBtnUpgrade,6,6,sPaintBoxGreen);
			canvas.drawText("Upgrade: " + GameModel.mSelectedTower.getUpgradeCost() + "$",
					sBtnUpgrade.left+16, sBtnUpgrade.top+(sBtnSell.height()/2)+6, sPaintTextBlack);
			canvas.drawText("Upgrade: " + GameModel.mSelectedTower.getUpgradeCost() + "$",
					sBtnUpgrade.left+15, sBtnUpgrade.top+(sBtnSell.height()/2)+4, sPaintTextWhite);

			// if the tower is not fully upgraded, but the player can't afford upgrading
		} else if (GameModel.mSelectedTower.canUpgrade() && 
				GameModel.sCurrentPlayer.getMoney() < GameModel.mSelectedTower.getUpgradeCost()) {

			canvas.drawRoundRect(sBtnUpgrade,6,6,sPaintBoxRed);
			canvas.drawText("Upgrade: " + GameModel.mSelectedTower.getUpgradeCost() + "$",
					sBtnUpgrade.left+16, sBtnUpgrade.top+(sBtnSell.height()/2)+6, sPaintTextBlack);
			canvas.drawText("Upgrade: " + GameModel.mSelectedTower.getUpgradeCost() + "$",
					sBtnUpgrade.left+15, sBtnUpgrade.top+(sBtnSell.height()/2)+4, sPaintTextWhite);

			// if the tower is fully upgraded
		} else if (GameModel.mSelectedTower.canUpgrade() == false) {

			Paint paint = new Paint();
			paint.setARGB(255, 100, 100, 100);

			canvas.drawRoundRect(sBtnUpgrade,6,6,paint);
			canvas.drawText("Fully upgraded!",
					sBtnUpgrade.left+10, sBtnUpgrade.top+(sBtnSell.height()/2)+6, sPaintTextBlack);
			canvas.drawText("Fully upgraded!",
					sBtnUpgrade.left+9, sBtnUpgrade.top+(sBtnSell.height()/2)+4, sPaintTextWhite);
		}
	}


	private void drawCurrentTower(Canvas canvas) {
		// draw the chosen tower
		canvas.drawBitmap(
				mBitMapCache.get(GameModel.mMovableTower.getTower().getImage()), 
				GameModel.GAME_TILE_SIZE*((int)GameModel.mMovableTower.getTower().getX() / GameModel.GAME_TILE_SIZE),
				GameModel.GAME_TILE_SIZE*((int)GameModel.mMovableTower.getTower().getY() / GameModel.GAME_TILE_SIZE), 
				null);

		// draw a red transparent rectangle on every occupied tile
		for (Point p : GameModel.sOccupiedTilePositions) {
			canvas.drawRect(
					p.x*GameModel.GAME_TILE_SIZE,
					p.y*GameModel.GAME_TILE_SIZE,
					(1+p.x)*GameModel.GAME_TILE_SIZE,
					(1+p.y)*GameModel.GAME_TILE_SIZE,
					gridpaint);
		}

		// draw a circle that shows the tower's range
		// one color if it can be placed on current location, another if can't
		if (GameModel.canAddTower(GameModel.mMovableTower.getTower())) {
			canvas.drawCircle(
					GameModel.GAME_TILE_SIZE*
					((int)GameModel.mMovableTower.getTower().getX() / GameModel.GAME_TILE_SIZE + (GameModel.mMovableTower.getTower().getWidth()/2)),
					GameModel.GAME_TILE_SIZE*
					((int)GameModel.mMovableTower.getTower().getY() / GameModel.GAME_TILE_SIZE + (GameModel.mMovableTower.getTower().getHeight()/2)),
					GameModel.mMovableTower.getTower().getRange(),
					rangeIndicationPaint);
		} else {
			canvas.drawCircle(
					GameModel.GAME_TILE_SIZE*
					((int)GameModel.mMovableTower.getTower().getX() / GameModel.GAME_TILE_SIZE + (GameModel.mMovableTower.getTower().getWidth()/2)),
					GameModel.GAME_TILE_SIZE*
					((int)GameModel.mMovableTower.getTower().getY() / GameModel.GAME_TILE_SIZE + (GameModel.mMovableTower.getTower().getHeight()/2)),
					GameModel.mMovableTower.getTower().getRange(),
					noRangeIndicationPaint);
		}
	}

	private void drawTooltip(Canvas canvas) {

		// draw tooltip for the current tower
		canvas.drawRoundRect(sTransparentBox,10,10, sPaintTransparentBox);

		// if a tower is being bought
		if (GameModel.mMovableTower != null) {
			canvas.drawBitmap(mBitMapCache.get(GameModel.mMovableTower.getTower().getImage()), 100, 80, null);
			canvas.drawText(GameModel.mMovableTower.getTower().getName(), 161, 89+2, sPaintTextBlack);
			canvas.drawText(GameModel.mMovableTower.getTower().getName(), 160, 89, sPaintTextWhite);

			canvas.drawText("Speed: " + GameModel.mMovableTower.getTower().getAttackSpeed(), 161, 110+2, sPaintTextBlack);
			canvas.drawText("Damage: " + GameModel.mMovableTower.getTower().getDamage(), 161, 130+2, sPaintTextBlack);
			canvas.drawText("Range: " + GameModel.mMovableTower.getTower().getRange(), 161, 150+2, sPaintTextBlack);

			canvas.drawText("Speed: " + GameModel.mMovableTower.getTower().getAttackSpeed(), 160, 110, sPaintTextWhite);
			canvas.drawText("Damage: " + GameModel.mMovableTower.getTower().getDamage(), 160, 130, sPaintTextWhite);
			canvas.drawText("Range: " + GameModel.mMovableTower.getTower().getRange(), 160, 150, sPaintTextWhite);

			Paint paintRedOrGreen;
			if (GameModel.sCurrentPlayer.getMoney() >= GameModel.mMovableTower.getTower().getCost())
				paintRedOrGreen = sPaintBoxGreen;
			else
				paintRedOrGreen = sPaintBoxRed;

			switch (GameModel.mMovableTower.getTower().getType()){
			case Tower.BASIC:
				canvas.drawText("Cost: " + GameModel.mMovableTower.getTower().getCost(), 161, 170+2, sPaintTextBlack);
				canvas.drawText("Cost: " + GameModel.mMovableTower.getTower().getCost(), 160, 170, paintRedOrGreen);
				break;

			case Tower.AIR:
				canvas.drawText("Cost: " + GameModel.mMovableTower.getTower().getCost(), 161, 170+2, sPaintTextBlack);
				canvas.drawText("Cost: " + GameModel.mMovableTower.getTower().getCost(), 160, 170, paintRedOrGreen);
				break;

			case Tower.SLOW:
				canvas.drawText("Slow: " + GameModel.mMovableTower.getTower().getSlow() + "%", 161, 170+2, sPaintTextBlack);
				canvas.drawText("Cost: " + GameModel.mMovableTower.getTower().getCost(), 161, 190+2, sPaintTextBlack);
				canvas.drawText("Slow: " + GameModel.mMovableTower.getTower().getSlow() + "%", 160, 170, sPaintTextWhite);
				canvas.drawText("Cost: " + GameModel.mMovableTower.getTower().getCost(), 160, 190, paintRedOrGreen);
				break;

			case Tower.SPLASH:
				canvas.drawText("Splash: " + GameModel.mMovableTower.getTower().getSplashRadius(), 161, 170+2, sPaintTextBlack);
				canvas.drawText("Cost: " + GameModel.mMovableTower.getTower().getCost(), 161, 190+2, sPaintTextBlack);
				canvas.drawText("Splash: " + GameModel.mMovableTower.getTower().getSplashRadius(), 160, 170, sPaintTextWhite);
				canvas.drawText("Cost: " + GameModel.mMovableTower.getTower().getCost(), 160, 190, paintRedOrGreen);
				break;
			}

			canvas.drawText(GameModel.mMovableTower.getTower().getDescription(), 101, 210+2, sPaintTextBlack);
			canvas.drawText(GameModel.mMovableTower.getTower().getDescription(), 100, 210, sPaintTextWhite);

			canvas.drawText("Drag to buy this tower!", 101, 230+2, sPaintTextBlack);
			canvas.drawText("Drag to buy this tower!", 100, 230, sPaintTextWhite);
		} else {

			Paint paintRedOrGreen;
			if (GameModel.sCurrentPlayer.getCurrentTrackScore() >= mSnowballTreshold)
				paintRedOrGreen = sPaintBoxGreen;
			else
				paintRedOrGreen = sPaintBoxRed;

			// if a snowball is being bought
			canvas.drawBitmap(mBitMapCache.get(R.drawable.bigsnowball), 90, 80,null);

			canvas.drawText("Snowball", 151, 90+2, sPaintTextBlack);
			canvas.drawText("Run over your enemies!", 131, 117+2, sPaintTextBlack);
			canvas.drawText("Control the snowball by", 131, 139+2, sPaintTextBlack);
			canvas.drawText("tilting your phone!", 131, 161+2, sPaintTextBlack);
			canvas.drawText("Available at "+mSnowballTreshold+" points.", 101, 210+2, sPaintTextBlack);

			canvas.drawText("Snowball", 150, 90, sPaintTextWhite);
			canvas.drawText("Run over your enemies!", 130, 117, sPaintTextWhite);
			canvas.drawText("Control the snowball by", 130, 139, sPaintTextWhite);
			canvas.drawText("tilting your phone!", 130, 161, sPaintTextWhite);
			canvas.drawText("Available at "+mSnowballTreshold+" points.", 100, 210, paintRedOrGreen);
		}
	}

	private void drawButtons(Canvas canvas) {

		Paint paint = new Paint();
		paint.setARGB(100,100,100,100);
		paint.setStyle(Paint.Style.FILL);

		canvas.drawRoundRect(sBtn1, 5, 5, paint);
		canvas.drawRoundRect(sBtn2, 5, 5, paint);
		canvas.drawRoundRect(sBtn3, 5, 5, paint);
		canvas.drawRoundRect(sBtn4, 5, 5, paint);
		canvas.drawRoundRect(sBtn5, 5, 5, paint);

		if(GameModel.GAME_STATE == GameModel.STATE_PAUSED)
			canvas.drawBitmap(mBitMapCache.get(R.drawable.pause2),20,5,null);
		else
			canvas.drawBitmap(mBitMapCache.get(R.drawable.pause),20,5,null);

		if(GameModel.mFast)
			canvas.drawBitmap(mBitMapCache.get(R.drawable.fastforward2),20,285,null);
		else
			canvas.drawBitmap(mBitMapCache.get(R.drawable.fastforward),20,285,null);

		Paint paintalfa = new Paint();

		//if the tower build buttons should be "unavaliable" or not
		if(mTower1.getCost() >= GameModel.sCurrentPlayer.getMoney())
			paintalfa.setAlpha(100);
		else
			paintalfa.setAlpha(255);

		canvas.drawBitmap(mBitMapCache.get(R.drawable.basictower),432,25,paintalfa);

		if(mTower2.getCost() >= GameModel.sCurrentPlayer.getMoney())
			paintalfa.setAlpha(100);
		else
			paintalfa.setAlpha(255);

		canvas.drawBitmap(mBitMapCache.get(R.drawable.eskimotowersplash),432,85,paintalfa);

		if(mTower3.getCost() >= GameModel.sCurrentPlayer.getMoney())
			paintalfa.setAlpha(100);
		else
			paintalfa.setAlpha(255);

		canvas.drawBitmap(mBitMapCache.get(R.drawable.slowtower),432,145,paintalfa);

		if(mTower4.getCost() >= GameModel.sCurrentPlayer.getMoney())
			paintalfa.setAlpha(100);
		else
			paintalfa.setAlpha(255);

		canvas.drawBitmap(mBitMapCache.get(R.drawable.airtower1),432,205,paintalfa);

		if(GameModel.sCurrentPlayer.getCurrentTrackScore() >= mSnowballTreshold*(1+mUsedSnowballs))
			paintalfa.setAlpha(255);
		else
			paintalfa.setAlpha(100);


		if (GameModel.sCheatEnabled)
			paintalfa.setAlpha(255);

		canvas.drawBitmap(mBitMapCache.get(R.drawable.bigsnowball),432,265,paintalfa);
	}

	/**
	 * Draws all projectiles currently on the gamefield
	 * @param canvas
	 */
	private void drawProjectiles(Canvas canvas) {
		// draw all projectiles
		int size = GameModel.sProjectiles.size();
		for (int i = 0; i < size; i++) {
			Projectile p = GameModel.sProjectiles.get(i);
			Bitmap bitmapOrg = mBitMapCache.get(GameModel.sProjectiles.get(i).getProjImage()); 
			//R.drawable.projsplash_big before ahmed
			Matrix matrix = new Matrix(); 

			// rotate the Bitmap 
			//matrix.postRotate((float) (-1*p.getAngle()/Math.PI*180));
			Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, bitmapOrg.getWidth(), 
					bitmapOrg.getHeight(), matrix, true); 
			canvas.drawBitmap(resizedBitmap, (int) p.getX(), (int) p.getY(), null);
		}
	}

	/**
	 * Draws any active towers on the gamefield
	 * @param canvas
	 */
	private void drawTowers(Canvas canvas) {
		// draw all towers
		int size = GameModel.sTowers.size();
		for (int i = 0; i < size; i++) {
			Tower t = GameModel.sTowers.get(i);
			canvas.drawBitmap(mBitMapCache.get(t.getImage()), (int) t.getX() , (int) t.getY() , null);
		}
	}

	/**
	 * Draws any active snowballs currently on the gamefield
	 * @param canvas
	 */
	private void drawSnowballs(Canvas canvas) {
		// draw snowballs
		float radius;
		for (int i = 0; i < GameModel.sSnowballs.size(); i++) {
			Snowball s = GameModel.sSnowballs.get(i);
			radius = s.getRadius();

			canvas.drawCircle((float)s.getX(), (float)s.getY(), radius, snowPaint);
			canvas.drawCircle((float)s.getX(), (float)s.getY(), radius, borderPaint);


		}
	}

	/**
	 * Draws all active mobs on the gamefield
	 * @param canvas
	 */
	private void drawMobs(Canvas canvas) {
		// for all mobs
		for (int i = GameModel.sMobs.size()-1; i >= 0; i--) {
			Mob m = GameModel.sMobs.get(i);

			// create offsets for AIR type mobs. they fly higher than other animals
			int mOffset,mOffset2;
			if(m.getType() == Mob.AIR) {
				mOffset = 25;
				mOffset2 = 2;
			} else {
				mOffset = 0;
				mOffset2 = 0;
			}

			if (m.isDead()) {
				sMobPaint.setAlpha((int)(255*m.mAnimation/m.mAnimationDeath));
			} else {
				sMobPaint.setAlpha(255);
				
				int hpRatio = (int)(255* (double)m.getHealth() / (double)m.getMaxHealth());

				// drawing health bars for each mob, first a black background
				healthBarPaint.setARGB(255, 0, 0, 0);
				canvas.drawRect(
						(float)m.getX() - 2 + mOffset2,
						(float) m.getY() - 5 - mOffset,
						(float) (m.getX() + 24 + mOffset2),
						(float) m.getY() - 2 - mOffset,
						healthBarPaint);

				// draw current health on the health bar
				healthBarPaint.setARGB(255, 255 - hpRatio, hpRatio, 0);
				canvas.drawRect(
						(float)m.getX() - 2 + mOffset2,
						(float) m.getY() - 5 - mOffset,
						(float) (m.getX() + (24 * hpRatio/255)) + mOffset2,
						(float) m.getY() - 2 - mOffset,
						healthBarPaint);	
				
			}
			
			// draw the image of the mob.
			canvas.drawBitmap(mBitMapCache.get(m.getMobImage()), (int) m.getX(), (int) m.getY() - mOffset, sMobPaint);

					

		}
	}

	/**
	 * Draws the background map and such.
	 * 
	 * Uses different background images depending on what track is played
	 * 
	 * @param canvas
	 */
	private void drawBackground(Canvas canvas) {

		// draw the background
		switch(GameModel.getTrack()) {
		case 1: // track 1
			canvas.drawBitmap(mBitMapCache.get(R.drawable.map1), 0, 0, null);
			break;
		case 2: //track 2
			canvas.drawBitmap(mBitMapCache.get(R.drawable.map2), 0, 0, null); 
			break;	
		case 3: //track 3
			canvas.drawBitmap(mBitMapCache.get(R.drawable.map3), 0, 0, null); 
			break;
		case 4: //track 4
			canvas.drawBitmap(mBitMapCache.get(R.drawable.map4), 0, 0, null); 
			break;
		case 5: //track 5
			canvas.drawBitmap(mBitMapCache.get(R.drawable.map5), 0, 0, null); 
			break;		
		}

		//draw the "end-point-base"
		canvas.drawBitmap(mBitMapCache.get(R.drawable.basee),403,0,null);
	}

	/**
	 * Draws the rewards that are showed when mobs die.
	 * @param canvas
	 */
	private void drawRewardsAfterDeadMob(Canvas canvas){
		for (int i = 0; i < GameModel.sShowRewardForMob.size(); i++) {
			Mob m = GameModel.sShowRewardForMob.get(i);
			
			canvas.drawText("" +m.getReward(),
					(int)m.getX() + 1,
					(int)m.getY() - m.getRewAni()  + 1,
					sMoneyAfterDeadBg);
			canvas.drawText("" +m.getReward(),
					(int)m.getX(),
					(int)m.getY() - m.getRewAni(),
					sMoneyAfterDead);
			m.incRewAni();
			if(m.getRewAni() > 12){
				GameModel.sShowRewardForMob.remove(m);
			}
		}
	}
	
	/**
	 * Draws the explosions from collided projectiles.
	 * @param canvas
	 */
	private void drawExplForProj(Canvas canvas){
		for (int i = 0; i < GameModel.sShowExplForProj.size(); i++) {
			Projectile p = GameModel.sShowExplForProj.get(i);

			// jag testar med en annan grej här lite....
			// om det är splash så ritar den ut en cirkel som blir större och fadar ut
			if (p instanceof SplashProjectile) {
				
				sMobPaint.setARGB((int)(255 - 255*p.mExplAnimation/p.getExplosionTime()), 255, 51, 0);
			
				canvas.drawCircle((float)p.getX(), (float)p.getY(), (((SplashProjectile)p).mSplashRadius)*(p.mExplAnimation/p.getExplosionTime()), sMobPaint);
			} else if(p instanceof SlowProjectile){

				sMobPaint.setARGB((int)(255 - 255*p.mExplAnimation/p.getExplosionTime()), 0, 0, 200);
			
				canvas.drawCircle((float)p.getX(), (float)p.getY(), 25*(p.mExplAnimation/p.getExplosionTime()), sMobPaint);
			
		    } else {
				canvas.drawBitmap(mBitMapCache.get(p.getProjImage()), (int) p.getX() -30, (int) p.getY() -30, null);

			}
			

		}
	}

	/**
	 * Configures all Paint-objects used in onDraw().
	 */
	private void setupPaint() {
		
		sMoneyAfterDead.setARGB(255,255,255,0);
		sMoneyAfterDead.setTextSize(16);

		sMoneyAfterDeadBg.setARGB(255,0,0,0);
		sMoneyAfterDeadBg.setTextSize(16);

		// set gray color for buttons in in-game menus 
		mBtnPaint.setARGB(255, 50, 50, 50);

		// set text size of the FPS meter and such and such
		sPaintText.setTextSize(16);
		Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
		sPaintText.setTypeface(font);
		sPaintText.setAntiAlias(true);

		sPaintTextWhite.setTextSize(16);
		sPaintTextWhite.setARGB(255, 255, 255, 255);
		Typeface font2 = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
		sPaintTextWhite.setTypeface(font2);
		sPaintTextWhite.setAntiAlias(true);

		sPaintTextBlack.setTextSize(16);
		sPaintTextBlack.setARGB(255, 0, 0, 0);
		Typeface font3 = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
		sPaintTextBlack.setTypeface(font3);
		sPaintTextBlack.setAntiAlias(true);

		// set color of the selected tower box
		sPaintTransparentBox.setARGB(180, 51, 51, 51);

		// set color of the upgrade- and sell buttons in the selected tower box
		sPaintBtnBox.setARGB(255, 51, 51, 51);

		// set text size and color of the text in selected tower box
		sPaintBoxText.setARGB(255, 255, 255, 255);
		sPaintBoxText.setTextSize(14);

		sPaintBoxGreen.setTextSize(16);
		sPaintBoxGreen.setARGB(255, 20, 190, 30);
		sPaintBoxGreen.setTypeface(font3);
		sPaintBoxGreen.setAntiAlias(true);

		sPaintBoxRed.setTextSize(16);
		sPaintBoxRed.setARGB(255, 255, 30, 20);
		sPaintBoxRed.setTypeface(font3);
		sPaintBoxRed.setAntiAlias(true);

		boxTextPaintTitle.setARGB(255, 255, 255, 255);
		boxTextPaintTitle.setTextSize(22);

		// set color and width of the lines in the selected tower box
		sPaintLine.setARGB(255, 255, 255, 0);
		sPaintLine.setStrokeWidth(5);

		// set color and style of the range indicators
		rangeIndicationPaint.setARGB(80, 80, 255, 80);
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
		//Log.v("GamePanel","surfaceDestroyed");
		boolean retry = true;
		mGameThread.setRunning(false);
		while (retry) {
			try {
				mGameThread.join();
				//Log.v("GamePanel","test");
				retry = false;
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
		}
		//Log.i("thread", "Thread terminated...");
		// To prevent memory filled exception
		mBitMapCache = new HashMap<Integer, Bitmap>();
	}


}