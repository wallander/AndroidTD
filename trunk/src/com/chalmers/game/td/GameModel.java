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
 * 
 * @author @author Jonas Andersson, Daniel Arvidsson, Ahmed Chaban, Disa Faith, Fredrik Persson, Jonas Wallander
 *
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
		 Log.v("", "Konstuktorn startar");
		mTowers = new ArrayList<Tower>();
		 Log.v("", "After towers");
		mMobs = new ArrayList<Mob>();
		 Log.v("", "After mobs");
		mProjectiles = new ArrayList<Projectile>();
		 Log.v("", "After PRO");
		 mPath = new Path();
		
		
		// Temporary code to add a tower and a mob for testing purposes	
		 Log.v("", "Skapa tower");
		//mTowers.add(new Tower());
		 Log.v("", "skapa mob");

		mMobs.add(new Mob(mPath));
		
		Mob a,b,c;
		
		a = MobFactory.CreateMob(Mob.MobType.GROUND);
		
		//b = MobFactory.CreateMob(Mob.MobType.GROUND);
		//c = MobFactory.CreateMob(Mob.MobType.GROUND);
		
		
		
		mMobs.add(a);
		//mobs.add(b);
		//mobs.add(c);
		/////////////////////////////////////////////////////////////////////////////
	}
	
	

	
}
