package com.chalmers.game.td;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundManager {

	private static MediaPlayer	sMenuMusic,
								sProgressionRouteMusic,
								sFastForwardMusic,								
								sTrackOneMusic,
								sTrackTwoMusic,
								sTrackThreeMusic,
								sTrackFourMusic,
								sTrackFiveMusic;
	private static SoundPool	sSounds;
	private static int			sWaterSplashSound;
	
	/**
	 * Constructor
	 */
	public SoundManager() {
		
	}
	
	/**
	 * 
	 * @param pFile
	 */
	public static void playSound(int pFile) {
		sSounds.play(pFile, 1, 1, 1, 0, 1);
	}
	
	/**
	 * 
	 * @param pFile
	 */
	public static void playMusic(MediaPlayer pFile) {
		
		if (!pFile.isPlaying()) {
		    pFile.seekTo(0);
		    pFile.start();
	    }
	}
	
	/**
	 * 
	 * @param pFile
	 */
	public static void pauseMusic(MediaPlayer pFile) {
		if (pFile.isPlaying()) pFile.pause();
	}
	
	/**
	 * 
	 */
	public static void releaseSounds() {
		
		if (sSounds == null)
			return;
		
	    sSounds.release();
	    
	    if (getFastForwardMusic().isPlaying() && getFastForwardMusic() != null) {
	    	getFastForwardMusic().stop();
	    	getFastForwardMusic().release();
	    }
	    if (getTrackOneMusic().isPlaying() && getTrackOneMusic() != null) {
	    	getTrackOneMusic().stop();
	    	getTrackOneMusic().release();
	    }
	    if (getTrackTwoMusic().isPlaying() && getTrackTwoMusic() != null) {
	    	getTrackTwoMusic().stop();
	    	getTrackTwoMusic().release();
	    }
	    if (getTrackThreeMusic().isPlaying() && getTrackThreeMusic() != null) {
	    	getTrackThreeMusic().stop();
	    	getTrackThreeMusic().release();
	    }
	    if (getTrackFourMusic().isPlaying() && getTrackFourMusic() != null) {
	    	getTrackFourMusic().stop();
	    	getTrackFourMusic().release();
	    }
	    if (getTrackFiveMusic().isPlaying() && getTrackFiveMusic() != null) {
	    	getTrackFiveMusic().stop();
	    	getTrackFiveMusic().release();
	    }
	    if(getProgressionRouteMusic().isPlaying() && getProgressionRouteMusic() != null) {
	    	getProgressionRouteMusic().stop();
	    	getProgressionRouteMusic().release();
	    }
	    if(getMenuMusic().isPlaying() && getMenuMusic() != null) {
	    	getMenuMusic().stop();
	    	getMenuMusic().release();
	    }
	}
	
	/**
	 * 
	 * @param pContext
	 */
	public static void initializeSound(Context pContext) {
		
		// Initialize the sound pool
		sSounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		
		// Initialize the sound effects
		setWaterSplashSound(sSounds.load(pContext, R.raw.water_splash, 1));	
		
		// Initialize the music
		setMenuMusic(MediaPlayer.create(pContext, R.raw.menu_music));
		setProgressionRouteMusic(MediaPlayer.create(pContext, R.raw.progression_route_music));
		setFastForwardMusic(MediaPlayer.create(pContext, R.raw.fast_forward_music));
		setTrackOneMusic(MediaPlayer.create(pContext, R.raw.track_1_music));
		setTrackTwoMusic(MediaPlayer.create(pContext, R.raw.track_2_music));
		setTrackThreeMusic(MediaPlayer.create(pContext, R.raw.track_3_music));
		setTrackFourMusic(MediaPlayer.create(pContext, R.raw.track_4_music));
		setTrackFiveMusic(MediaPlayer.create(pContext, R.raw.track_5_music));
		
	}

	public static void setWaterSplashSound(int pWaterSplashSound) {
		SoundManager.sWaterSplashSound = pWaterSplashSound;
	}

	public static int getWaterSplashSound() {
		return sWaterSplashSound;
	}

	public static void setTrackFiveMusic(MediaPlayer pTrackFiveMusic) {
		SoundManager.sTrackFiveMusic = pTrackFiveMusic;
	}

	public static MediaPlayer getTrackFiveMusic() {
		return sTrackFiveMusic;
	}

	public static void setTrackFourMusic(MediaPlayer pTrackFourMusic) {
		SoundManager.sTrackFourMusic = pTrackFourMusic;
	}

	public static MediaPlayer getTrackFourMusic() {
		return sTrackFourMusic;
	}

	public static void setTrackThreeMusic(MediaPlayer pTrackThreeMusic) {
		SoundManager.sTrackThreeMusic = pTrackThreeMusic;
	}

	public static MediaPlayer getTrackThreeMusic() {
		return sTrackThreeMusic;
	}

	public static void setTrackTwoMusic(MediaPlayer pTrackTwoMusic) {
		SoundManager.sTrackTwoMusic = pTrackTwoMusic;
	}

	public static MediaPlayer getTrackTwoMusic() {
		return sTrackTwoMusic;
	}

	public static void setTrackOneMusic(MediaPlayer pTrackOneMusic) {
		SoundManager.sTrackOneMusic = pTrackOneMusic;
	}

	public static MediaPlayer getTrackOneMusic() {
		return sTrackOneMusic;
	}

	public static void setFastForwardMusic(MediaPlayer pFastForwardMusic) {
		SoundManager.sFastForwardMusic = pFastForwardMusic;
	}

	public static MediaPlayer getFastForwardMusic() {
		return sFastForwardMusic;
	}

	public static void setProgressionRouteMusic(MediaPlayer pProgressionRouteMusic) {
		SoundManager.sProgressionRouteMusic = pProgressionRouteMusic;
	}

	public static MediaPlayer getProgressionRouteMusic() {
		return sProgressionRouteMusic;
	}

	public static void setMenuMusic(MediaPlayer pMenuMusic) {
		SoundManager.sMenuMusic = pMenuMusic;
	}

	public static MediaPlayer getMenuMusic() {
		return sMenuMusic;
	}
}
