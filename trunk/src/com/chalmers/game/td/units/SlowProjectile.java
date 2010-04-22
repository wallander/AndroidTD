package com.chalmers.game.td.units;


import com.chalmers.game.td.R;

public class SlowProjectile extends Projectile {

	private double mSlowEffect; //amount of slow - percent of original speed
	
	private int mImage = R.drawable.projslow;

	public SlowProjectile(Mob pTarget, SlowTower pTower, double pSlow) {
		super(pTarget, pTower);	
		mSlowEffect = pSlow;
	}

	/**
	 * Slows the target mob.
	 * TODO slow more when higher level
	 */
	public void inflictDmg() {

		getTarget().takeDamage(getDamage());

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
		return mImage;
	}
}