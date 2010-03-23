package com.chalmers.game.td.units;

import com.chalmers.game.td.Path;
import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.units.Unit;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Class which represents a Mob on the game board.
 * 
 * @author Fredrik Persson
 * @author Jonas Andersson
 * @author Ahmed Chaban
 * @author Jonas Wallander
 * @author Disa Faith
 * @author Daniel Arvidsson
 */
public class Mob extends Unit{

	
	/** Mob max health */
	private int mMaxHealth;
	
	/** Mob health */
	private int mHealth;
	
	/** Mob movement speed */
	private int mSpeed;
	
	/** Mob movement angle */
	private double mAngle;
	
	/** Mob armor */
	private int mArmor;
	
	/** Mob type (Ground, air or invisible) */
	private MobType mType;	
	
	/** Path Checkpoint */
	private int mCheckpoint;
	
	/** Path */
	private Path mPath;
	
	/** Speed */
	double speedX;
	double speedY;
	
	
	/**
	 * Enum for the mob type. One for each type of mob.
	 */
	public enum MobType { HEALTHY, ARMORED, FAST, NORMAL }
    
	/*
     * TestConstructor. hard coded lol-mob for testing purposes. TODO
     * 
     * @param 
     */

    public Mob(Path pPath, MobType pType) {
        mPath = pPath;
        mType = pType;
    	setCoordinates(mPath.getCoordinate(0));
    	setCheckpoint(1);
    	updateAngle();
        setSpeed(1);        
        setHealth(1200);
        setMaxHealth(1200);
        setArmor(1200);
        
        // TODO: fix dynamic size
        setSize(24);
        
        // TODO: Fredrik f�r fixa...
        speedX = getSpeed() * Math.cos(getAngle());
		speedY = getSpeed() * Math.sin(getAngle());

    }
	
    /**
     * Setter for mob movement speed
     * @param i
     */
	private void setSpeed(int i) {
		mSpeed = i;
	}
	/**
	 * Setter for mob armor
	 * @param i
	 */
	private void setArmor(int i) {
		mArmor = i;
		
	}

	/**
     * Constructor. even more hard code lolmobs.
     * 
     * 
     */
    public Mob(MobType pType) {
        setType(pType);
        
        setCoordinates(new Coordinate(180,20));
        setSpeed(1);
        setAngle(Math.PI * 1.5);

        
        setHealth(1200);
        setMaxHealth(1200);
        setArmor(1200);  
        
    }
	
    /**
     * Constructor
     * 
     * @param pHealth Mob health
     * @param pSpeed Mob movement speed
     * @param pAngle Mob movement angle
     * @param pArmor Mob armor
     */
    public Mob(int pHealth, int pSpeed, int pAngle, int pArmor){
    	setMaxHealth(pHealth);
    	setHealth(pHealth);
    	setSpeed(pSpeed);
    	setAngle(pAngle);
    	setArmor(pArmor);
    }

    /**
     * Setter for which checkpoint the mob is walking to.
     * 
     * @param pCheckpoint
     */
    public void setCheckpoint(int pCheckpoint) {
    	mCheckpoint = pCheckpoint;
    }
    
    /**
     * @return
     */
    public int getSpeed() {
        return mSpeed;
    }



    /**
     * @param
     */
    public void setType(MobType pType) {
        mType = pType;
    }

    /**
     * @return The type of the instance.
     */
    public MobType getType() {
        return mType;
    }

	/**
	 * @param mHealth the mHealth to set
	 */
	public void setHealth(int mHealth) {
		this.mHealth = mHealth;
	}

	/**
	 * @return the mHealth
	 */
	public int getHealth() {
		return mHealth;
	}
	
	/**
	 * Settes for mob movement angle
	 * @param mAngle
	 */
	public void setAngle(double mAngle) {
		this.mAngle = mAngle;
	}

	/**
	 * Getter for mob movement angle
	 * @return
	 */
	public double getAngle() {
		return mAngle;
	}

	/**
	 * Updates the mobs position according to speed and angle.
	 */
	
	public boolean updatePosition() {
		
		
		
		// if the mob reached his current checkpoint, change direction		
		if (reachedCheckpoint()) {
			setCheckpoint(getCheckpoint()+1);
			
		
			if (mPath.getCoordinate(getCheckpoint()) == null) {
				Log.v("MOB EVENT","NEXT COORDINATE IS NULL"); // Kan ta bort efter debug
				return false;
			}
			updateAngle();
			
			speedX = getSpeed() * Math.cos(getAngle());
			speedY = getSpeed() * Math.sin(getAngle());
			
			setX(getX() + speedX);
			setY(getY() - speedY);
		} else {
			setX(getX() + speedX);
			setY(getY() - speedY);
		}
		return true;
	}

	/**
	 * Getter for the current checkpoint number
	 * @return
	 */
	private int getCheckpoint() {
		return mCheckpoint;
	}

	/**
	 * Method that checks whether the mob has reached its current checkpoint
	 * Slightly bugged. TODO
	 * @return
	 */
	public boolean reachedCheckpoint() {
	
		double sqrDistance = Coordinate.getSqrDistance(this.getCoordinates(), mPath.getCoordinate(mCheckpoint));	
		if (sqrDistance < getSpeed()*getSpeed())
			return true;
		
		return false;
		
	}
	
	/**
	 * Update the mobs movement angle according to its current checkpoint.
	 */
	public void updateAngle() {
		setAngle(Coordinate.getAngle(this.getCoordinates(), mPath.getCoordinate(mCheckpoint)));
		
	}

	public void setMaxHealth(int mMaxHealth) {
		this.mMaxHealth = mMaxHealth;
	}

	public int getMaxHealth() {
		return mMaxHealth;
	}
   
}