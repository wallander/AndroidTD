package com.chalmers.game.td.units;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.R;

public class BasicProjectile extends Projectile {
	
	private Mob mTarget;


	public static float mExplosionTime = 0.3f;
	
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
		
		
//		
//		if (startExpl) {
//			if (getExplAni() == 1) {
//				return R.drawable.nexpl1;
//			} else if (getExplAni() == 2) {
//				return R.drawable.nexpl2;
//			} else if (getExplAni() == 3) {
//				return R.drawable.nexpl3;
//			} else if (getExplAni() == 4) {
//				return R.drawable.nexpl4;
//			} else if (getExplAni() == 5) {
//				return R.drawable.blankexpl;
//			} else if (getExplAni() == 6) {
//				return R.drawable.blankexpl;
//			} else if (getExplAni() == 7) {
//				return R.drawable.blankexpl;
//			} else if (getExplAni() == 8) {
//				return R.drawable.blankexpl;
//			} else if (getExplAni() == 9) {
//				return R.drawable.blankexpl;
//			} else if (getExplAni() == 10) {
//				return R.drawable.blankexpl;
//			} else if (getExplAni() == 11) {
//				return R.drawable.blankexpl;
//			} else if (getExplAni() == 12) {
//				return R.drawable.blankexpl;
//			}  else {
//				
//				return R.drawable.blankexpl;
//			}
			
	}
		
			
		
	
	public void setBoolExpl(Boolean b){
		startExpl = b;
		
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
