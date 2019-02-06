package com.ultime5528.util;

public class LinearInterpolator {

    private Point[] points;

    public LinearInterpolator(Point[] points) {

        this.points = points;

    }

    public double interpolate(double x) {

        int i = 1;

        if (x <= points[0].x) {

            return points[0].y;

        }

        while (i < points.length && x > points[i].x) {

            i++;

        }

        if (i == points.length) {

            return points[i - 1].y;

        }

        return (points[i].y - points[i - 1].y) / (points[i].x - points[i - 1].x) * (x - points[i - 1].x)
                + points[i - 1].y;

    }

}