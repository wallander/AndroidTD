package com.chalmers.game.td.units;

import android.graphics.Bitmap;

/**
 * Class which represents a Mob on the game board.
 * 
 * @author martin
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
	
	/** Bitmap for the mob */
    private Bitmap mBitmap;
	
	/**
	 * Enum for the mob type. One for each type of mob.
	 */
	private enum MobType { AIR, GROUND, INVIS }
    
	
	/**
     * Constructor.
     * 
     * @param bitmap Bitmap which should be drawn.
     */
    public Mob(Bitmap pBitmap, MobType pType) {
        mBitmap = pBitmap;
        mType = pType;
    }
	
    



    /**
     * Step of explosion which will take 50 steps.
     */
    private int _explosionStep = 0;

    

    /**
     * @param bitmap New bitmap to draw.
     */
    public void setBitmap(Bitmap pBitmap) {
        mBitmap = pBitmap;
    }

    /**
     * @return The stored bitmap.
     */
    public Bitmap getBitmap() {
        return mBitmap;
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

   
}