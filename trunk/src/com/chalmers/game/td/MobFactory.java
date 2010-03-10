package com.chalmers.game.td;

import java.util.List;

import com.chalmers.game.td.exceptions.EndOfGameException;
import com.chalmers.game.td.exceptions.EndOfLevelException;
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
	private static final int		NR_OF_LEVELS = 3;
	private WaveLoader				mWaveLoader;
	private List<List<Mob>>			mWaves;
	private List<Mob>				mMobs;
	private int						mLevel;
	
	/**
	 * Should not be used, call getInstance() instead.
	 */
	private MobFactory() {
		mWaveLoader = null;
		mWaves = null;		
		mMobs = null;
		mLevel = 1;
		initWaves(mLevel);
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
				
				++mLevel;							// Change level
				
				if(mLevel <= NR_OF_LEVELS) {			// If last level still not reached
				
					initWaves(mLevel);					// Initialize next level and...
					throw new EndOfLevelException();	// ...throw end of level exception
				} else {								// Otherwize
					throw new EndOfGameException();		// Game Over, gz, thanks fur playin' :)
				}
			}
		}
			
		return mMob;
	}
	
	/**
	 * 
	 * @param pLevel
	 */
	public void initWaves(int pLevel) {
		
		mWaveLoader = new WaveLoader("init/waves/level" + pLevel);
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
