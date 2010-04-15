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
	
	public static List<Tower> mTowers;
	public static List<Mob> mMobs;
	public static List<Mob> mShowRewardForMob;
	public static List<Projectile> mProjectiles;
	public static List<Snowball> mSnowballs;
	public static Path mPath;
	public static HashSet<Point> mOccupiedTilePositions;
	public static int mWaveNr;
	public static Player currentPlayer;
	public static int	sSelectedTrack = 1;
	

	/** Size of "game tiles" */
    public static final int GAME_TILE_SIZE = 16;
	
	/**
	 * Constructor
	 */
	public GameModel() {
		
		
	}
	
	/**
	 * Initializes all variables of the GameModel. TODO take care of track nr
	 */
	public static void initialize() {
		
		currentPlayer = new Player();
		mTowers = new ArrayList<Tower>();
		mMobs = new ArrayList<Mob>();
		mShowRewardForMob = new ArrayList<Mob>();
		mProjectiles = new ArrayList<Projectile>();
		mSnowballs = new ArrayList<Snowball>();
		mPath = Path.getInstance();
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
	}
	
	/**
	 * Adds a tower to a specific place on the game field.
	 * The added tower is placed on a grid, consisting of tiles with the size
	 * GAME_TILE_SIZExGAME_TILE_SIZE
	 * 
	 * @param x Tile position on the X-axis
	 * @param y Tile position on the Y-axis
	 */
	public static void buildTower(Tower tower, int x, int y){
		
		tower.setCoordinates(new Coordinate(x*GAME_TILE_SIZE , y*GAME_TILE_SIZE));
		
		if (!canAddTower(tower))
			return;

		mTowers.add(tower);
		
		for (int i = 0; i < tower.getWidth(); i++) {
			for (int j = 0; j < tower.getHeight(); j++) {
				mOccupiedTilePositions.add(new Point(x+i,y+j));
			}
		}
		
	}
	
	public static int getWaveNr() {
		return mWaveNr;
	}

	public static void setWaveNr(int pWaveNr) {
		mWaveNr = pWaveNr;
	}

	public static boolean canAddTower(Tower tower) {
		
		int tx = (int) (tower.getX() / GAME_TILE_SIZE);
		int ty = (int) (tower.getY() / GAME_TILE_SIZE);
		
		
		for (int i = 0; i < tower.getWidth(); i++) {
			
			for (int j = 0; j < tower.getHeight(); j++) {
				if (mOccupiedTilePositions.contains(new Point(tx+i,ty+j)))
					return false;
			}
		}
		return true;
		
	}
	
	public static void removeTower(Tower t) {
		mTowers.remove(t);
		
		int tx = (int) (t.getX() / GAME_TILE_SIZE);
		int ty = (int) (t.getY() / GAME_TILE_SIZE);
		
		for (int i = 0; i < t.getWidth(); i++) {
			
			for (int j = 0; j < t.getHeight(); j++) {
				if (mOccupiedTilePositions.contains(new Point(tx+i,ty+j)))
					mOccupiedTilePositions.remove(new Point(tx+i,ty+j));
			}
		}	
		
	}
	
	public static void setTrack(int pTrack) {
		sSelectedTrack = pTrack;
	}
	
	public static int getTrack() {
		return sSelectedTrack;
	}
}

