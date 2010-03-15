package com.chalmers.game.td;

import android.util.Log;

public class TDDebug {

	
	// Some global variables used to measure the time
	float  timeAtGameStart;
	Integer ticksPerSecond;
	
	// Called once at the start of the game
	public void InitGameTime()
	{
	  // We need to know how often the clock is updated
	  
	    ticksPerSecond = 1000000000;
	  // If timeAtGameStart is 0 then we get the time since
	  // the start of the computer when we call GetGameTime()
	  timeAtGameStart = 0;
	  timeAtGameStart = GetGameTime();
	}
	// Called every time you need the current game time
	public float GetGameTime()
	{
	  Long ticks;
	  float time;
	  // This is the number of clock ticks since start
	  
	    ticks = java.lang.System.nanoTime();
	  // Divide by frequency to get the time in seconds
	  time = (float)ticks/(float)ticksPerSecond;
	  // Subtract the time at game start to get
	  // the time since the game started
	  time -= timeAtGameStart;
	  return time;
	}
	
	
	// Global variables for measuring fps
	float lastUpdate        = 0;
	float fpsUpdateInterval = 0.5f;
	Integer  numFrames         = 0;
	float fps               = 0;
	// Called once for every frame
	public void UpdateFPS()
	{
	  numFrames++;
	  float currentUpdate = GetGameTime();
	  if( currentUpdate - lastUpdate > fpsUpdateInterval )
	  {
	    fps = numFrames / (currentUpdate - lastUpdate);
	  
	    lastUpdate = currentUpdate;
	    numFrames = 0;
	  }
	}
	public float getFPS() {
		
	return fps;
	}
	}
	

