package com.chalmers.game.td.units;

import android.graphics.Bitmap;
import android.util.Log;

import com.chalmers.game.td.Coordinates;

/**
 * Class which represents a projectile on the game board.
 * 
 * @author Fredrik Persson
 * @author Jonas Andersson
 * @author Ahmed Chaban
 * @author Jonas Wallander
 * @author Disa Faith
 * @author Daniel Arvidsson
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
    	setCoordinates(new Coordinates(pTower.getX()+pTower.getWidth()/2, pTower.getY()+pTower.getHeight()/2));

        setTarget(pTarget);
        setTower(pTower);
        setSpeed(6);
        setDamage(pTower.getDamage());
    }
    
    private void setDamage(int i) {
		// TODO Auto-generated method stub
		mDamage = i;
	}

	private void setSpeed(int i) {
		// TODO Auto-generated method stub
		mSpeed = i;
	}

	private void setTower(Tower pTower) {
		// TODO Auto-generated method stub
		mTower = pTower;
	}

	private void setTarget(Mob pTarget) {
		// TODO Auto-generated method stub
		mTarget = pTarget;
	}

	public Tower getProjectileTower(){
    	return mTower;
    }
    
    public Mob getTargetedMob(){
    	return mTarget;
    }
    

    public int getSpeed() {
        return mSpeed;
    }
    
	public double getAngle() {
		return mAngle;
	}
	
	public void setAngle(double mAngle) {
		this.mAngle = mAngle;
	}
    

	/**
	 * Update the position of the projectile. Currently this makes the projectile 
	 * act like a homing missile.
	 * 
	 * To create other types of projectiles, create subclasses of this class 
	 * and override this method.
	 */
	public void updatePosition() {
		
		setAngle(Coordinates.getAngle(this.getCoordinates(), getTargetedMob().getCoordinates()));

		setX(getX() + (getSpeed() * Math.cos(getAngle()) ));
		setY(getY() - (getSpeed() * Math.sin(getAngle()) ));
		
		
	}
	
  
    /**
     * @return Damage done
     */
    public void inflictDmg() {
       mTarget.setHealth(mTarget.getHealth() - mDamage);

    }

    /**
     * Method used for collision detection
     * @return
     */
	public boolean hasCollided() {
		
		double tx = getX();
		double ty = getY();
	
		
    	double mx = getTargetedMob().getX();
    	double my = getTargetedMob().getY();
		
		double sqrDistance = (tx - mx)*(tx - mx) + (ty - my)*(ty - my);
		
		if (sqrDistance < 10)
			return true;
		
		return false;
	}

}