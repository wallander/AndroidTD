package com.chalmers.game.td.units;


import java.util.ArrayList;
import java.util.List;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GamePanel;

import com.chalmers.game.td.R;

public class SlowTower extends Tower {

	private int mSlow;
	
	public SlowTower(int mX, int mY) {
		super(mX, mY);
		setName("Slow Tower");		
		setCost(200);
		setCoolDown(30);
    	resetCoolDown();
		setRange(60);
		setDamage(20);
		setSlow(30);
		// TODO Set appropriate values to range, damage, attack speed and such

	}

	public void setSlow(int i) {
		mSlow = i;
	}

	public int getSlow() {
		return mSlow;
	}

	// Temporary changes images up to 4 upgrades.
	public void setImageByLevel(int pLevel) {
		switch (pLevel) {
			case 1: setImage(R.drawable.slowtower); break;
			case 2: setImage(R.drawable.slowtower2);  break;
			case 3: setImage(R.drawable.slowtower3);  break;
			case 4: setImage(R.drawable.slowtower4);  break;
		}		
	}

    /**
     * Method that returns a Projectile set to target the first mob that isn't slowed
     * in the given list of mobs that the tower can reach.
     * 
     * @param mobs List of mobs for the tower to target
     * @return Projectile set to target the first mob the tower can reach.
     */
	public Projectile createProjectile(Mob pTarget) {
    	return new SlowProjectile(pTarget, this);
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
    			setDamage(25);
        		setRange(75);
        		setSlow(40); break;
    		case 3:
    			setDamage(30);
        		setRange(75);
        		setSlow(50); break;
    		case 4:
    			setDamage(40);
        		setRange(75);
        		setCoolDown(25);
        		setSlow(60); break;
    		}
    		
// Old values, kept for reference
//    		switch (getLevel()){			//set damage and range according to the new level
//    		case 2:
//    			setDamage(16);
//        		setRange(110); break;
//    		case 3:
//    			setCoolDown(25);
//    			setDamage(40);
//        		setRange(125); break;
//    		case 4:
//    			setCoolDown(15);
//    			setDamage(120);
//        		setRange(140); break;
//    		}
    	}
    	return true;
	}
    
    /**
     * returns the current upgrade cost
     */
	public int getUpgradeCost() {

		switch(getLevel()) {
		case 1: return 200;
		case 2: return 200;
		case 3: return 200;
		}
		return 0; 	//default, not gonna happen
	}

}
