package com.chalmers.game.td.units;

import java.util.ArrayList;
import java.util.List;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GamePanel;
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
public class Tower extends Unit {

	private enum TowerType { GROUND, AIR, INVIS }

	protected int mDamage;		// Tower damage
	protected int mRange;			// Tower shoot range
	protected int mCost;			// Tower cost
	protected int mLevel = 1;		// Tower level
	protected int mCooldownLeft;	// Tower shoot delay
	protected int mAttackSpeed;	// Tower constant shoot speed

	protected String mName;

	private TowerType mType;	// Tower type

	protected int mImage; //Har den protected f�r att kunna �ndra fr�n extended splashTower
	

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

    	setAttackSpeed(20);

    	setName("Eskimo Tower");


    	setDamage(6);
    	setCost(70);
    	setSize(2);

    	setImage(mLevel);
    }
    
    public void setName(String pName) {
		mName = pName;
	}

	public int getmAttackSpeed() {
		return mAttackSpeed;
	}

	public void setAttackSpeed(int pAttackSpeed) {
		this.mAttackSpeed = pAttackSpeed;
	}
	
	public String getAttackSpeed(){
		return "" + 100/mAttackSpeed;
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
	

	public void setCost(int i) {
		mCost = i;
		// ta bort kostnad fr�n spelarens konto?
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

   // public Projectile tryToShoot(List<Mob> mobs){

    public List<Projectile> tryToShoot(GameModel pGameModel){
    	
		// if the tower is not on cooldown
		if (mCooldownLeft <= 0) {

			List<Projectile> projectiles = new ArrayList<Projectile>();
			
			// loop through the list of mobs
			for (int i=0; i<pGameModel.mMobs.size();i++) {
				Mob m = pGameModel.mMobs.get(i);

				double sqrDist = Coordinate.getSqrDistance(this.getCoordinates(), m.getCoordinates());
    		
				// return a new Projectile on the first mob that the tower can reach

			
				if (sqrDist < mRange){
					mCooldownLeft = mAttackSpeed;
					projectiles.add(new Projectile(m, this, pGameModel));
	    			return projectiles;
	    		}
	
			}
		} else { // if the tower is on cooldown
			mCooldownLeft -= GamePanel.getSpeedMultiplier();
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
        		getY() <= pYpos && pYpos < getY()+(getHeight()*GameModel.GAME_TILE_SIZE));
    	
    }
    
    /**
     * Upgrade tower to next level
     */
    public boolean upgrade() {

    	mLevel++;
    	setImage(mLevel);

    	if(mLevel == 2) {
    		setDamage(16);
    		setRange(110);
    		
    	} else if (mLevel == 3) {
    		setDamage(40);
    		setRange(125);
    	} else if (mLevel == 4) {
    		setDamage(140);
    		setRange(140);
    	} else {
    		mLevel--; //level 5 finns ej, stanna p� level 4 (fulkod?)
    	
    		return false;
    	}
    	return true;
	}
    
    public boolean canUpgrade() {
    	return (mLevel < 4);
    }
    
    protected void setRange(int i) {
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
    public double sell(){
    	
    	return 0.5*getCost() + (getCost()*0.05*getLevel());
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

	public void setDamage(int pDamage) {
		mDamage = pDamage;
	}

	public int getDamage() {
		return mDamage;
	}

	public double getCost() {
		return mCost;
	}

	public int getLevel() {
		
		return mLevel;
	}
	
	public String getName() {
		return mName;
	}

	/**
	 * Returns upgrade cost.
	 * @return Uppgraderingskostnaden
	 */

	public int getUpgradeCost() {

		switch(mLevel) {
		case 1: return 130;
		case 2: return 320;
		case 3: return 780;
		}
		return 0; 	//default, not gonna happen
	}

}