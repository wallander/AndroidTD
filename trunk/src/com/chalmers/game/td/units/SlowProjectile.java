package com.chalmers.game.td.units;

import com.chalmers.game.td.GameModel;

public class SlowProjectile extends Projectile {

	public SlowProjectile(Mob pTarget, Tower pTower, GameModel pGameModel) {
		super(pTarget, pTower, pGameModel);
		
	}

	/**
	 * Slows the target mob.
	 */
	 public void inflictDmg() {
	       mTarget.setSlowed(50);
	    }
	
}
