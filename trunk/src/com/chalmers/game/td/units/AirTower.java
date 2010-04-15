package com.chalmers.game.td.units;


import java.util.ArrayList;
import java.util.List;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GamePanel;

import com.chalmers.game.td.R;
import com.chalmers.game.td.units.Mob.MobType;

public class AirTower extends Tower {

	
	
	public AirTower(int mX, int mY) {
		super(mX, mY);
		setName("Igloo Canon");		
		setCost(130);
		setCoolDown(30);
		setDescription("Can only damage AIR units.");
    	resetCoolDown();
		setRange(120);
		setDamage(120);
		setType(TowerType.AIR);
		// TODO Set appropriate values to range, damage, attack speed and such

	}
	


	// Temporary changes images up to 4 upgrades.
	public void setImageByLevel(int pLevel) {
		switch (pLevel) {
			case 1: setImage(R.drawable.airtower1); break;
			case 2: setImage(R.drawable.airtower2);  break;
			case 3: setImage(R.drawable.airtower3);  break;
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
		return new AirProjectile(pTarget, this);    	
    }

	public Projectile shoot() {

		ArrayList<Mob> mobsInRange = new ArrayList<Mob>();

		// loop through the list of mobs
		for (int i=0; i < GameModel.mMobs.size(); i++) {

			Mob m = GameModel.mMobs.get(i);

			double sqrDist = Coordinate.getSqrDistance(this.getCoordinates(), m.getCoordinates());

			// if the mob is in range, and is a air mob, add it to list
			if (sqrDist < getRange() && m.getType()== MobType.AIR)
				mobsInRange.add(m);
		}

		//if there are any mobs available to shoot, return a projectile on the first of them, 
		//else return null
		if (!mobsInRange.isEmpty())
			return createProjectile(firstMob(mobsInRange));
		else
			return null;
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
		default: return 500;	//case 3
		}
	}


}
