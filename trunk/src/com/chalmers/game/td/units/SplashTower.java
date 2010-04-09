package com.chalmers.game.td.units;


import java.util.ArrayList;
import java.util.List;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GamePanel;

import com.chalmers.game.td.R;

/**
 * This class extends the Tower class. It shoots splashprojectiles instead of projectiles.
 * 
 *
 */
public class SplashTower extends Tower {

	public SplashTower(int pX, int pY) {
		super(pX, pY);
		setName("Splash Tower");
		// TODO Set appropriate values to range, damage, attack speed and such
//		mDamage = 50;
//		mAttackSpeed = 30;
//		mCost = 50;
//		mRange = 80;
	}

	
	// Temporary changes images up to 4 upgrades.
	public void setImageByLevel(int pLevel) {

		switch (pLevel) {
			case 1: setImage(R.drawable.splashtower); break;
			case 2: setImage(R.drawable.splashtower2); break;
			case 3: setImage(R.drawable.splashtower3); break;
			case 4: setImage(R.drawable.splashtower4); break;
		}		
	}

    /**
     * Method that returns a Projectile set to target the first mob
     * in the given list of mobs that the tower can reach.
     * 
     * @param mobs List of mobs for the tower to target
     * @return Projectile set to target the first mob the tower can reach.
     */
    
    public Projectile createProjectile(Mob pTarget) {
    	return new SplashProjectile(pTarget, this);
    }

	public boolean upgrade() {
    	//TODO change values
    	if (!canUpgrade())					//return false if tower can't be upgraded
    		return false;
    	else {
    		setLevel(getLevel()+1);			//increment tower level by one
    		setImageByLevel(getLevel());	//set image according to the new level
    		
    		switch (getLevel()){			//set damage and range according to the new level
    		case 2:
    			setDamage(16);
        		setRange(110);
    		case 3:
    			setDamage(40);
        		setRange(125);
    		case 4:
    			setDamage(120);
        		setRange(140);
    		}
    	}
    	return true;
	}

}
