package com.chalmers.game.td.units;

import com.chalmers.game.td.R;

public class AirProjectile extends Projectile {
	
	public static float mExplosionTime = 0.3f;
	private Mob mTarget;
	//private int mImage =  R.drawable.snowball_small;

	public AirProjectile(Mob pTarget, AirTower pTower) {
		super(pTarget, pTower);
		mTarget = pTarget;
	}
	public AirProjectile(Mob pTarget, Tower pTower) {
		super(pTarget, pTower);
		mTarget = pTarget;
	}

	/**
	 * Slows the target mob.
	 * TODO slow more when higher level
	 */
	public void inflictDmg() {
		getTarget().takeDamage(getDamage());
		setX(mTarget.getX() +15);
		setY(mTarget.getY() +10);
	}
	
public int getProjImage(){
		
		if (mExploded) {

			if (mExplAnimation/mExplosionTime <= 0.25f)
				return R.drawable.nexpl1;
			else if (mExplAnimation/mExplosionTime <= 0.5f)
				return R.drawable.nexpl2;
			else if (mExplAnimation/mExplosionTime <= 0.75f)
				return R.drawable.nexpl3;
			else
				return R.drawable.nexpl4;

		} else 
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