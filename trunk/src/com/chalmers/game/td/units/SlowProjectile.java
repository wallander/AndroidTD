package com.chalmers.game.td.units;

import com.chalmers.game.td.GameModel;

public class SlowProjectile extends Projectile {

	public SlowProjectile(Mob pTarget, Tower pTower, GameModel pGameModel) {
		super(pTarget, pTower, pGameModel);
		
	}

	/**
	 * Slows the target mob.
	 * TODO slow more when higher level
	 */
	 public void inflictDmg() {
	       mTarget.setSlowed(200,(double)1/(double)(mTower.mLevel + 1));
	    }
	
}
