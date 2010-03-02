package com.chalmers.game.td.units;

import android.graphics.Bitmap;

/**
 * Class which represents a Projectiles on the game board.
 * 
 */
public class Projectile extends Unit{

	/** Projectile movement speed */
	private int mSpeed;
	
	/** Projectile damage */
	private int mDamage;
	
	/** Projectile direction */
	private int mDriection;

	/** Projectile target */
	private Mob mTarget;
	
	/** Projectile tower */
	private Tower mTower;
    
	
	/**
     * Constructor.
     * 
     * @param bitmap Bitmap which should be drawn.
     */
    public Projectile(Mob pTarget, Tower pTower) {
        mTarget = pTarget;
        mTower = pTower;
    }
	
    



  
    /**
     * @return Damage done
     */
    public int inflictDmg() {
       
    	return 1;
    }



   
}