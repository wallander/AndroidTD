package com.chalmers.game.td.units;


import com.chalmers.game.td.R;

/**
 * This class extends the Tower class. It shoots splashprojectiles instead of projectiles.
 * 
 *
 */
public class SplashTower extends Tower {
	
	//
	private int mSplash;
	private int mSplashRadius;

	public static final int[] sCoolDown = new int[]{50,50,50,50};
	public static final int[] sDamage = new int[]{6,24,50,80};
	public static final int[] sRange = new int[]{60,70,70,80};
	public static final int[] sSplash = new int[]{2,3,4,5};
	public static final int[] sSplashRadius = new int[]{50,60,80,90};
	
	public static final int[] sUpgradeCost = new int[]{150,330,800};

	public SplashTower(int pX, int pY) {
		super(pX, pY);
		setName("Splash Eskimo");
		setDescription("Throws snowballs damaging multiple targets");
		resetCoolDown();
		setCost(100);

	}
	
	
	// Temporary changes images up to 4 upgrades.
	public void setImageByLevel(int pLevel) {

		switch (pLevel) {
			case 1: setImage(R.drawable.eskimotowersplash); break;
			case 2: setImage(R.drawable.eskimotowersplash2); break;
			case 3: setImage(R.drawable.eskimotowersplash3); break;
			case 4: setImage(R.drawable.eskimotowersplash4); break;
		}		
	}

    /**
     * Method that returns a Projectile set to target the first mob
     * in the given list of mobs that the tower can reach.
     * 
     * @param mobs List of mobs for the tower to target
     * @return Projectile set to target the first mob the tower can reach.
     */
    
    public Projectile createProjectile(Mob pTarget) {
    	switch(pTarget.getType()) {
    		case AIR: return null;
    		default: return new SplashProjectile(pTarget, this);
    	}
    }

	public boolean upgrade() {
    	//TODO change values
    	if (!canUpgrade())					//return false if tower can't be upgraded
    		return false;
    	else {
    		int newLvl = getLevel()+1;
    		
    		setLevel(newLvl);			//increment tower level by one
    		setImageByLevel(newLvl);	//set image according to the new level
    		
    		setCoolDown(sCoolDown[newLvl-1]);
			setDamage(sDamage[newLvl-1]);
			setRange(sRange[newLvl-1]);
			setSplash(sSplash[newLvl-1]);
			setSplashRadius(sSplashRadius[newLvl-1]);

	    	return true;
    	}
	}		
    
    /**
     * Sets the splash effect (int 1-5) for the tower
     * @param mSplash Splasheffect
     */
    
    public void setSplash(int pSplash) {
    	mSplash = pSplash;
    }
    
    public void setSplashRadius(int mRadius) {
    	mSplashRadius = mRadius;
    }
    
    public int getSplashRadius() {
    	return mSplashRadius;
    }
    
    /**
     * Returns the splasheffect
     * @return Splasheffect
     */
    public int getSplash() {
    	return mSplash;
    }

    //returns the cost to level from current level to current level +1
	public int getUpgradeCost() {
		return sUpgradeCost[getLevel()-1];
	}

}
