package com.chalmers.game.td;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.chalmers.game.td.initializers.Loader;
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
	private static final int		MOB_AMOUNT = 15;
	private List<List<Mob>>			mAllWaves;
	private Loader					loader;
	
	/**
	 * Should not be used, call getInstance() instead.
	 */
	private MobFactory(){
		mAllWaves = new ArrayList<List<Mob>>();
		//loader = new Loader();
		//mAllWaves = loader.initWaves();
	}
		
	/**
	 * 
	 * @return
	 */
	public static MobFactory getInstance() {
		return INSTANCE;
	}
	
	
	
}
