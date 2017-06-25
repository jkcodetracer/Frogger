package com.example.codetracer.frogger;

import android.graphics.Canvas;
import android.util.*;
import android.util.Log;

/**
 * Created by codetracer on 3/26/17.
 */

public class Item extends Position{
    int state = 0;
    int speed = 0;
    int width = 5;
    int length = 0;

    public Item(float x, float y) {
        super(x, y);
    }

    public void setState(int state){
        this.state = state;
    }

    public void setSpeed(int newSpeed){
        this.speed = newSpeed;
    }

    public int getSpeed(){
        return this.speed;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return this.length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth(){
        return this.width;
    }

    public Position getLeftBound(){
        return new Position(x,y);
    }

    public Position getRightBound(){
        return new Position(x+length, y+width);
    }

    public void onDraw(Canvas canvas){

    }

    public float getRPos(float obsPos, float rate){
        return (rate/100)*obsPos;
    }

    public void onStep() {
        int tmp = (int)x;

        if (speed < 0) {

            if (tmp+length+speed >= 0) {
                x = tmp + speed;
            } else {
                x = 100;
            }
        } else {
            x = (tmp+speed)%100;
            //Log.d("On step", "B "+ String.valueOf(tmp));
        }
    }

    public boolean overlapCheck(Position a1, Position a2, Position b1, Position b2) {
        boolean result = false;
        float zx, x, zy, y;

        zx = Math.abs(a1.x+a2.x-b1.x-b2.x);
        x = Math.abs(a1.x-a2.x) + Math.abs(b1.x-b2.x);
        zy = Math.abs(a1.y+a2.y-b1.y-b2.y);
        y = Math.abs(a1.y-a2.y)+Math.abs(b1.y-b2.y);

        if (zx < x && zy < y) {
            result = true;
        }

        return result;
    }

    public DeathStatus onDeathCheck(Frog frogger) {

        return DeathStatus.UNKNOWN;
    }
}
