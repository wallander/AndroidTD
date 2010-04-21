package com.chalmers.game.td.units;

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
		//switch(getTarget().getType()) {
		//case AIR:
		getTarget().setHealth(getTarget().getHealth() - getDamage());
		//break;
		//default: getTarget().setHealth((int) (getTarget().getHealth() - getDamage()*0.7));
		//}
	}
	
	public int getProjImage(){
		
		return R.drawable.snowball_small;
	}
	
}
