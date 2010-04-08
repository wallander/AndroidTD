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

	private int mSplash;
	
	public SplashTower(int mX, int mY) {
		super(mX, mY);

		setImage(mLevel);
		
		// TODO Set appropriate values to range, damage, attack speed and such
		mDamage = 6;
		mAttackSpeed = 40;
		mCost = 100;
		mRange = 70;
		mSplash = 3;
		//mSplashEffect = 3; //Splash effect
		setName("Splash Tower");
	}

	
	
	// Temporary changes images up to 4 upgrades.
	public void setImage(int img) {
		if(img == 1){
			mImage = R.drawable.splashtower;	
		} else if(img == 2){
			mImage = R.drawable.splashtower2;
		} else if(img == 3){
			mImage = R.drawable.splashtower3;
		} else {
			mImage = R.drawable.splashtower4;
		}
		
	}

    /**
     * Method that returns a Projectile set to target the first mob
     * in the given list of mobs that the tower can reach.
     * 
     * @param mobs List of mobs for the tower to target
     * @return Projectile set to target the first mob the tower can reach.
     */
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
					projectiles.add(new SplashProjectile(m, this, pGameModel));
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
     * Sets the splash effect (int 1-5) for the tower
     * @param mSplash Splasheffect
     */
    
    public void setSplash(int mSplash) {
    	this.mSplash = mSplash;
    }
    
    
    /**
     * Returns the splasheffect
     * @return Splasheffect
     */
    public int getSplash() {
    	return mSplash;
    }
    
    
    /**
     * Upgrade tower to next level
     */
    public boolean upgrade() {

    	mLevel++;
    	setImage(mLevel);

    	if(mLevel == 2) {
    		setDamage(24);
    		setRange(70);
    		
    	} else if (mLevel == 3) {
    		setDamage(50);
    		setRange(70);
    		setAttackSpeed(30);
    	} else if (mLevel == 4) {
    		setDamage(100);
    		setRange(70);
    		setSplash(4);
    	} else {
    		mLevel--; //level 5 finns ej, stanna på level 4 (fulkod?)
    	
    		return false;
    	}
    	return true;
	}

    
	public int getUpgradeCost() {

		switch(mLevel) {
		case 1: return 150;
		case 2: return 330;
		case 3: return 800;
		}
		return 0; 	//default, not gonna happen
	}
}
