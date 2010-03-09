package com.chalmers.game.td;



/**
 * Internal class that represents a coordinate on the gamefield.
 * 
 * @author Fredrik Persson
 * @author Jonas Andersson
 * @author Ahmed Chaban
 * @author Jonas Wallander
 * @author Disa Faith
 * @author Daniel Arvidsson
 */
public class Coordinates
{
	private double	mXPos,
				mYPos;
	
	/**
	 * Constructor
	 * 
	 * @param pXPos Position on the X-axis
	 * @param pYPos Position on the Y-axis
	 */
	public Coordinates(double pXPos, double pYPos)
	{
		setXY(pXPos, pYPos);			
	}
	
	/**
	 * Method that returns the angle between two coordinates in radians.
	 * 
	 * Angle 0 represents a line moving from left to right on the X-axis.
	 * Angle Math.PI represents a line moving from right to left on the X-axis.
	 * 
	 * Angle 0.5 * Math.PI represents a line moving from down to up on the Y-axis
	 * Angle 1.5 * Math.PI represents a line moving from up to down on the Y-axis
	 * 
	 * @param from From-coordinate
	 * @param to To-coordinate
	 * @return Angle in radians [0, 2 * Math.PI]
	 */
	public static double getAngle(Coordinates from, Coordinates to) {
		 
		 double x1 = from.getX();
		 double y1 = from.getY();
		 
		 double x2 = to.getX();
		 double y2 = to.getY();
		 
		 
		 if (x1 < x2 && y1 >= y2) {
			 // if "to" is in the first quadrant of "from"
			return Math.atan((y1-y2) / (x2-x1));
		} else if (x1 >= x2 && y1 > y2) {
			// if "to" is in the second quadrant of "from"
			return Math.atan((x1-x2) / (y1-y2)) + 0.5*Math.PI;
		} else if (x1 > x2 && y1 <= y2) { 
			// if "to" is in the third quadrant of "from"
			return Math.atan((y2-y1) / (x1-x2)) + Math.PI;
		} else if (x1 <= x2 && y1 < y2) { 
			// if "to" is in the fourth quadrant of "from"
			return Math.atan((x2-x1) / (y2-y1)) + 1.5*Math.PI;
		} else {
			/*
			// if "to" and "from" are on the same place on the X-axis
			if (x1 == x2 && y1 < y2)
				return 1.5*Math.PI;
			if (x1 == x2 && y1 > y2)
				return 0.5*Math.PI;
			
			// if "to" and "from" are on the place on the Y-axis
			if (y1 == y2 && x1 < x2)
				return 0;
			if (y1 == y2 && x1 > x2)
				return Math.PI;
				*/
		}

		 // if "to" and "from" are on the exact same spot
		 return 0;
		 
		 
		}
	
	// SET POSITIONS
	
	/**
	 * Setter for X- and Y-positions
	 * 
	 * @param pXPos X-position
	 * @param pYPos Y-position
	 */
	public void setXY(double pXPos, double pYPos) {
		setX(pXPos);
		setY(pYPos);
	}
	
	/**
	 * Setter for the X-position
	 * 
	 * @param pXPos
	 */
	public void setX(double pXPos) {
		mXPos = pXPos;
	}
	
	/**
	 * Setter for the Y-position
	 * 
	 * @param pYPos
	 */
	public void setY(double pYPos) {
		mYPos = pYPos;
	}
	
	// GET POSITIONS
	
	/**
	 * Getter for X- and Y-positions
	 * 
	 * @return new double[] {mXPos, mYPos}
	 */
	public double[] getXY() {
		return new double[] {mXPos, mYPos};
	}
	
	/**
	 * Getter for the X-position
	 * 
	 * @return double mXPos
	 */
	public double getX() {
		return mXPos;
	}
	
	/**
	 * Getter for the Y-position
	 * 
	 * @return double mYPos
	 */
	public double getY() {
		return mYPos;
	}
}
