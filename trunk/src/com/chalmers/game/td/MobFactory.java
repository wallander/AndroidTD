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
	private WaveLoader				waveLoader;
	private List<List<Mob>>			mWaves;
	
	/**
	 * Should not be used, call getInstance() instead.
	 */
	private MobFactory() {
		
		// Default is to load the first levels waves
		// TDOO Change this if we want the opportunity to start at last played level
		waveLoader = new WaveLoader("init/waves/level1");
		mWaves = waveLoader.getWaves();
	}
		
	/**
	 * 
	 * @return
	 */
	public static MobFactory getInstance() {
		return INSTANCE;
	}
	
	
	
}
