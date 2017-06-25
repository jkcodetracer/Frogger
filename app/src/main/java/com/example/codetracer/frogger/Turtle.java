package com.example.codetracer.frogger;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by codetracer on 3/26/17.
 */

public class Turtle extends Item {
    int statusCount = 0;
    TurtleStatus status;

    public enum TurtleStatus {
        ONSURFACE, GOINGDOWN, INWATER;
    }

    public Turtle(float x, float y) {
        super(x, y);
        status = TurtleStatus.ONSURFACE;
    }

    public void onDraw(Canvas canvas){
        Paint p = new Paint();

        p.setColor(Color.CYAN);
        if (status == TurtleStatus.ONSURFACE ||
                status == TurtleStatus.GOINGDOWN) {
            canvas.drawRect(getRPos(canvas.getWidth(), x),
                    getRPos(canvas.getHeight(), y),
                    getRPos(canvas.getWidth(), x + length),
                    getRPos(canvas.getHeight(), y + width), p);
        }
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
        }

       switch(status) {
           case ONSURFACE:
               break;
           case GOINGDOWN:
               statusCount++;
               if (statusCount > 5) {
                   status = TurtleStatus.INWATER;
                   statusCount = 0;
               }
               break;
           case INWATER:
               statusCount++;
               if (statusCount > 5) {
                   status = TurtleStatus.ONSURFACE;
                   statusCount = 0;
               }
               break;
           default:
               break;
       }
    }

    public DeathStatus onDeathCheck(Frog frogger){
        DeathStatus result = DeathStatus.UNKNOWN;
        if (overlapCheck(this.getLeftBound(),this.getRightBound(),
                frogger.getLeftBound(), frogger.getRightBound())) {
            switch(status) {
                case ONSURFACE:
                case GOINGDOWN:
                    status = TurtleStatus.GOINGDOWN;
                    frogger.setSpeed(speed);
                    result = DeathStatus.SAFE;
                    break;
                case INWATER:
                    result = DeathStatus.DEAD;
                    break;
                default:
                    break;
            }
        }

        return result;
    }
}
