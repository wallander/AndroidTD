package com.chalmers.game.td;

import android.graphics.Bitmap;
import java.util.List;
import com.chalmers.game.td.units.Mob;

/**
 * 
 * @author Fredrik Persson
 * @author Jonas Andersson
 * @author Ahmed Chaban
 * @author Jonas Wallander
 * @author Disa Faith
 * @author Daniel Arvidsson
 */
public class Level {
	
	private Bitmap 		mMap;
	private List<Path>	mPath;
	private List<Mob>	mWaves;
	private int			mLevel;
	
	public Level(int pLevel){
		setLevel(pLevel);
	}
	
	// SET METHODS
	
	/**
	 * @param pLevel
	 */
	public void setLevel(int pLevel){
		mLevel = pLevel;	
	}
	
	/**
	 * 
	 * @param pMap
	 */
	public void setMap(Bitmap pMap){
		mMap = pMap;
	}
	
	/**
	 * 
	 * @param pPath
	 */
	public void setPath(List<Path> pPath) {
		mPath = pPath;
	}
	
	public void setWaves(List<Mob> pWaves)
	{
		mWaves = pWaves;
	}
	
	// GET METHODS
	
	/**
	 * @return mLevel
	 */
	public int getLevel(){
		return mLevel;
	}
	
	/**
	 * 
	 * @return mMap
	 */
	public Bitmap getMap() {
		return mMap;
	}
	
	/**
	 * 
	 * @return mPath
	 */
	public List<Path> getPath() {
		return mPath;
	}
	
	/**
	 * 
	 * @return mWaves
	 */
	public List<Mob> getWaves() {
		return mWaves;
	}

}
