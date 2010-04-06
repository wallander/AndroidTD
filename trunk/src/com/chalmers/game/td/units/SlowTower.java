package com.chalmers.game.td.units;


import java.util.ArrayList;
import java.util.List;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GamePanel;

import com.chalmers.game.td.R;

public class SlowTower extends Tower {

	public SlowTower(int mX, int mY) {
		super(mX, mY);

		setImage(mLevel);
		super.setName("Slow Tower");
		
		// TODO Set appropriate values to range, damage, attack speed and such

	}

	
	public String getName() {
		return "Slowing Tower";
	}

	// Temporary changes images up to 4 upgrades.
	public void setImage(int img) {
		if(img == 1){
			mImage = R.drawable.slowtower;	
		} else if(img == 2){
			mImage = R.drawable.slowtower2;
		} else if(img == 3){
			mImage = R.drawable.slowtower3;
		} else {
			mImage = R.drawable.slowtower4;
		}
		
	}

    /**
     * Method that returns a Projectile set to target the first mob that isn't slowed
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
				if (sqrDist < mRange && m.isSlowed() == false ){
					mCooldownLeft = mAttackSpeed;
					projectiles.add(new SlowProjectile(m, this, pGameModel));
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
	

}
