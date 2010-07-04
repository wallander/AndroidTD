package com.chalmers.game.td.units;

import android.util.Log;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GameView;
import com.chalmers.game.td.R;
import com.chalmers.game.td.R.drawable;

public class SplashProjectile extends Projectile {

	public static float mExplosionTime = 0.33f;
	
	private Coordinate mTargetCoordinate;
	public int mSplashRadius;	


	
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

		//Log.i("Collide=",distance +"<"+ 4 +"*" + GameModel.getSpeedMultiplier() +"*"+getSpeed() +"*"+timeDelta +"="+ 4*GameModel.getSpeedMultiplier()*getSpeed()*timeDelta);
		if (distance <= 6*GameModel.getSpeedMultiplier()*getSpeed()*timeDelta){
			setX(mTargetCoordinate.getX());
			setY(mTargetCoordinate.getY());
			return true;
		}

		return false;
	}
	 

	   
	public int getProjImage(){
		

		if (mExploded == false) {
			return R.drawable.snowball;
		} else {
			
			if (mExplAnimation/mExplosionTime <= 1f/9f)

				return R.drawable.expl1;
			else if ((float)(mExplAnimation/mExplosionTime) <= 2f/9f) 
				return R.drawable.expl2;
			else if ((float)(mExplAnimation/mExplosionTime) <= 3f/9f) 
				return R.drawable.expl3;
			else if ((float)(mExplAnimation/mExplosionTime) <= 4f/9f) 
				return R.drawable.expl4;
			else if ((float)(mExplAnimation/mExplosionTime) <= 5f/9f) 
				return R.drawable.expl5;
			else if ((float)(mExplAnimation/mExplosionTime) <= 6f/9f) 
				return R.drawable.expl4;
			else if ((float)(mExplAnimation/mExplosionTime) <= 7f/9f) 
				return R.drawable.expl3;
			else if ((float)(mExplAnimation/mExplosionTime) <= 8f/9f) 
				return R.drawable.expl2;
			else 
				return R.drawable.expl1;
		}

	}

	@Override
	protected void updateAnimation(float timeDelta) {
		// TODO Auto-generated method stub
		if (mExploded) {
			mExplAnimation = mExplAnimation + timeDelta;
		}
		
	}

	@Override
	public float getExplosionTime() {
		// TODO Auto-generated method stub
		return mExplosionTime;
	}
	

}
