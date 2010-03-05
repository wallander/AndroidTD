package com.chalmers.game.td;

import com.chalmers.game.td.units.Mob;
import com.chalmers.game.td.units.Mob.MobType;

/**
 * Factory class that is used to create mobs of different kinds.
 * 
 * @author Fredrik Persson
 * @author Jonas Andersson
 * @author Ahmed Chaban
 * @author Jonas Wallander
 * @author Disa Faith
 * @author Daniel Arvidsson
 */
public class MobFactory {
	
	public MobFactory(){
		
	}
	
	public static Mob createMob(MobType pType){
		switch (pType){
			case HIHEALTH: 
				return new Mob(Mob.MobType.HIHEALTH);
			case GROUND: 
				return new Mob(Mob.MobType.GROUND);
			case FAST: 
				return new Mob(Mob.MobType.FAST);
			default: 
				return null ;
		}
		
	}
	/*
	public Mob createMob(int pType){
		
		Mob m;
		switch (pType) {
			case 1: m = new Mob(Mob.MobType.AIR);
			case 2: ;
			default: return -1;
			
		}
		*/
	
}
