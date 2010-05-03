package com.chalmers.game.td.units;


import java.util.ArrayList;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.R;

public class BasicTower extends Tower {

	public static final int[] sDamage = new int[]{5,15,35,95};
	public static final int[] sCoolDown = new int[]{24,24,24,24};
	public static final int[] sRange = new int[]{80,90,100,100};
	
	public static final int[] sUpgradeCost = new int[]{130,320,760};
	
	public BasicTower(int pX, int pY) {
		super(pX, pY);

    	setCost(80);
    	setName("Eskimo");
    	setType(BASIC);
    	setDescription("Good range and speed!");
    	
	}
	
	@Override
	public void setImageByLevel(int pLevel) {
		
		switch (pLevel) {
			case 1: setImage(R.drawable.basictower); break;
			case 2: setImage(R.drawable.basictower2);  break;
			case 3: setImage(R.drawable.basictower3);  break;
			case 4: setImage(R.drawable.basictower4);  break;
		}	
	}
	
	@Override
	public boolean upgrade() {
    	
    	if (!canUpgrade())					//return false if tower can't be upgraded
    		return false;
    	else {
    		
    		incLevel();			//increment tower level by one
    		
    		int newLvl = getLevel();
    		setImageByLevel(newLvl);	//set image according to the new level
    		
    		setDamage(sDamage[newLvl-1]);
    		setCoolDown(sCoolDown[newLvl-1]);
    		setRange(sRange[newLvl-1]);

        	return true;
    	}
	}
	
	@Override
	public Projectile shoot() {

		ArrayList<Mob> mobsInRange = new ArrayList<Mob>();

		// loop through the list of mobs
		for (int i=0; i < GameModel.sMobs.size(); i++) {

			Mob m = GameModel.sMobs.get(i);

			double sqrDist = Coordinate.getSqrDistance(this.getCoordinates(), m.getCoordinates());

			// if the mob is in range, add it to list
			if (sqrDist < getRange())
				mobsInRange.add(m);
		}

		//if there are any mobs available to shoot, return a projectile on the first of them, 
		//else return null
		if (!mobsInRange.isEmpty())
			return createProjectile(firstMob(mobsInRange));
		else
			return null;
	}

	@Override
	public Projectile createProjectile(Mob pTarget) {
    	return new BasicProjectile(pTarget, this);
    }

	/**
	 * Returns upgrade cost.
	 * @return the cost to upgrade from current to next level
	 */
    @Override
	public int getUpgradeCost() {
    	return sUpgradeCost[getLevel()-1];
    }
}
