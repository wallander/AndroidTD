package com.chalmers.game.td;

public class Player {

	private String mName;
	private double mMoney;
	private int mExperience;
	
	private int mLives = 50;
	
	// Achievements
	
	public Player() {
		setName("noname");
		setMoney(400);

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
		// TODO Auto-generated method stub
		return mLives;
	}

	public void removeLife() {
		mLives--;
	}
	
}
