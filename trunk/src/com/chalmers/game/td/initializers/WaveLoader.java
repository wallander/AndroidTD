package com.chalmers.game.td.initializers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.chalmers.game.td.units.Mob;
import com.chalmers.game.td.units.Mob.MobType;

public class WaveLoader extends Loader {
	
	// Instance variables
	private List<List<Mob>>	mWaves;
	private List<Mob>		mMobs;
	
	public WaveLoader(String pFileName) {
		super(pFileName);
		
		mWaves = new ArrayList<List<Mob>>();		
		
		initWaves();
	}
	
	public List<List<Mob>> getWaves() {
		return mWaves;
	}
	
	public void initWaves() {
		
		String 			mLine = "";
		String[]		mWaveInfo = new String[2];
		
		try {
			
			while((mLine = mReader.readLine()) != null) {
				
				// Create a new list of mobs for each wave
				mMobs = new ArrayList<Mob>();
				
				// Split the string into an array
				// The first element contains the MobType
				// The second element contains the amount of mobs
				mWaveInfo = mLine.split(" ");
								
				for(int i = 0; i < Integer.parseInt(mWaveInfo[1]); ++i) {
					
					if(mWaveInfo[0].equals("NORMAL")) {
						
						mMobs.add(new Mob(MobType.NORMAL));
						
					} else if(mWaveInfo[0].equals("ARMORED")) {
						
						mMobs.add(new Mob(MobType.ARMORED));
						
					} else if(mWaveInfo[0].equals("FAST")) {
						
						mMobs.add(new Mob(MobType.FAST));
						
					} else if(mWaveInfo[0].equals("HEALTHY")) {
						
						mMobs.add(new Mob(MobType.HEALTHY));
					}					
				}
				
				mWaves.add(mMobs);
			}
			
		} catch(IOException ioe) {
			System.err.println("IOEXCEPTION!"); // TODO Give more specific cause...
		}
	}

}