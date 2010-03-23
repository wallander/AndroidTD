package com.chalmers.game.td;

public class Player {

	private String mName;
	private int mMoney;
	private int mExperience;
	
	private int mLives = 50;
	
	// Achievements
	
	public Player() {
		setName("noname");
		setMoney(100);
	}

	public void setMoney(int mMoney) {
		this.mMoney = mMoney;
	}

	public int getMoney() {
		return mMoney;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public String getName() {
		return mName;
	}

	public int getRemainingLives() {
		// TODO Auto-generated method stub
		return mLives;
	}

	public void removeLife() {
		mLives--;
	}
	
	
}
