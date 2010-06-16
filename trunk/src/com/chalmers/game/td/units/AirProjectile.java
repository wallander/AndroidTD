package com.chalmers.game.td.units;

import com.chalmers.game.td.R;

public class AirProjectile extends Projectile {
	
	public static float mExplosionTime = 0.0f;
	
	private int mImage =  R.drawable.snowball_small;

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
		getTarget().takeDamage(getDamage());
	}
	
	public int getProjImage(){
		
		return mImage;
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