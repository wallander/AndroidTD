package com.chalmers.game.td;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.Log;

import com.chalmers.game.td.units.Mob;
import com.chalmers.game.td.units.Tower;


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
	
	/**
	 * The index of the current wave. First wave has index 0.
	 */
	private int							mWaveIndex;
	private int							mMaxWaveDelay,
										mMobIndex,
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
	
	private boolean						mLastMobSent = false;
	
	
	/**
	 * Should not be used, call getInstance() instead.
	 */
	private MobFactory() {
		//mWaves = null;
		
		mContext = null;		
		//mMobs = null;
		mWaveDelayI = 0;
		mWaveIndex = 0;
		mMobIndex = 0;
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
		return mWaveIndex+1;
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
		int time = 0;
		if (waveInProgress())
			time += mMaxWaveDelay+numberOfMobsLeftThisWave();
		else if (mWaveDelayI < mMaxWaveDelay)
			time += mMaxWaveDelay-mWaveDelayI;
		return time;
	}
	
	/**
	 * Can only be called if there is at least one wave left after the current wave!
	 * 
	 * @return
	 */
	public String getNextWaveType(){

		if (waveInProgress()) //return type of next wave
			return mTrackWaves.get(mWaveIndex+1).get(0).toString();
		else //return type of current wave
			return mTrackWaves.get(mWaveIndex).get(0).toString();
	}
	
	private boolean waveInProgress(){
		//if the delaycounter is "ready" it means that a wave is in progress
		return (mWaveDelayI >= mMaxWaveDelay);
	}

	/**
	 *  Returns the total amount of waves
	 *  on the track the player is currently
	 *  playing.
	 *  
	 * @return		total number of waves
	 */
	public int getTotalNrOfWaves() {
		return mTrackWaves.size();
	}
	
	/**
	 * 
	 * @return The number of the last wave that entered the game field.
	 */
	public int getNextWaveNr(){
		int number=getWaveNr();
		if (!waveInProgress())
			number = getWaveNr()-1;
		return number;
	}
	
	/**
	 * 
	 * @return number of mobs in the current wave that has not yet entered the
	 * game field.
	 */
	private int numberOfMobsLeftThisWave(){
		int mobsInWave = mTrackWaves.get(mWaveIndex).size();
		int mobsLeft = mobsInWave-mMobIndex;
		return mobsLeft;
	}

	/**
	 * Checks if there are more mobs left to
	 * send out. Used to check for completion
	 * of a track.
	 * 
	 * @return	true if there are mobs left, false otherwise
	 */
	public boolean hasMoreMobs() {

		boolean hasMoreMobs;

		//If current wave is not the last wave. 
		if(hasMoreWaves()){
			hasMoreMobs = true;
			//Log.e("hasMoreMobs","false du to hasMoreWaves");
		//If current wave IS the last wave, but the current mob is not the last mob
		}else if(mMobIndex < mTrackWaves.get(mWaveIndex).size()-1){
			hasMoreMobs = true;
			//Log.e("hasMoreMobs","true " + mMobIndex + " < " + (mTrackWaves.get(mWaveIndex).size()-1));
		//If the current mob IS the last mob in the last wave	
		}else{
			hasMoreMobs = false;
			//Log.e("hasMoreMobs","false due to mMobIndex");
		}
		
		return hasMoreMobs;
	}
	
	public void resetWaveIndex() {
		mWaveIndex = 0;
	}
	
	public void resetMobIndex(){
		mMobIndex = 0;
	}
	
	public boolean lastWaveHasEntered(){
		//if there are no more waves after the current and the delay-counter

		//is "ready", that means the last wave has started to enter. 
		//Log.i("LASTWAVE",""+!hasMoreWaves() + "&&" + mWaveDelayI +">="+ mMaxWaveDelay);

		return (!hasMoreWaves() && mWaveDelayI >= mMaxWaveDelay);
	}
	
	/**
	 * Returns whether there are more waves after the current wave or not. 
	 */
	public boolean hasMoreWaves(){
		return (mWaveIndex < mTrackWaves.size() -1);
	}
	
	/**
	 * Returns the next mob on top of the queue, given the track number. TODO not a queue anymore...
	 * 
	 * @return the next mob to send out. First mob on the track
	 * will be mob 0 of wave 0.
	 */
	public Mob getNextMob() {	

		Mob mMob = null;

		//If the last mob of the track has not been sent already and the delay-counter is ready
		if (!mLastMobSent && mWaveDelayI >= mMaxWaveDelay) {

			mMob = mTrackWaves.get(mWaveIndex).get(mMobIndex);

			//if this was the last mob (no more mobs after this one)
			if (!hasMoreMobs())
				mLastMobSent = true;
				
			//else if there are more mobs, but mMob was the last mob in the current wave
			else if (mMobIndex == mTrackWaves.get(mWaveIndex).size() -1){
				if(mMob.getType()==Mob.HEALTHY)
					mMaxWaveDelay = 20;
				else
					mMaxWaveDelay = 10;
				mWaveIndex++;
				mMobIndex = 0;
				mWaveDelayI = 0;
				//else if mMob was NOT the last mob in the current wave
			}else
				mMobIndex++;

			mPath.setTrackPath(GameModel.getTrack());
			mMob.setPath(mPath);
			
		} else
			mWaveDelayI++; 

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
	
	public boolean getLastMobSent(){
		
		return mLastMobSent;
	}

	/**
	 * Initiate the waves
	 * TODO Somehow solve which track to load waves to
	 */
	private void initWaves() {
			
		mWaveDelayI = 0;
		mWaveIndex = 0;
		mMobIndex = 0;
		mTrackNr = GameModel.getTrack(); //1-5, Which track currently at
		mMaxWaveDelay = 10;
		mLastMobSent = false;
				
		//loops through the tracks, number of tracks is unknown, so it will loop until "break"
		for(int trackNr = 1; ; trackNr++) {

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
					////Log.v("Mob","added to list" + waveIndex + " track: "+trackNr);
					
				}
				
				//if last track is reached
				if(trackNr == GameModel.getTrack()) {
					mTrackWaves = mWaves;
					////Log.i("Finished","with wave initiation of "+trackNr);
					////Log.i("Waves","Amount"+mWaves.size());
				}
			
				
			} catch(NullPointerException npe) {
				////Log.i("INITIATION", "Mobs initiation complete.");
				// Reset mMobs so it will be able to be used at getNextMob()
				mMobIndex = 0;
				break;
			} catch(NotFoundException nfe) {
				////Log.i("INITIATION", "Mobs initiation complete. No more mobs to load.");
				// Reset mMobs so it will be able to be used at getNextMob()
				mMobIndex = 0;
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
			if (mWaveIndex < mTrackWaves.size())
				return "" + mTrackWaves.get(mWaveIndex).get(0).toString();
			else
				return "";
	}
	
	/**
	 * 
	 * @return
	 */
	public static MobFactory getInstance() {
		return INSTANCE;
	}

}