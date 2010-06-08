package com.chalmers.game.td;

import java.util.ArrayList;
import com.chalmers.game.td.units.Mob;

public class MobFactory {
	
	private static MobFactory			INSTANCE = new MobFactory();
	private ArrayList<ArrayList<Mob>>	mWaves;	
	
	/**
	 * Use getInstance() instead.
	 */
	private MobFactory() {
		
		mWaves = new ArrayList<ArrayList<Mob>>();				
	}
	
	/**
	 * 
	 * @return the instance of MobFactory
	 */
	public MobFactory getInstance() {
		return INSTANCE;
	}
	
	public void init() {
		
	}
	
	/**
	 * 
	 * @return the wave for the current track
	 */
	public ArrayList<Mob> getWave() {				
		
		return mWaves.get(GameModel.getTrack());
	}
}