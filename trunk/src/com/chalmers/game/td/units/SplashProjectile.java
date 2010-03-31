package com.chalmers.game.td.units;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;

public class SplashProjectile extends Projectile {

	private Coordinate targetCoordinate;
	private double blastRadius;
	
	public SplashProjectile(Mob pTarget, Tower pTower, GameModel pGameModel) {
		super(pTarget, pTower, pGameModel);
		
		targetCoordinate = new Coordinate(getMob().getX() + getMob().getWidth()/2, getMob().getY()  + getMob().getHeight()/2);
		setAngle(Coordinate.getAngle(this.getCoordinates(), targetCoordinate));
		
		blastRadius = 50;
	}

	/**
	 * Inflicts damage to all nearby mobs.
	 */
	 public void inflictDmg() {
	     	//TODO implement stuff
		 	// hit every mob within a certain radius of the target coordinate for
		 	// a certain amount of damage.
		 	for (int i = 0; i < mGameModel.mMobs.size(); i++) {
		 		Mob m = mGameModel.mMobs.get(i);
		 		
		 		double sqrDist = Coordinate.getSqrDistance(targetCoordinate, m.getCoordinates());
		 		
		 		if (sqrDist <= blastRadius) {
		 			m.setHealth(m.getHealth() - (int)((double)mTower.getDamage() * (1 - sqrDist/blastRadius)));
		 		}
		 	}
	    }
	
	 /**
	  * Updates the position of this projectile.
	  *
	  * This is NOT a homing projectile, it keeps the same coordinate as target.
	  */
	 public void updatePosition() {
			setX(getX() + (getSpeed() * Math.cos(getAngle()) ));
			setY(getY() - (getSpeed() * Math.sin(getAngle()) ));
		}
	 
	 public boolean hasCollided() {
			
			double sqrDist = Coordinate.getSqrDistance(getCoordinates(), targetCoordinate);
			
			if (sqrDist < getSpeed())
				return true;
			
			return false;
		}
	 
}
