package com.chalmers.game.td.units;

import android.graphics.Bitmap;
import java.util.List;

import com.chalmers.game.td.Coordinates;

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
	private boolean mEnabled;	// Tower SKIT I S�L�NGE
	private TowerType mType;	// Tower type
	// private Coordinates mCoordinates; finns i Unit
	
	

	/**
     * Constructor called to create a tower
     * @param 
	 */
    public Tower(){
    	setCoordinates(new Coordinates(130, 150));
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
    	setX(pPosX);
    	setY(pPosY);
    }
    
    
    
    public Projectile tryToShoot(List<Mob> mobs){
    	double tx = this.getX();
		double ty = this.getY();
	
		if (mCooldownLeft == 0) { // Om tornet inte �r p� cooldown

			for (int i=0; i<mobs.size();i++) {
				Mob m = mobs.get(i);
				double mx = m.getX();
				double my = m.getY();
    	
				double sqrDistance = (tx - mx)*(tx - mx) + (ty - my)*(ty - my);
    		
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