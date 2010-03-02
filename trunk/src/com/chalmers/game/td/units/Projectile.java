package com.chalmers.game.td.units;

import android.graphics.Bitmap;
import android.util.Log;

import com.chalmers.game.td.units.Unit.Coordinates;

/**
 * Class which represents a Projectiles on the game board.
 * 
 */
public class Projectile extends Unit{

	/** Projectile movement speed */
	private int mSpeed;
	
	/** Projectile damage */
	private int mDamage;
	
	/** Projectile direction */
	private int mDriection;

	/** Projectile target */
	private Mob mTarget;
	
	/** Projectile tower */
	private Tower mTower;
	
	/** Projectile movement angle */
	private double mAngle;
    
	
	/**
     * Constructor.
     * 
     * @param bitmap Bitmap which should be drawn.
     */
    public Projectile(Mob pTarget, Tower pTower) {
    	mCoordinates = new Coordinates(pTower.mCoordinates.getX(), pTower.mCoordinates.getY());
        mTarget = pTarget;
        mTower = pTower;
        mSpeed = 2;
        mDamage = 50;
        setAngle(0);
        updatePosition();
        
    }
    
    public Tower getProjectileTower(){
    	return mTower;
    }
    
    public Mob getTargetedMob(){
    	return mTarget;
    }
    
    /**
     * @return The speed of the instance
     */
    public int getSpeed() {
        return mSpeed;
    }
    
	public double getAngle() {
		return mAngle;
	}
	
	public void setAngle(double mAngle) {
		this.mAngle = mAngle;
	}
    
	private double mXPos;
	private double mYPos;
	public void updatePosition() {
		int x2 = mTarget.mCoordinates.getX();
		int y2 = mTarget.mCoordinates.getY();
		
		int x1 = mCoordinates.getX();
		int y1 = mCoordinates.getY();
		
		
		
		/*double newAngle = Math.tan((targety - mCoordinates.getY()) / (targetx - mCoordinates.getX()));
		if(targety > mCoordinates.getY()){
			newAngle += Math.PI;
		}
		*/
		
		
		double newAngle = 0;
		if (  x1 < x2 && y1 > y2  ) { // första kvadranten
			newAngle = Math.tan((y1-y2) / (x2-x1));
			
			
		} else if (  x1 > x2 && y1 > y2  ) { // andra kvadranten
			newAngle = Math.tan( (x1-x2) / (y1-y2)  ) + 0.5*Math.PI;
			
			
		} else if (  x1 > x2 && y1 < y2  ) { // tredje kvadranten
			newAngle = Math.tan( (y2-y1) / (x1-x2) ) + Math.PI;
			
			
		} else if (  x1 < x2 && y1 < y2  ) { // fjärde kvadranten
			newAngle = Math.tan( (x2-x1) / (y2-y1)  ) + 1.5*Math.PI;
			
			
		}
		
		
		setAngle(newAngle);
		
		
		
		if (mXPos == 0.0 || mYPos == 0.0) {
			mXPos = mCoordinates.getX();
			mYPos = mCoordinates.getY();
		}
		
		mXPos += getSpeed() * Math.cos(getAngle());
		mYPos -= getSpeed() * Math.sin(getAngle());
		
		mCoordinates.setX((int)mXPos);
		mCoordinates.setY((int)mYPos);
	}
	
  
    /**
     * @return Damage done
     */
    public void inflictDmg() {
       mTarget.setHealth(mTarget.getHealth() - mDamage);

    }

	public boolean hasCollided() {
		// TODO Auto-generated method stub
		
		int tx = mCoordinates.getX();
		int ty = mCoordinates.getY();
	
		
    	int mx = mTarget.mCoordinates.getX();
    	int my = mTarget.mCoordinates.getY();
		
		int sqrDistance = (tx - mx)*(tx - mx) + (ty - my)*(ty - my);
		
		if (sqrDistance < 10)
			return true;
		
		return false;
	}



   
}