package com.chalmers.game.td;

import java.util.LinkedList;
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
	private static final int		MAX_WAVE_DELAY = 10;
	private int						mWaveDelayI,
									mWaveNr,
									mTotalNrOfWaves;
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
		mWaveDelayI = 0;
		mWaveNr = 0;
		mTotalNrOfWaves = 0;
	}
	
	/**
	 * Returns the current wave number
	 * 
	 * @return
	 */
	public int getWaveNr() {
		return mWaveNr;
	}
	
	/**
	 *  Returns the total number of waves
	 * @return
	 */
	public int getTotalNrOfWaves() {
		return mTotalNrOfWaves;
	}
	
	/**
	 * Check if there are more mobs left
	 * @return
	 */
	public boolean hasMoreMobs() {
		
		if(mWaves != null && !mWaves.isEmpty()) {
			return true;
		} else {
			if(mMobs != null && !mMobs.isEmpty()) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * 
	 * @param pTrack
	 * @return
	 */
	public Mob getNextMob(int pTrack) {	
			
			Mob mMob = null;
			
			if(mWaves != null) {
				
				if(mMobs == null || mMobs.isEmpty()) {
					
					switch(mWaveDelayI) {
						
						case MAX_WAVE_DELAY:
							mWaveDelayI = 0;							
							mMobs = mWaves.poll();
														
							if(mMobs != null) 
								++mWaveNr;
							
						break;
						
						default:
							++mWaveDelayI;
						break;
					}							
				}
												
				if(mMobs != null) {
										
					mMob = mMobs.poll();
					
					if(mMob != null) {
						
						mPath.setTrackPath(pTrack);
						mMob.setPath(mPath);
						
						Log.v("GET NEXT MOB", "New Mob initialized with path..");
					}
				}
				
			} else {
				Log.v("GET NEXT MOB", "No more waves to send!");
				mWaveNr = 0; // Reset the wave number
			}
			
			if(mMob != null)
				Log.v("GET NEXT MOB", "Mob is now: " + mMob.toString() + " and of type: " + mMob.getType());
			else
				Log.v("GET NEXT MOB", "Mob is now NULL");
			
			return mMob;						
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
		mTotalNrOfWaves = mWaves.size();
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
				
				for(int j = 0; j < mAllMobs.length; ++j) {
					
					mMobs = new LinkedList<Mob>();
					
					mMobInfo = mAllMobs[j].split(" ");
					
					Log.v("INIT MOBS", "MobInfo = " + mMobInfo[0] + " " + mMobInfo[1]);
					
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
														
					mWaves.add(mMobs);
					Log.v("INIT MOBS", "New wave added!");
				}
				
			} catch(NullPointerException npe) {
				Log.v("INITIATION", "Mobs initiation complete.");
				// Reset mMobs so it will be able to be used at getNextMob()
				mMobs = null;
				break;
			} catch(NotFoundException nfe) {
				Log.v("INITIATION", "Mobs initiation complete. No more mobs to load.");
				// Reset mMobs so it will be able to be used at getNextMob()
				mMobs = null;
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