package com.chalmers.game.td;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ProgressionRoutePanel extends SurfaceView implements SurfaceHolder.Callback{

	
	
	public ProgressionRoutePanel(Context context) {
		super(context);				
		
//		Canvas c = this.getHolder().lockCanvas(null);
//		draw(c);
//		
//		
//		getHolder().unlockCanvasAndPost(c);
		// TODO Auto-generated constructor stub

		Thread thread = new Thread() {
			public void run() {
				Canvas c;
				while (true) {
					c = null;
					try {
						c = getHolder().lockCanvas(null);

						synchronized (getHolder()) {
							onDraw(c);
						}

						sleep(5);
					} catch (InterruptedException ie) {
						// doNothing();
					} finally {
						// do this in a finally so that if an exception is thrown
						// during the above, we don't leave the Surface in an
						// inconsistent state
						if (c != null) {
							getHolder().unlockCanvasAndPost(c);
						}

					}
				}
			}
		};

	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.progressionroute_background),0,0,null);
	}

}
