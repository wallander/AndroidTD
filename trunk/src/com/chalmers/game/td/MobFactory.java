package com.chalmers.game.td;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.content.Context;
import android.util.Log;

import com.chalmers.game.td.units.Mob;
import com.chalmers.game.td.units.Mob.MobType;


/**
 * Factory class that is used to create mobs of different kinds.
 * 
 * @author Fredrik Persson
 * @author Jonas Andersson
 * @author Ahmed Chaban
 * @author Jonas Wallander
 * @author Disa Faith
 * @author Daniel Arvidsson
 */
public class MobFactory {
	
	// Instance variables	
	private static final MobFactory	INSTANCE = new MobFactory();
	private static final int		TRACKS = 2;	// TODO number of tracks is not accurate, depends on how much time we will have left in the end...
	private Context					mContext;
	private Path					mPath;
	private List<Coordinate>		mCoordinates;
	private Queue<Mob>				mMobs;
	private Queue<Queue<Mob>>		mWaves;
	private Queue[]					mTrackWaves;
	
	/**
	 * Should not be used, call getInstance() instead.
	 */
	private MobFactory() {
		mWaves = null;
		mContext = null;
		mCoordinates = null;
		mMobs = null;
		mTrackWaves = new Queue[TRACKS]; 
	}
		
	/**
	 * 
	 * @param pTrack
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Mob getNextMob(int pTrack) {
				
		if(mMobs == null) {
			
			if(pTrack < mTrackWaves.length)
			{
				mMobs = (LinkedList<Mob>)mTrackWaves[pTrack-1].poll();
			} else {
				return null;
			}
		}
									
		if(mMobs != null) {
			return mMobs.poll();
		} else {
			return null;
		}
	
	}
	
	/**
	 * Needed reference to be able to reach initwaves.xml
	 * in resources.
	 * 
	 * @Nextparam pContext
	 */
	public void setContext(Context pContext) {
		mContext = pContext;
		mPath = Path.getInstance();
		initWaves();
	}
	
	/**
	 * Initiate the waves
	 * TODO Somehow solve which track to load waves to
	 */
	private void initWaves() {
		
		// TODO initPath & initWaves instead...
							
		for(int i = 0; i < TRACKS; ++i) {
			mPath.reset();
			
			mCoordinates = new ArrayList<Coordinate>();
			mWaves = new LinkedList<Queue<Mob>>();
			mMobs = new LinkedList<Mob>();
			
			String		mInitPath = "init_path_" + String.valueOf(i+1),
						mInitMob = "init_mob_" + String.valueOf(i+1);
			int			mPathIdentifier = mContext.getResources().getIdentifier(mInitPath, "array", mContext.getPackageName()),
						mMobIdentifier = mContext.getResources().getIdentifier(mInitMob, "array", mContext.getPackageName());
			String[]	mPathCoordinates = mContext.getResources().getStringArray(mPathIdentifier),
						mMobTypes = mContext.getResources().getStringArray(mMobIdentifier),						
						mCoords,
						mMobInfo;
			
			
			// For all coordinates in initwaves.xml
			for(int j = 0; j < mPathCoordinates.length; ++j) {
				mCoords = mPathCoordinates[j].split(" ");	// Split each <item>-element
				mCoordinates.add(new Coordinate(Integer.parseInt(mCoords[0]), Integer.parseInt(mCoords[1])));	// Add coordinates to the list
			}
			
			mPath.setTrackPath(mCoordinates);

			for(int j = 0; j < mMobTypes.length; ++j) {
				mMobInfo = mMobTypes[j].split(" ");
				
				for(int k = 0; k < Integer.parseInt(mMobInfo[1]); ++k) {

					if(mMobInfo[0].equals("NORMAL")) {
						mMobs.add(new Mob(mPath, MobType.NORMAL));
					} else if(mMobInfo[0].equals("ARMORED")) {
						mMobs.add(new Mob(mPath, MobType.ARMORED));					
					} else if(mMobInfo[0].equals("FAST")) {
						mMobs.add(new Mob(mPath, MobType.FAST));					
					} else if(mMobInfo[0].equals("HEALTHY")) {
						mMobs.add(new Mob(mPath, MobType.HEALTHY));					
					}
				}
				
				if(mMobs != null)
					mWaves.add(mMobs);
			}
			
			mTrackWaves[i] = mWaves;

		}
		
	}
		
	/**
	 * 
	 * @return
	 */
	public static MobFactory getInstance() {
		return INSTANCE;
	}
	
	
	
}
