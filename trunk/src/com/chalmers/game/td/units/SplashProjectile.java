package com.chalmers.game.td.units;

import android.util.Log;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GameView;
import com.chalmers.game.td.R;
import com.chalmers.game.td.R.drawable;

public class SplashProjectile extends Projectile {

	private Coordinate mTargetCoordinate;
	private int mSplashRadius;
	private int mExplAnimation = 0;
	private Boolean startExpl = false;

	
	private int mImage = R.drawable.snowball;

	public SplashProjectile(Mob pTarget, SplashTower pTower, int pSplashRadius) {
		super(pTarget, pTower);

		mTargetCoordinate = new Coordinate(getTarget().getX() + getTarget().getWidth()/2, 
				getTarget().getY() + getTarget().getHeight()/2);

		setAngle(Coordinate.getAngle(this.getCoordinates(), mTargetCoordinate));

		mSplashRadius = pSplashRadius;
	}

	/**
	 * Inflicts damage to all nearby mobs.
	 */
	public void inflictDmg() {
		// hit every mob within a certain radius from the target coordinate for
		// a certain amount of damage, depending on distance from center of splash
		for (Mob m: GameModel.sMobs){
			
			double sqrDist = Coordinate.getDistance(mTargetCoordinate, m.getCoordinates());

			if (sqrDist <= mSplashRadius) {
				//calculates the damage based on distance from explosion.
				m.takeDamage((int)
						(mTower.getDamage() * (1 - (sqrDist/mSplashRadius/2)))
				);
			}
		}
	}

	/**
	 * Updates the position of this projectile.
	 *
	 * This is NOT a homing projectile, it keeps the same coordinate as target.
	 */
	public void updatePosition(float timeDelta) {
		setX( getX() + 2*timeDelta*GameModel.getSpeedMultiplier()*(getSpeed() * Math.cos(getAngle())) );
		setY( getY() - 2*timeDelta*GameModel.getSpeedMultiplier()*(getSpeed() * Math.sin(getAngle())) );
	}

	public boolean hasCollided(float timeDelta) {

		double distance = Coordinate.getDistance(getCoordinates(), mTargetCoordinate);

		Log.i("Collide=",distance +"<"+ 4 +"*" + GameModel.getSpeedMultiplier() +"*"+getSpeed() +"*"+timeDelta +"="+ 4*GameModel.getSpeedMultiplier()*getSpeed()*timeDelta);
		if (distance <= 6*GameModel.getSpeedMultiplier()*getSpeed()*timeDelta){
			setX(mTargetCoordinate.getX());
			setY(mTargetCoordinate.getY());
			return true;
		}

		return false;
	}
	 

	   
	public int getProjImage(){
		
		if (startExpl) {
			if (getExplAni() == 1) {
				return R.drawable.snowball;
			} else if (getExplAni() == 2) {
				return R.drawable.expl1;
			} else if (getExplAni() == 3) {
				return R.drawable.expl2;
			} else if (getExplAni() == 4) {
				return R.drawable.expl3;
			} else if (getExplAni() == 5) {
				return R.drawable.expl4;
			} else if (getExplAni() == 6) {
				return R.drawable.expl5;
			} else if (getExplAni() == 7) {
				return R.drawable.expl5;
			} else if (getExplAni() == 8) {
				return R.drawable.expl4;
			} else if (getExplAni() == 9) {
				return R.drawable.expl3;
			} else if (getExplAni() == 10) {
				return R.drawable.expl2;
			} else if (getExplAni() == 11) {
				return R.drawable.expl1;
			} else if (getExplAni() == 12) {
				return R.drawable.expl1;
			} else {
				
				return R.drawable.blankexpl;
			}
			
		} else {
		return mImage;
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
