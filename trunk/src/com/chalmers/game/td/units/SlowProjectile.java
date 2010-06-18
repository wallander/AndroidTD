package com.chalmers.game.td.units;


import android.util.Log;

import com.chalmers.game.td.R;

public class SlowProjectile extends Projectile {

	public static float mExplosionTime = 0.4f;
	private Mob mTarget;

	
	private double mSlowEffect; //amount of slow - percent of original speed
	
//	private int mImage = R.drawable.projslow;

	public SlowProjectile(Mob pTarget, SlowTower pTower, double pSlow) {
		super(pTarget, pTower);	
		mSlowEffect = pSlow;
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
		switch (getTarget().getType()) {
		case Mob.HEALTHY:		// if HEALTHY/BOSS = less slow
			getTarget().setSlowed(50,1-(1-mSlowEffect)/3);
			break;
		case Mob.IMMUNE:
			break;
		default: getTarget().setSlowed(200,mSlowEffect);
		break;
		}
	}
	
	public int getProjImage(){
		
		if (mExploded) {
			Log.v("Exp","Success");
			if (mExplAnimation/mExplosionTime <= 0.25f)
				return R.drawable.sexpl1;
			else if (mExplAnimation/mExplosionTime <= 0.5f)
				return R.drawable.sexpl2;
			else if (mExplAnimation/mExplosionTime <= 0.75f)
				return R.drawable.sexpl3;
			else
				return R.drawable.sexpl3;

		} else 
			return R.drawable.projslow;
		
			
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