package controller;

import model.Circle;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

/**
 * Class provides methods to work with model.Circle class objects.
 * @autor Alexander Rai
 * @version 1.0
 */


public class CircleProcessing {

    /** Returns a list of indexes of circles that located on the defining line(a*x + b).
     * @param circles array of circles to work with
     * @param a a parameter of the equation a*x + b
     * @param b b parameter of the equation a*x + b
     * @return list of indexes of circles that located on the defining line
     */
    public static List<Integer> getOneLineCircles(Circle[] circles, double a, double b){
        LinkedList<Integer> indexes = new LinkedList<>();

        for (int i = 0; i < circles.length; i++){
            if (circles[i].getY() == circles[i].getX() * a + b)
                indexes.add(i);
        }

        return indexes;
    }

    /** Finds and returns array of indexes of circles with max and min squares/perimeters
     * @param circles array of circles to work with
     * @throws java.security.InvalidParameterException
     * If the length of the array is less than 2
     * @return array of indexes of circles with max and min squares
     */
    public static int[] getMaxMinIndexes(Circle[] circles){
        if (circles.length <= 1)
            throw new InvalidParameterException("Invalid array length");

        int max_i = 0, min_i = 0;

        for (int i = 0; i < circles.length; i++){

            if (circles[i].getRadius() > circles[max_i].getRadius())
                max_i = i;

            if(circles[i].getRadius() < circles[min_i].getRadius())
                min_i = i;
        }

        return new int[]{max_i, min_i};
    }

}
