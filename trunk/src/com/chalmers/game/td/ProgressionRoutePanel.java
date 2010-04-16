package com.chalmers.game.td;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ProgressionRoutePanel extends SurfaceView implements SurfaceHolder.Callback{

	private ProgressionThread thread;
	private Map<Integer, Bitmap> mBitMapCache = new HashMap<Integer, Bitmap>();
	private Activity mActivity;
	
	private final RectF mButtonTrack1 = new RectF(45, 200, 100, 240);
	
	public ProgressionRoutePanel(Context context) {
		super(context);				
		fillBitmapCache();		
	
		mActivity = (Activity) context;		
		getHolder().addCallback(this);
		thread = new ProgressionThread(this);
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();

	}
	
	private void fillBitmapCache() {
		mBitMapCache.put(R.drawable.progressionroute, BitmapFactory.decodeResource(getResources(), R.drawable.progressionroute));
		mBitMapCache.put(R.drawable.progdone, BitmapFactory.decodeResource(getResources(), R.drawable.progdone));
		mBitMapCache.put(R.drawable.prognotdone, BitmapFactory.decodeResource(getResources(), R.drawable.prognotdone));
		mBitMapCache.put(R.drawable.prognext, BitmapFactory.decodeResource(getResources(), R.drawable.prognext));

	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {		
		
		synchronized (getHolder()) {			
			
			switch(event.getAction()) {
			
				case MotionEvent.ACTION_UP:
					
					Log.v("PRESSED ON PROGRESSION ROUTE", "X: " + event.getX() + " Y: " + event.getY());
										
					if (mButtonTrack1.contains(event.getX(), event.getY())) {
						
						Log.v("PRESSED ON PROGRESSION ROUTE", "You have pressed track 1");
					
						GameModel.setTrack(1);
						
						thread.setRunning(false);
						getHolder().removeCallback(this);
						mActivity.setContentView(new GamePanel(getContext()));
					} else {
						GameModel.setTrack(2);
						
						thread.setRunning(false);
						getHolder().removeCallback(this);
						mActivity.setContentView(new GamePanel(getContext()));
					}
					
				break;
			}
			
		}
		
		return true;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
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
		
		canvas.drawBitmap(mBitMapCache.get(R.drawable.progressionroute),0,0,null);
		/*
		Paint p = new Paint();
		p.setARGB(150, 50, 50, 50);

		canvas.drawRoundRect(mButtonTrack1,5,5, p);
		 */
		canvas.drawBitmap(mBitMapCache.get(R.drawable.progdone), 90, 50,null);
		canvas.drawBitmap(mBitMapCache.get(R.drawable.prognext), 100, 180,null);
		canvas.drawBitmap(mBitMapCache.get(R.drawable.prognotdone), 280, 180,null);
		canvas.drawBitmap(mBitMapCache.get(R.drawable.prognotdone), 280, 50,null);
		canvas.drawBitmap(mBitMapCache.get(R.drawable.prognotdone), 420, 140,null);

	}

}
