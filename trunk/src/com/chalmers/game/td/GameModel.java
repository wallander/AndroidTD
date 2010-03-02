package com.chalmers.game.td;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import android.graphics.Bitmap;
import android.media.SoundPool;

import com.chalmers.game.td.units.*;
import com.chalmers.game.td.units.Tower;

public class GameModel {



	
	/*
	 * Lista med alla torn
	 * Lista med alla mobs
	 */
	
	protected List<Tower> towers;
	protected List<Mob> mobs;
	protected List<Projectile> projectiles;
	
	/**
	 * Constructor
	 */
	public GameModel() {
		towers = new ArrayList<Tower>();
		mobs = new ArrayList<Mob>();
		projectiles = new ArrayList<Projectile>();
		
		
		/*
		 * Hårdkodar in ett torn och en mob
		 */
		
		towers.add(new Tower());
		mobs.add(new Mob());
		
	}


	
	
	
}
