package com.chalmers.game.td;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

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
	private List<Coordinate> 		mPath;
	private static final Path		INSTANCE = new Path();
	private	Context					mContext;
	private List<List<Coordinate>>	mTrackPaths;
	private int						mNumberOfTracks;

	
	/**
	 * Constructor.
	 * 
	 */
	public Path() {
		mPath = new ArrayList<Coordinate>();		
	}
	
	public int getNumberOfTracks() {
		return mNumberOfTracks;
	}
	
	public static Path getInstance() {
		return INSTANCE;
	}
	
	public void setContext(Context pContext) {
		mContext = pContext;

		reset();
		initPath();
	}
	
	/**
	 * 
	 * @param pCoordinates
	 */
	public void setTrackPath(int pTrack) {		
		
		reset();
		
		if(mTrackPaths.get(pTrack-1) != null) {
			
			for(Coordinate c : mTrackPaths.get(pTrack-1)) {

				mPath.add(c);
			}
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
		} catch(IndexOutOfBoundsException iobe) {
			Log.v("GET COORDINATE FOR PATH", "Index out of bounds!");
			return null;
		}
		
	}
	
	/**
	 * 
	 */
	public void initPath() {
		
		String				mTrackNumber;
		String[]			mAllPathCoordinates,
							mPathCoordinates;
		int					mTrackIdentifier;
		List<Coordinate>	mListCoordinates;
		

		mTrackPaths = new ArrayList<List<Coordinate>>();
		
		// Stored outside to keep track
		// of how many tracks there are
		// in the game
		int i;
		
		for(i = 0; ; ++i) {
			
			try {
				
				// Initialize the list of coordinates for each new track
				mListCoordinates = new ArrayList<Coordinate>();
				
				// Set the name of the array to work with
				mTrackNumber = "path_track_" + String.valueOf(i+1);
				// Get identification number for the array to work with
				mTrackIdentifier = mContext.getResources().getIdentifier(mTrackNumber, "array", mContext.getPackageName());
				// Get the array element based on the above given id
				mAllPathCoordinates = mContext.getResources().getStringArray(mTrackIdentifier);
				
				for(int j = 0; j < mAllPathCoordinates.length; ++j) {
					
					// Split the item element to get the x and y coordinates
					mPathCoordinates = mAllPathCoordinates[j].split(" ");
					// Create a new Coordinate object and add it to a list of coordinates
					mListCoordinates.add(new Coordinate(Double.parseDouble(mPathCoordinates[0]), Double.parseDouble(mPathCoordinates[1])));
					
					Log.v("INIT PATH", "Track: " + String.valueOf(i+1) + " X: " + mPathCoordinates[0] + " Y: " + mPathCoordinates[1]);
				}
				

				// Add the new list of coordinates to the track list
				mTrackPaths.add(mListCoordinates);
				
			} catch(NullPointerException npe) {
				// If there are no more tracks, array elements in initpath.xml,
				// there will be a NullPointerException or a NotFoundException thrown, they will be
				// caught and the loop will break.
				Log.v("INITIATION", "Path initiation complete."); 
				break;

			} catch (android.content.res.Resources.NotFoundException nfe) {
				Log.v("INITIATION", "Path initiation complete."); 
				break;

			}
			
		}
		
		mNumberOfTracks = i;
			
		
		
							
	}

	/**
	 * Resets the Path
	 */
	public void reset() {
		mPath = new ArrayList<Coordinate>();
	}


}
