package com.chalmers.game.td.units;

import android.graphics.Bitmap;
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
        mTarget = pTarget;
        mTower = pTower;
    }
    
    public Tower getPrjectileTower(){
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
    
	public void updatePosition() {
		
		mCoordinates.setX(  (int) (mCoordinates.getX() + getSpeed() * Math.cos(getAngle())));
		mCoordinates.setY(  (int) (mCoordinates.getY() + getSpeed() * Math.sin(getAngle())));
		
	}
	
  
    /**
     * @return Damage done
     */
    public int inflictDmg() {
       
    	return 1;
    }



   
}