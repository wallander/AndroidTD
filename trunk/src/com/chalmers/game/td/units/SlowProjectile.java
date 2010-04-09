package com.chalmers.game.td.units;

import android.util.Log;

import com.chalmers.game.td.GameModel;

public class SlowProjectile extends Projectile {
	
	private double mSlowEffect;

	public SlowProjectile(Mob pTarget, SlowTower pTower, GameModel pGameModel) {
		super(pTarget, pTower, pGameModel);	
		mSlowEffect = 1-(pTower.getSlow()/100.0);
		Log.v("JONAS",""+mSlowEffect);
	}
	public SlowProjectile(Mob pTarget, SlowTower pTower) {
		super(pTarget, pTower);	
		mSlowEffect = 1-(pTower.getSlow()/100.0);
	}
	/**
	 * Slows the target mob.
	 * TODO slow more when higher level
	 */
	 public void inflictDmg() {
		 mTarget.setHealth(mTarget.getHealth() - mDamage);
	     //mTarget.setSlowed(200,(double)1/(double)(mTower.mLevel + 1));
		 mTarget.setSlowed(200,mSlowEffect); 
	    }
	
}
