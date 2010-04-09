package com.chalmers.game.td.units;

import com.chalmers.game.td.GameModel;

public class BasicProjectile extends Projectile {

	public BasicProjectile(Mob pTarget, Tower pTower, GameModel pGameModel) {
		super(pTarget, pTower, pGameModel);
	}
	public BasicProjectile(Mob pTarget, Tower pTower) {
		super(pTarget, pTower);
	}

	/**
	 * Slows the target mob.
	 * TODO slow more when higher level
	 */
	public void inflictDmg() {
		mTarget.setHealth(mTarget.getHealth() - mDamage);
	}
	
}
