package com.chalmers.game.td;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import com.chalmers.game.td.units.Mob;

import android.os.Environment;
import android.util.Log;

public class Highscore {

	private static final Highscore	INSTANCE = new Highscore();
	private double					mCurrentTrackScore;
	private File					mFile;
	private BufferedWriter			mWriter;
	private BufferedReader	 		mReader;
	private ArrayList<Double>	mSavedScore;
	
	/**
	 * Adds score when a mob is killed. Higher score is given if the mob hasn't walked very far.
	 * @param pMob
	 */
	public void changeScore(Mob pMob) {
		if (pMob.getDistanceWalked() < 500) {
			setCurrentTrackScore(getCurrentTrackScore() +
					(pMob.getMaxHealth() / 10) * (1 - 0.75*pMob.getDistanceWalked()/500 ));
		}
		else {
			
		}
	}
	
	public double getCurrentTrackScore() {
		return mCurrentTrackScore;
	}
	
	public void setCurrentTrackScore(double pScore) {
		mCurrentTrackScore =  pScore;
		
	}

	public void saveScore() {
		
		if(mSavedScore.get(GameModel.getTrack() - 1) < mCurrentTrackScore) {
			

			mSavedScore.set(GameModel.getTrack()-1, mCurrentTrackScore);

			try {
				
				mWriter = getWriter();
				
				for(int i = 0; i < mSavedScore.size(); ++i) {
																		
					mWriter.write("Track " + String.valueOf(i+1));
					mWriter.write("\n");
					mWriter.write("Score " + String.valueOf((mSavedScore.get(i))));
					mWriter.write("\n");
					
					Log.v("HIGHSCORE.saveScore", "Wrote to file:" + "Score " + String.valueOf(mSavedScore.get(i)));
				
				}
				
				mWriter.close();			
				
			} catch(IOException ioe) {
				Log.v("HIGHSCORE.saveScore", "Couldn't write to file");
			}
		}
		
		//mCurrentTrackScore = 0;
	}
	
	public double getTrackScore(int pTrack) {
		return mSavedScore.get(pTrack-1);
	}
	
	public double getTotalScore() {
		
		double totalScore = 0;
		
		for(int i = 0; i < mSavedScore.size(); ++i) {
			totalScore += mSavedScore.get(i);
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
	
	private Highscore() {
		initiateHighscore();
	
	}
	
	private void initiateHighscore() {
		mCurrentTrackScore = 0;	
		mSavedScore = new ArrayList<Double>();
		
		mSavedScore.add(0,0.0);
		mSavedScore.add(1,0.0);
		mSavedScore.add(2,0.0);
		mSavedScore.add(3,0.0);
		mSavedScore.add(4,0.0);
		
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
					
					Log.v("Highscore.constructor", "input[0] = " + input[0] + " input[1] = " + input[1]);
					
					if(input[0].equals("Track")) {
						Log.v("Highscore.constructor", "Read track... track is " + String.valueOf(input[1]));
						track = Integer.parseInt(input[1]);
						
					} else if(input[0].equals("Score")) {
						Log.v("Highscore.constructor", "Read score... score is " + String.valueOf(input[1]));
													
						mSavedScore.add(track,Double.parseDouble(String.valueOf(input[1])));
						
					}
				}

			} catch (IOException e) {
				
				Log.v("Highscore.constructor", "IOEXCEPTION!!" + e.getMessage());
				e.printStackTrace();
			}
			
			
		// If the file is not found, create it
		} catch(FileNotFoundException fnf) {
			
			Log.v("HIGHSCORE CONSTRUCTOR", "File not yet created.");
			
			try {
				
				mWriter = getWriter();
				mWriter.write("File created: " + Calendar.getInstance().get(Calendar.DATE) + "/" + Calendar.getInstance().get(Calendar.MONTH));
				mWriter.write("Track 1\n Score 0.0\n");
				mWriter.write("Track 2\n Score 0.0\n");
				mWriter.write("Track 3\n Score 0.0\n");
				mWriter.write("Track 4\n Score 0.0\n");
				mWriter.write("Track 5\n Score 0.0\n");
				mWriter.close();
				
				Log.v("HIGHSCORE CONSTRUCTOR", "File created.");
			
			
			} catch(IOException ioe) {
				Log.v("HIGHSCORE CONSTRUCTOR", "Creating file failed.");
			}
		}
	}
	
	/**
	 * Removes the "tddata.txt"-file from the SD-card, reseting the highscores.
	 */
	public static void resetHighscore() {
		new File(Environment.getExternalStorageDirectory() + "/tddata.txt").delete();
		getInstance().initiateHighscore();
	}
	
	public static Highscore getInstance() {
		return INSTANCE;
	}
}
