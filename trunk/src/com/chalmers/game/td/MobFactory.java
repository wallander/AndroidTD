package com.chalmers.game.td;

import com.chalmers.game.td.units.Mob;
import com.chalmers.game.td.units.Mob.MobType;

public class MobFactory {
	
	public MobFactory(){
		
	}
	
	public Mob CreateMob(MobType pType){
		Mob m;
		switch (pType){
			case HIHEALTH: m = new Mob(Mob.MobType.AIR);
			case GROUND: m = new Mob(Mob.MobType.GROUND);
			case FAST: m = new Mob();
			default: ;
		}
	}
}
