package com.chalmers.game.td.units;


import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;

import com.chalmers.game.td.R;

/**
 * This class extends the Tower class. It shoots splashprojectiles instead of projectiles.
 * 
 *
 */
public class SplashTower extends Tower {

	public SplashTower(int mX, int mY) {
		super(mX, mY);

		setImage(mLevel);

		
		// TODO Set appropriate values to range, damage, attack speed and such
		mDamage = 50;
		mAttackSpeed = 30;
		mCost = 50;
		mRange = 80;
		
		

	}

	public String getName() {
		return "Splash Tower";
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
    public Projectile tryToShoot(GameModel pGameModel){
    	
    	
		// if the tower is not on cooldown
		if (mCooldownLeft == 0) {

			// loop through the list of mobs
			for (int i=0; i<pGameModel.mMobs.size();i++) {
				Mob m = pGameModel.mMobs.get(i);

				double sqrDist = Coordinate.getSqrDistance(this.getCoordinates(), m.getCoordinates());
    		
				// return a new Projectile on the first mob that the tower can reach
				if (sqrDist < mRange){
					mCooldownLeft = mAttackSpeed;
	    			return (new SplashProjectile(m, this, pGameModel));
	    		}
			}
		
		} else { // if the tower is on cooldown
			mCooldownLeft--;
			return null;
		}
		
		// if the tower is off cooldown, but has no target in range
		return null;
    }

}
