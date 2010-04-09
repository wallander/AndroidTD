package com.chalmers.game.td.units;

import android.util.Log;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GamePanel;

public class SplashProjectile extends Projectile {

	private Coordinate mTargetCoordinate;
	private int mBlastRadius;
	
	//tal mellan 1-5, hur stor effect splashen har
	private int mBlastEffect;
	
	public SplashProjectile(Mob pTarget, SplashTower pTower, GameModel pGameModel) {
		super(pTarget, pTower, pGameModel);

		mTargetCoordinate = new Coordinate(getTarget().getX() + getTarget().getWidth()/2, 
				getTarget().getY() + getTarget().getHeight()/2);
		
		setAngle(Coordinate.getAngle(this.getCoordinates(), mTargetCoordinate));

		mBlastRadius = 50;
		mBlastEffect = pTower.getSplash(); //tal mellan 1-5, hur stor effect slashen har
	}

	public SplashProjectile(Mob pTarget, SplashTower pTower) {
		super(pTarget, pTower);

		mTargetCoordinate = new Coordinate(getTarget().getX() + getTarget().getWidth()/2, 
				getTarget().getY() + getTarget().getHeight()/2);

		setAngle(Coordinate.getAngle(this.getCoordinates(), mTargetCoordinate));

		mBlastRadius = 50;
		mBlastEffect = pTower.getSplash(); //tal mellan 1-5, hur stor effect splashen har
	}
	/**
	 * Inflicts damage to all nearby mobs.
	 */


	public void inflictDmg() {
		mTarget.setHealth(mTarget.getHealth() - mDamage);
		//TODO implement stuff
		// hit every mob within a certain radius of the target coordinate for
		// a certain amount of damage.
		for (int i = 0; i < mGameModel.mMobs.size(); i++) {
			Mob m = mGameModel.mMobs.get(i);

			double sqrDist = Coordinate.getSqrDistance(mTargetCoordinate, m.getCoordinates());

			if (mTarget != m && sqrDist <= mBlastRadius) {
				m.setHealth(m.getHealth() - (int)((double)mTower.getDamage() * (1 - (sqrDist/mBlastRadius/2))/5*mBlastEffect));
			}
		}
	}

	
	 /**
	  * Updates the position of this projectile.
	  *
	  * This is NOT a homing projectile, it keeps the same coordinate as target.
	  */
	 public void updatePosition() {

		 setX(getX() + GamePanel.getSpeedMultiplier()*(getSpeed() * Math.cos(getAngle()) ));
		 setY(getY() - GamePanel.getSpeedMultiplier()*(getSpeed() * Math.sin(getAngle()) ));
	 }

	 public boolean hasCollided() {

		 double distance = Coordinate.getSqrDistance(getCoordinates(), mTargetCoordinate);

		 if (distance < GamePanel.getSpeedMultiplier()*getSpeed())
			 return true;

		 return false;
	 }
	 
}
