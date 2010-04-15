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
		setName("Snowman");		
		setCost(200);
		setDescription("Little damage. Slows down units.");
		setCoolDown(30);
    	resetCoolDown();
		setRange(60);
		setDamage(15);
		setSlow(30);
		setPrio(Tower.NOT_SLOWED);
		setType(TowerType.SLOW);
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
    			setDamage(20);
        		setRange(75);
        		setSlow(40); break;
    		case 3:
    			setDamage(25);
        		setRange(75);
        		setSlow(50); break;
    		case 4:
    			setDamage(30);
        		setRange(75);
        		setCoolDown(25);
        		setSlow(60); break;
    		}
    	}
    	return true;
	}
    
    /**
     * returns the current upgrade cost
     */
	public int getUpgradeCost() {
//TODO ändra till ökande kostnader
		switch(getLevel()) {
		case 1: return 200;
		case 2: return 200;
		default: return 200; //case 3
		}
	}

	@Override
	public Projectile shoot() {

		ArrayList<Mob> mobsInRange = new ArrayList<Mob>();
		
		// loop through the list of mobs
		for (int i=0; i < GameModel.mMobs.size(); i++) {

			Mob m = GameModel.mMobs.get(i);

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
