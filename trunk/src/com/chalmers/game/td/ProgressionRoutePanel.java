package com.chalmers.game.td;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class ProgressionRoutePanel extends SurfaceView implements SurfaceHolder.Callback{

	private static final int STATE_CHOOSETRACK = 1;
	private static final int STATE_STARTTRACK = 2;
	private int STATE_PROGSTATE = STATE_CHOOSETRACK;
	
	private static final Paint sPaintTextWhite = new Paint();
	private static final Paint sPaintTextBlack = new Paint();
	
	private ProgressionThread thread;
	private Map<Integer, Bitmap> mBitMapCache = new HashMap<Integer, Bitmap>();
	private Activity mActivity;
	private int tooltip = 0;
	private int trackPic = 0;
	private String trackName = "";
	private int menuPic = 0;
	
	public void updateSound() {
		
		try {
			if (GameModel.sMusicEnabled) {
				
				SoundManager.playMusic(SoundManager.getProgressionRouteMusic());				
			}
		} catch (IllegalStateException ise) {
			SoundManager.initializeSound(getContext());
		}
	}
	
	public ProgressionRoutePanel(Context context) {
		super(context);				
		fillBitmapCache();		
	
		mActivity = (Activity) context;		
		getHolder().addCallback(this);
		thread = new ProgressionThread(this);
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();
		
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

	}
	
	private void fillBitmapCache() {
		mBitMapCache.put(R.drawable.progressionroute, BitmapFactory.decodeResource(getResources(), R.drawable.progressionroute));
		mBitMapCache.put(R.drawable.progdone, BitmapFactory.decodeResource(getResources(), R.drawable.progdone));
		mBitMapCache.put(R.drawable.prognotdone, BitmapFactory.decodeResource(getResources(), R.drawable.prognotdone));
		mBitMapCache.put(R.drawable.prognext, BitmapFactory.decodeResource(getResources(), R.drawable.prognext));
		mBitMapCache.put(R.drawable.progmapchoose, BitmapFactory.decodeResource(getResources(), R.drawable.progmapchoose));
		mBitMapCache.put(R.drawable.menutop, BitmapFactory.decodeResource(getResources(), R.drawable.menutop));
		mBitMapCache.put(R.drawable.menumid, BitmapFactory.decodeResource(getResources(), R.drawable.menumid));
		mBitMapCache.put(R.drawable.menubot, BitmapFactory.decodeResource(getResources(), R.drawable.menubot));
		mBitMapCache.put(R.drawable.menutop2, BitmapFactory.decodeResource(getResources(), R.drawable.menutop2));
		mBitMapCache.put(R.drawable.menumid2, BitmapFactory.decodeResource(getResources(), R.drawable.menumid2));
		mBitMapCache.put(R.drawable.menubot2, BitmapFactory.decodeResource(getResources(), R.drawable.menubot2));
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {		
		
		synchronized (getHolder()) {			
			
			switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				
				
				switch (STATE_PROGSTATE) {
				case STATE_CHOOSETRACK:
					if (event.getX() > 35 && event.getX() < 35+80 &&
							event.getY() > 40 && event.getY() < 40+90) {
							
						trackPic = 1;
						
					} else if (event.getX() > 30 && event.getX() < 30+110 &&
							event.getY() > 200 && event.getY() < 200+70) {
						
						trackPic = 2;
						
					} else if (event.getX() > 205 && event.getX() < 205+115 &&
							event.getY() > 210 && event.getY() < 210+70) {

						trackPic = 3;
						
					} else if (event.getX() > 200 && event.getX() < 200+100 &&
							event.getY() > 80 && event.getY() < 80+75) {
						
						trackPic = 4;
						
					} else if (event.getX() > 350 && event.getX() < 100+350 &&
							event.getY() > 150 && event.getY() < 150+120) {

						trackPic = 5;
					} else {
						trackPic = 0;
					}
					break;
				case STATE_STARTTRACK:
					if(event.getX() >= 100 && event.getX() <= 344 && event.getY() >= 80+34 &&  event.getY() <= 80+34+36){
						menuPic = 1;
					}
					else if(event.getX() >= 100 && event.getX() <= 344 && event.getY() >= 80+34+36 &&  event.getY() <= 80+34+36+36){
						menuPic = 2;
					}
					else if(event.getX() >= 100 && event.getX() <= 344 && event.getY() >= 80+34+36+36 &&  event.getY() <= 80+34+36+36+34){
						menuPic = 3;
					} else {
						menuPic = 0;
					}
					break;
				}
				
				
				tooltip = 0;
				break;
			case MotionEvent.ACTION_MOVE:
				
				switch (STATE_PROGSTATE) {
					case STATE_CHOOSETRACK:
						if (event.getX() > 35 && event.getX() < 35+80 &&
								event.getY() > 40 && event.getY() < 40+90) {
								
							trackPic = 1;
							
						} else if (event.getX() > 30 && event.getX() < 30+110 &&
								event.getY() > 200 && event.getY() < 200+70) {
							
							trackPic = 2;
							
						} else if (event.getX() > 205 && event.getX() < 205+115 &&
								event.getY() > 210 && event.getY() < 210+70) {
	
							trackPic = 3;
							
						} else if (event.getX() > 200 && event.getX() < 200+100 &&
								event.getY() > 80 && event.getY() < 80+75) {
							
							trackPic = 4;
							
						} else if (event.getX() > 350 && event.getX() < 100+350 &&
								event.getY() > 150 && event.getY() < 150+120) {
	
							trackPic = 5;
						} else {
							trackPic = 0;
						}
						break;
					case STATE_STARTTRACK:
						
						if(event.getX() >= 100 && event.getX() <= 344 && event.getY() >= 80+34 &&  event.getY() <= 80+34+36){
							menuPic = 1;
						}
						else if(event.getX() >= 100 && event.getX() <= 344 && event.getY() >= 80+34+36 &&  event.getY() <= 80+34+36+36){
							menuPic = 2;
						}
						else if(event.getX() >= 100 && event.getX() <= 344 && event.getY() >= 80+34+36+36 &&  event.getY() <= 80+34+36+36+34){
							menuPic = 3;
						} else {
							menuPic = 0;
						}
						break;
				}
				
				break; 
			case MotionEvent.ACTION_UP:
					
					Log.v("PRESSED ON PROGRESSION ROUTE", "X: " + event.getX() + " Y: " + event.getY());
					
					switch (STATE_PROGSTATE) {
					case STATE_CHOOSETRACK:
						
						if (event.getX() > 35 && event.getX() < 35+80 &&
								event.getY() > 40 && event.getY() < 40+90) {
								
							trackPic = 1;
							
						} else if (event.getX() > 30 && event.getX() < 30+110 &&
								event.getY() > 200 && event.getY() < 200+70) {
							
							trackPic = 2;
							
						} else if (event.getX() > 205 && event.getX() < 205+115 &&
								event.getY() > 210 && event.getY() < 210+70) {
	
							trackPic = 3;
							
						} else if (event.getX() > 200 && event.getX() < 200+100 &&
								event.getY() > 80 && event.getY() < 80+75) {
							
							trackPic = 4;
							
						} else if (event.getX() > 350 && event.getX() < 100+350 &&
								event.getY() > 150 && event.getY() < 150+120) {
	
							trackPic = 5;
						}
						
						if (event.getX() > 35 && event.getX() < 35+80 &&
								event.getY() > 40 && event.getY() < 40+90) {
							
							// Button for first level pressed
							
							Log.v("ProgressionRoutePanel.onTouchEvent", "Starting track 1");
						
							GameModel.setTrack(1);
							trackName = "North Pole";
							STATE_PROGSTATE = STATE_STARTTRACK;
							
						} else if (event.getX() > 30 && event.getX() < 30+110 &&
								event.getY() > 200 && event.getY() < 200+70) {
							
							// Button for second level pressed
							
							// TODO may have missunderstood but i think it should be 1 rather than 2 here...
							if (GameModel.currentPlayer.getTrackScore(1) != 0) {
								GameModel.setTrack(2);
								Log.v("ProgressionRoutePanel.onTouchEvent", "Starting track 2");
								trackName = "North Pole";
								STATE_PROGSTATE = STATE_STARTTRACK;
	
							} else {
								tooltip = 2;
								Toast.makeText(getContext(), "You can't start track "+tooltip+" yet!", Toast.LENGTH_SHORT).show();
							}
							
							
						} else if (event.getX() > 205 && event.getX() < 205+115 &&
								event.getY() > 210 && event.getY() < 210+70) {
							
							// Button for third level pressed
							
							if (GameModel.currentPlayer.getTrackScore(2) != 0) {
								GameModel.setTrack(3);
								Log.v("ProgressionRoutePanel.onTouchEvent", "Starting track 3");
								STATE_PROGSTATE = STATE_STARTTRACK;

							} else {
								tooltip = 3;
								Toast.makeText(getContext(), "You can't start track "+tooltip+" yet!", Toast.LENGTH_SHORT).show();
							}
							
						} else if (event.getX() > 200 && event.getX() < 200+100 &&
								event.getY() > 80 && event.getY() < 80+75) {
							
							// Button for fourth level pressed

							if (GameModel.currentPlayer.getTrackScore(3) != 0) {
								GameModel.setTrack(4);
								Log.v("ProgressionRoutePanel.onTouchEvent", "Starting track 4");
								STATE_PROGSTATE = STATE_STARTTRACK;

							} else {
								tooltip = 4;
								Toast.makeText(getContext(), "You can't start track "+tooltip+" yet!", Toast.LENGTH_SHORT).show();
							}
							
						} else if (event.getX() > 350 && event.getX() < 100+350 &&
								event.getY() > 150 && event.getY() < 150+120) {
							
							// Button for fifth level pressed
							
							if (GameModel.currentPlayer.getTrackScore(4) != 0) {
								GameModel.setTrack(5);
								Log.v("ProgressionRoutePanel.onTouchEvent", "Starting track 5");
								STATE_PROGSTATE = STATE_STARTTRACK;

							} else {
								tooltip = 5;
								Toast.makeText(getContext(), "You can't start track "+tooltip+" yet!", Toast.LENGTH_SHORT).show();
							}
						}
						
						break;
					case STATE_STARTTRACK:
						
						if(event.getX() >= 100 && event.getX() <= 344 && event.getY() >= 80+34 &&  event.getY() <= 80+34+36){
							menuPic = 0;
						}
						else if(event.getX() >= 100 && event.getX() <= 344 && event.getY() >= 80+34+36 &&  event.getY() <= 80+34+36+36){
							beforeEnteringLevel();
						}
						else if(event.getX() >= 100 && event.getX() <= 344 && event.getY() >= 80+34+36+36 &&  event.getY() <= 80+34+36+36+34){
							STATE_PROGSTATE = STATE_CHOOSETRACK;
							menuPic = 0;
						} else {
							menuPic = 0;
						}
						
						break;
					}

					
				break;
			}
			
		}
		
		return true;
	}
	
	public void beforeEnteringLevel(){
		thread.setRunning(false);
		getHolder().removeCallback(this);
		mActivity.setContentView(new GamePanel(getContext()));
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		
		if (!thread.isAlive()) {
			thread = new ProgressionThread(this);			
		}
		
		thread.setRunning(true);
		thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.v("Progression","surfaceDestroyed");
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
		}
		
		// To prevent memory filled exception
		mBitMapCache = new HashMap<Integer, Bitmap>();
	}
	
	public void onDraw(Canvas canvas) {
		
		// draw background
		canvas.drawBitmap(mBitMapCache.get(R.drawable.progressionroute),0,0,null);

		// draw tooltip telling the user that he sucks and can't start that track
		// TODO don't show it as a Toast
		
//		if (tooltip != 0) {
//			Toast.makeText(getContext(), "You can't start track "+tooltip+" yet!", Toast.LENGTH_SHORT).show();
//		}
		
		// draw buttons that shows the players progress
		if (GameModel.currentPlayer.getTrackScore(1) == 0.0) {
			canvas.drawBitmap(mBitMapCache.get(R.drawable.prognext), 90, 50,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.prognotdone), 100, 180,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.prognotdone), 280, 180,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.prognotdone), 280, 50,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.prognotdone), 420, 140,null);
		} else if (GameModel.currentPlayer.getTrackScore(2) == 0.0) {
			canvas.drawBitmap(mBitMapCache.get(R.drawable.progdone), 90, 50,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.prognext), 100, 180,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.prognotdone), 280, 180,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.prognotdone), 280, 50,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.prognotdone), 420, 140,null);
		} else if (GameModel.currentPlayer.getTrackScore(3) == 0.0) {
			canvas.drawBitmap(mBitMapCache.get(R.drawable.progdone), 90, 50,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.progdone), 100, 180,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.prognext), 280, 180,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.prognotdone), 280, 50,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.prognotdone), 420, 140,null);
		} else if (GameModel.currentPlayer.getTrackScore(4) == 0.0) {
			canvas.drawBitmap(mBitMapCache.get(R.drawable.progdone), 90, 50,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.progdone), 100, 180,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.progdone), 280, 180,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.prognext), 280, 50,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.prognotdone), 420, 140,null);
		} else if (GameModel.currentPlayer.getTrackScore(5) == 0.0) {
			canvas.drawBitmap(mBitMapCache.get(R.drawable.progdone), 90, 50,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.progdone), 100, 180,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.progdone), 280, 180,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.progdone), 280, 50,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.prognext), 420, 140,null);
		} else {
			canvas.drawBitmap(mBitMapCache.get(R.drawable.progdone), 90, 50,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.progdone), 100, 180,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.progdone), 280, 180,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.progdone), 280, 50,null);
			canvas.drawBitmap(mBitMapCache.get(R.drawable.progdone), 420, 140,null);
		}
		
		switch (STATE_PROGSTATE) {
			case STATE_CHOOSETRACK:
				if(trackPic == 1){
					canvas.drawBitmap(mBitMapCache.get(R.drawable.progmapchoose), 40, 50,null);
				} else if(trackPic == 2){
					canvas.drawBitmap(mBitMapCache.get(R.drawable.progmapchoose), 50, 190,null);
				} else if(trackPic == 3){
					canvas.drawBitmap(mBitMapCache.get(R.drawable.progmapchoose), 230, 210,null);
				} else if(trackPic == 4){
					canvas.drawBitmap(mBitMapCache.get(R.drawable.progmapchoose), 220, 80,null);
				} else if(trackPic == 5){
					canvas.drawBitmap(mBitMapCache.get(R.drawable.progmapchoose), 360, 160,null);
				} else {
					
				}
				break;
				
			case STATE_STARTTRACK:
				
				if(trackPic == 1){
					canvas.drawBitmap(mBitMapCache.get(R.drawable.progmapchoose), 40, 50,null);
				} else if(trackPic == 2){
					canvas.drawBitmap(mBitMapCache.get(R.drawable.progmapchoose), 50, 190,null);
				} else if(trackPic == 3){
					canvas.drawBitmap(mBitMapCache.get(R.drawable.progmapchoose), 230, 210,null);
				} else if(trackPic == 4){
					canvas.drawBitmap(mBitMapCache.get(R.drawable.progmapchoose), 220, 80,null);
				} else if(trackPic == 5){
					canvas.drawBitmap(mBitMapCache.get(R.drawable.progmapchoose), 360, 160,null);
				} else {
					
				}
				
				canvas.drawBitmap(mBitMapCache.get(R.drawable.menutop),100,80,null);
				
				if(menuPic == 1){
					canvas.drawBitmap(mBitMapCache.get(R.drawable.menumid),100,80+34,null);
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

				
				canvas.drawText(trackName, 166,80+20+2,sPaintTextBlack);
				canvas.drawText("Highscore: " + (int)Highscore.getInstance().getTrackScore(trackPic),
						171,80+34+20+2,sPaintTextBlack);
				canvas.drawText("Start Level",171,80+34+36+20+2,sPaintTextBlack);
				canvas.drawText("Cancel", 171, 80+34+36+36+20+2, sPaintTextBlack);
				
				canvas.drawText(trackName, 165,80+20,sPaintTextWhite);
				canvas.drawText("Highscore: " + (int)Highscore.getInstance().getTrackScore(trackPic),
						170,80+34+20,sPaintTextWhite);
				canvas.drawText("Start Level",170,80+34+36+20,sPaintTextWhite);
				canvas.drawText("Cancel", 170, 80+34+36+36+20, sPaintTextWhite);

				break;
		}



	}

}
