package com.chalmers.game.td;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.chalmers.game.td.initializers.Loader;
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
	private Context					mContext;
	private Loader					mLoader;
	private List<List<Mob>>			mWaves;
	
	/**
	 * Should not be used, call getInstance() instead.
	 */
	private MobFactory() {
		mWaves = null;
		mContext = null;
		mLoader = null;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Mob> getNextWave() {
					
		return (mWaves.get(0) != null) ? mWaves.remove(0) : null;
	}
	
	/**
	 * TODO Add param level, and edit accordingly in the xml-file
	 * @param pLevel
	 */
	public void initWaves() {
		
		if(mContext != null) {
			mLoader = new Loader(mContext);
		} else {
			System.err.println("Context not initiated! Error occured in MobFactory.java on line: 58");
			Log.v("Initiation error", "Context not initiated! Error occured in MobFactory.java on line: 58");
		}
		
		mWaves = mLoader.getWaves();
	}	
	
	/**
	 * Needed reference to be able to reach initwaves.xml
	 * in resources.
	 * 
	 * @param pContext
	 */
	public void setContext(Context pContext) {
		mContext = pContext;
	}
		
	/**
	 * 
	 * @return
	 */
	public static MobFactory getInstance() {
		return INSTANCE;
	}
	
	
	
}
