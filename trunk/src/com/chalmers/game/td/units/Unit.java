package com.chalmers.game.td.units;

import com.chalmers.game.td.Coordinate;

/**
 * Abstract class that represents a unit on the game field.
 * 
 * Right now this handles the coordinates of the unit.
 * 
 * @author Fredrik Persson
 * @author Jonas Andersson
 * @author Ahmed Chaban
 * @author Jonas Wallander
 * @author Disa Faith
 * @author Daniel Arvidsson
 */
public abstract class Unit {
	
	// INSTANCE VARIABLES
	private	Coordinate	mCoordinates;
	protected	int			mWidth,
							mHeight;
	
	public Coordinate getCoordinates() {
		return mCoordinates;
	}
	
	public double getX() {
		return mCoordinates.getX();
	}
	
	public double getY() {
		return mCoordinates.getY();
	}
	
	public void setCoordinates(Coordinate pCoordinates) {
		mCoordinates = new Coordinate(pCoordinates.getX(), pCoordinates.getY());
	}
	
	public void setX(double pXPos) {
		mCoordinates.setX(pXPos);
	}
	
	public void setY(double pYPos) {
		mCoordinates.setY(pYPos);
	}
	public void setHeight(int pHeight) {
		mHeight = pHeight;
	}
	public void setWidth(int pWidth) {
		mWidth = pWidth;
	}
	public int getHeight() {
		return mHeight;
	}
	public int getWidth() {
		return mWidth;
	}
	public void setSize(int pSize) {
		mHeight = pSize;
		mWidth = pSize;
	}
	
	public abstract void update(float timeDelta);
}

