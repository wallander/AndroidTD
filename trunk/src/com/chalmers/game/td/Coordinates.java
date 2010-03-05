package com.chalmers.game.td;



/**
 * 	 COORDINATES
 */
public class Coordinates
{
	private double	mXPos,
				mYPos;
	
	/**
	 * Constructor
	 * 
	 * @param pXPos
	 * @param pYPos
	 */
	public Coordinates(double pXPos, double pYPos)
	{
		setXY(pXPos, pYPos);			
	}
	
	/**
	 * Method that returns the angle between two coordinates
	 * 
	 * @param from From-coordinate
	 * @param to To-coordinate
	 * @return Angle
	 */
	public static double getAngle(Coordinates from, Coordinates to) {
		 
		 double x1 = from.getX();
		 double y1 = from.getY();
		 
		 double x2 = to.getX();
		 double y2 = to.getY();
		 
		 if (  x1 < x2 && y1 > y2  ) {
			return ( Math.tan((y1-y2) / (x2-x1)));
			
		} else if (  x1 > x2 && y1 > y2  ) {
			return ( Math.tan( (x1-x2) / (y1-y2)  ) + 0.5*Math.PI);
			
		} else if (  x1 > x2 && y1 < y2  ) { 
			return ( Math.tan( (y2-y1) / (x1-x2) ) + Math.PI);
			
		} else if (  x1 < x2 && y1 < y2  ) { 
			return ( Math.tan( (x2-x1) / (y2-y1)  ) + 1.5*Math.PI);

		}
		 return 0;
		 
		 
		}
	
	// SET POSITIONS
	
	/**
	 * 
	 * @param pXPos
	 * @param pYPos
	 */
	public void setXY(double pXPos, double pYPos)
	{
		setX(pXPos);
		setY(pYPos);
	}
	
	/**
	 * 
	 * @param pXPos
	 */
	public void setX(double pXPos)
	{
		mXPos = pXPos;
	}
	
	/**
	 * 
	 * @param pYPos
	 */
	public void setY(double pYPos)
	{
		mYPos = pYPos;
	}
	
	// GET POSITIONS
	
	/**
	 * @return new double[] {mXPos, mYPos}
	 */
	public double[] getXY()
	{
		return new double[] {mXPos, mYPos};
	}
	
	/**
	 * 
	 * @return double mXPos
	 */
	public double getX()
	{
		return mXPos;
	}
	
	/**
	 * 
	 * @return double mYPos
	 */
	public double getY()
	{
		return mYPos;
	}
}
