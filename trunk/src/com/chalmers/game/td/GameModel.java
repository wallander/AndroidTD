package com.chalmers.game.td;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import android.graphics.Bitmap;
import android.media.SoundPool;
import android.util.Log;

import com.chalmers.game.td.units.*;

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
		 Log.v("", "Konstuktorn startar");
		towers = new ArrayList<Tower>();
		 Log.v("", "Efter towers");
		mobs = new ArrayList<Mob>();
		 Log.v("", "Efter mobs");
		projectiles = new ArrayList<Projectile>();
		 Log.v("", "Efter PRO");
		
		
		/*
		 * Hårdkodar in ett torn och en mob
		 */
		 Log.v("", "Skapa tower");
		towers.add(new Tower());
		 Log.v("", "skapa mob");
		mobs.add(new Mob());
		
	}
	
	

	
}
