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
	
	
	/**
	 * Enum for the mob type. One for each type of mob.
	 */
	public enum MobType { HEALTHY, ARMORED, FAST, NORMAL }
    
	/*
     * TestConstructor. hard coded lol-mob for testing purposes. TODO
     * 
     * @param 
     */

    public Mob(Path pPath) {
        mPath = pPath;
    	setCoordinates(mPath.getCoordinate(0));
    	setCheckpoint(1);
    	updateAngle();
    	
    	
        setSpeed(1);        
       
        setHealth(1200);
        setMaxHealth(1200);
        setArmor(1200);

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
	public void updatePosition() {
		
		// if the mob reached his current checkpoint, change direction for the next checkpoint
		if (reachedCheckpoint()) {
			setCheckpoint(getCheckpoint()+1);
			updateAngle();
		}
		
		setX(getX() + (getSpeed() * Math.cos(getAngle())));
		setY(getY() - (getSpeed() * Math.sin(getAngle())));
		
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
		
		double tx = getX();
		double ty = getY();
	
		try {
			mPath.getCoordinate(mCheckpoint).getX();
			mPath.getCoordinate(mCheckpoint).getY();
		} catch (Exception e) {
			return false;
		}
    	
		double mx = mPath.getCoordinate(mCheckpoint).getX();
    	double my = mPath.getCoordinate(mCheckpoint).getY();
		
		if (mx == 0 && my == 0)
			return false;
    	
		double sqrDistance = (tx - mx)*(tx - mx) + (ty - my)*(ty - my);
		
		if (sqrDistance < getSpeed())
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