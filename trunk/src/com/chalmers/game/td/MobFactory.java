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
	private int							mWaveDelayI;
	private int							mWaveNr,		//first wave is number 1?
										mMaxWaveDelay,
										mMobNr,			//first mob is number 1?
										mTrackNr;
	private Context						mContext;
	private Path						mPath;
	
	private ArrayList<ArrayList<Mob>>	mTrackWaves;	//all the waves of a track
//	private List<Integer>				mWaveNumbers;
	private List<String> 				mMobTypeList;
	
	/**
	 * Array containing mobs for a specific wave.
	 */
	private	ArrayList<Mob>				mMobs;
	
	/**
	 * One ArrayList with mobs for each wave in the current track.
	 */
	private ArrayList<ArrayList<Mob>>	mWaves;
	private String						mTrackName;
	
	/**
	 * Will contain one string with info for each wave.
	 */
	private String[]					mAllWaves;
	
	/**
	 * String array containing info of the mobs in a specific wave. Will contain three strings. 
	 * MobInfo[0]: mob type, MobInfo[1]: number of mobs in this wave, MobInfo[2]: mob health
	 */
	private String[]					mMobInfo;
	/**
	 * ID for current track, used to find the track info in the xml-file
	 */
	private int							mTrackID;
	
	
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
	 * @return	The current wave number
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
		
		boolean hasMoreMobs;
		
		//If not on the last wave = Mobs left
		if(mWaveNr < mTrackWaves.size())
			hasMoreMobs = true;
		
		//If on the last wave
		else if(mWaveNr-1 == mTrackWaves.size()) {
			
			//If the current mob is not the last mob in the wave
			if(mMobNr < mTrackWaves.get(mWaveNr).size()) {
				//TODO remove this log message when done with it
				Log.d("Jonas2","mMobNr"+mMobNr);
				hasMoreMobs = true;
				
			//the current mob IS the last mob in the wave	
			} else
				hasMoreMobs = false;
		} else
			hasMoreMobs = false;
		
		return hasMoreMobs;
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
		//Log.i("TESTJONAS","mWaveNr/mWaveDelayI/mMaxWaveDelay/MobNr/maxMobNr: "+ mWaveNr + "/" + mWaveDelayI + "/" + mMaxWaveDelay + "/" + mMobNr + "/" + mTrackWaves.get(mWaveNr).size());
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
					}
					mWaveDelayI = mMaxWaveDelay;
					return mMob;
	
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
			
		mWaveDelayI = 0;
		mWaveNr = 0;
		mMobNr = 0;
		mTrackNr = GameModel.getTrack(); //1-5, Which track currently at
		mMaxWaveDelay = 10;

		
		//mTrackWaves = new ArrayList<ArrayList<ArrayList<Mob>>>();
		//mMobTypeList = new ArrayList<String>();
		
		//loops through the tracks, number of tracks is unknown, so it will loop until "break"
		for(int trackNr = 1; true; trackNr++) {

			try {
				
				mWaves = new ArrayList<ArrayList<Mob>>();
				
				//The name of the current track as found in the xml-file initwaves.xml
				mTrackName = "mobs_track_" + trackNr;
				
				mTrackID = mContext.getResources().getIdentifier(mTrackName, 
						"array", mContext.getPackageName());
				
				mAllWaves = mContext.getResources().getStringArray(mTrackID);
				
				//loop through all waves in the xml
				for(int waveIndex = 0; waveIndex < mAllWaves.length; waveIndex++) {
					
					//make sure mMobs is empty
					mMobs = new ArrayList<Mob>();
					
					//Break up the info of this wave in a separate string array
					mMobInfo = mAllWaves[waveIndex].split(" ");
					
					
					/*Log.i("INIT MOBS", "Wave:" + trackNr + "/" + waveIndex + " MobInfo = " 
							+ mMobInfo[0] + " " + mMobInfo[1] + " health:"+mMobInfo[2]);*/
					
					String sType = mMobInfo[0];
					int iType;
					
					if(sType.equals("NORMAL"))
						iType = Mob.NORMAL;
					else if (sType.equals("AIR"))
						iType = Mob.AIR;
					else if (sType.equals("FAST"))
						iType = Mob.FAST;
					else if (sType.equals("HEALTHY"))
						iType = Mob.HEALTHY;
					else // if (sType.equals("IMMUNE"))
						iType = Mob.IMMUNE;
					
					int numberOfMobsInWave = Integer.parseInt(mMobInfo[1]);
					int health = Integer.parseInt(mMobInfo[2]);
					
					//add mobs for this wave to mMobs, using the info about type, number of mobs and health 
					for(int mobNr = 0; mobNr < numberOfMobsInWave; mobNr++)
						mMobs.add(new Mob(iType, health));							
					
					mWaves.add(mMobs);
					//Log.v("INIT MOBS", "New wave added!");
					
				}
				if(trackNr == GameModel.getTrack()) {
					mTrackWaves = mWaves;
					Log.i("Finished","with wave initiation of "+trackNr);
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