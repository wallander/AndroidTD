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
        //setAngle(0);
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
		double x2 = getTargetedMob().mCoordinates.getX();
		double y2 = getTargetedMob().mCoordinates.getY();
		
		double x1 = mXPos;
		double y1 = mYPos;
		
		
		if (  x1 < x2 && y1 > y2  ) { // första kvadranten
			setAngle( Math.tan((y1-y2) / (x2-x1)));
			
		} else if (  x1 > x2 && y1 > y2  ) { // andra kvadranten
			setAngle( Math.tan( (x1-x2) / (y1-y2)  ) + 0.5*Math.PI);
			
		} else if (  x1 > x2 && y1 < y2  ) { // tredje kvadranten
			setAngle( Math.tan( (y2-y1) / (x1-x2) ) + Math.PI);
			
		} else if (  x1 < x2 && y1 < y2  ) { // fjärde kvadranten
			setAngle( Math.tan( (x2-x1) / (y2-y1)  ) + 1.5*Math.PI);

		}
		
		

		
		
		
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
		
		double tx = mCoordinates.getX();
		double ty = mCoordinates.getY();
	
		
    	double mx = mTarget.mCoordinates.getX();
    	double my = mTarget.mCoordinates.getY();
		
		double sqrDistance = (tx - mx)*(tx - mx) + (ty - my)*(ty - my);
		
		if (sqrDistance < 10)
			return true;
		
		return false;
	}



   
}