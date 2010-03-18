package com.chalmers.game.td.units;

import android.graphics.Bitmap;
import android.util.Log;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;

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
    	setCoordinates(new Coordinate(
    			pTower.getX() + (pTower.getWidth() * GameModel.GAME_TILE_SIZE / 2),
    			pTower.getY() - 16 + (pTower.getHeight() * GameModel.GAME_TILE_SIZE / 2)));

        setTarget(pTarget);
        setTower(pTower);
        setSpeed(6);
        setDamage(pTower.getDamage());
    }
    
    public double getAngle() {
		return mAngle;
	}

	public Mob getMob(){
    	return mTarget;
    }

	public int getSpeed() {
        return mSpeed;
    }

	public Tower getTower(){
    	return mTower;
    }

	/**
     * Method used for collision detection
     * @return
     */
	public boolean hasCollided() {
		
		Coordinate targetCoordinate = new Coordinate(getMob().getX() + getMob().getWidth()/2, getMob().getY()  + getMob().getHeight()/2);
		
		
		double sqrDist = Coordinate.getSqrDistance(getCoordinates(), targetCoordinate);
		
		if (sqrDist < getSpeed())
			return true;
		
		return false;
	}
    
    /**
     * @return Damage done
     */
    public void inflictDmg() {
       mTarget.setHealth(mTarget.getHealth() - mDamage);

    }
    

    public void setAngle(double mAngle) {
		this.mAngle = mAngle;
	}
    
	private void setDamage(int i) {
		// TODO Auto-generated method stub
		mDamage = i;
	}
	
	private void setSpeed(int i) {
		// TODO Auto-generated method stub
		mSpeed = i;
	}
    

	private void setTarget(Mob pTarget) {
		// TODO Auto-generated method stub
		mTarget = pTarget;
	}
	
  
    private void setTower(Tower pTower) {
		// TODO Auto-generated method stub
		mTower = pTower;
	}
    
    /**
	 * Update the position of the projectile. Currently this makes the projectile 
	 * act like a homing missile.
	 * 
	 * To create other types of projectiles, create subclasses of this class 
	 * and override this method.
	 */
	public void updatePosition() {
		
		
		Coordinate targetCoordinate = new Coordinate(getMob().getX() + getMob().getWidth()/2, getMob().getY()  + getMob().getHeight()/2);
		
		setAngle(Coordinate.getAngle(this.getCoordinates(), targetCoordinate));

		setX(getX() + (getSpeed() * Math.cos(getAngle()) ));
		setY(getY() - (getSpeed() * Math.sin(getAngle()) ));
	}

}