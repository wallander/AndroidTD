package com.chalmers.game.td;


import com.chalmers.game.td.units.Tower;
import com.chalmers.game.td.units.Unit;

public class MovableTower {

	private Tower mTower;
	public int mXPos, mYPos;
	private float speedX,speedY;
	
	public MovableTower(Tower pTower, int pXPos, int pYPos) {
		mTower = pTower;
		mXPos = pXPos;
		mYPos = pYPos;
		speedX = 0f;
		speedY = 0f;
	}
	
	public void update(float timeDelta) {
		// TODO: flytta objektet  med lite tröghet, baserat på onTouchEvents position
		
		speedX = mXPos - (int)mTower.getX();
		speedY = mYPos - (int)mTower.getY();
		
		mTower.setX(mTower.getX() + 10*speedX*timeDelta);
		mTower.setY(mTower.getY() + 10*speedY*timeDelta);
		
	}
	
	public Tower getTower() {
		return mTower;
	}
	
	
}
