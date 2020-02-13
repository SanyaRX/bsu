package model;

import java.security.InvalidParameterException;

/**
 * Class represents cylinder in a tree-dimensional space.
 * @autor Alexander Rai
 * @version 1.0
 */


public class Cylinder extends Circle {

    /** model.Cylinder's z coordinate */
    private double z;

    /** model.Cylinder's height */
    private double height;


    /** Creates a new model.Cylinder object that represents cylinder on a two-dimensional plane.
     * @param radius Radius of the cylinder.(must be greater than 0)
     * @param height Height of the cylinder
     * @param x X coordinate of the cylinder
     * @param y Y coordinate of the cylinder
     * @param z Z coordinate of the cylinder
     * @exception java.security.InvalidParameterException
     * If the radius or height parameters are less than or equals to 0
     */
    public Cylinder(double radius, double height, double x, double y, double z) {
        super(radius, x, y);

        if (height <= 0)
            throw new InvalidParameterException("Height can't be less then or equals to 0");

        this.height = height;
        this.z = z;
    }

    /** Creates a new model.Cylinder object that represents cylinder on a two-dimensional plane.
     * Coordinates of the cylinder are set by default(0,0,0).
     * @param radius Radius of the cylinder.(must be greater than 0)
     * @param height Height of the cylinder
     * @exception java.security.InvalidParameterException
     * If the radius or height parameter less than or equals to 0
     */
    public Cylinder(double radius, double height){
        this(radius, height, 0, 0, 0);
    }

    /** Returns the height of the cylinder
     * @return the height of the cylinder
     */
    public double getHeight() {
        return height;
    }

    /** Returns the z coordinate of the cylinder
     * @return the z coordinate of the cylinder
     */
    public double getZ() {
        return z;
    }

    /** Calculates and returns a square of the cylinder.
     * @return square of the cylinder
     */
    public double getSquare(){
        return 2 * super.getSquare() + height * super.getPerimeter();
    }

    /** Calculates and returns a volume of the cylinder.
     * @return volume of the cylinder
     */
    public double getVolume(){
        return height * super.getSquare();
    }

    /** Returns string that represents the cylinder
     * @return string that represents the cylinder
     */
    @Override
    public String toString() {
        return "r:" + super.getRadius() + ",h:"+ height +
                ",coords:(" + super.getX() + "," + super.getY() + "," + z + ")";
    }
}
