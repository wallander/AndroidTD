package com.chalmers.game.td.units;

import com.chalmers.game.td.GamePanel;
import com.chalmers.game.td.Path;
import com.chalmers.game.td.Coordinate;
import com.chalmers.game.td.R;
import com.chalmers.game.td.units.Unit;
import android.util.Log;

/**
 * Class which represents a Mob on the game board.
 * 
 * @author Fredrik Persson
 * @author Jonas Andersson
 * @author Ahmed Chaban
 * @author Jonas Wallander
 * @author Disa Faith
 * @author Daniel Arvidsson
 */
public class Mob extends Unit{

	
	/** Mob max health */
	private int mMaxHealth;
	
	private int mAnimation;
	
	
	/** Mob health */
	private int mHealth;
	
	/** Mob movement speed */
	private double mSpeed;
	
	/** Mob movement angle */
	private double mAngle;
	
	/** Mob armor */
	private int mArmor;
	
	// how much money you get when you kill this mob
	private int mReward;
	
	/** Mob type (Ground, air or invisible) */
	private MobType mType;	
	
	/** Path Checkpoint */
	private int mCheckpoint;
	
	/** Path */
	private Path mPath;
	
	private int mRewardAnimation = 0;
	
	/** Speed */
	private double mSpeedX;
	private double mSpeedY;

	private int mSlowLeft = 0;

	private double mSlowedSpeed;
	
	private int mobImage = R.drawable.penguinmob;

	/** Placement on the road relative to the other mobs */
	private double mDistanceWalked = 0;
	
	/**
	 * Enum for the mob type. One for each type of mob.
	 */
	public enum MobType { HEALTHY, AIR, FAST, NORMAL, IMMUNE}
  
    /**
     * Extra constructor for Mobs, used for setting health directly from xml-file
     * May be merged with the old one if the rest of the group approves
     * (2010-04-06) by Jonas
     * @param pType
     */
    public Mob(MobType pType, int pHealth) {
    	this(pType);		//anropar den andra kontruktorn
    	setHealth(pHealth);
    	setMaxHealth(pHealth);
    	mAnimation = 0;
    	if(pType == MobType.NORMAL){
        	if (pHealth <= 110) {
        		setReward(10);
        		setMobImage(R.drawable.penguinmob);
        	} else if(pHealth <= 790) {
        		setReward(20);
        		setMobImage(R.drawable.penguinmob);
        	} else if(pHealth <= 1200) {
        		setReward(30);
        		setMobImage(R.drawable.penguinmob);
        	} else if(pHealth <= 2000) {
        		setReward(40);
        		setMobImage(R.drawable.penguinmob);
        	} else  {
        		setMobImage(R.drawable.penguinmob);
        		setReward(50);
        	}    		
    	} else if (pType == MobType.AIR){
        	if (pHealth <= 110) {
        		setReward(10);
        		setMobImage(R.drawable.flyingpenguin);
        	} else if(pHealth <= 790) {
        		setReward(20);
        		setMobImage(R.drawable.flyingpenguin);
        	} else if(pHealth <= 1200) {
        		setReward(30);
        		setMobImage(R.drawable.flyingpenguin);
        	} else if(pHealth <= 2000) {
        		setReward(40);
        		setMobImage(R.drawable.flyingpenguin);
        	} else  {
        		setMobImage(R.drawable.flyingpenguin);
        		setReward(50);
        	}
    	} else if (pType == MobType.FAST){
        	if (pHealth <= 110) {
        		setReward(10);
        		setMobImage(R.drawable.bear);
        	} else if(pHealth <= 790) {
        		setReward(20);
        		setMobImage(R.drawable.bear);
        	} else if(pHealth <= 1200) {
        		setReward(30);
        		setMobImage(R.drawable.icebear);
        	} else if(pHealth <= 2000) {
        		setReward(40);
        		setMobImage(R.drawable.icebear);
        	} else  {
        		setMobImage(R.drawable.icebear);
        		setReward(50);
        	}
    	} else if (pType == MobType.HEALTHY){
        	if (pHealth <= 110) {
        		setReward(10);
        		setMobImage(R.drawable.walrus);
        	} else if(pHealth <= 790) {
        		setReward(20);
        		setMobImage(R.drawable.walrus);
        	} else if(pHealth <= 1200) {
        		setReward(30);
        		setMobImage(R.drawable.walrus);
        	} else if(pHealth <= 2000) {
        		setReward(40);
        		setMobImage(R.drawable.walrus);
        	} else  {
        		setMobImage(R.drawable.walrus);
        		setReward(50);
        	}
    	} 
    	
    	

    }
	
	
    /**
     * Currently used constructor for Mobs
     * (2010-03-24)
     * @param pType
     */
    public Mob(MobType pType) {
    	mType = pType;
    	    	
        setSpeed(1.2);      
        setHealth(20);
        setMaxHealth(20);
        setArmor(20);
        setReward(10);                
        
        // TODO: fix dynamic size
        setSize(24);
        
        if(pType == MobType.HEALTHY) {
    		setSpeed(0.5);
    	} else if(pType == MobType.FAST) {
    		setSpeed(1.6);
    	}
    }
    
    
	public int getMobImage(){
		
		return mobImage;
	}
	
	public void setMobImage(int image){
		mobImage = image;
	}
    
    public void setPath(Path pPath) {
    	
    	mPath = pPath;
    	setCoordinates(mPath.getCoordinate(0));
    	setCheckpoint(0);
    	updateAngle();
    	
    	updatePosition();
    }
    
    public void incRewAni(){
    	mRewardAnimation++;
    }
    
    public int getRewAni(){
    	return mRewardAnimation;
    }
	
    /**
     * Setter for mob movement speed
     * @param d
     */
	private void setSpeed(double d) {
		mSpeed = d;
	}

	/**
	 * Setter for mob armor
	 * @param i
	 */
	private void setArmor(int i) {
		mArmor = i;
	}

    /**
     * Setter for which checkpoint the mob is walking to.
     * 
     * @param pCheckpoint
     */
    public void setCheckpoint(int pCheckpoint) {
    	mCheckpoint = pCheckpoint;
    }
    
    /**
     * @return
     */
    public double getSpeed() {
        return mSpeed;
    }

    public boolean isBefore(Mob pMob){
    	if (mDistanceWalked > pMob.getDistanceWalked())
    		return true;
    	else
    		return false;
    }
    
    public double getDistanceWalked(){
    	return mDistanceWalked;
    }

    /**
     * @param
     */
    public void setType(MobType pType) {
        mType = pType;
    }

    /**
     * @return The type of the instance.
     */
    public MobType getType() {
        return mType;
    }

	/**
	 * @param mHealth the mHealth to set
	 */
	public void setHealth(int mHealth) {
		this.mHealth = mHealth;
	}

	/**
	 * @return the mHealth
	 */
	public int getHealth() {
		return mHealth;
	}
	
	/**
	 * Settes for mob movement angle
	 * @param mAngle
	 */
	public void setAngle(double mAngle) {
		this.mAngle = mAngle;
	}

	/**
	 * Getter for mob movement angle
	 * @return
	 */
	public double getAngle() {
		return mAngle;
	}

	/**
	 * Updates the mobs position according to speed and angle.
	 */
	
	public boolean updatePosition() {

		// if the mob reached his current checkpoint, change direction		
		if (reachedCheckpoint()) {
			setCheckpoint(getCheckpoint()+1);

			if (mPath.getCoordinate(getCheckpoint()) == null) {
				Log.v("MOB EVENT","NEXT COORDINATE IS NULL"); // TODO Kan ta bort efter debug
				return false;
			}
			updateAngle();

			mSpeedX = getSpeed() * Math.cos(getAngle());
			mSpeedY = getSpeed() * Math.sin(getAngle());

		}

		if(isSlowed()){
			setX(getX() + GamePanel.getSpeedMultiplier()*mSpeedX*mSlowedSpeed);
			setY(getY() - GamePanel.getSpeedMultiplier()*mSpeedY*mSlowedSpeed);
			mDistanceWalked += getSpeed()*mSlowedSpeed;
			mSlowLeft -= GamePanel.getSpeedMultiplier();
		} else {
			setX(getX() + GamePanel.getSpeedMultiplier()*mSpeedX);
			setY(getY() - GamePanel.getSpeedMultiplier()*mSpeedY);
			mDistanceWalked += mSpeed;
		}

		return true;
	}

	/**
	 * Getter for the current checkpoint number
	 * @return
	 */
	private int getCheckpoint() {
		return mCheckpoint;
	}

	/**
	 * Method that checks whether the mob has reached its current checkpoint
	 * 
	 * @return
	 */
	public boolean reachedCheckpoint() {
	
		double sqrDistance = Coordinate.getSqrDistance(this.getCoordinates(), mPath.getCoordinate(mCheckpoint));
		if (sqrDistance < GamePanel.getSpeedMultiplier()*getSpeed()*getSpeed())
			return true;
		
		return false;
		
	}
	
	/**
	 * Update the mobs movement angle according to its current checkpoint.
	 */
	public void updateAngle() {
		setAngle(Coordinate.getAngle(this.getCoordinates(), mPath.getCoordinate(mCheckpoint)));
		
	}

	public void setSlowed(int time, double slow) {
		mSlowLeft   = time;
		mSlowedSpeed = slow;
	}
	
	public boolean isSlowed() {
		return (mSlowLeft > 0);
	}
	
	public void setMaxHealth(int mMaxHealth) {
		this.mMaxHealth = mMaxHealth;
	}

	public int getMaxHealth() {
		return mMaxHealth;
	}

	public void setReward(int mReward) {
		this.mReward = mReward;
	}

	public int getReward() {
		return mReward;
	}


	/**
	 * @param mAnimation the mAnimation to set
	 */
	public int nextAnimation(int max) {
		mAnimation++;
		if (mAnimation >= max) {
			mAnimation = 0;
		}
		return mAnimation; 
	}


}