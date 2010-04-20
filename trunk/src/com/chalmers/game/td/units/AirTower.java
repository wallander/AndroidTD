package com.chalmers.game.td.units;


import java.util.ArrayList;
import java.util.List;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GamePanel;

import com.chalmers.game.td.R;
import com.chalmers.game.td.units.Mob.MobType;

public class AirTower extends Tower {

	public static final int[] sCoolDown = new int[]{30,30,30,30};
	public static final int[] sDamage = new int[]{25,35,50,80};
	public static final int[] sRange = new int[]{120,120,120,120};

	public static final int[] sUpgradeCost = new int[]{200,300,500};

	
	public AirTower(int mX, int mY) {
		super(mX, mY);
		setName("Igloo Canon");		
		setCost(130);
		setType(AIR);
		setDescription("Can only damage flying units.");
    	resetCoolDown();
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
	
	public int getAttackSpeed(int pLevel){
		if (pLevel >= 1)
			return 100/sCoolDown[pLevel-1];
		else
			return -1;
	}

	public boolean upgrade() {
		
		if (!canUpgrade())					//return false if tower can't be upgraded
    		return false;
    	else {
    		
    		setLevel(getLevel()+1);			//increment tower level by one
    		setImageByLevel(getLevel());	//set image according to the new level
    		
    		setDamage(sDamage[getLevel()-1]);
    		setCoolDown(sCoolDown[getLevel()-1]);
    		setRange(sRange[getLevel()-1]);

        	return true;
    	}
	}
	
    /**
     * returns the current upgrade cost
     */
	public int getUpgradeCost() {
		return sUpgradeCost[getLevel()-1];
	}

}
