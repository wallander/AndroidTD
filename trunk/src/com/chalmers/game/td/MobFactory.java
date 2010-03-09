package com.chalmers.game.td;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.chalmers.game.td.units.Mob;
import com.chalmers.game.td.units.Mob.MobType;

/**
 * 
 * @authors Jonas Andersson, Daniel Arvidsson, Ahmed Chaban, Disa Faith, Fredrik Persson, Jonas Wallander
 *
 */
public class MobFactory {
	
	// Instance variables	
	private static final MobFactory	INSTANCE = new MobFactory();
	private static final int		MOB_AMOUNT = 15;
	private Object[]			mWaves;
	
	/**
	 * Should not be used, call getInstance() instead.
	 */
	private MobFactory(){
		
		
	}
		
	/**
	 * 
	 * @return
	 */
	public static MobFactory getInstance() {
		return INSTANCE;
	}
		
}
