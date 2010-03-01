package com.android.towerdef;

import android.graphics.Bitmap;

/**
 * Class which represents a Mob on the game board.
 * 
 * @author martin
 */
public class Mob extends Unit{

	private String mName;	// Mob name
	private int mHealth;	// Mob health
	private int mSpeed;	// Mob movement speed
	private int mArmor;		// Mob armor
	// private Coordinates mCoordinates; finns i Unit
	
	

    
    /**
     * Bitmap which should be drawn.
     */
    private Bitmap _bitmap;




    /**
     * Object type which could be rock, scissors, paper or explosion.
     */
    private String _type;

    /**
     * Step of explosion which will take 50 steps.
     */
    private int _explosionStep = 0;

    /**
     * Constructor.
     * 
     * @param bitmap Bitmap which should be drawn.
     */
    public Mob(Bitmap bitmap) {
        _bitmap = bitmap;
       // _coordinates = new Coordinates();
       // mSpeed = new Speed();
    }

    /**
     * @param bitmap New bitmap to draw.
     */
    public void setBitmap(Bitmap bitmap) {
        _bitmap = bitmap;
    }

    /**
     * @return The stored bitmap.
     */
    public Bitmap getBitmap() {
        return _bitmap;
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
    public void setType(String type) {
        _type = type;
    }

    /**
     * @return The type of the instance.
     */
    public String getType() {
        return _type;
    }

    /**
     * @param step The new explosion step.
     */
    public void setExplosionStep(int step) {
        _explosionStep = step;
    }

    /**
     * @return The explosion step.
     */
    public int getExplosionStep() {
        return _explosionStep;
    }
}