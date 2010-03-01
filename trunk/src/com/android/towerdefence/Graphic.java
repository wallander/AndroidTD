package com.android.towerdefence;

import android.graphics.Bitmap;

/**
 * Class which contains a object we want to draw on specific position.
 * 
 * @author martin
 */
public class Graphic {

    /**
     * Class which represent the speed the object has in both x and y direction.
     * 
     * @author martin
     */
    public class Speed {
        private int _x = 1;
        private int _y = 1;

        /**
         * @return The speed in x direction. Negative amount means, the speed is
         *         backward.
         */
        public int getX() {
            return _x;
        }

        /**
         * @param speed Speed in x direction. Negative amount means, the speed
         *            is backward.
         */
        public void setX(int speed) {
            _x = speed;
        }

        /**
         * @return The speed in y direction. Negative amount means, the speed is
         *         backward.
         */
        public int getY() {
            return _y;
        }

        /**
         * @param speed Speed in y direction. Negative amount means, the speed
         *            is backward.
         */
        public void setY(int speed) {
            _y = speed;
        }

        /**
         * Helper method. Useful for debugging.
         */
        public String toString() {
            return "Speed: x: " + _x + " | y: " + _y;
        }
    }

    /**
     * Contains the coordinates of the instance.
     * 
     * @author martin
     */
    public class Coordinates {
        private int _x = 0;
        private int _y = 0;

        /**
         * @return The x coordinate of the upper left corner.
         */
        public int getX() {
            return _x;
        }

        /**
         * @return The x coordinate of the center.
         */
        public int getTouchedX() {
            return _x + _bitmap.getWidth() / 2;
        }

        /**
         * @param value The new x coordinate of the upper left corner.
         */
        public void setX(int value) {
            _x = value;
        }

        /**
         * @param value The new x coordinate of the center.
         */
        public void setTouchedX(int value) {
            _x = value - _bitmap.getWidth() / 2;
        }

        /**
         * @return The y coordinate of the upper left corner.
         */
        public int getY() {
            return _y;
        }

        /**
         * @return The y coordinate of the center.
         */
        public int getTouchedY() {
            return _y + _bitmap.getHeight() / 2;
        }

        /**
         * @param value The new y coordinate of the upper left corner.
         */
        public void setY(int value) {
            _y = value;
        }

        /**
         * @param value The new y coordinate of the center.
         */
        public void setTouchedY(int value) {
            _y = value - _bitmap.getHeight() / 2;
        }

        /**
         * Helper method for debugging.
         */
        public String toString() {
            return "Coordinates: (" + _x + "/" + _y + ")";
        }
    }

    /**
     * Bitmap which should be drawn.
     */
    private Bitmap _bitmap;

    /**
     * Coordinates on which the bitmap should be drawn.
     */
    private Coordinates _coordinates;

    /**
     * Speed of the object.
     */
    private Speed _speed;

    /**
     * Object type which could be rock, scissors, paper or explosion.
     */
    private String _type;

    /**
     * Step of explosion which will take 50 steps.
     */
    private int _explosionStep = 0;

    /**
     * Constructor.
     * 
     * @param bitmap Bitmap which should be drawn.
     */
    public Graphic(Bitmap bitmap) {
        _bitmap = bitmap;
        _coordinates = new Coordinates();
        _speed = new Speed();
    }

    /**
     * @param bitmap New bitmap to draw.
     */
    public void setBitmap(Bitmap bitmap) {
        _bitmap = bitmap;
    }

    /**
     * @return The stored bitmap.
     */
    public Bitmap getBitmap() {
        return _bitmap;
    }

    /**
     * @return The speed of the instance
     */
    public Speed getSpeed() {
        return _speed;
    }

    /**
     * @return The coordinates of the instance.
     */
    public Coordinates getCoordinates() {
        return _coordinates;
    }

    /**
     * @param type The new type of the instance.
     */
    public void setType(String type) {
        _type = type;
    }

    /**
     * @return The type of the instance.
     */
    public String getType() {
        return _type;
    }

    /**
     * @param step The new explosion step.
     */
    public void setExplosionStep(int step) {
        _explosionStep = step;
    }

    /**
     * @return The explosion step.
     */
    public int getExplosionStep() {
        return _explosionStep;
    }
}