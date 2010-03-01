package com.chalmers.game.td;

/**
 * 
 * @author Jonas Wallander
 *
 */
public class Unit 
{
	/**
	 * 	 
	 */
	private class Coordinates
	{
		private int	mXPos,
					mYPos;
		
		/**
		 * Constructor
		 * 
		 * @param pXPos
		 * @param pYPos
		 */
		public Coordinates(int pXPos, int pYPos)
		{
			setXY(pXPos, pYPos);			
		}
		
		// SET POSITIONS
		
		/**
		 * 
		 * @param pXPos
		 * @param pYPos
		 */
		public void setXY(int pXPos, int pYPos)
		{
			setX(pXPos);
			setY(pYPos);
		}
		
		/**
		 * 
		 * @param pXPos
		 */
		public void setX(int pXPos)
		{
			mXPos = pXPos;
		}
		
		/**
		 * 
		 * @param pYPos
		 */
		public void setY(int pYPos)
		{
			mYPos = pYPos;
		}
		
		// GET POSITIONS
		
		/**
		 * @return new int[] {mXPos, mYPos}
		 */
		public int[] getXY()
		{
			return new int[] {mXPos, mYPos};
		}
		
		/**
		 * 
		 * @return int mXPos
		 */
		public int getX()
		{
			return mXPos;
		}
		
		/**
		 * 
		 * @return int mYPos
		 */
		public int getY()
		{
			return mYPos;
		}
	}
}
