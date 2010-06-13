package com.chalmers.game.td.units;

import java.util.ArrayList;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GameView;

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
public abstract class Tower extends Unit {

	/** Tower damage.*/
	private int mDamage;
	
	/** Tower shoot range.*/
	private int mRange;
	
	/** The cost to buy the tower.*/
	private int mCost;
	
	/** The tower's current level.*/
	private int mLevel=0;
	
	/** The time between each shot.*/
	private float mCoolDown;		// Tower constant shoot speed
	
	/** The time left until next shot is fired.*/
	private float mCoolDownLeft;	// Tower shoot delay
	
	/** Text description of the tower. Will be displayed in the tower´s tooltip.*/
	private String mDescription; // Tower description

	/** The tower name. Will be displayed in the tower´s tooltip.*/
	private String mName;
	
	/** Constants representing different tower types.*/
	public static final int BASIC=1, SPLASH=2, SLOW=3, AIR=4;
	
	/** The tower type stored as one of the constants BASIC, SPLASH, SLOW, AIR.*/
	private int mType;
	
	/** The current image for the tower.*/
	private int mImage;

	private Projectile mNextProjectile;
	
	/**
     * Constructor called when a tower is created.
     * 
     * @param pX X-coordinate where the created tower should be placed.
     * @param pY Y-coordinate where the created tower should be placed.
     * 
	 */
    public Tower(int pX, int pY){
    	setCoordinates(new Coordinate(pX, pY));
    	upgrade();
    	setSize(2);
    	setImageByLevel(mLevel);
    }
    
	public void setName(String pName) {
		mName = pName;
	}
	
	public String getName() {
		return mName;
	}

	public float getCoolDown() {
		return mCoolDown;
	}

	public void setCoolDown(float pCoolDown) {
		mCoolDown = pCoolDown;
	}
	
	public float getCoolDownLeft(){
		return mCoolDownLeft;
	}
	
	//true if there is cooldown left, else false
	public boolean isOnCoolDown(){
		return mCoolDownLeft > 0;
	}
	
	/** 
	 * Decreases the time left until next shot is fired by the tower.
	 * Will be called each time that the game model is updated unless the tower shoots.
	 * 
	 * @param pSpeed The current speed multiplier of the game.
	 */
	public void decCoolDownLeft(float pSpeed){
		mCoolDownLeft -=  pSpeed*1.18;
	}
	
	/** 
	 * Resets the time remaining until next shot. 
	 * Called when the tower has fired a shot. 
	 */
	public void resetCoolDown(){
		mCoolDownLeft = mCoolDown;
	}
	
	
	/** Attack speed is another way to express cooldown, that is more intuitive to the user.
	 * A higher attack speed means that the tower is faster.
	 */
	public float getAttackSpeed(){
		return mCoolDown;
	}
	
	//Should be implemented so it takes the level of the tower as argument and sets the image 
	//to the drawable that corresponds to that level.
	
	/** 
	 * Sets the image corresponding to the tower's level.
	 * @param pLevel The level of the tower
	 */
	public abstract void setImageByLevel(int pLevel);
	
	/** 
	 * Sets the image of the tower to a given image.
	 * @param pImage The image to use for the tower.
	 */
	public void setImage(int pImage) {
		mImage = pImage;
	}
	
	public int getImage() {
		return mImage;
	}
	
	public void setCost(int i) {
		mCost = i;
	}
	
	public abstract int getUpgradeCost();

    /**
     * The tower tries to shoot at something. 
     * If a shot is fired the tower's cooldown will be reset. If the tower is on cooldown the 
     * cooldown that is left until next shot will be decreased appropriately.
     * @param timeDelta 
     * 
     * @return Projectile A projectile specific for the tower type, targeting the first valid
     * mob within the tower's range. It will be null if the tower is on cooldown, or if
     * no mobs (that the tower is allowed to shoot at) are in range of the tower.
     */
	public void tryToShoot(float timeDelta){
		mNextProjectile = null;

		if (isOnCoolDown() == false){
			mNextProjectile = shoot(); 		//can be null (if there are no valid mobs to shoot at)
			if(mNextProjectile != null)		//reset the cooldown if the tower actually shoots
				resetCoolDown();
		} else
			decCoolDownLeft(timeDelta*GameModel.getSpeedMultiplier());

	}

	public void update(float timeDelta) {
		this.tryToShoot(timeDelta);
	}
	
	public Projectile getNextProjectile() {
		return mNextProjectile;
	}
	
	/**
	 * Creates a projectile that targets a mob that is in the tower's range, and is a valid target of the
	 * tower. If no such mob exists returns null. Called from tryToShoot if the tower is off cooldown.
	 */
	public abstract Projectile shoot();

	public Mob firstMob(ArrayList<Mob> pMobs) {
		Mob first = pMobs.get(0);
		
		//if only one mob, return it no need to change
		if (pMobs.size()>1){
			//otherwise loop through to find and return first
			for (Mob m : pMobs){
				if (m.isBefore(first))
					first = m;
			}
		}
		
		return first;
	}

    public abstract Projectile createProjectile(Mob pTarget);    
    
    /**
     * Returns whether this tower is located at the given position (x,y)
     * 
     * @param x The x-coordinate for the tower's position.
     * @param y The y-coordinate for the tower's position.
     * 
     * @return Returns true if the tower occupies the given coordinate.
     */
    public boolean selectTower(double pXpos, double pYpos) {
    	
    	return (getX() <= pXpos && pXpos < getX()+(getWidth()*GameModel.GAME_TILE_SIZE) &&
    			getY() <= pYpos && pYpos < getY()+(getHeight()*GameModel.GAME_TILE_SIZE));	
    }
    
    /**
     * Upgrade tower to next level. Upgrades the stats accordingly.
     */
    public abstract boolean upgrade();
    
    /**
     * Dummy method for tower types that do not have slow. Should never be called.
     * 
     * @return Always returns 0
     */
    public int getSlow() {
    	return 0;
    }

    /**
     * Dummy method for tower types that do not have splash. Should never be called.
     * 
     * @return Always returns 0
     */
    public int getSplashRadius(){
    	return 0;
    }
    /**
     * If the tower can be upgraded. 
     * 
     * @return Returns true if the tower is not max level, else false.
     */
    public boolean canUpgrade() {
    	return (mLevel < 4);
    }
    
    /**
     * 
     * 
     * @param pRange
     */
    public void setRange(int pRange) {
		mRange = pRange;
	}

	public int getRange() {
    	return mRange;
    }
      
    /**
     * Returns amount of money you get when you sell this tower.
     * 
     * @return The sell price for the tower.
     */
    public int sellPrice(){
    	return (int)(0.5*getCost() + (getCost()*0.05*getLevel()));
    }

	public void setDamage(int pDamage) {
		mDamage = pDamage;
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
	
	/**
	 * Increments the tower level by one.
	 * 
	 * @return True if the level was successfully incremented, else false.
	 */
	public boolean incLevel(){
		if (canUpgrade())
			mLevel++;
		return (canUpgrade());
	}

	/**
	 * @param pDescription A short description of the tower type.
	 */
	public void setDescription(String pDescription) {
		mDescription = pDescription;
	}

	/**
	 * @return the mDescription
	 */
	public String getDescription() {
		return mDescription;
	}

	/**
	 * @param mType the TowerType to set
	 */
	public void setType(int pType) {
		mType = pType;
	}

	/**
	 * @return the TowerType
	 */
	public int getType() {
		return mType;
	}
}