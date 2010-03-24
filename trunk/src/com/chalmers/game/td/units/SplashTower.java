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
		setImage(R.drawable.rock2);
	}

	public String getName() {
		return "Splash Tower";
	}
}
