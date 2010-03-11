package com.chalmers.game.td;

import java.util.List;

import com.chalmers.game.td.exceptions.EndOfGameException;
import com.chalmers.game.td.exceptions.EndOfTrackException;
import com.chalmers.game.td.exceptions.EndOfWaveException;
import com.chalmers.game.td.exceptions.WaveException;
import com.chalmers.game.td.initializers.WaveLoader;
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
	private static final MobFactory	INSTANCE = new MobFactory();
	private WaveLoader				mWaveLoader;
	private List<List<Mob>>			mWaves;
	private List<Mob>				mMobs;
	
	/**
	 * Should not be used, call getInstance() instead.
	 */
	private MobFactory() {
		mWaveLoader = null;
		mWaves = null;		
		mMobs = null;
	}
	
	/**
	 * 
	 * @return
	 */
	public Mob getMob() throws WaveException {
	
		Mob mMob = null;
		
		if(mMobs != null) {						// If there still are mobs left in the wave
			mMob = mMobs.remove(0); 			// Remove one from the list
		} else {								// If the mobs are all requested
			
			if(mWaves != null) {					// If there still are waves left on current level
						
				mMobs = mWaves.remove(0);			// Get the next wave and...						
				throw new EndOfWaveException();		// ...throw end of wave exception
			
			} else {								// If it's the end of the level
				throw new EndOfTrackException();	// ...throw end of level exception
			}
		}
			
		return mMob;
	}
	
	/**
	 * 
	 * @param pLevel
	 */
	public void initWaves(int pLevel) {
		
		mWaveLoader = new WaveLoader("init/waves/level" + String.valueOf(pLevel));
		mWaves = mWaveLoader.getWaves();
		mMobs = mWaves.remove(0);
	}
		
	/**
	 * 
	 * @return
	 */
	public static MobFactory getInstance() {
		return INSTANCE;
	}
	
	
	
}
