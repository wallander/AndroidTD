package com.chalmers.game.td.units;

import android.graphics.Bitmap;

/**
 * Class which contains tower specific information
 */
public class Tower extends Unit{

	private enum TowerType { GROUND, AIR, INVIS }

	private int mDamage;		// Tower damage
	private int mRange;			// Tower shoot range
	private int mCost;			// Tower cost
	private int mLevel;			// Tower level
	private int mWait2SHoot;	// Tower shoot delay
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
    	mRange = 40;
    	mAttackSpeed = 5;
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
    
    /**
     * Upgrade tower to next level
     */
    public void upgrade(){ //Could be boolean
    	
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