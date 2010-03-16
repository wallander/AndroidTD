package com.chalmers.game.td;

import java.util.ArrayList;
import java.util.List;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.units.Unit;


/**
 * Class that represents the path a mob takes on the game field.
 * It is basically a list of Coordinates, with some fancy methods added.
 * 
 * @author Fredrik Persson
 * @author Jonas Andersson
 * @author Ahmed Chaban
 * @author Jonas Wallander
 * @author Disa Faith
 * @author Daniel Arvidsson
 */
public class Path {
	private List<Coordinate> mPath;
	/*private List<Double> mAngles;*/
	
	/**
	 * Constructor.
	 * 
	 * Currently the path is hard coded here, will be fixed. later. TODO
	 */
	public Path() {
		mPath = new ArrayList<Coordinate>();
		
		mPath.add(new Coordinate(0,140));
		//mPath.add(new Coordinate(120, 140));
		mPath.add(new Coordinate(120, 60));
		mPath.add(new Coordinate(300, 60));
		mPath.add(new Coordinate(300, 250));
		mPath.add(new Coordinate(480, 250));		
	}
	
	/**
	 * Returns the amount of coordinates stored in the Path
	 */
	public int getSize() {
		return mPath.size();
	}
	
	/**
	 * Getter for a certain coordinate of the Path.
	 * The first Coordinate is always where the mob starts.
	 * The last Coordinate is where it should stop.
	 * 
	 * @param index
	 * @return
	 */
	public Coordinate getCoordinate(int index) {
		try {
			return mPath.get(index);
		} catch (Exception e) {
			return new Coordinate(0,0);
		} 
		
	}

}
