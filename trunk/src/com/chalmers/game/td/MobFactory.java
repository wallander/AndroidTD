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
	private int						mWaveDelayI,
									mWaveNr,
									mMaxWaveDelay;
	private Context					mContext;
	private Path					mPath;
	private Queue<Mob>				mMobs;
	private Queue<Queue<Mob>>		mWaves;
	private List<Queue<Queue<Mob>>>	mTrackWaves;
	private List<Integer>			mWaveNumbers;
	private List<String> 			mMobTypeList;
	
	/**
	 * Should not be used, call getInstance() instead.
	 */
	private MobFactory() {
		mWaves = null;
		mTrackWaves = null;
		mContext = null;		
		mMobs = null;
		mWaveDelayI = 0;
		mWaveNr = 0;
		mWaveNumbers = new ArrayList<Integer>();
		mMaxWaveDelay = 10;
	}

	/**
	 * Returns the number of the wave the player
	 * currently is facing.
	 * 
	 * @return		the current wave number
	 */
	public int getWaveNr() {
		return mWaveNr;
	}
	
	public int getWaveMaxDelay() {
		return mMaxWaveDelay;
	}
	
	/**
	 * Returns how many integers there are left
	 * until next wave arrives.
	 * 
	 * @return
	 */
	public int getWaveTime() {
		return (mMaxWaveDelay - mWaveDelayI);
	}

	/**
	 *  Returns the total amount of waves
	 *  on the track the player is currently
	 *  playing.
	 *  TODO FIX WITH ARRAY 
	 * @return		total number of waves
	 */
	public int getTotalNrOfWaves() {
		return mWaveNumbers.get(GameModel.getTrack() - 1);
	}

	/**
	 * Checks if there are more mobs left to
	 * send out. Used to check for completion
	 * of a track.
	 * 
	 * @return		true if there are mobs left, false otherwise
	 */
	public boolean hasMoreMobs() {
		
		if(mWaveNr < mWaveNumbers.get(GameModel.getTrack() - 1)) {
					
			if(mWaves != null && !mWaves.isEmpty()) {
				return true;
			} else {
				if(mMobs != null && !mMobs.isEmpty()) {
					return true;
				} else {					
					return false;
				}
			}
		} else {
			return false;
		}
	}
	
	public void resetWaveNr() {
		mWaveNr = 0;
	}
	
	/**
	 * Returns the next mob on top of the queue, 
	 * given the track number.
	 * 
	 * @param	pTrack	TODO make sure the track number is used for waves correctly
	 * @return
	 */
	public Mob getNextMob() {	

		Mob mMob = null;
		Log.v("JOnas","Gettrack:"+GameModel.getTrack());
		if(mWaveNr <= mWaveNumbers.get(GameModel.getTrack() - 1)) {
			
			if(mWaves == null) {
				mWaves = mTrackWaves.get(GameModel.getTrack());
			} else if(mWaves.isEmpty()) {
				mWaves = null;
				return null;
			}
			
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
	
						mPath.setTrackPath(GameModel.getTrack());
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
				mWaveNr = 0;
			}
	
			if(mMob != null)
				Log.v("GET NEXT MOB", "Mob is now: " + mMob.toString() + " and of type: " + mMob.getType());
			else
				Log.v("GET NEXT MOB", "Mob is now NULL");
		}
		
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
		mTrackWaves = new ArrayList<Queue<Queue<Mob>>>();
		mMobTypeList = new ArrayList<String>();
		
		for(int i = 0; ; ++i) {						
			// i == track number
			try {

				mTrackNumber = "mobs_track_" + String.valueOf(i+1);
				mTrackIdentifier = mContext.getResources().getIdentifier(mTrackNumber, "array", mContext.getPackageName());
				mAllMobs = mContext.getResources().getStringArray(mTrackIdentifier);

				int j = 0;
				
				for( ; j < mAllMobs.length; ++j) {
					// j == nr of waves
					
					mMobs = new LinkedList<Mob>();

					mMobInfo = mAllMobs[j].split(" ");

					Log.v("INIT MOBS", "MobInfo = " + mMobInfo[0] + " " + mMobInfo[1] + " health:"+mMobInfo[2]);
					
					mHealth = Integer.parseInt(mMobInfo[2]);
					
					//if(i == GameModel.getTrack() - 1) {
						mMobTypeList.add(mMobInfo[0]);
					//}
					
					for(int k = 0; k < Integer.parseInt(mMobInfo[1]); ++k) {
						// k == nr of mobs
						if(mMobInfo[0].equals("NORMAL")) {

							mMobs.add(new Mob(MobType.NORMAL, mHealth));
							//Log.v("INIT MOBS", "Created mob of type NORMAL");

						} else if(mMobInfo[0].equals("AIR")) {

							mMobs.add(new Mob(MobType.AIR, mHealth));
							//Log.v("INIT MOBS", "Created mob of type AIR");

						} else if(mMobInfo[0].equals("FAST")) {

							mMobs.add(new Mob(MobType.FAST, mHealth));
							//Log.v("INIT MOBS", "Created mob of type FAST");

						} else if(mMobInfo[0].equals("HEALTHY")) {

							mMobs.add(new Mob(MobType.HEALTHY, mHealth));
							//Log.v("INIT MOBS", "Created mob of type HEALTHY");
						}											
					}									
					
					mWaves.add(mMobs);
					Log.v("INIT MOBS", "New wave added!");
					
				}
				
				mWaveNumbers.add(j);
				mTrackWaves.add(mWaves);

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
	 * Peeks the Queue of Mobs to see what the type of the next wave is
	 * Only to be used in between waves
	 * @return Type of the next mob
	 */
	public String getWaveType() {
		if(mMobTypeList.size() > getWaveNr()) {
			return "" + mMobTypeList.get(getWaveNr());
		}
		return "-";
	}
	
	/**
	 * 
	 * @return
	 */
	public static MobFactory getInstance() {
		return INSTANCE;
	}



}