package com.chalmers.game.td.units;


import java.util.ArrayList;
import java.util.List;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GamePanel;

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

	public SplashTower(int pX, int pY) {
		super(pX, pY);
		setName("Splash Eskimo");
		setDescription("Trows snowballs damaging multiple targets");
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
    		setLevel(getLevel()+1);			//increment tower level by one
    		setImageByLevel(getLevel());	//set image according to the new level
    		
    		setCoolDown(sCoolDown[getLevel()-1]);
			setDamage(sDamage[getLevel()-1]);
			setRange(sRange[getLevel()-1]);
			setSplash(sSplash[getLevel()-1]);
			setSplashRadius(sSplashRadius[getLevel()-1]);

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

    
	public int getUpgradeCost() {

		switch(getLevel()) {
		case 1: return 150;
		case 2: return 330;
		case 3: return 800;
		}
		return 0; 	//default, not gonna happen
	}


}
