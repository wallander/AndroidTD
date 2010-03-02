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
	private int mlevel;			// Tower level
	private int mWait2SHoot;	// Tower shoot delay
	private int mAttackSpeed;	// Tower constant shoot speed
	private boolean mEnabled;	// Tower SKIT I SÅLÄNGE
	private TowerType mType;	// Tower type
	// private Coordinates mCoordinates; finns i Unit
	
	

	/**
     * Constructor called to create a tower
     * @param 
	 */
    public Tower(){
    	
    }
	
	/**
     * Constructor called to create a tower
     * @param 
	 */
    public Tower(TowerType pType, int pPosX, int pPosY){
    	mType = pType;
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
    }