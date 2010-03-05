package com.chalmers.game.td;

import java.util.ArrayList;
import java.util.List;

import com.chalmers.game.td.Coordinates;


/**
 * 
 * @author Fredrik Persson
 * @author Jonas Andersson
 * @author Ahmed Chaban
 * @author Jonas Wallander
 * @author Disa Faith
 * @author Daniel Arvidsson
 */
public class Path {
	private List<Coordinates> mPath;
	private List<Double> mAngles;
	
	public Path() {
		
		mPath = new ArrayList<Coordinates>();
		
		mPath.add(new Coordinates(160, 0));
		mPath.add(new Coordinates(160, 120));
		mPath.add(new Coordinates(80, 120));
		mPath.add(new Coordinates(80, 300));
		mPath.add(new Coordinates(350, 300));
		mPath.add(new Coordinates(350, 500));
		
		/*mAngles.add(Math.PI * 1.5);
		mAngles.add(Math.PI);
		mAngles.add(Math.PI * 1.5);
		mAngles.add(0.0);
		mAngles.add(Math.PI * 1.5);
		*/
		
		
	}
	
	public Coordinates getCoordinate(int index) {
		try {
			return mPath.get(index);
		} catch (Exception e) {
			return new Coordinates(0,0);
		} 
		
	}

}
