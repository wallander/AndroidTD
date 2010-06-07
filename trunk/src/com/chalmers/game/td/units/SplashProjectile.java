package com.chalmers.game.td.units;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GameView;
import com.chalmers.game.td.R;

public class SplashProjectile extends Projectile {

	private Coordinate mTargetCoordinate;
	private int mSplashRadius;
	
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
	public void updatePosition() {
		setX( getX() + GameView.getSpeedMultiplier()*(getSpeed() * Math.cos(getAngle())) );
		setY( getY() - GameView.getSpeedMultiplier()*(getSpeed() * Math.sin(getAngle())) );
	}

	public boolean hasCollided() {

		double distance = Coordinate.getDistance(getCoordinates(), mTargetCoordinate);

		if (distance < GameView.getSpeedMultiplier()*getSpeed())
			return true;

		return false;
	}
	
	public int getProjImage(){
		return mImage;
	}

}
