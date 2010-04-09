package com.chalmers.game.td.units;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GamePanel;

public class SplashProjectile extends Projectile {

	private Coordinate targetCoordinate;
	private int blastRadius;
	
	//tal mellan 1-5, hur stor effect splashen har
	private int blastEffect;
	
	public SplashProjectile(Mob pTarget, SplashTower pTower, GameModel pGameModel) {
		super(pTarget, pTower, pGameModel);
		
		targetCoordinate = new Coordinate(getMob().getX() + getMob().getWidth()/2, getMob().getY()  + getMob().getHeight()/2);
		setAngle(Coordinate.getAngle(this.getCoordinates(), targetCoordinate));
		
		blastRadius = 50;
		blastEffect = pTower.getSplash(); //tal mellan 1-5, hur stor effect slashen har
	}

	public SplashProjectile(Mob pTarget, Tower pTower) {
		super(pTarget, pTower);
		
		targetCoordinate = new Coordinate(getMob().getX() + getMob().getWidth()/2, getMob().getY()  
				+ getMob().getHeight()/2);
		
		setAngle(Coordinate.getAngle(this.getCoordinates(), targetCoordinate));
		
		blastRadius = 20;
		blastEffect = 3; //tal mellan 1-5, hur stor effect splashen har
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
		 		
		 		double sqrDist = Coordinate.getSqrDistance(targetCoordinate, m.getCoordinates());
		 		
		 		if (mTarget != m && sqrDist <= blastRadius) {
		 			m.setHealth(m.getHealth() - (int)((double)mTower.getDamage() * (1 - (sqrDist/blastRadius/2))/5*blastEffect));
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
			
			double distance = Coordinate.getSqrDistance(getCoordinates(), targetCoordinate);
			
			if (distance < GamePanel.getSpeedMultiplier()*getSpeed())
				return true;
			
			return false;
		}
	 
}
