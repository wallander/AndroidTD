package com.chalmers.game.td.units;

import java.util.ArrayList;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GamePanel;

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

	private int mDamage;		// Tower damage
	private int mRange;			// Tower shoot range
	private int mCost;			// Tower cost
	private int mLevel;			// Tower level
	private int mCoolDownLeft;	// Tower shoot delay
	private int mCoolDown;		// Tower constant shoot speed
	private String mDescription; // Tower description
	
	private String mName;
	public static final int BASIC=1, SPLASH=2, SLOW=3, AIR=4;
	private int mType;
	
	private int mImage;
	
	//List<Projectile> mProjectiles;
	/**
     * Constructor called to create a tower
     * 
     * Currently hardcoded. TODO
     * 
     * @param 
	 */
    public Tower(int pX, int pY){
    	setCoordinates(new Coordinate(pX, pY)); //gäller alla
    	setLevel(0);
    	upgrade();
    	setSize(2);				//gäller alla torn?
    	setImageByLevel(mLevel);	//gäller för alla torn
    	setDescription("");
    }
    
	public void setName(String pName) {
		mName = pName;
	}
	
	public String getName() {
		return mName;
	}

	public int getCoolDown() {
		return mCoolDown;
	}

	public void setCoolDown(int pCoolDown) {
		mCoolDown = pCoolDown;
	}
	
	public int getCoolDownLeft(){
		return mCoolDownLeft;
	}
	
	//true if there is cooldown left, else false
	public boolean isOnCoolDown(){
		return mCoolDownLeft > 0;
	}
	
	//called by GamePanel in UpdateModel() so that the cool down is decreased 
	// by the game speed multiplier every time the model is updated
	public void decCoolDownLeft(int pGameSpeed){
		mCoolDownLeft -=  pGameSpeed;
	}
	
	//public boolean isInRange()
	
	//called after the tower has shot, reset CD
	public void resetCoolDown(){
		mCoolDownLeft = mCoolDown;
	}
	
	//Attack speed is another way to express cool down, that is more intuitive to the user
	//higher value = better
	//TODO add this value to tower tool tip
	public int getAttackSpeed(){
		return 100/mCoolDown;
	}
	
	//Should be implemented so it takes the level of the tower as argument and sets the image 
	//to the drawable that corresponds to that level.
	public abstract void setImageByLevel(int pLevel);
	
    // Changes the tower images to the image specified by pImage.
	public void setImage(int pImage) {
		mImage = pImage;
	}
	//returns the integer mImage
	public int getImage() {
		return mImage;
	}
	
	public void setCost(int i) {
		mCost = i;
		// ta bort kostnad från spelarens konto?
	}
	
	public abstract int getUpgradeCost();


    /**
     * Method that returns a Projectile set to target the first mob
     * in the given list of mobs that the tower can reach.
     * 
     * @param mobs List of mobs for the tower to target
     * @return Projectile set to target the first mob the tower can reach.
     */
	
	public Projectile tryToShoot(){

		if (!isOnCoolDown()){
			Projectile p = shoot();
			if(p != null)		//reset the cooldown if the tower actually shoots
				resetCoolDown();
			return p;	//return the projectile regardless of if it is null or not	
		}else {
			decCoolDownLeft(GamePanel.getSpeedMultiplier());
			return null;
		}
	}

	public Projectile shoot() {

		ArrayList<Mob> mobsInRange = new ArrayList<Mob>();

		// loop through the list of mobs
		for (int i=0; i < GameModel.mMobs.size(); i++) {

			Mob m = GameModel.mMobs.get(i);

			double sqrDist = Coordinate.getSqrDistance(this.getCoordinates(), m.getCoordinates());

			// if the mob is in range, add it to list
			if (sqrDist < getRange())
				mobsInRange.add(m);
		}

		//if there are any mobs available to shoot, return a projectile on the first of them, 
		//else return null
		if (!mobsInRange.isEmpty())
			return createProjectile(firstMob(mobsInRange));
		else
			return null;
	}

	public Mob firstMob(ArrayList<Mob> pMobs) {
		Mob first = pMobs.get(0);

		//if only one mob, return it
		if (pMobs.size()==1)
			return first;

		//otherwise loop through to find and return first
		for (Mob m : pMobs){
			if (m.isBefore(first))
				first = m;
		}
		return first;
	}

    public abstract Projectile createProjectile(Mob pTarget);    
    
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
    
    public abstract boolean upgrade();
    
    public int getSlow() {
    	return 0;
    }
    
    public int getSplash() {
    	return 0;
    }
    
    public boolean canUpgrade() {
    	return (mLevel < 4);
    }
    
    public void setRange(int i) {
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
    
    public void setLevel(int pLevel) {
		mLevel = pLevel;
	}

	/**
	 * @param mDescription the mDescription to set
	 */
	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
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