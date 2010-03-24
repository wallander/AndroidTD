package com.chalmers.game.td.units;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.R;

public class SlowTower extends Tower {

	public SlowTower(int mX, int mY) {
		super(mX, mY);
		setImage(R.drawable.big);
		
		// TODO Set appropriate values to range, damage, attack speed and such
	}

	
	public String getName() {
		return "Slowing Tower";
	}
	
    /**
     * Method that returns a Projectile set to target the first mob that isn't slowed
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
				if (sqrDist < mRange && m.isSlowed() == false ){
					mCooldownLeft = mAttackSpeed;
	    			return (new SlowProjectile(m, this, pGameModel));
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
