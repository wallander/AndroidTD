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

		// TODO Implement...
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
		
		mWaves = new LinkedList<Queue<Mob>>();		
		
		for(int i = 0; ; ++i) {
			
			try {
				
				mTrackNumber = "mobs_track_" + String.valueOf(i+1);
				mTrackIdentifier = mContext.getResources().getIdentifier(mTrackNumber, "array", mContext.getPackageName());
				mAllMobs = mContext.getResources().getStringArray(mTrackIdentifier);
				
				for(int j = 0; j < mAllMobs.length; ++i) {
					
					mMobs = new LinkedList<Mob>();
					
					mMobInfo = mAllMobs[j].split(" ");
					
					for(int k = 0; k < Integer.parseInt(mMobInfo[1]); ++k) {
						
						if(mMobInfo[0].equals("NORMAL")) {
							
							mMobs.add(new Mob(MobType.NORMAL));
							Log.v("INIT MOBS", "Created mob of type NORMAL");
							
						} else if(mMobInfo[0].equals("ARMORED")) {
							
							mMobs.add(new Mob(MobType.ARMORED));
							Log.v("INIT MOBS", "Created mob of type ARMORED");
							
						} else if(mMobInfo[0].equals("FAST")) {
							
							mMobs.add(new Mob(MobType.FAST));
							Log.v("INIT MOBS", "Created mob of type FAST");
							
						} else if(mMobInfo[0].equals("HEALTHY")) {
							
							mMobs.add(new Mob(MobType.HEALTHY));
							Log.v("INIT MOBS", "Created mob of type HEALTHY");
						}											
					}
					
					if(mMobs != null)
						mWaves.add(mMobs);						
					else
						break;
				}
				
			} catch(NullPointerException npe) {
				Log.v("INITIATION", "Mobs initiation complete.");
				break;
			} catch(NotFoundException nfe) {
				Log.v("INITIATION", "Mobs initiation complete. No more mobs to load.");
				break;
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