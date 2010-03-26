package com.chalmers.game.td;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
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
	private Queue<Mob>				mMobs;
	private Queue<Queue<Mob>>		mWaves;
	
	/**
	 * Should not be used, call getInstance() instead.
	 */
	private MobFactory() {
		mWaves = null;
		mContext = null;		
		mMobs = null;
	}
		
	/**
	 * 
	 * @param pTrack
	 * @return
	 */
	public Mob getNextMob(int pTrack) {

		
		return null;

	
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
		mPath.setContext(pContext);
		initWaves();
	}
	
	/**
	 * Initiate the waves
	 * TODO Somehow solve which track to load waves to
	 */
	private void initWaves() {
		
		String		mTrackNumber;
		String[]	mAllMobs,
					mMobInfo;
		int			mTrackIdentifier;
		
		for(int i = 0; ; ++i) {
			
			try {
				
				mTrackNumber = "mobs_track_" + String.valueOf(i+1);
				mTrackIdentifier = mContext.getResources().getIdentifier(mTrackNumber, "array", mContext.getPackageName());
				mAllMobs = mContext.getResources().getStringArray(mTrackIdentifier);
				
				for(int j = 0; j < mAllMobs.length; ++i) {
					
					mMobInfo = mAllMobs[j].split(" ");
					
					for(int k = 0; k < Integer.parseInt(mMobInfo[1]); ++k) {
						
						if(mMobInfo.equals("NORMAL")) {
							
							mMobs.add(new Mob(MobType.NORMAL));
							Log.v("INIT MOBS", "Created mob of type NORMAL");
							
						} else if(mMobInfo.equals("ARMORED")) {
							
							mMobs.add(new Mob(MobType.NORMAL));
							Log.v("INIT MOBS", "Created mob of type ARMORED");
							
						} else if(mMobInfo.equals("FAST")) {
							
							mMobs.add(new Mob(MobType.NORMAL));
							Log.v("INIT MOBS", "Created mob of type FAST");
							
						} else if(mMobInfo.equals("HEALTHY")) {
							
							mMobs.add(new Mob(MobType.NORMAL));
							Log.v("INIT MOBS", "Created mob of type HEALTHY");
						}
					}
					
				}
				
			} catch(NullPointerException npe) {
				
			}
			
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