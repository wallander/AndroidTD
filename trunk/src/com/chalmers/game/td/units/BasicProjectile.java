package com.chalmers.game.td.units;

import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.R;

public class BasicProjectile extends Projectile {

	public BasicProjectile(Mob pTarget, Tower pTower) {
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
