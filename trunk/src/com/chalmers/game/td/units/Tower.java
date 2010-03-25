package com.chalmers.game.td.units;

import android.graphics.Bitmap;
import java.util.List;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.R;

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
	protected int mLevel = 1;		// Tower level
	private int mCooldownLeft;	// Tower shoot delay
	private int mAttackSpeed;	// Tower constant shoot speed
	private TowerType mType;	// Tower type

	protected int mImage; //Har den protected för att kunna ändra från extended splashTower
	
	

	/**
     * Constructor called to create a tower
     * 
     * Currently hardcoded. TODO
     * 
     * @param 
	 */
    public Tower(int mX, int mY){
    	setCoordinates(new Coordinate(mX, mY));
    	setRange(100);
    	mAttackSpeed = 5;
    	setDamage(50);
    	setCost(50);
    	
    	setSize(2);
    	
    	setImage(mLevel);
    }

    // Temporary changes images up to 4 upgrades.
	public void setImage(int img) {
		if(img == 1){
			mImage = R.drawable.basictower;	
		} else if(img == 2){
			mImage = R.drawable.basictower2;
		} else if(img == 3){
			mImage = R.drawable.basictower3;
		} else {
			mImage = R.drawable.basictower4;
		}
		
	}

	public int getImage() {
		return mImage;
	}
	
	private void setCost(int i) {
		mCost = i;
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
    	
		// if the tower is not on cooldown
		if (mCooldownLeft == 0) {

			// loop through the list of mobs
			for (int i=0; i<mobs.size();i++) {
				Mob m = mobs.get(i);

				double sqrDist = Coordinate.getSqrDistance(this.getCoordinates(), m.getCoordinates());
    		
				// return a new Projectile on the first mob that the tower can reach
				if (sqrDist < mRange ){
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
     * Returns whether this tower is located at the given position (x,y)
     * 
     * @param x
     * @param y
     * @return
     */
    public boolean selectTower(double pXpos, double pYpos) {

    	return (getX() <= pXpos && pXpos < getX()+(getWidth()*GameModel.GAME_TILE_SIZE) &&
        		getY() <= pYpos && pYpos < getY()+(getHeight()*GameModel.GAME_TILE_SIZE) );
    	
    }
    
    
    
    
    /**
     * Upgrade tower to next level
     * TODO: increase damage/range according to level
     * currently damage is increased by 10 for each level
     * range is increased by 5 for each level
     */
    public boolean upgrade() {
    	mLevel++;
    	setImage(mLevel);
    	setDamage(getDamage()+10);
    	setRange(getRange()+5);
    	
    	return true;
    }
    
    private void setRange(int i) {
		mRange = i;
		
	}

	public int getRange() {
    	return mRange;
    }
    
    
    /**
     * returns amount of money you get when you sell this tower
     * 
     * @return
     */
    public int sell(){
    	
    	return (int)0.5*getCost() + (int)(getCost()*0.05*getLevel());
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

	public int getCost() {
		return mCost;
	}

	public int getLevel() {
		
		return mLevel;
	}
	
	public String getName() {
		return "Basic Tower";
	}

	/**
	 * Returns upgrade cost.
	 * Currently 50% of the tower cost, plus another 10% per tower level
	 * @return
	 */
	public int getUpgradeCost() {

		return (int)(getCost()*0.5) + (int)(getCost()*0.10*(getLevel() - 1));
	}
}