package com.chalmers.game.td.units;

import java.util.List;

import android.hardware.SensorEvent;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.GameModel;

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
	
	/** damage */
	protected int mDamage;
	
	/** Projectile movement angle */
	protected double mAngle;
    
	private int mCharges;

	
	/**
     * Constructor.
     * 
     * @param bitmap Bitmap which should be drawn.
     */
    public Snowball(int pXPos, int pYPos) {
    	setCoordinates(new Coordinate(pXPos,pYPos));

        setSpeedX(0);
        setSpeedY(0);
        setAngle(0);
        setDamage(1);
        setCharges(10);

		
    }
    
    public double getAngle() {
		return mAngle;
	}


	public double getSpeedX() {
        return mSpeedX;
    }

	public double getSpeedY() {
        return mSpeedY;
    }
	
	/**
     * Method used for collision detection
     * @return
     */
//	public boolean hasCollided() {
//		
//		Coordinate targetCoordinate = new Coordinate(getMob().getX() + getMob().getWidth()/2, getMob().getY()  + getMob().getHeight()/2);
//		
//		
//		double sqrDist = Coordinate.getSqrDistance(getCoordinates(), targetCoordinate);
//		
//		if (sqrDist < getSpeed())
//			return true;
//		
//		return false;
//	}
    
    /**
     * @param mMobs 
     * 
     */
    public void inflictDmg(List<Mob> mMobs) {
//       mTarget.setHealth(mTarget.getHealth() - mDamage);

    }
    

    public void setAngle(double mAngle) {
		this.mAngle = mAngle;
	}
    
	private void setDamage(int i) {
		// TODO Auto-generated method stub
		mDamage = i;
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

		
		setSpeedX(getSpeedX() + (x/10));
		setSpeedY(getSpeedY() + (y/10));
		
//		setSpeedX(getSpeedX() + x);
//		setSpeedY(getSpeedY() + y);
		
		if ((getX() < 0 && getSpeedX() < 0 )|| (getX() > 480 && getSpeedX() > 0))
			setSpeedX(-getSpeedX()*0.8);

		if ((getY() < 0 && getSpeedY() < 0 )|| (getY() > 320 && getSpeedY() > 0))
			setSpeedY(-getSpeedY()*0.8);
		
		
		setX(getX() + getSpeedX());
		setY(getY() + getSpeedY());
		
		lastUpdate = s;
		
	}

	public void setCharges(int mCharges) {
		this.mCharges = mCharges;
	}

	public int getCharges() {
		return mCharges;
	}


}
