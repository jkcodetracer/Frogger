package com.example.codetracer.frogger;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by codetracer on 3/26/17.
 */
public class Car extends Item {
    public Car(float x, float y) {
        super(x, y);
    }

    public void onDraw(Canvas canvas){
        Paint p = new Paint();

        p.setColor(Color.RED);
        canvas.drawRect(getRPos(canvas.getWidth(), x),
                        getRPos(canvas.getHeight(), y),
                        getRPos(canvas.getWidth(), x+length),
                        getRPos(canvas.getHeight(), y+width), p);
    }

    public DeathStatus onDeathCheck(Frog frogger){
        DeathStatus result = DeathStatus.UNKNOWN;

        if (overlapCheck(this.getLeftBound(), this.getRightBound(),
                            frogger.getLeftBound(), frogger.getRightBound())) {
            result = DeathStatus.DEAD;
        }

        return result;
    }
}
