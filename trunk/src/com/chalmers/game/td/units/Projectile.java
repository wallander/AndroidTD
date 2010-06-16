package com.chalmers.game.td.units;

import android.util.Log;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GameView;
import com.chalmers.game.td.R;

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
public abstract class Projectile extends Unit{

	/** Projectile movement speed */
	private float mSpeed;
	
	
	/** Projectile type emun */
	//public enum ProjectileType { NORMAL, SLOW, SPLASH }
	
	/** Projectile damage */
	private int mDamage;

	/** Projectile target */
	private Mob mTarget;
	
	/** Projectile tower */
	protected Tower mTower;
	
	/** Projectile movement angle */
	protected double mAngle;
	
	public float mExplAnimation = 0f;
	
	public boolean mExploded = false;
	
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
        setSpeed(100);
        setDamage(mTower.getDamage());
        
        // Jonas försökte göra så misilerna inte blev målsökande
        // Denna koden är bortkommenterad men testa om du vill
        // Kommentera bort viss kod i updatePosition isf också
        // I denna variant beräknas vart proj och mob kommer mötas och så skickas
        // proj ut i blindo. Formeln verkar dock göra så projektilen missar ofta då proj
        // inte är snabb nog
        
        //Coordinate targetCoordinate = new Coordinate(getMob().getX() + getMob().getWidth()/2, getMob().getY()  + getMob().getHeight()/2);
		//double a2 = Coordinate.getAngle(this.getCoordinates(), targetCoordinate);
		//setAngle(a2 - Math.asin(getMob().getSpeed()/getSpeed()*Math.sin(Math.PI - a2 + getMob().getAngle())));
    }
    
    public double getAngle() {
		return mAngle;
	}
    
    public int getDamage(){
    	return mDamage;
    }

	public Mob getTarget(){
    	return mTarget;
    }

	public float getSpeed() {
        return mSpeed;
    }

	public Tower getTower(){
    	return mTower;
    }

	/**
     * Method used for collision detection
	 * @param timeDelta 
     * @return
     */
	public boolean hasCollided(float timeDelta) {
		
		Coordinate targetCoordinate = new Coordinate(mTarget.getX() + mTarget.getWidth()/2, 
				mTarget.getY() + mTarget.getHeight()/2);

		double sqrDist = Coordinate.getDistance(getCoordinates(), targetCoordinate);
		
		Log.w("Collide=",sqrDist +"<"+ 4 +"*" + GameModel.getSpeedMultiplier() +"*"+getSpeed() +"*"+timeDelta +"="+ 4*GameModel.getSpeedMultiplier()*getSpeed()*timeDelta);
		//return true if the projectile has collided, else return false
		if (sqrDist < 4*GameModel.getSpeedMultiplier()*getSpeed()*timeDelta) {
			return true;
		}
		return false;	
	}
    
    /**
     * 
     */
    public void inflictDmg() {
       mTarget.setHealth(mTarget.getHealth() - mDamage);
    }
    

    public void setAngle(double pAngle) {
		mAngle = pAngle;
	}
    
	private void setDamage(int i) {
		mDamage = i;
	}
	
	private void setSpeed(float i) {
		mSpeed = i;
	}
    
	private void setTarget(Mob pTarget) {
		mTarget = pTarget;
	}
  
    private void setTower(Tower pTower) {
		mTower = pTower;
	}
    
	public abstract int getProjImage();
    
    /**
	 * Update the position of the projectile. Currently this makes the projectile 
	 * act like a homing missile.
	 * 
	 * To create other types of projectiles, create subclasses of this class 
	 * and override this method.
     * @param timeDelta 
	 */
	private void updatePosition(float timeDelta) {
		
		// Kommentera bort 2 rader ner om du ska testa min variant. Se kommentar i konstruktorn. / Jonas
		Coordinate targetCoordinate = new Coordinate(mTarget.getX() + mTarget.getWidth()/2, 
				mTarget.getY() + mTarget.getHeight()/2);
		setAngle(Coordinate.getAngle(this.getCoordinates(), targetCoordinate));

		setX(getX() + 2*timeDelta*GameModel.getSpeedMultiplier()*(getSpeed() * Math.cos(getAngle())) );
		setY(getY() - 2*timeDelta*GameModel.getSpeedMultiplier()*(getSpeed() * Math.sin(getAngle())) );
		
	}

	public void update(float timeDelta) {
		if(mExploded == false)
			this.updatePosition(timeDelta);
		
		this.updateAnimation(timeDelta);
		
	}

	protected abstract void updateAnimation(float timeDelta);

	public abstract float getExplosionTime();


}