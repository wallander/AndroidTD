package com.chalmers.game.td;

import com.chalmers.game.td.units.Mob;
import com.chalmers.game.td.units.Mob.MobType;

/**
 * 
 * @author Jonas Andersson, Daniel Arvidsson, Ahmed Chaban, Disa Faith, Fredrik Persson, Jonas Wallander
 *
 */
public class MobFactory {
	
	public MobFactory(){
		
	}
	
	public static Mob CreateMob(MobType pType){
		
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
	public Mob CreateMob(int pType){
		
		Mob m;
		switch (pType) {
			case 1: m = new Mob(Mob.MobType.AIR);
			case 2: ;
			default: return -1;
			
		}
		*/
	
}
