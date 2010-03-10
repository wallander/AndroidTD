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
	private List<List<Mob>>			mWaves;
	
	/**
	 * Should not be used, call getInstance() instead.
	 */
	private MobFactory(){
		
		mWaves = new ArrayList<List<Mob>>();
		
		// TODO Implement import from .waves
		try	{
			
			BufferedReader reader = new BufferedReader(new FileReader("/resources/init.waves"));
			String	mLine = "";
			
			while((mLine = reader.readLine()) != null){
				
				 List<Mob> mWave = new ArrayList<Mob>();
				 
				 for(int i = 0; i < MOB_AMOUNT; ++i) {
					 
					 // Create mob of type: HEALTHY, ARMORED, FAST or NORMAL
					 if(mLine.equals("HEALTHY")) {
						 mWave.add(new Mob(MobType.HEALTHY));
					 } else if(mLine.equals("ARMORED")) {
						 mWave.add(new Mob(MobType.ARMORED));
					 } else if(mLine.equals("FAST")) {
						 mWave.add(new Mob(MobType.FAST));
					 } else if(mLine.equals("NORMAL")) {
						 mWave.add(new Mob(MobType.NORMAL));
					 }
					 
				 }
				 
				 mWaves.add(mWave);
			}
			
		} catch(FileNotFoundException fnf) {
			System.err.println("Message: " + fnf.getMessage() + "\n CAUSE: " + fnf.getCause() + "\n LOCATION: MobFactory.java");
		} catch(IOException ioe) {
			System.err.println("Message: " + ioe.getMessage() + "\n CAUSE: " + ioe.getCause() + "\n LOCATION: MobFactory.java");
		}
	}
		
	/**
	 * 
	 * @return
	 */
	public static MobFactory getInstance() {
		return INSTANCE;
	}
	
	/**
	 * 
	 * @param pWaveIndex
	 * @return
	 */
	public List<Mob> nextWave(int pWaveIndex) {
		
		return mWaves.get(pWaveIndex);
	}
	
}
