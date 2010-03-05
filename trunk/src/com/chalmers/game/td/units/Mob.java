package com.chalmers.game.td.units;

import com.chalmers.game.td.Path;
import com.chalmers.game.td.units.Unit.Coordinates;
import com.chalmers.game.td.units.Unit;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Class which represents a Mob on the game board.
 * 
 * @author Jonas Andersson, Daniel Arvidsson, Ahmed Chaban, Disa Faith, Fredrik Persson, Jonas Wallander
 */
public class Mob extends Unit{

	/** Mob name */
	private String mName;
	
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
	
	/** Mob target (To next crossroad */
	private Coordinates mNextCrossRoad;
	
	private Path mPath;
	
	
	/**
	 * Enum for the mob type. One for each type of mob.
	 */
	public enum MobType { HEALTHY, ARMORED, FAST, NORMAL }
    

	
	/**
     * TestConstructor. skapar en testmob, h�rdkodad osv osv.
     * 
     * @param 
     */

    public Mob(Path pPath) {
    	mCoordinates = new Coordinates(40, 15);

        mSpeed = 1;
        setAngle(Math.PI * 1.5);
        mArmor = 200;
        setHealth(200);
        mPath = pPath;
    }
	
	/**
     * Constructor.
     * 
     * 
     */
    public Mob(MobType pType) {
        setType(pType);
        
        
        // H�RDKODAT! Ta bort sen!
        mCoordinates = new Coordinates(40,20);
        mSpeed = 1;
        setAngle(Math.PI * 1.5);
        mArmor = 200;
        setHealth(200);
        
        
    }
	
    public Mob(int pHealth, int pSpeed, int pAngle, int pArmor){
    	mHealth = pHealth;
    	mSpeed = pSpeed;
    	mAngle = pAngle;
    	mArmor = pArmor;
    }

    
    /**
     * @return The speed of the instance
     */
    public int getSpeed() {
        return mSpeed;
    }



    /**
     * @param type The new type of the instance.
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

	public void setAngle(double mAngle) {
		this.mAngle = mAngle;
	}

	public double getAngle() {
		return mAngle;
	}

	/**
	 * Updates the mobs position according to speed and angle.
	 */
	
	private double mXPos;
	private double mYPos;
	public void updatePosition() {
		
		if (mXPos == 0.0 || mYPos == 0.0) {
			mXPos = mCoordinates.getX();
			mYPos = mCoordinates.getY();
		}
		
		mXPos += getSpeed() * Math.cos(getAngle());
		mYPos -= getSpeed() * Math.sin(getAngle());
		
		mCoordinates.setX(mXPos);
		mCoordinates.setY(mYPos);
		
	}

   
}