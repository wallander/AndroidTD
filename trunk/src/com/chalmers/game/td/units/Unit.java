package com.chalmers.game.td.units;

/**
 * 
 * @author Jonas Wallander
 *
 */
public class Unit 
{
	// INSTANCE VARIABLES
	public	Coordinates	mCoordinates;
	protected	int			mWidth,
							mHeight;
	
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
}
