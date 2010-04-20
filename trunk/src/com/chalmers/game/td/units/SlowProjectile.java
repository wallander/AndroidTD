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
		
		
		//switch (getTarget().getType()) {
		//case AIR:		// if HEALTHY/BOSS = no slow
			//getTarget().setHealth((int) (getTarget().getHealth() - getDamage()*0.7));
			//break;
		//default: 
			getTarget().setHealth(getTarget().getHealth() - getDamage());
			//break;
		//}
		
		switch (getTarget().getType()) {
		case HEALTHY:		// if HEALTHY/BOSS = less slow
			getTarget().setSlowed(50,1-(1-mSlowEffect)/3);
		break;
		case IMMUNE:
			break;
		default: getTarget().setSlowed(200,mSlowEffect);
			break;
		}
	}
	
	public int getProjImage(){
		
		return R.drawable.projslow;
	}
}