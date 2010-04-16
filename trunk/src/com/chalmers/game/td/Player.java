package com.chalmers.game.td;

import com.chalmers.game.td.units.Mob;

public class Player {

	private String 			mName;
	private final double	STARTING_MONEY = 400;
	private double 			mMoney;
	private double			mCurrentTrackScore;
	private double[]		mTrackScore;
	
	private int mLives = 50;
	
	// Achievements
	
	public Player(int pTracks) {
		setName("Mr. Awesome");
		setMoney(STARTING_MONEY);
		mTrackScore = new double[pTracks];
	}
	
	public void changeScore(Mob m) {
		setCurrentTrackScore(getCurrentTrackScore() + (m.getMaxHealth() / 10));
	}
	
	public void saveCurrentTrackScore() {
		mTrackScore[GameModel.getTrack() - 1] = getCurrentTrackScore();
		saveScore();
	}
	
	public void saveScore() {
		
		// TODO implement possibility to save score to xml 
	}
	
	public double getTrackScore(int pTrack) {
		return mTrackScore[pTrack - 1];
	}
	
	/**
	 * TODO connect with xml somehow
	 * @return
	 */
	public double getTotalScore() {
		double mTotalScore = 0;
		
		for(int i = 0; i < mTrackScore.length; ++i) {
			mTotalScore += mTrackScore[i];
		}
		
		return mTotalScore;
	}

	public void changeMoney(double amount) {
		this.mMoney = this.mMoney + amount;
	}
	
	public void setMoney(double mMoney) {
		this.mMoney = mMoney;
	}

	public double getMoney() {
		return mMoney;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public String getName() {
		return mName;
	}

	public int getRemainingLives() {		
		return mLives;
	}

	public void removeLife() {
		mLives--;
	}

	/**
	 * TODO kanske kan ta bort...
	 * @param mCurrentTrackScore
	 */
	public void setCurrentTrackScore(double mCurrentTrackScore) {
		this.mCurrentTrackScore = mCurrentTrackScore;
	}

	public double getCurrentTrackScore() {
		return mCurrentTrackScore;
	}
}