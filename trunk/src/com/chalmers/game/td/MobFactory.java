package com.chalmers.game.td;

import java.util.List;

import com.chalmers.game.td.exceptions.WaveNotInitializedException;
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
	public Mob getMob() {
				
		Mob mMob;
		
		if(mMobs != null) {
			mMob = mMobs.remove(0);
		} else if(mWaves != null) {
			mMobs = mWaves.remove(0);
		} else {
			return null;
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
