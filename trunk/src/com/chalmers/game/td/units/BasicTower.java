package com.chalmers.game.td.units;


import com.chalmers.game.td.R;

public class BasicTower extends Tower {

	public static final int[] sDamage = new int[]{6,16,35,100};
	public static final int[] sCoolDown = new int[]{20,22,24,26};
	public static final int[] sRange = new int[]{100,105,110,110};
	
	public static final int[] sUpgradeCost = new int[]{130,320,780};
	
	public BasicTower(int pX, int pY) {
		super(pX, pY);

    	setCost(70);
    	setName("Eskimo");
    	setDescription("Throws spears with good range and speed");
    	setDamage(7);
	}
	
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
    		
    		setLevel(getLevel()+1);			//increment tower level by one
    		setImageByLevel(getLevel());	//set image according to the new level
    		
    		setDamage(sDamage[getLevel()-1]);
    		setCoolDown(sCoolDown[getLevel()-1]);
    		setRange(sRange[getLevel()-1]);

        	return true;
    	}
	}

	public Projectile createProjectile(Mob pTarget) {
    	return new BasicProjectile(pTarget, this);
    }

	/**
	 * Returns upgrade cost.
	 * @return the cost to upgrade from current to next level
	 */
    public int getUpgradeCost() {
    	return sUpgradeCost[getLevel()-1];
    }

}
