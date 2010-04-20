package com.chalmers.game.td;

import android.graphics.Bitmap;
import java.util.List;
import com.chalmers.game.td.units.Mob;

/**
 * Class that represents a level of the game.
 * 
 * @author Fredrik Persson
 * @author Jonas Andersson
 * @author Ahmed Chaban
 * @author Jonas Wallander
 * @author Disa Faith
 * @author Daniel Arvidsson
 */

//TODO används den här klassen överhuvudtaget?
public class Level {
	
	private Bitmap 		mMap;
	private List<Path>	mPath;
	private List<Mob>	mWaves;
	private int			mLevel;
	
	/**
	 * Constructor
	 * 
	 * @param pLevel The level number
	 */
	public Level(int pLevel){
		setLevel(pLevel);
	}
	
	// SET METHODS
	
	/**
	 * Setter for Level number
	 * 
	 * @param pLevel Level number
	 */
	public void setLevel(int pLevel){
		mLevel = pLevel;	
	}
	
	/**
	 * Setter for the game map Bitmap
	 * 
	 * @param pMap Game map
	 */
	public void setMap(Bitmap pMap){
		mMap = pMap;
	}
	
	/**
	 * Setter for the paths the mobs should take
	 * 
	 * @param pPath
	 */
	public void setPath(List<Path> pPath) {
		mPath = pPath;
	}
	
	/**
	 * Setter for the mob waves on the level. Should probably be an add-method instead. TODO
	 * 
	 * @param pWaves
	 */
	public void setWaves(List<Mob> pWaves)
	{
		mWaves = pWaves;
	}
	
	// GET METHODS
	
	/**
	 * Getter for the level number
	 * 
	 * @return mLevel Level number
	 */
	public int getLevel(){
		return mLevel;
	}
	
	/**
	 * Getter for the game map Bitmap
	 * 
	 * @return mMap Game map
	 */
	public Bitmap getMap() {
		return mMap;
	}
	
	/**
	 * Getter for the paths
	 * 
	 * @return mPath
	 */
	public List<Path> getPath() {
		return mPath;
	}
	
	/**
	 * Getter for the mob waves
	 * 
	 * @return mWaves
	 */
	public List<Mob> getWaves() {
		return mWaves;
	}

}
