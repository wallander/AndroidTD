package com.chalmers.game.td;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Set;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.SoundPool;
import android.util.Log;

import com.chalmers.game.td.units.*;
import com.chalmers.game.td.Path;

/**
 * Class that represents the Game Model. All towers, mobs, projectiles 
 * and information about the current level is stored here.
 * 
 * @author Fredrik Persson
 * @author Jonas Andersson
 * @author Ahmed Chaban
 * @author Jonas Wallander
 * @author Disa Faith
 * @author Daniel Arvidsson
 */
public class GameModel {
	
	protected List<Tower> mTowers;
	protected List<Mob> mMobs;
	protected List<Projectile> mProjectiles;
	protected Path mPath;
	protected HashSet<Point> mOccupiedTilePositions;
	protected int mWaveNr;
	
	


	/** Size of "game tiles" */
    public static final int GAME_TILE_SIZE = 16;
	
	/**
	 * Constructor
	 */
	public GameModel() {
		mTowers = new ArrayList<Tower>();
		mMobs = new ArrayList<Mob>();
		mProjectiles = new ArrayList<Projectile>();
		mPath = new Path();
		mOccupiedTilePositions = new HashSet<Point>();
		
		// calculate where towers can be placed.
		for (int i = 0; i < mPath.getSize()-1; i++) {
			Coordinate c1 = mPath.getCoordinate(i);
			Coordinate c2 = mPath.getCoordinate(i+1);
			
			double angle = Coordinate.getAngle(c1, c2);
			Coordinate temp = new Coordinate(c1.getX(), c1.getY());
			
			while (Coordinate.getSqrDistance(temp, c2) > 2 ) {
				
				mOccupiedTilePositions.add(new Point((int)temp.getX()/GAME_TILE_SIZE,(int)temp.getY()/GAME_TILE_SIZE ));
				
				mOccupiedTilePositions.add(new Point((int)temp.getX()/GAME_TILE_SIZE + 1,(int)temp.getY()/GAME_TILE_SIZE ));
				
				mOccupiedTilePositions.add(new Point((int)temp.getX()/GAME_TILE_SIZE + 1,(int)temp.getY()/GAME_TILE_SIZE + 1));
				
				mOccupiedTilePositions.add(new Point((int)temp.getX()/GAME_TILE_SIZE,(int)temp.getY()/GAME_TILE_SIZE + 1));
				
				temp.setXY(temp.getX() + Math.cos(angle), temp.getY() - Math.sin(angle));
			
			}
		}
		
		
		// Temporary code to add a tower and a mob for testing purposes	

		 Log.v("", "Skapa tower");

		 buildTower(8,10);
		 buildTower(12,6);
		 
		 Log.v("", "skapa mob");

		 // adds a new mob to the gamefield with a predefined path
		mMobs.add(new Mob(mPath));


		

	
	}
	
	/**
	 * Adds a tower to a specific place on the game field.
	 * The added tower is placed on a grid, consisting of tiles with the size
	 * GAME_TILE_SIZExGAME_TILE_SIZE
	 * 
	 * @param x Tile position on the X-axis
	 * @param y Tile position on the Y-axis
	 */
	public void buildTower(int x, int y){
		
		// mycket fulkod blire TODO
		if (!mOccupiedTilePositions.contains(new Point(x,y)) &&
				!mOccupiedTilePositions.contains(new Point(x+1,y)) &&
				!mOccupiedTilePositions.contains(new Point(x+1,y+1)) &&
				!mOccupiedTilePositions.contains(new Point(x,y+1))) {
			Tower t = new Tower(x*GAME_TILE_SIZE , y*GAME_TILE_SIZE);
			t.setSize(2);
			mTowers.add(t);
			
			// fulkod TODO
			mOccupiedTilePositions.add(new Point(x,y));
			mOccupiedTilePositions.add(new Point(x+1,y));
			mOccupiedTilePositions.add(new Point(x+1,y+1));
			mOccupiedTilePositions.add(new Point(x,y+1));
		}
	}
	
	 public int getWaveNr() {
		return mWaveNr;
	}

	public void setWaveNr(int mWaveNr) {
		this.mWaveNr = mWaveNr;
	}
}

