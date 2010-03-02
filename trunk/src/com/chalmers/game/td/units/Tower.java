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
    	mType = pType;
    	mCoordinates.setX(pPosX);
    	mCoordinates.setY(pPosY);
    }
    
    int tx;
    int ty;
    int mx;
    int my;
    public Projectile tryToShoot(List<Mob> mobs){
    	tx = mCoordinates.getX();
		ty = mCoordinates.getY();
	
		for (Mob m : mobs) {
    		mx = m.mCoordinates.getX();
    		my = m.mCoordinates.getY();
    	
    		int sqrDistance = (tx - mx)*(tx - mx) + (ty - my)*(ty - my);
    		if (sqrDistance < mRange * mRange ){
    			return (new Projectile(m, this));
    		}
		}
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
    }