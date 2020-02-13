package controller;

import model.Ball;
import model.Circle;
import model.Cylinder;

/**
 * Class provides methods to test class Circle and methods to work with this class.
 * @autor Alexander Rai
 * @version 1.0
 */

public class Main {

    private static Circle[] getTestCircles(){
        Circle[] circles = new Circle[4];

        circles[0] = new Circle(7);
        circles[1] = new Circle(2, 4, 0);
        circles[2] = new Circle(1, -1, 0);
        circles[3] = new Circle(5, 4, -4);

        return circles;
    }

    public static void main(String[] args) {
        Circle[] circles = getTestCircles();
        int a = 0;
        int b = 0;
        System.out.println("Circles on the line " + a + "x + " + b + ": ");
        for (Integer i: CircleProcessing.getOneLineCircles(circles, 0, 0))
            System.out.println(circles[i]);

        System.out.println();

        System.out.println("Circles with max and min radius: ");
        for (Integer i: CircleProcessing.getMaxMinIndexes(circles))
            System.out.println(circles[i]);

        System.out.println();
        System.out.println("Cylinder and ball example:");
        System.out.println(new Cylinder(5.0, 10.0));
        System.out.println(new Ball(7.0, 1.0, 2.0,4.0));
    }



}
