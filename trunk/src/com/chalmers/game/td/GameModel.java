package com.chalmers.game.td;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import android.graphics.Bitmap;
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
	
	/**
	 * Constructor
	 */
	public GameModel() {
		mTowers = new ArrayList<Tower>();
		mMobs = new ArrayList<Mob>();
		mProjectiles = new ArrayList<Projectile>();
		mPath = new Path();
		
		
		// Temporary code to add a tower and a mob for testing purposes	

		 Log.v("", "Skapa tower");
		 mTowers.add(new Tower());
		 Log.v("", "skapa mob");

		 // adds a new mob to the gamefield with a predefined path
		mMobs.add(new Mob(mPath));


		//Mob a,b,c;
		
		//a = MobFactory.CreateMob(Mob.MobType.GROUND);
		
		//b = MobFactory.CreateMob(Mob.MobType.GROUND);
		//c = MobFactory.CreateMob(Mob.MobType.GROUND);
		

	
	}
}

