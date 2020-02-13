package model;

import java.security.InvalidParameterException;

/**
 * Class represents circle on a two-dimensional plane.
 * @autor Alexander Rai
 * @version 1.0
 */

public class Circle {
    /** model.Circle's radius */
    private double radius;

    /** model.Circle's coordinates */
    private double x, y;

    /** Creates a new model.Circle object that represents circle on a two-dimensional plane.
     * @param radius Radius of the circle.(must be greater than 0)
     * @param x X coordinate of the circle
     * @param y Y coordinate of the circle
     * @exception java.security.InvalidParameterException
     * If the radius parameter is less than or equals to 0
     */
    public Circle(double radius, double x, double y){
        if (radius <= 0)
            throw new InvalidParameterException("Radius can't be less than or equals to 0");

        this.radius = radius;
        this.x = x;
        this.y = y;
    }

    /** Creates a new model.Circle object that represents circle on a two-dimensional plane.
     * Coordinates of the circle are set by default(0,0).
     * @param radius Radius of the circle.(must be greater than 0)
     * @exception java.security.InvalidParameterException
     * If the radius parameter less than or equals to 0
     */
    public Circle(double radius){
        this(radius, 0, 0);
    }

    /** Calculates and returns a square of the circle.
     * @return square of the circle
     */
    public double getSquare(){
        return Math.PI * radius * radius;
    }

    /** Calculates and returns a perimeter of the circle.
     * @return perimeter of the circle
     */
    public double getPerimeter(){
        return 2 * Math.PI * radius;
    }

    /** Returns the radius of the circle
     * @return the radius of the circle
     */
    public double getRadius() {
        return radius;
    }

    /** Returns the x coordinate of the circle
     * @return the x coordinate of the circle
     */
    public double getX() {
        return x;
    }

    /** Returns the y coordinate of the circle
     * @return the y coordinate of the circle
     */
    public double getY() {
        return y;
    }

    /** Returns string that represents the circle
     * @return string that represents the circle
     */
    @Override
    public String toString() {
        return "r:" + radius + ",coords:(" + x + "," + y + ")";
    }
}
