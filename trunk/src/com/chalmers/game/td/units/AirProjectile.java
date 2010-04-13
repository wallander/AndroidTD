package com.chalmers.game.td.units;

import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.R;

public class AirProjectile extends Projectile {

	public AirProjectile(Mob pTarget, AirTower pTower) {
		super(pTarget, pTower);
	}
	public AirProjectile(Mob pTarget, Tower pTower) {
		super(pTarget, pTower);
	}

	/**
	 * Slows the target mob.
	 * TODO slow more when higher level
	 */
	public void inflictDmg() {
		getTarget().setHealth(getTarget().getHealth() - getDamage());
	}
	
	public int getProjImage(){
		
		return R.drawable.snowball_small;
	}
	
}
