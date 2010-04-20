package com.chalmers.game.td;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.chalmers.game.td.units.Mob;

import android.os.Environment;
import android.util.Log;

public class Highscore {

	private static final Highscore	INSTANCE = new Highscore();
	private double					mCurrentTrackScore;
	private double[]				mTrackScore;
	private File					mFile;
	private BufferedWriter			mWriter;
	private FileInputStream 		mFileIS;
	
	public void changeScore(Mob pMob) {
		setCurrentTrackScore(getCurrentTrackScore() + (pMob.getMaxHealth() / 10));
	}
	
	public double getCurrentTrackScore() {
		return mCurrentTrackScore;
	}
	
	private void setCurrentTrackScore(double pScore) {
		mCurrentTrackScore =  pScore;
		
	}
	
	public void saveScore() {
		
		mTrackScore[GameModel.getTrack() - 1] = mCurrentTrackScore;
		mCurrentTrackScore = 0;
		
		// TODO write to file below...
		try {
			
			mWriter = getWriter();
			
			for(int i = 0; i < mTrackScore.length; ++i) {
				
				mWriter.write("Track: " + String.valueOf(i+1) + "\n Score: " + String.valueOf((int)mTrackScore[i]) + "\n");
				Log.v("HIGHSCORE.saveScore", "Wrote score to file.");
			
			}
			
			mWriter.close();
			
		} catch(IOException ioe) {
			Log.v("HIGHSCORE.saveScore", "Couldn't write to file");
		}
		
	}
	
	public void setTracks(int pTracks) {
		mTrackScore = new double[pTracks];
		
		for(int i = 0; i < mTrackScore.length; ++i) {
			mTrackScore[i] = 0;
		}
	}
	
	public double getTrackScore(int pTrack) {
		//return mTrackScore[pTrack - 1];
		return 1.0;
	}
	
	public double getTotalScore() {
		
		double totalScore = 0;
		
		for(int i = 0; i < mTrackScore.length; ++i) {
			totalScore += mTrackScore[i];
		}
		
		return totalScore;
	}
	
	public BufferedWriter getWriter() {
		
		File root = Environment.getExternalStorageDirectory();
		
		if(root.canWrite()) {
			
			Log.v("HIGHSCORE CONSTRUCTOR", "File can write.");
			
			mFile = new File(root, "tddata.txt");
			
			try {
			
				mWriter = new BufferedWriter(new FileWriter(mFile));
			
			} catch (IOException e) {
				e.printStackTrace();
				Log.v("Highscore.getWriter", e.getMessage());
			}
		}
					
		return mWriter;		
	}
	
	public Highscore() {
					
		mCurrentTrackScore = 0;		
		
		// At first try to load data.score		
		try {
		
			Log.v("HIGHSCORE CONSTRUCTOR", "Try to load file");
			
			mFile = new File(Environment.getExternalStorageDirectory() + "/tddata.txt");
			mFileIS = new FileInputStream(mFile);
			
			// TODO get food, and THEEEEEN read from file...
			
		// If the file is not found, create it
		} catch(FileNotFoundException fnf) {
			
			Log.v("HIGHSCORE CONSTRUCTOR", "File not yet created.");
			
			try {
				
				mWriter = getWriter();
				mWriter.write("File created: " + Calendar.getInstance().get(Calendar.DATE) + "/" + Calendar.getInstance().get(Calendar.MONTH));
				mWriter.close();
				
				Log.v("HIGHSCORE CONSTRUCTOR", "File written to.");
			
			
			} catch(IOException ioe) {
				Log.v("HIGHSCORE CONSTRUCTOR", "Writing to file failed.");
			}
		}
	}
	
	public static Highscore getInstance() {
		return INSTANCE;
	}
}
