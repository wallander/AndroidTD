package com.chalmers.game.td.units;

import com.chalmers.game.td.units.Unit.Coordinates;

import android.graphics.Bitmap;

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
	
	/** Mob armor */
	private int mArmor;
	
	/** Mob type (Ground, air or invisible) */
	private MobType mType;	
	
	
	/**
	 * Enum for the mob type. One for each type of mob.
	 */
	private enum MobType { AIR, GROUND, INVIS }
    

	
	/**
     * TestConstructor.
     * 
     * @param 
     */
    public Mob() {
    	mCoordinates = new Coordinates(25, 15);
        mSpeed = 1;
        mArmor = 200;
        setHealth(200);
        
       
    }
	
	/**
     * Constructor.
     * 
     * @param bitmap Bitmap which should be drawn.
     */
    public Mob(MobType pType) {
       
        mType = pType;
    }
	
    



    /**
     * Step of explosion which will take 50 steps.
     */
    private int _explosionStep = 0;

    
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

   
}