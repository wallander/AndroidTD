package com.chalmers.game.td.units;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.chalmers.game.td.MobFactory;

public class MobTest {
	private Mob m;

	@Before
	public void setUp(){
		m = MobFactory.createMob(Mob.MobType.GROUND);
	}
	
	@Test
	public final void testUpdateDirection() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetBitmap() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetBitmap() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetSpeed() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetType() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetType() {
		fail("Not yet implemented"); // TODO
	}

}
