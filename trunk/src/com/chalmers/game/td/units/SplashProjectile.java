package com.chalmers.game.td.units;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GamePanel;
import com.chalmers.game.td.R;

public class SplashProjectile extends Projectile {

	private Coordinate mTargetCoordinate;
	private int mSplashRadius;

	//tal mellan 1-5, hur stor effect splashen har
	private int mSplashEffect;
	
	private int mImage = R.drawable.projsplash_big;

	public SplashProjectile(Mob pTarget, SplashTower pTower) {
		super(pTarget, pTower);

		mTargetCoordinate = new Coordinate(getTarget().getX() + getTarget().getWidth()/2, 
				getTarget().getY() + getTarget().getHeight()/2);

		setAngle(Coordinate.getAngle(this.getCoordinates(), mTargetCoordinate));

		mSplashRadius = pTower.getSplashRadius(); //50;
		mSplashEffect = 5; //pTower.getSplash(); //tal mellan 1-5, hur stor effect splashen har
	}

	/**
	 * Inflicts damage to all nearby mobs.
	 */
	public void inflictDmg() {
		getTarget().setHealth(getTarget().getHealth() - getDamage());
		// hit every mob within a certain radius of the target coordinate for
		// a certain amount of damage.
		for (int i = 0; i < GameModel.mMobs.size(); i++) {
			Mob m = GameModel.mMobs.get(i);

			double sqrDist = Coordinate.getSqrDistance(mTargetCoordinate, m.getCoordinates());

			if (getTarget() != m && sqrDist <= mSplashRadius) {
				m.setHealth(m.getHealth() - (int)((double)mTower.getDamage() 
						* (1 - (sqrDist/mSplashRadius/2))/5*mSplashEffect));
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
	
	public int getProjImage(){
		return mImage;
	}

}
