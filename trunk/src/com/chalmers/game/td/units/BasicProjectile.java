package com.chalmers.game.td.units;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.R;

public class BasicProjectile extends Projectile {
	
	private Mob mTarget;
	private Boolean startExpl = false;

	public BasicProjectile(Mob pTarget, Tower pTower) {

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
		if (startExpl) {
			if (getExplAni() == 1) {
				return R.drawable.nexpl1;
			} else if (getExplAni() == 2) {
				return R.drawable.nexpl2;
			} else if (getExplAni() == 3) {
				return R.drawable.nexpl3;
			} else if (getExplAni() == 4) {
				return R.drawable.nexpl4;
			} else if (getExplAni() == 5) {
				return R.drawable.blankexpl;
			} else if (getExplAni() == 6) {
				return R.drawable.blankexpl;
			} else if (getExplAni() == 7) {
				return R.drawable.blankexpl;
			} else if (getExplAni() == 8) {
				return R.drawable.blankexpl;
			} else if (getExplAni() == 9) {
				return R.drawable.blankexpl;
			} else if (getExplAni() == 10) {
				return R.drawable.blankexpl;
			} else if (getExplAni() == 11) {
				return R.drawable.blankexpl;
			} else if (getExplAni() == 12) {
				return R.drawable.blankexpl;
			}  else {
				
				return R.drawable.blankexpl;
			}
			
		}	else {
			return R.drawable.snowball_small;
		}
		
			
		
	}
	public void setBoolExpl(Boolean b){
		startExpl = b;
		
	}
	
	public int getExplAni(){
		return mExplAnimation;
	}
	   
	public void incExplAni(){
		mExplAnimation++;
	}
	
}
