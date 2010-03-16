package com.chalmers.game.td.units;

import android.graphics.Bitmap;
import java.util.List;

import com.chalmers.game.td.Coordinate;

/**
 * Class which contains tower specific information
 * 
 * @author Fredrik Persson
 * @author Jonas Andersson
 * @author Ahmed Chaban
 * @author Jonas Wallander
 * @author Disa Faith
 * @author Daniel Arvidsson
 */
public class Tower extends Unit{

	private enum TowerType { GROUND, AIR, INVIS }

	private int mDamage;		// Tower damage
	private int mRange;			// Tower shoot range
	private int mCost;			// Tower cost
	private int mLevel;			// Tower level
	private int mCooldownLeft;	// Tower shoot delay
	private int mAttackSpeed;	// Tower constant shoot speed
	private boolean mEnabled;	// wat
	private TowerType mType;	// Tower type

	
	

	/**
     * Constructor called to create a tower
     * 
     * Currently hardcoded. TODO
     * 
     * @param 
	 */
    public Tower(int mX, int mY){
    	setCoordinates(new Coordinate(mX, mY));
    	mRange = 100;
    	mAttackSpeed = 5;
    	setDamage(50);
    	mEnabled = true;
    	setSize(32);
    }
	
	/**
     * Constructor called to create a tower
     * @param 
	 */
    public Tower(TowerType pType, int pPosX, int pPosY){
    	setType(pType);
    	setX(pPosX);
    	setY(pPosY);
    }
    
    
    /**
     * Method that returns a Projectile set to target the first mob
     * in the given list of mobs that the tower can reach.
     * 
     * @param mobs List of mobs for the tower to target
     * @return Projectile set to target the first mob the tower can reach.
     */
    public Projectile tryToShoot(List<Mob> mobs){
    	double tx = this.getX();
		double ty = this.getY();
	
		// if the tower is not on cooldown
		if (mCooldownLeft == 0) {

			// loop through the list of mobs
			for (int i=0; i<mobs.size();i++) {
				Mob m = mobs.get(i);
				double mx = m.getX();
				double my = m.getY();
    	
				
    		
				// return a new Projectile on the first mob that the tower can reach
				if ((tx - mx)*(tx - mx) + (ty - my)*(ty - my) < mRange * mRange ){
					mCooldownLeft = mAttackSpeed;
	    			return (new Projectile(m, this));
	    		}
			}
		
		} else { // if the tower is on cooldown
			mCooldownLeft--;
			return null;
		}
		
		// if the tower is off cooldown, but has no target in range
		return null;
    }
    
    /**
     * Upgrade tower to next level (NYI)
     */
    public void upgrade(){ //Could be boolean
    	
    }
    
    public int getRange(){
    	return mRange;
    }
    
    
    /**
     * Sell item, return money
     * 
     * @return
     */
    public int sell(){
    	
    	return 0;
    }
    

	/**
	 * @param mType the mType to set
	 */
	public void setType(TowerType mType) {
		this.mType = mType;
	}

	/**
	 * @return the mType
	 */
	public TowerType getType() {
		return mType;
	}

	public void setDamage(int mDamage) {
		this.mDamage = mDamage;
	}

	public int getDamage() {
		return mDamage;
	}
}