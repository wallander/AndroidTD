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
		loader = new Loader();
		mAllWaves = loader.initWaves();
	}
		
	/**
	 * 
	 * @return
	 */
	public static MobFactory getInstance() {
		return INSTANCE;
	}
	
	
	private class Loader {
		
		// Instance variables
		private BufferedReader	mReader;
		private List<List<Mob>>	mAllWaves;
		private List<Mob>		mWave;
		
		public Loader() {
			
			mAllWaves = new ArrayList<List<Mob>>();
			mWave = new ArrayList<Mob>();
			
			try { // Try to read the file init.waves which contains the different MobTypes the wave should contain
				mReader = new BufferedReader(new FileReader("resources/init.waves"));
			} catch(FileNotFoundException fnfe) {
				System.err.println("File not found exception when trying to initialize the waves \n Location: MobFactory.java row: 50 \n Cause: " + fnfe.getCause());
			}
		}
		
		public List<List<Mob>> initWaves() {
			
			String mLine = "";
			
			try {
				
				while((mLine = mReader.readLine()) != null) {
					
					if(Character.isDigit(mLine.toCharArray()[0])) {
						
						mAllWaves.add(mWave);
						mWave = new ArrayList<Mob>();
						
					} else {
						
						if(mLine.equals("NORMAL")) {
							mWave.add(new Mob(MobType.NORMAL));
						} else if(mLine.equals("ARMORED")) {
							mWave.add(new Mob(MobType.ARMORED));
						} else if(mLine.equals("HEALTHY")) {
							mWave.add(new Mob(MobType.HEALTHY));
						} else if(mLine.equals("FAST")) {
							mWave.add(new Mob(MobType.FAST));
						}						
					}
				}
				
			} catch(IOException ioe) {
				
			}
			
			return mAllWaves;
		}
	}
}
