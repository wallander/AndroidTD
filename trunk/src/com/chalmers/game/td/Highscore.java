package com.chalmers.game.td;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.chalmers.game.td.units.Mob;

import android.os.Environment;
import android.util.Log;

public class Highscore {

	private static final Highscore	INSTANCE = new Highscore();
	private double					mCurrentTrackScore;
	private double[]				mTrackScore;
	private File					mFile;
	private BufferedWriter			mWriter;
	private BufferedReader	 		mReader;
	private Map<Integer, Integer>	mSavedScore;
	
	public void changeScore(Mob pMob) {
		setCurrentTrackScore(getCurrentTrackScore() + (pMob.getMaxHealth() / 10));
	}
	
	public double getCurrentTrackScore() {
		return mCurrentTrackScore;
	}
	
	private void setCurrentTrackScore(double pScore) {
		mCurrentTrackScore =  pScore;
	}
	
	public double[] loadScore() {
		
		double[] loadedScores = new double[mSavedScore.size()];
		
		if(mSavedScore == null) {
			return null;
		} else {
		
			for(int i = 0; i < loadedScores.length; ++i) {
				
				loadedScores[i] = mSavedScore.get(i+1);
			}
		}
		
		return loadedScores;
	}
	
	public void saveScore() {
		
		mTrackScore[GameModel.getTrack() - 1] = mCurrentTrackScore;
		mCurrentTrackScore = 0;
		
		// TODO write to file below...
		try {
			
			mWriter = getWriter();
			
			for(int i = 0; i < mTrackScore.length; ++i) {
				
				mWriter.write("Track " + String.valueOf(i+1) + "\n Score " + String.valueOf((int)mTrackScore[i]) + "\n");
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
		return mTrackScore[pTrack - 1];
	}
	
	public double getTotalScore() {
		
		double totalScore = 0;
		
		for(int i = 0; i < mTrackScore.length; ++i) {
			totalScore += mTrackScore[i];
		}
		
		return totalScore;
	}
	
	private BufferedReader getReader() throws FileNotFoundException {
		
		mFile = new File(Environment.getExternalStorageDirectory() + "/tddata.txt");
		mReader = new BufferedReader(new InputStreamReader(new FileInputStream(mFile)));
		
		return mReader;
	}
	
	private BufferedWriter getWriter() {
		
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
		mSavedScore = new HashMap<Integer, Integer>();
		
		// At first try to load data.score		
		try {
		
			Log.v("HIGHSCORE CONSTRUCTOR", "Try to load file");
			
			
			mReader = getReader();
			
			String 		readLine = "";
			String[] 	input = new String[2];
			int			track = 0;
			
			try {
				
				while((readLine = mReader.readLine()) != null) {
					
					Log.v("Highscore.constructor", "Read line..." + readLine);
					
					input = readLine.split(" ");
					
					Log.v("Highscore.constructor", "input[0] = " + input[0] + " input[1] " + input[1]);
					
					if(input[0].equals("Track")) {
						Log.v("Highscore.constructor", "Read track... track is " + String.valueOf(input[1]));
						track = Integer.parseInt(input[1]);
						
					} else if(input[0].equals("Score")) {
						Log.v("Highscore.constructor", "Read score... score is " + String.valueOf(input[1]));
						if(!input[1].equals("0")) {
							
							mSavedScore.put(track, Integer.parseInt(input[1]));
						}
					}
				}								
				
			} catch (IOException e) {
				
				Log.v("Highscore.constructor", "IOEXCEPTION!!" + e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		// If the file is not found, create it
		} catch(FileNotFoundException fnf) {
			
			Log.v("HIGHSCORE CONSTRUCTOR", "File not yet created.");
			
			try {
				
				mWriter = getWriter();
				mWriter.write("File created: " + Calendar.getInstance().get(Calendar.DATE) + "/" + Calendar.getInstance().get(Calendar.MONTH));
				mWriter.close();
				
				Log.v("HIGHSCORE CONSTRUCTOR", "File created.");
			
			
			} catch(IOException ioe) {
				Log.v("HIGHSCORE CONSTRUCTOR", "Creating file failed.");
			}
		}
	}
	
	public static Highscore getInstance() {
		return INSTANCE;
	}
}
