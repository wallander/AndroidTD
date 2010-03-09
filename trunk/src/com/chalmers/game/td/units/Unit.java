package com.chalmers.game.td.units;

import com.chalmers.game.td.Coordinates;

/**
 * 
 * @author Jonas Andersson, Daniel Arvidsson, Ahmed Chaban, Disa Faith, Fredrik Persson, Jonas Wallander
 *
 */
public abstract class Unit 
{
	// INSTANCE VARIABLES
	private	Coordinates	mCoordinates;
	protected	int			mWidth,
							mHeight;
	
	public Coordinates getCoordinates() {
		return mCoordinates;
	}
	
	public double getX() {
		return mCoordinates.getX();
	}
	
	public double getY() {
		return mCoordinates.getY();
	}
	
	public void setCoordinates(Coordinates pCoordinates) {
		mCoordinates = pCoordinates;
	}
	
	public void setX(double pXPos) {
		mCoordinates.setX(pXPos);
	}
	
	public void setY(double pYPos) {
		mCoordinates.setY(pYPos);
	}
}
