package com.chalmers.game.td;

import java.util.ArrayList;
import java.util.List;

import com.chalmers.game.td.Coordinate;


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
	private List<Coordinate> 	mPath;
	private static final Path	INSTANCE = new Path();
	private static boolean initialized = false;
	
	/**
	 * Constructor.
	 * 
	 */
	public Path() {
		mPath = new ArrayList<Coordinate>();	
	}
	
	public static Path getInstance() {
		return INSTANCE;
	}
	
	
	
	public void setTrackPath(List<Coordinate> pCoordinates) {
		for(Coordinate c : pCoordinates) {
			mPath.add(c);
		}
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
			return null;
		} 
		
	}

	public static void setInitialized(boolean initialized) {
		Path.initialized = initialized;
	}

	public static boolean isInitialized() {
		return initialized;
	}

}
