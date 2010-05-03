package com.chalmers.game.td.units;


import java.util.ArrayList;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;

import com.chalmers.game.td.R;

public class SlowTower extends Tower {

	/** How many percentages of the mob's speed that will be lost if it's hit by the tower.*/
	private int mSlow;
	public static final int[] sCoolDown = new int[]{30,30,30,25};
	public static final int[] sDamage = new int[]{15,20,25,30};
	public static final int[] sRange = new int[]{60,75,75,75};
	
	//Slow is given in percent. If a mob is slowed by 30% it's new speed is 70% of original speed. 
	public static final int[] sSlow = new int[]{30,40,50,60};
	
	public static final int[] sUpgradeCost = new int[]{200,200,200};
	
	public SlowTower(int mX, int mY) {
		super(mX, mY);
		setType(SLOW);
		setName("Snowman");
		setCost(200);
		setDescription("Slows down mobs!");
    	resetCoolDown();
		// TODO Set appropriate values to range, damage, attack speed and such
	}

	/**
	 * Sets the amount of slow that this tower inflicts on mobs.
	 * @return How many percentages of the mob's speed that will be lost if it's hit by the tower.
	 */
	@Override
	public int getSlow() {
		return mSlow;
	}

	// Temporary changes images up to 4 upgrades.
	@Override
	public void setImageByLevel(int pLevel) {
		switch (pLevel) {
			case 1: setImage(R.drawable.slowtower);		break; //called from the constructor
			case 2: setImage(R.drawable.slowtower2);	break;
			case 3: setImage(R.drawable.slowtower3);	break;
			case 4: setImage(R.drawable.slowtower4);	break;
		}		
	}

    /**
     * Method that returns a Projectile set to target the first mob that isn't slowed
     * in the given list of mobs that the tower can reach.
     * 
     * @param mobs List of mobs for the tower to target
     * @return Projectile set to target the first mob the tower can reach.
     */
	@Override
	public Projectile createProjectile(Mob pTarget) {
    	return new SlowProjectile(pTarget, this, 1-(mSlow/100.0));
    }

	@Override
	public boolean upgrade() {
		
    	if (canUpgrade()) {
    		incLevel();							//increment tower level by one
    		
    		int newLvl = getLevel();
    		setImageByLevel(newLvl);			//set image according to the new level
    		
    		setCoolDown(sCoolDown[newLvl-1]); 	//set CD according to new level
			setDamage(sDamage[newLvl-1]);		//set damage according to new level
			setRange(sRange[newLvl-1]);			//set range according to new level
			setSlow(sSlow[newLvl-1]);			//set slow according to new level
			
	    	return true;
    	}
    	else
    		return false;				//return false if tower can't be upgraded
	}
	
	@Override
	public int getUpgradeCost() {
		return sUpgradeCost[getLevel()-1];
	}
	
	private void setSlow(int pSlow){
		mSlow = pSlow;
	}

	@Override
	public Projectile shoot() {

		ArrayList<Mob> mobsInRange = new ArrayList<Mob>();
		
		// loop through the list of mobs
		for (int i=0; i < GameModel.sMobs.size(); i++) {

			Mob m = GameModel.sMobs.get(i);

			double sqrDist = Coordinate.getSqrDistance(this.getCoordinates(), m.getCoordinates());

			// if the mob is in range, and not slowed already, add it to list
			if (sqrDist < getRange() && !m.isSlowed())
				mobsInRange.add(m);
		}

		//if there are any mobs available to shoot, return a projectile on the first of them, 
		//else return null
		if (!mobsInRange.isEmpty())
			return createProjectile(firstMob(mobsInRange));
		else
			return null;
	}
}