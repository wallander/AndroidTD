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

		
		Mob mMob = null;
		
		if(pTrack < mWaves.size()) {
			
			if(mMobs == null) {
				mMobs = mWaves.poll();
				mPath.setTrackPath(pTrack);
			}
			
			/*if(mMobs != null) {
				mMob = mMobs.poll();	
				
				if(mMob != null)
					mMob.setPath(mPath);
				
				return mMob;
			
			} else {			
				return null;
			}*/
			
		} else {		
			return null;
		}

		
		Log.v("GET NEXT MOB", "Nr of waves: " + mWaves.size());
				
		Log.v("GET NEXT MOB", "Path contains " + mPath.getSize() + " coordinates.");
		
		if(mMobs == null) {
			mMobs = mWaves.poll();
			Log.v("GET NEXT MOB", "Polled a wave from the queue..");
			Log.v("GET NEXT MOB", "Nr of mobs in wave is: " + mMobs.size());
		}
		/*
		if(mMobs != null) {
			Mob m = mMobs.poll();
			Log.v("GET NEXT MOB", "Polled a mob from the wave..");
			if(m != null)
				m.setPath(mPath);
			return m;
		}
		*/
		
		
		Log.v("GET NEXT MOB", "");
		
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
		
		// TODO initPath & initWaves instead...

		mWaves = new LinkedList<Queue<Mob>>();
		

		for(int i = 0; ; ++i) {
			
			try {
				
				Log.v("INIT MOB", "Track nr: " + String.valueOf(i+1));

	//	for(int i = 0; ; i++) {
			
			//try {
				
//				Log.v("INIT MOB", "Track nr: " + String.valueOf(i+1));

			
				
				mMobs = new LinkedList<Mob>();
				
				String		mInitMob = "mobs_track_" + String.valueOf(i+1);

				Log.v("INIT MOB", "Track nr: " + String.valueOf(i+1));

				int			mMobIdentifier = mContext.getResources().getIdentifier(mInitMob, "array", mContext.getPackageName());
				String[]	mMobTypes = mContext.getResources().getStringArray(mMobIdentifier),
							mMobInfo;														
	

				for(int j = 0; j < mMobTypes.length; ++j) {

		//		for(int j = 0; j < mMobTypes.length; j++) {

					mMobInfo = mMobTypes[j].split(" ");
					Log.v("INIT MOB", "mMobInfo contains: " + mMobInfo[0] + " " + mMobInfo[1]);
					Log.v("INIT MOB", "Mobs: " + String.valueOf(j+1) + " / " + String.valueOf(mMobTypes.length));
					
					for(int k = 0; k < Integer.parseInt(mMobInfo[1]); ++k) {
	
						if(mMobInfo[0].equals("NORMAL")) {							
							mMobs.add(new Mob(MobType.NORMAL));
							Log.v("INIT MOB", "Mob of type: NORMAL created");
						} else if(mMobInfo[0].equals("ARMORED")) {
							mMobs.add(new Mob(MobType.ARMORED));
							Log.v("INIT MOB", "Mob of type: ARMORED created");
						} else if(mMobInfo[0].equals("FAST")) {
							mMobs.add(new Mob(MobType.FAST));		
							Log.v("INIT MOB", "Mob of type: FAST created");
						} else if(mMobInfo[0].equals("HEALTHY")) {
							mMobs.add(new Mob(MobType.HEALTHY));
							Log.v("INIT MOB", "Mob of type: HEALTHY created");
						}
					}
					

					if(mMobs != null)
						mWaves.add(mMobs);

					if(mMobs != null) {
						mWaves.add(mMobs);
						Log.v("INIT MOB","Wave nr: " + i + " added.");
					}

				}
				
				
			} catch(NullPointerException npe) {
				// If there are no more tracks, array elements in initwaves.xml,
				// there will be a NullPointerException thrown, it will be
				// caught and the loop will break.
				Log.v("INITIATION", "Mob initiation complete."); 
				break;
			} catch(NotFoundException nfe) {				
				Log.v("INIT MOB", "Mob initiation complete, no more tracks to add.");
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
