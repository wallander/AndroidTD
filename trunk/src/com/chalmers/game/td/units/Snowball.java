package com.chalmers.game.td.units;

import java.util.ArrayList;
import java.util.List;
import android.hardware.SensorEvent;
import com.chalmers.game.td.Coordinate;

/**
 * This class represents a snowball that can be released in the game field
 * to do inflict damage on mobs.
 * 
 * Will only be available if the phone running the game supports use of accelerometer.
 * 
 * @author Fredrik
 *
 */
public class Snowball extends Unit {


	/** movement speed */
	protected double mSpeedX;
	protected double mSpeedY;

	private SensorEvent lastUpdate;

	private int mCharges;
	private boolean mSlowed;


	/**
	 * Constructor.
	 * 
	 * @param bitmap Bitmap which should be drawn.
	 */
	public Snowball(int pXPos, int pYPos) {
		setCoordinates(new Coordinate(pXPos,pYPos));

		setSpeedX(0);
		setSpeedY(0);
		setCharges(10);


	}


	public double getSpeedX() {
		return mSpeedX;
	}

	public double getSpeedY() {
		return mSpeedY;
	}

	/**
	 * @param mMobs 
	 * 
	 */
	public List<Mob> getCollidedMobs(List<Mob> mMobs) {

		List<Mob> deadMobs = new ArrayList<Mob>();

		for (int i = 0; i < mMobs.size() && getCharges() > 0; i++) {
			Mob m = mMobs.get(i);

			Coordinate mobCoordinate = new Coordinate(m.getX()+m.getWidth()/2,m.getY()+m.getHeight()/2);

			double distance = Coordinate.getSqrDistance(this.getCoordinates(), mobCoordinate);

			if (distance < 10 + getCharges() + m.getHeight()/2) {
				deadMobs.add(m);
				setCharges(getCharges() - 1);
			}

		}
		return deadMobs;
	}




	private void setSpeedX(double i) {
		mSpeedX = i;
	}

	private void setSpeedY(double i) {
		mSpeedY = i;
	}

	/**
	 * Uses accelerometer to change position of the ball
	 */
	public void updatePosition(SensorEvent s) {
		if (lastUpdate == null) {
			lastUpdate = s;
			return;
		}


		double x = s.values[1];
		double y = s.values[0];
		
		setSpeedX(getSpeedX() + x / 25);
		setSpeedY(getSpeedY() + y / 25);
		
		if ((getX() < 0 && getSpeedX() < 0)	|| (getX() > 480 && getSpeedX() > 0)) {
			setSpeedX(-getSpeedX() * 0.8);
			mCharges--;
		}
		
		if ((getY() < 0 && getSpeedY() < 0) || (getY() > 320 && getSpeedY() > 0)) {
			setSpeedY(-getSpeedY() * 0.8);
			mCharges--;
		}
		
		// if the snowball is slowed, move slower
		if (!isSlowed()) {
			setX(getX() + getSpeedX());
			setY(getY() + getSpeedY());
		} else {
			setX(getX() + getSpeedX()*0.2);
			setY(getY() + getSpeedY()*0.2);
		}
			
		lastUpdate = s;
	}

	public void setCharges(int mCharges) {
		this.mCharges = mCharges;
	}

	public int getCharges() {
		return mCharges;
	}


	public void setSlowed(boolean b) {
		mSlowed = b;
	}

	public boolean isSlowed() {
		return mSlowed;
	}

}