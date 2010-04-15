package com.chalmers.game.td.units;


import java.util.ArrayList;
import java.util.List;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GamePanel;

import com.chalmers.game.td.R;

public class AirTower extends Tower {

	
	
	public AirTower(int mX, int mY) {
		super(mX, mY);
		setName("Air Tower");		
		setCost(12);
		setCoolDown(30);
    	resetCoolDown();
		setRange(120);
		setDamage(20);
		// TODO Set appropriate values to range, damage, attack speed and such

	}
	


	// Temporary changes images up to 4 upgrades.
	public void setImageByLevel(int pLevel) {
		switch (pLevel) {
			case 1: setImage(R.drawable.airtower); break;
			case 2: setImage(R.drawable.airtower);  break;
			case 3: setImage(R.drawable.airtower);  break;
			case 4: setImage(R.drawable.airtower);  break;
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
		switch(pTarget.getType()) {
		case AIR:
			return new AirProjectile(pTarget, this);
			default: return null;
			
		}
    	
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
    			setDamage(35);
        		setRange(120);
        		 break;
    		case 3:
    			setDamage(50);
        		setRange(120);
        		break;
    		case 4:
    			setDamage(80);
        		setRange(120);
        		break;
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
		case 2: return 300;
		case 3: return 500;
		}
		return 0; 	//default, not gonna happen
	}

}
