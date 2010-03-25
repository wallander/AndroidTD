package com.chalmers.game.td.units;

import com.chalmers.game.td.R;

/**
 * This class extends the Tower class. It shoots splashprojectiles instead of projectiles.
 * 
 *
 */
public class SplashTower extends Tower {

	public SplashTower(int mX, int mY) {
		super(mX, mY);
		setImage(mLevel);
	}

	public String getName() {
		return "Splash Tower";
	}
	
	// Temporary changes images up to 4 upgrades.
	public void setImage(int img) {
		if(img == 1){
			mImage = R.drawable.splashtower;	
		} else if(img == 2){
			mImage = R.drawable.splashtower2;
		} else if(img == 3){
			mImage = R.drawable.splashtower3;
		} else {
			mImage = R.drawable.splashtower4;
		}
		
	}
}
