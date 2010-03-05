package com.chalmers.game.td;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	private List<MobType>			mWaves;
	
	private MobFactory(){
		
		// TODO Implement import from .waves
		try	{
			
			BufferedReader reader = new BufferedReader(new FileReader("/res/levelinfo/init.waves"));							
			
		} catch(FileNotFoundException fnf) {
			System.err.println("Message: " + fnf.getMessage() + "\n CAUSE: " + fnf.getCause());
		}
	}
		
	public static MobFactory getInstance() {
		return INSTANCE;
	}
	
	public List<Mob> nextWave() {
		
		return null;
	}
	
}
