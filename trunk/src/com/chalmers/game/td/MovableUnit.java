package com.chalmers.game.td;

import android.view.MotionEvent;

import com.chalmers.game.td.units.Unit;

public class MovableUnit {

	private Unit mUnit;
	private int mXPos, mYPos;
	
	public MovableUnit(Unit pUnit, int pXPos, int pYPos) {
		mUnit = pUnit;
		mXPos = pXPos;
		mYPos = pYPos;
	}
	
	public void update(float timeDelta, MotionEvent event) {
		// TODO: flytta objektet  med lite tröghet, baserat på onTouchEvents position
	}
	
	public Unit getUnit() {
		return mUnit;
	}
	
	public int getX() {
		return mXPos;
	}
	
	public int getY() {
		return mYPos;
	}
}
