package com.chalmers.game.td;

import com.chalmers.game.td.units.Mob;

public class Player {

	private String 			mName;
	private final double	STARTING_MONEY = 400;
	private double 			mMoney;
	private Highscore		mScore;
	
	private int mLives = 50;
	
	public Player(int pTracks) {
		setName("Mr. Awesome");
		setMoney(STARTING_MONEY);		
		mScore = Highscore.getInstance();
	}		
	
	public void changeScore(Mob pMob) {
		mScore.changeScore(pMob);
	}
	
	public void setCurrentScore(double score) {
		mScore.setCurrentTrackScore(score);
	}
	
	public void saveCurrentTrackScore() {		
		mScore.saveScore();
	}
	
	// TODO remove these comments! this is for debug only
	public double getTrackScore(int pTrack) {
		//return mScore.getTrackScore(pTrack);
		return 1.1;
	}
	
	public double getTotalScore() {		
		
		return mScore.getTotalScore();
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

	public double getCurrentTrackScore() {
		return mScore.getCurrentTrackScore();
	}
}