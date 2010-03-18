package com.chalmers.game.td.initializers;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;

import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.Path;
import com.chalmers.game.td.R;
import com.chalmers.game.td.units.Mob;
import com.chalmers.game.td.units.Mob.MobType;
//import com.chalmers.game.td.units.Mob.MobType;

public class Loader {
	
	// Instance variables
	private Path				mPath;
	private List<Coordinate>	mCoordinates;
	private List<List<Mob>>		mWaves;
	private List<Mob>			mMobs;
	private Context				mContext;
	
	public Loader(Context pContext) {
		
		mPath = Path.getInstance();
		mWaves = new ArrayList<List<Mob>>();
		mContext = pContext;
		initWaves();
	}
	
	public List<List<Mob>> getWaves() {
		return mWaves;
	}
	
	public void initWaves() {
	
		mCoordinates = new ArrayList<Coordinate>();
		mMobs = new ArrayList<Mob>();
		
		String[]	mPathCoordinates = mContext.getResources().getStringArray(R.array.init_path),
					mCoords,
					mMobTypes = mContext.getResources().getStringArray(R.array.init_mobs),
					mMobInfo;		
		
		for(int i = 0; i < mPathCoordinates.length; ++i) {
			mCoords = mPathCoordinates[i].split(" ");
			mCoordinates.add(new Coordinate(Integer.parseInt(mCoords[0]), Integer.parseInt(mCoords[1])));
		}
		
		mPath.setTrackPath(mCoordinates);
		
		for(int i = 0; i < mMobTypes.length; ++i) {
			mMobInfo = mMobTypes[i].split(" ");
			
			for(int j = 0; j < Integer.parseInt(mMobInfo[1]); ++j) {
							
				if(mMobInfo[0].equals("NORMAL")) {
					mMobs.add(new Mob(mPath, MobType.NORMAL));
				} else if(mMobInfo[0].equals("ARMORED")) {
					mMobs.add(new Mob(mPath, MobType.ARMORED));
				} else if(mMobInfo[0].equals("FAST")) {
					mMobs.add(new Mob(mPath, MobType.FAST));
				} else if(mMobInfo[0].equals("HEALTHY")) {
					mMobs.add(new Mob(mPath, MobType.HEALTHY));
				}
			}
			
			mWaves.add(mMobs);
		}
		
	}

}