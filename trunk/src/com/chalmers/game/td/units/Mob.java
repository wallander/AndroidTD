package com.chalmers.game.td.units;

import com.chalmers.game.td.units.Unit.Coordinates;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Class which represents a Mob on the game board.
 * 
 * @author Tomten
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
	
	
	/**
	 * Enum for the mob type. One for each type of mob.
	 */
	private enum MobType { AIR, GROUND, INVIS }
    

	
	/**
     * TestConstructor. skapar en testmob, hårdkodad osv osv.
     * 
     * @param 
     */
    public Mob() {
    	mCoordinates = new Coordinates(25, 15);
        mSpeed = 1;
        setAngle(Math.PI * 1.5);
        mArmor = 200;
        setHealth(200);
    }
	
	/**
     * Constructor.
     * 
     * 
     */
    public Mob(MobType pType) {
       
        mType = pType;
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
		
		mCoordinates.setX((int)mXPos);
		mCoordinates.setY((int)mYPos);
		
	}

   
}