package com.chalmers.game.td;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


import android.content.Context;
import android.graphics.Point;
import android.hardware.SensorEvent;

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
public class GameModel  implements java.io.Serializable {
	public static final long serialVersionUID = 1L;

	
	public List<Tower> sTowers;
	public List<Mob> sMobs;
	public List<Mob> sShowRewardForMob;
	public List<Projectile> sShowExplForProj;
	public List<Projectile> sProjectiles;
	public List<Snowball> sSnowballs;
	public Path sPath;
	public HashSet<Point> sOccupiedTilePositions;
	public Player sCurrentPlayer;
	public int	sSelectedTrack = 1;
	public boolean sMusicEnabled = true;
	public boolean sCheatEnabled = false;
	
	/* Grejer från GameView.... */
	
	public MobFactory mMobFactory = MobFactory.getInstance();
	
	public final int STATE_RUNNING = 1;
	public final int STATE_GAMEOVER = 2;
	public final int STATE_WIN = 3;
	public final int STATE_PAUSED = 4;

	public int GAME_STATE = STATE_RUNNING;
	
	private int GAME_SPEED_MULTIPLIER = 1;
	
	public Tower mCurrentTower;
	public Snowball mCurrentSnowball;
	
	public MovableTower mMovableTower;
	
	public Tower mSelectedTower;
	
	/** Indicates if fast forward is activated or not. */
	public boolean mFast = false;
	
	public Tower mTower1 = new BasicTower(0,0);
	public Tower mTower2 = new SplashTower(0,0);
	public Tower mTower3 = new SlowTower(0,0);
	public Tower mTower4 = new AirTower(0,0);
	
	public boolean mShowTooltip = false;
	public boolean mAllowBuild = false;
	
	
	public final int mSnowballTreshold = 4000;
	public int mUsedSnowballs;
	
	/** Keeps track of the delay between creation of Mobs in waves */
	public final float MOB_DELAY_MAX = 1;
	private float mMobDelayI = 0;
	
	boolean mSplash = false;
	boolean mCreatedLastWave = false; //Ahmed
	
	public SensorEvent mLatestSensorEvent;
	

	/** Size of "game tiles" */
    public final int GAME_TILE_SIZE = 16;
	
	/**
	 * Constructor
	 */
	public GameModel() {
		
	}
	
	/**
	 * Initializes all variables of the 
	 */
	public void initialize(Context context) {
		
		
		sTowers = new ArrayList<Tower>();
		sMobs = new ArrayList<Mob>();
		sShowRewardForMob = new ArrayList<Mob>();
		sShowExplForProj = new ArrayList<Projectile>();
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
		sOccupiedTilePositions.add(new Point(0,18));
		sOccupiedTilePositions.add(new Point(1,18));
		sOccupiedTilePositions.add(new Point(2,18));
		sOccupiedTilePositions.add(new Point(0,17));
		sOccupiedTilePositions.add(new Point(1,17));
		sOccupiedTilePositions.add(new Point(2,17));


		
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
	public void buildTower(Tower tower, int x, int y){
		
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
	public boolean canAddTower(Tower tower) {
		
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
	
	public void removeTower(Tower t) {
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
	
	public void setTrack(int pTrack) {
		sSelectedTrack = pTrack;
	}
	
	public int getTrack() {
		return sSelectedTrack;
	}

	public void setMusicEnabled(boolean isChecked) {
		sMusicEnabled = isChecked;
	}

	public void setCheatEnabled(boolean isChecked) {
		sCheatEnabled = isChecked;
	}

	/** 
	 * Activates or deactivates fast forward by changing the game's speed
	 * multiplier.
	 * 
	 * @param setFast True sets the game in fast forward mode, false sets the game to 
	 * normal speed.
	 */
	public void setFast(boolean setFast){
		mFast = setFast;
		
		if (mFast)
			setSpeedMultiplier(3);
		else
			setSpeedMultiplier(1);
	}
	
	/** 
	 * Toggles fast forward by changing the game's speed
	 * multiplier to the opposite of what it was before.
	 * 
	 */
	public void toggleFast(){
		mFast = !mFast;
		setFast(mFast);
	}
	
	public int getSpeedMultiplier() {
		return GAME_SPEED_MULTIPLIER;
	}

	private void setSpeedMultiplier(int i) {
		GAME_SPEED_MULTIPLIER = i;
	}
	
	/**
	 * This class is called each frame. 
	 * It keeps track of the creation of the mobs from the waves of the current map.
	 * Called from updateModel.
	 * @param timeDelta 
	 */
	private Mob createMobs(float timeDelta) {  	    	    	    	        	    	    	

		int track = getTrack();
		
		if (mMobDelayI >= MOB_DELAY_MAX) { //if it's time to get next mob
			mMobDelayI = 0;
			
			if(track > 0)
				return mMobFactory.getNextMob();
			else
				return null;
			
		} else {
			mMobDelayI += timeDelta*getSpeedMultiplier();
			return null;
		}

	}
	
	/**
	 * This class is called from the GameThread. 
	 * It updates the state of all towers, mobs and projectiles. 
	 * It also handles projectile collisions with mobs dying and such.
	 * @param timeDelta 
	 */
	public void updateModel(float timeDelta) {

		if (mMovableTower != null)
			mMovableTower.update(timeDelta);

		if (GAME_STATE == STATE_RUNNING) { //Only update if running

			// If the player has 0 or less lives remaining, change game state
			if (sCurrentPlayer.getRemainingLives() <= 0) {
				mSelectedTower = null;
				mCurrentSnowball = null;
				mCurrentTower = null;
				mShowTooltip = false;
				setFast(false);
				GAME_STATE = STATE_GAMEOVER;
				return;
			}
			
			Mob mNewMob = createMobs(timeDelta);
			if (mNewMob != null) {
				sMobs.add(mNewMob);
				if(mMobFactory.hasMoreWaves()){
					mCreatedLastWave = true;
				}
//				Log.v("GAME MOBS", "Added new mob of type: "
//						+ mNewMob.getType().toString());
			}

			// if the player has won (no more mobs and all mobs dead)
			if (mMobFactory.lastWaveHasEntered() && sMobs.isEmpty() && mCreatedLastWave && mMobFactory.getLastMobSent()) {
				mSelectedTower = null;
				mCurrentSnowball = null;
				mCurrentTower = null;
				mShowTooltip = false;
				setFast(false);
				sCurrentPlayer.saveCurrentTrackScore();
				GAME_STATE = STATE_WIN;
				return;
			}

			
			/*
			 * for every tower:
			 * 	create a new Projectile set to a Mob that the Tower can reach
			 *  and add that to the list of Projectiles in the GameModel
			 * 
			 * tryToShoot() returns null if the tower can't reach any mob or if the tower is on CD
			 */
			int size = sTowers.size();
			for (int i = 0; i < size; ++i) {
				Tower t = sTowers.get(i);
				t.update(timeDelta);
				Projectile newProjectile = null;

				//if there are any mobs, try to shoot at them
				if (sMobs.isEmpty() == false)
					newProjectile = t.getNextProjectile();

				//if a projectile was returned, add it to the game model
				if (newProjectile != null)
					sProjectiles.add(newProjectile);
				
			}

			// Check if any projectile has hit it's target
			// Handle hit, remove projectile, calculate damage on mob, etc. etc.
			size = sProjectiles.size();
			int removed = 0;
			for (int i = 0; i < size - removed; ++i) {
				Projectile p = sProjectiles.get(i);								
				
				// Update position for the projectiles
				p.update(timeDelta);

				// If the projectile has collided, inflict damage and remove it.
				if (p.hasCollided(timeDelta)) {					
					p.inflictDmg();
					p.mExploded = true;
					sShowExplForProj.add(p);
					sProjectiles.remove(p);
					++removed;
				}

				// if the projectile's target is dead, remove the projectile
				// this is fulkod to keep projectiles that never reached their target from staying on the gamefield
				if (p.getTarget().getHealth() <= 0) {					
					sProjectiles.remove(p);	
					++removed;
				}
			}
			
			
			

			for (int i = 0; i < sShowExplForProj.size(); ++i) {
				Projectile p = sShowExplForProj.get(i);								
				
				// Update position for the projectiles
				p.update(timeDelta);

				if (p.mExplAnimation > p.getExplosionTime())
					sShowExplForProj.remove(p);
				
			}
			
			
			
			

			/*
			 * For every snowball:
			 * update position
			 * do damage to any mob it hits
			 */
			for (int j = 0; j < sSnowballs.size(); ++j) {
				Snowball s = sSnowballs.get(j);

				// update position with accelerometer
				s.update(timeDelta);

				// read what mobs are hit
				List<Mob> deadMobs = s.getCollidedMobs(sMobs);

				// handle mobs that were hit
				for (int k = 0; k < deadMobs.size(); k++) {
					Mob deadMob = deadMobs.get(k);
					switch(deadMob.getType()) {

						case Mob.HEALTHY:	deadMob.setHealth((int) (0.993 * deadMob.getHealth())); break;
						default:deadMob.setHealth((int) (0.93 * deadMob.getHealth())); break;

					}
				}

				// if the snowball is out of charges, remove it
				if (s.getCharges() <= 0) {
					sSnowballs.remove(s);
				}
			}

			/*
			 * For every mob:
			 *  Update position
			 *  If the mob has died, handle it
			 */
			size = sMobs.size();
			removed = 0;
			for (int j = 0; j < size - removed; j++) {
				Mob m = sMobs.get(j);				

				m.update(timeDelta);
				
				if (m.isEnabled()) {
					
					if (m.hasReachedLastCheckpoint()) {
						mSplash = true;

						switch (m.getType()) {
						case Mob.HEALTHY:
							sCurrentPlayer.removeLife(5);
							break;
						default:
							sCurrentPlayer.removeLife(1);
							break;
						}

						sMobs.remove(m);
						++removed;
						// TODO fult, fixa
						GameView.mVibrator.vibrate(50);
					}
				}
				// handle mob death
				if (m.isDead() && m.isEnabled()) {
					m.mAnimation = m.mAnimationDeath;
					sCurrentPlayer.changeMoney(m.getReward());
					sCurrentPlayer.changeScore(m);					
					sShowRewardForMob.add(m);
					m.setEnabled(false);
				}
				
				if (m.isDead() && m.mAnimation < 0) {
					sMobs.remove(m);
					++removed;
				}
			}
		}
	}

	public void release() {

		// TODO Auto-generated method stub
		
	
	sTowers = null;
	sMobs = null;
	sShowRewardForMob = null;
	sShowExplForProj = null;
	sProjectiles = null;
	sSnowballs = null;
	sPath = null;
	sOccupiedTilePositions = null;
	sCurrentPlayer = null;
	}
}

