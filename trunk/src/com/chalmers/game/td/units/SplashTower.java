package com.chalmers.game.td.units;


import java.util.ArrayList;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.R;

/**
 * This class extends the Tower class. It shoots splash projectiles instead of projectiles,
 * and cannot attack flying mobs.
 *
 */
public class SplashTower extends Tower {
	
	//
	private int mSplash;
	private int mSplashRadius;

	public static final float[] sCoolDown = new float[]{(float)2.3,(float)2.3,(float)2.3,(float)2.3};
	public static final int[] sDamage = new int[]{10,30,80,200};
	public static final int[] sRange = new int[]{60,70,70,80};
	
	public static final int[] sSplashRadius = new int[]{50,60,70,70};
	
	public static final int[] sUpgradeCost = new int[]{150,330,700};

	public SplashTower(int pX, int pY) {
		super(pX, pY);
		setName("Splash Eskimo");
		setDescription("Can hit many mobs at once!");
		setType(Tower.SPLASH);
		resetCoolDown();
		setCost(80);
	}
	
	// Temporary changes images up to 4 upgrades.
	@Override
	public void setImageByLevel(int pLevel) {

		switch (pLevel) {
			case 1: setImage(R.drawable.eskimotowersplash); break;
			case 2: setImage(R.drawable.eskimotowersplash2); break;
			case 3: setImage(R.drawable.eskimotowersplash3); break;
			case 4: setImage(R.drawable.eskimotowersplash4); break;
		}		
	}

    /**
     * Method that returns a Projectile set to target the first mob
     * in the given list of mobs that the tower can reach.
     * 
     * @param mobs List of mobs for the tower to target
     * @return Projectile set to target the first mob the tower can reach.
     */
    
    @Override
	public Projectile createProjectile(Mob pTarget) {
    	return new SplashProjectile(pTarget, this, mSplashRadius);
    }
    
    @Override
	public Projectile shoot() {

		ArrayList<Mob> mobsInRange = new ArrayList<Mob>();

		// loop through the list of mobs
		for (int i=0; i < GameModel.sMobs.size(); i++) {

			Mob m = GameModel.sMobs.get(i);

			double sqrDist = Coordinate.getDistance(this.getCoordinates(), m.getCoordinates());

			// if the mob is in range, add it to list
			if (sqrDist < getRange() && m.getType() != Mob.AIR && m.isDead() == false)
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
	public boolean upgrade() {
    	if (!canUpgrade())					//return false if tower can't be upgraded
    		return false;
    	else {
    		incLevel();						//increment tower level by one
    		
    		int newLvl = getLevel();
    		setImageByLevel(newLvl);		//set image according to the new level
    		
    		setCoolDown(sCoolDown[newLvl-1]);
			setDamage(sDamage[newLvl-1]);
			setRange(sRange[newLvl-1]);
			setSplashRadius(sSplashRadius[newLvl-1]);

	    	return true;
    	}
	}
    
    private void setSplashRadius(int mRadius) {
    	mSplashRadius = mRadius;
    }
    
    @Override
	public int getSplashRadius() {
    	return mSplashRadius;
    }
    
    /**
     * Returns the splasheffect
     * @return Splasheffect
     */
    public int getSplash() {
    	return mSplash;
    }

    //returns the cost to level from current level to current level +1
	@Override
	public int getUpgradeCost() {
		return sUpgradeCost[getLevel()-1];
	}
}
