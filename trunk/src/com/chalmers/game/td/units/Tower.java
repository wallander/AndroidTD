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
public abstract class Tower extends Unit {

	private int mDamage;		// Tower damage
	private int mRange;			// Tower shoot range
	private int mCost;			// Tower cost
	private int mLevel;			// Tower level
	private int mCoolDownLeft;	// Tower shoot delay
	private int mCoolDown;		// Tower constant shoot speed

	private String mName;

	private int mImage; //Har den protected för att kunna ändra från extended splashTower
	
	List<Projectile> mProjectiles;
	/**
     * Constructor called to create a tower
     * 
     * Currently hardcoded. TODO
     * 
     * @param 
	 */
    public Tower(int pX, int pY){
    	setCoordinates(new Coordinate(pX, pY)); //gäller alla
    	setRange(100);			//default
    	setCoolDown(20);		//default
    	resetCoolDown();		//sätter CD Left till CD
    	setLevel(1);			//gäller alla
    	setDamage(6);			//default
    	setCost(70);			//default, may be overwritten by towers' constructor
    	setSize(2);				//gäller alla torn?
    	setImageByLevel(mLevel);	//gäller för alla torn
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


    /**
     * Method that returns a Projectile set to target the first mob
     * in the given list of mobs that the tower can reach.
     * 
     * @param mobs List of mobs for the tower to target
     * @return Projectile set to target the first mob the tower can reach.
     */

    public Projectile tryToShoot(GameModel pGameModel){
    	
		// if the tower is not on cooldown
		if (!isOnCoolDown()) {
			mProjectiles = new ArrayList<Projectile>();
			
			// loop through the list of mobs
			for (int i=0; i < pGameModel.mMobs.size(); i++) {
				
				Mob m = pGameModel.mMobs.get(i);

				double sqrDist = Coordinate.getSqrDistance(this.getCoordinates(), m.getCoordinates());
    		
				// return a new Projectile on the first mob that the tower can reach
				if (sqrDist < getRange()){
					resetCoolDown();
					return (createProjectile(m));
	    		}
			}
		
		} else { // if the tower is on cooldown
			return null;
		}
		
		// if the tower is off cooldown, but has no target in range
		return null;
    }
    
    public abstract Projectile createProjectile(Mob pTarget);
   // public Projectile tryToShoot(List<Mob> mobs){

//    public List<Projectile> tryToShoot(GameModel pGameModel){
//    	
//		// if the tower is not on cooldown
//		if (mCoolDownLeft <= 0) {
//
//			List<Projectile> projectiles = new ArrayList<Projectile>();
//			
//			// loop through the list of mobs
//			for (int i=0; i<pGameModel.mMobs.size();i++) {
//				Mob m = pGameModel.mMobs.get(i);
//
//				double sqrDist = Coordinate.getSqrDistance(this.getCoordinates(), m.getCoordinates());
//    		
//				// return a new Projectile on the first mob that the tower can reach
//			
//				if (sqrDist < mRange){
//					mCoolDownLeft = mCoolDown;
//					projectiles.add(new Projectile(m, this, pGameModel));
//	    			return projectiles;
//	    		}
//	
//			}
//		} else { // if the tower is on cooldown
//			mCoolDownLeft -= GamePanel.getSpeedMultiplier();
//			return null;
//		}
//		
//		// if the tower is off cooldown, but has no target in range
//		return null;
//    }
    
    
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
	 * Returns upgrade cost.
	 * @return Uppgraderingskostnaden
	 */

    public int getUpgradeCost() {

    	switch(mLevel) {
	    	case 1: return 130;
	    	case 2: return 320;
	    	default: return 780; //case 3 that is
    	}	
    }

}