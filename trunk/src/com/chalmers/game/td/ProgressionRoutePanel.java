package com.chalmers.game.td;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ProgressionRoutePanel extends SurfaceView implements SurfaceHolder.Callback{

	private ProgressionThread thread;
	private Map<Integer, Bitmap> mBitMapCache = new HashMap<Integer, Bitmap>();
	private Activity mActivity;
	
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
		mBitMapCache.put(R.drawable.progressionroute_background, BitmapFactory.decodeResource(getResources(), R.drawable.progressionroute_background));
	
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		synchronized (getHolder()) {
			
			if (event.getAction() == MotionEvent.ACTION_UP) {
				
				thread.setRunning(false);
				mActivity.setContentView(new GamePanel(getContext()));
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
		mBitMapCache = null;
	}
	
	public void onDraw(Canvas canvas) {
		
		canvas.drawBitmap(mBitMapCache.get(R.drawable.progressionroute_background),0,0,null);
	}

}
