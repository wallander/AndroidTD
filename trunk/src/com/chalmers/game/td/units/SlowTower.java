package com.chalmers.game.td.units;

import com.chalmers.game.td.R;

public class SlowTower extends Tower {

	public SlowTower(int mX, int mY) {
		super(mX, mY);
		setImage(mLevel);
	}

	
	public String getName() {
		return "Slowing Tower";
	}
	
	// Temporary changes images up to 4 upgrades.
	public void setImage(int img) {
		if(img == 1){
			mImage = R.drawable.slowtower;	
		} else if(img == 2){
			mImage = R.drawable.slowtower2;
		} else if(img == 3){
			mImage = R.drawable.slowtower3;
		} else {
			mImage = R.drawable.slowtower4;
		}
		
	}
}
