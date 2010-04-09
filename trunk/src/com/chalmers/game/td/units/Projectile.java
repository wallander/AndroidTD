package com.chalmers.game.td.units;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GamePanel;

/**
 * Class which represents a projectile on the game board.
 * 
 * @author Fredrik Persson
 * @author Jonas Andersson
 * @author Ahmed Chaban
 * @author Jonas Wallander
 * @author Disa Faith
 * @author Daniel Arvidsson
 */
public class Projectile extends Unit{

	/** Projectile movement speed */
	protected int mSpeed;
	
	/** Projectile type emun */
	public enum ProjectileType { NORMAL, SLOW, SPLASH }
	
	
	
	/** Projectile damage */
	protected int mDamage;

	/** Projectile target */
	protected Mob mTarget;
	
	protected GameModel mGameModel;
	
	/** Projectile tower */
	protected Tower mTower;
	
	/** Projectile movement angle */
	protected double mAngle;
	
	/**
     * Constructor.
     * 
     * @param bitmap Bitmap which should be drawn.
     */
    public Projectile(Mob pTarget, Tower pTower, GameModel model) {
    	mGameModel = model;
    	setCoordinates(new Coordinate(
    			pTower.getX() + (pTower.getWidth() * GameModel.GAME_TILE_SIZE / 2),
    			pTower.getY() - 16 + (pTower.getHeight() * GameModel.GAME_TILE_SIZE / 2)));

        setTarget(pTarget);
        setTower(pTower);
        setSpeed(10);
        setDamage(mTower.getDamage());
        
        
        // Jonas försökte göra så misilerna inte blev målsökande
        // Denna koden är bortkommenterad men testa om du vill
        // Kommentera bort viss kod i updatePosition isf också
        // I denna variant beräknas vart proj och mob kommer mötas och så skickas
        // proj ut i blindo. Formeln verkar dock göra så projektilen missar ofta då proj
        // inte är snabb nog
        
        //Coordinate targetCoordinate = new Coordinate(getMob().getX() + getMob().getWidth()/2, getMob().getY()  + getMob().getHeight()/2);
		//double a2 = Coordinate.getAngle(this.getCoordinates(), targetCoordinate);
		//setAngle(a2 - Math.asin(getMob().getSpeed()/getSpeed()*Math.sin(Math.PI - a2 + getMob().getAngle())));
    }
    
    public Projectile(Mob pTarget, Tower pTower) {
    	setCoordinates(new Coordinate(
    			pTower.getX() + (pTower.getWidth() * GameModel.GAME_TILE_SIZE / 2),
    			pTower.getY() - 16 + (pTower.getHeight() * GameModel.GAME_TILE_SIZE / 2)));

        setTarget(pTarget);
        setTower(pTower);
        setSpeed(10);
        setDamage(mTower.getDamage());
        
        
        // Jonas försökte göra så misilerna inte blev målsökande
        // Denna koden är bortkommenterad men testa om du vill
        // Kommentera bort viss kod i updatePosition isf också
        // I denna variant beräknas vart proj och mob kommer mötas och så skickas
        // proj ut i blindo. Formeln verkar dock göra så projektilen missar ofta då proj
        // inte är snabb nog
        
        //Coordinate targetCoordinate = new Coordinate(getMob().getX() + getMob().getWidth()/2, getMob().getY()  + getMob().getHeight()/2);
		//double a2 = Coordinate.getAngle(this.getCoordinates(), targetCoordinate);
		//setAngle(a2 - Math.asin(getMob().getSpeed()/getSpeed()*Math.sin(Math.PI - a2 + getMob().getAngle())));
    }
    
    public double getAngle() {
		return mAngle;
	}

	public Mob getMob(){
    	return mTarget;
    }

	public int getSpeed() {
        return mSpeed;
    }

	public Tower getTower(){
    	return mTower;
    }

	/**
     * Method used for collision detection
     * @return
     */
	public boolean hasCollided() {
		
		Coordinate targetCoordinate = new Coordinate(getMob().getX() + getMob().getWidth()/2, getMob().getY()  + getMob().getHeight()/2);

		double sqrDist = Coordinate.getSqrDistance(getCoordinates(), targetCoordinate);
		
		//return true if the projectile has collided, else return false
		if (sqrDist < GamePanel.getSpeedMultiplier()*getSpeed())
			return true;
		return false;
	}
    
    /**
     * 
     */
    public void inflictDmg() {
       mTarget.setHealth(mTarget.getHealth() - mDamage);
    }
    

    public void setAngle(double mAngle) {
		this.mAngle = mAngle;
	}
    
	private void setDamage(int i) {
		mDamage = i;
	}
	
	private void setSpeed(int i) {
		mSpeed = i;
	}
    

	private void setTarget(Mob pTarget) {
		mTarget = pTarget;
	}
	
  
    private void setTower(Tower pTower) {
		mTower = pTower;
	}
    
    /**
	 * Update the position of the projectile. Currently this makes the projectile 
	 * act like a homing missile.
	 * 
	 * To create other types of projectiles, create subclasses of this class 
	 * and override this method.
	 */
	public void updatePosition() {
		
		// Kommentera bort 2 rader ner om du ska testa min variant. Se kommentar i konstruktorn. / Jonas
		Coordinate targetCoordinate = new Coordinate(getMob().getX() + getMob().getWidth()/2, getMob().getY()  + getMob().getHeight()/2);
		setAngle(Coordinate.getAngle(this.getCoordinates(), targetCoordinate));

		setX(getX() + GamePanel.getSpeedMultiplier()*(getSpeed() * Math.cos(getAngle())) );
		setY(getY() - GamePanel.getSpeedMultiplier()*(getSpeed() * Math.sin(getAngle())) );
		
	}

}