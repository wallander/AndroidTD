package com.chalmers.game.td.units;

import android.util.Log;

import com.chalmers.game.td.R;

public class BasicTower extends Tower {

	
	
	public BasicTower(int pX, int pY) {
		super(pX, pY);
    	setName("Eskimo");
    	setDescription("Throws spears with good range and speed");
    	setDamage(7);
    	setPrio(2);
    
    	setType(TowerType.NORMAL);
    	
	}
	
	
	public void setImageByLevel(int pLevel) {
		
		switch (pLevel) {
			case 1: setImage(R.drawable.basictower); break;
			case 2: setImage(R.drawable.basictower2);  break;
			case 3: setImage(R.drawable.basictower3);  break;
			case 4: setImage(R.drawable.basictower4);  break;
		}	
	}
	
	public boolean upgrade() {
    	
    	if (!canUpgrade())					//return false if tower can't be upgraded
    		return false;
    	else {
    		setLevel(getLevel()+1);			//increment tower level by one
    		setImageByLevel(getLevel());	//set image according to the new level
    		
    		switch (getLevel()){			//set damage and range according to the new level
    		case 2:
    			setDamage(16);
        		setRange(105);
        		break;
    		case 3:
    			setDamage(40);
        		setRange(110);
        		break;
    		case 4:
    			setDamage(120);
        		setRange(120);
        		break;
    		}
    		
    		//Old values, kept for reference
//    		switch (getLevel()){			//set damage and range according to the new level
//    		case 2:
//    			setDamage(16);
//        		setRange(110);
//    		case 3:
//    			setDamage(40);
//        		setRange(125);
//    		case 4:
//    			setDamage(120);
//        		setRange(140);
//    		}
    	}
    	return true;
	}

	public Projectile createProjectile(Mob pTarget) {
    	return new BasicProjectile(pTarget, this);
    }



}
