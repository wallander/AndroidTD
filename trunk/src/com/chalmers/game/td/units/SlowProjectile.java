package com.chalmers.game.td.units;

import android.util.Log;

import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.R;

public class SlowProjectile extends Projectile {

	private double mSlowEffect; //amount of slow - percent of original speed

	public SlowProjectile(Mob pTarget, SlowTower pTower) {
		super(pTarget, pTower);	
		mSlowEffect = 1-(pTower.getSlow()/100.0);
	}

	/**
	 * Slows the target mob.
	 * TODO slow more when higher level
	 */
	public void inflictDmg() {
		getTarget().setHealth(getTarget().getHealth() - getDamage());
		//mTarget.setSlowed(200,(double)1/(double)(mTower.mLevel + 1));

		switch (getTarget().getType()) {
		
		case HEALTHY:		// if HEALTHY/BOSS = no slow
			break;
		default: getTarget().setSlowed(200,mSlowEffect);
			break;
		}
	}
	
	public int getProjImage(){
		
		return R.drawable.projslow;
	}
}