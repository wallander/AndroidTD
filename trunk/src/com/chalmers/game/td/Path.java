package com.chalmers.game.td;

import java.util.ArrayList;
import java.util.List;

import com.chalmers.game.td.Coordinates;
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
	private List<Coordinates> mPath;
	private List<Double> mAngles;
	
	/**
	 * Constructor.
	 * 
	 * Currently the path is hard coded here, will be fixed. later. TODO
	 */
	public Path() {
		mPath = new ArrayList<Coordinates>();
		
		mPath.add(new Coordinates(140, 0));
		mPath.add(new Coordinates(140, 120));
		mPath.add(new Coordinates(60, 120));
		mPath.add(new Coordinates(60, 300));
		mPath.add(new Coordinates(250, 300));
		mPath.add(new Coordinates(250, 480));
		
		
	}
	
	/**
	 * Getter for a certain coordinate of the Path.
	 * The first Coordinate is always where the mob starts.
	 * The last Coordinate is where it should stop.
	 * 
	 * @param index
	 * @return
	 */
	public Coordinates getCoordinate(int index) {
		try {
			return mPath.get(index);
		} catch (Exception e) {
			return new Coordinates(0,0);
		} 
		
	}

}