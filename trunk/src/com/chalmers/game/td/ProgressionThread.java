package com.chalmers.game.td;

import android.graphics.Canvas;


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
class ProgressionThread extends Thread {
    private ProgressionRouteView mProgressionPanel;
    private boolean mRunThread = false;
    
    /**
     * Constructor.
     * 
     * @param panel View class on which we trigger the drawing.
     */
    public ProgressionThread(ProgressionRouteView panel) {
        mProgressionPanel = panel;
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
        Canvas c;
        while (mRunThread) {
            c = null;
            try {
                
                
               // synchronized (mProgressionPanel.getHolder()) {
                	c = mProgressionPanel.getHolder().lockCanvas(null);
                    mProgressionPanel.onDraw(c);
                    mProgressionPanel.updateSound();
                //}
                
                sleep(5);
            } catch (InterruptedException ie) {
            	// doNothing();
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) {
                    mProgressionPanel.getHolder().unlockCanvasAndPost(c);
                }
              
            }
        }
    }
}