package com.chalmers.game.td.units;

import com.chalmers.game.td.GameModel;
import com.chalmers.game.td.GameView;
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
	private final float mAnimationWalk = 1.3f;
	public final float mAnimationDeath = 0.8f;
	public float mAnimation;
	
	/** Mob health */
	private int mHealth;
	
	/** Mob movement speed */
	private double mSpeed;
	
	/** Mob movement angle */
	private double mAngle;
	
	
	/** Amount of money recieved when killing this mob */
	private int mReward;
	
	/**
	 * Enum for the mob type. One for each type of mob.
	 */
	public static final int HEALTHY=0, AIR=1, FAST=2, NORMAL=3, IMMUNE=4;
	
	/** Mob type (Ground, air or invisible) */
	private int mType;	
	
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

	/** Placement on the road relative to the other mobs */
	private double mDistanceWalked = 0;
	private boolean mEnabled = true;
	
  
    /**
     * Extra constructor for Mobs, used for setting health directly from xml-file
     * May be merged with the old one if the rest of the group approves
     * (2010-04-06) by Jonas
     * @param pType
     */
    public Mob(int pType, int pHealth) {
    	this(pType);		//anropar den andra kontruktorn
    	setHealth(pHealth);
    	setMaxHealth(pHealth);
    	mAnimation = mAnimationWalk;
    	
    	switch (pType){
    	case Mob.NORMAL:
    		
    		
        	if (pHealth <= 110) {
        		setReward(10);
        	} else if(pHealth <= 790) {
        		setReward(20);
        	} else if(pHealth <= 1200) {
        		setReward(30);
        	} else if(pHealth <= 2000) {
        		setReward(40);
        	} else  {
        		setReward(50);
        	}    	
        	break;
    	case Mob.AIR:
        	if (pHealth <= 110) {
        		setReward(10);
        	} else if(pHealth <= 790) {
        		setReward(20);
        	} else if(pHealth <= 1200) {
        		setReward(30);
        	} else if(pHealth <= 2000) {
        		setReward(40);
        	} else  {
        		setReward(50);
        	}
        	break;
    	case Mob.FAST:
    		if (pHealth <= 110) {
        		setReward(10);
        	} else if(pHealth <= 790) {
        		setReward(20);
        	} else if(pHealth <= 1200) {
        		setReward(30);
        	} else if(pHealth <= 2000) {
        		setReward(40);
        	} else  {
        		setReward(50);
        	}
    		break;
    	case Mob.HEALTHY:
        	if (pHealth <= 110) {
        		setReward(10);
        	} else if(pHealth <= 790) {
        		setReward(20);
        	} else if(pHealth <= 1200) {
        		setReward(50);
        	} else if(pHealth <= 2000) {
        		setReward(100);
        	} else  {
        		setReward(200);
        	}
        	break;
     	case Mob.IMMUNE:
        	if (pHealth <= 110) {
        		setReward(15);
        	} else if(pHealth <= 790) {
        		setReward(25);
        	} else if(pHealth <= 1200) {
        		setReward(45);
        	} else if(pHealth <= 2000) {
        		setReward(50);
        	} else  {
        		setReward(60);
        	}    	
        	break;
   
    	}
    	
    }
	
	
    /**
     * Currently used constructor for Mobs
     * (2010-03-24)
     * @param pType
     */
    public Mob(int pType) {
    	mType = pType;
    	    	
        setSpeed(30);      
        setHealth(20);
        setMaxHealth(20);
        setReward(10);                
        
        setSize(24);
        
        if(pType == Mob.HEALTHY) {
    		setSpeed(20);
    	} else if(pType == Mob.FAST) {
    		setSpeed(45);
    	}
    }
    
    /**
     * TODO 
     * @return
     */
	public int getMobImage(){
		
    	switch (this.getType()){
    	case Mob.NORMAL:
    		if (isDead() == false) {
    			if (mAnimation >= 3*mAnimationWalk/4) {
    				return R.drawable.penguinmob;
    			} else if (mAnimation >= 2*mAnimationWalk/4) {
    				return R.drawable.penguinmobright;
    			} else if (mAnimation >= 1*mAnimationWalk/4) {
    				return R.drawable.penguinmob;
    			} else {
    				return R.drawable.penguinmobleft;
    			} 
    			
    		} else {
    			// om moben är död. Man kan göra likt ovan, men man jämför med mAnimationDeath istället
    			// Alphan ändras också i GameView.drawMobs beroende på hur långt animationen kommit
    			return R.drawable.penguinmob;
    		}
       	
    	case Mob.AIR:
    		return R.drawable.flyingpenguin;

        	
    	case Mob.FAST:
    		return R.drawable.bear;

    	case Mob.HEALTHY:
    		return R.drawable.walrus;
    		// animation för att "guppa"
    		
     	case Mob.IMMUNE:
     		return R.drawable.icebear;

     	default:
     		return R.drawable.penguinmob;
    	}
		
	}
	
    public void setPath(Path pPath) {
    	
    	mPath = pPath;
    	setCoordinates(mPath.getCoordinate(0));
    	setCheckpoint(0);
    	updateAngle();
    	
    	updatePosition(0);
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
    public void setType(int pType) {
        mType = pType;
    }

    /**
     * @return The type of the instance.
     */
    public int getType() {
        return mType;
    }

	/**
	 * @param mHealth the mHealth to set
	 */
	public void setHealth(int pHealth) {
		mHealth = pHealth;
	}

	/**
	 * @return the mHealth
	 */
	public int getHealth() {
		return mHealth;
	}
	
	public void takeDamage(int pDamage){
		mHealth -= pDamage;
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
	 * @param timeDelta 
	 */
	
	public boolean updatePosition(float timeDelta) {

		// if the mob reached his current checkpoint, change direction		
		if (reachedCheckpoint(timeDelta)) {
			setCheckpoint(getCheckpoint()+1);

			if (mPath.getCoordinate(getCheckpoint()) == null) {
				return false;
			}
			updateAngle();

			mSpeedX = getSpeed() * Math.cos(getAngle());
			mSpeedY = getSpeed() * Math.sin(getAngle());

		}

		if(isSlowed()){
			setX(getX() + timeDelta*GameModel.getSpeedMultiplier()*mSpeedX*mSlowedSpeed);
			setY(getY() - timeDelta*GameModel.getSpeedMultiplier()*mSpeedY*mSlowedSpeed);
			mDistanceWalked += getSpeed()*mSlowedSpeed;
			mSlowLeft -= GameModel.getSpeedMultiplier();
		} else {
			setX(getX() + timeDelta*GameModel.getSpeedMultiplier()*mSpeedX);
			setY(getY() - timeDelta*GameModel.getSpeedMultiplier()*mSpeedY);
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
	public boolean reachedCheckpoint(float timeDelta) {
	
		double sqrDistance = Coordinate.getDistance(this.getCoordinates(), mPath.getCoordinate(mCheckpoint));
		if (sqrDistance < timeDelta*getSpeed()*GameModel.getSpeedMultiplier())
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
	


//	/**
//	 * @param mAnimation the mAnimation to set
//	 */
//	public int nextAnimation(int max) {
//		
//		
//		mAnimation++;
//		if (mAnimation >= max) {
//			mAnimation = 0;
//		}
//		return mAnimation; 
//	}

	public String toString() {
		switch(mType) {
		case HEALTHY: return "Boss";
		case AIR: return "Air";
		case NORMAL: return "Normal";
		case FAST: return "Fast";
		case IMMUNE: return "Immune";
		default: return "-";
		}
	}

	public boolean isDead() {
		return mHealth <= 0;
	}

	public void updateAnimation(float timeDelta) {
		mAnimation -= timeDelta;
		
		if (!isDead() && mAnimation < 0) {
			mAnimation += mAnimationWalk;
		}
				
	}


	public void setEnabled(boolean b) {
		// TODO Auto-generated method stub
		mEnabled  = b;
	}


	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return mEnabled;
	}
}