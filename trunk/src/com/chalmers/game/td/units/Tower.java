package com.chalmers.game.td.units;

import android.graphics.Bitmap;
import java.util.List;

/**
 * Class which contains tower specific information
 */
public class Tower extends Unit{

	private enum TowerType { GROUND, AIR, INVIS }

	private int mDamage;		// Tower damage
	private int mRange;			// Tower shoot range
	private int mCost;			// Tower cost
	private int mLevel;			// Tower level
	private int mCooldownLeft;	// Tower shoot delay
	private int mAttackSpeed;	// Tower constant shoot speed
	private boolean mEnabled;	// Tower SKIT I S�L�NGE
	private TowerType mType;	// Tower type
	// private Coordinates mCoordinates; finns i Unit
	
	

	/**
     * Constructor called to create a tower
     * @param 
	 */
    public Tower(){
    	mCoordinates = new Coordinates(60, 300);
    	mRange = 200;
    	mAttackSpeed = 8;
    	mDamage = 10;
    	mEnabled = true;
    }
	
	/**
     * Constructor called to create a tower
     * @param 
	 */
    public Tower(TowerType pType, int pPosX, int pPosY){
    	setType(pType);
    	mCoordinates.setX(pPosX);
    	mCoordinates.setY(pPosY);
    }
    
    
    
    public Projectile tryToShoot(List<Mob> mobs){
    	int tx = mCoordinates.getX();
		int ty = mCoordinates.getY();
	
		if (mCooldownLeft == 0) { // Om tornet inte �r p� cooldown

			for (Mob m : mobs) {
				int mx = m.mCoordinates.getX();
				int my = m.mCoordinates.getY();
    	
				int sqrDistance = (tx - mx)*(tx - mx) + (ty - my)*(ty - my);
    		
				// Skjut p� den f�rsta moben i listan som �r inom range
				if (sqrDistance < mRange * mRange ){
					mCooldownLeft = mAttackSpeed;
	    			return (new Projectile(m, this));
	    		}
    		
			}
		
		} else { // Om tornet �r p� cooldown
			mCooldownLeft--;
			return null;
		}
		
		// Om tornet �r av cooldown, och inte hittar n�got att skjuta
		return null;
    }
    
    /**
     * Upgrade tower to next level
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
     * DONT KNOW!
     */
    public void update(){
    	
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
    }