package com.chalmers.game.td;

import java.util.List;

/**
 * 
 * @author Jonas Andersson, Daniel Arvidsson, Ahmed Chaban, Disa Faith, Fredrik Persson, Jonas Wallander
 *
 */
public class Path {
	private List<Coordinates> mPath;
	private List<Double> mAngles;
	
	public void Path() {
		mPath.add(new Coordinates(160, 0));
		mPath.add(new Coordinates(160, 120));
		mPath.add(new Coordinates(80, 120));
		mPath.add(new Coordinates(80, 300));
		mPath.add(new Coordinates(350, 300));
		mPath.add(new Coordinates(350, 500));
		
		mAngles.add(Math.PI * 1.5);
		mAngles.add(Math.PI);
		mAngles.add(Math.PI * 1.5);
		mAngles.add(0.0);
		mAngles.add(Math.PI * 1.5);
		
		/*
		for (int i = 0; i < mPath.size()-1; i++){
			mAngles.add();
		}*/
		
	}
	
	
	public class Coordinates{
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
