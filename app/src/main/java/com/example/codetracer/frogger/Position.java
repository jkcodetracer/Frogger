package com.example.codetracer.frogger;

/**
 * Created by codetracer on 3/26/17.
 */

public class Position {

    float x, y;

    public Position(float x, float y) {
        super();
        this.x = x;
        this.y = y;
    }

    public double distance(Position o) {
        double dx = x - o.x;
        double dy = y - o.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
}