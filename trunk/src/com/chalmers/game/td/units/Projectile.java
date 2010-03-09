package com.chalmers.game.td.units;

import android.graphics.Bitmap;
import android.util.Log;

import com.chalmers.game.td.Coordinates;

/**
 * Class which represents a projectile on the game board.
 * 
 * @author Jonas Andersson, Daniel Arvidsson, Ahmed Chaban, Disa Faith, Fredrik Persson, Jonas Wallander
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
    
	double x2;
	double y2;
	
	/**
     * Constructor.
     * 
     * @param bitmap Bitmap which should be drawn.
     */
    public Projectile(Mob pTarget, Tower pTower) {
    	setCoordinates(new Coordinates(pTower.getX(), pTower.getY()));

        setTarget(pTarget);
        setTower(pTower);
        setSpeed(2);
        setDamage(50);
        
		x2 = getTargetedMob().getX();
		y2 = getTargetedMob().getY();
        //setAngle(0);
        //updatePosition();
        
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
		if(getTargetedMob().getHealth() > 0){
			x2 = getTargetedMob().getX();
			y2 = getTargetedMob().getY();
		} 
		
		double x1 = getX();
		double y1 = getY();
		
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

	public boolean hasCollided() {
		// TODO Auto-generated method stub
		
		double tx = getX();
		double ty = getY();
	
		
    	double mx = x2;
    	double my = y2;
		
		double sqrDistance = (tx - mx)*(tx - mx) + (ty - my)*(ty - my);
		
		if (sqrDistance < 10)
			return true;
		
		return false;
	}



   
}