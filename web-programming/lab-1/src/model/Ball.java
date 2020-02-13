package model;

/**
 * Class represents ball in a tree-dimensional space.
 * @autor Alexander Rai
 * @version 1.0
 */


public class Ball extends Circle {

    /** model.Ball's z coordinate */
    private double z;

    /** Creates a new model.Ball object that represents ball on a two-dimensional plane.
     * @param radius Radius of the ball.(must be greater than 0)
     * @param x X coordinate of the ball
     * @param y Y coordinate of the ball
     * @param z Z coordinate of the ball
     * @exception java.security.InvalidParameterException
     * If the radius parameter is less than or equals to 0
     */
    public Ball(double radius, double x, double y, double z) {
        super(radius, x, y);
        this.z = z;
    }

    /** Creates a new model.Ball object that represents ball on a two-dimensional plane.
     * Coordinates of the ball are set by default(0,0,0).
     * @param radius Radius of the ball.(must be greater than 0)
     * @exception java.security.InvalidParameterException
     * If the radius parameter is less than or equals to 0
     */
    public Ball(double radius){
        this(radius, 0, 0, 0);
    }

    /** Returns the z coordinate of the ball
     * @return the z coordinate of the ball
     */
    public double getZ() {
        return z;
    }

    /** Calculates and returns a square of the ball.
     * @return square of the ball
     */
    public double getSquare(){
        return 4.0 / 3.0 * super.getSquare() * super.getRadius();
    }

    /** Calculates and returns a volume of the ball.
     * @return volume of the ball
     */
    public double getVolume(){
        return super.getSquare();
    }

    /** Returns string that represents the ball
     * @return string that represents the ball
     */
    @Override
    public String toString() {
        return "r:" + super.getRadius() +
                ",coords:(" + super.getX() + "," + super.getY() + "," + z + ")";
    }
}
