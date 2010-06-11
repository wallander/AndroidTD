package com.chalmers.game.td;

import android.graphics.Canvas;
import android.os.SystemClock;


/**
 * Thread class to perform the so called "game loop".
 * 
 * @author Fredrik Persson
 * @author Jonas Andersson
 * @author Ahmed Chaban
 * @author Jonas Wallander
 * @author Disa Faith
 * @author Daniel Arvidsson
 */
class GameThread extends Thread {
	private GameView mGamePanel;
	private boolean mRunThread = false;
	private long mLastTime;

	/**
	 * Constructor.
	 * 
	 * @param panel View class on which we trigger the drawing.
	 */
	public GameThread(GameView panel) {
		mGamePanel = panel;
		mLastTime = SystemClock.uptimeMillis();
			
	}

	/**
	 * @param run Should the game loop keep running? 
	 */
	public void setRunning(boolean run) {
		mRunThread = run;
	}

	/**
	 * @return If the game loop is running or not.
	 */
	public boolean isRunning() {
		return mRunThread;
	}

	/**
	 * Perform the game loop.
	 * Order of performing:
	 * 1. update game model
	 * 2. draw everything
	 */
	@Override
	public void run() {
		Canvas c = null;
		mLastTime = SystemClock.uptimeMillis();

		while (mRunThread) {

			// calculate time delta for updates
			final long time = SystemClock.uptimeMillis();
			final long timeDelta = time - mLastTime;
			long finalDelta = timeDelta;

			if (timeDelta > 12) {
				float secondsDelta = (time - mLastTime) * 0.001f;
				if (secondsDelta > 0.1f) {
					secondsDelta = 0.1f;
				}
				mLastTime = time;

				// Do everything that needs a time delta!
				GameModel.updateModel(secondsDelta);
			}
			
			final long endTime = SystemClock.uptimeMillis();
            finalDelta = endTime - time;
            
			try {
				c = mGamePanel.getHolder().lockCanvas(null);
				synchronized (mGamePanel.getHolder()) {

					mGamePanel.updateSounds();
					mGamePanel.onDraw(c);
				}
			} finally {
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface in an
				// inconsistent state
				if (c != null) {
					mGamePanel.getHolder().unlockCanvasAndPost(c);
				}
			}
            
            
            // If the game logic completed in less than 16ms, that means it's running
            // faster than 60fps, which is our target frame rate.  In that case we should
            // yield to the rendering thread, at least for the remaining frame.
           
            if (finalDelta < 16) {
                try {
                    Thread.sleep(16 - finalDelta);
                } catch (InterruptedException e) {
                    // Interruptions here are no big deal.
                }
            }
			
		}
	}
}