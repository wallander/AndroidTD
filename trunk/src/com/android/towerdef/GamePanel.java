package com.android.towerdef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Custom SurfaceView to handle everything we need from physics to drawings.
 * 
 * @author Jonas
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    
    /**
     * Thread which contains our game loop.
     */
    private GameThread _thread;
    
    /**
     * List of graphics we already have to handle.
     */
    private ArrayList<Tower> _graphics = new ArrayList<Tower>();
    
    /**
     * List of explosions we have to draw.
     */
    private ArrayList<Tower> _explosions = new ArrayList<Tower>();
    
    /**
     * The graphic we have while our finger touches the screen.
     */
    private Tower _currentGraphic;
    
    /**
     * Last coordinates to store the point we first touched the screen.
     */
    private Tower.Coordinates _lastCoords;
    
    /**
     * Sound pool which allows us to play multiple sounds at once.
     */
    private SoundPool _soundPool;
    
    /**
     * Id of our explosion sound.
     */
    private int _playbackFile = 0;
    
    /**
     * Cache variable for all used images.
     */
    private Map<Integer, Bitmap> _bitmapCache = new HashMap<Integer, Bitmap>();
    
    /**
     * Constructor called on instantiation.
     * @param context Context of calling activity.
     */
    public GamePanel(Context context) {
        super(context);
        fillBitmapCache();
        
        
        getHolder().addCallback(this);
        _thread = new GameThread(this);
        setFocusable(true);
    }
    
    /**
     * Fill the bitmap cache.
     */
    private void fillBitmapCache() {
        _bitmapCache.put(R.drawable.icon, BitmapFactory.decodeResource(getResources(), R.drawable.icon));
        _bitmapCache.put(R.drawable.abstrakt, BitmapFactory.decodeResource(getResources(), R.drawable.abstrakt));
        _bitmapCache.put(R.drawable.wallpaper, BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper));
        _bitmapCache.put(R.drawable.scissors, BitmapFactory.decodeResource(getResources(), R.drawable.scissors));
        _bitmapCache.put(R.drawable.paper, BitmapFactory.decodeResource(getResources(), R.drawable.paper));
        _bitmapCache.put(R.drawable.rock, BitmapFactory.decodeResource(getResources(), R.drawable.rock));
        _bitmapCache.put(R.drawable.smaller, BitmapFactory.decodeResource(getResources(), R.drawable.smaller));
        _bitmapCache.put(R.drawable.small, BitmapFactory.decodeResource(getResources(), R.drawable.small));
        _bitmapCache.put(R.drawable.big, BitmapFactory.decodeResource(getResources(), R.drawable.big));
    }
    
    /**
     * Process the MotionEvent.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	
    	
        synchronized (getHolder()) {
            Tower graphic = null;
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Random random = new Random();
                int rand = Math.abs(random.nextInt() % 3);
                switch (rand) {
                    case 0: graphic = new Tower(_bitmapCache.get(R.drawable.scissors));
                            graphic.setType("scissors");
                            break;
                    case 1: graphic = new Tower(_bitmapCache.get(R.drawable.rock));
                            graphic.setType("rock");
                            break;
                    case 2: graphic = new Tower(_bitmapCache.get(R.drawable.paper));
                            graphic.setType("paper");
                            break;
                    default:
                        throw new RuntimeException("RANDOM not between 0 and 2: " + rand);
                }
                graphic.getCoordinates().setTouchedX((int) event.getX());
                graphic.getCoordinates().setTouchedY((int) event.getY());
                _lastCoords = new Tower(_bitmapCache.get(R.drawable.icon)).getCoordinates();
                _lastCoords.setTouchedX(graphic.getCoordinates().getTouchedX());
                _lastCoords.setTouchedY(graphic.getCoordinates().getTouchedY());
                _currentGraphic = graphic;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                _currentGraphic.getCoordinates().setTouchedX((int) event.getX());
                _currentGraphic.getCoordinates().setTouchedY((int) event.getY());
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                // calculating speed
                calculatingSpeedX((int) event.getX());
                calculatingSpeedY((int) event.getY());
                _graphics.add(_currentGraphic);
                _currentGraphic = null;
            }
            
        }
        try {
        	Thread.sleep(16);
        } catch (InterruptedException e) {
        	Log.v("App: ", "Error 2");
        }
        
        return true;
        
    
    }
    
    /**
     * Calculating the speed of the item.
     * Using the difference between start point and release point with smoothing factor.
     * 
     * @param currentX X coordinate which represent the last point.
     */
    private void calculatingSpeedX(int currentX) {
        if (currentX != _lastCoords.getTouchedX()) {
            int diff = currentX - _lastCoords.getTouchedX();
            int amplitude = diff / 10;
            _currentGraphic.getSpeed().setX(amplitude);
        } else {
            _currentGraphic.getSpeed().setX(0);
        }
    }
    
    /**
     * Calculating the speed of the item.
     * Using the difference between start point and release point with smoothing factor.
     * 
     * @param currentY Y coordinate which represent the last point.
     */
    private void calculatingSpeedY(int currentY) {
        if (currentY != _lastCoords.getTouchedY()) {
            int diff = currentY - _lastCoords.getTouchedY();
            int amplitude = diff / 10;
            _currentGraphic.getSpeed().setY(amplitude);
        } else {
            _currentGraphic.getSpeed().setY(0);
        }
    }
    
    /**
     * Update the physics of each item already added to the panel.
     * Not including items which are currently exploding and moved by a touch event.
     */
    public void updatePhysics() {
        Tower.Coordinates coord;
        Tower.Speed speed;
        for (Tower graphic : _graphics) {
            coord = graphic.getCoordinates();
            speed = graphic.getSpeed();
            
            // Direction
            coord.setX(coord.getX() + speed.getX());
            coord.setY(coord.getY() + speed.getY());                
            
            // borders for x...
            if (coord.getX() < 0) {
                speed.setX(-speed.getX());
                coord.setX(-coord.getX());
            } else if (coord.getX() + graphic.getBitmap().getWidth() > getWidth()) {
                speed.setX(-speed.getX());
                coord.setX(coord.getX() + getWidth() - (coord.getX() + graphic.getBitmap().getWidth()));
            }
            
            // borders for y...
            if (coord.getY() < 0) {
                speed.setY(-speed.getY());
                coord.setY(-coord.getY());
            } else if (coord.getY() + graphic.getBitmap().getHeight() > getHeight()) {
                speed.setY(-speed.getY());
                coord.setY(coord.getY() + getHeight() - (coord.getY() + graphic.getBitmap().getHeight()));
            }
        }
    }
    
    /**
     * Check all items on the panel for collisions and find the winner.
     * The loser will added to the list of explosions.
     */
    public void checkForWinners() {
        ArrayList<Tower> toExplosion = new ArrayList<Tower>();
        for (Tower grapics : _graphics) {
            for (Tower battleGraphic : _graphics) {
                if (battleGraphic != grapics && !(toExplosion.contains(battleGraphic) || toExplosion.contains(grapics))) {
                    if (!battleGraphic.getType().equals(grapics.getType()) && checkCollision(battleGraphic, grapics)) {
                        if (firstWins(battleGraphic.getType(), grapics.getType())) {
                            toExplosion.add(grapics);
                            _soundPool.play(_playbackFile, 1, 1, 0, 0, 1);
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        if (!toExplosion.isEmpty()) {
            _explosions.addAll(toExplosion);
            _graphics.removeAll(toExplosion);
        }
    }
    
    /**
     * Mathematical calculation of a collision between two items.
     * 
     * @param first First item.
     * @param second Second item.
     * @return Returns true if first and second item hit each other.
     */
    private boolean checkCollision(Tower first, Tower second) {
        boolean retValue = false;
        int width = first.getBitmap().getWidth();
        int height = first.getBitmap().getHeight();
        int firstXRangeStart = first.getCoordinates().getX();
        int firstXRangeEnd = firstXRangeStart + width;
        int firstYRangeStart = first.getCoordinates().getY();
        int firstYRangeEnd = firstYRangeStart + height;
        int secondXRangeStart = second.getCoordinates().getX();
        int secondXRangeEnd = secondXRangeStart + width;
        int secondYRangeStart = second.getCoordinates().getY();
        int secondYRangeEnd = secondYRangeStart + height;
        if ((secondXRangeStart >= firstXRangeStart && secondXRangeStart <= firstXRangeEnd)
            || (secondXRangeEnd >= firstXRangeStart && secondXRangeEnd <= firstXRangeEnd)) {
            if ((secondYRangeStart >= firstYRangeStart && secondYRangeStart <= firstYRangeEnd)
                || (secondYRangeEnd >= firstYRangeStart && secondYRangeEnd <= firstYRangeEnd)) {
                retValue = true;
            }
        }
        return retValue;
    }
    
    /**
     * True if first type wins, false if second type wins.
     * 
     * @param firstType Type of the first object.
     * @param secondType Type of the second object.
     * @return Returns who wins.
     */
    private boolean firstWins(String firstType, String secondType) {
        if (firstType.equals("explosion") || secondType.equals("explosion")) {
            return false;
        } else if (firstType.equals("scissors") && secondType.equals("paper")) {
            return true;
        } else if (firstType.equals("scissors") && secondType.equals("rock")) {
            return false;
        } else if (firstType.equals("paper") && secondType.equals("scissors")) {
            return false;
        } else if (firstType.equals("paper") && secondType.equals("rock")) {
            return true;
        } else if (firstType.equals("rock") && secondType.equals("paper")) {
            return false;
        } else if (firstType.equals("rock") && secondType.equals("scissors")) {
            return true;
        } else {
            throw new RuntimeException("Fight not possible!");
        }
    }
    
    /**
     * Draw on the SurfaceView.
     * Order:
     * <ul>
     *  <li>Background image</li>
     *  <li>Items on the panel</li>
     *  <li>Explosions</li>
     *  <li>Item moved by hand</li>
     * </ul>
     */
    @Override
    public void onDraw(Canvas canvas) {
        // draw the background
        canvas.drawBitmap(_bitmapCache.get(R.drawable.abstrakt), 0, 0, null);
        Bitmap bitmap;
        Tower.Coordinates coords;
        // draw the normal items
        for (Tower graphic : _graphics) {
            bitmap = graphic.getBitmap();
            coords = graphic.getCoordinates();
            canvas.drawBitmap(bitmap, coords.getX(), coords.getY(), null);
        }
        
        // draw the explosions
        ArrayList<Tower> finishedExplosion = new ArrayList<Tower>();
        for (Tower graphic : _explosions) {
            if (!graphic.getType().equals("explosion")) {
                graphic.setType("explosion");
                graphic.setExplosionStep(0);
                graphic.getSpeed().setX(0);
                graphic.getSpeed().setY(0);
                graphic.setBitmap(_bitmapCache.get(R.drawable.smaller));
                bitmap = graphic.getBitmap();
                coords = graphic.getCoordinates();
                canvas.drawBitmap(bitmap, coords.getX(), coords.getY(), null);
            } else {
                switch (graphic.getExplosionStep()) {
                    case 10: bitmap = _bitmapCache.get(R.drawable.small);
                            graphic.setBitmap(bitmap);
                            break;
                    case 20: bitmap = _bitmapCache.get(R.drawable.big);
                            graphic.setBitmap(bitmap);
                            break;
                    case 30: bitmap = _bitmapCache.get(R.drawable.small);
                            graphic.setBitmap(bitmap);
                            break;
                    case 40: bitmap = _bitmapCache.get(R.drawable.smaller);
                            graphic.setBitmap(bitmap);
                            break;
                    default: bitmap = graphic.getBitmap();
                }
                coords = graphic.getCoordinates();
                canvas.drawBitmap(bitmap, coords.getX(), coords.getY(), null);
                graphic.setExplosionStep(graphic.getExplosionStep() + 1);
            }
            if (graphic.getExplosionStep() > 50) {
                finishedExplosion.add(graphic);
            }
        }
        
        // remove all Objects who are already fully exploded...
        if (!finishedExplosion.isEmpty()) {
            _explosions.removeAll(finishedExplosion);
        }
        
        // draw current graphic at last...
        if (_currentGraphic != null) {
            bitmap = _currentGraphic.getBitmap();
            coords = _currentGraphic.getCoordinates();
            canvas.drawBitmap(bitmap, coords.getX(), coords.getY(), null);
        }
    }

    /**
     * Called if you change the configuration like open the keypad.
     */
    //@Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /**
     * Called on creation of the SurfaceView.
     * Which could be on first start or relaunch.
     */
    //@Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!_thread.isAlive()) {
            _thread = new GameThread(this);
        }
        _thread.setRunning(true);
        _thread.start();
    }

    /**
     * Called if the SurfaceView should be destroyed.
     * We try to finish the game loop thread here.
     */
    //@Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        _thread.setRunning(false);
        while (retry) {
            try {
                _thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // we will try it again and again...
            }
        }
        Log.i("thread", "Thread terminated...");
    }
}