package com.chalmers.game.td.units;

import com.chalmers.game.td.R;

public class BasicProjectile extends Projectile {

	public static float mExplosionTime = 0.0f;
	
	public BasicProjectile(Mob pTarget, Tower pTower) {
		super(pTarget, pTower);
	}

	/**
	 * Slows the target mob.
	 * TODO slow more when higher level
	 */
	public void inflictDmg() {
		getTarget().takeDamage(getDamage());
	}
	
	public int getProjImage(){
		return R.drawable.snowball_small;
	}

	@Override
	protected void updateAnimation(float timeDelta) {
		// TODO Auto-generated method stub
		if (mExploded) {
			mExplAnimation += timeDelta;
		}
	}

	@Override
	public float getExplosionTime() {
		// TODO Auto-generated method stub
		return mExplosionTime;
	}
	
}
