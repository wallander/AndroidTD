package com.chalmers.game.td;

import java.util.List;

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
	
	/**
	 * Should not be used, call getInstance() instead.
	 */
	private MobFactory() {
		
	}
	
	/**
	 * Returns a list containing the different waves of mobs, represented by a List<Mob>.
	 * 
	 * @param pLevel
	 * @return
	 */
	public List<List<Mob>> getWaves(int pLevel) {
		
		mWaveLoader = new WaveLoader("init/waves/level" + pLevel);
		mWaves = mWaveLoader.getWaves();
		return mWaves;
	}
		
	/**
	 * 
	 * @return
	 */
	public static MobFactory getInstance() {
		return INSTANCE;
	}
	
	
	
}
