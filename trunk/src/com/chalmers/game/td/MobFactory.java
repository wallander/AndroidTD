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
	private Context					mContext;
	private Path					mPath;
	private List<Coordinate>		mCoordinates;
	private Queue<Mob>				mMobs;
	private Queue<Queue<Mob>>		mWaves;
	
	/**
	 * Should not be used, call getInstance() instead.
	 */
	private MobFactory() {
		mWaves = null;
		mContext = null;
		mCoordinates = null;
		mMobs = null;		
	}
		
	public Mob getNextMob() {
		
		if(mMobs == null)
			mMobs = mWaves.poll();				
		
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
		mPath.reset();
		
		mCoordinates = new ArrayList<Coordinate>();
		mWaves = new LinkedList<Queue<Mob>>();
		mMobs = new LinkedList<Mob>();
		
		
		String[]	mPathCoordinates = mContext.getResources().getStringArray(R.array.init_path),
					mCoords,
					mMobTypes = mContext.getResources().getStringArray(R.array.init_mobs),
					mMobInfo;		
		
		// For all coordinates in initwaves.xml
		for(int i = 0; i < mPathCoordinates.length; ++i) {
			mCoords = mPathCoordinates[i].split(" ");	// Split each <item>-element
			mCoordinates.add(new Coordinate(Integer.parseInt(mCoords[0]), Integer.parseInt(mCoords[1])));	// Add coordinates to the list
		}
		
		// TODO Somehow it might be nice to be able to reset or just change path depending on level
		mPath.setTrackPath(mCoordinates);

		for(int i = 0; i < mMobTypes.length; ++i) {
			mMobInfo = mMobTypes[i].split(" ");
			
			for(int j = 0; j < Integer.parseInt(mMobInfo[1]); ++j) {

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
		
	}
		
	/**
	 * 
	 * @return
	 */
	public static MobFactory getInstance() {
		return INSTANCE;
	}
	
	
	
}
