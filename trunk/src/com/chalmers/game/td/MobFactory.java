package com.chalmers.game.td;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.content.Context;

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
	private Context					mContext;
	private Path					mPath;	
	private Queue<Mob>				mMobs;
	private Queue<Queue<Mob>>		mWaves;
	private List<Queue<Queue<Mob>>>	mTrackWaves;
	
	/**
	 * Should not be used, call getInstance() instead.
	 */
	private MobFactory() {
		mWaves = null;
		mContext = null;		
		mMobs = null;
		mTrackWaves = new ArrayList<Queue<Queue<Mob>>>();
	}
		
	/**
	 * 
	 * @param pTrack
	 * @return
	 */
	public Mob getNextMob(int pTrack) {
				
		
		return null;
	
	}
	
	/**
	 * Needed reference to be able to reach initwaves.xml
	 * in resources.
	 * 
	 * @Nextparam pContext
	 */
	public void setContext(Context pContext) {
		mContext = pContext;
		mPath = Path.getInstance();		
		mPath.setContext(pContext);
		initWaves();
	}
	
	/**
	 * Initiate the waves
	 * TODO Somehow solve which track to load waves to
	 */
	private void initWaves() {
		
		// TODO initPath & initWaves instead...
							
		for(int i = 0; ; ++i) {
			
			try {
			
				mWaves = new LinkedList<Queue<Mob>>();
				mMobs = new LinkedList<Mob>();
				
				String		mInitMob = "init_mob_" + String.valueOf(i+1);
				int			mMobIdentifier = mContext.getResources().getIdentifier(mInitMob, "array", mContext.getPackageName());
				String[]	mMobTypes = mContext.getResources().getStringArray(mMobIdentifier),
							mMobInfo;														
	
				for(int j = 0; j < mMobTypes.length; ++j) {
					mMobInfo = mMobTypes[j].split(" ");
					
					for(int k = 0; k < Integer.parseInt(mMobInfo[1]); ++k) {
	
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
					
					if(mMobs != null)
						mWaves.add(mMobs);
				}
				
				mTrackWaves.add(mWaves);
				
			} catch(NullPointerException npe) {
				
			}

		}
		
	}
		
	/**
	 * 
	 * @return
	 */
	public static MobFactory getInstance() {
		return INSTANCE;
	}
	
	
	
}
