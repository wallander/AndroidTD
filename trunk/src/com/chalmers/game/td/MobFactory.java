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
	//private static final int		MAX_WAVE_DELAY = 10;
	private int						mWaveDelayI,
									mWaveNr,
									mTotalNrOfWaves,
									mMaxWaveDelay;
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
		mMaxWaveDelay = 10;
	}

	/**
	 * Returns the number of the wave the player
	 * currently is facing.
	 * 
	 * @return		the current wave number
	 */
	/*@ public normal_behaviour
	  @ 
	  @ ensures \result == mWaveNr;
	  @*/
	public /*@ pure @*/ int getWaveNr() {
		return mWaveNr;
	}

	/**
	 *  Returns the total amount of waves
	 *  on the track the player is currently
	 *  playing.
	 *  
	 * @return		total number of waves
	 */
	/*@ public normal_behaviour
	  @
	  @ ensures \result == mTotalNrOfWaves;
	  @*/
	public /*@ pure @*/ int getTotalNrOfWaves() {
		return mTotalNrOfWaves;
	}

	/**
	 * Checks if there are more mobs left to
	 * send out. Used to check for completion
	 * of a track.
	 * 
	 * @return		true if there are mobs left, false otherwise
	 */
	/*@ public normal_behaviour
	  @ 
	  @ TODO
	  @	  
	  @*/
	public /*@ pure @*/ boolean hasMoreMobs() {

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
	 * Returns the next mob on top of the queue, 
	 * given the track number.
	 * 
	 * @param	pTrack	TODO make sure the track number is used for waves correctly
	 * @return
	 */
	public Mob getNextMob(int pTrack) {	

		Mob mMob = null;

		if(mWaves != null) {

			if(mMobs == null || mMobs.isEmpty()) {

				if (mWaveDelayI >= mMaxWaveDelay) {
					mWaveDelayI = 0;		
					
					mMobs = mWaves.poll();
					
					if(mMobs != null) 
						++mWaveNr;
				} else {
					//mWaveDelayI += GamePanel.getSpeedMultiplier();
					mWaveDelayI++;
				}
			}

			if(mMobs != null) {

				mMob = mMobs.poll();

				if(mMob != null) {

					mPath.setTrackPath(pTrack);
					mMob.setPath(mPath);
					
					switch(mMob.getType()) {
					case FAST:
						mMaxWaveDelay = 10;
						Log.v("Delay","FAST");
						break;
					case HEALTHY:
						mMaxWaveDelay = 30;
						Log.v("Delay","HEALTHY");
						break;
					default:
						mMaxWaveDelay = 10;
						Log.v("Delay","STANDARD");
						break;
					}

					//Log.v("GET NEXT MOB", "New Mob initialized with path..");
				}
			}

		} else {
			//Log.v("GET NEXT MOB", "No more waves to send!");
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
		mWaveNr = 0;
	}

	/**
	 * Initiate the waves
	 * TODO Somehow solve which track to load waves to
	 */
	private void initWaves() {

		String		mTrackNumber;
		String[]	mAllMobs,
		mMobInfo;
		int			mTrackIdentifier,
		mHealth;

		mWaves = new LinkedList<Queue<Mob>>();		

		for(int i = 0; ; ++i) {

			try {

				mTrackNumber = "mobs_track_" + String.valueOf(i+1);
				mTrackIdentifier = mContext.getResources().getIdentifier(mTrackNumber, "array", mContext.getPackageName());
				mAllMobs = mContext.getResources().getStringArray(mTrackIdentifier);

				for(int j = 0; j < mAllMobs.length; ++j) {

					mMobs = new LinkedList<Mob>();

					mMobInfo = mAllMobs[j].split(" ");

					Log.v("INIT MOBS", "MobInfo = " + mMobInfo[0] + " " + mMobInfo[1] + " health:"+mMobInfo[2]);
					
					mHealth = Integer.parseInt(mMobInfo[2]);
					
					for(int k = 0; k < Integer.parseInt(mMobInfo[1]); ++k) {

						if(mMobInfo[0].equals("NORMAL")) {

							mMobs.add(new Mob(MobType.NORMAL, mHealth));
							Log.v("INIT MOBS", "Created mob of type NORMAL");

						} else if(mMobInfo[0].equals("AIR")) {

							mMobs.add(new Mob(MobType.AIR, mHealth));
							Log.v("INIT MOBS", "Created mob of type AIR");

						} else if(mMobInfo[0].equals("FAST")) {

							mMobs.add(new Mob(MobType.FAST, mHealth));
							Log.v("INIT MOBS", "Created mob of type FAST");

						} else if(mMobInfo[0].equals("HEALTHY")) {

							mMobs.add(new Mob(MobType.HEALTHY, mHealth));
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