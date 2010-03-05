package com.chalmers.game.td.units;

import android.graphics.Bitmap;
import java.util.List;

/**
 * Class which contains tower specific information
 * 
 * @author Jonas Andersson, Daniel Arvidsson, Ahmed Chaban, Disa Faith, Fredrik Persson, Jonas Wallander
 */
public class Tower extends Unit{

	private enum TowerType { GROUND, AIR, INVIS }

	private int mDamage;		// Tower damage
	private int mRange;			// Tower shoot range
	private int mCost;			// Tower cost
	private int mLevel;			// Tower level
	private int mCooldownLeft;	// Tower shoot delay
	private int mAttackSpeed;	// Tower constant shoot speed
	private boolean mEnabled;	// Tower SKIT I Sï¿½Lï¿½NGE
	private TowerType mType;	// Tower type
	// private Coordinates mCoordinates; finns i Unit
	
	

	/**
     * Constructor called to create a tower
     * @param 
	 */
    public Tower(){
    	mCoordinates = new Coordinates(130, 150);
    	mRange = 200;
    	mAttackSpeed = 20;
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
    	double tx = mCoordinates.getX();
		double ty = mCoordinates.getY();
	
		if (mCooldownLeft == 0) { // Om tornet inte ï¿½r pï¿½ cooldown

			for (int i=0; i<mobs.size();i++) {
				Mob m = mobs.get(i);
				double mx = m.mCoordinates.getX();
				double my = m.mCoordinates.getY();
    	
				double sqrDistance = (tx - mx)*(tx - mx) + (ty - my)*(ty - my);
    		
				// Skjut på den första moben i listan som är inom range
				if (sqrDistance < mRange * mRange ){
					mCooldownLeft = mAttackSpeed;
	    			return (new Projectile(m, this));
	    		}
    		
			}
		
		} else { // Om tornet ï¿½r pï¿½ cooldown
			mCooldownLeft--;
			return null;
		}
		
		// Om tornet ï¿½r av cooldown, och inte hittar nï¿½got att skjuta
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