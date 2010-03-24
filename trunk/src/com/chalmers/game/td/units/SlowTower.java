package com.chalmers.game.td.units;

import com.chalmers.game.td.R;

public class SlowTower extends Tower {

	public SlowTower(int mX, int mY) {
		super(mX, mY);
		setImage(R.drawable.big);
	}

	
	public String getName() {
		return "Slowing Tower";
	}
}
