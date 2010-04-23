package com.chalmers.game.td;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.Log;

import com.chalmers.game.td.units.Mob;


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
	private static final MobFactory		INSTANCE = new MobFactory();
	private int							mWaveDelayI,
										mWaveNr,
										mMaxWaveDelay,
										mMobNr,
										mTrackNr;
	private Context						mContext;
	private Path						mPath;
	
	private ArrayList<ArrayList<Mob>>	mTrackWaves;
	private List<Integer>				mWaveNumbers;
	private List<String> 				mMobTypeList;
	
	/**
	 * Should not be used, call getInstance() instead.
	 */
	private MobFactory() {
		//mWaves = null;
		
		
		mContext = null;		
		//mMobs = null;
		mWaveDelayI = 0;
		mWaveNr = 0;
		mMobNr = 0;
		mTrackNr = GameModel.getTrack(); //1-5, Which track currently at
		mTrackWaves = new ArrayList<ArrayList<Mob>>();
		//mWaveNumbers = new ArrayList<Integer>();
		mMaxWaveDelay = 10;

	}

	/**
	 * Returns the number of the wave the player
	 * currently is facing.
	 * 
	 * @return	the current wave number
	 */
	public int getWaveNr() {
		if(hasMoreMobs()) {
			return mWaveNr+1;
		} else {
			return mWaveNr;
		}
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
		//return mWaveNumbers.get(GameModel.getTrack() - 1);
		return mTrackWaves.size();
	}

	/**
	 * Checks if there are more mobs left to
	 * send out. Used to check for completion
	 * of a track.
	 * 
	 * @return		true if there are mobs left, false otherwise
	 */
	public boolean hasMoreMobs() {
		
		//If not on last wave = Mobs left
		if(mWaveNr < mTrackWaves.size()) {
			return true;
		} else if(mWaveNr-1 == mTrackWaves.size()) {
			if(mMobNr < mTrackWaves.get(mWaveNr).size()) {
				Log.d("Jonas2","mMobNr"+mMobNr);
				//If we are on the last wave, but there still are mobs left
				return true;
			} else {
				return false;
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
			
		// If the track is not ended
		if(mWaveNr < mTrackWaves.size()) {
	
			// If the delay is up send next wave, otherwise delay
			if (mWaveDelayI >= mMaxWaveDelay) {
	
				// If the wave is not ended
				if(mMobNr < mTrackWaves.get(mWaveNr).size()) {
				
						
					Log.d("Jonas","Ny Mob" + mMobNr);	
					// Add the mob
					mMob = mTrackWaves.get(mWaveNr).get(mMobNr);
					mMobNr++;
						
					mPath.setTrackPath(GameModel.getTrack());
					mMob.setPath(mPath);
						
					switch(mMob.getType()) {
					case Mob.FAST:
						mMaxWaveDelay = 10;
						Log.i("Delay","FAST");
						break;
					case Mob.HEALTHY:
						mMaxWaveDelay = 30;
						Log.i("Delay","HEALTHY");
						break;
					default:
						mMaxWaveDelay = 10;
						Log.i("Delay","STANDARD");
						break;
					} return mMob;
	
				} else { //if the wave is over
					mWaveDelayI = 0; // Reset delay
					mWaveNr++;
					mMobNr = 0;
					Log.i("Wave","Delay ended, start over");
				}
			} else { // the delay if running
				// If the delay has not reached max yet
				mWaveDelayI++;
				Log.d("Jonas","Delay"+mWaveDelayI);
				
			} 
			//return null;
			
		} else {
			Log.d("Jonas","Track Ended");
			// If the track has no more waves
			// No need to increase, mTrackNr, handled by hasMoreMobs
			//return null;
			mMobNr = 10000;
		}
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
		ArrayList<Mob>		mMobs;
		ArrayList<ArrayList<Mob>>		mWaves;
		String		mTrackNumber;
		String[]	mAllMobs,
		mMobInfo;
		int			mTrackIdentifier,
		mHealth;
		mWaveDelayI = 0;
		mWaveNr = 0;
		mMobNr = 0;
		mTrackNr = GameModel.getTrack(); //1-5, Which track currently at
		mMaxWaveDelay = 10;

		
		//mTrackWaves = new ArrayList<ArrayList<ArrayList<Mob>>>();
		//mMobTypeList = new ArrayList<String>();
		
		for(int i = 0; ; ++i) {						
			// i == track number
			try {
				
				mWaves = new ArrayList<ArrayList<Mob>>();
				
				mTrackNumber = "mobs_track_" + String.valueOf(i+1);
				mTrackIdentifier = mContext.getResources().getIdentifier(mTrackNumber, "array", mContext.getPackageName());
				mAllMobs = mContext.getResources().getStringArray(mTrackIdentifier);

				int j = 0;
				
				for( ; j < mAllMobs.length; ++j) {
					// j == nr of waves
					
					mMobs = new ArrayList<Mob>();
					mMobInfo = mAllMobs[j].split(" ");
					Log.i("INIT MOBS", "Wave:" + i + "/" + j + " MobInfo = " + mMobInfo[0] + " " + mMobInfo[1] + " health:"+mMobInfo[2]);
					
					
					
					mHealth = Integer.parseInt(mMobInfo[2]);
					
					
					for(int k = 0; k < Integer.parseInt(mMobInfo[1]); ++k) {
						// k == nr of mobs
						if(mMobInfo[0].equals("NORMAL")) {

							mMobs.add(new Mob(Mob.NORMAL, mHealth));
							//Log.v("INIT MOBS", "Created mob of type NORMAL");

						} else if(mMobInfo[0].equals("AIR")) {

							mMobs.add(new Mob(Mob.AIR, mHealth));
							//Log.v("INIT MOBS", "Created mob of type AIR");

						} else if(mMobInfo[0].equals("FAST")) {

							mMobs.add(new Mob(Mob.FAST, mHealth));
							//Log.v("INIT MOBS", "Created mob of type FAST");

						} else if(mMobInfo[0].equals("HEALTHY")) {

							mMobs.add(new Mob(Mob.HEALTHY, mHealth));
							//Log.v("INIT MOBS", "Created mob of type HEALTHY");
						} else if(mMobInfo[0].equals("IMMUNE")) {

							mMobs.add(new Mob(Mob.IMMUNE, mHealth));
							//Log.v("INIT MOBS", "Created mob of type IMMUNE");
						}
						
				
						
					}									
					
					mWaves.add(mMobs);
					Log.v("INIT MOBS", "New wave added!");
					
				}
				if(i+1 == GameModel.getTrack()) {
					mTrackWaves = mWaves;
					Log.i("Finished","with wave initiation of "+i+1);
					Log.i("Waves","Amount"+mWaves.size());
				}
			
				
				//Log.i("INFO","mMob/mWave/mTrack"+mMobNr + "/" + mWaveNr + "/" + mTrackNr);
				//Log.i("INFO","WaveSize/MobSize" + mTrackWaves.size() + "/" + mTrackWaves.get(mWaveNr).size());

			} catch(NullPointerException npe) {
				Log.i("INITIATION", "Mobs initiation complete.");
				// Reset mMobs so it will be able to be used at getNextMob()
				mMobNr = 0;
				break;
			} catch(NotFoundException nfe) {
				Log.i("INITIATION", "Mobs initiation complete. No more mobs to load.");
				// Reset mMobs so it will be able to be used at getNextMob()
				mMobNr = 0;
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
			if (mTrackWaves.size() > mWaveNr) {
				return "" + mTrackWaves.get(mWaveNr).get(0).toString();
			} else {
				return "";
			}
	}
	
	/**
	 * Same as getWaveType but returns
	 */
	
	/**
	 * 
	 * @return
	 */
	public static MobFactory getInstance() {
		return INSTANCE;
	}

}