package com.chalmers.game.td;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


import android.content.Context;
import android.graphics.Point;

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
	
	public static List<Tower> sTowers;
	public static List<Mob> sMobs;
	public static List<Mob> sShowRewardForMob;
	public static List<Projectile> sProjectiles;
	public static List<Snowball> sSnowballs;
	public static Path sPath;
	public static HashSet<Point> sOccupiedTilePositions;
	public static Player sCurrentPlayer;
	public static int	sSelectedTrack = 1;
	public static boolean sMusicEnabled = true;
	public static boolean sCheatEnabled = false;
	

	/** Size of "game tiles" */
    public static final int GAME_TILE_SIZE = 16;
	
	/**
	 * Constructor
	 */
	public GameModel() {
		
	}
	
	/**
	 * Initializes all variables of the GameModel.
	 */
	public static void initialize(Context context) {
		
		
		sTowers = new ArrayList<Tower>();
		sMobs = new ArrayList<Mob>();
		sShowRewardForMob = new ArrayList<Mob>();
		sProjectiles = new ArrayList<Projectile>();
		sSnowballs = new ArrayList<Snowball>();
		sPath = Path.getInstance();
		sPath.setContext(context);  
		sPath.setTrackPath(getTrack());
		sOccupiedTilePositions = new HashSet<Point>();
		sCurrentPlayer = new Player(sPath.getNumberOfTracks());
				
		
		// add a "frame" of occupied tiles around the game field
		for (int i = -1; i < 31; i++) {
			sOccupiedTilePositions.add(new Point(i, 20));
			sOccupiedTilePositions.add(new Point(i, 21));
		}
		
		for (int i = -1; i < 21; i++) {
			sOccupiedTilePositions.add(new Point(-2, i));
			sOccupiedTilePositions.add(new Point(-1, i));
			sOccupiedTilePositions.add(new Point(31, i));
		}
		
		// add occupied tiles under the pause- and fastforward buttons
		sOccupiedTilePositions.add(new Point(0,0));
		sOccupiedTilePositions.add(new Point(1,0));
		sOccupiedTilePositions.add(new Point(2,0));
		sOccupiedTilePositions.add(new Point(3,0));
		sOccupiedTilePositions.add(new Point(0,1));
		sOccupiedTilePositions.add(new Point(1,1));
		sOccupiedTilePositions.add(new Point(2,1));
		sOccupiedTilePositions.add(new Point(3,1));
		sOccupiedTilePositions.add(new Point(0,19));
		sOccupiedTilePositions.add(new Point(1,19));
		sOccupiedTilePositions.add(new Point(2,19));
		sOccupiedTilePositions.add(new Point(3,19));
		sOccupiedTilePositions.add(new Point(0,18));
		sOccupiedTilePositions.add(new Point(1,18));
		sOccupiedTilePositions.add(new Point(2,18));
		sOccupiedTilePositions.add(new Point(3,18));
		
		
		// calculate where towers can be placed depending on the Path
		for (int i = 0; i < sPath.getSize()-1; i++) {
			Coordinate c1 = sPath.getCoordinate(i);			
			Coordinate c2 = sPath.getCoordinate(i+1);
			
			double angle = Coordinate.getAngle(c1, c2);
			Coordinate temp = new Coordinate(c1.getX(), c1.getY());
			
			while (Coordinate.getDistance(temp, c2) > 2 ) {
				
				sOccupiedTilePositions.add(new Point((int)temp.getX()/GAME_TILE_SIZE,
						(int)temp.getY()/GAME_TILE_SIZE ));
				
				sOccupiedTilePositions.add(new Point((int)temp.getX()/GAME_TILE_SIZE + 1,
						(int)temp.getY()/GAME_TILE_SIZE ));
				
				sOccupiedTilePositions.add(new Point((int)temp.getX()/GAME_TILE_SIZE + 1,
						(int)temp.getY()/GAME_TILE_SIZE + 1));
				
				sOccupiedTilePositions.add(new Point((int)temp.getX()/GAME_TILE_SIZE,
						(int)temp.getY()/GAME_TILE_SIZE + 1));
				
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

		sTowers.add(tower);
		
		for (int i = 0; i < tower.getWidth(); i++) {
			for (int j = 0; j < tower.getHeight(); j++) {
				sOccupiedTilePositions.add(new Point(x+i,y+j));
			}
		}
		
	}
	
	/**
	 * Returns whether a tower can be placed on the gamefield with it's current coordinates
	 */
	public static boolean canAddTower(Tower tower) {
		
		int tx = (int) (tower.getX() / GAME_TILE_SIZE);
		int ty = (int) (tower.getY() / GAME_TILE_SIZE);
		
		for (int i = 0; i < tower.getWidth(); i++) {
			for (int j = 0; j < tower.getHeight(); j++) {
				if (sOccupiedTilePositions.contains(new Point(tx+i,ty+j)))
					return false;
			}
		}
		return true;
		
	}
	
	public static void removeTower(Tower t) {
		sTowers.remove(t);
		
		int tx = (int) (t.getX() / GAME_TILE_SIZE);
		int ty = (int) (t.getY() / GAME_TILE_SIZE);
		
		for (int i = 0; i < t.getWidth(); i++) {
			
			for (int j = 0; j < t.getHeight(); j++) {
				if (sOccupiedTilePositions.contains(new Point(tx+i,ty+j)))
					sOccupiedTilePositions.remove(new Point(tx+i,ty+j));
			}
		}	
		
	}
	
	public static void setTrack(int pTrack) {
		sSelectedTrack = pTrack;
	}
	
	public static int getTrack() {
		return sSelectedTrack;
	}

	public static void setMusicEnabled(boolean isChecked) {
		sMusicEnabled = isChecked;
	}

	public static void setCheatEnabled(boolean isChecked) {
		sCheatEnabled = isChecked;
	}
}

